package me.tahacheji.mafana.data;

import org.bukkit.entity.Player;

public class PlayerDirector {

    private Player player;

    public PlayerDirector(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
