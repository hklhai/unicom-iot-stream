package com.hxqh.iot.hxqhiot.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ocean lin on 2019/3/22.
 *
 * @author Ocean lin
 */
public class DateUtils {
    /**
     * 日期转换为时间戳
     *
     * @param timers
     * @return
     */
    public static long timeToStamp(String timers) {
        Date d = new Date();
        long timeStemp = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            // 日期转换为时间戳
            d = sf.parse(timers);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timeStemp = d.getTime();
        return timeStemp;
    }

}
