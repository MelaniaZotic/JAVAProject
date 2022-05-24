package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    /*
    private static Connection con;
    private DB() {};
    private static void Initiere()
    {
        String url = "jdbc:mysql://localhost:3306/javaproject";
        String user = "root";
        String pass = "ciscosecpa55";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection connection()
    {
        Initiere();
        return con;
    }

     */
    String url = "jdbc:mysql://localhost:3306/javaproject";
    String user = "root";
    String pass = "ciscosecpa55";
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
