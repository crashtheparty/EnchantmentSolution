package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class UnrestPlayer extends AbilityPlayer{

	private int refreshRate = 80, refresh = 0;
	
	public UnrestPlayer(Player player, ItemStack item) {
		super(player, item, DefaultEnchantments.UNREST);
	}

	public void setItem(ItemStack item, boolean constructor) {
		setPreviousItem(item);
		ItemStack previousItem = getPreviousItem();
		if(item == null && previousItem != null){
			doUnequip(previousItem);
		}else if(item != null && previousItem == null){
			doEquip(item);
		}else if(previousItem != null && item != null){
			if (!item.toString().equalsIgnoreCase(
					previousItem.toString())) {
				doUnequip(previousItem);
				doEquip(item);
				refresh = 0;
			} else {
				refresh++;
				if(refresh >= refreshRate) {
					doEquip(item);
					refresh = 0;
				}
			}
		}else if(item == null && previousItem == null && constructor) {
			doUnequip();
		}
		this.item = item;
	}
	
	@Override
	protected void doEquip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, getEnchantment())) {
			if(getPlayer().getStatistic(Statistic.TIME_SINCE_REST) < 96000) {
				getPlayer().setStatistic(Statistic.TIME_SINCE_REST, 96000);
			}
			getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000000, 0, false, false), true);
		}
	}

	@Override
	protected void doUnequip(ItemStack item) {
		if(Enchantments.hasEnchantment(item, getEnchantment())) {
			getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 160, 0, false, false), true);
		}
	}

	@Override
	protected void doUnequip() {
		if(getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION) && getPlayer().getPotionEffect(PotionEffectType.NIGHT_VISION).getDuration() > 100000000) {
			getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 160, 0, false, false), true);
		}
	}

}
