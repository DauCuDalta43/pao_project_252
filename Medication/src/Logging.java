import com.opencsv.CSVWriter;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logging {

    static final File logfile = new File("C:/Users/razva/IdeaProjects/Medication/log.csv");
    CSVWriter writer;
    public Logging(){
        try{
            FileWriter logoutput = new FileWriter(logfile,true);
            writer = new CSVWriter(logoutput);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void LogAction(String s)
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String[] timestamp = {s, now.format(formatter)};
        writer.writeNext(timestamp);
    }

    public void SaveLog()
    {
        try {
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
