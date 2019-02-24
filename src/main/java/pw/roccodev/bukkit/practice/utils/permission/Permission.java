package pw.roccodev.bukkit.practice.utils.permission;

import org.bukkit.command.CommandSender;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;

public enum Permission {

    DUEL("duel"),

    PING("ping"),
    PING_OTHERS("ping.others"),

    REPORT("report"),
    REPORT_RECEIVE("report.receive"),

    SPECTATE("spectate"),

    STATS("stats"),
    STATS_OTHERS("stats.others"),

    INFO("info"),
    ADMIN("admin");

    private String permission;

    Permission(String perm) {
        this.permission = perm;
    }

    public String getPermission() {
        return permission;
    }

    public boolean has(CommandSender sender) {
        return sender.hasPermission("practice." + permission);
    }

    public boolean assertHasPerm(CommandSender sender) {
        if(has(sender)) return false;
        else {
            ConfigEntries.formatAndSend(sender, ConfigEntries.E_PERM, "practice." + permission);
            return true;
        }
    }
}
