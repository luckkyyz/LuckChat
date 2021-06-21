package me.luckkyyz.luckchat.filter.question;

import me.luckkyyz.luckapi.message.Message;
import me.luckkyyz.luckchat.util.Patterns;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class MessageQuestion {

    private List<String> questionVariants;
    private Message answer;

    MessageQuestion(List<String> questionVariants, Message answer) {
        this.questionVariants = questionVariants.stream()
                .map(String::toLowerCase)
                .map(Patterns::onlyLetters)
                .collect(Collectors.toList());
        this.answer = answer;
    }

    public boolean isQuestion(String message) {
        return questionVariants.stream()
                .anyMatch(string -> message.equals(string) || message.startsWith(string) || message.endsWith(string) || message.contains(string));
    }

    public void answer(Player player) {
        answer.send(player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageQuestion that = (MessageQuestion) o;
        return new EqualsBuilder()
                .append(questionVariants, that.questionVariants)
                .append(answer, that.answer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(questionVariants)
                .append(answer)
                .toHashCode();
    }

}
