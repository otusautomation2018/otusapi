package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    public static String getPropertyFromFile(String pathToFile, String propertyName) {
        Properties prop = new Properties();
        InputStream input = null;
        String propertyValue = null;

        try {

            input = PropertyReader.class.getClassLoader().getResourceAsStream(pathToFile);

            // load a properties file
            prop.load(input);

            // get the property value
            propertyValue = prop.getProperty(propertyName);
            return propertyValue;

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return propertyValue;
    }
}
