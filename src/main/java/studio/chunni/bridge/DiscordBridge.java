package studio.chunni.bridge;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.chunni.constants.ModConstants;
import studio.chunni.listeners.DiscordListener;


public class DiscordBridge {
    private static JDA jda;
    private static WebhookClient webhookClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(ModConstants.MOD_ID);


    public static void start(String botToken) {
        try {
            jda = JDABuilder.createDefault(botToken)
                    .addEventListeners(new DiscordListener())
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .build();
        }catch (Exception e) {
            LOGGER.error("Failed to start Discord Bridge", e);
        }
    }

    public static void startWebhook(String url) {
        try {
            webhookClient = WebhookClient.withUrl(url);
        }catch (Exception e) {
            LOGGER.error("Failed to start Webhook", e);
        }
    }

    public static void sendToDiscordWebhook(String message, String name, String avatarUrl) {
        webhookClient.send(
                new WebhookMessageBuilder()
                        .setUsername(message)
                        .setAvatarUrl(avatarUrl)
                        .setContent(name)
                        .build()
        );
    }

    public static void sendToDiscord(String sender, String message, String channelId) {
        if(jda != null && jda.getStatus().isInit()) {
            LOGGER.info("Sending Discord Message {}", channelId);
            TextChannel channel = jda.getTextChannelById(channelId);
            if(channel != null) {
                String finalMessage = "[MINECRACT] " + sender + ": " + message;
                LOGGER.info("Sending Discord Message {}", finalMessage);
                channel.sendMessage(finalMessage).queue();
            }
        }
    }
}
