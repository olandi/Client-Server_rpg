package com.multiPlayer.client.swing.model.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static String getTime(long millis) {

        return TimeUnit.MILLISECONDS.toSeconds(millis) + "";

    }
}
