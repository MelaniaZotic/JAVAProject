package repository.db;

import config.DbConnection;
import model.Package;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
