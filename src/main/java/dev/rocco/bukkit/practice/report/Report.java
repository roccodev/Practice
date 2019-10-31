/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.report;

import dev.rocco.bukkit.practice.utils.config.ConfigEntries;
import dev.rocco.bukkit.practice.utils.permission.Permission;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Report {

    private CommandSender sender;
    private Player target;
    private String reason;

    public Report(CommandSender sender, Player target, String reason) {
        this.sender = sender;
        this.target = target;
        this.reason = reason;

        ReportMgr.reports.add(this);

    }

    public CommandSender getSender() {
        return sender;
    }

    public Player getTarget() {
        return target;
    }

    public String getReason() {
        return reason;
    }

    public void send() {
        TextComponent textComponent = new TextComponent(String.format(ConfigEntries.REPORT_NOTIFICATION,
                sender.getName(), target.getName(), reason));

        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rtp " + target.getName()));

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(Permission.REPORT_RECEIVE.has(p)) p.spigot().sendMessage(textComponent);
        }
    }

}
