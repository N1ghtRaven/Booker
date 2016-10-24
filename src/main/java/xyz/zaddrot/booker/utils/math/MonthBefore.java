package xyz.zaddrot.booker.utils.math;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by NightBook on 15.10.2016.
 */
public class MonthBefore {
    static int getDayBefore(){
        return new Month().getMonthMap().get(getCurrentMonth()) - getCurrentDay();
    }

    private static int getCurrentDay(){
        return Integer.parseInt(new SimpleDateFormat("dd").format(Calendar.getInstance().getTime()));
    }

    private static int getCurrentMonth(){
        return Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()));
    }
}
