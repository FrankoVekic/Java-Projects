package hexviewer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ArgumentProperties {

    public static void main(String[] args) {

        // saveProperties();
        readProperties();
    }

    private static void saveProperties() {

        try (FileOutputStream fos = new FileOutputStream("config.properties")) {

            Properties p = new Properties();
            p.setProperty("s", "true");
            p.setProperty("h", "true");
            p.setProperty("b", "false");
            p.setProperty("a", "false");
            p.setProperty("file", "HexViewer.java");

            p.storeToXML(fos, "Arguments");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Properties readProperties() {

        Properties p = new Properties();

        try (FileInputStream fis = new FileInputStream("config.properties")) {

            p.loadFromXML(fis);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

}
