package pw.roccodev.bukkit.practice;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pw.roccodev.bukkit.practice.arena.FileLoader;
import pw.roccodev.bukkit.practice.arena.listener.PlayerListener;
import pw.roccodev.bukkit.practice.commands.*;
import pw.roccodev.bukkit.practice.utils.FileCheck;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;
import pw.roccodev.bukkit.practice.utils.metrics.MetricsLite;

import java.io.File;

public class PracticePlugin extends JavaPlugin {

    public static File PLUGIN_DIR;
    public static PracticePlugin INST;

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

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListener(), this);

        new MetricsLite(this); /* Metrics */

        PluginCompat.check();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
