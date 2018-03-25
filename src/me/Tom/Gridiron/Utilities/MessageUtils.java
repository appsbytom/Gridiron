package me.Tom.Gridiron.Utilities;

import me.Tom.Gridiron.ConfigManager.Configs.Messages;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String addPrefix() {
        return format(Messages.PREFIX);
    }

    public static String formattedMessage(String message) {
        return addPrefix() + format(message);
    }

    public static void broadcastMessage(String message) {
        Bukkit.getServer().broadcastMessage(format(message));
    }

    public static void consoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(format(message));
    }

    public static void sendError(String error) {
        consoleMessage("&4Error &7>> " + error);
    }

    public static void sendError(CommandSender sender, String error) {
        sender.sendMessage(format("&4Error &7>> " + error));
    }

    public static void sendWarning(String warning) {
        consoleMessage("&6Warning &7>> " + warning);
    }

    public static void sendWarning(CommandSender sender, String warning) {
        sender.sendMessage(format("&6Warning &7>> " + warning));
    }

    @SuppressWarnings("deprecation")
    public static void sendTitle(Player p, String title, String subtitle) {
        p.sendTitle(format(title), format(subtitle));
    }
}
