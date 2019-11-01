/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.arena.listener;

import dev.rocco.bukkit.practice.arena.Arena;
import dev.rocco.bukkit.practice.arena.ArenaState;
import dev.rocco.bukkit.practice.arena.Arenas;
import dev.rocco.bukkit.practice.arena.queue.Queues;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if(event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent byEntity = (EntityDamageByEntityEvent) event;
            if(byEntity.getDamager() instanceof Player) {
                Player damager = (Player) byEntity.getDamager();
                Arena spec = Arenas.getBySpectator(damager);
                if(spec != null) {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Arena arena = Arenas.getByPlayer(player);
            if(arena == null) {
                Arena spec = Arenas.getBySpectator(player);
                if(spec != null)
                    event.setCancelled(true);
                return;
            }
            if(event.getFinalDamage() >= player.getHealth()) {
                event.setCancelled(true);
                if(event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE ||
                    event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    arena.playerKilled(player, DeathType.PLAYER);
                }
                else arena.playerKilled(player, DeathType.OTHER);

            }
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player leaving = event.getPlayer();
        Arena arena = Arenas.getByPlayer(leaving);
        if(arena == null) return;
        if(arena.getState() != ArenaState.REQUEST)
            arena.playerKick(leaving, "Logged off.");
        Queues.removePlayer(leaving);
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        Arena arena = Arenas.getByPlayer(event.getPlayer());
        if(arena != null) {
            arena.getPlayerPlacedBlocks().add(event.getBlockPlaced());
        }
    }

    @EventHandler
    public void onBlockBroken(BlockBreakEvent event) {
        Arena arena = Arenas.getByGenericPlayer(event.getPlayer());
        if(arena == null) return;

        if(arena.getSpectators().contains(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        if(!arena.getPlayerPlacedBlocks().contains(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Arena arena = Arenas.getByPlayer((Player)event.getEntity());
        if(arena != null) {
            if(!arena.getKit().isHungerEnabled()) event.setCancelled(true);
        }
        else {
            arena = Arenas.getBySpectator((Player)event.getEntity());
            if(arena != null && !arena.getKit().isHungerEnabled()) event.setCancelled(true);
        }
    }

}
