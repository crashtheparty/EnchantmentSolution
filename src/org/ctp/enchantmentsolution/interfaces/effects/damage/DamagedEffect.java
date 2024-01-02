package org.ctp.enchantmentsolution.interfaces.effects.damage;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityDamageEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class DamagedEffect extends EntityDamageEffect {

	private double incAmount = 0, incPercent = 1;
	private String incAmountLevel = "0", incPercentLevel = "1";
	private boolean useAmount, usePercent, useAmountLevel, usePercentLevel;

	public DamagedEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority, double incAmount,
	double incPercent, String incAmountLevel, String incPercentLevel, boolean useAmount, boolean usePercent, boolean useAmountLevel, boolean usePercentLevel,
	DamageCondition... conditions) {
		super(enchantment, type, location, priority, conditions);
		this.incAmount = incAmount;
		this.incPercent = incPercent;
		this.incAmountLevel = incAmountLevel;
		this.incPercentLevel = incPercentLevel;
		this.useAmount = useAmount;
		this.usePercent = usePercent;
		this.useAmountLevel = useAmountLevel;
		this.usePercentLevel = usePercentLevel;
	}

	@Override
	public DamagedResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		int level = getLevel(items);

		double damage = event.getDamage();
		if (willUsePercent()) damage *= getIncPercent();
		if (willUseAmount()) damage += getIncAmount();
		if (willUsePercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getIncPercentLevel(), codes);
			damage *= MathUtils.eval(percent);
		}
		if (willUseAmountLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getIncAmountLevel(), codes);
			damage += MathUtils.eval(percent);
		}
		damage = Math.max(damage, 0);
		return new DamagedResult(level, damage);
	}

	public void setIncAmount(double incAmount) {
		this.incAmount = incAmount;
	}

	public void setIncPercent(double incPercent) {
		this.incPercent = incPercent;
	}

	public void setIncAmountLevel(String incAmountLevel) {
		this.incAmountLevel = incAmountLevel;
	}

	public void setIncPercentLevel(String incPercentLevel) {
		this.incPercentLevel = incPercentLevel;
	}

	public void setUseAmount(boolean useAmount) {
		this.useAmount = useAmount;
	}

	public void setUsePercent(boolean usePercent) {
		this.usePercent = usePercent;
	}

	public void setUseAmountLevel(boolean useAmountLevel) {
		this.useAmountLevel = useAmountLevel;
	}

	public void setUsePercentLevel(boolean usePercentLevel) {
		this.usePercentLevel = usePercentLevel;
	}

	public double getIncAmount() {
		return incAmount;
	}

	public double getIncPercent() {
		return incPercent;
	}

	public String getIncAmountLevel() {
		return incAmountLevel;
	}

	public String getIncPercentLevel() {
		return incPercentLevel;
	}

	public boolean willUseAmount() {
		return useAmount;
	}

	public boolean willUsePercent() {
		return usePercent;
	}

	public boolean willUseAmountLevel() {
		return useAmountLevel;
	}

	public boolean willUsePercentLevel() {
		return usePercentLevel;
	}

	public class DamagedResult extends EffectResult {

		private final double damage;

		public DamagedResult(int level, double damage) {
			super(level);
			this.damage = damage;
		}

		public double getDamage() {
			return damage;
		}
	}

}