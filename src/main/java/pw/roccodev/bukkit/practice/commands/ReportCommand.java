package pw.roccodev.bukkit.practice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.roccodev.bukkit.practice.report.Report;
import pw.roccodev.bukkit.practice.report.ReportMgr;
import pw.roccodev.bukkit.practice.utils.GetPlayer;
import pw.roccodev.bukkit.practice.utils.config.ConfigEntries;
import pw.roccodev.bukkit.practice.utils.permission.Permission;

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
