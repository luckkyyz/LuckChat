package me.luckkyyz.luckchat.chat.spy;

import me.luckkyyz.luckapi.event.QuickEvent;
import me.luckkyyz.luckapi.event.QuickEventListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ChatSpyingManager {

    private List<Player> spying = new ArrayList<>();

    public ChatSpyingManager(Plugin plugin) {
        QuickEventListener.newListener().event(QuickEvent.newBuilder(AsyncPlayerChatEvent.class)
                .setIgnoreCancelled(true)
                .setPriority(EventPriority.MONITOR)
                .addAction(event -> {
                    String format = event.getFormat();
                    String spyMessage = String.format(format, event.getPlayer().getName(), event.getMessage());
                    for(Player spying : this.spying) {
                        if(event.getRecipients().contains(spying)) {
                            continue;
                        }
                        spying.sendMessage(ChatColor.GRAY + "[Прослушка] " + ChatColor.WHITE + spyMessage);
                    }
                })
        ).register(plugin);
    }

    public boolean isSpying(Player player) {
        return spying.contains(player);
    }

    public void addSpying(Player player) {
        spying.add(player);
    }

    public void removeSpying(Player player) {
        spying.remove(player);
    }

}
