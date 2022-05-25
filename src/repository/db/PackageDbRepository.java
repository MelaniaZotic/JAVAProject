package repository.db;

import config.DB;
import config.DbConnection;
import model.Client;
import model.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PackageDbRepository {
    private DbConnection dbConnection;
    public PackageDbRepository(DbConnection dbConnection){
        this.dbConnection = dbConnection;
    }
    public List<Package>selectPackage()throws SQLException{
        PreparedStatement st = dbConnection.prepareStatement("Select * from Package");
        ResultSet rs = st.executeQuery();
        List<Package> result = new ArrayList<>();
        while(rs.next()){
            Package pack = new Package();
            pack.settype(rs.getString("type"));
            pack.setweightColet(rs.getFloat("weightPackage"));
            result.add(pack);
        }
        return result;
    }

    public void deletePackage(Package packages){
        String SQL = "DELETE FROM package WHERE id = ?";
        Scanner scan = new Scanner(System.in);
        System.out.println("Ce pachet doresti sa stergi din baza de date?");
        Integer index = scan.nextInt();
        DB db = new DB();
        try (
                Connection connection = db.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL); ){
            preparedStatement.setInt(1,index);
            preparedStatement.execute();
           // connection.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
    public void updatePackage(Package pack) throws SQLException {
        Scanner s = new Scanner(System.in);
        System.out.println("Dati id-ul pachetului pe care doresti sa-l modifici: ");
        int cond = Integer.parseInt(s.nextLine());
        System.out.print("Id: ");
        int id = Integer.parseInt(s.nextLine());
        System.out.print("Package Type: ");
        String type = s.nextLine();
        System.out.print("weightColet: ");
        float weightColet = Float.parseFloat(s.nextLine());
        String sql = "UPDATE PACKAGE set id=?, type=?, weightPackage=? where id = ?";
        PreparedStatement pr = dbConnection.prepareStatement(sql);
        pr.setInt(1,id);
        pr.setString(2,type);
        pr.setFloat(3,weightColet);
        pr.setInt(4,cond);
        pr.executeUpdate();

    }
    public void insertPack(List<Package> packages) throws SQLException{
        String SQL = "INSERT INTO package(id,type,weightPackage)" + "values(?,?,?)";
        DB db = new DB();
        try (
                Connection connection = db.connect();
                PreparedStatement pstmt = connection.prepareStatement(SQL);) {
            int count = 0;
            for (Package pack : packages) {
                pstmt.setInt(1, pack.getId());
                pstmt.setString(2, pack.gettype());
                pstmt.setFloat(3, pack.getweightColet());
                pstmt.addBatch();
                count++;
                if (count % 100 == 0 || count == packages.size()) {
                    pstmt.executeBatch();
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }
}
