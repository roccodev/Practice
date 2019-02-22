package pw.roccodev.bukkit.practice.arena;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import pw.roccodev.bukkit.practice.PracticePlugin;
import pw.roccodev.bukkit.practice.arena.kit.KitParser;
import pw.roccodev.bukkit.practice.arena.kit.Kits;
import pw.roccodev.bukkit.practice.arena.map.Maps;
import pw.roccodev.bukkit.practice.arena.proto.TeamPrototype;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileLoader {

    private static void loadAllKits() throws IOException, InvalidConfigurationException {
        File kitsDir = new File(PracticePlugin.PLUGIN_DIR + "/kits");
        if(!kitsDir.exists()) kitsDir.mkdirs();

        for(File kitFile : kitsDir.listFiles()) {

            if(!kitFile.getName().endsWith(".yml")) continue;

            YamlConfiguration config = new YamlConfiguration();
            config.load(kitFile);
            KitParser parser = new KitParser(kitFile.getName(), config);
            Kits.kits.add(parser.parse());
        }

    }

    private static void loadAllMaps() {
        File mapsDir = new File(PracticePlugin.PLUGIN_DIR + "/maps");
        if(!mapsDir.exists()) mapsDir.mkdirs();

        for(File mapFile : mapsDir.listFiles()) {

            if (!mapFile.getName().endsWith(".schematic")) continue;
            ArenaMap map = new ArenaMap(mapFile);
            Maps.maps.add(map);
        }
    }

    private static void loadTeams() throws IOException {
        File teamsFile = new File(PracticePlugin.PLUGIN_DIR + "/teams.yml");
        if(!teamsFile.exists()) {
            teamsFile.createNewFile();
        }
        YamlConfiguration config = new YamlConfiguration();
        config.options().copyDefaults(true);
        config.addDefault("teams", Arrays.asList("&cRed", "&eYellow", "&aGreen"));
        config.save(teamsFile);

        for(String s : config.getStringList("teams")) {
            new TeamPrototype(s);
        }


    }

    public static void loadEverything() {
        try {
           loadAllKits();
           loadAllMaps();
           loadTeams();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
