/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.stats;

import dev.rocco.bukkit.practice.PracticePlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SQL {
    private static Connection connection;
    private static final Object lock = new Object();
    private static HashMap<String, PreparedStatement> statements = new HashMap<>();

    public static void load() {
        PracticePlugin.STATS_MGR.load();
        connection = (Connection) PracticePlugin.STATS_MGR.connect();
        PracticePlugin.STATS_MGR.loadComplete();
    }

    static boolean run(String query, Object... data) {
        try {
            PreparedStatement stmt = getStatement(query);
            for (int i = 0; i < data.length; i++) {
                stmt.setObject(i + 1, data[i]);
            }
            return stmt.execute();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    static boolean runNoExcept(String query, Object... data) {
        try {
            PreparedStatement stmt = getStatement(query);
            for (int i = 0; i < data.length; i++) {
                stmt.setObject(i + 1, data[i]);
            }
            return stmt.execute();
        } catch(SQLException ignored) {
        }
        return false;
    }

    static ResultSet runQuery(String query, Object... data) {
        try {
            PreparedStatement stmt = getStatement(query);
            for (int i = 0; i < data.length; i++) {
                stmt.setObject(i + 1, data[i]);
            }
            return stmt.executeQuery();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static PreparedStatement getStatement(String query) throws SQLException {
        synchronized (lock) {
            PreparedStatement stmt = statements.get(query);
            if(stmt == null) {
                stmt = connection.prepareStatement(query);
            }
            return stmt;
        }
    }
}
