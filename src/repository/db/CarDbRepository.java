package repository.db;

import config.DB;
import config.DbConnection;
import model.Car;
import model.Client;
import repository.RepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

//1:04
public class CarDbRepository{

    private DbConnection dbConnection;

    public CarDbRepository(DbConnection dbConnection){
        this.dbConnection = dbConnection;

    }
    public List<Car>selectCar() throws SQLException{
        PreparedStatement st = dbConnection.prepareStatement("Select * from Car");
        ResultSet rs = st.executeQuery();
        List<Car> result = new ArrayList<>();
        while(rs.next()){
            Car car = new Car();
            car.setIdCar(rs.getInt("idCar"));
            car.setNoRegistration(rs.getString("NoRegistration"));
            car.setBrand(rs.getString("brand"));
            car.setColor(rs.getString("color"));
            result.add(car);
        }
        return result;
    }
    public  void insertCar(List<Car> car) throws SQLException {
        String SQL = " INSERT INTO car(idCar, noRegistration, brand, color) " + "values(?,?,?,?)";
        DB db = new DB();
        try (
                Connection connection = db.connect();
                PreparedStatement pstmt = connection.prepareStatement(SQL);) {
            int count = 0;
            for (Car car1 : car) {
                pstmt.setInt(1, car1.getIdCar());
                pstmt.setString(2, car1.getNoRegistration());
                pstmt.setString(3, car1.getBrand());
                pstmt.setString(4, car1.getColor());
                pstmt.addBatch();
                count++;
                if (count % 100 == 0 || count == car.size()) {
                    pstmt.executeBatch();
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }




}
