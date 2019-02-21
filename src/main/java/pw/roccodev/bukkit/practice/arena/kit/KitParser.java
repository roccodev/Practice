package pw.roccodev.bukkit.practice.arena.kit;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pw.roccodev.bukkit.practice.arena.ArenaKit;
import pw.roccodev.bukkit.practice.utils.item.ItemSerializer;

import java.util.List;

public class KitParser {

    private YamlConfiguration yaml;
    private String fileName;

    public ArenaKit parse() {

        boolean hitDelay = yaml.getBoolean("hashitdelay");
        int hitDelayValue = yaml.contains("hitdelay") ? yaml.getInt("hitdelay") : 20;

        String name = yaml.getString("name");
        String description = yaml.getString("description");

        ItemStack icon = ItemSerializer.deserialize(yaml.getString("icon"));

        List<ItemStack> items = (List<ItemStack>) yaml.getList("items");

        return new ArenaKit(name, description, hitDelay, icon, hitDelayValue, items.toArray(new ItemStack[0]), fileName);

    }

    public KitParser(String fileName, YamlConfiguration file) {
        yaml = file;
        this.fileName = fileName;
    }

}
