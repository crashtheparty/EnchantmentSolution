package org.ctp.enchantmentsolution.threads;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.Reflectionable;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

@SuppressWarnings("unused")
public class AbilityThreads implements Runnable, Reflectionable {

	@Override
	public void run() {
		runMethod(this, "noRest");
		runMethod(this, "unrest");
	}

	private void noRest() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.NO_REST)) return;
		for(Player player: Bukkit.getOnlinePlayers()) {
			if (isDisabled(player, RegisterEnchantments.NO_REST)) return;
			ItemStack helmet = player.getInventory().getHelmet();
			if (EnchantmentUtils.hasEnchantment(helmet, RegisterEnchantments.NO_REST)) player.setStatistic(Statistic.TIME_SINCE_REST, 0);
		}
	}

	private void unrest() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.UNREST)) return;
		for(Player player: Bukkit.getOnlinePlayers()) {
			if (isDisabled(player, RegisterEnchantments.UNREST)) return;
			ItemStack helmet = player.getInventory().getHelmet();
			if (EnchantmentUtils.hasEnchantment(helmet, RegisterEnchantments.UNREST)) if (player.getStatistic(Statistic.TIME_SINCE_REST) < 96000) player.setStatistic(Statistic.TIME_SINCE_REST, 96000);
		}
	}
}
