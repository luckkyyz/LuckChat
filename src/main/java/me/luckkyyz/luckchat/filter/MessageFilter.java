package me.luckkyyz.luckchat.filter;

import me.luckkyyz.luckapi.config.SettingConfig;
import me.luckkyyz.luckapi.util.permission.PermissionNode;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public interface MessageFilter {

    MessageFilterRequest test(AsyncPlayerChatEvent event);

    Config getConfig();

    interface Config {

        boolean isEnabled();

        boolean isBypass();

        PermissionNode getBypassPermission();

        boolean load(SettingConfig config);

    }
}
