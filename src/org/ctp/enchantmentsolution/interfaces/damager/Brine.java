package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.BrineEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.HealthPercentDamagedCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.DamagerEffect;

public class Brine extends DamagerEffect {

	public Brine() {
		super(RegisterEnchantments.BRINE, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 0, 2, "0", "1", false, true, false, false, new DamageCondition[] { new HealthPercentDamagedCondition(0.5, true) });
	}

	@Override
	public DamagerResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		DamagerResult result = super.run(damager, damaged, items, event);
		if (result.getLevel() == 0) return null;
		double damage = result.getDamage();
		BrineEvent brine = new BrineEvent((LivingEntity) damaged, (LivingEntity) damager, event.getDamage(), damage);
		Bukkit.getPluginManager().callEvent(brine);
		if (!brine.isCancelled()) {
			event.setDamage(brine.getNewDamage());
			return new DamagerResult(result.getLevel(), brine.getNewDamage());
		}
		return null;
	}
}
