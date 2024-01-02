package org.ctp.enchantmentsolution.interfaces.effects.item;

import java.util.HashMap;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ItemDamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.ItemDamageEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public abstract class ReduceItemDamageEffect extends ItemDamageEffect {

	private double reduceChance = 1;
	private String reduceChanceLevel = "1";
	private boolean useReduceChance, useReduceChanceLevel;

	public ReduceItemDamageEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double reduceChance, String reduceChanceLevel, boolean useReduceChance, boolean useReduceChanceLevel, ItemDamageCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.reduceChance = reduceChance;
		this.reduceChanceLevel = reduceChanceLevel;
		this.setUseReduceChance(useReduceChance);
		this.setUseReduceChanceLevel(useReduceChanceLevel);
	}

	@Override
	public ReduceItemDamageResult run(Player player, ItemStack item, PlayerItemDamageEvent event) {
		int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.TANK);
		
		double chance = 1;
		
		if (willUseReduceChance()) chance *= reduceChance;
		if (willUseReduceChanceLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getReduceChanceLevel(), codes);
			chance *= MathUtils.eval(percent);
		}

		int damage = event.getDamage();
		for(int i = 0; i < event.getDamage(); i++) {
			double random = Math.random();
			if (chance > random) damage--;
		}

		return new ReduceItemDamageResult(level, event.getDamage(), damage);
	}

	public double getReduceChance() {
		return reduceChance;
	}

	public void setReduceChance(double reduceChance) {
		this.reduceChance = reduceChance;
	}

	public String getReduceChanceLevel() {
		return reduceChanceLevel;
	}

	public void setReduceChanceLevel(String reduceChanceLevel) {
		this.reduceChanceLevel = reduceChanceLevel;
	}

	public boolean willUseReduceChance() {
		return useReduceChance;
	}

	public void setUseReduceChance(boolean useReduceChance) {
		this.useReduceChance = useReduceChance;
	}

	public boolean willUseReduceChanceLevel() {
		return useReduceChanceLevel;
	}

	public void setUseReduceChanceLevel(boolean useReduceChanceLevel) {
		this.useReduceChanceLevel = useReduceChanceLevel;
	}

	public class ReduceItemDamageResult extends EffectResult {

		private final int originalDamage, finalDamage;

		public ReduceItemDamageResult(int level, int originalDamage, int finalDamage) {
			super(level);
			this.originalDamage = originalDamage;
			this.finalDamage = finalDamage;
		}

		public int getOriginalDamage() {
			return originalDamage;
		}

		public int getFinalDamage() {
			return finalDamage;
		}
	}

}
