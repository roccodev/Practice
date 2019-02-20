package pw.roccodev.bukkit.practice.utils.config;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigEntries {

    public static String INFO_PREFIX, ERROR_PREFIX;

    public static String E_PERM, E_404;

    public static String PING_RESULT;


    public static void formatAndSend(CommandSender sendTo, String in, Object... values) {
        sendTo.sendMessage(String.format(in, values));
    }

    private static String c(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static void init(FileConfiguration config) {
        INFO_PREFIX = c(config.getString("prefix.info"));
        ERROR_PREFIX = c(config.getString("prefix.error"));

        E_PERM = ERROR_PREFIX + c(config.getString("error.permission"));
        E_404 = ERROR_PREFIX + c(config.getString("error.notfound"));

        PING_RESULT = INFO_PREFIX + c(config.getString("ping.result"));
    }
}
