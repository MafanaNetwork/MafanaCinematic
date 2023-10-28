package me.tahacheji.mafana.data;

import me.tahacheji.mafana.MafanaCinematic;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Cinematic implements CinematicEvents {

    private Location point1;
    private Location point2;
    private int lengthSpeed;
    private Player target;
    private GameMode gameMode;
    private Location endLocation;
    private int position;

    public Cinematic(Location point1, Location point2, int lengthSpeed) {
        this.point1 = point1;
        this.point2 = point2;
        this.lengthSpeed = lengthSpeed;
        this.position = 0;
    }

    public Cinematic(Location point1, Location point2, int lengthSpeed, Player target, GameMode gameMode, Location endLocation, int position) {
        this.point1 = point1;
        this.point2 = point2;
        this.lengthSpeed = lengthSpeed;
        this.target = target;
        this.gameMode = gameMode;
        this.endLocation = endLocation;
        this.position = position;
    }

    public void send(List<Cinematic> cinematicList) {
        if (position < cinematicList.size()) {
            target.setGameMode(GameMode.SPECTATOR);
            target.setFlying(true);
            startOfCinematicView(target);
            new BukkitRunnable() {
                private int ticksElapsed = 0;
                private int totalTicks = lengthSpeed * 20;

                @Override
                public void run() {
                    ticksElapsed++;

                    double progress = (double) ticksElapsed / totalTicks;
                    Location intermediateLocation = interpolateLocation(point1, point2, progress);
                    whileInCinematicView(target);
                    target.teleport(intermediateLocation);

                    if (ticksElapsed >= totalTicks) {
                        target.setGameMode(gameMode);
                        target.setFlying(false);
                        endOfCinematicView(target);
                        if (endLocation != null) {
                            target.teleport(endLocation);
                        }
                        position++;
                        if (position < cinematicList.size()) {
                            cinematicList.get(position).send(cinematicList);
                        }
                        cancel();
                    }
                }
            }.runTaskTimer(MafanaCinematic.getInstance(), 0L, 1L);
        }
    }

    private Location interpolateLocation(Location loc1, Location loc2, double progress) {
        double x = loc1.getX() + (loc2.getX() - loc1.getX()) * progress;
        double y = loc1.getY() + (loc2.getY() - loc1.getY()) * progress;
        double z = loc1.getZ() + (loc2.getZ() - loc1.getZ()) * progress;
        float yaw = (float) (loc1.getYaw() + (loc2.getYaw() - loc1.getYaw()) * progress);
        float pitch = (float) (loc1.getPitch() + (loc2.getPitch() - loc1.getPitch()) * progress);
        return new Location(loc1.getWorld(), x, y, z, yaw, pitch);
    }


    public GameMode getGameMode() {
        return gameMode;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public Player getTarget() {
        return target;
    }

    public void setPoint1(Location point1) {
        this.point1 = point1;
    }

    public void setPoint2(Location point2) {
        this.point2 = point2;
    }

    public void setLengthSpeed(int lengthSpeed) {
        this.lengthSpeed = lengthSpeed;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLengthSpeed() {
        return lengthSpeed;
    }

    public Location getPoint1() {
        return point1;
    }

    public Location getPoint2() {
        return point2;
    }
}
