package pw.roccodev.bukkit.practice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pw.roccodev.bukkit.practice.arena.Arena;
import pw.roccodev.bukkit.practice.arena.Arenas;
import pw.roccodev.bukkit.practice.utils.GetPlayer;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;
import pw.roccodev.bukkit.practice.utils.permission.Permission;

public class SpectateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(Permission.SPECTATE.assertHasPerm(sender)) return true;
        if(!(sender instanceof Player)) return true;

        Player pSender = (Player) sender;

        if(args.length == 1) {
            Player player = GetPlayer.get(sender, args[0]);
            if(player == null) return true;
            Arena arena = Arenas.getByPlayer(player);
            if(arena == null) {
                ConfigEntries.formatAndSend(sender, ConfigEntries.E_NOTINARENA, player.getName());
                return true;
            }

            arena.spectate(pSender);
            pSender.teleport(player);

        }
        return true;
    }


}
