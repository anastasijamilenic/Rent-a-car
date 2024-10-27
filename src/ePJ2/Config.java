package ePJ2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for accessing configuration properties.
 * This class loads properties from a configuration file named "config.properties" and provides methods to access them.
 */
public class Config {
    
    /**
     * Properties object to store configuration properties.
     */
    private static Properties properties = new Properties();

    /**
     * Static initializer block to load properties from the configuration file.
     */
    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the value of the property with the specified key.
     * @param key The key of the property.
     * @return The value of the property with the specified key, or null if the property is not found.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Gets the value of the property with the specified key as a double.
     * @param key The key of the property.
     * @return The value of the property with the specified key as a double, or 0.0 if the property is not found or cannot be parsed as a double.
     */
    public static double getDoubleProperty(String key) {
        String value = properties.getProperty(key);
        return value != null ? Double.parseDouble(value) : 0.0;
    }
}
