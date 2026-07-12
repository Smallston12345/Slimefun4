package net.guizhanss.slimefun4.utils;

import io.github.bakedlibs.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.bukkit.command.CommandSender;

/**
 * 聊天相關方法
 * @author ybw0014
 */
public class ChatUtils {
    private ChatUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 發送帶 Slimefun 前綴的消息
     * @param sender 消息接收人
     * @param message 消息
     */
    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(Slimefun.getLocalization().getChatPrefix() + ChatColors.color(message));
    }

    /**
     * 發送帶 Slimefun 前綴的消息
     * @param sender 消息接收人
     * @param message 消息
     * @param function 對消息進行處理的{@link Function}
     */
    public static void sendMessage(CommandSender sender, String message, UnaryOperator<String> function) {
        sendMessage(sender, function.apply(message));
    }
}
