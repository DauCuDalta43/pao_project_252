import java.io.File;
import java.time.LocalDateTime;



public class Service {
    private Hospital hospital;
    private Logging logger;

    public Service(Hospital hospital, Logging logger) {
        this.hospital = hospital;
        this.logger=logger;
    }
    public void SaveLog()
    {
        logger.SaveLog();
    }
    public Patient addPatient(String name, int age, String gender, String contactNumber, DBPatient dbPatient) {
        Patient patient = new Patient(name, age, gender, contactNumber, hospital);
        dbPatient.create(patient);

        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);

        return patient;
    }

    public MinorPatient addMinorPatient(String name, int age, String gender,
                                        String contactNumber, String parentName, String parentContact, DBPatient dbPatient) {
        MinorPatient minor = new MinorPatient(name, age, gender, contactNumber, parentName, parentContact, hospital);
        dbPatient.create(minor);
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
        return minor;
    }

    public Doctor addDoctor(String name, String specialization, String contactNumber, DBDoctor dbDoctor) {
        Doctor doctor = new Doctor(name, specialization, contactNumber, hospital);
        dbDoctor.create(doctor);
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
        return doctor;
    }

    public Appointment scheduleAppointment(int patientId, String specialization,
                                           LocalDateTime appointmentDateTime, int durationMinutes, String notes, DBAppointment dbAppointment) {
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
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
        Appointment appt= new Appointment(patient, availableDoctor, appointmentDateTime,
                durationMinutes, notes, availableRoom, hospital);
        dbAppointment.create(appt);
        return appt;
    }

    public boolean rescheduleAppointment(int appointmentId, LocalDateTime newStartTime, DBAppointment dbAppointment) {

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
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
        if (doctorAvailable && roomAvailable) {
            appointment.setAppointmentDateTime(newStartTime);
            dbAppointment.update(appointment);
            return true;
        } else {
            return false;
        }
    }
    public void addAllergyToPatient(int patientId, String allergy, DBPatient dbPatient) {
        Patient patient = hospital.getPatients().stream()
                .filter(p -> p.getId()==patientId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        patient.addAllergy(allergy);
        dbPatient.update(patient);
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
    }

    public void showDoctorAppointments(int doctorId) {
        Doctor doctor = hospital.getDoctors().stream()
                .filter(d -> d.getId()==doctorId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        if (doctor.getScheduledAppointments().size()==0)
            System.out.println("No appointments");
        for (Appointment appt : doctor.getScheduledAppointments())
            System.out.println(appt);
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
    }

    public void showDoctors() {
        for (Doctor doc : hospital.getDoctors())
            System.out.println(doc);
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
    }

    public void showPatients() {
        for (Patient pat : hospital.getPatients())
            System.out.println(pat);
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
    }

    public void showRooms() {
        for (Room r : hospital.getRooms())
            System.out.println(r);
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
    }

    public void showAppointments() {
        for (Appointment app : hospital.getAppointments())
            System.out.println(app);
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
    }

    public int getDoctorId(String name){
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
        return hospital.getDoctorId(name);
    }

    public int getRoomId(String name){
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
        return hospital.getRoomId(name);
    }

    public int getPatientId(String name){
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
        return hospital.getPatientId(name);
    }

    public Doctor getDoctor(int id)
    {
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
        return hospital.getDoctor(id);
    }

    public Room getRoom(int id)
    {
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
        return hospital.getRoom(id);
    }

    public Patient getPatient(int id)
    {
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
        return hospital.getPatient(id);
    }




    public void removeAppointment(int appointmentId,DBAppointment dbAppointment) {
        Appointment appointment = hospital.getAppointments().stream()
                .filter(p -> p.getAppointmentId()==appointmentId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        appointment.getDoctor().removeAppointment(appointment);
        appointment.getRoom().removeAppointment(appointment);
        hospital.getAppointments().remove(appointment);
        dbAppointment.delete(appointmentId);
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);

    }

    public Surgery scheduleSurgery(int patientId, int doctorId, int roomId,
                                   LocalDateTime start, int durationMinutes, String notes, DBAppointment dbAppointment) {

        Patient patient = hospital.getPatients().stream()
                .filter(p -> p.getId()==patientId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Doctor doctor = hospital.getDoctors().stream()
                .filter(d -> d.getId()==doctorId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Room room = hospital.getRooms().stream()
                .filter(r -> r.getRoomId()==roomId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        String functionname = new Throwable().getStackTrace()[0].getMethodName();
        logger.LogAction(functionname);
        return new Surgery(patient, doctor, start, durationMinutes, notes, room, hospital,dbAppointment);
    }



}