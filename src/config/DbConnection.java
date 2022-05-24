package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbConnection {
    /*
    private final String url = "jdbc:mysql://localhost:3306/javaproject";
    private final String username = "root";
    private final String password = "ciscosecpa55";

    public Connection connect() throws SQLException{
        return DriverManager.getConnection(url,username,password)
    }

     */
    /*
    private static Connection con;
    private DbConnection(){};
    private static void Initiere()
    {
        String url = "jdbc:mysql://localhost:3306/javaproject";
        String username = "root";
        String password = "ciscosecpa55";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);

        }catch (ClassNotFoundException | SQLException E){
            E.printStackTrace();
        }
    }
    public static Connection getConnection()
    {
        Initiere();;
        return con;
    }
}

     */

    Connection connection;
    public DbConnection(String url, String user, String pass) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,user,pass);

    }
    public Connection getInstance() throws SQLException {
        if(connection == null){
        String url = "jdbc:mysql://localhost:3306/javaproject";
        String username = "root";
        String password = "ciscosecpa55";
        connection = DriverManager.getConnection(url,username,password);
        }
        return connection;
    }
    public void close() throws SQLException{
        connection.close();
    }
    public PreparedStatement prepareStatement(String st) throws SQLException{
        if(connection.isClosed()){

        }
        return connection.prepareStatement(st);
    }
}
