package org.ctp.enchantmentsolution.interfaces.effects.death;

import java.util.Collection;
import java.util.HashMap;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.effects.DeathEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class SpawnOnDeathEffect extends DeathEffect {

	private double chance;
	private String chanceLevel;
	private boolean useChance, useChanceLevel;

	public SpawnOnDeathEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double chance, String chanceLevel, boolean useChance, boolean useChanceLevel, DeathCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.chance = chance;
		this.chanceLevel = chanceLevel;
		this.useChance = useChance;
		this.useChanceLevel = useChanceLevel;
	}

	@Override
	public DeathResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		double chance = 0;
		int level = getLevel(items);

		if (willUseChance()) chance += getChance();
		if (willUseChanceLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getChanceLevel(), codes);
			chance += MathUtils.eval(percent);
		}
		return new DeathResult(level, chance);
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

	public String getChanceLevel() {
		return chanceLevel;
	}

	public void setChanceLevel(String chanceLevel) {
		this.chanceLevel = chanceLevel;
	}

	public boolean willUseChance() {
		return useChance;
	}

	public void setUseChance(boolean useChance) {
		this.useChance = useChance;
	}

	public boolean willUseChanceLevel() {
		return useChanceLevel;
	}

	public void setUseChanceLevel(boolean useChanceLevel) {
		this.useChanceLevel = useChanceLevel;
	}

	public class DeathResult extends EffectResult {

		private final double chance;

		public DeathResult(int level, double chance) {
			super(level);
			this.chance = chance;
		}

		public double getChance() {
			return chance;
		}
	}

}
