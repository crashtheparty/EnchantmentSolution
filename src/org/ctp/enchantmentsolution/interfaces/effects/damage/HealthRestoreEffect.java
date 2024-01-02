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

public abstract class HealthRestoreEffect extends EntityDamageEffect {

	private double restoreAmount = 0, restorePercent = 0;
	private String restoreAmountLevel = "0", restorePercentLevel = "0";
	private boolean useAmount, usePercent, useAmountLevel, usePercentLevel;

	public HealthRestoreEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double restoreAmount, double restorePercent, String restoreAmountLevel, String restorePercentLevel, boolean useAmount, boolean usePercent,
	boolean useAmountLevel, boolean usePercentLevel, DamageCondition... conditions) {
		super(enchantment, type, location, priority, conditions);
		this.restoreAmount = restoreAmount;
		this.restorePercent = restorePercent;
		this.restoreAmountLevel = restoreAmountLevel;
		this.restorePercentLevel = restorePercentLevel;
		this.usePercentLevel = usePercentLevel;
		this.useAmount = useAmount;
		this.usePercent = usePercent;
		this.useAmountLevel = useAmountLevel;
		this.usePercentLevel = usePercentLevel;
	}

	@Override
	public HealthResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		int level = getLevel(items);

		double damage = event.getDamage();
		double restore = 0;
		if (willUsePercent()) restore += damage * getRestorePercent();
		if (willUseAmount()) restore += getRestoreAmount();
		if (willUsePercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getRestorePercentLevel(), codes);
			restore += damage * MathUtils.eval(percent);
		}
		if (willUseAmountLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getRestoreAmountLevel(), codes);
			restore += MathUtils.eval(percent);
		}
		restore = Math.max(restore, 0);
		return new HealthResult(level, restore);
	}

	public double getRestoreAmount() {
		return restoreAmount;
	}

	public void setRestoreAmount(double restoreAmount) {
		this.restoreAmount = restoreAmount;
	}

	public double getRestorePercent() {
		return restorePercent;
	}

	public void setRestorePercent(double restorePercent) {
		this.restorePercent = restorePercent;
	}

	public String getRestoreAmountLevel() {
		return restoreAmountLevel;
	}

	public void setRestoreAmountLevel(String restoreAmountLevel) {
		this.restoreAmountLevel = restoreAmountLevel;
	}

	public String getRestorePercentLevel() {
		return restorePercentLevel;
	}

	public void setRestorePercentLevel(String restorePercentLevel) {
		this.restorePercentLevel = restorePercentLevel;
	}

	public boolean willUseAmount() {
		return useAmount;
	}

	public void setUseAmount(boolean useAmount) {
		this.useAmount = useAmount;
	}

	public boolean willUsePercent() {
		return usePercent;
	}

	public void setUsePercent(boolean usePercent) {
		this.usePercent = usePercent;
	}

	public boolean willUseAmountLevel() {
		return useAmountLevel;
	}

	public void setUseAmountLevel(boolean useAmountLevel) {
		this.useAmountLevel = useAmountLevel;
	}

	public boolean willUsePercentLevel() {
		return usePercentLevel;
	}

	public void setUsePercentLevel(boolean usePercentLevel) {
		this.usePercentLevel = usePercentLevel;
	}

	public class HealthResult extends EffectResult {

		private final double restore;

		public HealthResult(int level, double restore) {
			super(level);
			this.restore = restore;
		}

		public double getRestore() {
			return restore;
		}
	}

}
