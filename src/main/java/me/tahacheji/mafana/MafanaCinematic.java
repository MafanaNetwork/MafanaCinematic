package me.tahacheji.mafana;

import me.tahacheji.mafana.command.DirectorCommand;
import me.tahacheji.mafana.data.PlayerDirector;
import me.tahacheji.mafana.event.PlayerBreakBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MafanaCinematic extends JavaPlugin {


    private static MafanaCinematic instance;
    private List<PlayerDirector> directorList = new ArrayList<>();

    @Override
    public void onEnable() {
       instance = this;
        getCommand("MC").setExecutor(new DirectorCommand());
        getServer().getPluginManager().registerEvents(new PlayerBreakBlock(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public PlayerDirector getDirector(Player player) {
        for(PlayerDirector playerDirector : getDirectorList()) {
            if(playerDirector.getPlayer().getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
                return playerDirector;
            }
        }
        return null;
    }

    public List<PlayerDirector> getDirectorList() {
        return directorList;
    }

    public void addDirector(Player player) {
        getDirectorList().add(new PlayerDirector(player));
    }

    public void removeDirector(Player player) {
        Iterator<PlayerDirector> iterator = getDirectorList().iterator();

        while (iterator.hasNext()) {
            PlayerDirector playerDirector = iterator.next();

            if (playerDirector.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                iterator.remove();
            }
        }
    }


    public static MafanaCinematic getInstance() {
        return instance;
    }
}
