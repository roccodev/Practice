package pw.roccodev.bukkit.practice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pw.roccodev.bukkit.practice.utils.GetPlayer;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;
import pw.roccodev.bukkit.practice.utils.permission.Permission;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(Permission.PING.assertHasPerm(sender)) return true;

        Player player;

        if(args.length != 0 && Permission.PING_OTHERS.has(sender)) {
            player = GetPlayer.get(sender, args[0]);
        }
        else if(sender instanceof Player) {
            player = (Player) sender;
        }
        else return true;
        if(player == null) return true;
        sender.sendMessage(buildPing(player));
        return true;
    }

    private String buildPing(Player player) {
        int ping = ((CraftPlayer)player).getHandle().ping;
        return String.format(ConfigEntries.PING_RESULT, player.getName(), ping);
    }
}
