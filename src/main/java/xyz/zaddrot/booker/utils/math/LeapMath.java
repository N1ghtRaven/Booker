package xyz.zaddrot.booker.utils.math;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by NightBook on 06.10.2016.
 */
class LeapMath {
    static boolean isLeap(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY");
        int year = Integer.parseInt(dateFormat.format(Calendar.getInstance().getTime()));
        return !(year / 4 == 0 && year / 100 != 0) || (year / 400 == 0);
    }
}
