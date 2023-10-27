package me.tahacheji.mafana.event;

import me.tahacheji.mafana.MafanaCinematic;
import me.tahacheji.mafana.data.PlayerDirector;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBreakBlock implements Listener {

    @EventHandler
    public void breakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerDirector playerDirector = MafanaCinematic.getInstance().getDirector(player);
        Location blockLocation = event.getBlock().getLocation();
        Location playerLocation = player.getLocation();
        if(playerDirector != null) {
            event.setCancelled(true);
            String message = String.format(
                    "%d, %d, %d, %.2f, %.2f",
                    blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ(),
                    playerLocation.getYaw(), playerLocation.getPitch()
            );
            Component chatMessage = Component.text(message)
                    .clickEvent(ClickEvent.copyToClipboard(message))
                    .color(net.kyori.adventure.text.format.NamedTextColor.GREEN);
            player.sendMessage(chatMessage);
        }
    }
 }
