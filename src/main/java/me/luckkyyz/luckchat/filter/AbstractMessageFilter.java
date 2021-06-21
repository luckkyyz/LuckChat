package me.luckkyyz.luckchat.filter;

import me.luckkyyz.luckapi.config.SettingConfig;
import me.luckkyyz.luckapi.util.permission.PermissionNode;
import me.luckkyyz.luckapi.util.permission.PermissionUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.function.Supplier;

public abstract class AbstractMessageFilter<T extends AbstractMessageFilter.AbstractConfig> implements MessageFilter {

    protected T config;

    protected AbstractMessageFilter(Supplier<T> configSupplier, SettingConfig settingConfig) {
        config = configSupplier.get();
        if(!config.load(settingConfig)) {
            throw new IllegalArgumentException("Some message filter was loaded incorrectly!");
        }
    }

    protected abstract MessageFilterRequest testing(AsyncPlayerChatEvent event);

    @Override
    public MessageFilterRequest test(AsyncPlayerChatEvent event) {
        if(!config.isEnabled()) {
            return new MessageFilterRequest(event);
        }
        if(config.isBypass() && PermissionUtil.hasPermission(event.getPlayer(), config.getBypassPermission())) {
            return new MessageFilterRequest(event);
        }
        return testing(event);
    }

    @Override
    public Config getConfig() {
        return config;
    }

    protected T getTypedConfig() {
        return config;
    }

    public abstract static class AbstractConfig implements Config {

        protected boolean enabled;
        protected boolean bypass;
        protected PermissionNode bypassPermission;

        protected AbstractConfig() {
        }

        protected abstract String getPath();

        protected abstract boolean loading(ConfigurationSection section);

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        @Override
        public boolean isBypass() {
            return bypass;
        }

        @Override
        public PermissionNode getBypassPermission() {
            return bypassPermission;
        }

        @Override
        public boolean load(SettingConfig config) {
            ConfigurationSection section = config.getSection(getPath());
            if(section == null) {
                return false;
            }
            enabled = section.getBoolean("enabled", false);
            bypass = section.getBoolean("bypass", false);
            bypassPermission = PermissionNode.node("LuckChat." + getPath() + ".bypass");
            return loading(section);
        }
    }
}
