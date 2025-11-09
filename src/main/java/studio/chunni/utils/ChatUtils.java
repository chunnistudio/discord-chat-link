package studio.chunni.utils;

public class ChatUtils {

    public static String filterMessage(String msg) {
        String filteredMsg = msg.replaceFirst("^literal\\s*", "")
                .replaceFirst("\\{","")
                .replaceFirst("\\}","");
        return filteredMsg;
    }
}
