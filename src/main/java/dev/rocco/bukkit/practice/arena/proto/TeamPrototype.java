package dev.rocco.bukkit.practice.arena.proto;

import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

public class TeamPrototype {

    public static Set<TeamPrototype> registeredPrototypes = new HashSet<>();

    private String name;

    public String getName() {
        return name;
    }

    public TeamPrototype(String name) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);

        registeredPrototypes.add(this);
    }
}
