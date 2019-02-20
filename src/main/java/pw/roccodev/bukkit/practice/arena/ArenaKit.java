package pw.roccodev.bukkit.practice.arena;

import org.bukkit.inventory.ItemStack;

public class ArenaKit {

    private String name;
    private boolean noHitDelay;

    private int hitDelay;

    private ItemStack[] inventory;

    public String getName() {
        return name;
    }

    public boolean hasNoHitDelay() {
        return noHitDelay;
    }

    public int getHitDelay() {
        return noHitDelay ? 20 : hitDelay;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }
}
