package model;

public class Package {
    private int id;
    private String type;
    private Float weightColet;

    public Package() {
    }

    public Package(Integer id,String type, Float weightColet) {
        this.id = id;
        this.type = type;
        this.weightColet = weightColet;
    }
    public Package(Package packages){
        this.id = packages.id;
        this.type = packages.type;
        this.weightColet = packages.weightColet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public Float getweightColet() {
        return weightColet;
    }

    public void setweightColet(Float weightColet) {
        this.weightColet = weightColet;
    }

    @Override
    public String toString() {
        return "Package{" +
                "type='" + type + '\'' +
                ", weightColet=" + weightColet +
                '}';
    }
}
