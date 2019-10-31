/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.utils;

import dev.rocco.bukkit.practice.utils.config.ConfigEntries;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
