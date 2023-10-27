package me.tahacheji.mafana.data;

import me.tahacheji.mafana.MafanaCinematic;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CubicBezierCinematic {

    private Location point1;
    private Location point2;
    private Location controlPoint1;
    private Location controlPoint2;
    private int lengthSpeed;

    public CubicBezierCinematic(Location point1, Location controlPoint1, Location controlPoint2, Location point2, int lengthSpeed) {
        this.point1 = point1;
        this.controlPoint1 = controlPoint1;
        this.controlPoint2 = controlPoint2;
        this.point2 = point2;
        this.lengthSpeed = lengthSpeed;
    }

    public void send(Player target, GameMode gameMode, Location endLocation) {
        target.setGameMode(GameMode.SPECTATOR);
        target.setFlying(true);
        new BukkitRunnable() {
            private int ticksElapsed = 0;
            private int totalTicks = lengthSpeed * 20;

            @Override
            public void run() {
                ticksElapsed++;

                double t = (double) ticksElapsed / totalTicks;
                Location intermediateLocation = cubicBezierInterpolation(point1, controlPoint1, controlPoint2, point2, t);

                target.teleport(intermediateLocation);

                if (ticksElapsed >= totalTicks) {
                    target.setGameMode(gameMode);
                    target.setFlying(false);
                    if(endLocation != null){
                        target.teleport(endLocation);
                    }
                    cancel();
                }
            }
        }.runTaskTimer(MafanaCinematic.getInstance(), 0L, 1L);
    }

    private Location cubicBezierInterpolation(Location p0, Location p1, Location p2, Location p3, double t) {
        double u = 1 - t;
        double tt = t * t;
        double uu = u * u;
        double uuu = uu * u;
        double ttu = tt * u;
        double uut = uu * t;

        double x = p0.getX() * uuu +
                3 * p1.getX() * uut +
                3 * p2.getX() * ttu +
                p3.getX() * tt;

        double y = p0.getY() * uuu +
                3 * p1.getY() * uut +
                3 * p2.getY() * ttu +
                p3.getY() * tt;

        double z = p0.getZ() * uuu +
                3 * p1.getZ() * uut +
                3 * p2.getZ() * ttu +
                p3.getZ() * tt;

        float yaw = (float) Math.toDegrees(Math.atan2(p3.getX() - p0.getX(), p3.getZ() - p0.getZ()));
        float pitch = (float) Math.toDegrees(Math.asin((p3.getY() - p0.getY()) / p3.distance(p0)));

        return new Location(p0.getWorld(), x, y, z, yaw, pitch);
    }

    private Location cubicInterpolate(Location loc1, Location controlPoint1, Location controlPoint2, Location loc2, double progress) {
        double t = 1.0 - progress;
        double tt = t * t;
        double ttt = tt * t;
        double u = progress * progress;
        double uu = u * progress;
        double x = ttt * loc1.getX() + 3 * tt * progress * controlPoint1.getX() + 3 * t * u * controlPoint2.getX() + uu * loc2.getX();
        double y = ttt * loc1.getY() + 3 * tt * progress * controlPoint1.getY() + 3 * t * u * controlPoint2.getY() + uu * loc2.getY();
        double z = ttt * loc1.getZ() + 3 * tt * progress * controlPoint1.getZ() + 3 * t * u * controlPoint2.getZ() + uu * loc2.getZ();
        float yaw = (float) (loc1.getYaw() + (loc2.getYaw() - loc1.getYaw()) * progress);
        float pitch = (float) (loc1.getPitch() + (loc2.getPitch() - loc1.getPitch()) * progress);
        return new Location(loc1.getWorld(), x, y, z, yaw, pitch);
    }


    public int getLengthSpeed() {
        return lengthSpeed;
    }

    public Location getPoint1() {
        return point1;
    }

    public Location getControlPoint1() {
        return controlPoint1;
    }

    public Location getControlPoint2() {
        return controlPoint2;
    }

    public Location getPoint2() {
        return point2;
    }
}
