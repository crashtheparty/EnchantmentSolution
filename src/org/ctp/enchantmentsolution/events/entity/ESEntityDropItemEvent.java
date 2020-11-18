package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public abstract class ESEntityDropItemEvent extends ESEntityEvent {

	private final Player killer;
	private final ItemStack item;

	public ESEntityDropItemEvent(LivingEntity who, Player killer, EnchantmentLevel enchantment, ItemStack item) {
		super(who, enchantment);
		this.killer = killer;
		this.item = item;
	}

	@Override
	public LivingEntity getEntity() {
		return (LivingEntity) super.getEntity();
	}

	public Player getKiller() {
		return killer;
	}

	public ItemStack getItem() {
		return item;
	}

}
