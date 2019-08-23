package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class UnrestPlayer extends AbilityPlayer{

	public UnrestPlayer(Player player, ItemStack item) {
		super(player, item, DefaultEnchantments.UNREST, true);
	}

	@Override
	protected void doEquip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, getEnchantment())) {
			if(getPlayer().getStatistic(Statistic.TIME_SINCE_REST) < 96000) {
				getPlayer().setStatistic(Statistic.TIME_SINCE_REST, 96000);
			}
			getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 305, 0, false, false), true);
		}
	}

	@Override
	protected void doUnequip(ItemStack item) {
		
	}

	@Override
	protected void doUnequip() {
		
	}

}
