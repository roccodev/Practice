/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.commands;

import dev.rocco.bukkit.practice.arena.Arena;
import dev.rocco.bukkit.practice.arena.Arenas;
import dev.rocco.bukkit.practice.utils.GetPlayer;
import dev.rocco.bukkit.practice.utils.config.ConfigEntries;
import dev.rocco.bukkit.practice.utils.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
