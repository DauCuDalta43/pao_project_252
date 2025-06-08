import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {



    public static void main(String[] args) {




        Hospital hospital = new Hospital("Colentina");
        Logging logger = new Logging();
        Service service = new Service(hospital,logger);
        DBConnection dbcon = new DBConnection();
        DBDoctor dbDoctor= new DBDoctor(dbcon.getConnection(),hospital);
        DBRoom dbRoom = new DBRoom(dbcon.getConnection(), hospital);
        DBPatient dbPatient = new DBPatient(dbcon.getConnection(), hospital);
        DBAppointment dbAppointment = new DBAppointment(dbcon.getConnection(), hospital);
        dbRoom.init();
        dbDoctor.init();
        dbPatient.init();
        dbAppointment.Init();

        LocalDateTime appointmentTime2 = LocalDateTime.of(2025, 5, 10, 11, 0);

        ///service.scheduleAppointment(2,"Optometrist",appointmentTime2,40,"",dbAppointment);
        service.showDoctorAppointments(1);

        service.scheduleSurgery(3,2,1,appointmentTime2,50,"",dbAppointment);


        service.SaveLog();





    }
}
