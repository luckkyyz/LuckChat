package me.luckkyyz.luckchat.provider;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public interface GroupProvider {

    static GroupProvider initialize() {
        if(Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            return new VaultGroupProvider();
        }
        return new GroupProviderImpl();
    }

    String getGroup(Player player);

}
