/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.stats;

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
    public Object connect() {
        return null;
    }

    @Override
    public void incrementStatistic(String profile, String statistic) {

    }

    @Override
    public void addProfile(String profile) {

    }

    @Override
    public Object getStatistic(String profile, String statistic) {
        return null;
    }

    @Override
    public StatsProfile buildProfile(String uuid) {
        return null;
    }

    @Override
    public void setStatistic(String profile, String statistic, Object newValue) {

    }

    @Override
    public void loadComplete() {

    }
}
