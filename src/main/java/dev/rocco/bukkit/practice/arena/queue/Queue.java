package dev.rocco.bukkit.practice.arena.queue;

public class Queue {

    private QueueType type;

    public boolean isRanked() {
        return type == QueueType.RANKED;
    }
}
