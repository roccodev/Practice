package pw.roccodev.bukkit.practice.arena;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerData {

    public static HashMap<Player, DeathInventoryData> deadInventories = new HashMap<>();

    static class InventoryData {

        private ItemStack[] contents, armorContents;
        private Player player;

        public InventoryData(Player player) {
            this.player = player;
            contents = player.getInventory().getContents();
            armorContents = player.getInventory().getArmorContents();
        }

        public ItemStack[] getArmorContents() {
            return armorContents;
        }

        public ItemStack[] getContents() {
            return contents;
        }
    }

    static class DeathInventoryData {

        private InventoryData player, killer;

        public DeathInventoryData(Player player, Player killer) {
            this.player = new InventoryData(player);
            if(killer != null) {
                this.killer = new InventoryData(killer);
            }
        }

        public InventoryData getPlayer() {
            return player;
        }

        public InventoryData getKiller() {
            return killer;
        }
    }

}
