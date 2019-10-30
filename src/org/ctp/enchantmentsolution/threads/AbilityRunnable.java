package org.ctp.enchantmentsolution.threads;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.Reflectionable;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

@SuppressWarnings("unused")
public class AbilityRunnable implements Runnable, Reflectionable{
	
	@Override
	public void run() {
		runMethod(this, "noRest");
		runMethod(this, "unrest");
	}
	
	private void noRest() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			ItemStack helmet = player.getInventory().getHelmet();
			if(helmet != null && ItemUtils.hasEnchantment(helmet, RegisterEnchantments.NO_REST)){
				if(player.getStatistic(Statistic.TIME_SINCE_REST) > 72000
						&& player.getWorld().getTime() > 12540 && player.getWorld().getTime() < 23459) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.COFFEE_BREAK, "coffee");
				}
				player.setStatistic(Statistic.TIME_SINCE_REST, 0);
			}
		}
	}
	
	private void unrest() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			ItemStack helmet = player.getInventory().getHelmet();
			if(helmet != null && ItemUtils.hasEnchantment(helmet, RegisterEnchantments.UNREST)){
				if(player.getStatistic(Statistic.TIME_SINCE_REST) < 96000) {
					player.setStatistic(Statistic.TIME_SINCE_REST, 96000);
				}
			}
		}
	}
}
