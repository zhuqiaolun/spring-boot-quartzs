package com.demon.springbootquartz.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: Demon
 * @date 2021/2/23 11:33
 * @description:   生成CODE值
 */
public class RandomCodeUtil {

    /**
     *  创建自定义位置的CODE值
     */
    public static String code(int count) {
        return code(count, null);
    }

    /**
     *  创建自定义位的CODE值，以及添加前缀值
     */
    public static String code(int count, String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            return prefix + RandomStringUtils.randomAlphanumeric(count);
        } else {
            return RandomStringUtils.randomAlphanumeric(count);
        }
    }

    /**
     *  创建自定义位的CODE值-纯数字，以及添加前缀值
     */
    public static String codeNum(int count, String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            return prefix + RandomStringUtils.random(count, false, true);
        } else {
            return RandomStringUtils.random(count, false, true);
        }
    }

    /**
     *  创建10位置的CODE值，以及添加前缀值
     *  前缀值为null时 则没有
     */
    public static String code5(String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            return prefix + RandomStringUtils.randomAlphanumeric(5);
        } else {
            return RandomStringUtils.randomAlphanumeric(5);
        }
    }

    /**
     *  创建10位置的CODE值，以及添加前缀值
     *  前缀值为null时 则没有
     *  letters true,生成的字符串可以包括字母字符
     *  numbers true,生成的字符串可以包含数字字符
     */
    public static String code10(String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            return prefix + RandomStringUtils.random(10, false, true);
        } else {
            return RandomStringUtils.random(10, false, true);
        }
    }

    /**
     *  创建15位置的CODE值，以及添加前缀值
     *  前缀值为null时 则没有
     */
    public static String code15(String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            return prefix + RandomStringUtils.randomAlphanumeric(15);
        } else {
            return RandomStringUtils.randomAlphanumeric(15);
        }
    }

    /**
     *  创建20位置的CODE值，以及添加前缀值
     *  前缀值为null时 则没有
     */
    public static String code20() {
        return code20(null);
    }

    /**
     *  创建20位置的CODE值，以及添加前缀值
     *  前缀值为null时 则没有
     */
    public static String code20(String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            return prefix + RandomStringUtils.randomAlphanumeric(20);
        } else {
            return RandomStringUtils.randomAlphanumeric(20);
        }
    }



}
