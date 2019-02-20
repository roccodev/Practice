package pw.roccodev.bukkit.practice.arena;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class Arena {

    private int id;
    private ArenaMap map;
    private ArenaKit kit;

    private List<ArenaTeam> combatants;
    private List<Player> spectators;


    public void start() {

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
