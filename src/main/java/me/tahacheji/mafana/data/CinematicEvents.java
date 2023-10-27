package me.tahacheji.mafana.data;

import org.bukkit.entity.Player;

public interface CinematicEvents {

    default boolean whileInCinematicView(Player var1) {return false;}
    default boolean endOfCinematicView(Player var1) {return false;}
    default boolean startOfCinematicView(Player var1) {return false;}
}
