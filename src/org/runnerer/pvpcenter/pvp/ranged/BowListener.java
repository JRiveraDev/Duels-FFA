package org.runnerer.pvpcenter.pvp.ranged;

import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.runnerer.pvpcenter.utils.C;
import org.runnerer.pvpcenter.utils.F;

public class BowListener implements Listener
{

    @EventHandler
    public void bowDing(EntityDamageByEntityEvent event)
    {
        Entity entity = event.getDamager();
        if (!(entity instanceof Arrow) || !(event.getEntity() instanceof Player)) return;

        Arrow arrow = (Arrow)entity;

        if ((arrow.getShooter() instanceof Player))
        {
            Player shooter = (Player) arrow.getShooter();
            Player shootee = (Player) event.getEntity();

            shooter.sendMessage(C.Yellow + shootee.getName() + "'s health is at " + shootee.getHealth() + "!");
            shooter.getWorld().playSound(shooter.getLocation(), Sound.ORB_PICKUP, 1, 1);

            shootee.getWorld().playSound(shooter.getLocation(), Sound.ORB_PICKUP, 1, 1);
        }
    }
}
