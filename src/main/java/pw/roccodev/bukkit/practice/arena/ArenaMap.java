package pw.roccodev.bukkit.practice.arena;

import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.util.List;

public class ArenaMap {

    private String name;
    private World world;
    private File schemFile;

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

    public ArenaMap(File schem) {
        this.schemFile = schem;
        this.name = schem.getName().split("\\.")[0];
    }

    public String getSchemFile() {
        return schemFile.getName();
    }
}
