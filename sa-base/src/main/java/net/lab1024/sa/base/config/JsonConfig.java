package net.lab1024.sa.base.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import net.lab1024.sa.base.common.json.serializer.LongJsonSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * json 序列化配置
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2017-11-28 15:21:10
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Configuration
public class JsonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            // 创建自定义模块
            SimpleModule module = new SimpleModule();
            module.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());

            builder.deserializers(new LocalDateDeserializer(DatePattern.NORM_DATE_FORMAT.getDateTimeFormatter()));
            builder.serializers(new LocalDateSerializer(DatePattern.NORM_DATE_FORMAT.getDateTimeFormatter()));
            builder.serializers(new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMAT.getDateTimeFormatter()));
            builder.serializerByType(Long.class, LongJsonSerializer.INSTANCE);
            builder.modules(module);
        };
    }

    /**
     * 自定义LocalDateTime反序列化器，支持多种日期格式
     */
    public static class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String dateString = p.getValueAsString();
            if (StringUtils.isBlank(dateString)) {
                return null;
            }

            // 处理ISO 8601格式带时区 (2025-05-29T16:17:36.104Z)
            if (dateString.endsWith("Z")) {
                try {
                    Instant instant = Instant.parse(dateString);
                    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                } catch (DateTimeParseException e) {
                    throw new RuntimeException("无法解析ISO 8601日期格式: " + dateString, e);
                }
            }

            // 尝试解析ISO 8601格式不带时区 (2025-05-29T16:17:36)
            try {
                return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException e1) {
                // 尝试解析标准格式 (yyyy-MM-dd HH:mm:ss)
                try {
                    return LocalDateTime.parse(dateString, DatePattern.NORM_DATETIME_FORMAT.getDateTimeFormatter());
                } catch (DateTimeParseException e2) {
                    throw new RuntimeException("无法解析日期格式: " + dateString + "，支持的格式: yyyy-MM-dd HH:mm:ss 或 ISO 8601", e2);
                }
            }
        }
    }

    /**
     * string 转为 LocalDateTime 配置类
     *
     * @author 卓大
     */
    @Configuration
    public static class StringToLocalDateTime implements Converter<String, LocalDateTime> {

        @Override
        public LocalDateTime convert(String str) {
            if (StringUtils.isBlank(str)) {
                return null;
            }
            LocalDateTime localDateTime;
            try {
                // 先尝试ISO 8601格式
                if (str.endsWith("Z")) {
                    Instant instant = Instant.parse(str);
                    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                }
                localDateTime = LocalDateTimeUtil.parse(str, DatePattern.NORM_DATETIME_FORMAT.getDateTimeFormatter());
            } catch (DateTimeParseException e) {
                throw new RuntimeException("请输入正确的日期格式：yyyy-MM-dd HH:mm:ss 或 ISO 8601");
            }
            return localDateTime;
        }
    }

    /**
     * string 转为 LocalDate 配置类
     *
     * @author 卓大
     */
    @Configuration
    public static class StringToLocalDate implements Converter<String, LocalDate> {

        @Override
        public LocalDate convert(String str) {
            if (StringUtils.isBlank(str)) {
                return null;
            }
            LocalDate localDate;
            try {
                localDate = LocalDateTimeUtil.parseDate(str, DatePattern.NORM_DATE_FORMAT.getDateTimeFormatter());
            } catch (DateTimeParseException e) {
                throw new RuntimeException("请输入正确的日期格式：yyyy-MM-dd");
            }
            return localDate;
        }
    }
}