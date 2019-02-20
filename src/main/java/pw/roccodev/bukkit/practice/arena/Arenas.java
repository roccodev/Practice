package pw.roccodev.bukkit.practice.arena;

import org.bukkit.World;

import java.util.HashSet;

public class Arenas {

    private static HashSet<Arena> arenas = new HashSet<>();

    public static Arena getByWorldAndPlaying(World world) {
        for(Arena arena : arenas) {
            if(arena.getState() != ArenaState.REQUEST && arena.getMap().getWorld() == world) return arena;
        }
        return null;
    }

}
