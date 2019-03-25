package dev.rocco.bukkit.practice.arena.kit;

import dev.rocco.bukkit.practice.PracticePlugin;
import dev.rocco.bukkit.practice.arena.ArenaKit;
import dev.rocco.bukkit.practice.utils.item.ItemSerializer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class KitParser {

    private YamlConfiguration yaml;
    private String fileName;

    public ArenaKit parse() {

        boolean hitDelay = yaml.getBoolean("hasnohitdelay");
        int hitDelayValue = yaml.contains("hitdelay") ? yaml.getInt("hitdelay") : 20;

        String name = ChatColor.translateAlternateColorCodes('&', yaml.getString("name"));
        String description = ChatColor.translateAlternateColorCodes('&', yaml.getString("description"));

        ItemStack icon = ItemSerializer.deserialize(yaml.getString("icon"));

        List<ItemStack> items = (List<ItemStack>) yaml.getList("items");
        List<ItemStack> armor = (List<ItemStack>) yaml.getList("armor");

        boolean hunger = yaml.getBoolean("hunger", true);
        int maxHealth = yaml.getInt("maxhealth", 20);

        return new ArenaKit(name, description, hitDelay, icon, hitDelayValue, items.toArray(new ItemStack[0]),
                armor.toArray(new ItemStack[0]), fileName, hunger, maxHealth);

    }

    public KitParser(String fileName, YamlConfiguration file) {
        yaml = file;
        this.fileName = fileName;
    }

    public static void writeKitToFile(ArenaKit kit) {
        File file = new File(PracticePlugin.PLUGIN_DIR + "/kits/" + kit.getFileName());
        YamlConfiguration config = new YamlConfiguration();

        config.set("name", kit.getName());
        config.set("description", kit.getDescription());
        config.set("icon", ItemSerializer.serialize(kit.getIcon()));
        config.set("items", kit.getInventory());
        config.set("armor", kit.getArmor());
        config.set("hasnohitdelay", kit.hasNoHitDelay());
        config.set("hitdelay", kit.getHitDelay());

        config.set("hunger", kit.isHungerEnabled());
        config.set("maxhealth", kit.getMaxHealth());

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
