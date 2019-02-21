package pw.roccodev.bukkit.practice.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pw.roccodev.bukkit.practice.arena.Arena;
import pw.roccodev.bukkit.practice.arena.ArenaKit;
import pw.roccodev.bukkit.practice.arena.kit.Kits;

import java.util.ArrayList;
import java.util.List;

public class GuiSelectKit implements Listener {

    private static Inventory guiInventory;

    public static void init() {
        guiInventory = Bukkit.createInventory(null,
                9); // TODO Change
        List<ItemStack> contents = new ArrayList<>();

        for(ArenaKit kit : Kits.kits) {
            contents.add(kit.getIcon());
        }

        guiInventory.setContents(contents.toArray(new ItemStack[0]));
    }

    public static void show(Player player) {
        player.openInventory(guiInventory);
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        ArenaKit kit = Kits.getByIcon(event.getCurrentItem());
        if(kit == null) return;
        Arena arena = new Arena(null, kit, 2);
    }


}
