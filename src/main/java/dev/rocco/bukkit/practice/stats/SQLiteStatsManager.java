/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.stats;

import dev.rocco.bukkit.practice.PracticePlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteStatsManager extends MySQLStatsManager {

    @Override
    public void load() {
        /* Load driver */
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addProfile(String profile) {
        SQL.run("INSERT OR IGNORE INTO player_stats (uuid, kills, deaths, victories, played, forfeits) VALUES (?, 0, 0, 0, 0, 0)", profile);
    }

    @Override
    public Connection connect() {
        String connUrl = "jdbc:sqlite:" + PracticePlugin.PLUGIN_DIR.getAbsolutePath() + "/stats.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
