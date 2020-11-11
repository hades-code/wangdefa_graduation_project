package org.gourd.hu.core.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 * @author gourd.hu
 */
public class MatcherUtil {
    /**
     * @param regex
     * 正则表达式字符串
     * @param str
     * 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断当前系统时候是linux
     * @return
     */
    public static boolean isLinux() {
        String osType = System.getProperties().getProperty("os.name").toLowerCase();
        if (osType.startsWith("windows")) {
            return false;
        } else {
            return true;
        }
    }
}
