package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.StreakEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.StreakTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.DamagerStreakEffect;

public class StreakDamage extends DamagerStreakEffect {

	public StreakDamage() {
		super(RegisterEnchantments.STREAK, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 0, 1, "0", "(%streak% + 100) / 100", false, false, false, true, "streak", new DamageCondition[] { new StreakTypeCondition("streak") });
	}

	@Override
	public DamagerResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		DamagerResult result = super.run(damager, damaged, items, event);
		int level = result.getLevel();
		if (level == 0) return null;

		StreakEvent streak = new StreakEvent((LivingEntity) damaged, (Player) damager, event.getDamage(), result.getDamage());
		Bukkit.getPluginManager().callEvent(streak);

		if (!streak.isCancelled()) {
			event.setDamage(streak.getNewDamage());
			return new DamagerResult(level, streak.getNewDamage());
		}
		return null;
	}

}
