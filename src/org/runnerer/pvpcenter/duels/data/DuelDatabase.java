package org.runnerer.pvpcenter.duels.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DuelDatabase
{

    public static ArrayList<Player> duel1v1 = new ArrayList<>();
    public static ArrayList<Player> duel2v2 = new ArrayList<>();
    public static HashMap<Player, Player> duelRequest = new HashMap<>();

    public static boolean isIn1v1DuelMatch(Player player)
    {
        if (duel1v1.size() <= 0) return false;

        if (duel1v1.size() == 1) return false;

        if (duel1v1.get(1) == player || duel1v1.get(2) == player) return true;

        return false;
    }

    public static boolean isAbleToStart()
    {
        if (duel1v1.size() <= 0) return false;

        if (duel1v1.size() == 1) return false;

        return true;
    }

    public static Player getOtherPlayer(Player player)
    {
        if (!isIn1v1DuelMatch(player)) return null;

        if (duel1v1.get(1) == player)
        {
            return duel1v1.get(2);
        } else
        {
            return duel1v1.get(1);
        }
    }

    public static void removePlayer(Player player)
    {
        duel1v1.remove(player);
    }

    public static void addPlayer(Player player)
    {
        duel1v1.add(player);
    }
}
