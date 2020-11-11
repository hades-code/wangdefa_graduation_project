package org.gourd.hu.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 *
 * @author gourd.hu
 * @date 2018/10/26 10:20
 **/
public class LocalDateUtil {

    private static final Logger log = LoggerFactory.getLogger(LocalDateUtil.class);

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
     * String转LocalDate
     * @param source
     * @return
     */
    public static LocalDate str2LocalDate(String source){
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(DATE_FORMAT_DEFAULT));
    }

    /**
     * String转LocalDate
     * @param source
     * @param format
     * @return
     */
    public static LocalDate str2LocalDate(String source,String format){
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(format));
    }

    /**
     * String转LocalTime
     * @param source
     * @return
     */
    public static LocalTime str2LocalTime(String source){
        return LocalTime.parse(source, DateTimeFormatter.ofPattern(DATE_FORMAT_DEFAULT));
    }

    /**
     * String转LocalTime
     * @param source
     * @param format
     * @return
     */
    public static LocalTime str2LocalTime(String source,String format){
        return LocalTime.parse(source, DateTimeFormatter.ofPattern(format));
    }

    /**
     * String转LocalDateTime
     * @param source
     * @return
     */
    public static LocalDateTime str2LocalDateTime(String source){
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DATE_FORMAT_DEFAULT));
    }

    /**
     * String转LocalDateTime
     * @param source
     * @param format
     * @return
     */
    public static LocalDateTime str2LocalDateTime(String source,String format){
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(format));
    }


    /**
     * LocalDate转String
     * @param source
     * @return
     */
    public static String localDate2Str(LocalDate source){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DATE_FORMAT_DEFAULT);
        return source.format(fmt);
    }

    /**
     * LocalDate转String
     * @param source
     * @param format
     * @return
     */
    public static String localDate2Str(LocalDate source,String format){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
        return source.format(fmt);
    }

    /**
     * LocalTime转String
     * @param source
     * @return
     */
    public static String localTime2Str(LocalTime source){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DATE_FORMAT_DEFAULT);
        return source.format(fmt);
    }

    /**
     * LocalTime转String
     * @param source
     * @param format
     * @return
     */
    public static String localTime2Str(LocalTime source,String format){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
        return source.format(fmt);
    }

    /**
     * LocalDateTime转String
     * @param source
     * @return
     */
    public static String localDateTime2Str(LocalDateTime source){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DATE_FORMAT_DEFAULT);
        return source.format(fmt);
    }

    /**
     * LocalDateTime转String
     * @param source
     * @param format
     * @return
     */
    public static String localDateTime2Str(LocalDateTime source,String format){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
        return source.format(fmt);
    }


}
