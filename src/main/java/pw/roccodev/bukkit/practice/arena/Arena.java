package pw.roccodev.bukkit.practice.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pw.roccodev.bukkit.practice.arena.kit.KitDispatcherType;
import pw.roccodev.bukkit.practice.arena.kit.Kits;
import pw.roccodev.bukkit.practice.arena.listener.DeathType;
import pw.roccodev.bukkit.practice.arena.map.MapGenerator;
import pw.roccodev.bukkit.practice.arena.map.Maps;
import pw.roccodev.bukkit.practice.arena.team.TeamAssigner;
import pw.roccodev.bukkit.practice.utils.CollUtils;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    private HashMap<Player, ItemStack[]> cachedInventories = new HashMap<>();
    private HashMap<Player, ItemStack[]> cachedArmor = new HashMap<>();

    public Arena(ArenaMap map, ArenaKit kit, int maxTeams) {
        this.map = map == null ? Maps.maps.stream().collect(CollUtils.toShuffledStream()).findAny().get() : map;
        this.kit = kit == null ? Kits.kits.stream().collect(CollUtils.toShuffledStream()).findAny().get() : kit;
        this.maxTeams = maxTeams;
        this.id = ThreadLocalRandom.current().nextInt();
    }

    public Arena(ArenaMap map, int maxTeams) {
        this(map, null, maxTeams);
    }

    public Arena(ArenaKit kit, int maxTeams) {
        this(null, kit, maxTeams);
    }

    public Arena(int maxTeams) {
        this(null, null, maxTeams);
    }

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

    public void playerKilled(Player who, DeathType cause) {
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

        /* Give kits */
        for(ArenaTeam team : combatants) {
            for(Player player : team.getPlayers()) {
                kit.apply(player);
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
        cachedInventories.put(joining, joining.getInventory().getContents());
        cachedArmor.put(joining, joining.getInventory().getArmorContents());
    }

    public void playerKick(Player toKick, String reason) {
        ConfigEntries.formatAndSend(toKick, ConfigEntries.E_KICK, reason);
        if(!spectators.contains(toKick))
            playerKilled(toKick, DeathType.FORFEIT);
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

        /* Since we don't know if the arena was in another world,
           we clear the inventory as requested before teleporting. */
        if(ConfigEntries.ARENA_KIT_RESET == KitDispatcherType.CLEAR) {
            spectator.getInventory().clear();
            spectator.updateInventory();
        }

        spectator.teleport(cachedSpectatorLocations.get(spectator));

        /* Now we give the items back in the world they belong. */
        if(ConfigEntries.ARENA_KIT_RESET == KitDispatcherType.RESET) {
            spectator.getInventory().setContents(cachedInventories.get(spectator));
            spectator.getInventory().setArmorContents(cachedArmor.get(spectator));

            spectator.updateInventory();
        }
    }

    public void stop() {

    }

}
