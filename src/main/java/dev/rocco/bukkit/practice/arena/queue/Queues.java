/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.arena.queue;

import dev.rocco.bukkit.practice.arena.ArenaKit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Queues {
    public static HashMap<ArenaKit, Queue> unrankedQueues = new HashMap<>();
    public static HashMap<ArenaKit, Queue> rankedQueues = new HashMap<>();

    public static void removePlayer(Player player) {
        for(Queue queue : unrankedQueues.values()) {
            for(QueuedPlayer queued : queue.getPlayers()) {
                if(queued.getBukkit() == player) queue.getPlayers().remove(queued);
            }
        }
        for(Queue queue : rankedQueues.values()) {
            for(QueuedPlayer queued : queue.getPlayers()) {
                if(queued.getBukkit() == player) queue.getPlayers().remove(queued);
            }
        }
    }
}
