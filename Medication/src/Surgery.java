import java.time.*;
import java.util.*;

public class Surgery extends Appointment {
    public Surgery(Patient patient, Doctor doctor,
                   LocalDateTime appointmentDateTime, int durationMinutes,
                   String notes, Room room, Hospital hospital) {
        super(patient, doctor, appointmentDateTime, durationMinutes, notes, room, hospital);

        this.room = room;

        if (room != null) {
            Iterator<Appointment> iterator = room.getScheduledAppointments().iterator();
            while (iterator.hasNext()) {
                Appointment appt = iterator.next();
                if (overlaps(this, appt)) {
                    iterator.remove();
                    if (hospital != null) hospital.getAppointments().remove(appt);
                    room.removeAppointment(appt);
                    System.out.println("Removed conflicting room appointment: " + appt.getAppointmentId());
                }
            }
            room.scheduleAppointment(this);
        }

        if (doctor != null) {
            Iterator<Appointment> iterator = doctor.getScheduledAppointments().iterator();
            while (iterator.hasNext()) {
                Appointment appt = iterator.next();
                if (overlaps(this, appt)) {
                    iterator.remove();
                    if (hospital != null) hospital.getAppointments().remove(appt);
                    System.out.println("Removed conflicting doctor appointment: " + appt.getAppointmentId());
                    doctor.removeAppointment(appt);
                }
            }
            doctor.scheduleAppointment(this);
        }
    }

    private boolean overlaps(Appointment a1, Appointment a2) {
        return a1.getAppointmentDateTime().isBefore(a2.getEndTime()) &&
                a1.getEndTime().isAfter(a2.getAppointmentDateTime());
    }
}