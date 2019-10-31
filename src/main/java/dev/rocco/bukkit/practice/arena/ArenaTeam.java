/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.arena;

import dev.rocco.bukkit.practice.arena.proto.TeamPrototype;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaTeam {

    private TeamPrototype proto;

    private List<Player> players = new ArrayList<>();

    public List<Player> getPlayers() {
        return players;
    }

    public TeamPrototype getPrototype() {
        return proto;
    }

    public ArenaTeam(TeamPrototype proto) {
        this.proto = proto;
    }
}
