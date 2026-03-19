import java.sql.*;

public class DbConnection {

    private static final String url  = "jdbc:mysql://localhost:3306/javadb";
    private static final String user = "root";
    private static final String password = "root";


    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url,user,password);
    }
}
