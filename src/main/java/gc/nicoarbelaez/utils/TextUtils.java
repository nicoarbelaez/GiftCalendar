package gc.nicoarbelaez.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class TextUtils {
    public static String formatText(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> formatText(List<String> textList) {
        if (textList == null) {
            return null;
        }

        List<String> textListFormatted = new ArrayList<>();
        for (String text : textList) {
            textListFormatted.add(TextUtils.formatText(text));
        }
        return textListFormatted;
    }

    public static void debugLogs(String message) {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(formatText(message));
    }

    public static List<String> autocompleteSuggestions(String input, List<String> listSuggestions) {
        if (input == null || listSuggestions == null || listSuggestions.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<String> suggestions = new ArrayList<>();
        input = input.toLowerCase();
        for (String suggestion : listSuggestions) {
            if (suggestion != null && suggestion.toLowerCase().startsWith(input)) {
                suggestions.add(suggestion);
            }
        }
        System.out.print("[TextUtils]{autocompleteSuggestions} input: " + input + " listSuggestions: " + listSuggestions.toString()); // TODO: Remove
        System.out.print("[TextUtils]{autocompleteSuggestions} suggestions: " + suggestions.toString()); // TODO: Remove
        return suggestions.isEmpty() ? listSuggestions : suggestions;
    }
}
