package pw.roccodev.bukkit.practice.arena;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pw.roccodev.bukkit.practice.PracticePlugin;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;

import java.util.Arrays;
import java.util.Objects;

public class DeathMessage {

    public static void sendDeathMessage(Arena arena, Player dead, Player killer) {
        boolean noKiller = killer == null;

        if(ConfigEntries.ARENA_DEATH_CLICK) {
            String text;
            if(noKiller) {
                text = String.format(ConfigEntries.ARENA_DEATH_U, dead.getName());
            }
            else {
                text = String.format(ConfigEntries.ARENA_DEATH, dead.getName(), killer.getName());
            }

            TextComponent comp = new TextComponent(text);
            comp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel inventory " + dead.getName()));

            PlayerData.DeathInventoryData data = new PlayerData.DeathInventoryData(dead, killer);
            PlayerData.deadInventories.put(dead, data);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(PlayerData.deadInventories.get(dead) == data)
                        PlayerData.deadInventories.remove(dead);
                }
            }.runTaskLaterAsynchronously(PracticePlugin.INST, 30 * 20);

            arena.broadcast(comp);
        }
        else {
            if(noKiller) {
                arena.broadcast(String.format(ConfigEntries.ARENA_DEATH_U, dead.getName()));
            }
            else {
                arena.broadcast(String.format(ConfigEntries.ARENA_DEATH, dead.getName(), killer.getName()));
            }
        }
    }

    public static void showInventory(Player dead, Player toShow) {
        PlayerData.DeathInventoryData data = PlayerData.deadInventories.get(dead);
        if(data == null) return;

        Inventory inventory = Bukkit.createInventory(null,
                54,
                "[D] " + dead.getName() + "'s Inventory");

        inventory.addItem(filter(data.getPlayer().getContents()));
        inventory.addItem(filter(data.getPlayer().getArmorContents()));

        toShow.openInventory(inventory);

    }

    private static ItemStack[] filter(ItemStack[] in) {
        return Arrays.stream(in).filter(Objects::nonNull).toArray(ItemStack[]::new);
    }

}
