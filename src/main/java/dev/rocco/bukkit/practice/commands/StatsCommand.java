package dev.rocco.bukkit.practice.commands;

import dev.rocco.bukkit.practice.PracticePlugin;
import dev.rocco.bukkit.practice.stats.StatsProfile;
import dev.rocco.bukkit.practice.utils.Prefix;
import dev.rocco.bukkit.practice.utils.config.ConfigEntries;
import dev.rocco.bukkit.practice.utils.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(Permission.STATS.assertHasPerm(sender)) return true;

        OfflinePlayer player;

        if(args.length != 0 && Permission.STATS_OTHERS.has(sender)) {
            player = Bukkit.getOfflinePlayer(args[0]);
        }
        else if(sender instanceof Player) {
            player = (OfflinePlayer) sender;
        }
        else return true;
        if(player == null) return true;

        StatsProfile profile = PracticePlugin.STATS_MGR.buildProfile(player.getUniqueId().toString().replace("-", ""));
        if(profile == null) {
            ConfigEntries.formatAndSend(sender, ConfigEntries.E_404, args[0]);
            return true;
        }

        ConfigEntries.formatAndSend(sender, ConfigEntries.STATS_OVERVIEW,
                profile.getKills(), profile.getDeaths(), profile.getVictories(), profile.getPlayed(),
                profile.getForfeits(), player.getName(), Prefix.INFO);

        return true;
    }

}
