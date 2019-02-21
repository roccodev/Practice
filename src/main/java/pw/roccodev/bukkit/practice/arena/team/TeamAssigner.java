package pw.roccodev.bukkit.practice.arena.team;

import org.bukkit.entity.Player;
import pw.roccodev.bukkit.practice.arena.Arena;
import pw.roccodev.bukkit.practice.arena.ArenaTeam;

import java.util.List;

public class TeamAssigner {
    public static void assignTeams(Arena arena) {
        List<Player> waiting = arena.getAwaitingTeam();
        List<ArenaTeam> alreadyChosenTeams = arena.getCombatants();

        int maxTeams = arena.getMaxTeams();

        for(int i = 0; i < waiting.size(); i++) {
            Player toAssign = waiting.get(i);
            if(i >= alreadyChosenTeams.size() && i <= maxTeams) {
                ArenaTeam newTeam = new ArenaTeam();
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
}
