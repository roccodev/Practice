package pw.roccodev.bukkit.practice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.roccodev.bukkit.practice.report.Report;
import pw.roccodev.bukkit.practice.report.ReportMgr;
import pw.roccodev.bukkit.practice.utils.GetPlayer;
import pw.roccodev.bukkit.practice.utils.permission.Permission;

import java.util.HashSet;

public class ReportTPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(Permission.REPORT_RECEIVE.assertHasPerm(commandSender)) return true;
        if(args.length != 1) return false;
        if(!(commandSender instanceof Player)) return true;

        Player target = GetPlayer.get(commandSender, args[0]);
        if(target == null) return true;

        HashSet<Report> copy = new HashSet<>(ReportMgr.reports);

        for(Report rep : copy) {
            if(rep.getTarget() == target) ReportMgr.reports.remove(rep);
        }

        ((Player)commandSender).teleport(target);

        return true;
    }
}
