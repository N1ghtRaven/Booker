package xyz.zaddrot.booker.utils.conf.fragment.dto;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.zaddrot.booker.utils.encrypt.Encoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NightBook on 04.10.2016.
 */
public class Token {
    final private Logger logger = LogManager.getLogger(getClass());
    final private Encoder encoder = new Encoder();
    private String token = null;

    public Token(Map<String, Object> data){
        try {
            token = encoder.decryptData(data.get("token").toString());
        }catch (NullPointerException e) { logger.warn(e); }

        if(token == null) token = "0";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("token", encoder.encryptData(getToken()));

        return map;
    }
}
