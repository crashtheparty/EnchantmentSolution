package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.LancerEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagerRidingEntityCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.DamagerEffect;

public class Lancer extends DamagerEffect {

	public Lancer() {
		super(RegisterEnchantments.LANCER, EnchantmentMultipleType.ALL, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 0, 1, "1.25 * %level%", "1", false, false, true, false, new DamageCondition[] { new DamagerRidingEntityCondition(false, new MobData("HORSE"), new MobData("SKELETON_HORSE"), new MobData("DONKEY"), new MobData("MULE")) });
	}
	
	@Override
	public DamagerResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		DamagerResult result = super.run(damager, damaged, items, event);
		if (result.getLevel() == 0) return null;
		double damage = result.getDamage();
		LancerEvent lancer = new LancerEvent((LivingEntity) damaged, result.getLevel(), (LivingEntity) damager, event.getDamage(), damage);
		Bukkit.getPluginManager().callEvent(lancer);
		if (!lancer.isCancelled()) {
			event.setDamage(lancer.getNewDamage());
			return new DamagerResult(result.getLevel(), lancer.getNewDamage());
		}
		return null;
	}

}
