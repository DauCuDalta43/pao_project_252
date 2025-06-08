import java.sql.Connection;
import java.sql.*;

public class DBDoctor {

    private Connection connection;
    private Hospital hospital;

    public DBDoctor(Connection connection, Hospital hospital)
    {
        this.hospital=hospital;
        this.connection=connection;
    }

    public Doctor read(int id)
    {
        String query = "SELECT * FROM doctors WHERE id = ?";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(1,id);
            ResultSet result = sqlquery.executeQuery();
            if(result.next()){
                return new Doctor(
                        result.getString("name"),
                        result.getString("specialization"),
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
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            ResultSet result = sqlquery.executeQuery();
            while(result.next()){
                Doctor d=new Doctor(
                        result.getString("name"),
                        result.getString("specialization"),
                        result.getString("contactNumber"),
                        hospital);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void create(Doctor doctor)
    {
        String query = "INSERT INTO doctors (id, name, specialization, contactNumber) VALUES (?,?,?,?)";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(1,doctor.getId());
            sqlquery.setString(2,doctor.getName());
            sqlquery.setString(3,doctor.getSpecialization());
            sqlquery.setString(4,doctor.getContactNumber());
            sqlquery.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void delete(int id)
    {
        String query = "DELETE FROM doctors WHERE id = ?";
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

    public void update(Doctor doctor)
    {
        String query = "UPDATE doctors SET name = ?, specialization = ?, contactNumber = ? WHERE id = ?";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(4,doctor.getId());
            sqlquery.setString(1,doctor.getName());
            sqlquery.setString(2,doctor.getSpecialization());
            sqlquery.setString(3,doctor.getContactNumber());
            sqlquery.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }



}
