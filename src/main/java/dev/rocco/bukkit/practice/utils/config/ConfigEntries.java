/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.utils.config;

import dev.rocco.bukkit.practice.arena.kit.KitDispatcherType;
import dev.rocco.bukkit.practice.stats.ManagerType;
import dev.rocco.bukkit.practice.utils.Prefix;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Locale;

public class ConfigEntries {

    public static String INFO_PREFIX, ERROR_PREFIX;

    public static String E_PERM, E_404, E_KICK, E_NOTINARENA;

    public static ManagerType DATABASE_ENGINE;
    public static String DATABASE_HOST;
    public static int DATABASE_PORT;
    public static String DATABASE_NAME;
    public static String DATABASE_USER;
    public static String DATABASE_KEY;

    public static String PING_RESULT;

    public static double ARENA_YLEVEL;
    public static boolean ARENA_SPEC_FLIGHT, ARENA_SPEC_FLIGHTON, ARENA_SPEC_INTERACT, ARENA_PLAYER_INTERACT, ARENA_DEATH_CLICK;
    public static String ARENA_INVITE, ARENA_JOIN, ARENA_LEAVE, ARENA_SPECJOIN, ARENA_SPECLEAVE, ARENA_DEATH, ARENA_DEATH_U,
                         ARENA_END;

    public static KitDispatcherType ARENA_KIT_RESET;

    public static String REPORT_NOTIFICATION, REPORT_COOLDOWN_MESSAGE, REPORT_SUCCESSFUL;
    public static long REPORT_COOLDOWN;

    public static String STATS_OVERVIEW;

    public static long QUEUE_TIMEOUT, QUEUE_ELO_SHIFT;
    public static String QUEUE_JOIN, QUEUE_LEAVE, QUEUE_SEARCH_ELO, QUEUE_UPDATE_ELO;

    public static void formatAndSend(CommandSender sendTo, String in, Object... values) {
        sendTo.sendMessage(String.format(in, values));
    }

    private static String c(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static void init(FileConfiguration config) {
        INFO_PREFIX = c(config.getString("prefix.info"));
        ERROR_PREFIX = c(config.getString("prefix.error"));

        Prefix.INFO = INFO_PREFIX;
        Prefix.ERROR = ERROR_PREFIX;

        E_PERM = ERROR_PREFIX + c(config.getString("error.permission"));
        E_404 = ERROR_PREFIX + c(config.getString("error.notfound"));
        E_KICK = ERROR_PREFIX + c(config.getString("error.arena.kick"));
        E_NOTINARENA = ERROR_PREFIX + c(config.getString("error.arena.playernotfound"));

        PING_RESULT = INFO_PREFIX + c(config.getString("ping.result"));

        ARENA_YLEVEL = config.getDouble("arena.ylevel");

        ARENA_SPEC_FLIGHT = config.getBoolean("arena.spectator.allowflight");
        ARENA_SPEC_FLIGHTON = config.getBoolean("arena.spectator.flyondeath");
        ARENA_DEATH_CLICK = config.getBoolean("arena.death.inventory");
        ARENA_SPEC_INTERACT = config.getBoolean("arena.spectator.interact");
        ARENA_PLAYER_INTERACT = config.getBoolean("arena.interact.player");

        ARENA_KIT_RESET = KitDispatcherType.valueOf(config.getString("arena.kit.reset").toUpperCase());

        ARENA_INVITE = INFO_PREFIX + c(config.getString("arena.invite"));
        ARENA_JOIN = INFO_PREFIX + c(config.getString("arena.join"));
        ARENA_LEAVE = INFO_PREFIX + c(config.getString("arena.leave"));
        ARENA_SPECJOIN = INFO_PREFIX + c(config.getString("arena.spectator.join"));
        ARENA_SPECLEAVE = INFO_PREFIX + c(config.getString("arena.spectator.leave"));
        ARENA_DEATH = INFO_PREFIX + c(config.getString("arena.death.player"));
        ARENA_DEATH_U = INFO_PREFIX + c(config.getString("arena.death.unknown"));
        ARENA_END = INFO_PREFIX + c(config.getString("arena.end"));

        REPORT_NOTIFICATION = INFO_PREFIX + c(config.getString("report.notification"));
        REPORT_COOLDOWN_MESSAGE = INFO_PREFIX + c(config.getString("report.cooldown.error"));
        REPORT_SUCCESSFUL = INFO_PREFIX + c(config.getString("report.successful"));

        REPORT_COOLDOWN = config.getInt("report.cooldown.amount");

        STATS_OVERVIEW = INFO_PREFIX + c(config.getString("stats.overview"));

        QUEUE_TIMEOUT = config.getLong("queue.ranked.timeout");
        QUEUE_ELO_SHIFT = config.getLong("queue.ranked.elo_shift");
        QUEUE_JOIN = INFO_PREFIX + c(config.getString("queue.join"));
        QUEUE_LEAVE = INFO_PREFIX + c(config.getString("queue.leave"));
        QUEUE_SEARCH_ELO = INFO_PREFIX + c(config.getString("queue.elo_search"));
        QUEUE_UPDATE_ELO = INFO_PREFIX + c(config.getString("queue.elo_update"));

        DATABASE_ENGINE = ManagerType.valueOf(config.getString("database.engine").toUpperCase(Locale.ROOT));
        DATABASE_HOST = config.getString("database.host");
        DATABASE_NAME = config.getString("database.name");
        DATABASE_USER = config.getString("database.user");
        DATABASE_PORT = config.getInt("database.port");
        DATABASE_KEY = config.getString("database.key");
    }
}
