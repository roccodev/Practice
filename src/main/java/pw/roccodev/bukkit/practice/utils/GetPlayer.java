package pw.roccodev.bukkit.practice.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;

public class GetPlayer {
    public static Player get(CommandSender sender, String name) {
        Player res = Bukkit.getPlayer(name);
        if(res == null) {
            ConfigEntries.formatAndSend(sender, ConfigEntries.E_404, name);
            return null;
        }
        else return res;
    }

}
