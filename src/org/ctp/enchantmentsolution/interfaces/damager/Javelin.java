package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.JavelinEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagerThrewEntityCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.DamagerEffect;

public class Javelin extends DamagerEffect {

	public Javelin() {
		super(RegisterEnchantments.JAVELIN, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 0, 1, "1.25 * %level%", "1", false, false, true, false, new DamageCondition[] { new DamagerThrewEntityCondition(new MobData("TRIDENT")) });
	}

	@Override
	public DamagerResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		DamagerResult result = super.run(damager, damaged, items, event);
		if (result.getLevel() == 0) return null;
		double damage = result.getDamage();
		JavelinEvent javelin = new JavelinEvent((LivingEntity) damaged, result.getLevel(), (LivingEntity) damager, event.getDamage(), damage);
		Bukkit.getPluginManager().callEvent(javelin);
		if (!javelin.isCancelled()) {
			event.setDamage(javelin.getNewDamage());
			return new DamagerResult(result.getLevel(), javelin.getNewDamage());
		}
		return null;
	}

}
