package org.ctp.enchantmentsolution.interfaces.conditions.damage;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;

public class HealthPercentDamagerCondition implements DamageCondition {

	private double percent;
	private boolean less;

	public HealthPercentDamagerCondition(double percent, boolean less) {
		this.percent = percent;
		this.less = less;
	}

	@Override
	public boolean metCondition(Entity damager, Entity damaged, EntityDamageByEntityEvent event) {
		if (damager instanceof Damageable && damager instanceof Attributable) {
			Damageable d = (Damageable) damager;
			AttributeInstance instance = ((Attributable) damager).getAttribute(Attribute.GENERIC_MAX_HEALTH);
			if (less && instance.getValue() >= d.getHealth() * percent || !less && instance.getValue() <= d.getHealth() * percent) return true;
		}
		return false;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public boolean isLess() {
		return less;
	}

	public void setLess(boolean less) {
		this.less = less;
	}

}
