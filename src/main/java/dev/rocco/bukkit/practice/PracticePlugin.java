/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice;

import dev.rocco.bukkit.practice.arena.FileLoader;
import dev.rocco.bukkit.practice.arena.listener.PlayerListener;
import dev.rocco.bukkit.practice.commands.*;
import dev.rocco.bukkit.practice.stats.SQL;
import dev.rocco.bukkit.practice.stats.SQLiteStatsManager;
import dev.rocco.bukkit.practice.stats.StatsManager;
import dev.rocco.bukkit.practice.utils.FileCheck;
import dev.rocco.bukkit.practice.utils.config.ConfigEntries;
import dev.rocco.bukkit.practice.utils.metrics.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PracticePlugin extends JavaPlugin {

    public static File PLUGIN_DIR;
    public static PracticePlugin INST;
    public static StatsManager STATS_MGR;

    @Override
    public void onEnable() {

        PLUGIN_DIR = getDataFolder();
        INST = this;
        FileCheck.initDirs();

        ConfigEntries.init(getConfig());
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveDefaultConfig();

        /* Load kits, maps, etc. */
        FileLoader.loadEverything();

        getCommand("practice").setExecutor(new AdminCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("spectate").setExecutor(new SpectateCommand());
        getCommand("duel").setExecutor(new DuelCommand());
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("rtp").setExecutor(new ReportTPCommand());
        getCommand("stats").setExecutor(new StatsCommand());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListener(), this);

        new MetricsLite(this); /* Metrics */

        PluginCompat.check();

        STATS_MGR = new SQLiteStatsManager();
        SQL.load();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
