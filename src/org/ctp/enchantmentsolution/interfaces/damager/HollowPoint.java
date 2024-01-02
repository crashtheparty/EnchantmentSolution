package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.HollowPointEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagedWearingArmorCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.DamagerEffect;

public class HollowPoint extends DamagerEffect {

	public HollowPoint() {
		super(RegisterEnchantments.HOLLOW_POINT, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 0, 1.25, "0", "1", false, true, false, false, new DamageCondition[] { new DamagedWearingArmorCondition(false) });
	}

	@Override
	public DamagerResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		DamagerResult result = super.run(damager, damaged, items, event);
		double damage = result.getDamage();
		if (result.getLevel() == 0) return null;

		HollowPointEvent hollowPoint = new HollowPointEvent((HumanEntity) damaged, (HumanEntity) damager, event.getDamage(), damage);
		Bukkit.getPluginManager().callEvent(hollowPoint);

		if (!hollowPoint.isCancelled()) {
			event.setDamage(hollowPoint.getNewDamage());
			return new DamagerResult(result.getLevel(), hollowPoint.getNewDamage());
		}
		return null;
	}
}
