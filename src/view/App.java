package view;

import config.DB;
import config.DbConnection;
import exceptions.InvalidDataException;
import model.*;
import model.Package;
import repository.db.CarDbRepository;
import repository.db.ClientDbRepository;
import repository.db.DriverDbRepository;
import repository.db.PackageDbRepository;
import service.*;
import service.csv.AuditServices;
import service.csv.PackageCSVService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private Scanner s = new Scanner(System.in);
    private CompanyServices companyServices = new CompanyServices();
    private PackageServices packageServices = new PackageServices();
    private DriverServices driverServices  = new DriverServices();
    private CarServices carServices = new CarServices();
    //private TransportServices transportServices = new TransportServices();
    private PackageCSVService packageCSVService = new PackageCSVService();
    private  ClientServices clientServices = new ClientServices();
    public static void main(String args[]) throws InvalidDataException, SQLException, ClassNotFoundException {
       // view.App app = new view.App();
        //app.listedInCSVFiles();
        //app.listedCSVFiles();
       while(true) {
           Scanner scanner = new Scanner(System.in);
           System.out.println("Cum doresti sa lucrezi 1- manual; 2- baza de date?\n");
           String optiune = scanner.nextLine();
           view.App app = new App();
           if(optiune.equals("1")) {
               app.showMenu();
               int option = app.readOption();
               app.execute(option);
               //app.listedInCSVFiles();
               //app.listedCSVFiles();
           }
           else
               if(optiune.equals("2")){
                app.showMenuDb();
                int op = app.readOption();
                app.executeDb(op);
               }
       }

    }
    public void showMenu() {
        System.out.println("---------------------");
        System.out.println("What do you want to do?");
        System.out.println("1. Adauga colet");
        System.out.println("2. Adauga sofer");
        System.out.println("3. Adauga masina");
        System.out.println("4. Adauga companie");
        System.out.println("5. Adauga  client");
        System.out.println("6. Listare masini");
        System.out.println("7. Listare colete");
        System.out.println("8. Listare soferi");
        System.out.println("9. Listare companie");
        System.out.println("10. Update sofer(update car)");
        System.out.println("11. Delete sofer");
        System.out.println("12. exit");
        System.out.print("Option:");
    }

    private int readOption() {
        try {
            int option = readInt();
            if (option >= 1 && option <= 12) {
                return option;
            }
        } catch (InvalidDataException invalid) {
            // nothing to do, as it's handled below
        }
        System.out.println("Invalid option. Try again");
        return readOption();
    }

    private int readInt() throws InvalidDataException {
        String line = s.nextLine();
        if (line.matches("^\\d+$")) {
            return Integer.parseInt(line);
        } else {
            throw new InvalidDataException("Invalid number");
        }
    }
    /*private void executeDb(int option) throws SQLException, ClassNotFoundException {
        switch (option){
            case 1:
                DbConnection db = new DbConnection("jdbc:mysql://localhost:3306/javaproject","root", "ciscosecpa55");
                CarDbRepository carDb = new CarDbRepository(db);
                List<Car> cars = carDb.selectCar();

                for(Car car : cars){
                    System.out.println(car);
                }
                break;
        }
    }*/
    private void executeDb(int option) throws SQLException, ClassNotFoundException {
        DbConnection db = new DbConnection("jdbc:mysql://localhost:3306/javaproject","root", "ciscosecpa55");
        CarDbRepository carDb = new CarDbRepository(db);
        List<Car> c = new ArrayList<>();
        PackageDbRepository packDb = new PackageDbRepository(db);
        DriverDbRepository driverDbRepository = new DriverDbRepository(db);
        ClientDbRepository clientDbRepository = new ClientDbRepository(db);
        switch (option){
            case 1:
                List<Car> cars = carDb.selectCar();

                for(Car car : cars){
                    System.out.println(car);
                }
                break;

            case 2:
                List<Package> packages = packDb.selectPackage();

                for(Package pack: packages){
                    System.out.println(pack);
                }
                break;

            case 3:
                List<Driver> drivers =  driverDbRepository.selectDriver();
                for(Driver driver: drivers){
                    System.out.println(driver);
                }
                break;
            case 4:
                List<Client> clients = clientDbRepository.selectClient();
                for(Client client: clients){
                    System.out.println(client);
                }
                break;
            case 5:

                List<Client> client = new ArrayList<Client>();
                System.out.print("User client: ");
                int userClient = Integer.parseInt(s.nextLine());
                System.out.print("Last Name: ");
                String lastName = s.nextLine();
                System.out.print("First Name: ");
                String firstName = s.nextLine();
                System.out.print("Phone No: ");
                String phoneNo = s.nextLine();
                System.out.print("Adresa: ");
                String adresa = s.nextLine();
                client.add(new Client(userClient,lastName,firstName,phoneNo,adresa));

                clientDbRepository.insertClient(client);
                break;


               // ClientDbRepository clientDbRepository1 = new ClientDbRepository();
            case 6:
                List<Driver> dr = new ArrayList<>();
                System.out.print("Driver id: ");
                int idDriver = Integer.parseInt(s.nextLine());
                System.out.print("Last name: ");
                String firsttName = s.nextLine();
                System.out.print("First name: ");
                String lasttName = s.nextLine();
                System.out.print("noPhone: ");
                String noPhone = s.nextLine();
                int idCar = Integer.parseInt(s.nextLine());
                System.out.print("IdCar: ");
                dr.add(new Driver(idDriver,firsttName,lasttName,noPhone,idCar));
                driverDbRepository.insertDriver(dr);
                break;
            case 7:
                List<Package> pk = new ArrayList<>();
                System.out.print("Id: ");
                int id = Integer.parseInt(s.nextLine());
                System.out.print("Package Type: ");
                String type = s.nextLine();
                System.out.print("weightColet: ");
                float weightColet = Float.parseFloat(s.nextLine());
                pk.add(new Package(id,type,weightColet));
                packDb.insertPack(pk);
                break;

            case 8:
                List<Car> car = new ArrayList<>();
                System.out.print("idCar: ");
                int idC = Integer.parseInt(s.nextLine());
                System.out.print("No Registration: ");
                String noRegistration = s.nextLine();
                System.out.print("Brand: ");
                String brand = s.nextLine();
                System.out.print("Color: ");
                String color = s.nextLine();
                car.add(new Car(idC,noRegistration,brand,color));
                carDb.insertCar(car);
                break;
            case 9:
                Package pack = new Package();
                packDb.deletePackage(pack);
                break;
            case 10:
                Driver driver = new Driver();
                driverDbRepository.deleteDriver(driver);
                break;
            case 11:
                Client client1 = new Client();
                clientDbRepository.deleteClient(client1);;
                break;
            case 12:
                Car car1 = new Car();
                carDb.deleteCar(car1);
                break;

            case 13:
                Package p = new Package();
                packDb.updatePackage(p);
                break;
            case 14:
                Client cl = new Client();
                clientDbRepository.updateClient(cl);
                break;

            case 15:
                Car c1 = new Car();
                carDb.updateCar(c1);
                break;
            case 16:
                Driver driver1 = new Driver();
                driverDbRepository.updateDriver(driver1);
            case 17:
                System.exit(0);
        }
    }
    public void showMenuDb() {
        System.out.println("---------------------");
        System.out.println("What do you want to do?");
        System.out.println("1. Afisare masini");
        System.out.println("2. Afisare pachete");
        System.out.println("3. Afisare soferi");
        System.out.println("4. Afisare clienti");
        System.out.println("5. Adauga clienti ");
        System.out.println("6. Adauga sofer ");
        System.out.println("7. Adauga pachete ");
        System.out.println("8. Adauga masini ");
        System.out.println("9. Sterge pachet ");
        System.out.println("10. Sterge sofer");
        System.out.println("11. Sterge client ");
        System.out.println("12. Sterge masina ");
        System.out.println("13. Modifica pachet ");
        System.out.println("14. Modifica client ");
        System.out.println("15. Modifica masina");
        System.out.println("16. Modifica sofer ");
        System.out.println("17. Exit");
        System.out.print("Option:");
    }
    private void execute(int option) throws InvalidDataException, SQLException, ClassNotFoundException {
        switch (option) {
            case 1:
                readPackage();
                audit("Added a new package");
                break;
            case 2:
                readDrivers();
                audit("Added a new drive");
                break;
            case 3:
                readCars();
                audit("Added a new cars");
                break;
            case 4:
                readCompany();
                audit("Added a new company");
                break;
            case 5:
                readClient();
                audit("Added a new client");
                break;
            case 6:
                printAllCars();
                audit("All cars are listed");
                break;
            case 7:
                printAllPackages();
                audit("All packages are listed");
                break;
            case 8:
                printAllDrivers();
                audit("All drivers are listed");
                break;
            case 9:
                printAllCompanies();
                audit("All companies are listed");
                break;
            case 10:
                updateDriver();
                audit("Driver updated");
                break;
            case 11:
                System.out.println("Enter de id of the driver to delete: ");
                int id = s.nextInt();
                s.nextLine();
                driverServices.deleteDriver(id);
                audit("Driver deleted");
                break;

            case 12:
                System.exit(0);
        }
    }


    private void listedCSVFiles(){

        packageServices.listedFromCSV();
        driverServices.listedFromCSV();
        carServices.listedFromCSV();
        clientServices.listedFromCSV();

    }


    private void listedInCSVFiles(){
        packageServices.listedInCSV();
        driverServices.listedInCSV();
        carServices.listedInCSV();
        clientServices.listedInCSV();
    }


    private void audit(String act){
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        AuditServices auditServices = new AuditServices();
        auditServices.write(act, date.format(localDateTime));
    }

    void updateDriver() {
        System.out.println("For the driver update the car: ");
        System.out.println("Driver id: ");
        int index = s.nextInt();
        s.nextLine();
        System.out.println("No Registration car: ");
        int idCar = s.nextInt();
        s.nextLine();
        driverServices.updateCar(index,idCar);
    }



    void printAllCompanies()
    {
        List<DeliveryCompany> deliveryCompanies = companyServices.getAllF();
        if(deliveryCompanies.size() == 0){
            System.out.println("The company doesn't exist!");
        }
        for(DeliveryCompany deliveryCompany: deliveryCompanies){
            System.out.println(deliveryCompany);
        }
    }

    void printAllDrivers(){
        List<Driver> drivers  = driverServices.getAllDrivers();
        if(drivers.size() == 0){
            System.out.println("The drivers doesn't exist");
        }
        for(Driver driver: drivers){
            System.out.println(driver);
        }
    }

    void printAllPackages(){
        List<Package> packages = packageServices.getAllPackages();
        if(packages.size() == 0){
            System.out.println("The packages doesn't exist! ");
        }
        for(Package pack :packages){
            System.out.println(packages);
        }
    }

    void printAllCars(){
        List<Car> cars = carServices.getAllCars();
        if(cars.size() == 0) {
            System.out.println("The cars doesn't exist");
        }
        for(Car car: cars){
            System.out.println(car);
        }
    }
    void printAllClients(){
        List<Client> clients = clientServices.getAllClients();
        if(clients.size() == 0){
            for(Client client:clients){
                System.out.println(client);
            }
        }
    }
    void readClient(){
        System.out.println("How many clients do you add?");
        int c = Integer.parseInt(s.nextLine());
        for(int i = 0 ; i < c ;i++){
            System.out.print("The client no: ");
            System.out.println(i+1);
            try {
                System.out.print("User client: ");
                int userClient = Integer.parseInt(s.nextLine());
                System.out.print("Last Name: ");
                String lastName = s.nextLine();
                System.out.print("First Name: ");
                String firstName = s.nextLine();
                System.out.print("Phone No: ");
                String phoneNo = s.nextLine();
                System.out.print("Adresa: ");
                String adresa = s.nextLine();

                clientServices.addNewClients(lastName,firstName,phoneNo,userClient,adresa);
            }catch (InvalidDataException | NumberFormatException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    void readCompany() {
        System.out.println("How many companies do you add?");
        int cntrc = Integer.parseInt(s.nextLine());
        for(int it = 0; it<cntrc; it++) {
            System.out.print("The drive no : ");
            System.out.println(it+1);
            System.out.print("Name: ");
            String name = s.nextLine();
            System.out.print("Transport type: ");
            String transportType = s.nextLine();

            System.out.println("No of drivers: ");
            int cntr = Integer.parseInt(s.nextLine());
            List<Driver> drivers = driverServices.getAllDrivers();
            for (int i = 0; i < cntr; i++) {
                System.out.println("Driver: ");
                System.out.print("Last Name: ");
                String lastName = s.nextLine();
                System.out.print("First Name: ");
                String firstName = s.nextLine();
                System.out.print("Phone No: ");
                String phoneNo = s.nextLine();
                System.out.print("idDriver: ");
                int idDriver = Integer.parseInt(s.nextLine());
                System.out.println("Car: ");
                System.out.println("Id car:");
                int idCar = Integer.parseInt(s.nextLine());
                System.out.print("No Registration: ");
                String noRegistration = s.nextLine();
                System.out.print("Brand: ");
                String brand = s.nextLine();
                System.out.print("Culoare: ");
                String color = s.nextLine();

                Car car = new Car(idCar, noRegistration, brand, color);
                Driver driver = new Driver(lastName, firstName, phoneNo, idDriver, car);

                CompanyServices.addNewCompany(name, transportType, driver);
            }
        }
    }


    void readCars() {
        System.out.println("How many cars do you add?");
        int cntr = Integer.parseInt(s.nextLine());
        for(int i = 0; i<cntr; i++) {
            try {
                System.out.print("idCar: ");
                int idCar = Integer.parseInt(s.nextLine());
                System.out.print("No Registration: ");
                String noRegistration = s.nextLine();
                System.out.print("Brand: ");
                String brand = s.nextLine();
                System.out.print("Color: ");
                String color = s.nextLine();

                carServices.addNewCar(idCar, noRegistration, brand, color);
            } catch (InvalidDataException invalidData) {
                System.out.println(invalidData.getMessage());

            }
        }
    }
    void readPackage() {
        System.out.println("How many packages do you add?");
        int cntr = Integer.parseInt(s.nextLine());
        for (int i = 0; i < cntr; i++) {
            System.out.print("The package no : ");
            System.out.println(i+1);
            try {
                System.out.print("Id: ");
                int id = Integer.parseInt(s.nextLine());
                System.out.print("Package Type: ");
                String type = s.nextLine();
                System.out.print("weightColet: ");
                float weightColet = Float.parseFloat(s.nextLine());

                packageServices.addNewPackages(id,type, weightColet);
            } catch (NumberFormatException | InvalidDataException numberFormat) {
                System.out.println("Invalid type or quantity!");
            }
        }
    }
    void readDrivers() {
        System.out.println("How many drivers do you add?");
        int cntr = Integer.parseInt(s.nextLine());
        for (int i = 0; i < cntr; i++) {
            System.out.print("The drive no : ");
            System.out.println(i+1);
            try {
                System.out.print("Last name: ");
                String lastName = s.nextLine();
                System.out.print("First name: ");
                String firstName = s.nextLine();
                System.out.print("noPhone: ");
                String phoneNo = s.nextLine();
                System.out.print("Driver id: ");
                int idDriver = Integer.parseInt(s.nextLine());
                System.out.println("Car: ");
                System.out.print("Id car:");
                int idCar = Integer.parseInt(s.nextLine());
                System.out.print("No Registration: ");
                String noRegistration = s.nextLine();
                System.out.print("Brand: ");
                String brand = s.nextLine();
                System.out.print("Color: ");
                String color = s.nextLine();

                Car car = new Car(idCar, noRegistration, brand, color);

                driverServices.addNewDrivers(lastName, firstName, phoneNo, idDriver, car);
            } catch (InvalidDataException invalidData) {
                System.out.println(invalidData.getMessage());
            } catch (NumberFormatException numberFormat) {
                System.out.println("Invalid!");
            }
        }
    }
}

