package helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyProvider {
    private static PropertyProvider instance;
    private static Properties properties;

    public static PropertyProvider getInstance() {
        if (instance != null) {
            return instance;
        }
        return new PropertyProvider();
    }

    private PropertyProvider() {
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
            Properties appProps = new Properties();
            appProps.load(new FileInputStream(path));
            properties = appProps;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperties(String propName) {
        return properties.getProperty(propName);
    }
}
