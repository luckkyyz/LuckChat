package me.luckkyyz.luckchat.chat;

import me.luckkyyz.luckapi.chat.ChatFormatTags;
import me.luckkyyz.luckapi.config.SettingConfig;
import me.luckkyyz.luckapi.event.QuickEvent;
import me.luckkyyz.luckapi.event.QuickEventListener;
import me.luckkyyz.luckapi.util.color.ColorUtils;
import me.luckkyyz.luckapi.util.permission.PermissionUtil;
import me.luckkyyz.luckchat.provider.GroupProvider;
import me.luckkyyz.luckchat.util.Permissions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class DistanceChatManager {

    private SettingConfig config;
    private Map<String, DistanceChat> chatMap = new HashMap<>();

    public DistanceChatManager(Plugin plugin, SettingConfig config, GroupProvider groupProvider) {
        this.config = config;

        reload();

        QuickEventListener.newListener().event(QuickEvent.newBuilder(AsyncPlayerChatEvent.class)
                .setPriority(EventPriority.HIGHEST)
                .setIgnoreCancelled(true)
                .addAction(event -> {
                    Player player = event.getPlayer();
                    String message = event.getMessage();
                    DistanceChat distanceChat = findChat(message);
                    if(distanceChat == null) {
                        return;
                    }
                    distanceChat.getFarRecipients(player).forEach(event.getRecipients()::remove);
                    String symbol = distanceChat.getFirstSymbol();
                    if(!symbol.equals("")) {
                        message = message.replaceFirst(symbol, "");
                    }
                    event.setMessage(PermissionUtil.hasPermission(player, Permissions.LUCK_CHAT_COLORS) ? ColorUtils.color(message) : message);

                    String group = groupProvider.getGroup(player);
                    String format = distanceChat.getMessageFormat(group);
                    if(format == null) {
                        return;
                    }
                    event.setFormat(format);
                })
        ).register(plugin);
    }

    private DistanceChat findChat(String message) {
        if(message.isEmpty()) {
            return null;
        }
        return chatMap.getOrDefault(String.valueOf(message.charAt(0)), chatMap.get(""));
    }

    public void reload() {
        chatMap.clear();

        ConfigurationSection chatSection = config.getSection("chats");
        if(chatSection == null) {
            return;
        }
        chatSection.getKeys(false).forEach(key -> {
            ConfigurationSection currentSection = chatSection.getConfigurationSection(key);
            if(currentSection == null) {
                return;
            }
            String symbol = currentSection.getString("symbol", "");
            double distance = currentSection.getDouble("distance", -1);
            Map<String, String> messageFormats = new HashMap<>();

            ConfigurationSection formatSection = currentSection.getConfigurationSection("formats");
            if(formatSection == null) {
                return;
            }
            formatSection.getKeys(false).forEach(key1 -> {
                String format = ColorUtils.color(formatSection.getString(key1, "%name%: %message%"));
                format = format.replace("%name%", ChatFormatTags.NAME).replace("%message%", ChatFormatTags.MESSAGE);

                messageFormats.put(key1, format);
            });

            chatMap.put(symbol, new DistanceChatImpl(symbol, distance, messageFormats));
        });
    }

}
