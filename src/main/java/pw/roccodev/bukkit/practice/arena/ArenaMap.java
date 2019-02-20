package pw.roccodev.bukkit.practice.arena;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ArenaMap {

    private String name;
    private World world;

    private List<Location> spawnPointsTeamA = new ArrayList<>();
    private List<Location> spawnPointsTeamB = new ArrayList<>();

    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    public List<Location> getSpawnPointsTeamA() {
        return spawnPointsTeamA;
    }

    public List<Location> getSpawnPointsTeamB() {
        return spawnPointsTeamB;
    }
}
