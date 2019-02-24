package pw.roccodev.bukkit.practice.stats;

public interface StatsManager {

    void load();

     Object connect();

     void incrementStatistic(String profile, String statistic);

     void addProfile(String profile);

     StatsProfile buildProfile(String uuid);

}
