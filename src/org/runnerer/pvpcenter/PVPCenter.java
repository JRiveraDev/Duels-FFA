package org.runnerer.pvpcenter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.runnerer.pvpcenter.database.MySQL;
import org.runnerer.pvpcenter.duels.DuelEngine;
import org.runnerer.pvpcenter.ffa.FFAEngine;
import org.runnerer.pvpcenter.pvp.PVPManager;

public class PVPCenter extends JavaPlugin
{
    public static PVPCenter instance;

    @Override
    public void onEnable()
    {
        instance = this;

        PVPManager.registerEngines(this);
        registerEngine(new DuelEngine());
        registerEngine(new FFAEngine());

        new MySQL();
        try
        {
            MySQL.Instance.openConnection();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void registerEngine(Listener listener)
    {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public static PVPCenter getInstance()
    {
        return instance;
    }

    public Location getSpawnLocation() { return new Location(Bukkit.getWorld("world"), 0, 103, 0); }
}
