package me.luckkyyz.luckchat.filter.delay;

import me.luckkyyz.luckapi.config.SettingConfig;
import me.luckkyyz.luckchat.filter.AbstractMessageFilter;
import me.luckkyyz.luckchat.filter.MessageFilterRequest;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DelayMessageFilter extends AbstractMessageFilter<DelayMessageFilterConfig> {

    public DelayMessageFilter(SettingConfig settingConfig) {
        super(DelayMessageFilterConfig::new, settingConfig);
    }

    private Map<UUID, Long> sentMap = new HashMap<>();

    @Override
    protected MessageFilterRequest testing(AsyncPlayerChatEvent event) {
        MessageFilterRequest request = new MessageFilterRequest(event);
        if(event.isCancelled()) {
            return request;
        }

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        long lastTime = sentMap.getOrDefault(uuid, System.currentTimeMillis());
        if(!sentMap.containsKey(uuid)) {
            sentMap.put(uuid, System.currentTimeMillis());
            return request;
        }

        long delay = (System.currentTimeMillis() - lastTime) / 1000; // задержка между последним сообщением и текущим
        if(delay < config.getDelay()) {
            event.setCancelled(true);
            long delayShow = config.getDelay() - delay;
            config.getMessage().toAdaptive()
                    .placeholder("%delay%", delayShow)
                    .send(player);
        } else {
            sentMap.compute(uuid, (uuid1, time) -> System.currentTimeMillis());
        }

        return request;
    }
}
