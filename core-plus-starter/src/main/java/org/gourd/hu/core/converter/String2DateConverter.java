package org.gourd.hu.core.converter;

import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.exception.ArgumentException;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.core.utils.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;



/**
 * 解决入参为 Date类型,传入为string类型
 * @author gourd.hu
 */
@Slf4j
public class String2DateConverter extends BaseDateConverter<Date> implements Converter<String, Date> {

    protected static final Map<String, String> FORMAT = new LinkedHashMap(11);

    static {
        FORMAT.put(DateUtil.DEFAULT_YYYY_FORMAT, "^\\d{4}$");
        FORMAT.put(DateUtil.DATE_FORMAT_YYYY_MM, "^\\d{4}-\\d{1,2}$");
        FORMAT.put(DateUtil.DATE_FORMAT_YYYY_MM_DD, "^\\d{4}-\\d{1,2}-\\d{1,2}$");
        FORMAT.put(DateUtil.DATE_FORMAT_YYYY_MM_DD_HH, "^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}$");
        FORMAT.put(DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM, "^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$");
        FORMAT.put(DateUtil.DATE_FORMAT_DEFAULT, "^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$");
    }

    /**
     * 格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    protected static Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            //严格模式
            dateFormat.setLenient(false);
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            log.info("转换日期失败, date={}, format={}", dateStr, format, e);
            throw new ArgumentException(ResponseEnum.BAD_REQUEST);
        }
        return date;
    }

    @Override
    protected Map<String, String> getFormat() {
        return FORMAT;
    }

    @Override
    public Date convert(String source) {
        return super.convert(source, (key) -> parseDate(source, key));
    }

}
