package org.gourd.hu.core.config;

import org.gourd.hu.core.converter.String2DateConverter;
import org.gourd.hu.core.converter.String2LocalDateConverter;
import org.gourd.hu.core.converter.String2LocalDateTimeConverter;
import org.gourd.hu.core.converter.String2LocalTimeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * 转换器配置
 * 自动转换请求体中string的日期类型
 *
 * @author gourd.hu
 *
 */
@Configuration
public class ConverterConfig {

    /**
     * 解决 @RequestParam(value = "date") Date date
     * date 类型参数 格式问题
     *
     * @return
     */
    @Bean
    public Converter<String, Date> dateConvert() {
        return new String2DateConverter();
    }

    /**
     * 解决 @RequestParam(value = "time") LocalDate time
     *
     * @return
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new String2LocalDateConverter();
    }

    /**
     * 解决 @RequestParam(value = "time") LocalTime time
     *
     * @return
     */
    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new String2LocalTimeConverter();
    }

    /**
     * 解决 @RequestParam(value = "time") LocalDateTime time
     *
     * @return
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new String2LocalDateTimeConverter();
    }
}
