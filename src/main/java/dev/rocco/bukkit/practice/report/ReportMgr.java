package dev.rocco.bukkit.practice.report;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ReportMgr {

    public static Set<Report> reports = new HashSet<>();
    public static HashMap<CommandSender, Long> cachedReportRequests = new HashMap<>();

}
