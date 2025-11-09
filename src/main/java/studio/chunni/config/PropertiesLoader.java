package studio.chunni.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.chunni.constants.BotConstant;
import studio.chunni.constants.ModConstants;

import java.io.*;
import java.util.Properties;

public class PropertiesLoader {

    private static Properties properties = new Properties();
    private static File propertiesFile = new File("config/discord-link.properties");
    public static final Logger LOGGER = LoggerFactory.getLogger(ModConstants.MOD_ID);

    public static void loadOrCreate() {
        if(!propertiesFile.exists()) {
            createDefault();
        }
        try( FileInputStream reader = new FileInputStream(propertiesFile)) {
            properties.load(reader);
        } catch (IOException e ) {
            LOGGER.error("Error loading properties file", e);
        }
    }

    private static void createDefault() {
        try {
            boolean isCreated = propertiesFile.createNewFile();
            if(isCreated) {
                FileWriter writer = new FileWriter("config/discord-link.properties");
                writer.write("bot.token=".concat(BotConstant.BOT_TOKEN).concat("\n"));
                writer.write("channel.id=".concat(BotConstant.CHANNEL_ID));
                writer.close();
            }

        }catch (IOException e) {
            LOGGER.error("Error creating default properties file", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
