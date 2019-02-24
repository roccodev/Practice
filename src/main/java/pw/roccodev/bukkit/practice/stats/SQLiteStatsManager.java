package pw.roccodev.bukkit.practice.stats;

import pw.roccodev.bukkit.practice.PracticePlugin;

import java.sql.*;

public class SQLiteStatsManager implements StatsManager {

    @Override
    public void load() {

        /* Load driver */
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        initTables();
    }

    @Override
    public Connection connect() {
        String connUrl = "jdbc:sqlite:" + PracticePlugin.PLUGIN_DIR.getAbsolutePath() + "/stats.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void initTables() {
        try (Connection conn = this.connect()) {
             execStatement(conn,
                     "CREATE TABLE IF NOT EXISTS player_stats (uuid text PRIMARY KEY, kills integer, deaths integer, " +
                     "victories integer, played integer, forfeits integer)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void execStatement(Connection connection, String statement) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(statement);
    }

    @Override
    public void addProfile(String profile) {
        try(Connection connection = connect()) {
            execStatement(connection,
                    "INSERT OR IGNORE INTO player_stats (uuid, kills, deaths, victories, played, forfeits) VALUES ('" + profile + "', 0, 0, 0, 0, 0)");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incrementStatistic(String profile, String statistic) {
        try(Connection connection = connect()) {
            execStatement(connection,
                    "UPDATE player_stats SET " + statistic + " = " + statistic + " + 1 WHERE uuid = '" + profile + "'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T getStatistic(String profile, String statistic) {
        String sql = "SELECT " + statistic + " FROM player_stats WHERE uuid = '" + profile + "'";

        try(Connection connection = connect()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return (T) rs.getObject(statistic);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
