package me.luckkyyz.luckchat.command;

import me.luckkyyz.luckapi.command.ChatCommand;
import me.luckkyyz.luckapi.command.ExecutingChecks;
import me.luckkyyz.luckapi.command.ExecutingStrategy;
import me.luckkyyz.luckapi.config.SettingConfig;
import me.luckkyyz.luckapi.util.player.PlayerFilters;
import me.luckkyyz.luckchat.chat.DistanceChatManager;
import me.luckkyyz.luckchat.chat.spy.ChatSpyingManager;
import me.luckkyyz.luckchat.filter.MessageFilters;
import me.luckkyyz.luckchat.util.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LuckChatCommand {

    public LuckChatCommand(SettingConfig config, MessageFilters filters, DistanceChatManager distanceChatManager, ChatSpyingManager chatSpyingManager) {
        ChatCommand.registerCommand("luckchat", ExecutingStrategy.newBuilder()
                .subCommandStrategy()
                .addCheck(ExecutingChecks.permission(Permissions.LUCK_CHAT_COMMAND), session -> session.send("&cУ Вас нет прав!"))
                .whenArgumentAbsent(session -> session.getExecutor().send(
                        "&7[&cLuck&fChat&7] &fПомощь по командам:",
                        "&7/luckchat reload &7&l- &fперезагрузить плагин",
                        "&7/luckchat clear &7&l- &fочистить чат",
                        "&7/luckchat spy &7&l- &fпрослушка чата"))
                .whenSubCommandAbsent(session -> session.send("&cТакой команды не существует! Используйте &f/luckchat &cдля помощи."))
                .addSubCommand(ExecutingStrategy.newBuilder()
                        .commandStrategy()
                        .addCheck(ExecutingChecks.permission(Permissions.LUCK_CHAT_RELOAD_COMMAND), session -> session.send("&cУ Вас нет прав!"))
                        .addAction(session -> {
                            config.reload();
                            filters.getMessageFilters().forEach(filter -> {
                                if(!filter.getConfig().load(config)) {
                                    session.send("&cУпс.. Что-то пошло не так, проверьте конфиг!");
                                }
                            });
                            distanceChatManager.reload();
                            session.send("&fВы успешно перезагрузили плагин!");
                        }), "reload", "restart"
                ).addSubCommand(ExecutingStrategy.newBuilder()
                        .commandStrategy()
                        .addCheck(ExecutingChecks.permission(Permissions.LUCK_CHAT_CLEAR_COMMAND), session -> session.send("&cУ Вас нет прав!"))
                        .addAction(session -> {
                            PlayerFilters.online().forEach(player -> {
                                for(int i = 0; i < 100; i++) {
                                    player.sendMessage("");
                                }
                                player.sendMessage(ChatColor.WHITE + "Чат был очищен!");
                            });
                            session.send("&fВы успешно очистили чат!");
                        }), "clear", "clean"
                ).addSubCommand(ExecutingStrategy.newBuilder()
                        .commandStrategy()
                        .addCheck(ExecutingChecks.player(), session -> session.send("&cЭта команда только для игроков!"))
                        .addCheck(ExecutingChecks.permission(Permissions.LUCK_CHAT_SPY_COMMAND), session -> session.send("&cУ Вас нет прав!"))
                        .addAction(session -> {
                            Player player = session.getExecutor().getPlayer();
                            if(chatSpyingManager.isSpying(player)) {
                                chatSpyingManager.removeSpying(player);
                                session.send("&fВы успешно отключили прослушку чата!");
                            } else {
                                chatSpyingManager.addSpying(player);
                                session.send("&fВы успешно включили прослушку чата!");
                            }
                        }), "spy"
                )
        );
    }

}
