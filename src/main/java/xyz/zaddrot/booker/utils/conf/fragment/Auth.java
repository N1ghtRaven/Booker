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
        initCfg();

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

    private void initCfg(){
        if(!Path.PATH_TO_CFG.toFile().exists()){
            Path.PATH_TO_DIR.toFile().mkdirs();

            Map<String, Object> init = new HashMap<>();
            init.put("token", 0);

            token = new Token(init);
            init.clear();

            init.put("logTopMonth", false);
            init.put("logTopDay", false);

            properties = new Properties(init);
            init.clear();

            dump();
        }
    }

    public void dump(){
        Map<String, Object> result = new HashMap<>();
        result.put("auth", getToken().getMap());
        result.put("properties", getProperties().getMap());

        try{ yaml.dump(result, new FileWriter((Path.PATH_TO_CFG.toFile()))); }catch (IOException e) { logger.error(e); }
    }
}
