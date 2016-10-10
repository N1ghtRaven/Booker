package xyz.zaddrot.booker.utils.conf.fragment.dto;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xyz.zaddrot.booker.utils.encrypt.Encoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NightBook on 04.10.2016.
 */
public class Properties {
    final private Logger logger = LogManager.getLogger(getClass());
    final private Encoder encoder = new Encoder();
    private boolean topMounth, topDay;
    private String token = null;

    public Properties(Map<String, Object> data){
        try {
            topMounth = Boolean.parseBoolean(data.get("logTopMonth").toString());
            topDay = Boolean.parseBoolean(data.get("logTopDay").toString());
        }catch (NullPointerException e) { logger.warn(e); }
    }


    public boolean isTopMounth() {
        return topMounth;
    }

    public void setTopMounth(boolean topMounth) {
        this.topMounth = topMounth;
    }

    public boolean isTopDay() {
        return topDay;
    }

    public void setTopDay(boolean topDay) {
        this.topDay = topDay;
    }

    public Map<String, Object> getMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("logTopMonth", isTopMounth());
        map.put("logTopDay", isTopDay());

        return map;
    }
}
