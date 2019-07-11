package org.runnerer.pvpcenter.pvp.melee;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.runnerer.pvpcenter.duels.data.DuelDatabase;
import org.runnerer.pvpcenter.ffa.FFAEngine;

public class MeleeListener implements Listener
{
    @EventHandler
    public void armorSound(EntityDamageByEntityEvent event)
    {
        Entity damagee = event.getEntity();

        if (damagee instanceof Player)
        {
            Player player = (Player) damagee;

            double r = Math.random();

            Sound sound = null;
            if (r > 0.25) sound = Sound.ITEM_BREAK;
            else sound = Sound.BLAZE_HIT;

            player.getWorld().playSound(player.getLocation(), sound, 1.6236523f, 0.642615f);
        }
    }

    @EventHandler
    public void damagePrevention(EntityDamageByEntityEvent event)
    {
        if (!(event.getEntity() instanceof Player)) return;

        if (FFAEngine.playing.contains(event.getEntity())) return;
        if (DuelDatabase.isIn1v1DuelMatch((Player)event.getEntity())) return;

        event.setCancelled(true);
    }
}
