package cn.imiaomi.admin.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

    /**
     * 转日期
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static Date toDate(String dateStr, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 转日期
     * @param date
     * @param dateFormat
     * @return
     */
    public static String toDate(Date date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        String str = format.format(date);
        return str;
    }

    /**
     * 日期加减
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DATE, days);

        date = cd.getTime();
        return date;
    }
}
