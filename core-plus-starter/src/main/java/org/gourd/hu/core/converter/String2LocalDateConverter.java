package org.gourd.hu.core.converter;

import org.gourd.hu.core.utils.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 解决入参为 LocalDate类型,传入为string类型
 * @author gourd.hu
 */
public class String2LocalDateConverter extends BaseDateConverter<LocalDate> implements Converter<String, LocalDate> {

    protected static final Map<String, String> FORMAT = new LinkedHashMap(2);

    static {
        FORMAT.put(DateUtil.DATE_FORMAT_YYYY_MM_DD, "^\\d{4}-\\d{1,2}-\\d{1,2}$");
        FORMAT.put(DateUtil.DATE_FORMAT_YYYY_MM_DD_X, "^\\d{4}/\\d{1,2}/\\d{1,2}$");
        FORMAT.put(DateUtil.DATE_FORMAT_YYYYMMDD, "^\\d{4}\\d{1,2}\\d{1,2}$");
    }

    @Override
    protected Map<String, String> getFormat() {
        return FORMAT;
    }

    @Override
    public LocalDate convert(String source) {
        return super.convert(source, (key) -> LocalDate.parse(source, DateTimeFormatter.ofPattern(key)));
    }

}

