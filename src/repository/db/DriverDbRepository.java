package repository.db;

import config.DbConnection;
import model.Car;
import model.Driver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
