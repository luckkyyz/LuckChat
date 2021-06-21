package me.luckkyyz.luckchat;

import me.luckkyyz.luckapi.config.SettingConfig;
import me.luckkyyz.luckchat.chat.DistanceChatManager;
import me.luckkyyz.luckchat.chat.spy.ChatSpyingManager;
import me.luckkyyz.luckchat.command.LuckChatCommand;
import me.luckkyyz.luckchat.filter.MessageFilters;
import me.luckkyyz.luckchat.filter.delay.DelayMessageFilter;
import me.luckkyyz.luckchat.filter.question.QuestionMessageFilter;
import me.luckkyyz.luckchat.filter.word.WordMessageFilter;
import me.luckkyyz.luckchat.provider.GroupProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "LuckChat", version = "1.0.0-SNAPSHOT")
@Author("luckkyyz")
@SoftDependency("LuckApi")
@SoftDependency("Vault")
@ApiVersion(ApiVersion.Target.v1_16)
@Commands(
        @Command(name = "luckchat")
)
public final class LuckChatPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        SettingConfig config = new SettingConfig(this);

        MessageFilters filters = new MessageFilters(this);
        filters.registerMessageFilter(new DelayMessageFilter(config));
        filters.registerMessageFilter(new WordMessageFilter(config));
        filters.registerMessageFilter(new QuestionMessageFilter(config));

        GroupProvider groupProvider = GroupProvider.initialize();
        DistanceChatManager distanceChatManager = new DistanceChatManager(this, config, groupProvider);

        ChatSpyingManager chatSpyingManager = new ChatSpyingManager(this);

        new LuckChatCommand(config, filters, distanceChatManager, chatSpyingManager);
    }

}
