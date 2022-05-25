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
    public void updateClient(Client client) throws SQLException{
        Scanner s = new Scanner(System.in);
        System.out.println("Dati id-ul clientului pe care doriti sa-l modificati: ");
        int cond = Integer.parseInt(s.nextLine());
        System.out.print("User client: ");
        int userClient = Integer.parseInt(s.nextLine());
        System.out.print("Last Name: ");
        String lastName = s.nextLine();
        System.out.print("First Name: ");
        String firstName = s.nextLine();
        System.out.print("Phone No: ");
        String noPhone = s.nextLine();
        System.out.print("Adresa: ");
        String adresa = s.nextLine();
        String SQL = "UPDATE client set userClient = ?, lastName=?,firstName=?,noPhone=?,address=? where userClient=?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(SQL);
        preparedStatement.setInt(1,userClient);
        preparedStatement.setString(2,lastName);
        preparedStatement.setString(3,firstName);
        preparedStatement.setString(4,noPhone);
        preparedStatement.setString(5,adresa);
        preparedStatement.setInt(6,cond);
        preparedStatement.executeUpdate();
    }
    public void deleteClient(Client client){
        String SQL = "DELETE FROM client where userClient = ?";
        Scanner s = new Scanner(System.in);
        System.out.println("Ce client doresti sa stergi?");
        int index = s.nextInt();
        DB db = new DB();
        try(
                Connection connection = db.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
            preparedStatement.setInt(1, index);
            preparedStatement.execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}



