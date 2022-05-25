package repository.db;

import config.DB;
import config.DbConnection;
import model.Car;
import model.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverDbRepository {
    private DbConnection dbConnection;

    public DriverDbRepository(DbConnection dbConnection){
        this.dbConnection = dbConnection;
    }
    public List<Driver> selectDriver() throws SQLException{
        PreparedStatement st = dbConnection.prepareStatement("Select * from driver");
        ResultSet rs = st.executeQuery();
        List<Driver> result = new ArrayList<>();
        while(rs.next()){
            Driver driver = new Driver();
            Car car = new Car();
            driver.setIdDriver(rs.getInt("idDriver"));
            driver.setfirstName(rs.getString("firstName"));
            driver.setlastName(rs.getString("lastName"));
            driver.setphoneNo(rs.getString("noPhone"));
            car.setIdCar(rs.getInt("idCar"));
            //car.setNoRegistration(rs.getString("noRegistration"));
            //car.setBrand(rs.getString("brand"));
            //car.setBrand(rs.getString("color"));
            driver.setCar(car);
            result.add(driver);
        }
        return result;
    }
    public void insertDriver(List<Driver>driver ) throws SQLException{
        String SQL = "INSERT INTO driver(idDriver,firstName,lastName,noPhone,idCar)" + "values(?,?,?,?,?)";
        DB db = new DB();
        try (
                Connection connection = db.connect();
                PreparedStatement pstmt = connection.prepareStatement(SQL);) {
            int count = 0;
            for (Driver driver1 : driver) {
                pstmt.setInt(1, driver1.getIdDriver());
                pstmt.setString(2, driver1.getfirstName());
                pstmt.setString(3, driver1.getlastName());
                pstmt.setString(4, driver1.getphoneNo());
                pstmt.setString(5, driver1.getphoneNo());
                pstmt.addBatch();
                count++;
                if (count % 100 == 0 || count == driver.size()) {
                    pstmt.executeBatch();
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }
    public void deleteDriver(Driver driver){
        String SQL = "DELETE FROM driver WHERE idDriver = ?";
        Scanner scan = new Scanner(System.in);
        System.out.println("Ce sofer doresti sa stergi?");
        int index = scan.nextInt();
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
    public void updateDriver(Driver driver) throws SQLException{
        Scanner s = new Scanner(System.in);
        System.out.println("Dati id-ul soferului pe care doriti sa-l modificati: ");
        int cond = Integer.parseInt(s.nextLine());
        System.out.print("Driver id: ");
        int idDriver = Integer.parseInt(s.nextLine());
        System.out.print("Last name: ");
        String lastName = s.nextLine();
        System.out.print("First name: ");
        String firstName = s.nextLine();
        System.out.print("noPhone: ");
        String noPhone = s.nextLine();
        String SQL = "UPDATE driver set idDriver=?, lastname=?, firstName=?,noPhone = ? where idDriver=?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(SQL);
        preparedStatement.setInt(1,idDriver);
        preparedStatement.setString(2,firstName);
        preparedStatement.setString(3,lastName);
        preparedStatement.setString(4,noPhone);
        preparedStatement.setInt(5,cond);
        preparedStatement.executeUpdate();
    }
}
