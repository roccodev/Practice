package pw.roccodev.bukkit.practice.stats;

public interface StatsManager {

    void load();

     Object connect();

     void incrementStatistic(String profile, String statistic);

     void setStatistic(String profile, String statistic, Object newValue);

     void addProfile(String profile);

     StatsProfile buildProfile(String uuid);

}
