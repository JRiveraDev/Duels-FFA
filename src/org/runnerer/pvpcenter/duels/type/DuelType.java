package org.runnerer.pvpcenter.duels.type;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum DuelType
{

    DUEL1_1V1("Haunted Streets", new Location(Bukkit.getWorld("Map1"), 0, 132, 25), new Location(Bukkit.getWorld("Map1"), 0, 132, -25)),
    DUEL2_1V1("Monster House", new Location(Bukkit.getWorld("Map1"), 0, 132, 25), new Location(Bukkit.getWorld("Map1"), 0, 132, -25)),
    DUEL1_2V2("The Mall", new Location(Bukkit.getWorld("Map1"), 0, 132, 25), new Location(Bukkit.getWorld("Map1"), 0, 132, -25));

    Location duel1Location;
    Location duel2Location;
    String duelName;

    DuelType(String name, Location location1, Location location2)
    {
        this.duel1Location = location1;
        this.duel2Location = location2;
        this.duelName = name;
    }

    public String getMapName()
    {
        return duelName;
    }

    public Location getDuel1Location()
    {
        return duel1Location;
    }

    public Location getDuel2Location()
    {
        return duel2Location;
    }
}
