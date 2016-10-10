package xyz.zaddrot.booker.utils.conf;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.zaddrot.booker.utils.conf.fragment.Auth;
import xyz.zaddrot.booker.utils.conf.util.Path;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by NightBook on 04.10.2016.
 */
public class ConfigController {
    final private Logger logger = LogManager.getLogger(getClass());
    final private Auth auth = new Auth();

    public ConfigController(){
        if(!Path.PATH_TO_CFG.toFile().exists()){
            Path.PATH_TO_DIR.toFile().mkdirs();

            try { Files.copy(new File(getClass().getResource("/skeleton/cfg.yaml").getFile().substring(6)).toPath(), Path.PATH_TO_CFG.toFile().toPath()); } catch (IOException e) { logger.error(e); }
        }
    }

    public Auth getAuth(){
        return auth;
    }
}
