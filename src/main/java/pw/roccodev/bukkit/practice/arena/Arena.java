package pw.roccodev.bukkit.practice.arena;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import pw.roccodev.bukkit.practice.arena.kit.KitDispatcherType;
import pw.roccodev.bukkit.practice.arena.kit.Kits;
import pw.roccodev.bukkit.practice.arena.listener.DeathType;
import pw.roccodev.bukkit.practice.arena.map.Maps;
import pw.roccodev.bukkit.practice.arena.team.TeamAssigner;
import pw.roccodev.bukkit.practice.utils.CollUtils;
import pw.roccodev.bukkit.practice.utils.Prefix;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
    private HashMap<Player, PlayerData.InventoryData> cachedInventories = new HashMap<>();

    private List<Player> invited = new ArrayList<>();

    private Set<Block> playerPlacedBlocks = new HashSet<>();

    public Arena(ArenaMap map, ArenaKit kit, int maxTeams) {
        this.map = map == null ? Maps.maps.stream().collect(CollUtils.toShuffledStream()).findAny().get() : map;
        this.kit = kit == null ? Kits.kits.stream().collect(CollUtils.toShuffledStream()).findAny().get() : kit;
        this.maxTeams = maxTeams;
        this.id = ThreadLocalRandom.current().nextInt();

        Arenas.arenas.add(this);
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

    public List<Player> getSpectators() {
        return spectators;
    }

    public Set<Block> getPlayerPlacedBlocks() {
        return playerPlacedBlocks;
    }

    public void playerKilled(Player who, DeathType cause) {
        if(who != null)
            who.setMaximumNoDamageTicks(20); /* Reset hit delay */

        combatants.stream().filter(t -> t.getPlayers().contains(who)).findAny()
                .ifPresent(arenaTeam -> arenaTeam.getPlayers().remove(who));

        combatants.removeIf(t -> t.getPlayers().size() == 0);

        spectate(who);

        if(who != null) {
            DeathMessage.sendDeathMessage(this, who, who.getKiller());
            who.getInventory().clear();
            clearPlayerArmor(who);
            who.updateInventory();
        }


        if(combatants.size() <= 1) {
            System.out.println("Stopping arena " + id);
            stop();
        }
    }

    public List<Player> getInvited() {
        return invited;
    }

    public int getTotalPlayerCount() {
        int count = 0;
        for(ArenaTeam team : combatants) {
            count += team.getPlayers().size();
        }
        return count;
    }

    public void teamsAssigned() {
        awaitingTeam.clear();
    }

    public void invitePlayer(Player toInvite, String sender) {
        invited.add(toInvite);
        String message = String.format(ConfigEntries.ARENA_INVITE, sender, kit.getName(), map.getName());

        TextComponent cmp = new TextComponent(message);
        cmp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept " + sender));

        toInvite.spigot().sendMessage(cmp);
    }

    public void broadcast(String message) {
        for(Player awaiting : awaitingTeam) {
            awaiting.sendMessage(message);
        }

        for(ArenaTeam team : combatants) {
            for(Player player : team.getPlayers()) {
                player.sendMessage(message);
            }
        }

        for(Player spec : spectators) {
            spec.sendMessage(message);
        }
    }

    public void broadcast(BaseComponent message) {
        for(Player awaiting : awaitingTeam) {
            awaiting.spigot().sendMessage(message);
        }

        for(ArenaTeam team : combatants) {
            for(Player player : team.getPlayers()) {
                player.spigot().sendMessage(message);
            }
        }

        for(Player spec : spectators) {
            spec.spigot().sendMessage(message);
        }
    }

    public void start() {

        state = ArenaState.PREGAME;

        /* Load the map */
        // MapGenerator.generateMap(map);

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

                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
                player.setSaturation(20);
            }
        }

        state = ArenaState.GAME;

    }

    public void playerJoin(Player joining) {
        playerJoin(joining, null);
    }

    public void playerJoin(Player joining, ArenaTeam team) {
        invited.remove(joining);
        Arenas.voidRequests(joining);

        if(team == null) {
            awaitingTeam.add(joining);
        }
        else {
            team.getPlayers().add(joining);
        }

        cachedSpectatorLocations.put(joining, joining.getLocation());
        cachedInventories.put(joining, new PlayerData.InventoryData(joining));
        broadcast(String.format(ConfigEntries.ARENA_JOIN, joining.getName()));

        if(awaitingTeam.size() >= maxTeams) start();
    }

    public void playerKick(Player toKick, String reason) {
        ConfigEntries.formatAndSend(toKick, ConfigEntries.E_KICK, reason);
        if(!spectators.contains(toKick))
            playerKilled(toKick, DeathType.FORFEIT);
        stopSpectating(toKick);
    }

    public void spectate(Player spectator) {
        spectators.add(spectator);
        broadcast(String.format(ConfigEntries.ARENA_SPECJOIN, spectator.getName()));

        if(ConfigEntries.ARENA_SPEC_FLIGHT)
            spectator.setAllowFlight(true);

        if(ConfigEntries.ARENA_SPEC_FLIGHTON)
            spectator.setFlying(true);
    }

    public void stopSpectating(Player spectator) {
        spectators.remove(spectator);
        broadcast(String.format(ConfigEntries.ARENA_SPECLEAVE, spectator.getName()));
        spectator.setAllowFlight(false);
        spectator.setFlying(false);
        spectator.setMaximumNoDamageTicks(20);

        /* Since we don't know if the arena was in another world,
           we clear the inventory as requested before teleporting. */
        if(ConfigEntries.ARENA_KIT_RESET == KitDispatcherType.CLEAR) {
            spectator.getInventory().clear();
            clearPlayerArmor(spectator);
            spectator.updateInventory();
        }

        spectator.teleport(cachedSpectatorLocations.get(spectator));

        /* Now we give the items back in the world they belong. */
        if(ConfigEntries.ARENA_KIT_RESET == KitDispatcherType.RESET) {

            PlayerData.InventoryData data = cachedInventories.get(spectator);

            spectator.getInventory().setContents(data.getContents());
            spectator.getInventory().setArmorContents(data.getArmorContents());

            spectator.updateInventory();
        }
    }

    private void clearPlayerArmor(Player player) {
        PlayerInventory inv = player.getInventory();
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
    }

    public void stop() {

        ArenaTeam winner = combatants.get(0);

        broadcast(String.format(ConfigEntries.ARENA_END,
                "Red",
                String.join(",", winner.getPlayers().stream().map(Player::getName).collect(Collectors.toList())),
                winner.getPlayers().size(),
                String.join(",", spectators.stream().map(Player::getName).collect(Collectors.toList())),
                spectators.size(),
                Prefix.INFO
                ));

        combatants.clear();
        awaitingTeam.clear();
        spectators.clear();
        Arenas.arenas.remove(this);
    }

}
