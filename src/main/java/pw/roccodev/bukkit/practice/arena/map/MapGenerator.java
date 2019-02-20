package pw.roccodev.bukkit.practice.arena.map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pw.roccodev.bukkit.practice.PluginCompat;
import pw.roccodev.bukkit.practice.arena.ArenaMap;
import pw.roccodev.bukkit.practice.arena.Arenas;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;
import pw.roccodev.bukkit.practice.utils.schema.SchematicUtils;

import java.util.concurrent.ThreadLocalRandom;

public class MapGenerator {

    public static MapGenerationType type = MapGenerationType.RANDOM;

    public static void generateMap(ArenaMap map) {
        generateMap(map, type);
    }

    private static void generateMap(ArenaMap map, MapGenerationType type) {
        switch (type) {
            case RANDOM:
                generateMap(map);
                break;
            case WORLD:
                if(Arenas.getByWorldAndPlaying(map.getWorld()) != null) {

                }
                else {

                }
                break;
        }
    }

    public static void generateRandom(ArenaMap map) {
        if(!PluginCompat.we) {
            Bukkit.getLogger().severe("WorldEdit not found, can't use Random generation!");
            generateMap(map, MapGenerationType.WORLD);
            return;
        }
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int start = rand.nextInt(20, 50000000);
        int mult = rand.nextInt(1, 5);

        double total = start * mult;

        World w = map.getWorld();
        Location target = new Location(w, total, ConfigEntries.ARENA_YLEVEL, total);

        SchematicUtils.pasteSchematic(map.getName(), target, w);

    }

}
