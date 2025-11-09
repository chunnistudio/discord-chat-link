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

public class Chunnidiscordchatlink implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(ModConstants.MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Initializing Minecraft Chat Link");
		PropertiesLoader.loadOrCreate();
		String botToken = PropertiesLoader.get("bot.token");
		String channelId = PropertiesLoader.get("channel.id");
		if(botToken == null || channelId == null) {
			LOGGER.error("Bot token or channel id is null");
			return;
		}
		DiscordBridge.start(botToken);
		ServerLifecycleEvents.SERVER_STARTED.register(MinecraftBridge::setServer);

		ServerMessageEvents.CHAT_MESSAGE.register((msg, sender, context) -> {
			String finalMessage =ChatUtils.filterMessage(msg.decoratedContent().toString());
			String user = ChatUtils.filterMessage(sender.getName().toString());
			LOGGER.info("Sender {} received a chat message {}", user, finalMessage);
			DiscordBridge.sendToDiscord(user, finalMessage, channelId);
		});


	}
}