package com.rockbb.thor.commons.lib.utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Time utility methods
 *
 * Created by Milton on 2015/8/21 at 18:53.
 */
public class TimeUtil
{
    public static final String FORMAT_YMD = "yyyy-MM-dd";
    public static final String FORMAT_YM = "yyyy-MM";
    public static final String FORMAT_YMD_HM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_SHORT_YMD = "yyyyMMdd";
    public static final String FORMAT_SHORT_YMDH = "yyyyMMddHH";
    public static final String FORMAT_SHORT_YMDHMS = "yyyyMMddHHmmss";
    public static final String FORMAT_SHORT_YMDHMSS = "yyyyMMddHHmmssSSS";
    public static final String FORMAT_SHORT_YMD_HM = "yyyyMMdd HHmm";

    public static final String REGEX_YMD = "(19|20)\\d\\d-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])";
    public static final String REGEX_YMD_HM = "(19|20)\\d\\d-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|[2][0-3]):([0-5][0-9])";
    public static final String REGEX_YMD_HMS = "(19|20)\\d\\d-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])";

    /**
     * 使用已知的日期格式, 还原日期字符串至日期对象, 出错则返回空
     *
     * @param strDate
     * @param format
     * @return
     */
    public static Date getDate(String strDate, String format) {
        Date date = null;
        if (strDate != null && strDate.length() > 0) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                date = sdf.parse(strDate);
            } catch (Exception ex) {
            }
        }
        return date;
    }

    /**
     * Format current time with specified format, using default locale
     *
     * @param format
     * @return
     */
    public static String getStr(String format) {
        return getStr(new Date(), format, null);
    }

    /**
     * Format a date with specified format, using default locale
     *
     * @param date
     * @param format
     * @return
     */
    public static String getStr(Date date, String format) {
        return getStr(date, format, null);
    }

    /**
     * Format a date with specified format and locale
     *
     * @param date
     * @param format
     * @param locale
     * @return
     */
    public static String getStr(Date date, String format, Locale locale) {
        if (date != null) {
            SimpleDateFormat dFormat;
            if (locale != null)
                dFormat = new SimpleDateFormat(format, locale);
            else
                dFormat = new SimpleDateFormat(format);

            return dFormat.format(date);
        } else {
            return "";
        }
    }

    public static int getDay() {
        return getDay(null);
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        return cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DATE);
    }

    /**
     * 获取日期分解得到的整数数组, 分别代表日, 月, 年
     *
     * @param date
     * @return int[]{day, month, year}
     */
    public static int[] getDayInts(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        return new int[]{cal.get(Calendar.DATE), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR)};
    }

    /**
     * 获取两个时间点之间的天数
     *
     * @param timeZoneOffset 标准时间与当地时间的时差, 毫秒数
     * @param from
     * @param to
     * @return
     */
    public static int getDaysDiff(int timeZoneOffset, Date from, Date to) {
        if (from == null || to == null) return 0;
        long daysFrom = (from.getTime() + timeZoneOffset) / (1000L * 3600 * 24);
        long daysTo = (to.getTime() + timeZoneOffset) / (1000L * 3600 * 24);
        return (int)(daysTo - daysFrom);
    }
    
    public static int getHoursDiff(int timeZoneOffset, Date from, Date to) {
        if (from == null || to == null) return 0;
        long hoursFrom = (from.getTime() + timeZoneOffset) / (1000L * 3600);
        long hoursTo = (to.getTime() + timeZoneOffset) / (1000L * 3600);
        return (int)(hoursTo - hoursFrom);
    }

    /**
     * 获取两个时间点之间的天数, 使用JVM默认时区
     *
     * @param from
     * @param to
     * @return
     */
    public static int getDaysDiff(Date from, Date to) {
        return getDaysDiff(TimeZone.getDefault().getRawOffset(), from, to);
    }
    
    public static int getHoursDiff(Date from, Date to) {
        return getHoursDiff(TimeZone.getDefault().getRawOffset(), from, to);
    }

    /**
     * 生成指定日期的准确开始和结束时间, 从0:00.000到23:59:999
     *
     * @param date 如果为空, 则自动获取当前时间
     * @return 包含两个时间值的数组
     */
    public static Date[] getDayRangeOfDate(Date date) {
        Date[] range = new Date[2];

        if (date == null)
            date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        setDayStart(cal);
        range[0] = cal.getTime();
        setDayEnd(cal);
        range[1] = cal.getTime();
        return range;
    }

    /**
     * 获取指定时间日期的开始时间
     *
     * @param date 如果为空, 则自动获取当前时间
     * @return 日期时间的左边界
     */
    public static Date getDayStart(Date date) {
        if (date == null)
            date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        setDayStart(cal);
        return cal.getTime();
    }

    /**
     * 获取指定时间日期的结束时间
     *
     * @param date 如果为空, 则自动获取当前时间
     * @return 日期时间的右边界
     */
    public static Date getDayEnd(Date date) {
        if (date == null)
            date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        setDayEnd(cal);
        return cal.getTime();
    }

    /**
     * 生成一系列的月份开始和结束日期
     *
     * @param offset 开始距现在的月份数
     * @param months 输入多少个月, 负数表示输出的是过去的月份
     * @return 按时间顺序排列的列表, 每个节点是一个时间数组, 包含两个时间值, 代表月份的起始和结束时间
     */
    public static List<Date[]> getDateRangeByMonth(int offset, int months) {
        List<Date[]> ranges = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, offset);
        if (months < 0) {
            cal.add(Calendar.MONTH, months + 1);
            months = 0 - months;
        }

        for (int i = 0; i < months; i++) {
            if (i > 0)
                cal.add(Calendar.MONTH, 1);
            Date[] range = new Date[2];
            cal.set(Calendar.DATE, 1);
            setDayStart(cal);
            range[0] = cal.getTime();

            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
            setDayEnd(cal);
            range[1] = cal.getTime();
            ranges.add(range);
        }

        return ranges;
    }

    /**
     * 得到一天的开始时间
     * @param cal 传入的Calendar实例
     */
    public static void setDayStart(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 得到一天结束时间
     * @param cal 传入的Calendar实例
     */
    public static void setDayEnd(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
    }

    /**
     * 得到某月的开始时间
     *
     * @param date
     * @return
     */
    public static Date getLeftBoundaryOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        setDayStart(cal);
        return cal.getTime();
    }

    /**
     * 得到某年某月的开始时间
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLeftBoundaryOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return getDayRangeOfDate(cal.getTime())[0];
    }

    /**
     * 得到某月的结束时间
     *
     * @param date
     * @return
     */
    public static Date getRightBoundaryOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        setDayEnd(cal);
        return cal.getTime();
    }

    /**
     * 得到某年某月的结束时间
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getRightBoundaryOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDayRangeOfDate(cal.getTime())[1];
    }

    /**
     * 获取指定日期偏移add后的天数
     * @param add 增加的天数, 如果为负则往前移
     * @param boundary -1:到日期左边界, 0:不变, 1:到日期右边界
     */
    public static Date getDay(Date date,int add, int boundary){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + add);
        switch (boundary) {
            case -1:
                setDayStart(c);
                break;
            case 1:
                setDayEnd(c);
                break;
        }
        return c.getTime();
    }

    public static void main(String[] args) {
        Date date = new Date();
        Date start = getLeftBoundaryOfMonth(date);
        Date end = getRightBoundaryOfMonth(date);
        System.out.println(start);
        System.out.println(end);
        
        System.out.println(TimeUtil.getDaysDiff(new Date(), TimeUtil.getDate("2016-05-19",TimeUtil.FORMAT_YMD)));
    }
}
