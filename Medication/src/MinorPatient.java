public class MinorPatient extends Patient {

    private String parentName;
    private String parentContact;


    public MinorPatient(String name, int age, String gender, String medicalHistory,
                        String parentName, String parentContact, Hospital hospital) {

        super(name, age, gender, medicalHistory, hospital);
        this.parentName = parentName;
        this.parentContact = parentContact;
    }


    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentContact() {
        return parentContact;
    }

    public void setParentContact(String parentContact) {
        this.parentContact = parentContact;
    }

    @Override
    public String toString() {
        return super.toString() + ", MinorPatient{" +
                "ParentName='" + parentName + '\'' +
                ", ParentContact='" + parentContact + '\'' +
                '}';
    }
}
