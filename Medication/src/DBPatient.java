import java.sql.Connection;
import java.sql.*;

public class DBPatient {

    private Connection connection;
    private Hospital hospital;

    public DBPatient(Connection connection, Hospital hospital)
    {
        this.hospital=hospital;
        this.connection=connection;
    }

    public Patient read(int id)
    {
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(1,id);
            ResultSet result = sqlquery.executeQuery();
            if(result.next()){
                return new Patient(
                        result.getString("name"),
                        result.getInt("age"),
                        result.getString("gender"),
                        result.getString("contactNumber"),
                        hospital,id);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public void init()
    {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            ResultSet result = sqlquery.executeQuery();
            while(result.next()){
                 Patient p =new Patient(
                        result.getString("name"),
                        result.getInt("age"),
                        result.getString("gender"),
                        result.getString("contactNumber"),
                        hospital);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void create(Patient patient)
    {
        String query = "INSERT INTO patients (id, name, age, gender, contactNumber) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(1,patient.getId());
            sqlquery.setString(2,patient.getName());
            sqlquery.setInt(3,patient.getAge());
            sqlquery.setString(4,patient.getGender());
            sqlquery.setString(5,patient.getContactNumber());
            sqlquery.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void delete(int id)
    {
        String query = "DELETE FROM patients WHERE id = ?";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(1,id);
            sqlquery.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void update(Patient patient)
    {
        String query = "UPDATE patients SET name = ?, age = ?, gender = ?, contactNumber = ? WHERE id = ?";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(5,patient.getId());
            sqlquery.setString(1,patient.getName());
            sqlquery.setInt(2,patient.getAge());
            sqlquery.setString(3,patient.getGender());
            sqlquery.setString(4,patient.getContactNumber());
            sqlquery.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }



}
