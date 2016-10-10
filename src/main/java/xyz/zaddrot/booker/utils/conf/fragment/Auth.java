package xyz.zaddrot.booker.utils.conf.fragment;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import xyz.zaddrot.booker.utils.conf.fragment.dto.Properties;
import xyz.zaddrot.booker.utils.conf.fragment.dto.Token;
import xyz.zaddrot.booker.utils.conf.util.Path;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NightBook on 04.10.2016.
 */
public class Auth {
    final private Logger logger = LogManager.getLogger(getClass());
    final private Yaml yaml = new Yaml();
    private Map<String, Map<String, Object>> cfg;

    private Token token;
    private Properties properties;

    public Auth(){
        if(!Path.PATH_TO_CFG.toFile().exists()){
            Path.PATH_TO_DIR.toFile().mkdirs();
            try {
                String conf = getClass().getResource("/skeleton/cfg.yaml").getFile();
                if(conf.startsWith("file:")) conf = conf.substring(6);
                Files.copy(new File(conf).toPath(), Path.PATH_TO_CFG.toFile().toPath());
            } catch (IOException e) { logger.error(e); }
        }

        try{
            cfg = (Map<String, Map<String, Object>>) yaml.load(new FileInputStream(Path.PATH_TO_CFG.toFile()));
        }catch (FileNotFoundException e) { logger.warn(e); }

        token = new Token(cfg.get("auth"));
        properties = new Properties(cfg.get("properties"));
    }

    public Token getToken() {
        return token;
    }

    public Properties getProperties() {
        return properties;
    }

    public void dump(){
        Map<String, Object> result = new HashMap<>();
        result.put("auth", getToken().getMap());
        result.put("properties", getProperties().getMap());

        try{ yaml.dump(result, new FileWriter((Path.PATH_TO_CFG.toFile()))); }catch (IOException e) { logger.error(e); }
    }
}
