/*
 * Copyright (c) 2019 RoccoDev
 * All rights reserved.
 */

package dev.rocco.bukkit.practice.utils.item;

import org.bukkit.inventory.ItemStack;

public class ItemSerializer {

    public static String serialize(ItemStack is) {
        return is.getType().getId() + "/" + is.getDurability() + "/" + is.getAmount();
    }

    public static ItemStack deserialize(String s) {

        String[] data = s.split("/");
        return new ItemStack(Integer.parseInt(data[0]), Integer.parseInt(data[2]), Short.parseShort(data[1]));
    }
}
