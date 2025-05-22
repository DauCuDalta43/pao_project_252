import java.time.LocalDateTime;

public class Service {
    private Hospital hospital;

    public Service(Hospital hospital) {
        this.hospital = hospital;
    }

    public Patient addPatient(String name, int age, String gender, String contactNumber) {
        Patient patient = new Patient(name, age, gender, contactNumber, hospital);
        return patient;
    }

    public MinorPatient addMinorPatient(String name, int age, String gender,
                                        String contactNumber, String parentName, String parentContact) {
        MinorPatient minor = new MinorPatient(name, age, gender, contactNumber, parentName, parentContact, hospital);
        return minor;
    }

    public Doctor addDoctor(String name, String specialization, String contactNumber) {
        Doctor doctor = new Doctor(name, specialization, contactNumber, hospital);
        return doctor;
    }

    public Appointment scheduleAppointment(int patientId, String specialization,
                                           LocalDateTime appointmentDateTime, int durationMinutes, String notes) {
        Patient patient = hospital.getPatients().stream()
                .filter(p -> p.getId()==patientId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Doctor availableDoctor = hospital.getDoctors().stream()
                .filter(d -> d.getSpecialization().equalsIgnoreCase(specialization))
                .filter(d -> d.isAvailable(appointmentDateTime, appointmentDateTime.plusMinutes(durationMinutes)))
                .findFirst()
                .orElse(null);
        if (availableDoctor == null) {
            throw new IllegalArgumentException("No available doctor with specialization: " + specialization);
        }


        Room availableRoom = hospital.getRooms().stream()
                .filter(r -> r.isAvailable(appointmentDateTime, appointmentDateTime.plusMinutes(durationMinutes)))
                .findFirst()
                .orElse(null);

        if (availableRoom == null) {
            throw new IllegalArgumentException("No available room at the specified time.");
        }

        return new Appointment(patient, availableDoctor, appointmentDateTime,
                durationMinutes, notes, availableRoom, hospital);
    }

    public boolean rescheduleAppointment(int appointmentId, LocalDateTime newStartTime) {

        Appointment appointment = hospital.getAppointments().stream()
                .filter(p -> p.getAppointmentId()==appointmentId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        LocalDateTime newEndTime = newStartTime.plusMinutes(appointment.getDurationMinutes());

        Doctor doctor = appointment.getDoctor();
        Room room = appointment.getRoom();

        boolean doctorAvailable = doctor.isAvailable(newStartTime, newEndTime);
        boolean roomAvailable = room.getScheduledAppointments().stream()
                .noneMatch(appt -> !appt.equals(appointment) &&
                        newStartTime.isBefore(appt.getEndTime()) &&
                        newEndTime.isAfter(appt.getAppointmentDateTime()));

        if (doctorAvailable && roomAvailable) {
            appointment.setAppointmentDateTime(newStartTime);
            return true;
        } else {
            return false;
        }
    }
    public void addAllergyToPatient(int patientId, String allergy) {
        Patient patient = hospital.getPatients().stream()
                .filter(p -> p.getId()==patientId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        patient.addAllergy(allergy);
    }

    public void showDoctorAppointments(int doctorId) {
        Doctor doctor = hospital.getDoctors().stream()
                .filter(d -> d.getId()==doctorId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        if (doctor.getScheduledAppointments().size()==0)
            System.out.println("No appointments");
        for (Appointment appt : doctor.getScheduledAppointments())
            System.out.println(appt);
    }

    public void showDoctors() {
        for (Doctor doc : hospital.getDoctors())
            System.out.println(doc);
    }

    public void showPatients() {
        for (Patient pat : hospital.getPatients())
            System.out.println(pat);
    }

    public void showRooms() {
        for (Room r : hospital.getRooms())
            System.out.println(r);
    }

    public void showAppointments() {
        for (Appointment app : hospital.getAppointments())
            System.out.println(app);
    }

    public int getDoctorId(String name){
        return hospital.getDoctorId(name);
    }

    public int getRoomId(String name){
        return hospital.getRoomId(name);
    }

    public int getPatientId(String name){
        return hospital.getPatientId(name);
    }

    public void removeAppointment(int appointmentId) {
        Appointment appointment = hospital.getAppointments().stream()
                .filter(p -> p.getAppointmentId()==appointmentId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        appointment.getDoctor().removeAppointment(appointment);
        appointment.getRoom().removeAppointment(appointment);
        hospital.getAppointments().remove(appointment);

    }

    public Surgery scheduleSurgery(int patientId, int doctorId, int roomId,
                                   LocalDateTime start, int durationMinutes, String notes) {

        Patient patient = hospital.getPatients().stream()
                .filter(p -> p.getId()==patientId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Doctor doctor = hospital.getDoctors().stream()
                .filter(d -> d.getId()==doctorId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Room room = hospital.getRooms().stream()
                .filter(r -> r.getRoomId()==roomId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        return new Surgery(patient, doctor, start, durationMinutes, notes, room, hospital);
    }



}