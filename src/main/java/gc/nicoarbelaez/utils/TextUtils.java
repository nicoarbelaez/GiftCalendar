package gc.nicoarbelaez.utils;

import org.bukkit.ChatColor;

public class TextUtils {
    public static String formatText(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
