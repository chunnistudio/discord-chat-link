package studio.chunni.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import studio.chunni.bridge.MinecraftBridge;
import studio.chunni.config.PropertiesLoader;

public class DiscordListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        if(!event.getChannel().getId().equals(PropertiesLoader.get("channel.id"))) return;

        String user = event.getAuthor().getName();
        String message = event.getMessage().getContentDisplay();
        String finalMessage = "[DISCORD] " + user + ": " + message;
        MinecraftBridge.sendToMinecraft(finalMessage);
    }
}
