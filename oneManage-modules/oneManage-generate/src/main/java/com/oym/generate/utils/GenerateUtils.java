package com.oym.generate.utils;

/**
 * 代码生成常用工具
 *
 * @author oneyuanma
 * @date 2021/08/04
 */
public class GenerateUtils {

    /**
     * 替换掉下划线并让紧跟它后面的字母大写,例如 ad_code 转成 adCode
     *
     * @param str
     * @return
     */
    public static String strReplaceToLowerCase(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append(str.toLowerCase());

        int count = sb.indexOf("_");
        while (count != 0) {
            int num = sb.indexOf("_", count);
            count = num + 1;
            if (num != -1) {
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count, count + 1, ia + "");
            }
        }
        return sb.toString().replaceAll("_", "");
    }

    /**
     * 返回首字母大写的字符串
     *
     * @param str
     * @return
     */
    public static String firstStrToUpperCase(String str) {
        str = strReplaceToLowerCase(str);
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 返回首字母和第二个字母小写的字符串
     *
     * @param str
     * @return
     */
    public static String firstAndSecondStrToLowerCase(String str) {
        String resultStr = str.substring(0, 1).toLowerCase() + str.substring(1, 2).toLowerCase() + str.substring(2);
        return resultStr;
    }

    /**
     * 返回首字母小写的字符串
     *
     * @param str
     * @return
     */
    public static String firstStrToLowerCase(String str) {
        String resultStr = str.substring(0, 1).toLowerCase() + str.substring(1);
        return resultStr;
    }
}
