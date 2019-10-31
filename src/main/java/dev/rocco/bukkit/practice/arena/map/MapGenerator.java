/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.arena.map;

import dev.rocco.bukkit.practice.PluginCompat;
import dev.rocco.bukkit.practice.arena.ArenaMap;
import dev.rocco.bukkit.practice.arena.Arenas;
import dev.rocco.bukkit.practice.utils.config.ConfigEntries;
import dev.rocco.bukkit.practice.utils.schema.SchematicUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.concurrent.ThreadLocalRandom;

public class MapGenerator {

    public static MapGenerationType type = MapGenerationType.RANDOM;

    public static void generateMap(ArenaMap map) {
        generateMap(map, type);
    }

    private static void generateMap(ArenaMap map, MapGenerationType type) {
        switch (type) {
            case RANDOM:
                generateRandom(map);
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
        int start = rand.nextInt(20, 6000000);
        int mult = rand.nextInt(1, 5);

        double total = start * mult;

        World w = Bukkit.getWorlds().get(0);
        Location target = new Location(w, total, ConfigEntries.ARENA_YLEVEL, total);

        SchematicUtils.pasteSchematic(map, target);

    }

}
