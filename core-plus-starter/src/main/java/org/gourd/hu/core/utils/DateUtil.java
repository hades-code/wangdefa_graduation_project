package org.gourd.hu.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 *
 * @author gourd.hu
 * @date 2018/10/26 10:20
 **/
public class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DEFAULT_X = "yyyy/MM/dd  HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_DD_X = "yyyy/MM/dd";

    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";

    public static final String DATE_FORMAT_DEFAULT_CHINESE_PATTERN = "yyyy年MM月dd日 HH:mm:ss";


    public static final String DEFAULT_YYYY_FORMAT = "yyyy";
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";






    private static String[] parsePatterns = {
            "yyyy年MM月dd日 HH时mm分ss秒","yyy年MM月dd日 HH时mm分","yyyy年MM月dd日",
            "yyyyMMdd HH:mm:ss","yyyyMMdd HH:mm","yyyyMMdd",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm","yyyy-MM-dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd"};

    /**
     * 将日期转化为标准格式
     * @param dateStr
     * @return
     */
    public static String transferToStandardStr(String dateStr) {
        try {
            Date date = DateUtils.parseDate(dateStr, parsePatterns);
            return date2Str(date,DATE_FORMAT_DEFAULT);
        } catch (ParseException e) {
            // 日期格式不对
            log.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 将日期转化为标准格式
     * @param dateStr
     * @return
     */
    public static Date transferToStandard(String dateStr) {
        try {
            return DateUtils.parseDate(dateStr, parsePatterns);
        } catch (ParseException e) {
            // 日期格式不对
            log.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 校验日期格式及是否过期
     * @param dateStr
     * @return
     */
    public static boolean compareToBias(String dateStr) {
        if(StringUtils.isBlank(dateStr)){
            return false;
        }
        try {
            Date date = DateUtils.parseDate(dateStr, parsePatterns);
            if(date.getTime() < System.currentTimeMillis()){
                // 已过期
                return false;
            }else {
                return true;
            }
        } catch (ParseException e) {
            // 日期格式不对
            log.error(e.getMessage(),e);
        }
        return false;
    }
    /**
     *  根据字符串格式日期获取日期
     *
     * @param source
     * @param format
     * @return
     */
    public static Date str2Date(String source, String format) {
        format = (format == null) ? DATE_FORMAT_DEFAULT : format;
        try {
            if (source == null) {
                return null;
            } else {
                SimpleDateFormat parseformat = new SimpleDateFormat(format);
                return parseformat.parse(source);
            }
        } catch (ParseException e){
            log.error("解析日期字符串异常", e);
        }
        return null;
    }

    /**
     * 根据日期获取字符串格式日期
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format){
        format = (format == null) ? DATE_FORMAT_DEFAULT : format;
        if(date == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获取当天的开始时间
     *
     * @return
     */
    public static Date getBeginTimeOfToday() {
        return getBeginTime(new Date());
    }

    /**
     * 获取当天的结束时间
     * @return
     */
    public static Date getEndTimeOfToday() {
        return getEndTime(new Date());
    }

    /**
     * 获取某天开始时间
     *
     * @return
     */
    public static Date getBeginTimeOfSome(Date date) {
        return getBeginTime(date);
    }

    /**
     * 获取某天结束时间
     * @return
     */
    public static Date getEndTimeOfSome(Date date) {
        return getEndTime(date);
    }



    /**
     * 获取某天的开始时间
     * @param date
     * @return
     */
    private static Date getBeginTime(Date date) {
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        return start.getTime();
    }


    /**
     * 获取某天的结束时间
     * @param date
     * @return
     */
    private static Date getEndTime(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获得当前月的第一天
     * @return
     */
    public static Date getFirstDayByMonth(){
        //获取当前日期
        Calendar cal_1=Calendar.getInstance();
        //设置为1号,当前日期既为本月第一天
        cal_1.set(Calendar.DAY_OF_MONTH,1);
        cal_1.set(Calendar.HOUR_OF_DAY,0);
        cal_1.set(Calendar.MINUTE, 0);
        cal_1.set(Calendar.SECOND, 0);
        cal_1.set(Calendar.MILLISECOND, 0);
        return cal_1.getTime();

    }

    /**
     * 获得当前月的最后一天
     * @return
     */
    public static Date getEndDayByMonth(){
        Calendar cal_1 = Calendar.getInstance();
        cal_1.add(Calendar.MONTH, 1);
        cal_1.set(Calendar.DAY_OF_MONTH,0);//设置为0号
        cal_1.set(Calendar.HOUR_OF_DAY,23);
        cal_1.set(Calendar.MINUTE, 59);
        cal_1.set(Calendar.SECOND, 59);
        cal_1.set(Calendar.MILLISECOND, 59);
        return cal_1.getTime();
    }

    /**
     * 是否是同一日期
     * @param d1
     * @param d2
     * @return
     */
    public static boolean sameDate(Date d1, Date d2) {
        if(null == d1 || null == d2){
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        return  cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
    }


    /**
     *  某个日期加多少天
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE,days);
        return cal.getTime();
    }
    /**
     *  某个日期加多少小时
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, int hours) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.HOUR,hours);
        return cal.getTime();
    }


    /**
     *  某个日期假多少分钟
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(Date date, int amount) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MINUTE,amount);
        return cal.getTime();
    }

    /**
     *  某个日期加多少秒
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSeconds(Date date, int seconds) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.SECOND,seconds);
        return cal.getTime();
    }

    /**
     * 获得本月第一天0点时间
     * @return
     */
    public static Date getTimesMonthMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获得本月最后一天24点时间
     * @return
     */
    public static Date getTimesMonthNight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        return cal.getTime();
    }

    /**
     * 获得本周一与当前日期相差的天数
     * @return
     */
    private static  int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * 获得昨天的日期
     * @return
     */
    public static  String getYesterday() {
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date monday = currentDate.getTime();
        String preMonday = dateFormat.format(monday);
        return preMonday;
    }

    /**
     * 获得当前周- 周一的日期
     * @return
     */
    public static  String getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date monday = currentDate.getTime();
        String preMonday = dateFormat.format(monday);
        return preMonday;
    }


    /**
     * 获得当前周- 周日的日期
     * @return
     */
    public static String getPreviousSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus +6);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date monday = currentDate.getTime();
        String preMonday = dateFormat.format(monday);
        return preMonday;
    }

    /**
     * 获得日期加几天
     * @return
     */
    public static Date addDaysForDate(Date date, int days){
        Calendar cal_1=Calendar.getInstance();
        cal_1.setTime(date);
        cal_1.add(Calendar.DATE, days);
        return cal_1.getTime();

    }

    /**
     * 获取两个日期相差年数
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * 时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long
     * @author gourd.hu
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance();
        // 日期减 如果不够减会将月变动
        canlendar.add(Calendar.DATE, daysInt);
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /**
     * 把时间根据时、分、秒转换为时间段
     * @param StrDate
     */
    public static String getTimes(String StrDate) {
        String resultTimes = "";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now;

        try {
            now = new Date();
            Date date = df.parse(StrDate);
            long times = now.getTime() - date.getTime();
            long day = times / (24 * 60 * 60 * 1000);
            long hour = (times / (60 * 60 * 1000) - day * 24);
            long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            StringBuffer sb = new StringBuffer();
            if (hour > 0) {
                sb.append(hour + "小时前");
            } else if (min > 0) {
                sb.append(min + "分钟前");
            } else {
                sb.append(sec + "秒前");
            }

            resultTimes = sb.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultTimes;
    }

    /**
     * 获取当前时间年份
     * @return
     */
    public static String getCurrentYear() {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.YEAR));
    }
    /**
     * 获取当前时间月份
     * @return
     */
    public static String getCurrentMonth() {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.MONTH));
    }


    /**
     * 生日转为年龄，计算法定年龄
     *
     * @param birthDay 生日
     * @return 年龄
     */
    public static int ageOfNow(Date birthDay) {
        return age(birthDay, new Date());
    }

    /**
     * 是否闰年
     *
     * @param year 年
     * @return 是否闰年
     */
    public static boolean isLeapYear(int year) {
        return new GregorianCalendar().isLeapYear(year);
    }



    /**
     * 计算相对于dateToCompare的年龄，长用于计算指定生日在某年的年龄
     *
     * @param birthDay      生日
     * @param dateToCompare 需要对比的日期
     * @return 年龄
     */
    public static int age(Date birthDay, Date dateToCompare) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToCompare);

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("日期不合法");
        }

        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        final boolean isLastDayOfMonth = dayOfMonth == cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int age = year - cal.get(Calendar.YEAR);

        final int monthBirth = cal.get(Calendar.MONTH);
        if (month == monthBirth) {

            final int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            final boolean isLastDayOfMonthBirth = dayOfMonthBirth == cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            if ((false == isLastDayOfMonth || false == isLastDayOfMonthBirth) && dayOfMonth < dayOfMonthBirth) {
                // 如果生日在当月，但是未达到生日当天的日期，年龄减一
                age--;
            }
        } else if (month < monthBirth) {
            // 如果当前月份未达到生日的月份，年龄计算减一
            age--;
        }

        return age;
    }

}
