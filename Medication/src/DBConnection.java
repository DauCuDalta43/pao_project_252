
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    static final String DB_url = "jdbc:mysql://localhost/hospital";
    static final String USER = "DauCuDalta43";
    static final String PASS = "daubaroase43";
    private Connection dbConnection;

    public DBConnection(){

        try
        {
            dbConnection = DriverManager.getConnection(DB_url,USER,PASS);
            System.out.println("Connected succesfully");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection()
    {
        return dbConnection;
    }



}
