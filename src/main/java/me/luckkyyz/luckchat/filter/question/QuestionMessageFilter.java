package me.luckkyyz.luckchat.filter.question;

import me.luckkyyz.luckapi.config.SettingConfig;
import me.luckkyyz.luckchat.filter.AbstractMessageFilter;
import me.luckkyyz.luckchat.filter.MessageFilterRequest;
import me.luckkyyz.luckchat.util.Patterns;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class QuestionMessageFilter extends AbstractMessageFilter<QuestionMessageFilterConfig> {

    public QuestionMessageFilter(SettingConfig settingConfig) {
        super(QuestionMessageFilterConfig::new, settingConfig);
    }

    @Override
    protected MessageFilterRequest testing(AsyncPlayerChatEvent event) {
        MessageFilterRequest request = new MessageFilterRequest(event);
        if(event.isCancelled()) {
            return request;
        }
        Player player = event.getPlayer();

        String message = Patterns.onlyLetters(event.getMessage().toLowerCase());

        MessageQuestion question = config.getQuestion(message);
        if(question != null) {
            question.answer(player);
            event.setCancelled(true);
        }

        return request;
    }
}
