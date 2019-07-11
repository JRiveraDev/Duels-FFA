package org.runnerer.pvpcenter.duels;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.runnerer.pvpcenter.PVPCenter;
import org.runnerer.pvpcenter.duels.data.DuelDatabase;
import org.runnerer.pvpcenter.duels.type.DuelType;
import org.runnerer.pvpcenter.stats.Stats;
import org.runnerer.pvpcenter.utils.C;

public class DuelEngine implements Listener
{

    public static void startDuel(Player duelee1, Player duelee2, DuelType type)
    {
        duelee1.teleport(type.getDuel1Location());
        duelee2.teleport(type.getDuel2Location());

        // Prepare.
        duelee1.setHealth(20);
        duelee1.setExhaustion(0);
        duelee1.setSaturation(3);
        duelee1.setFoodLevel(20);
        duelee2.setHealth(20);
        duelee2.setExhaustion(0);
        duelee2.setSaturation(3);
        duelee2.setFoodLevel(20);

        giveDuelItems(duelee1, duelee2);

        duelee1.sendMessage(C.Yellow + C.Bold + "DUELS MINIGAME");
        duelee1.sendMessage(C.Gray + "Fight the other person until the death!");
        duelee1.sendMessage(C.Gray + "Whoever kills the other person first wins!");
        duelee1.sendMessage(C.Gray + "");
        duelee1.sendMessage(C.Gray + "Opponent: " + C.Red + C.Bold + duelee2.getName());
    }

    public static void giveDuelItems(Player one, Player two)
    {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Dueling Sword");
        itemStack.setItemMeta(itemMeta);

        one.getInventory().addItem(itemStack);
        two.getInventory().addItem(itemStack);

        ItemStack wood = new ItemStack(Material.WOOD, 32);

        one.getInventory().addItem(wood);
        two.getInventory().addItem(wood);
        one.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE));
        two.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE));
        one.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        one.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        one.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        one.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        two.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        two.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        two.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        two.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));

        one.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET));
        two.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET));
        one.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
        two.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));

        Stats.addDuelsGamesPlayed(one.getUniqueId().toString(), 1);
        Stats.addDuelsGamesPlayed(two.getUniqueId().toString(), 1);
    }

    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent event)
    {
        if (!(event.getDamager() instanceof Player)) return;

        if (event.getEntity() instanceof Player)
        {
            Player loser = (Player) event.getEntity();

            if (!DuelDatabase.isIn1v1DuelMatch(loser)) return;

            if (loser.getHealth() - event.getDamage() >= 0)
            {
                event.setCancelled(true);
                loser.setHealth(20);

                Player winner = DuelDatabase.getOtherPlayer(loser);

                winner.setHealth(20);

                loser.sendMessage("");
                loser.sendMessage(C.Red + C.Bold + "YOU LOST THE MATCH!");
                loser.sendMessage(C.Red + "Winner's Health: " + winner.getHealth());
                loser.sendMessage("");

                winner.sendMessage("");
                winner.sendMessage(C.Green + C.Bold + "YOU WON THE MATCH!");
                winner.sendMessage(C.Red + "Your Health: " + winner.getHealth());
                winner.sendMessage("");

                winner.teleport(PVPCenter.getInstance().getSpawnLocation());
                loser.teleport(PVPCenter.getInstance().getSpawnLocation());

                winner.setFoodLevel(20);
                loser.setFoodLevel(20);

                winner.getInventory().clear();
                loser.getInventory().clear();
                loser.getInventory().setBoots(null);
                loser.getInventory().setLeggings(null);
                loser.getInventory().setChestplate(null);
                loser.getInventory().setHelmet(null);

                winner.getInventory().setBoots(null);
                winner.getInventory().setLeggings(null);
                winner.getInventory().setChestplate(null);
                winner.getInventory().setHelmet(null);

                DuelDatabase.removePlayer(winner);
                DuelDatabase.removePlayer(loser);

                Stats.addDuelsKills(winner.getUniqueId().toString(), 1);
                Stats.addDuelsWins(winner.getUniqueId().toString(), 1);

                if (((DuelDatabase.duelRequest.containsKey(winner) || DuelDatabase.duelRequest.containsValue(winner)) && ((DuelDatabase.duelRequest.containsKey(loser) || DuelDatabase.duelRequest.containsValue(loser)))
                {
                    try
                    {
                        DuelDatabase.duelRequest.remove(winner);
                        DuelDatabase.duelRequest.remove(loser);
                    } catch (Exception e)
                    {
                    
                    }
                }
                if (DuelDatabase.isAbleToStart())
                {
                    startDuel(DuelDatabase.duel1v1.get(1), DuelDatabase.duel1v1.get(2), DuelType.DUEL1_1V1);
                }
            }
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        if (!DuelDatabase.isIn1v1DuelMatch(player)) return;

        Player winner = DuelDatabase.getOtherPlayer(player);

        winner.sendMessage("");
        winner.sendMessage(C.Green + C.Bold + "YOU WON THE MATCH AS THE USER LEFT :(");
        winner.sendMessage(C.Red + "Your Health: " + winner.getHealth());
        winner.sendMessage("");

        winner.getInventory().clear();
        winner.setFoodLevel(20);
        winner.setHealth(20);
        winner.teleport(PVPCenter.getInstance().getSpawnLocation());

        DuelDatabase.removePlayer(winner);
        DuelDatabase.removePlayer(player);

        Stats.addDuelsWins(winner.getUniqueId().toString(), 1);

        if (DuelDatabase.isAbleToStart())
        {
            startDuel(DuelDatabase.duel1v1.get(1), DuelDatabase.duel1v1.get(2), DuelType.DUEL1_1V1);
        }
    }

    public static void sendRequest(Player requester, Player requestee)
    {
        requester.sendMessage(C.Green + "You sent a request to " + requestee.getName() + "!");
        requestee.sendMessage(C.Green + requester.getName() + " requested a duel with you. Type /duel accept " + requester.getName() + " to accept.");

        DuelDatabase.duelRequest.put(requester, requestee);
    }
}
