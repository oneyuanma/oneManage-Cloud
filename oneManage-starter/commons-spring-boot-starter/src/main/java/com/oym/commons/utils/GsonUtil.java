package com.oym.commons.utils;

import com.google.common.base.Strings;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class GsonUtil {

    private static final String DEFAULT_DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    private static final String DEFAULT_DATE_FORMATTER = "yyyy-MM-dd";

    private static final String DEFAULT_TIME_FORMATTER = "HH:mm:ss";

    private static final Gson GSON;

    private static final JsonParser JSON_PARSER = new JsonParser();

    private static final Gson PRETTY_GSON;

    /*
     * 增加java8 时间处理类序列化反序列化
     * @since 1.2.5
     */
    static {
        GSON = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, typeOfSrc, context) -> {
                    if (Objects.isNull(localDateTime)) {
                        return new JsonPrimitive("");
                    }
                    return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMATTER)));
                })
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json1, typeOfT, context) -> {
                    String string = json1.getAsJsonPrimitive().getAsString();
                    if (Strings.isNullOrEmpty(string)) {
                        return null;
                    }
                    return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMATTER));
                })
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> {
                    if (Objects.isNull(localDate)) {
                        return new JsonPrimitive("");
                    }
                    return new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMATTER)));
                })
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> {
                    String string = json.getAsJsonPrimitive().getAsString();
                    if (Strings.isNullOrEmpty(string)) {
                        return null;
                    }
                    return LocalDate.parse(string, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMATTER));
                })
                .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (localTime, type, jsonSerializationContext) -> {
                    if (Objects.isNull(localTime)) {
                        return new JsonPrimitive("");
                    }
                    return new JsonPrimitive(localTime.format(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMATTER)));
                })
                .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, typeOfT, context) -> {
                    String string = json.getAsJsonPrimitive().getAsString();
                    if (Strings.isNullOrEmpty(string)) {
                        return null;
                    }
                    return LocalTime.parse(string, DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMATTER));
                })
                .create();
        PRETTY_GSON = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, typeOfSrc, context) -> {
                    if (Objects.isNull(localDateTime)) {
                        return new JsonPrimitive("");
                    }
                    return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMATTER)));
                })
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json1, typeOfT, context) -> {
                    String string = json1.getAsJsonPrimitive().getAsString();
                    if (Strings.isNullOrEmpty(string)) {
                        return null;
                    }
                    return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMATTER));
                })
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> {
                    if (Objects.isNull(localDate)) {
                        return new JsonPrimitive("");
                    }
                    return new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMATTER)));
                })
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> {
                    String string = json.getAsJsonPrimitive().getAsString();
                    if (Strings.isNullOrEmpty(string)) {
                        return null;
                    }
                    return LocalDate.parse(string, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMATTER));
                })
                .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (localTime, type, jsonSerializationContext) -> {
                    if (Objects.isNull(localTime)) {
                        return new JsonPrimitive("");
                    }
                    return new JsonPrimitive(localTime.format(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMATTER)));
                })
                .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, typeOfT, context) -> {
                    String string = json.getAsJsonPrimitive().getAsString();
                    if (Strings.isNullOrEmpty(string)) {
                        return null;
                    }
                    return LocalTime.parse(string, DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMATTER));
                })
                .setPrettyPrinting()
                .create();
    }

    /**
     * Json美化输出工具
     *
     * @param json json string
     * @return beauty json string
     */
    public static String toPrettyFormat(String json) {
        JsonElement jsonElement = JSON_PARSER.parse(json);
        return PRETTY_GSON.toJson(jsonElement);
    }

    /**
     * 美化json
     *
     * @param object obj
     * @return beauty json string
     */
    public static String toPrettyFormat(Object object) {
        return toPrettyFormat(toJson(object));
    }

    /**
     * Json转换成对象
     *
     * @param json json string
     * @param <T>  对象类型
     * @return T
     */
    public static <T> T fromJson(String json, Class<T> t) {
        return GSON.fromJson(json, t);
    }

    /**
     * Json转换成对象
     * 该方式适用于泛型内包含泛型，如想转换的返回值为：User<String>
     * 使用方式
     * <code>
     * Type type = new TypeToken<User<String>>(){}.getType();
     * User<String> user = JsonHelper.json2Object(json,type);
     * </code>
     *
     * @param json json
     * @param type type
     * @param <T>  T
     * @return T
     */
    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    /**
     * 对象转换成Json字符串
     * json分为对象和数组两种类型，需要分别处理
     *
     * @param object obj
     * @return json string
     */
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    /**
     * json转换成JsonElement
     *
     * @param json jsonStr
     * @return {@link JsonElement}
     */
    public static JsonElement json2JsonElement(String json) {
        return JSON_PARSER.parse(json);
    }

}
