package pw.roccodev.bukkit.practice.arena.kit;

import org.bukkit.inventory.ItemStack;
import pw.roccodev.bukkit.practice.arena.ArenaKit;

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
