package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.Arrays;
import java.util.List;

//import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class FishingListener implements Listener{
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerFish(PlayerFishEvent event) {
		if(event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
			Item item = (Item)event.getCaught();
			ItemStack caught = item.getItemStack();
			Player player = event.getPlayer();
			ItemStack rod = player.getInventory().getItemInMainHand();
			if(Enchantments.hasEnchantment(rod, DefaultEnchantments.FRIED)) {
				if(DefaultEnchantments.isEnabled(DefaultEnchantments.FRIED)) {
					if(caught.getType().equals(Material.COD)) {
						caught.setType(Material.COOKED_COD);
					}else if(caught.getType().equals(Material.SALMON)) {
						caught.setType(Material.COOKED_SALMON);
					}
				}
			}
			if(Enchantments.hasEnchantment(rod, DefaultEnchantments.ANGLER)) {
				if(DefaultEnchantments.isEnabled(DefaultEnchantments.ANGLER)) {
					List<Material> fish = Arrays.asList(Material.COD, Material.COOKED_COD, Material.SALMON, Material.COOKED_SALMON, Material.TROPICAL_FISH, Material.PUFFERFISH);
					if(fish.contains(caught.getType())) {
						caught.setAmount(1 + Enchantments.getLevel(rod, DefaultEnchantments.ANGLER));
					}
				}
			}
			((Item) event.getCaught()).setItemStack(caught);
		}
	}
}
