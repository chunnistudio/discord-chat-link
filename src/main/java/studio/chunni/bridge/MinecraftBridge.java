package studio.chunni.bridge;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;


public class MinecraftBridge {

    private static MinecraftServer server;

    public static void setServer(MinecraftServer s) {
        server = s;
    }

    public static void sendToMinecraft(String message) {
        if(server != null) {
            Component chatMessage = Component.literal(message);
            server.getPlayerList().broadcastSystemMessage(chatMessage, false);
        }
    }
}
