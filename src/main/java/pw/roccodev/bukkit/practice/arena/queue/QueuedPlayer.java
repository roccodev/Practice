package pw.roccodev.bukkit.practice.arena.queue;

import org.bukkit.entity.Player;
import pw.roccodev.bukkit.practice.PracticePlugin;
import pw.roccodev.bukkit.practice.arena.ArenaKit;

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
