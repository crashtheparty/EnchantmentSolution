package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class StreakDeathEvent extends ESEntityDeathEvent {

	private final Player player;

	public StreakDeathEvent(LivingEntity who, Player player) {
		super(who, new EnchantmentLevel(CERegister.STREAK, 1));
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public LivingEntity getEntity() {
		return (LivingEntity) super.getEntity();
	}

}
