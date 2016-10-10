package xyz.zaddrot.booker.utils.math;

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
}
