/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.commands;

import dev.rocco.bukkit.practice.PracticePlugin;
import dev.rocco.bukkit.practice.arena.ArenaKit;
import dev.rocco.bukkit.practice.arena.kit.KitParser;
import dev.rocco.bukkit.practice.utils.Prefix;
import dev.rocco.bukkit.practice.utils.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(Permission.INFO.assertHasPerm(sender)) return true;

        if(args.length == 0) {
            sender.sendMessage(Prefix.INFO + "Using Practice v" + PracticePlugin.INST.getDescription().getVersion()
                    + " by RoccoDev");
            sender.sendMessage(Prefix.INFO + "Info:§a https://rocco.dev/practice");
        }
        else {
            if(Permission.ADMIN.assertHasPerm(sender)) return true;
            String mode = args[0];
            if(mode.equalsIgnoreCase("newkit") && args.length == 2) {
                Player pSender = (Player) sender;
                String name = args[1];
                ArenaKit kit = new ArenaKit(name, pSender.getInventory().getContents(),
                        pSender.getInventory().getArmorContents());

                KitParser.writeKitToFile(kit);
            }
        }

        return true;
    }
}
