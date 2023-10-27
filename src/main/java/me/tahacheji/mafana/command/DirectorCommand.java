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
                String message = String.format(
                        "%d, %d, %d, %.2f, %.2f",
                        blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ(),
                        playerLocation.getYaw(), playerLocation.getPitch()
                );
                Component chatMessage = Component.text(message)
                        .clickEvent(ClickEvent.copyToClipboard(message))
                        .color(net.kyori.adventure.text.format.NamedTextColor.GREEN);
                player.sendMessage(chatMessage);
                return true;
            }
            if (args[0].equalsIgnoreCase("send")) {
                Location point1 = new Location(Bukkit.getWorld("world"), 2387, 165, 3049, (float) -90.82, (float) -0.92);
                Location point2 = new Location(Bukkit.getWorld("world"), 2400, 166, 3057, (float) 0.16, (float) 1.70);
                Cinematic cinematic = new Cinematic(point1, point2, 15);

                cinematic.send(player, GameMode.SURVIVAL, null);
                return true;
            }
        }
        return false;
    }
}
