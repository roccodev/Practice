package pw.roccodev.bukkit.practice.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pw.roccodev.bukkit.practice.arena.map.MapGenerator;
import pw.roccodev.bukkit.practice.arena.team.TeamAssigner;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Arena {

    private int id;
    private ArenaMap map;
    private ArenaKit kit;
    private ArenaState state = ArenaState.REQUEST;

    private int maxTeams;

    private List<ArenaTeam> combatants = new ArrayList<>();
    private List<Player> spectators = new ArrayList<>();

    private HashMap<Player, Location> cachedSpectatorLocations = new HashMap<>();
    private List<Player> awaitingTeam = new ArrayList<>();

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

    public List<ArenaTeam> getCombatants() {
        return combatants;
    }

    public List<Player> getAwaitingTeam() {
        return awaitingTeam;
    }

    public int getMaxTeams() {
        return maxTeams;
    }

    public void setCombatants(List<ArenaTeam> combatants) {
        this.combatants = combatants;
    }

    public void playerKilled(Player who) {
        if(who != null)
            who.setMaximumNoDamageTicks(20); /* Reset hit delay */

        combatants.stream().filter(t -> t.getPlayers().contains(who)).findAny()
                .ifPresent(arenaTeam -> arenaTeam.getPlayers().remove(who));

       spectate(who);

        if(getTotalPlayerCount() <= 1) {
            stop();
        }
    }

    public int getTotalPlayerCount() {
        int count = 0;
        for(ArenaTeam team : combatants) {
            count += team.getPlayers().size();
        }
        return count;
    }

    public void start() {

        /* Load the map */
        MapGenerator.generateMap(map);

        /* Assign teams */
        TeamAssigner.assignTeams(this);

        /* TODO Teleport players */

        /* Apply no-hit-delay to players */
        if(kit.hasNoHitDelay()) {
            for (ArenaTeam team : combatants) {
                for (Player player : team.getPlayers()) {
                    player.setMaximumNoDamageTicks(kit.getHitDelay());
                }
            }
        }

    }

    public void playerJoin(Player joining) {
        playerJoin(joining, null);
    }

    public void playerJoin(Player joining, ArenaTeam team) {
        if(team == null) {
            awaitingTeam.add(joining);
        }
        else {
            team.getPlayers().add(joining);
        }
        cachedSpectatorLocations.put(joining, joining.getLocation());
    }

    public void playerKick(Player toKick, String reason) {
        ConfigEntries.formatAndSend(toKick, ConfigEntries.E_KICK, reason);
        stopSpectating(toKick);
    }

    public void spectate(Player spectator) {
        spectators.add(spectator);

        if(ConfigEntries.ARENA_SPEC_FLIGHT)
            spectator.setAllowFlight(true);

        if(ConfigEntries.ARENA_SPEC_FLIGHTON)
            spectator.setFlying(true);
    }

    public void stopSpectating(Player spectator) {
        spectators.remove(spectator);
        spectator.setAllowFlight(false);
        spectator.setFlying(false);

        spectator.teleport(cachedSpectatorLocations.get(spectator));
    }

    public void stop() {

    }

}
