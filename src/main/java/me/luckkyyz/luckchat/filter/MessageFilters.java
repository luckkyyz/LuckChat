package me.luckkyyz.luckchat.filter;

import me.luckkyyz.luckapi.event.QuickEventListener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class MessageFilters {

    private Set<MessageFilter> filters = new HashSet<>();

    public MessageFilters(Plugin plugin) {
        QuickEventListener.newListener().event(AsyncPlayerChatEvent.class, event -> {
            filters.forEach(filter -> filter.test(event));
        }).register(plugin);
    }

    public void registerMessageFilter(MessageFilter filter) {
        filters.add(filter);
    }

    public Set<MessageFilter> getMessageFilters() {
        return filters;
    }
}
