package xyz.zaddrot.booker.utils.donation.logger.util;

import java.io.File;

/**
 * Created by NightBook on 05.10.2016.
 */
public enum Path {
    PATH_TO_BASE("data/base/"),
    BASE("data/base/base.json"),
    DAY_BASE("data/base/day_base.json"),

    BACKUP_BASE_PATH("data/backup/"),
    BACKUP_BASE("data/backup/base.bkp"),
    BACKUP_DAY_BASE("data/backup/day_base.bkp"),

    PATH_TO_OUT("out/"),
    PATH_TO_DAY("out/day.txt"),
    PATH_TO_MONTH_TOP_1("out/top1.txt"),
    PATH_TO_MONTH_TOP_2("out/top2.txt"),
    PATH_TO_MONTH_TOP_3("out/top3.txt"),

    VERSION_PATH("data/version/"),
    VERSION_DAY("data/version/day.ver"),
    VERSION_MONTH("data/version/month.ver");


    final private String request;

    Path(String request){
        this.request = request;
    }

    @Override
    public String toString(){
        return request;
    }
    public File toFile(){
        return new File(request);
    }
}
