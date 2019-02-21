package pw.roccodev.bukkit.practice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.roccodev.bukkit.practice.PracticePlugin;
import pw.roccodev.bukkit.practice.arena.Arena;
import pw.roccodev.bukkit.practice.arena.ArenaKit;
import pw.roccodev.bukkit.practice.arena.Arenas;
import pw.roccodev.bukkit.practice.arena.kit.KitParser;
import pw.roccodev.bukkit.practice.gui.GuiSelectKit;
import pw.roccodev.bukkit.practice.utils.GetPlayer;
import pw.roccodev.bukkit.practice.utils.Prefix;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;
import pw.roccodev.bukkit.practice.utils.permission.Permission;

public class DuelCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(Permission.DUEL.assertHasPerm(sender)) return true;
        if(!(sender instanceof Player)) return true;

        if(args.length == 1) {
            Player target = GetPlayer.get(sender, args[0]);
            if(target == null) return true;
            GuiSelectKit gsk = new GuiSelectKit(target, 2);
            gsk.show((Player) sender);
        }
        else if(args.length == 2){
            String mode = args[0];
            if(mode.equalsIgnoreCase("accept")) {
                Player target = GetPlayer.get(sender, args[1]);
                if(target == null) return true;

                Arena arena = Arenas.getByPlayer(target);
                if(arena == null) {
                    ConfigEntries.formatAndSend(sender, ConfigEntries.E_NOTINARENA, target.getName());
                    return true;
                }
                if(!arena.getInvited().contains(sender)) {
                    ConfigEntries.formatAndSend(sender, ConfigEntries.E_PERM, "Arena_Join." + arena.getId());
                    return true;
                }

                arena.playerJoin((Player) sender);
            }
        }

        return true;
    }
}
