package org.ctp.enchantmentsolution.interfaces.effects.damage;

import java.util.HashMap;

import org.bukkit.Location;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityDamageEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class LightningChanceEffect extends EntityDamageEffect {

	private double chancePercent = 1;
	private String chancePercentLevel = "1";
	private boolean usePercent, usePercentLevel;

	public LightningChanceEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double chancePercent, String chancePercentLevel, boolean usePercent, boolean usePercentLevel, DamageCondition... conditions) {
		super(enchantment, type, location, priority, conditions);
		this.chancePercent = chancePercent;
		this.chancePercentLevel = chancePercentLevel;
		this.usePercent = usePercent;
		this.usePercentLevel = usePercentLevel;
	}

	@Override
	public LightningResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		int level = getLevel(items);

		double chance = 0;
		if (willUsePercent()) chance += getChancePercent();
		if (willUsePercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getChancePercentLevel(), codes);
			chance += MathUtils.eval(percent);
		}
		return new LightningResult(level, chance, damaged.getLocation().clone());
	}

	public double getChancePercent() {
		return chancePercent;
	}

	public void setChancePercent(double chancePercent) {
		this.chancePercent = chancePercent;
	}

	public String getChancePercentLevel() {
		return chancePercentLevel;
	}

	public void setChancePercentLevel(String chancePercentLevel) {
		this.chancePercentLevel = chancePercentLevel;
	}

	public boolean willUsePercent() {
		return usePercent;
	}

	public void setUsePercent(boolean usePercent) {
		this.usePercent = usePercent;
	}

	public boolean willUsePercentLevel() {
		return usePercentLevel;
	}

	public void setUsePercentLevel(boolean usePercentLevel) {
		this.usePercentLevel = usePercentLevel;
	}

	public class LightningResult extends EffectResult {

		private final double chance;
		private final Location location;

		public LightningResult(int level, double chance, Location location) {
			super(level);
			this.chance = chance;
			this.location = location;
		}

		public double getChance() {
			return chance;
		}

		public Location getLocation() {
			return location;
		}
	}

}
