package manager;



import db.DBConnectionProvider;
import model.Task;
import model.TaskStatus;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private UserManager userManager = new UserManager();


    public boolean create(Task task) {
        String sql = "INSERT INTO task(name,description,deadline,status,user_id) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getName());
            statement.setString(2,task.getDescription());
            if (task.getDeadline() != null) {
                statement.setString(3, sdf.format(task.getDeadline()));
            } else {
                statement.setString(3, null);
            }

            statement.setString(4, task.getTaskStatus().name());
            statement.setInt(5, task.getUserId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setId(generatedKeys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Task getTaskById(long id) {
        String sql = "SELECT * FROM task WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return getTaskFromResultSet(resultSet);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Task> getAllTaskByUserId(int userId) {
        List<Task> task = new ArrayList<>();
        String sql = "SELECT * FROM task WHERE user_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                task.add(getTaskFromResultSet(resultSet));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    public List<Task> getAllTask() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tasks.add(getTaskFromResultSet(resultSet));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private Task getTaskFromResultSet(ResultSet resultSet) {
        try {
            try {
                return Task.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .deadline(resultSet.getString(4) == null ? null : sdf.parse(resultSet.getString(4)))
                        .taskStatus(TaskStatus.valueOf(resultSet.getString(5)))
                        .userId(resultSet.getInt(6))
                        .user(userManager.getById(resultSet.getInt(6)))
                        .build();
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(int taskId, String status) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE task SET status = ? WHERE id = ?");
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, taskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean delete(int id) {
        String sql = "DELETE FROM task WHERE  id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
