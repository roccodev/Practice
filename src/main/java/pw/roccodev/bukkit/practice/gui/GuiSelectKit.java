package pw.roccodev.bukkit.practice.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pw.roccodev.bukkit.practice.PracticePlugin;
import pw.roccodev.bukkit.practice.arena.Arena;
import pw.roccodev.bukkit.practice.arena.ArenaKit;
import pw.roccodev.bukkit.practice.arena.kit.Kits;

import java.util.ArrayList;
import java.util.List;

public class GuiSelectKit implements Listener {

    private Inventory guiInventory;
    private Player target;
    private int teams;

    public GuiSelectKit(Player target, int teams) {
        this.target = target;
        this.teams = teams;

        guiInventory = Bukkit.createInventory(null,
                9, "Duel " + target.getName()); // TODO Change
        List<ItemStack> contents = new ArrayList<>();

        for(ArenaKit kit : Kits.kits) {
            contents.add(kit.getIcon());
        }

        guiInventory.setContents(contents.toArray(new ItemStack[0]));
        
        Bukkit.getPluginManager().registerEvents(this, PracticePlugin.INST);
    }

    public void show(Player player) {
        player.openInventory(guiInventory);
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if(event.getClickedInventory().getTitle().startsWith("Duel ")) {
            event.setCancelled(true);
            ArenaKit kit = Kits.getByIcon(event.getCurrentItem());
            if (kit == null) return;
            Arena arena = new Arena(null, kit, teams);
            arena.invitePlayer(target, event.getWhoClicked().getName());
            arena.playerJoin((Player)event.getWhoClicked());

            event.getWhoClicked().closeInventory();
        }
    }


}
