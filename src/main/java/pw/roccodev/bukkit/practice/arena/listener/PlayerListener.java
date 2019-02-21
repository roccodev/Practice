package pw.roccodev.bukkit.practice.arena.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pw.roccodev.bukkit.practice.arena.Arena;
import pw.roccodev.bukkit.practice.arena.ArenaState;
import pw.roccodev.bukkit.practice.arena.Arenas;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Arena arena = Arenas.getByPlayer(player);
            if(arena == null) return;
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
    }

}
