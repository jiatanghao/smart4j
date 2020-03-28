package org.smart4j.chapter2.util;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class CastUtil {
    private CastUtil() {
    }

    public static String castString(Object object, String defaultValue) {
        return object != null ? String.valueOf(object) : defaultValue;
    }

    public static String castString(Object object) {
        return castString(object, StringUtils.EMPTY);
    }

    public static Double castDouble(Object object, Double defaultValue) {
        Double doubleValue = defaultValue;
        if (object != null) {
            String strValue = castString(object);
            doubleValue = NumberUtils.toDouble(strValue, defaultValue);
        }
        return doubleValue;
    }

    public static Double castDouble(Object object) {
        return castDouble(object, 0.0);
    }

    public static Long castLong(Object object, Long defaultValue) {
        Long longValue = defaultValue;
        if (object != null) {
            String strValue = castString(object);
            longValue = NumberUtils.toLong(strValue, defaultValue);
        }
        return longValue;
    }

    public static Long castLong(Object object) {
        return castLong(object, 0L);
    }

    public static Integer castInt(Object object, Integer defaultValue) {
        Integer intValue = defaultValue;
        if (object != null) {
            String strValue = castString(object);
            intValue = NumberUtils.toInt(strValue, defaultValue);
        }
        return intValue;
    }

    public static Integer castInt(Object object) {
        return castInt(object, 0);
    }

    public static Boolean castBoolean(Object object, Boolean defaultValue) {
        Boolean boolValue = defaultValue;
        if (object != null) {
            String strValue = castString(object);
            boolValue = BooleanUtils.toBoolean(strValue);
        }
        return boolValue;
    }

    public static Boolean castBoolean(Object object) {
        return castBoolean(object, Boolean.FALSE);
    }
}
