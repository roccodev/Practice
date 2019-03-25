package dev.rocco.bukkit.practice.commands;

import dev.rocco.bukkit.practice.report.Report;
import dev.rocco.bukkit.practice.report.ReportMgr;
import dev.rocco.bukkit.practice.utils.GetPlayer;
import dev.rocco.bukkit.practice.utils.config.ConfigEntries;
import dev.rocco.bukkit.practice.utils.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ReportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(Permission.REPORT.assertHasPerm(commandSender)) return true;
        if(args.length < 2) return false;
        long now = System.currentTimeMillis();
        if(ReportMgr.cachedReportRequests.containsKey(commandSender)
                && now - ReportMgr.cachedReportRequests.get(commandSender)
                < ConfigEntries.REPORT_COOLDOWN * 1000) {
            ConfigEntries.formatAndSend(commandSender, ConfigEntries.REPORT_COOLDOWN_MESSAGE, ConfigEntries.REPORT_COOLDOWN
                    - (now - ReportMgr.cachedReportRequests.get(commandSender)));
            return true;
        }
        Player target = GetPlayer.get(commandSender, args[0]);
        if(target == null) return true;

        new Report(commandSender, target, String.join(" ", Arrays.copyOfRange(args, 1, args.length)))
                .send();
        ReportMgr.cachedReportRequests.put(commandSender, System.currentTimeMillis());
        return true;
    }
}
