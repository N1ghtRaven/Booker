package xyz.zaddrot.booker.utils.math;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NightBook on 06.10.2016.
 */
class Month {
    final private int January = 31, February, March = 31, April = 30, May = 31, June = 30, July = 31, August = 31, September = 30, October = 31, November = 30, December = 31;
    public Month(){
        if(LeapMath.isLeap()) this.February = 29;
        else this.February = 28;
    }

    int getJanuary() {
        return January;
    }
    int getFebruary() {
        return February;
    }
    int getMarch() {
        return March;
    }
    int getApril() {
        return April;
    }
    int getMay() {
        return May;
    }
    int getJune() {
        return June;
    }
    int getJuly() {
        return July;
    }
    int getAugust() {
        return August;
    }
    int getSeptember() {
        return September;
    }
    int getOctober() {
        return October;
    }
    int getNovember() {
        return November;
    }
    int getDecember() {
        return December;
    }

    public Map<Integer, Integer> getMonthMap(){
        Map<Integer, Integer> monthMap = new HashMap<>();
        monthMap.put(1, getJanuary());
        monthMap.put(2, getFebruary());
        monthMap.put(3, getMarch());
        monthMap.put(4, getApril());
        monthMap.put(5, getMay());
        monthMap.put(6, getJune());
        monthMap.put(7, getJuly());
        monthMap.put(8, getAugust());
        monthMap.put(9, getSeptember());
        monthMap.put(10, getOctober());
        monthMap.put(11, getNovember());
        monthMap.put(12, getDecember());

        return monthMap;
    }
}
