/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.commands;

import dev.rocco.bukkit.practice.arena.Arena;
import dev.rocco.bukkit.practice.arena.Arenas;
import dev.rocco.bukkit.practice.arena.DeathMessage;
import dev.rocco.bukkit.practice.gui.GuiSelectKit;
import dev.rocco.bukkit.practice.utils.GetPlayer;
import dev.rocco.bukkit.practice.utils.config.ConfigEntries;
import dev.rocco.bukkit.practice.utils.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            else if(mode.equalsIgnoreCase("inventory")) {
                Player target = GetPlayer.get(sender, args[1]);
                if(target == null) return true;

                DeathMessage.showInventory(target, (Player) sender);
            }
        }

        return true;
    }
}
