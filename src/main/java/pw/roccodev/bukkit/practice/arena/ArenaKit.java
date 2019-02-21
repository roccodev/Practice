package pw.roccodev.bukkit.practice.arena;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class ArenaKit {

    private String name, description;

    private boolean noHitDelay;
    private ItemStack icon;

    private int hitDelay;

    private String fileName;

    private ItemStack[] inventory;
    private ItemStack[] armor;

    public String getName() {
        return name;
    }

    public ArenaKit(String name, String description, boolean noHitDelay, ItemStack icon, int hitDelay, ItemStack[] inventory,
                    ItemStack[] armor, String fileName) {
        this.name = name;
        this.description = description;
        this.noHitDelay = noHitDelay;
        this.icon = icon;
        this.hitDelay = hitDelay;
        this.inventory = inventory;
        this.fileName = fileName;
        this.armor = armor;

        initIcon();
    }

    private void initIcon() {
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(description.split("\n")));
        icon.setItemMeta(meta);
    }

    public void apply(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.setContents(inventory);
        inv.setArmorContents(armor);

        player.updateInventory();
    }


    public boolean hasNoHitDelay() {
        return noHitDelay;
    }

    public int getHitDelay() {
        return noHitDelay ? 20 : hitDelay;
    }

    public boolean isPots() {
        return Arrays.stream(inventory).anyMatch(item ->
                item.getType() == Material.POTION && ((PotionMeta)item.getItemMeta()).hasCustomEffect(PotionEffectType.HEAL));
    }

    public boolean isSoups() {
        return Arrays.stream(inventory).anyMatch(item ->
                item.getType() == Material.MUSHROOM_SOUP);
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public ItemStack[] getArmor() {
        return armor;
    }
}
