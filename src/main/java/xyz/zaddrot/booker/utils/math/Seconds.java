package xyz.zaddrot.booker.utils.math;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by NightBook on 06.10.2016.
 */
public class Seconds {
    final private long January, February, March, April, May, June, July, August, September, October, November, December;
    public Seconds(){
        Month month = new Month();
        this.January = (60 * 60 * 24) * month.getJanuary();
        this.February = (60 * 60 * 24) * month.getFebruary();
        this.March = (60 * 60 * 24) * month.getMarch();
        this.April = (60 * 60 * 24) * month.getApril();
        this.May = (60 * 60 * 24) * month.getMay();
        this.June = (60 * 60 * 24) * month.getJune();
        this.July = (60 * 60 * 24) * month.getJuly();
        this.August = (60 * 60 * 24) * month.getAugust();
        this.September = (60 * 60 * 24) * month.getSeptember();
        this.October = (60 * 60 * 24) * month.getOctober();
        this.November = (60 * 60 * 24) * month.getNovember();
        this.December = (60 * 60 * 24) * month.getDecember();
    }

    public long getCurrentMonth(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        switch(Integer.parseInt(dateFormat.format(Calendar.getInstance().getTime()))){
            case 1:
                return getJanuary();
            case 2:
                return getFebruary();
            case 3:
                return getMarch();
            case 4:
                return getApril();
            case 5:
                return getMay();
            case 6:
                return getJune();
            case 7:
                return getJuly();
            case 8:
                return getAugust();
            case 9:
                return getSeptember();
            case 10:
                return getOctober();
            case 11:
                return getNovember();
            case 12:
                return getDecember();
        }
        return 0;
    }

    private long getJanuary() {
        return January;
    }
    private long getFebruary() {
        return February;
    }
    private long getMarch() {
        return March;
    }
    private long getApril() {
        return April;
    }
    private long getMay() {
        return May;
    }
    private long getJune() {
        return June;
    }
    private long getJuly() {
        return July;
    }
    private long getAugust() {
        return August;
    }
    private long getSeptember() {
        return September;
    }
    private long getOctober() {
        return October;
    }
    private long getNovember() {
        return November;
    }
    private long getDecember() {
        return December;
    }
}
