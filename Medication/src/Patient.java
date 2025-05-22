import java.util.HashSet;
import java.util.Set;

public class Patient {
    static int cntId=0;
    private int id;
    private String name;
    private int age;
    private String gender;
    private String contactNumber;
    private Set<String> allergies;

    public Patient(String name, int age, String gender, String contactNumber, Hospital hospital) {
        this.id = cntId++;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.allergies = new HashSet<>();

        if (hospital != null) {
            hospital.addPatient(this);
        }
    }


    public int getId() { return id; }

    public String getName() { return name; }

    public int getAge() { return age; }

    public String getGender() { return gender; }

    public String getContactNumber() { return contactNumber; }

    @Override
    public String toString() {
        return "Patient{" +
                "ID='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", Age=" + age +
                ", Gender='" + gender + '\'' +
                ", ContactNumber='" + contactNumber + '\'' +
                '}';
    }

    public void addAllergy(String allergy) {
        allergies.add(allergy);
    }
}
