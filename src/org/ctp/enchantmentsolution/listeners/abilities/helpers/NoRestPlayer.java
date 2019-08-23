package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class NoRestPlayer extends AbilityPlayer{

	public NoRestPlayer(Player player, ItemStack item) {
		super(player, item, DefaultEnchantments.NO_REST, true);
	}

	@Override
	protected void doEquip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, getEnchantment())){
			if(getPlayer().getStatistic(Statistic.TIME_SINCE_REST) > 72000
					&& getPlayer().getWorld().getTime() > 12540 && getPlayer().getWorld().getTime() < 23459) {
				AdvancementUtils.awardCriteria(getPlayer(), ESAdvancement.COFFEE_BREAK, "coffee");
			}
			getPlayer().setStatistic(Statistic.TIME_SINCE_REST, 0);
		}
	}

	@Override
	protected void doUnequip(ItemStack item) {
		
	}
	
	@Override
	protected void doUnequip() {
		
	}
}
