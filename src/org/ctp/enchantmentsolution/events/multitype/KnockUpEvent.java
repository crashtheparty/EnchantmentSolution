package org.ctp.enchantmentsolution.events.multitype;

import org.bukkit.entity.LivingEntity;

public interface KnockUpEvent {

	public double getX();

	public double getY();

	public double getZ();

	public LivingEntity getDamager();

	public void setDamager(LivingEntity damager);

	public boolean isCancelled();
}
