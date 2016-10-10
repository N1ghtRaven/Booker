package xyz.zaddrot.booker.utils.backup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.zaddrot.booker.utils.conf.ConfigController;
import xyz.zaddrot.booker.utils.conf.fragment.dto.Properties;
import xyz.zaddrot.booker.utils.donation.logger.util.Path;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by NightBook on 07.10.2016.
 */
public class Backupper {
    final private Logger logger = LogManager.getLogger(getClass());
    public Backupper(){
        Path.BACKUP_BASE_PATH.toFile().mkdirs();
    }

    public void backup(){
        Properties cfg = new ConfigController().getAuth().getProperties();
        if(cfg.isTopMounth()) backupMonthBase();
        if(cfg.isTopDay()) backupDayBase();
    }

    private void backupDayBase(){
        if(Path.DAY_BASE.toFile().exists()) {
            try {
                Files.copy(Path.DAY_BASE.toFile().toPath(), Path.BACKUP_DAY_BASE.toFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) { logger.error(e); }
        }
    }
    private void backupMonthBase(){
        if(Path.BASE.toFile().exists()) {
            try {
                Files.copy(Path.BASE.toFile().toPath(), Path.BACKUP_BASE.toFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) { logger.error(e); }
        }
    }
}
