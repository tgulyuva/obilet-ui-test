package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestConfig {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/application.properties";
    
    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE_PATH, e);
        }
    }

    public static String getBaseUrl() {
        String url = System.getProperty("base.url");
        return url != null ? url : properties.getProperty("base.url");
    }

    public static String getBrowser() {
        String browser = System.getProperty("browser");
        return browser != null ? browser : properties.getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        String headless = System.getProperty("headless");
        if (headless != null) {
            return Boolean.parseBoolean(headless);
        }
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

} 