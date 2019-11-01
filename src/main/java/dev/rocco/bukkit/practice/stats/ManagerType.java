/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.stats;

public enum ManagerType {
    SQLITE(SQLiteStatsManager.class),
    MYSQL(MySQLStatsManager.class);

    private Class<? extends StatsManager> type;

    ManagerType(Class<? extends StatsManager> type) {
        this.type = type;
    }

    public Class<? extends StatsManager> getType() {
        return type;
    }
}
