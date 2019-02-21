package pw.roccodev.bukkit.practice.utils.config;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import pw.roccodev.bukkit.practice.arena.kit.KitDispatcherType;

public class ConfigEntries {

    public static String INFO_PREFIX, ERROR_PREFIX;

    public static String E_PERM, E_404, E_KICK;

    public static String PING_RESULT;

    public static double ARENA_YLEVEL;
    public static boolean ARENA_SPEC_FLIGHT, ARENA_SPEC_FLIGHTON;

    public static KitDispatcherType ARENA_KIT_RESET;



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
        E_KICK = c(config.getString("error.arena.kick"));

        PING_RESULT = INFO_PREFIX + c(config.getString("ping.result"));

        ARENA_YLEVEL = config.getDouble("arena.ylevel");

        ARENA_SPEC_FLIGHT = config.getBoolean("arena.spectator.allowflight");
        ARENA_SPEC_FLIGHTON = config.getBoolean("arena.spectator.flyondeath");

        ARENA_KIT_RESET = KitDispatcherType.valueOf(config.getString("arena.kit.reset").toUpperCase());


    }
}
