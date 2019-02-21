package pw.roccodev.bukkit.practice.arena.kit;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pw.roccodev.bukkit.practice.PracticePlugin;
import pw.roccodev.bukkit.practice.arena.ArenaKit;
import pw.roccodev.bukkit.practice.utils.item.ItemSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class KitParser {

    private YamlConfiguration yaml;
    private String fileName;

    public ArenaKit parse() {

        boolean hitDelay = yaml.getBoolean("hasnohitdelay");
        int hitDelayValue = yaml.contains("hitdelay") ? yaml.getInt("hitdelay") : 20;

        String name = yaml.getString("name");
        String description = yaml.getString("description");

        ItemStack icon = ItemSerializer.deserialize(yaml.getString("icon"));

        List<ItemStack> items = (List<ItemStack>) yaml.getList("items");
        List<ItemStack> armor = (List<ItemStack>) yaml.getList("armor");

        return new ArenaKit(name, description, hitDelay, icon, hitDelayValue, items.toArray(new ItemStack[0]),
                armor.toArray(new ItemStack[0]), fileName);

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

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
