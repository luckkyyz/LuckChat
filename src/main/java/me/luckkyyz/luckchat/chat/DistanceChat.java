package me.luckkyyz.luckchat.chat;

import me.luckkyyz.luckapi.util.player.PlayerFilters;
import me.luckkyyz.luckapi.util.player.PlayerUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public interface DistanceChat {

    String getFirstSymbol();

    double getDistance();

    String getMessageFormat(String group);

    default List<Player> getFarRecipients(Player player) {
        if(getDistance() == -1) {
            return new ArrayList<>();
        }
        return PlayerFilters.predicate(player1 -> PlayerUtils.getDistance(player, player1.getLocation()) > getDistance());
    }

}
