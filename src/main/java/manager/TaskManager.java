package manager;

import db.DBConnectionProvider;
import model.Task;
import model.TaskStatus;
import model.User;
import model.UserType;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TaskManager {

    private Connection connection;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private UserManager userManager = new UserManager();

    public TaskManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }


    public void addTask(Task task)  {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("Insert into task (name,description,deadline,status,user_id) Values(?,?,?,?,?)");
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, sdf.format(task.getDeadline()));
            preparedStatement.setString(4, task.getTaskStatus().name());
            preparedStatement.setInt(5, task.getUserId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                task.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<Task> getAllTasks()  {
        Statement statement = null;
        List<Task> tasks = new LinkedList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM task");
            tasks = getTasksFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> getAllTasksByUserId(int userId) {
        PreparedStatement statement = null;
        List<Task> tasks = new LinkedList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM task WHERE user_id =?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            tasks = getTasksFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    private List<Task> getTasksFromResultSet(ResultSet resultSet) throws SQLException {
        List<Task> tasks = new LinkedList<>();
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getInt("id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            try {
                task.setDeadline(sdf.parse(resultSet.getString("deadline")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            task.setTaskStatus(TaskStatus.valueOf(resultSet.getString("status")));
            task.setUserId(resultSet.getInt("user_id"));
            task.setUser(userManager.getUserById(task.getUserId()));

            tasks.add(task);
        }
        return tasks;
    }

    public void updateTaskStatus(int taskId, String newStatus){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE task set status = ? where id = ?");
            preparedStatement.setString(1,newStatus);
            preparedStatement.setInt(2,taskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public Task getTaskById(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM task WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                try {
                    task.setDeadline(sdf.parse(resultSet.getString("deadline")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                task.setTaskStatus(TaskStatus.valueOf(resultSet.getString("status")));
                task.setUserId(resultSet.getInt("user_id"));
                task.setUser(userManager.getUserById(task.getUserId()));
                return task;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }


}