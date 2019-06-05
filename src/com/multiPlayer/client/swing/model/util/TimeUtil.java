package com.multiPlayer.client.swing.model.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static String getTime(long millis) {
/*
        Date date = new Date(timeInMillisec);
        DateFormat formatter = new SimpleDateFormat("ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
*/
return TimeUnit.MILLISECONDS.toSeconds(millis)+"";
       /* String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );*/
    }
}
