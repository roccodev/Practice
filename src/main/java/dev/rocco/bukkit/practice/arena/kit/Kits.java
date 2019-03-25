package dev.rocco.bukkit.practice.arena.kit;

import dev.rocco.bukkit.practice.arena.ArenaKit;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class Kits {
    public static HashSet<ArenaKit> kits = new HashSet<>();

    public static ArenaKit getByIcon(ItemStack item) {
        for(ArenaKit kit : kits) {
            if(kit.getIcon().equals(item)) return kit;
        }
        return null;
    }
}
