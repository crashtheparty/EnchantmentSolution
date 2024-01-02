package org.ctp.enchantmentsolution.interfaces.death;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.entity.StreakDeathEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KillerExistsCondition;
import org.ctp.enchantmentsolution.interfaces.effects.death.KilledStreakEffect;
import org.ctp.enchantmentsolution.utils.abilityhelpers.Streak;
import org.ctp.enchantmentsolution.utils.player.ESEntity;

public class StreakDeath extends KilledStreakEffect {

	public StreakDeath() {
		super(RegisterEnchantments.STREAK, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, "streak", new DeathCondition[] { new KillerExistsCondition(false) });
	}

	@Override
	public KilledStreakResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		KilledStreakResult result = super.run(killer, killed, items, drops, event);

		StreakDeathEvent streak = new StreakDeathEvent((LivingEntity) killed, (LivingEntity) killer);
		Bukkit.getPluginManager().callEvent(streak);

		if (!streak.isCancelled()) {
			ESEntity entity = EnchantmentSolution.getESEntity(streak.getKiller());
			Streak s = result.getStreak();
			if (s == null) s = new Streak(entity);
			s.addToStreak(streak.getEntity());
			entity.setCustomValue(getField(), s);
			return result;
		}

		return null;
	}

}
