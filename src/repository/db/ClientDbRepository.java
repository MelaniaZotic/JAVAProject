package repository.db;

import config.DB;
import config.DbConnection;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientDbRepository {
    private DbConnection dbConnection;

    public ClientDbRepository(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Client> selectClient() throws SQLException {
        PreparedStatement st = dbConnection.prepareStatement("Select * from client");
        ResultSet rs = st.executeQuery();
        List<Client> result = new ArrayList<>();
        while (rs.next()) {
            Client client = new Client();
            client.setUserClient(rs.getInt("userClient"));
            client.setfirstName(rs.getString("firstName"));
            client.setlastName(rs.getString("lastName"));
            client.setphoneNo(rs.getString("noPhone"));
            client.setAddress(rs.getString("address"));
            result.add(client);

        }
        return result;
    }

    /*
        public void insertClient(Client client) {
            String query = "insert into client values (?,?,?,?,?)";
            try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                preparedStatement.setInt(1, client.getUserClient());
                preparedStatement.setString(2, client.getfirstName());
                preparedStatement.setString(3, client.getlastName());
                preparedStatement.setString(4, client.getphoneNo());
                preparedStatement.setString(5, client.getAddress());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

     */
    public void insertClient(List<Client> client) throws SQLException {
        String SQL = " INSERT INTO client(userClient, firstName, lastName, noPhone, address) " + "values(?,?,?,?,?)";
        DB db = new DB();
        try (
                Connection connection = db.connect();
                PreparedStatement pstmt = connection.prepareStatement(SQL);) {
            int count = 0;
            for (Client client1 : client) {
                pstmt.setInt(1, client1.getUserClient());
                pstmt.setString(2, client1.getfirstName());
                pstmt.setString(3, client1.getlastName());
                pstmt.setString(4, client1.getphoneNo());
                pstmt.setString(5, client1.getAddress());
                pstmt.addBatch();
                count++;
                if (count % 100 == 0 || count == client.size()) {
                    pstmt.executeBatch();
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}


    /*
    public void insertClient(Connection connection, List<Client> client) throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ce client doresti sa adaugi in baza de date?");
        Integer index = scan.nextInt();
        String query = "insert into client values (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,client.get(index).getUserClient());
        preparedStatement.setString(2,client.get(index).getfirstName());
        preparedStatement.setString(3,client.get(index).getlastName());
        preparedStatement.setString(4,client.get(index).getphoneNo());
        preparedStatement.setString(5,client.get(index).getAddress());
        preparedStatement.executeUpdate();
    }
     */
   /*
    public  List<Client> void insertClient()throws SQLException{
        Scanner scanner = new Scanner(System.in);
        PreparedStatement st = dbConnection.prepareStatement("Insert into client values (?,?,?,?,?)");
        ResultSet rs = st.executeQuery();
        List<Client> result = new ArrayList<>();
        Integer indexClient =
        while (rs.next()){
            Client client = new Client();
            client.;
        }
    }
*/



