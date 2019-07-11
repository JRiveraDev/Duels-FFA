package org.runnerer.pvpcenter.duels.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.runnerer.pvpcenter.duels.DuelEngine;
import org.runnerer.pvpcenter.gui.IGUI;

public class DuelRequestUI implements IGUI
{
    private Player requester;
    private Player requestee;

    public DuelRequestUI(Player requester, Player requestee)
    {
        this.requester = requester;
        this.requestee = requestee;
    }

    @Override
    public Inventory getInventory() {
        Inventory GUI = Bukkit.createInventory(this, 9, "Request to " + requestee.getName());
        GUI.setItem(4, new ItemStack(Material.DIAMOND_SWORD));
        return GUI;
    }

    @Override
    public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem) {

        if(clickedItem == null || clickedItem.getType().equals(Material.AIR))
            return;

        if(clickedItem.getType() == Material.DIAMOND_SWORD)
        {
            DuelEngine.sendRequest(whoClicked, requestee);
        }
    }
}
