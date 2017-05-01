package com.lusichong.util;

import java.util.Calendar;

/**
 * Created by lusichong on 2017/4/24 21:03.
 */
public class Log {
    public static void i(String tag, String info) {
        System.out.println(currentTime() + " I: " + tag + ": " + info);
    }

    public static void i(Object tag, String info) {
        System.out.println(currentTime() + " I: " + tag.getClass().getSimpleName() + ": " + info);
    }

    public static void e(String tag, String error) {
        System.out.println(currentTime() + " E:" + tag + ": " + error);
    }

    private static String currentTime() {
        Calendar time = Calendar.getInstance();
        time.get(Calendar.HOUR_OF_DAY);//获取小时
        time.get(Calendar.MINUTE);//获取分钟
        time.get(Calendar.SECOND);//获取秒
        return "[" + time.get(Calendar.HOUR_OF_DAY) + ":" + time.get(Calendar.MINUTE) + ":" + time.get(Calendar.SECOND) + "]";
    }

}
