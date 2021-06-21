package me.luckkyyz.luckchat.filter.question;

import me.luckkyyz.luckapi.message.Message;
import me.luckkyyz.luckapi.message.serialize.MessageSerializers;
import me.luckkyyz.luckchat.filter.AbstractMessageFilter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuestionMessageFilterConfig extends AbstractMessageFilter.AbstractConfig {

    private Set<MessageQuestion> questions;

    QuestionMessageFilterConfig() {
    }

    public MessageQuestion getQuestion(String message) {
        return questions.stream()
                .filter(question -> question.isQuestion(message))
                .findFirst()
                .orElse(null);
    }

    @Override
    protected String getPath() {
        return "questions";
    }

    @Override
    protected boolean loading(ConfigurationSection section) {
        questions = new HashSet<>();
        ConfigurationSection valuesSection = section.getConfigurationSection("values");
        if(valuesSection == null) {
            return true;
        }
        valuesSection.getKeys(false).forEach(key -> {
            ConfigurationSection section1 = valuesSection.getConfigurationSection(key);
            if(section1 == null) {
                return;
            }
            List<String> variants = section1.getStringList("variants");
            Message answer = MessageSerializers.yaml().deserialize(section.getRoot(), section1.getCurrentPath() + ".answer");
            questions.add(new MessageQuestion(variants, answer));
        });
        return true;
    }
}
