package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class FishingListener extends EnchantmentListener{
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerFish(PlayerFishEvent event) {
		if(event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
			Item item = (Item)event.getCaught();
			ItemStack caught = item.getItemStack();
			Player player = event.getPlayer();
			ItemStack rod = player.getInventory().getItemInMainHand();
			if(Enchantments.hasEnchantment(rod, DefaultEnchantments.FRIED)) {
				if(canRun(DefaultEnchantments.FRIED, event)) {
					if (caught.getType().equals(Material.COD)) {
						caught.setType(Material.COOKED_COD);
						AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(ESAdvancement.FISH_STICKS.getNamespace()));
						if(progress.getRemainingCriteria().contains("cooked")) {
							progress.awardCriteria("cooked");
						}
					} else if (caught.getType().equals(Material.SALMON)) {
						caught.setType(Material.COOKED_SALMON);
						AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(ESAdvancement.FISH_STICKS.getNamespace()));
						if(progress.getRemainingCriteria().contains("cooked")) {
							progress.awardCriteria("cooked");
						}
					} else if (caught.getType() == Material.TROPICAL_FISH) {
						AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(ESAdvancement.NEMO_ENIM_COQUIT.getNamespace()));
						if(progress.getRemainingCriteria().contains("tropical_fish")) {
							progress.awardCriteria("tropical_fish");
						}
					}
				}
			}
			if(Enchantments.hasEnchantment(rod, DefaultEnchantments.ANGLER)) {
				if(canRun(DefaultEnchantments.ANGLER, event)) {
					List<Material> fish = Arrays.asList(Material.COD, Material.COOKED_COD, Material.SALMON, Material.COOKED_SALMON, Material.TROPICAL_FISH, Material.PUFFERFISH);
					if(fish.contains(caught.getType())) {
						caught.setAmount(1 + Enchantments.getLevel(rod, DefaultEnchantments.ANGLER));
						AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(ESAdvancement.FED_FOR_A_LIFETIME.getNamespace()));
						Material type = caught.getType();
						if(type == Material.COOKED_COD) type = Material.COD;
						if(type == Material.COOKED_SALMON) type = Material.SALMON;
						if(progress.getRemainingCriteria().contains(type.name().toLowerCase())) {
							progress.awardCriteria(type.name().toLowerCase());
						}
					}
				}
			}
			((Item) event.getCaught()).setItemStack(caught);
		}
	}
}
