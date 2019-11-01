/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.arena.queue;

import dev.rocco.bukkit.practice.PracticePlugin;
import dev.rocco.bukkit.practice.arena.Arena;
import dev.rocco.bukkit.practice.arena.ArenaKit;
import dev.rocco.bukkit.practice.utils.config.ConfigEntries;
import org.bukkit.entity.Player;

public class QueuedPlayer {
    private Player bukkit;
    private long elo;
    private ArenaKit kit;
    private boolean consumed = false;

    public QueuedPlayer(Player bukkit, ArenaKit kit) {
        Integer elo = (Integer) PracticePlugin.STATS_MGR.getStatistic(Arena.uuidStripped(bukkit), "elo_" + kit.getName());
        if(elo == null) elo = 1000;

        this.bukkit = bukkit;
        this.kit = kit;
        this.elo = elo;
    }

    public void consume() {
        this.consumed = true;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public long getElo() {
        return elo;
    }

    public Player getBukkit() {
        return bukkit;
    }

    public void updateElo(long newElo) {
        long old = elo;
        if(newElo < 0) newElo = 0;
        this.elo = newElo;
        String uuid = bukkit.getUniqueId().toString().replace("-", "");
        PracticePlugin.STATS_MGR.setStatistic(uuid, "elo_" + kit.getName(), newElo);

        long delta = newElo - old;
        String deltaString = delta > 0 ? "+" + delta : Long.toString(delta);

        ConfigEntries.formatAndSend(bukkit, ConfigEntries.QUEUE_UPDATE_ELO, newElo, deltaString);
    }
}
