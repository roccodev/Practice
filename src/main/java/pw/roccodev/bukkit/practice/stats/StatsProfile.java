package pw.roccodev.bukkit.practice.stats;

public class StatsProfile {

    private String uuid;
    private long kills, deaths, victories, played, forfeits;

    public String getUuid() {
        return uuid;
    }

    public long getKills() {
        return kills;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getVictories() {
        return victories;
    }

    public long getPlayed() {
        return played;
    }

    public long getForfeits() {
        return forfeits;
    }

    public StatsProfile(String uuid, long kills, long deaths, long victories, long played, long forfeits) {
        this.uuid = uuid;
        this.kills = kills;
        this.deaths = deaths;
        this.victories = victories;
        this.played = played;
        this.forfeits = forfeits;
    }
}
