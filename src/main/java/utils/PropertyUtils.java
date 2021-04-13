package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    private static final String CONFIG_PATH = "src/test/resources/config.properties";
    private static final Properties PROPERTIES = System.getProperties();


    private static void readProperties() {
        try {
            PROPERTIES.load(new FileInputStream(new File(CONFIG_PATH)));
        } catch (IOException e) {
            System.out.println("WARNING: There is no property file " + CONFIG_PATH);
        }
    }

    public static String getSystemProperty(String propertyKey) {
        String envProp = System.getenv(propertyKey);
        if (envProp == null) {
            readProperties();
            envProp = PROPERTIES.getProperty(propertyKey);
        }
        return envProp;
    }

}
