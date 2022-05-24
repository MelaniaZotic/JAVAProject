package view;

import config.DbConnection;
import model.Car;
import repository.db.CarDbRepository;
import service.*;
import service.csv.PackageCSVService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner s = new Scanner(System.in);
    private CompanyServices companyServices = new CompanyServices();
    private PackageServices packageServices = new PackageServices();
    private DriverServices driverServices  = new DriverServices();
    private CarServices carServices = new CarServices();
    //private TransportServices transportServices = new TransportServices();
    private PackageCSVService packageCSVService = new PackageCSVService();
    private ClientServices clientServices = new ClientServices();
    public static void main(String args[]) throws ClassNotFoundException, SQLException{

    }
}
