package me.luckkyyz.luckchat.filter;

import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MessageFilterRequest {

    private AsyncPlayerChatEvent event;

    public MessageFilterRequest(AsyncPlayerChatEvent event) {
        this.event = event;
    }

    public AsyncPlayerChatEvent getEvent() {
        return event;
    }
}
