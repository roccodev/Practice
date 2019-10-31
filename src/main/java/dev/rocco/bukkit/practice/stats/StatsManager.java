/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.stats;

public interface StatsManager {

    void load();

     Object connect();

     void incrementStatistic(String profile, String statistic);

     void setStatistic(String profile, String statistic, Object newValue);

     void addProfile(String profile);

     StatsProfile buildProfile(String uuid);

     void loadComplete();

}
