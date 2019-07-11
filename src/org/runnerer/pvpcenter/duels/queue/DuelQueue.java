package org.runnerer.pvpcenter.duels.queue;

import org.bukkit.entity.Player;
import org.runnerer.pvpcenter.duels.data.DuelDatabase;

public class DuelQueue
{

    public void addPlayerToQueue(Player player)
    {
        DuelDatabase.addPlayer(player);
    }
}
