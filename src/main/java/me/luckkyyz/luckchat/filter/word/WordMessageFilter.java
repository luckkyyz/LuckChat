package me.luckkyyz.luckchat.filter.word;

import me.luckkyyz.luckapi.config.SettingConfig;
import me.luckkyyz.luckchat.filter.AbstractMessageFilter;
import me.luckkyyz.luckchat.filter.MessageFilterRequest;
import me.luckkyyz.luckchat.util.Patterns;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;

public class WordMessageFilter extends AbstractMessageFilter<WordMessageFilterConfig> {

    public WordMessageFilter(SettingConfig settingConfig) {
        super(WordMessageFilterConfig::new, settingConfig);
    }

    @Override
    protected MessageFilterRequest testing(AsyncPlayerChatEvent event) {
        MessageFilterRequest request = new MessageFilterRequest(event);
        if(event.isCancelled()) {
            return request;
        }

        Player player = event.getPlayer();
        String message = Patterns.onlyLetters(event.getMessage().toLowerCase());

        Matcher matcher = config.getRestricted().matcher(message.toLowerCase());
        while(matcher.find()) {
            if(config.isWhitelisted(message)) {
                continue;
            }

            event.setCancelled(true);
            config.getMessage().send(player);
            break;
        }

        return request;
    }
}
