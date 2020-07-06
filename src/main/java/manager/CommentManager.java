package manager;

import db.DBConnectionProvider;
import model.Comment;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class CommentManager {

    private Connection connection;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private UserManager userManager = new UserManager();
    TaskManager taskManager = new TaskManager();

    public CommentManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }


    public void addComment(Comment comment) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("Insert into comment (task_id,user_id,comment) Values(?,?,?)");
            preparedStatement.setInt(1, comment.getTaskId());
            preparedStatement.setInt(2, comment.getUserId());
            preparedStatement.setString(3, comment.getComment());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                comment.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<Comment> getAllComments() {
        Statement statement = null;
        List<Comment> comments = new LinkedList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM comment");
            comments = getCommentsFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public List<Comment> getAllCommentsByUserId(int user_id) {
        PreparedStatement statement = null;
        List<Comment> comments = new LinkedList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM comment WHERE user_id =?");
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            comments = getCommentsFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    public List<Comment> getAllCommentsByTaskId(int task_id) {
        PreparedStatement statement = null;
        List<Comment> comments = new LinkedList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM comment WHERE task_id =?");
            statement.setInt(1, task_id);
            ResultSet resultSet = statement.executeQuery();
            comments = getCommentsFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    private List<Comment> getCommentsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Comment> comments = new LinkedList<>();
        while (resultSet.next()) {
            Comment comment = new Comment();
            comment.setId(resultSet.getInt("id"));
            comment.setTaskId(resultSet.getInt("task_id"));
            comment.setUserId(resultSet.getInt("user_id"));
            comment.setComment(resultSet.getString("comment"));
            try {
                comment.setDate(sdf.parse(resultSet.getString("date")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            comment.setTask(taskManager.getTaskById(comment.getTaskId()));
            comment.setUser(userManager.getUserById(comment.getUserId()));

            comments.add(comment);
        }
        return comments;
    }
    public void deleteCommentById(int id) throws SQLException {
        String sql = "DELETE FROM comment WHERE  id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}