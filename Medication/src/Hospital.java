import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hospital {
    private String name;
    private List<Patient> patients;
    private List<Doctor> doctors;
    private List<Appointment> appointments;
    private Map<String,Integer> patientid;
    private Map<String,Integer> roomid;
    private Map<String,Integer> doctorid;
    private Map<Integer,Doctor> doctorMap;
    private Map<Integer,Room> roomMap;
    private Map<Integer,Patient> patientMap;
    private ArrayList<Room> rooms;

    public Hospital(String name) {
        this.name = name;
        this.patients = new ArrayList<>();
        this.doctors = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.patientid = new HashMap<String,Integer>();
        this.doctorid = new HashMap<String,Integer>();
        this.roomid = new HashMap<String,Integer>();
        this.doctorMap=new HashMap<Integer,Doctor>();
        this.patientMap=new HashMap<Integer,Patient>();
        this.roomMap=new HashMap<Integer,Room>();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
        patientMap.put(patient.getId(),patient);
        patientid.put(patient.getName(), patient.getId());
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        doctorMap.put(doctor.getId(),doctor);
        doctorid.put(doctor.getName(), doctor.getId());
    }

    public void addRoom(Room room) {
        rooms.add(room);
        roomMap.put(room.getRoomId(),room);
        roomid.put(room.getName(), room.getRoomId());
    }

    public int getDoctorId(String name){
        return doctorid.get(name);
    }

    public int getRoomId(String name){
        return roomid.get(name);
    }

    public int getPatientId(String name){
        return patientid.get(name);
    }

    public Doctor getDoctor(int id){
        return doctorMap.get(id);
    }

    public Room getRoom(int id){
        return roomMap.get(id);
    }

    public Patient getPatient(int id){
        return patientMap.get(id);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public String getName() {
        return name;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void printSummary() {
        System.out.println("Hospital: " + name);
        System.out.println("Patients: " + patients.size());
        System.out.println("Doctors: " + doctors.size());
        System.out.println("Appointments: " + appointments.size());
        System.out.println("Rooms: " + rooms.size());
    }
}
