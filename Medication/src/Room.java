import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Room {
    static int cntId;
    private int roomId;
    private String name;
    private List<Appointment> scheduledAppointments;

    public Room(String name, Hospital hospital) {
        this.roomId = cntId++;
        this.name = name;
        this.scheduledAppointments = new ArrayList<>();

        if (hospital != null) {
            hospital.addRoom(this);
        }
    }

    public Room() {
        this.scheduledAppointments = new ArrayList<>();
    }
    public boolean isAvailable(LocalDateTime startTime, LocalDateTime endTime) {
        for (Appointment appointment : scheduledAppointments) {
            boolean overlap = startTime.isBefore(appointment.getEndTime()) &&
                    endTime.isAfter(appointment.getAppointmentDateTime());
            if (overlap) {
                return false;
            }
        }
        return true;
    }

    public boolean scheduleAppointment(Appointment newAppointment) {
        if (isAvailable(newAppointment.getAppointmentDateTime(), newAppointment.getEndTime())) {
            scheduledAppointments.add(newAppointment);
            scheduledAppointments.sort(Comparator.comparing(Appointment::getAppointmentDateTime));
            return true;
        }
        return false;
    }

    // Getters and Setters...

    public int getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Appointment> getScheduledAppointments() {
        return scheduledAppointments;
    }

    public void removeAppointment(Appointment appt)
    {
        scheduledAppointments.remove(appt);
    }

    @Override
    public String toString() {
        return "Room{" +
                "RoomID='" + roomId + '\'' +
                ", Name='" + name + '\'' +
                '}';
    }
}
