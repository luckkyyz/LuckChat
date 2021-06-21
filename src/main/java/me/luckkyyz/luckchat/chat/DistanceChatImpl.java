package me.luckkyyz.luckchat.chat;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Map;

class DistanceChatImpl implements DistanceChat {

    private String symbol;
    private double distance;
    private Map<String, String> messageFormats;

    DistanceChatImpl(String symbol, double distance, Map<String, String> messageFormats) {
        this.symbol = symbol;
        this.distance = distance;
        this.messageFormats = messageFormats;
    }

    @Override
    public String getFirstSymbol() {
        return symbol;
    }

    @Override
    public double getDistance() {
        return distance;
    }

    @Override
    public String getMessageFormat(String group) {
        return messageFormats.get(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistanceChatImpl that = (DistanceChatImpl) o;
        return new EqualsBuilder()
                .append(distance, that.distance)
                .append(symbol, that.symbol)
                .append(messageFormats, that.messageFormats)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(symbol)
                .append(distance)
                .append(messageFormats)
                .toHashCode();
    }
}
