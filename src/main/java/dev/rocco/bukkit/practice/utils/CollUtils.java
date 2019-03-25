package dev.rocco.bukkit.practice.utils;

import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollUtils {
    public static <T> Collector<T, ?, Stream<T>> toShuffledStream() {
        return Collectors.collectingAndThen(Collectors.toList(), collected -> {
            Collections.shuffle(collected);
            return collected.stream();
        });
    }
}
