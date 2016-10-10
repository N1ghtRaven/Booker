package xyz.zaddrot.booker.utils.conf.util;

import java.io.File;

/**
 * Created by NightBook on 04.10.2016.
 */
public enum Path {
    PATH_TO_DIR("data/"),
    PATH_TO_CFG("data/cfg.yaml");

    final private String param;

    Path(String param){
        this.param = param;
    }

    @Override
    public String toString(){
        return this.param;
    }
    public File toFile(){
        return new File(this.param);
    }
}
