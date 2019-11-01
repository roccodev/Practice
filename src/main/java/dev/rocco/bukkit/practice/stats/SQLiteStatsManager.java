/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.stats;

import dev.rocco.bukkit.practice.PracticePlugin;
import dev.rocco.bukkit.practice.arena.ArenaKit;
import dev.rocco.bukkit.practice.arena.kit.Kits;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteStatsManager implements StatsManager {

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
    public void loadComplete() {
        initTables();
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

    private void initTables() {
        SQL.run("CREATE TABLE IF NOT EXISTS player_stats (uuid text PRIMARY KEY, kills integer, deaths integer, " +
                "victories integer, played integer, forfeits integer)");

        for(ArenaKit kit : Kits.kits) {
            String colName = "elo_" + kit.getName();
            SQL.runNoExcept("ALTER TABLE player_stats ADD COLUMN \"" + colName + "\" INTEGER DEFAULT 1000");
        }
    }

    @Override
    public void addProfile(String profile) {
        SQL.run("INSERT OR IGNORE INTO player_stats (uuid, kills, deaths, victories, played, forfeits) VALUES (?, 0, 0, 0, 0, 0)", profile);
    }

    @Override
    public void incrementStatistic(String profile, String statistic) {
        SQL.run("UPDATE player_stats SET " + statistic + " = " + statistic + " + 1 WHERE uuid = ?", profile);
    }

    @Override
    public void setStatistic(String profile, String statistic, Object newValue) {
        SQL.run("UPDATE player_stats SET " + statistic + " = ? WHERE uuid = ?", newValue, profile);
    }

    @Override
    public Object getStatistic(String profile, String statistic) {
        ResultSet res = SQL.runQuery("SELECT \"" + statistic + "\" FROM player_stats WHERE uuid = ?", profile);
        try {
            if(res != null && res.next()) {
                return res.getObject(statistic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public StatsProfile buildProfile(String uuid) {
        String sql = "SELECT * FROM player_stats WHERE uuid = ?";
        ResultSet rs = SQL.runQuery(sql, uuid);
        try {
            if (rs != null && rs.next()) {
                return new StatsProfile(uuid, rs.getLong("kills"), rs.getLong("deaths"), rs.getLong("victories"),
                        rs.getLong("played"), rs.getLong("forfeits"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
