package org.ctp.enchantmentsolution.interfaces.effects.death;

import java.util.Collection;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.effects.DeathEffect;
import org.ctp.enchantmentsolution.utils.abilityhelpers.Streak;
import org.ctp.enchantmentsolution.utils.player.ESEntity;

public abstract class KilledStreakEffect extends DeathEffect {
	
	private final String field;

	public KilledStreakEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	String field, DeathCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.field = field;
	}

	@Override
	public KilledStreakResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		int level = getLevel(items);
		
		ESEntity entity = EnchantmentSolution.getESEntity((LivingEntity) killer);
		Streak streak = entity.getCustomValue(Streak.class, field);
		
		return new KilledStreakResult(level, streak);
	}
	
	public String getField() {
		return field;
	}

	public class KilledStreakResult extends EffectResult {

		private final Streak streak;
		
		public KilledStreakResult(int level, Streak streak) {
			super(level);
			this.streak = streak;
		}

		public Streak getStreak() {
			return streak;
		}
		
	}

}
