package org.runnerer.pvpcenter.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIEH implements Listener
{

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getHolder() instanceof IGUI) {
            e.setCancelled(true);
            IGUI gui = (IGUI) e.getInventory().getHolder();
            gui.onGUIClick((Player)e.getWhoClicked(), e.getRawSlot(), e.getCurrentItem());
        }
    }
}