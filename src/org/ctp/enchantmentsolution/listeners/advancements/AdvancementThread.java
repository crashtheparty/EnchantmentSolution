package org.ctp.enchantmentsolution.listeners.advancements;

import org.bukkit.Bukkit;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class AdvancementThread implements Runnable{

	@Override
	public void run() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			boolean hasTank = true;
			for(ItemStack item : player.getInventory().getArmorContents()) {
				if(item == null || !(Enchantments.hasEnchantment(item, DefaultEnchantments.TANK) && 
						Enchantments.getLevel(item, DefaultEnchantments.TANK) == DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.TANK).getMaxLevel())) {
					hasTank = false;
				}
			}
			if(hasTank) {
				AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(ESAdvancement.PANZER_SOLDIER.getNamespace()));
				if(!progress.isDone()) {
					progress.awardCriteria("tank");
				}
			}
		}
	}

}
