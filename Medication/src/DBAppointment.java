import java.sql.Connection;
import java.sql.*;

public class DBAppointment {

    private Connection connection;
    private Hospital hospital;
    private Service service;

    public DBAppointment(Connection connection, Hospital hospital)
    {
        this.hospital=hospital;
        this.connection=connection;
        this.service=service;
    }

    public void Init()
    {
        String query = "SELECT * FROM appointments";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            ResultSet result = sqlquery.executeQuery();
            while(result.next()){
                Appointment a = new Appointment(
                        hospital.getPatient(result.getInt("patient_id")),
                        hospital.getDoctor(result.getInt("doctor_id")),
                        result.getTimestamp("appointment_date").toLocalDateTime(),
                        result.getInt("duration"),
                        result.getString("notes"),
                        hospital.getRoom(result.getInt("room_id")),
                        hospital);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Appointment read(int id)
    {
        String query = "SELECT * FROM appointments WHERE id = ?";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(1,id);
            ResultSet result = sqlquery.executeQuery();
            if(result.next()){
                return new Appointment(
                        hospital.getPatient(result.getInt("patient_id")),
                        hospital.getDoctor(result.getInt("doctor_id")),
                        result.getTimestamp("appointment_date").toLocalDateTime(),
                        result.getInt("duration"),
                        result.getString("notes"),
                        hospital.getRoom(result.getInt("room_id")),
                        hospital,id);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void create(Appointment appointment)
    {
        String query = "INSERT INTO appointments (id, doctor_id, room_id, patient_id, appointment_date, duration, notes) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(1,appointment.getAppointmentId());
            sqlquery.setInt(2,appointment.getDoctor().getId());
            sqlquery.setInt(3,appointment.getRoom().getRoomId());
            sqlquery.setInt(4,appointment.getPatient().getId());
            sqlquery.setTimestamp(5,Timestamp.valueOf(appointment.getAppointmentDateTime()));
            sqlquery.setInt(6,appointment.getDurationMinutes());
            sqlquery.setString(7,appointment.getNotes());
            sqlquery.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void delete(int id)
    {
        String query = "DELETE FROM appointments WHERE id = ?";
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

    public void update(Appointment appointment)
    {
        String query = "UPDATE room SET doctor_id = ?, room_id = ?, patient_id = ?, appointment_date = ?, duration = ?, notes = ? WHERE id = ?";
        try {
            PreparedStatement sqlquery = connection.prepareStatement(query);
            sqlquery.setInt(1,appointment.getDoctor().getId());
            sqlquery.setInt(2,appointment.getRoom().getRoomId());
            sqlquery.setInt(3,appointment.getPatient().getId());
            sqlquery.setTimestamp(4,Timestamp.valueOf(appointment.getAppointmentDateTime()));
            sqlquery.setInt(5,appointment.getDurationMinutes());
            sqlquery.setString(6,appointment.getNotes());
            sqlquery.setInt(7,appointment.getAppointmentId());
            sqlquery.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }



}
