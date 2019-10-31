/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginCompat {

    public static boolean we = false; /* WorldEdit support */

    public static WorldEditPlugin worldEditPlugin;

    public static void check() {
        Plugin we = Bukkit.getPluginManager().getPlugin("WorldEdit");
        if(we instanceof WorldEditPlugin) {
            PluginCompat.we = true;
            worldEditPlugin = (WorldEditPlugin) we;
            Bukkit.getLogger().info("Successfully enabled WorldEdit support.");
        }
        else Bukkit.getLogger().warning("WorldEdit wasn't found, so the 'Random' arena generation type is unavailable.");
    }

}
