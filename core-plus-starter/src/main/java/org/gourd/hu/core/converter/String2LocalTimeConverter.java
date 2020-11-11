package org.gourd.hu.core.converter;

import org.gourd.hu.core.utils.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 解决入参为 LocalTime类型,传入为string类型
 * @author gourd.hu
 */
public class String2LocalTimeConverter extends BaseDateConverter<LocalTime> implements Converter<String, LocalTime> {

    protected static final Map<String, String> FORMAT = new LinkedHashMap(1);

    static {
        FORMAT.put(DateUtil.DATE_FORMAT_HH_MM_SS, "^\\d{1,2}:\\d{1,2}:\\d{1,2}$");
    }

    @Override
    protected Map<String, String> getFormat() {
        return FORMAT;
    }

    @Override
    public LocalTime convert(String source) {
        return super.convert(source, (key) -> LocalTime.parse(source, DateTimeFormatter.ofPattern(key)));
    }
}
