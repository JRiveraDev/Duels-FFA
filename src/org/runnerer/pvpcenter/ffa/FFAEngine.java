package org.runnerer.pvpcenter.ffa;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.runnerer.pvpcenter.PVPCenter;
import org.runnerer.pvpcenter.duels.data.DuelDatabase;
import org.runnerer.pvpcenter.stats.Stats;
import org.runnerer.pvpcenter.utils.C;
import org.runnerer.pvpcenter.utils.UtilPlayer;

import java.util.ArrayList;

public class FFAEngine implements Listener
{
    public static ArrayList<Player> playing = new ArrayList<>();

    @EventHandler
    public void playingFFA(PlayerMoveEvent event)
    {
        if (playing.contains(event.getPlayer())) return;

        if (DuelDatabase.isIn1v1DuelMatch(event.getPlayer())) return;

        if (UtilPlayer.playerInArea(event.getPlayer(), new Location(Bukkit.getWorld("world"), 0, 100, 100), new Location(Bukkit.getWorld("world"), 100, 100, 0))) return;

        playing.add(event.getPlayer());
        event.getPlayer().sendMessage(C.Green + "You are now playing in FFA!");

        event.getPlayer().getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        event.getPlayer().getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        event.getPlayer().getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        event.getPlayer().getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta itemMeta = diamondSword.getItemMeta();
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        diamondSword.setItemMeta(itemMeta);

        event.getPlayer().getInventory().addItem(diamondSword);
        event.getPlayer().getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 35));
    }

    @EventHandler
    public void ffaQuit(PlayerQuitEvent event)
    {
        if (!playing.contains(event.getPlayer())) return;

        playing.remove(event.getPlayer());
    }

    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent event)
    {
        if (!(event.getDamager() instanceof Player)) return;

        if (event.getEntity() instanceof Player)
        {
            Player loser = (Player) event.getEntity();

            if (DuelDatabase.isIn1v1DuelMatch(loser)) return;

            if (!playing.contains(loser)) return;

            if (loser.getHealth() - event.getDamage() >= 0)
            {
                event.setCancelled(true);
                loser.setHealth(20);

                Player winner = (Player)event.getDamager();

                winner.sendMessage(C.Gray + "You killed " + loser.getName() + "!");

                loser.sendMessage("You died to " + winner.getName() + ".");

                loser.teleport(PVPCenter.getInstance().getSpawnLocation());

                loser.setFoodLevel(20);

                loser.getInventory().clear();
                loser.getInventory().setBoots(null);
                loser.getInventory().setLeggings(null);
                loser.getInventory().setChestplate(null);
                loser.getInventory().setHelmet(null);

                playing.remove(loser);

                Stats.addFFAKills(winner.getUniqueId().toString(), 1);
                Stats.addFFADeaths(loser.getUniqueId().toString(), 1);
            }
        }
    }
}
