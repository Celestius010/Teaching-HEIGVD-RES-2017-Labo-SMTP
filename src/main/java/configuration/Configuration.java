package configuration;

import model.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Benoit Gianinetti
 */
public class Configuration {

    private static final String MESSAGES_SEPARATOR = "~~~";
    private static Configuration instance;
    private Properties properties;
    private List<String> messages = new ArrayList<String>();
    private List<Person> victims = new ArrayList<Person>();

    public Configuration() {

        // Load configuration file
        String propertyFile = "config/config.properties";
        properties  = new Properties();


        try {

            InputStream is = new FileInputStream(propertyFile);
            properties.load(is);

            // Load prank messages
            BufferedReader reader = new BufferedReader(new FileReader("config/pranks"));

            String line;
            String message = new String();

            while ((line = reader.readLine()) != null) {

                if (line.contains(MESSAGES_SEPARATOR)) {
                    messages.add(new String(message));
                    message = new String();
                } else {
                    message += line;
                }
            }

            // Load targets' email addresses
            reader = new BufferedReader(new FileReader("./config/victims"));

            while ((line = reader.readLine()) != null) {
                victims.add(new Person(line));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int smtpServerPort() {
        return Integer.parseInt(properties.getProperty("smtpServerPort"));
    }

    public String smtpServerAddress() {
        return properties.getProperty("smtpServerAddress");
    }

    public int getNumberOfGroups() {
        return Integer.parseInt(properties.getProperty("numberOfGroups"));
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<Person> getVictims() {
        return victims;
    }

    public static Configuration getInstance() {
        instance = new Configuration();

        return instance;
    }
}
