package db;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionProvider {

    private static DBConnectionProvider ourInstance = new DBConnectionProvider();

    private Connection connection;

    private  String dbDriver ;
    private  String dbUrl;
    private  String dbUsername ;
    private  String dbPassword ;

    private DBConnectionProvider() {
        try {
            loadProperties();
            Class.forName(dbDriver);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("C:\\Users\\WPP\\IdeaProjects\\Task-Management\\src\\main\\resources\\DBConfig.properties"));
        dbUrl = properties.getProperty("db.Url");
        dbDriver = properties.getProperty("db.Driver");
        dbUsername = properties.getProperty("db.Username");
        dbPassword = properties.getProperty("db.Password");
    }

    public static DBConnectionProvider getInstance() {
        return ourInstance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
