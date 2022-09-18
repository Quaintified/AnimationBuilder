package com.dndcraft.quaint.utils;

import com.dndcraft.quaint.AnimationBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messenger {
    public static void logInfo(String message) {
        AnimationBuilder.getInstance().getLogger().info(message);
    }

    public static void logWarning(String message) {
        AnimationBuilder.getInstance().getLogger().warning(message);
    }

    public static void logSevere(String message) {
        AnimationBuilder.getInstance().getLogger().severe(message);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(codedString(message));
    }

    public static String codedString(String from) {
        return ChatColor.translateAlternateColorCodes('&', from);
    }

    public static String stripColor(String from){
        return PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize(from))
                .replaceAll("([<][#][1-9a-f][1-9a-f][1-9a-f][1-9a-f][1-9a-f][1-9a-f][>])+", "")
                .replaceAll("[&][1-9a-f]", "");
    }

    public static String legacyColors(Component from) {
        return LegacyComponentSerializer.legacySection().serialize(from);
    }

    public static Component codedComponent(String from) {
        return LegacyComponentSerializer.legacy('&').deserialize(newLined(from));
    }

    public static String newLined(String from) {
        return from.replace("%newline%", "\n");
    }
}
