/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.arena.queue;

import dev.rocco.bukkit.practice.arena.ArenaKit;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class Queue {

    private QueueType type;
    private ArenaKit kit;

    private BlockingQueue<QueuedPlayer> players = new LinkedBlockingQueue<>();

    public boolean isRanked() {
        return type == QueueType.RANKED;
    }

    public ArenaKit getKit() {
        return kit;
    }

    public QueuedPlayer search(long eloStart, long eloEnd) throws InterruptedException {
        BlockingQueue filtered =
                players.stream().filter(pl -> pl.getElo() >= eloStart && pl.getElo() <= eloEnd)
                        .collect(Collectors.toCollection(LinkedBlockingQueue::new));
        return (QueuedPlayer) filtered.take();
    }

    public QueuedPlayer search() throws InterruptedException {
        return players.take();
    }

    public void communicate(QueuedPlayer sender, QueuedPlayer receiver) {
        players.remove(receiver);
        players.remove(sender);
    }
}
