package pw.roccodev.bukkit.practice.arena;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import pw.roccodev.bukkit.practice.PracticePlugin;
import pw.roccodev.bukkit.practice.arena.kit.KitParser;
import pw.roccodev.bukkit.practice.arena.kit.Kits;
import pw.roccodev.bukkit.practice.gui.GuiSelectKit;

import java.io.File;
import java.io.IOException;

public class FileLoader {

    private static void loadAllKits() throws IOException, InvalidConfigurationException {
        File kitsDir = new File(PracticePlugin.PLUGIN_DIR + "kits");
        if(!kitsDir.exists()) kitsDir.mkdirs();

        for(File kitFile : kitsDir.listFiles()) {

            if(!kitFile.getName().endsWith(".yml")) continue;

            YamlConfiguration config = new YamlConfiguration();
            config.load(kitFile);
            KitParser parser = new KitParser(kitFile.getName(), config);
            Kits.kits.add(parser.parse());
        }

        GuiSelectKit.init();

    }

    public static void loadEverything() {
        try {
           loadAllKits();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
