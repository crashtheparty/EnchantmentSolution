package org.ctp.enchantmentsolution.events.modify.player;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.multitype.KnockUpEvent;

public class KnockUpPlayerEvent extends ModifyPlayerVelocityEvent implements KnockUpEvent {

	private LivingEntity damager;

	public KnockUpPlayerEvent(Player damaged, int level, LivingEntity damager, double x, double y, double z) {
		super(damaged, new EnchantmentLevel(CERegister.KNOCKUP, level), x, y, z);
		setDamager(damager);
	}

	@Override
	public LivingEntity getDamager() {
		return damager;
	}

	@Override
	public void setDamager(LivingEntity damager) {
		this.damager = damager;
	}
}
