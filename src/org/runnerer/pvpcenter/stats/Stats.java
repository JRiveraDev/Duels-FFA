package org.runnerer.pvpcenter.stats;

import org.runnerer.pvpcenter.database.MySQL;
import org.runnerer.pvpcenter.stats.type.StatType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Stats
{
    public static String retrieveDuelStats(String uuid, StatType statType)
    {
        try
        {
            if (statType == StatType.DUELS_KILLS)
            {
                ResultSet resultSet = MySQL.querySQL("SELECT * FROM duelStats WHERE uuid = '" + uuid + "';");

                while (resultSet.next())
                {
                    String kills = resultSet.getString("kills");

                    return kills;
                }
            } else if (statType == StatType.DUELS_WINS)
            {
                ResultSet resultSet = MySQL.querySQL("SELECT * FROM duelStats WHERE uuid = '" + uuid + "';");

                while (resultSet.next())
                {
                    String wins = resultSet.getString("wins");

                    return wins;
                }
            } else if (statType == StatType.DUELS_GAMES_PLAYED)
            {
                ResultSet resultSet = MySQL.querySQL("SELECT * FROM duelStats WHERE uuid = '" + uuid + "';");

                while (resultSet.next())
                {
                    String wins = resultSet.getString("games");

                    return wins;
                }
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String retrieveFFAStats(String uuid, StatType statType)
    {
        try
        {
            if (statType == StatType.FFA_KILLS)
            {
                ResultSet resultSet = MySQL.querySQL("SELECT * FROM ffaStats WHERE uuid = '" + uuid + "';");

                while (resultSet.next())
                {
                    String kills = resultSet.getString("kills");

                    return kills;
                }
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void addDuelsKills(String uuid, int amount)
    {
        try
        {
            MySQL.updateSQL("UPDATE duelStats SET kills='" + retrieveDuelStats(uuid, StatType.DUELS_KILLS) + amount + "' WHERE uuid='" + uuid + "';");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void addDuelsGamesPlayed(String uuid, int amount)
    {
        try
        {
            MySQL.updateSQL("UPDATE duelStats SET games='" + retrieveDuelStats(uuid, StatType.DUELS_GAMES_PLAYED) + amount + "' WHERE uuid='" + uuid + "';");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void addFFAKills(String uuid, int amount)
    {
        try
        {
            MySQL.updateSQL("UPDATE ffaStats SET kills='" + retrieveDuelStats(uuid, StatType.FFA_KILLS) + amount + "' WHERE uuid='" + uuid + "';");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void addFFADeaths(String uuid, int amount)
    {
        try
        {
            MySQL.updateSQL("UPDATE ffaStats SET deaths='" + retrieveDuelStats(uuid, StatType.FFA_DEATHS) + amount + "' WHERE uuid='" + uuid + "';");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void addDuelsWins(String uuid, int amount)
    {
        try
        {
            MySQL.updateSQL("UPDATE duelStats SET wins='" + retrieveDuelStats(uuid, StatType.DUELS_WINS) + amount + "' WHERE uuid='" + uuid + "';");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
