package org.runnerer.pvpcenter.pvp;

import org.bukkit.Bukkit;
import org.runnerer.pvpcenter.PVPCenter;
import org.runnerer.pvpcenter.pvp.melee.MeleeListener;
import org.runnerer.pvpcenter.pvp.ranged.BowListener;

public class PVPManager
{

    public static void registerEngines(PVPCenter plugin)
    {
        Bukkit.getPluginManager().registerEvents(new BowListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new MeleeListener(), plugin);
    }
}
