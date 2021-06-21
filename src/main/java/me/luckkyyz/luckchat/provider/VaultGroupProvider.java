package me.luckkyyz.luckchat.provider;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

class VaultGroupProvider implements GroupProvider {

    private Permission permission;

    VaultGroupProvider() {
        permission = Bukkit.getServicesManager().load(Permission.class);
        if(permission == null) {
            throw new IllegalStateException("Vault has not loaded!");
        }
    }

    @Override
    public String getGroup(Player player) {
        return permission.getPrimaryGroup(player);
    }
}
