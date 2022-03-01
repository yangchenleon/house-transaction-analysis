package org.example.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyUtil {
    private static Properties properties;
    synchronized private static void loadProperties(String fileName) {
        properties = new Properties();
        InputStreamReader stream = null;
        try {
            stream = new InputStreamReader(
                    PropertyUtil.class.getClassLoader()
                    .getResourceAsStream(fileName + ".properties"),
                    "UTF-8");
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != stream) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String getProperty(String fileName, String key) {
        if (null == properties) {
            loadProperties(fileName);
        }
        return properties.getProperty(key);
    }
}
