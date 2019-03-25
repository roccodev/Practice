package dev.rocco.bukkit.practice.arena;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class Arenas {

    public static HashSet<Arena> arenas = new HashSet<>();

    public static Arena getByWorldAndPlaying(World world) {
        for(Arena arena : arenas) {
            if(arena.getState() != ArenaState.REQUEST && arena.getMap().getWorld() == world) return arena;
        }
        return null;
    }

    public static Arena getByPlayer(Player player) {
        for(Arena arena : arenas) {
            if(arena.getAwaitingTeam().contains(player)
            || arena.getCombatants().stream().anyMatch(team -> team.getPlayers().contains(player)))
                return arena;
        }
        return null;
    }

    public static void voidRequests(Player player) {
        for(Arena arena : arenas) {
            if(arena.getState() == ArenaState.REQUEST)
                arena.getAwaitingTeam().remove(player);
        }
    }

    public static Arena getBySpectator(Player player) {
        for(Arena arena : arenas) {
            if(arena.getSpectators().contains(player))
                return arena;
        }
        return null;
    }

    public static Arena getByGenericPlayer(Player player) {
        for(Arena arena : arenas) {
            if(arena.getAwaitingTeam().contains(player)
                    || arena.getSpectators().contains(player)
                    || arena.getCombatants().stream().anyMatch(team -> team.getPlayers().contains(player)))
                return arena;
        }
        return null;
    }


}
