package com.oym.commons.utils;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author oneyuanma
 * @date 2021/06/25
 */
public class StringUtil {

    static String COMMA = ",";

    /**
     * 去掉所有空格
     *
     * @param str
     * @return
     */
    public static String trimAll(String str) {
        return str.replaceAll("\\s+", "");
    }

    /**
     * trim
     * @param str
     * @return
     */
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 把字符串数组转成带，的字符串
     *
     * @param arr
     * @return 返回字符串，格式如1,2,3
     */
    public static String convertArrayToString(String[] arr) {
        return convertArrayToString(arr, COMMA);
    }

    /**
     * 把字符串数组转成带，的字符串
     *
     * @param arr
     * @param split
     * @return String 返回字符串，格式如1,2,3
     * @since 1.0.0
     */
    public static String convertArrayToString(String[] arr, String split) {
        if (arr == null || arr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            sb.append(split);
        }
        return sb.substring(0, sb.length() - split.length());
    }

    /**
     * 把字符串类型的集合转成带，的字符串
     *
     * @param strs Collection<String> 适用于List、Set等。
     * @return
     */
    public static String convertCollectionAsString(Collection<String> strs) {
        return convertCollectionAsString(strs, COMMA);
    }

    /**
     * /** 把字符串类型的集合转成指定拆分字符串的字符串
     *
     * @param strs  Collection<String> 适用于List、Set等。
     * @param split 拆分字符串
     * @return String
     * @since 1.0.0
     */
    public static String convertCollectionAsString(Collection<String> strs,
                                                   String split) {
        if (strs == null || strs.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = strs.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            sb.append(split);
        }
        return sb.substring(0, sb.length() - split.length());
    }

    /**
     * 把字符串的第一个字母转为大写
     *
     * @param str
     * @return
     */
    public static String upperFirst(String str) {
        return toFirst(str, true);
    }


    /**
     * 为空判断,0做空处理。
     * <pre>
     * 这里判断：
     * 1.字符串为NULL
     * 2.字符串为空串。
     * 3.字符串为0。
     * </pre>
     *
     * @param tmp
     * @return boolean
     */
    public static boolean isZeroEmpty(String tmp) {
        boolean isEmpty = StringUtil.isEmpty(tmp);
        if (isEmpty) {
            return true;
        }
        return "0".equals(tmp);
    }

    /**
     * 非空判断。
     *
     * @param tmp
     * @return boolean
     */
    public static boolean isNotZeroEmpty(String tmp) {
        return !isZeroEmpty(tmp);
    }

    /**
     * 把字符串的第一个字母转为小写
     *
     * @param str
     * @return
     */
    public static String lowerFirst(String str) {
        return toFirst(str, false);
    }

    /**
     * 把字符串的第一个字母转为大写或者小写
     *
     * @param str     字符串
     * @param isUpper 是否大写
     * @return
     */
    public static String toFirst(String str, boolean isUpper) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        char first = str.charAt(0);
        String firstChar = new String(new char[]{first});
        firstChar = isUpper ? firstChar.toUpperCase() : firstChar.toLowerCase();
        return firstChar + str.substring(1);
    }

    /**
     * 将content中所有{...}的替换为replace参数内容
     *
     * @param content 待替换的字符串
     * @param replace 替换的字符串
     * @return 替换后的字符串，如content=abc{aa}{bb} ； replace ="ff"，结果就是abcffff
     */
    public static String replaceVariable(String content, String replace) {
        return replaceVariable(content, replace, "\\{(.*?)\\}");
    }

    /**
     * 将content中所有符合regular正则表达式的内容替换为replace参数内容
     *
     * @param content 待替换的字符串
     * @param replace 替换的字符串
     * @param regular 正则表达式
     * @return 替换后的字符串。 如content=abc{aa}{bb} ； replace
     * ="ff"，regular="\\{(.*?)\\}"；结果就是abcffff
     */
    public static String replaceVariable(String content, String replace,
                                         String regular) {
        Pattern regex = Pattern.compile(regular);
        String result = content;
        Matcher regexMatcher = regex.matcher(result);
        while (regexMatcher.find()) {
            String toReplace = regexMatcher.group(0);
            result = result.replace(toReplace, replace);
            regexMatcher = regex.matcher(result);
        }
        return result;
    }

    /**
     * 对传入的字符串（content）进行变量值替换（map） 采用默认的正则表达式：\\{(.*?)\\}
     *
     * @param content 要处理的字符串
     * @param map     替换参数和值的集合
     * @return 替换后的字符串
     * @throws Exception
     */
    public static String replaceVariableMap(String content,
                                            Map<String, Object> map) throws Exception {
        return replaceVariableMap(content, map, "\\{(.*?)\\}");
    }

    /**
     * @param template 要处理的字符串
     * @param map      替换参数和值的集合
     * @param regular  正则表达式
     * @return 替换后的字符串
     * @throws Exception 如果template的某个
     */
    public static String replaceVariableMap(String template,
                                            Map<String, Object> map, String regular) throws Exception {
        Pattern regex = Pattern.compile(regular);
        Matcher regexMatcher = regex.matcher(template);
        while (regexMatcher.find()) {
            String key = regexMatcher.group(1);
            String toReplace = regexMatcher.group(0);
            String value = (String) map.get(key);
            if (value != null) {
                template = template.replace(toReplace, value);
            } else {
                throw new Exception("没有找到[" + key + "]对应的变量值，请检查表变量配置!");
            }
        }

        return template;
    }

    /**
     * String转Byte数组
     *
     * @return
     */
    public static byte[] stringToBytes(String str) {
        byte digest[] = new byte[str.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = str.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }

        return digest;
    }

    /**
     * Byte数组转String
     *
     * @param b
     * @return
     */
    public static String bytesToString(byte b[]) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
            hexString.append(plainText);
        }
        return hexString.toString();
    }


    /**
     * 字符串 编码转换
     *
     * @param str  字符串
     * @param from 原來的編碼
     * @param to   轉換后的編碼
     * @return
     */
    public static String encodingString(String str, String from, String to) {
        String result = str;
        try {
            result = new String(str.getBytes(from), to);
        } catch (Exception e) {
            result = str;
        }
        return result;
    }

    /**
     * 删除后面指定的字符
     *
     * @param toTrim
     * @param trimStr
     * @return
     */
    public static String trimSufffix(String toTrim, String trimStr) {
        while (toTrim.endsWith(trimStr)) {
            toTrim = toTrim.substring(0, toTrim.length() - trimStr.length());
        }
        return toTrim;
    }

    /**
     * 将数据库字段名转为DataGrid字段名
     * isIgnoreFirst:是否忽略第一个字段不转大写
     *
     * @return
     */
    public static String convertDbFieldToField(String dbField) {
        return convertDbFieldToField(dbField, "_", true);
    }

    /**
     * 将数据库字段名转为DataGrid字段名,如 sys_data_ 转为sysData
     * symbol:间隔符号
     * isIgnoreFirst：是否忽略第一个单词的首字母转大写
     *
     * @return
     */
    public static String convertDbFieldToField(String dbField, String symbol, boolean isIgnoreFirst) {
        String result = "";
        if (dbField.startsWith(symbol)) {
            dbField = dbField.substring(1);
        }
        ;
        if (dbField.endsWith(symbol)) {
            dbField = dbField.substring(0, dbField.length() - 1);
        }
        ;
        String[] arr = dbField.split(symbol);
        for (int i = 0; i < arr.length; i++) {
            String str = arr[i];
            if (isIgnoreFirst && i != 0) {
                char oldChar = str.charAt(0);
                char newChar = (oldChar + "").toUpperCase().charAt(0);
                str = newChar + str.substring(1);
            }
            result += str;
        }
        return result;
    }


    public static String[] getStringAryByStr(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        ;

        String[] aryId = str.split(",");
        String[] lAryId = new String[aryId.length];
        for (int i = 0; i < aryId.length; i++) {
            lAryId[i] = (aryId[i]);
        }
        return lAryId;
    }

    /**
     * 数组合并。
     *
     * @param vals
     * @param separator
     * @return
     */
    public static String join(String[] vals, String separator) {
        if (Argument.isNull(vals) || vals.length == 0) {
            return "";
        }
        String val = "";
        for (int i = 0; i < vals.length; i++) {
            if (i == 0) {
                val += vals[i];
            } else {
                val += separator + vals[i];
            }
        }
        return val;
    }


    /**
     * 对字符串去掉前面的指定字符
     *
     * @param content 待处理的字符串
     * @param suffix  要去掉后面的指定字符串
     * @return
     */
    public static String trimSuffix(String content, String suffix) {
        String resultStr = content;
        while (resultStr.endsWith(suffix)) {
            resultStr = resultStr.substring(0,
                    resultStr.length() - suffix.length());
        }
        return resultStr;
    }

    /**
     * 对字符串去掉前面的指定字符
     *
     * @param content 待处理的字符串
     * @param prefix  要去掉前面的指定字符串
     * @return
     */
    public static String trimPrefix(String content, String prefix) {
        String resultStr = content;
        while (resultStr.startsWith(prefix)) {
            resultStr = resultStr.substring(prefix.length());
        }
        return resultStr;
    }

    private static Pattern UNDERLINE_TO_CAMELHUMP_PATTERN = Pattern.compile("_[a-z]");

    /**
     * 空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 非空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 非空判断，包括“”,null,"null"
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {

        return !isEmpty(str) && !str.equals("null");
    }

    public static boolean isBlank(String str) {

        return !isNotBlank(str);
    }

    /**
     * 将驼峰风格替换为下划线风格
     */
    public static String camelhumpToUnderline(String str) {
        final int size;
        final char[] chars;
        final StringBuilder sb = new StringBuilder(
                (size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
        char c;
        for (int i = 0; i < size; i++) {
            c = chars[i];
            if (isUppercaseAlpha(c)) {
                sb.append('_').append(toLowerAscii(c));
            } else {
                sb.append(c);
            }
        }
        return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
    }

    /**
     * 将下划线风格替换为驼峰风格
     */
    public static String underlineToCamelhump(String str) {
        Matcher matcher = UNDERLINE_TO_CAMELHUMP_PATTERN.matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }
        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }
        return builder.toString();
    }

    public static boolean isUppercaseAlpha(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    public static boolean isLowercaseAlpha(char c) {
        return (c >= 'a') && (c <= 'z');
    }

    public static char toUpperAscii(char c) {
        if (isLowercaseAlpha(c)) {
            c -= (char) 0x20;
        }
        return c;
    }

    public static char toLowerAscii(char c) {
        if (isUppercaseAlpha(c)) {
            c += (char) 0x20;
        }
        return c;
    }

}
