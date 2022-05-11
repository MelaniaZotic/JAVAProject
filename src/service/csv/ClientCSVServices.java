package service.csv;

import model.Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientCSVServices implements GenericCSV<Client> {
    public static final ClientCSVServices INSTANCE = new ClientCSVServices();
    private ClientCSVServices(){}

    public static ClientCSVServices getInstance(){return INSTANCE;}


    @Override
    public void write(Client client){
        try(FileWriter fileWriter = new FileWriter("files/clients.csv",true)){
            fileWriter.write(client.getlastName() + ", " + client.getfirstName() + "," + client.getphoneNo() + ", "
                    + client.getUserClient() + "," + client.getAddress() );
        }catch (IOException Ex){
            System.out.println("Error writing to file!");
        }
    }

    @Override
    public List<Client> read(){
        List<Client> clients = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("files/clients.csv"))){
            String l;
            while ((l=bufferedReader.readLine())!=null){
                String[] cl = l.split(",");
                Client client = new Client(cl[0], cl[1], cl[2], Integer.parseInt(cl[3]), cl[4] );

                clients.add(client);
            }
        }catch(IOException ex){
            System.out.println("Error reading to file");
        }
        return clients;
    }
}
