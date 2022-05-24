package model;

public class Client extends Person {
    private int userClient;
    private String address;

    public Client() {
    }

    public Client(int userClient,String lastName, String firstName, String phoneNo, String address) {
        super(lastName, firstName, phoneNo);
        this.userClient = userClient;
        this.address = address;
    }

    public Client(Client client) {
        super(client);
        this.userClient = client.userClient;
        this.address = client.address;
    }

    public Integer getUserClient() {
        return userClient;
    }

    public void setUserClient(Integer userClient) {
        this.userClient = userClient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

