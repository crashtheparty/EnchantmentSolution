package org.ctp.enchantmentsolution.interfaces.effects.damage;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.utils.MathUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.Streak;
import org.ctp.enchantmentsolution.utils.player.ESEntity;

public abstract class DamagerStreakEffect extends DamagerEffect {

	private final String field;

	public DamagerStreakEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double incAmount, double incPercent, String incAmountLevel, String incPercentLevel, boolean useAmount, boolean usePercent, boolean useAmountLevel,
	boolean usePercentLevel, String field, DamageCondition[] conditions) {
		super(enchantment, type, location, priority, incAmount, incPercent, incAmountLevel, incPercentLevel, useAmount, usePercent, useAmountLevel, usePercentLevel, conditions);
		this.field = field;
	}

	@Override
	public DamagerResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		int level = getLevel(items);

		double damage = event.getDamage();
		if (willUsePercent()) damage *= getIncPercent();
		if (willUseAmount()) damage += getIncAmount();
		if (willUsePercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			ESEntity entity = EnchantmentSolution.getESEntity((LivingEntity) damager);
			Streak s = entity.getCustomValue(Streak.class, field);
			if (s != null) {
				double streak = s.getStreak();
				codes.put("%" + field + "%", streak);
			} else
				codes.put("%" + field + "%", 0);
			String percent = Chatable.get().getMessage(getIncPercentLevel(), codes);
			damage *= MathUtils.eval(percent);
		}
		if (willUseAmountLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			ESEntity entity = EnchantmentSolution.getESEntity((LivingEntity) damager);
			Streak s = entity.getCustomValue(Streak.class, field);
			if (s != null) {
				double streak = s.getStreak();
				codes.put("%" + field + "%", streak);
			} else
				codes.put("%" + field + "%", 0);
			String percent = Chatable.get().getMessage(getIncAmountLevel(), codes);
			damage += MathUtils.eval(percent);
		}
		damage = Math.max(damage, 0);
		return new DamagerResult(level, damage);
	}

	public String getField() {
		return field;
	}

}
