package me.luckkyyz.luckchat.filter.word;

import me.luckkyyz.luckapi.message.Message;
import me.luckkyyz.luckapi.message.serialize.MessageSerializers;
import me.luckkyyz.luckchat.filter.AbstractMessageFilter;
import me.luckkyyz.luckchat.util.Patterns;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WordMessageFilterConfig extends AbstractMessageFilter.AbstractConfig {

    private Pattern restricted;
    private List<String> whitelist;

    private Message message;

    WordMessageFilterConfig() {
    }

    public Pattern getRestricted() {
        return restricted;
    }

    public boolean isWhitelisted(String message) {
        return whitelist.stream().anyMatch(string -> message.equals(string) || message.startsWith(string) || message.endsWith(string) || message.contains(string));
    }

    public Message getMessage() {
        return message;
    }

    @Override
    protected String getPath() {
        return "word";
    }

    @Override
    protected boolean loading(ConfigurationSection section) {
        List<String> restricted = section.getStringList("restricted").stream().map(String::toLowerCase).collect(Collectors.toList());

        StringBuilder string = new StringBuilder();
        restricted.forEach(got -> string.append("|").append(got));

        this.restricted = Pattern.compile(string.length() > 1 ? string.substring(1) : "a^" + (Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE));

        whitelist = section.getStringList("whitelist").stream()
                .map(String::toLowerCase)
                .map(Patterns::onlyLetters)
                .collect(Collectors.toList());

        message = MessageSerializers.yaml().deserialize(section.getRoot(), section.getCurrentPath() + ".message");
        return true;
    }
}
