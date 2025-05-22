import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Doctor {
    static int cntId=0;
    private int id;
    private String name;
    private String specialization;
    private String contactNumber;
    private List<Appointment> scheduledAppointments;

    public Doctor(String name, String specialization, String contactNumber, Hospital hospital) {
        this.id = cntId++;
        this.name = name;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.scheduledAppointments = new ArrayList<>();

        if (hospital != null) {
            hospital.addDoctor(this);
        }
    }

    // Default constructor
    public Doctor() {
        this.scheduledAppointments = new ArrayList<>();
    }

    // Check if doctor is available
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


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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
        return "Doctor{" +
                "ID='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", Specialization='" + specialization + '\'' +
                ", ContactNumber='" + contactNumber + '\'' +
                '}';
    }

}
