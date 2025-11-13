package studio.chunni;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.chunni.bridge.DiscordBridge;
import studio.chunni.bridge.MinecraftBridge;
import studio.chunni.config.PropertiesLoader;
import studio.chunni.constants.ModConstants;
import studio.chunni.utils.ChatUtils;

import java.util.HashMap;

public class Chunnidiscordchatlink implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(ModConstants.MOD_ID);
	private HashMap<String, String> avatars = null;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Initializing Minecraft Chat Link");
		PropertiesLoader.loadOrCreate();
		String botToken = PropertiesLoader.get("bot.token");
		String channelId = PropertiesLoader.get("channel.id");
		String mode = PropertiesLoader.get("mode");
		String webhookUrl = PropertiesLoader.get("webhook.url");

		if(ModConstants.MODE_BOTS.equals(mode)) {
			if(channelId == null || channelId.isEmpty()) {
				LOGGER.error("channel id is null");
				return;
			}

		}else if(ModConstants.MODE_WEBHOOK.equals(mode)) {
			if(webhookUrl == null || webhookUrl.isEmpty()) {
				LOGGER.error("Webhook url is null");
				return;
			}
			avatars = new HashMap<>();
			DiscordBridge.startWebhook(webhookUrl);
		}else {
			LOGGER.error("Bot token or channel id is null");
			return;
		}

		if(botToken == null || botToken.isEmpty()) {
			LOGGER.error("Bot token is null");
			return;
		}
		DiscordBridge.start(botToken);
		ServerLifecycleEvents.SERVER_STARTED.register(MinecraftBridge::setServer);


		ServerMessageEvents.CHAT_MESSAGE.register((msg, sender, context) -> {
			String finalMessage =ChatUtils.filterMessage(msg.decoratedContent().toString());
			String user = ChatUtils.filterMessage(sender.getName().toString());
			LOGGER.info("Sender {} received a chat message {}", user, finalMessage);
			if(ModConstants.MODE_BOTS.equals(mode)) {
				DiscordBridge.sendToDiscord(user, finalMessage, channelId);
			}else {
				String playerAvatarToSend = "";
				String playerUUID = sender.getStringUUID();
				if(!avatars.containsKey(playerUUID)) {
					String playerAvatar = "https://mc-heads.net/avatar/".concat(playerUUID);
					avatars.put(playerUUID, playerAvatar);
					playerAvatarToSend = playerAvatar;
				}else {
					playerAvatarToSend = avatars.get(playerUUID);
				}
				DiscordBridge.sendToDiscordWebhook(user, finalMessage, playerAvatarToSend);
			}
		});




	}
}