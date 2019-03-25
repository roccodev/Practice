package dev.rocco.bukkit.practice.arena.queue.elo;

import dev.rocco.bukkit.practice.arena.queue.QueuedPlayer;

public class EloCalculator {

    public static void updateElo(QueuedPlayer[] players, int winner) {

        /* Roughly the average ELO change */
        double updateFactor = 50;

        double Q = 0D;
        for(QueuedPlayer player : players) {
            Q += Math.pow(10D, player.getElo() / 400D);
        }

        for(int index = 0; index < players.length; index++) {

            QueuedPlayer player = players[index];

            int score = winner == index ? 1 : 0;

            /* Elo formula, calculates the expected rating */
            double expected = Math.pow(10D, player.getElo() / 400D) / Q;

            long newElo = Math.round(player.getElo() + updateFactor * (score - expected));

            player.updateElo(newElo);
        }

    }

}
