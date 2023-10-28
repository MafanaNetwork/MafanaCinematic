package me.tahacheji.mafana.command;

import me.tahacheji.mafana.MafanaCinematic;
import me.tahacheji.mafana.data.Cinematic;
import me.tahacheji.mafana.data.PlayerDirector;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DirectorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("MC")) {
            Player player = (Player) sender;
            if (!player.isOp()) {
                return true;
            }
            if (args[0].equalsIgnoreCase("set")) {
                MafanaCinematic.getInstance().addDirector(player);
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                MafanaCinematic.getInstance().removeDirector(player);
                return true;
            }
            if (args[0].equalsIgnoreCase("pos")) {
                Location blockLocation = player.getLocation();
                Location playerLocation = player.getLocation();
                String message = "new Location(Bukkit.getWorld(\"world\"), " + blockLocation.getX() + ", " + blockLocation.getY() + ", " +
                        blockLocation.getZ() + ", (float) " + playerLocation.getYaw() + ", (float) " + playerLocation.getPitch() + ");";
                Component chatMessage = Component.text(message)
                        .clickEvent(ClickEvent.copyToClipboard(message))
                        .color(net.kyori.adventure.text.format.NamedTextColor.GREEN);
                player.sendMessage(chatMessage);
                return true;
            }
            if (args[0].equalsIgnoreCase("send")) {
                List<Cinematic> cinematicSequence = new ArrayList<>();
                cinematicSequence.add(new Cinematic(new Location(Bukkit.getWorld("world"), 2360, 35, 3159, (float) 0.14, (float) -2.90),
                        new Location(Bukkit.getWorld("world"), 2360, 64, 3159, (float) 32.27, (float) 29.41), 5, player, GameMode.SPECTATOR, null, 0)); // Changed to 0
                cinematicSequence.add(new Cinematic(new Location(Bukkit.getWorld("world"), 2384, 42, 3205, (float) 17.89, (float) -2.69),
                        new Location(Bukkit.getWorld("world"), 2384, 42, 3205, (float) 17.89, (float) -2.69), 3, player, GameMode.SPECTATOR, null, 1)); // Changed to 1
                cinematicSequence.add(new Cinematic(new Location(Bukkit.getWorld("world"), 2324, 27, 3198, (float) -141.58, (float) 18.88),
                        new Location(Bukkit.getWorld("world"), 2324, 27, 3198, (float) -141.58, (float) 18.88), 3, player, GameMode.SPECTATOR, null, 2)); // Changed to 2
                cinematicSequence.add(new Cinematic(new Location(Bukkit.getWorld("world"), 2316, 22, 3171, (float) 39.23, (float) 8.67),
                        new Location(Bukkit.getWorld("world"), 2316, 22, 3171, (float) 39.23, (float) 8.67), 3, player, GameMode.SPECTATOR, null, 3)); // Changed to 3
                cinematicSequence.add(new Cinematic(new Location(Bukkit.getWorld("world"), 2270, 25, 3162, (float) 16.54, (float) 18.65),
                        new Location(Bukkit.getWorld("world"), 2265, 19, 3177, (float) 23.53, (float) 2.41), 4, player, GameMode.SURVIVAL,
                        new Location(Bukkit.getWorld("world"), 2265, 19, 3177, (float) 28.86, (float) 2.80), 4)); // Changed to 4

                cinematicSequence.get(0).send(cinematicSequence);
                return true;
            }
        }
        return false;
    }
}
