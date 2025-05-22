import java.time.LocalDateTime;
import java.time.Duration;

public class Appointment {
    static int cntId=0;
    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime appointmentDateTime;
    private int durationMinutes; // New field
    private String notes;
    Room room;

    public Appointment(Patient patient, Doctor doctor,
                       LocalDateTime appointmentDateTime, int durationMinutes,
                       String notes, Room room, Hospital hospital) {

        this.appointmentDateTime = appointmentDateTime;
        this.durationMinutes = durationMinutes;
        this.notes = notes;
        this.room = room;
        this.patient = patient;
        this.doctor = doctor;

        if (hospital != null) {
            hospital.addAppointment(this);
        }

        doctor.scheduleAppointment(this);

        room.scheduleAppointment(this);
        this.appointmentId = cntId++;
    }

    public LocalDateTime getEndTime() {
        return appointmentDateTime.plusMinutes(durationMinutes);
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "AppointmentID='" + appointmentId + '\'' +
                ", Patient=" + patient.getName() +
                ", Doctor=" + doctor.getName() +
                ", Start=" + appointmentDateTime +
                ", Duration=" + durationMinutes + " mins" +
                ", Room=" + (room != null ? room.getName() : "None") +
                ", Notes='" + notes + '\'' +
                '}';
    }
}
