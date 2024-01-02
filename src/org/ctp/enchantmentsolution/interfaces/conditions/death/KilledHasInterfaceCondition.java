package org.ctp.enchantmentsolution.interfaces.conditions.death;

import java.util.Collection;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobInterface;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;

public class KilledHasInterfaceCondition implements DeathCondition {

	private final boolean opposite;
	private final MobInterface[] interfaces;

	public KilledHasInterfaceCondition(boolean opposite, MobInterface... interfaces) {
		this.opposite = opposite;
		this.interfaces = interfaces;
	}

	@Override
	public boolean metCondition(Entity killer, Entity killed, Collection<ItemStack> drops) {
		for(MobInterface inter: interfaces)
			if (inter.hasInterface(killed)) return !opposite;
		return opposite;
	}

}
