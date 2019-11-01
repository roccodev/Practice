/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.commands;

import dev.rocco.bukkit.practice.PracticePlugin;
import dev.rocco.bukkit.practice.arena.ArenaKit;
import dev.rocco.bukkit.practice.arena.kit.Kits;
import dev.rocco.bukkit.practice.arena.queue.Queue;
import dev.rocco.bukkit.practice.arena.queue.QueuedPlayer;
import dev.rocco.bukkit.practice.arena.queue.Queues;
import dev.rocco.bukkit.practice.utils.config.ConfigEntries;
import dev.rocco.bukkit.practice.utils.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(Permission.DUEL.assertHasPerm(sender)) return true;
        if(!(sender instanceof Player)) return true;

        if(args.length > 1) {
            Queues.removePlayer((Player)sender);

            List<String> arguments = new ArrayList<>(Arrays.asList(args));
            arguments.remove(0);
            String kitName = String.join(" ", arguments);
            ArenaKit kit = Kits.getByName(kitName);

            Queue queue = null;
            if(args[0].startsWith("u")) {
                queue = Queues.unrankedQueues.get(kit);
            }
            else if(args[0].startsWith("r")) {
                queue = Queues.rankedQueues.get(kit);
            }

            Queue finalQueue = queue;
            QueuedPlayer queued = new QueuedPlayer((Player)sender, finalQueue.getKit());
            Bukkit.getScheduler().runTaskAsynchronously(PracticePlugin.INST, new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        ConfigEntries.formatAndSend(sender, ConfigEntries.QUEUE_JOIN, finalQueue.isRanked() ? "Ranked" : "Unranked",
                                finalQueue.getKit().getName());
                        finalQueue.init(queued);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return true;
    }
}
