import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital("Colentina");
        Service service = new Service(hospital);
        Room r1 = new Room("101",hospital);
        Room r2 = new Room("102",hospital);
        Room r3 = new Room("103",hospital);


        service.addDoctor( "Dr. Smith", "Cardiology", "555-1111");
        service.addDoctor( "Dr. Lee", "Neurology", "555-2222");
        service.addDoctor( "Dr. Gupta", "Orthopedics", "555-3333");
        service.addDoctor( "Dr. Johnson", "Dermatology", "555-4444");
        service.addDoctor( "Dr. Adams", "Pediatrics", "555-5555");

        for (int i = 1; i <= 10; i++) {
            service.addPatient("Patient " + i, 20 + i, "Male", "555-000" + i);
        }
        service.addMinorPatient("Minor",10,"Female","555-6666","Parent","4444-66666");

        service.addAllergyToPatient(0, "Peanuts");
        service.addAllergyToPatient(1, "Penicillin");
        service.addAllergyToPatient(2, "Dust");

        LocalDateTime appointmentTime1 = LocalDateTime.of(2025, 5, 10, 10, 0);
        int patientid=service.getPatientId("Patient 1");
        Appointment appointment1 = service.scheduleAppointment( patientid, "Cardiology", appointmentTime1, 30, "Routine Check-up");
        LocalDateTime appointmentTime2 = LocalDateTime.of(2025, 5, 10, 11, 0);
        Appointment appointment2 = service.scheduleAppointment( 1, "Neurology", appointmentTime2, 45, "Headache Consultation");

        LocalDateTime appointmentTime3 = LocalDateTime.of(2025, 5, 10, 12, 0);
        Appointment appointment3 = service.scheduleAppointment(2, "Orthopedics", appointmentTime3, 60, "Joint Pain");
        service.showDoctorAppointments(1);
        service.showRooms();
        patientid=service.getPatientId("Patient 3");
        int doctorid=service.getDoctorId("Dr. Gupta");
        int roomid=service.getRoomId("102");
        LocalDateTime surgeryTime = LocalDateTime.of(2025, 5, 11, 9, 0);
        service.showDoctorAppointments(2);
        Surgery surgery = service.scheduleSurgery(patientid, doctorid, 0,  appointmentTime2, 120, "Knee Surgery");

        service.showDoctors();
        service.showPatients();
        service.showRooms();
        service.showAppointments();


        ///service.removeAppointment(0 );
        service.showDoctorAppointments(2);

        ///System.out.println("\nScheduled Surgery: " + surgery.getAppointmentDateTime() + " with Dr. " + surgery.getDoctor().getName());
    }
}
