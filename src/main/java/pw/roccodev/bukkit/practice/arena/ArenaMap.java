package pw.roccodev.bukkit.practice.arena;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public class ArenaMap {

    private String name;
    private World world;

    private List<Location> spawnPoints;

    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    public List<Location> getSpawnPoints() {
        return spawnPoints;
    }
}
