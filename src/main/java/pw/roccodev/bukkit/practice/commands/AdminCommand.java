package pw.roccodev.bukkit.practice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pw.roccodev.bukkit.practice.PracticePlugin;
import pw.roccodev.bukkit.practice.utils.Prefix;
import pw.roccodev.bukkit.practice.utils.permission.Permission;

public class AdminCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(Permission.INFO.assertHasPerm(sender)) return true;

        if(args.length == 0) {
            sender.sendMessage(Prefix.INFO + "Using Practice v" + PracticePlugin.INST.getDescription().getVersion()
                    + " by RoccoDev");
            sender.sendMessage(Prefix.INFO + "Info:§a https://roccodev.pw/practice");
        }
        else {
            if(Permission.ADMIN.assertHasPerm(sender)) return true;
            String mode = args[0];
        }

        return true;
    }
}
