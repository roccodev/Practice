package dev.rocco.bukkit.practice.arena.queue;

import dev.rocco.bukkit.practice.PracticePlugin;
import dev.rocco.bukkit.practice.arena.ArenaKit;
import org.bukkit.entity.Player;

public class QueuedPlayer {
    private Player bukkit;
    private long elo;
    private ArenaKit kit;

    public QueuedPlayer(Player bukkit, long elo, ArenaKit kit) {
        this.bukkit = bukkit;
        this.elo = elo;
        this.kit = kit;
    }

    public QueuedPlayer(Player bukkit, ArenaKit kit) {
        this(bukkit, -1, kit);
    }

    public long getElo() {
        return elo;
    }

    public Player getBukkit() {
        return bukkit;
    }

    public void updateElo(long newElo) {
        this.elo = newElo;
        String uuid = bukkit.getUniqueId().toString().replace("-", "");
        PracticePlugin.STATS_MGR.setStatistic(uuid, "elo_" + kit.getName(), newElo);
    }
}
