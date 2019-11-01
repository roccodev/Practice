/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.stats;

import dev.rocco.bukkit.practice.arena.ArenaKit;
import dev.rocco.bukkit.practice.arena.kit.Kits;
import dev.rocco.bukkit.practice.utils.config.ConfigEntries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLStatsManager implements StatsManager {
    @Override
    public void load() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection connect() {
        Properties connProps = new Properties();
        connProps.put("user", ConfigEntries.DATABASE_USER);
        connProps.put("password", ConfigEntries.DATABASE_KEY);
        String connString = "jdbc:mysql://" + ConfigEntries.DATABASE_HOST + ":" + ConfigEntries.DATABASE_PORT + "/"
                + ConfigEntries.DATABASE_NAME;
        try {
            return DriverManager.getConnection(connString, connProps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void incrementStatistic(String profile, String statistic) {
        SQL.run("UPDATE player_stats SET " + statistic + " = " + statistic + " + 1 WHERE uuid = ?", profile);
    }

    @Override
    public void addProfile(String profile) {
        SQL.run("INSERT IGNORE INTO player_stats (uuid, kills, deaths, victories, played, forfeits) VALUES (?, 0, 0, 0, 0, 0)", profile);
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

    @Override
    public void setStatistic(String profile, String statistic, Object newValue) {
        SQL.run("UPDATE player_stats SET " + statistic + " = ? WHERE uuid = ?", newValue, profile);
    }

    @Override
    public void loadComplete() {
        SQL.run("CREATE TABLE IF NOT EXISTS player_stats (uuid varchar(32) PRIMARY KEY, KEY uuid_data (uuid(32)), kills integer, deaths integer, " +
                "victories integer, played integer, forfeits integer)");

        for(ArenaKit kit : Kits.kits) {
            String colName = "elo_" + kit.getName();
            SQL.runNoExcept("ALTER TABLE player_stats ADD COLUMN \"" + colName + "\" INTEGER DEFAULT 1000");
        }
    }
}
