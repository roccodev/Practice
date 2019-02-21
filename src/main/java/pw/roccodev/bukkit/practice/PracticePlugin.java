package pw.roccodev.bukkit.practice;

import org.bukkit.plugin.java.JavaPlugin;
import pw.roccodev.bukkit.practice.arena.FileLoader;
import pw.roccodev.bukkit.practice.commands.PingCommand;
import pw.roccodev.bukkit.practice.commands.SpectateCommand;
import pw.roccodev.bukkit.practice.utils.FileCheck;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;
import pw.roccodev.bukkit.practice.utils.metrics.MetricsLite;

import java.io.File;

public class PracticePlugin extends JavaPlugin {

    public static File PLUGIN_DIR;

    @Override
    public void onEnable() {

        PLUGIN_DIR = getDataFolder();
        FileCheck.initDirs();

        ConfigEntries.init(getConfig());
        saveDefaultConfig();

        /* Load kits, maps, etc. */
        FileLoader.loadEverything();

        getCommand("ping").setExecutor(new PingCommand());
        getCommand("spectate").setExecutor(new SpectateCommand());

        new MetricsLite(this); /* Metrics */
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
