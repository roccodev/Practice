/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.arena.queue;

import dev.rocco.bukkit.practice.PracticePlugin;
import dev.rocco.bukkit.practice.arena.Arena;
import dev.rocco.bukkit.practice.arena.ArenaKit;
import dev.rocco.bukkit.practice.utils.config.ConfigEntries;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Queue {

    private QueueType type;
    private ArenaKit kit;

    private java.util.Queue<QueuedPlayer> players = new ConcurrentLinkedQueue<>();

    public Queue(QueueType type, ArenaKit kit) {
        this.type = type;
        this.kit = kit;
    }

    public boolean isRanked() {
        return type == QueueType.RANKED;
    }

    public java.util.Queue<QueuedPlayer> getPlayers() {
        return players;
    }

    public ArenaKit getKit() {
        return kit;
    }

    public void init(QueuedPlayer sender) throws InterruptedException {
        QueuedPlayer target = null;
        if(isRanked()) {
            long eloStart = sender.getElo() - ConfigEntries.QUEUE_ELO_SHIFT;
            long eloEnd = sender.getElo() + ConfigEntries.QUEUE_ELO_SHIFT;
            players.offer(sender);
            while(eloStart != 0) {
                QueuedPlayer result = search(sender, eloStart, eloEnd);
                if(result != null) {
                    target = result;
                    break;
                }
                else if(!players.contains(sender)) return;
                if((eloStart -= ConfigEntries.QUEUE_ELO_SHIFT) < 0) eloStart = 0;
                eloEnd += ConfigEntries.QUEUE_ELO_SHIFT;

                ConfigEntries.formatAndSend(sender.getBukkit(), ConfigEntries.QUEUE_SEARCH_ELO, eloStart, eloEnd);
            }
        }
        else {
            target = search(sender);
        }
        if(target == null) return;
        commence(sender, target);
    }

    private QueuedPlayer search(QueuedPlayer sender, long eloStart, long eloEnd) throws InterruptedException {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < ConfigEntries.QUEUE_TIMEOUT &&
                players.contains(sender)) {
            for (QueuedPlayer elem : players) {
                if (elem != sender) {
                    if (elem.getElo() >= eloStart && elem.getElo() <= eloEnd) {
                        players.remove(elem);
                        return elem;
                    }
                }
            }
        }
        return null;
    }

    private QueuedPlayer search(QueuedPlayer sender) throws InterruptedException {
        players.offer(sender);
        while (players.contains(sender)) {
            for (QueuedPlayer elem : players) {
                if (elem != sender) {
                    players.remove(elem);
                    return elem;
                }
            }
        }
        return null;
    }

    private synchronized void commence(QueuedPlayer sender, QueuedPlayer receiver) throws InterruptedException {
        if(sender.isConsumed() || receiver.isConsumed()) return;
        players.remove(sender);

        sender.consume();
        receiver.consume();

        Arena arena = new Arena(null, kit, 2);
        arena.setType(type);
        arena.setQueuedPlayers(new QueuedPlayer[] {sender, receiver});
        new BukkitRunnable() {
            @Override
            public void run() {
                arena.playerJoin(sender.getBukkit());
                arena.playerJoin(receiver.getBukkit());
            }
        }.runTaskAsynchronously(PracticePlugin.INST);
    }
}
