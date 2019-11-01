/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.arena.kit;

import dev.rocco.bukkit.practice.arena.ArenaKit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Locale;

public class Kits {
    public static HashSet<ArenaKit> kits = new HashSet<>();

    public static ArenaKit getByIcon(ItemStack item) {
        for(ArenaKit kit : kits) {
            if(kit.getIcon().equals(item)) return kit;
        }
        return null;
    }

    public static ArenaKit getByName(String name) {
        for(ArenaKit kit : kits) {
            if(ChatColor.stripColor(kit.getName().toLowerCase(Locale.ROOT)).equals(name.toLowerCase(Locale.ROOT))) return kit;
        }
        return null;
    }
}
