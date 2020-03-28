package org.smart4j.chapter2.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public final class PropertiesUtil {
    private PropertiesUtil() {
    }

    public static Properties loadProperties(String filename) {
        Properties properties = new Properties();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return properties;
    }

    public static String getString(Properties properties, String key) {
        return getString(properties, key, StringUtils.EMPTY);
    }

    public static String getString(Properties properties, String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getInt(Properties properties, String key, int defaultValue) {
        return CastUtil.castInt(properties.getProperty(key), defaultValue);
    }

    public static int getInt(Properties properties, String key) {
        return getInt(properties, key, 0);
    }

    public static Boolean getBoolean(Properties properties, String key, Boolean defaultValue) {
        return CastUtil.castBoolean(properties.getProperty(key), defaultValue);
    }

    public static Boolean getBoolean(Properties properties, String key) {
        return getBoolean(properties, key, Boolean.FALSE);
    }
}
