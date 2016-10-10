package xyz.zaddrot.booker.utils.donation.logger.fragment.version;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.zaddrot.booker.utils.donation.logger.util.Path;
import xyz.zaddrot.booker.utils.math.Seconds;

import java.io.*;
import java.util.Calendar;

/**
 * Created by NightBook on 06.10.2016.
 */
public class Version {
    private final Logger logger = LogManager.getLogger(getClass());
    public Version(){
        Path.VERSION_PATH.toFile().mkdirs();
        long time = Calendar.getInstance().getTimeInMillis();

        if((time - getDayTimestamp() >= 86400 * 1000) || getDayTimestamp() == 0) {
            writeFile(Path.VERSION_DAY.toFile(), time);
            writeFile(Path.DAY_BASE.toFile(), "{}");
        }

        if((time - getMonthTimestamp() >= new Seconds().getCurrentMonth() * 1000) || getMonthTimestamp() == 0){
            writeFile(Path.VERSION_MONTH.toFile(), time);
            writeFile(Path.BASE.toFile(), "{}");
        }
    }

    private long getDayTimestamp(){
        String timestamp = readFile(Path.VERSION_DAY.toFile());
        if(timestamp != null) return Long.parseLong(timestamp.trim());
        return 0;
    }
    private long getMonthTimestamp(){
        String timestamp = readFile(Path.VERSION_MONTH.toFile());
        if(timestamp != null) return Long.parseLong(timestamp.trim());
        return 0;
    }

    private String readFile(File versionFile){
        if (versionFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(versionFile))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                br.close();
                return sb.toString();
            } catch (IOException e) { logger.error(e); }
        }
        return null;
    }
    private void writeFile(File versionFile, Object data){
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(versionFile), "UTF-8"));

            out.write(String.valueOf(data));
            out.close();
        }catch (IOException e) { logger.error(e); }
    }
}
