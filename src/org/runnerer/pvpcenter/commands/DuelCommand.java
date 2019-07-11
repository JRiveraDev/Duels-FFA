package org.runnerer.pvpcenter.commands;

import com.sun.rowset.internal.Row;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.runnerer.pvpcenter.PVPCenter;
import org.runnerer.pvpcenter.duels.DuelEngine;
import org.runnerer.pvpcenter.duels.data.DuelDatabase;
import org.runnerer.pvpcenter.duels.type.DuelType;
import org.runnerer.pvpcenter.duels.ui.DuelRequestUI;
import org.runnerer.pvpcenter.utils.C;
import org.runnerer.pvpcenter.utils.F;
import org.runnerer.pvpcenter.utils.InventoryUtil;

public class DuelCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player)) return false;

        Player caller = (Player) sender;

        if (command.getName().equalsIgnoreCase("duel"))
        {
            if (args.length == 0 || args == null)
            {
                caller.sendMessage(F.main("Duels", "/duel <player> to duel!"));
                return true;
            }


            if (args.length == 2)
            {
                if (args[0].equalsIgnoreCase("accept"))
                {
                    Player player = Bukkit.getPlayer(args[1]);

                    if (player == null)
                    {
                        caller.sendMessage(C.Red + "That player is not online!");
                        return true;
                    }

                    if (DuelDatabase.duelRequest.containsKey(caller) && DuelDatabase.duelRequest.containsKey(player))
                    {
                        DuelEngine.startDuel(caller, player, DuelType.DUEL1_1V1);
                    }
                }
            } else if (args.length == 1)
            {


                Player player = Bukkit.getPlayer(args[0]);

                if (player == null)
                {
                    caller.sendMessage(C.Red + "That player is not online!");
                    return true;
                }

                DuelRequestUI gui = new DuelRequestUI(caller, Bukkit.getPlayer(args[0]));
                caller.openInventory(gui.getInventory());
            } else
            {
                caller.sendMessage(F.main("Duels", "/duel <player> to duel!"));
            }
        }
        return true;
    }
}
