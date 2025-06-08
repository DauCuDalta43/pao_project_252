import java.sql.Connection;
import java.sql.*;

public class DBRoom {

    private Connection connection;
    private Hospital hospital;

    public DBRoom(Connection connection, Hospital hospital)
    {
        this.hospital=hospital;
        this.connection=connection;
    }

    public void init()
    {
        String query = "SELECT * FROM rooms";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            ResultSet result = sqlquery.executeQuery();
            while (result.next()){
                Room r = new Room(
                        result.getString("name"),
                        hospital);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Room read(int id)
    {
        String query = "SELECT * FROM rooms WHERE id = ?";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(1,id);
            ResultSet result = sqlquery.executeQuery();
            if(result.next()){
                return new Room(
                        result.getString("name"),
                        hospital,id);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void create(Room room)
    {
        String query = "INSERT INTO rooms (id, name) VALUES (?,?)";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(1,room.getRoomId());
            sqlquery.setString(2,room.getName());
            sqlquery.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void delete(int id)
    {
        String query = "DELETE FROM rooms WHERE id = ?";
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

    public void update(Room room)
    {
        String query = "UPDATE rooms SET name = ? WHERE id = ?";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(2,room.getRoomId());
            sqlquery.setString(1,room.getName());
            sqlquery.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }



}
