package pw.roccodev.bukkit.practice.arena;

import org.bukkit.entity.Player;
import pw.roccodev.bukkit.practice.arena.map.MapGenerator;

import java.util.List;

public class Arena {

    private int id;
    private ArenaMap map;
    private ArenaKit kit;
    private ArenaState state = ArenaState.REQUEST;

    private List<ArenaTeam> combatants;
    private List<Player> spectators;

    public ArenaMap getMap() {
        return map;
    }

    public ArenaKit getKit() {
        return kit;
    }

    public ArenaState getState() {
        return state;
    }

    public int getId() {
        return id;
    }

    public void start() {

        /* Load the map */
        MapGenerator.generateMap(map);

        /* Apply no-hit-delay to players */
        if(kit.hasNoHitDelay()) {
            for (ArenaTeam team : combatants) {
                for (Player player : team.getPlayers()) {
                    player.setMaximumNoDamageTicks(kit.getHitDelay());
                }
            }
        }



    }

}
