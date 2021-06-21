package me.luckkyyz.luckchat.util;

import me.luckkyyz.luckapi.util.permission.PermissionNode;

public final class Permissions {

    private Permissions() {
        throw new UnsupportedOperationException();
    }

    public static final PermissionNode LUCK_CHAT_COMMAND = PermissionNode.node("LuckChat.luckchat");

    public static final PermissionNode LUCK_CHAT_RELOAD_COMMAND = PermissionNode.node("LuckChat.luckchat.reload");

    public static final PermissionNode LUCK_CHAT_CLEAR_COMMAND = PermissionNode.node("LuckChat.luckchat.clear");

    public static final PermissionNode LUCK_CHAT_SPY_COMMAND = PermissionNode.node("LuckChat.luckchat.clear");

    public static final PermissionNode LUCK_CHAT_COLORS = PermissionNode.node("LuckChat.chat.colors");
}
