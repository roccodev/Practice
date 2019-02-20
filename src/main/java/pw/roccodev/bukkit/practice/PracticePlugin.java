package pw.roccodev.bukkit.practice;

import org.bukkit.plugin.java.JavaPlugin;
import pw.roccodev.bukkit.practice.commands.PingCommand;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;

public class PracticePlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        ConfigEntries.init(getConfig());
        saveDefaultConfig();

        getCommand("ping").setExecutor(new PingCommand());
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
