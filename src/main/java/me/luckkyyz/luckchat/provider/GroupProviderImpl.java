package me.luckkyyz.luckchat.provider;

import org.bukkit.entity.Player;

class GroupProviderImpl implements GroupProvider {

    GroupProviderImpl() {
    }

    @Override
    public String getGroup(Player player) {
        return "default";
    }
}
