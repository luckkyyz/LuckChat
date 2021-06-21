package me.luckkyyz.luckchat.filter.delay;

import me.luckkyyz.luckapi.message.Message;
import me.luckkyyz.luckapi.message.serialize.MessageSerializers;
import me.luckkyyz.luckchat.filter.AbstractMessageFilter;
import org.bukkit.configuration.ConfigurationSection;

public class DelayMessageFilterConfig extends AbstractMessageFilter.AbstractConfig {

    private int delay;
    private Message message;

    DelayMessageFilterConfig() {
    }

    public int getDelay() {
        return delay;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    protected String getPath() {
        return "delay";
    }

    @Override
    protected boolean loading(ConfigurationSection section) {
        delay = section.getInt("delay", 3);
        message = MessageSerializers.yaml().deserialize(section.getRoot(), section.getCurrentPath() + ".message");
        return true;
    }
}
