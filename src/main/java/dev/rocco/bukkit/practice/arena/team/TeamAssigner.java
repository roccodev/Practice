package dev.rocco.bukkit.practice.arena.team;

import dev.rocco.bukkit.practice.arena.Arena;
import dev.rocco.bukkit.practice.arena.ArenaTeam;
import dev.rocco.bukkit.practice.arena.proto.TeamPrototype;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TeamAssigner {
    public static void assignTeams(Arena arena) {
        List<Player> waiting = arena.getAwaitingTeam();
        List<ArenaTeam> alreadyChosenTeams = arena.getCombatants();

        int maxTeams = arena.getMaxTeams();
        ArrayList<TeamPrototype> protoPool = new ArrayList<>(TeamPrototype.registeredPrototypes);
        System.out.println(protoPool.size());

        for(int i = 0; i < waiting.size(); i++) {
            Player toAssign = waiting.get(i);
            if(i >= alreadyChosenTeams.size() && i <= maxTeams) {
                ArenaTeam newTeam = new ArenaTeam(getFromPool(protoPool));
                newTeam.getPlayers().add(toAssign);
                alreadyChosenTeams.add(newTeam);
            }
            else if(i < alreadyChosenTeams.size()) {
                alreadyChosenTeams.get(i).getPlayers().add(toAssign);
            }
            else {
                arena.playerKick(toAssign, "Matchmaking failed.");
            }
        }

        arena.teamsAssigned();
        arena.setCombatants(alreadyChosenTeams);
    }

    private static TeamPrototype getFromPool(ArrayList<TeamPrototype> pool) {
        if(pool.size() == 1) {
            TeamPrototype elem = pool.get(0);
            pool.remove(0);
            return elem;
        }
        int rand = ThreadLocalRandom.current().nextInt(0, pool.size());
        TeamPrototype elem = pool.get(rand);
        pool.remove(rand);
        return elem;
    }
}
