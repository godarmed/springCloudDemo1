package com.springcloud.global.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 给字符串中指定模式的字串加上前后缀
     *
     * @param sourceStr 初始字符串
     * @param regex     模式字串
     * @param prefix    要添加的前缀
     * @param suffix    要添加的后缀
     * @return
     */
    public static String addPreAndSuf(String sourceStr, String regex, String prefix, String suffix) {
        // 生成 Pattern 对象并且编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // 用 Pattern 类的 matcher() 方法生成一个 Matcher 对象
        Matcher m = pattern.matcher(sourceStr);
        StringBuffer sb = new StringBuffer(sourceStr);
        while (m.find()) {
            //此时sb为str，替换指定模式的字符串,并且将最后匹配到之前的子串都添加到sb对象中
            m.appendReplacement(sb, prefix + m.group() + suffix);
        }
        //此时sb为替换前和替换后的字符串之和，将最后匹配到后面的子串添加到sb对象中
        m.appendTail(sb);
        //输出内容为fatdogfatdogfat
        return sb.substring(sourceStr.length());
    }
}
