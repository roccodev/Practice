package pw.roccodev.bukkit.practice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.roccodev.bukkit.practice.utils.permission.Permission;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(Permission.PING.assertHasPerm(sender)) return true;

        if(args.length != 0 && Permission.PING_OTHERS.has(sender)) {

        }
        else {

        }
        return true;
    }

    private String buildPing(Player player) {
        return "";
    }
}
