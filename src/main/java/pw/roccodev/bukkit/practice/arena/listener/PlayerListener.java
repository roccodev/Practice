package pw.roccodev.bukkit.practice.arena.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pw.roccodev.bukkit.practice.arena.Arena;
import pw.roccodev.bukkit.practice.arena.Arenas;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Arena arena = Arenas.getByPlayer(player);
            if(arena == null) return;
            if(event.getFinalDamage() >= player.getHealth()) {
                arena.playerKilled(player);
            }
        }
    }

}
