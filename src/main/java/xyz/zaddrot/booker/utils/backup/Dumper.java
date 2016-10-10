package xyz.zaddrot.booker.utils.backup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.zaddrot.booker.utils.conf.ConfigController;
import xyz.zaddrot.booker.utils.conf.fragment.dto.Properties;
import xyz.zaddrot.booker.utils.donation.logger.util.Path;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by NightBook on 07.10.2016.
 */
public class Dumper {
    final private Logger logger = LogManager.getLogger(getClass());
    public Dumper(){
        Path.BACKUP_BASE_PATH.toFile().mkdirs();
    }
    public void dump(){
        Properties cfg = new ConfigController().getAuth().getProperties();
        if(cfg.isTopMounth()) dumpMonthBase();
        if(cfg.isTopDay()) dumpDayBase();
    }

    private void dumpDayBase(){
        if(Path.BACKUP_DAY_BASE.toFile().exists()) {
            try {
                Files.copy(Path.BACKUP_DAY_BASE.toFile().toPath(), Path.DAY_BASE.toFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) { logger.error(e); }
        }
    }
    private void dumpMonthBase(){
        if(Path.BACKUP_BASE.toFile().exists()) {
            try {
                Files.copy(Path.BACKUP_BASE.toFile().toPath(), Path.BASE.toFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) { logger.error(e); }
        }
    }
}
