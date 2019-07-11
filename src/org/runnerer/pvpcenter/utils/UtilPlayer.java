package org.runnerer.pvpcenter.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class UtilPlayer
{
    public static boolean playerInArea(Player k, Location loc1, Location loc2)
    {
        double[] x = {loc1.getX(), loc2.getX()};
        double[] y = {loc1.getY(), loc2.getY()};
        double[] z = {loc1.getZ(), loc2.getZ()};

        Arrays.sort(x);
        Arrays.sort(y);
        Arrays.sort(z);

        if(k.getLocation().getX() >= x[0] && k.getLocation().getX() <= x[1] && k.getLocation().getY() >= y[0] && k.getLocation().getY() <= y[1] && k.getLocation().getZ() >= z[0] && k.getLocation().getZ() <= z[1]) return true;

        return false;
    }
}
