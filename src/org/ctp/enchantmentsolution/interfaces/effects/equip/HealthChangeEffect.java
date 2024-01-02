package org.ctp.enchantmentsolution.interfaces.effects.equip;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.attribute.*;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.events.EquipEvent;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class HealthChangeEffect extends EquipAttributeEffect {

	private double healthAmount = 0, healthPercent = 1;
	private String healthAmountLevel = "0", healthPercentLevel = "1";
	private boolean useAmount, usePercent, useAmountLevel, usePercentLevel;

	public HealthChangeEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double healthAmount, double healthPercent, String healthAmountLevel, String healthPercentLevel, boolean useAmount, boolean usePercent,
	boolean useAmountLevel, boolean usePercentLevel, UUID attrUUID, String attrName, Operation operation, EquipCondition[] conditions) {
		super(enchantment, type, location, priority, Attribute.GENERIC_MAX_HEALTH, attrUUID, attrName, operation, conditions);
		setHealthAmount(healthAmount);
		setHealthAmountLevel(healthAmountLevel);
		setHealthPercent(healthPercent);
		setHealthPercentLevel(healthPercentLevel);
		setUseAmount(useAmount);
		setUseAmountLevel(useAmountLevel);
		setUsePercent(usePercent);
		setUsePercentLevel(usePercentLevel);
	}

	@Override
	public HealthChangeResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		int level = getLevel(items, getEnchantment());
		if (level == 0) return new HealthChangeResult(level, new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation(), null));
		AttributeInstance a = ((Attributable) entity).getAttribute(getAttr());
		double health = a.getBaseValue();
		double modifyHealth = 0, scalar = 0;
		if (willUsePercent()) {
			modifyHealth += health * getHealthPercent();
			scalar = getHealthPercent();
		}
		if (willUseAmount()) {
			modifyHealth += getHealthAmount();
			scalar = getHealthAmount();
		}
		if (willUsePercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getHealthPercentLevel(), codes);
			modifyHealth += health * MathUtils.eval(percent);
			scalar = MathUtils.eval(percent);
		}
		if (willUseAmountLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getHealthAmountLevel(), codes);
			modifyHealth += MathUtils.eval(percent);
			scalar = MathUtils.eval(percent);
		}
		AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), getOperation() == Operation.ADD_NUMBER ? modifyHealth : scalar, getOperation(), null);
		return new HealthChangeResult(level, modifier);
	}

	public double getHealthAmount() {
		return healthAmount;
	}

	public void setHealthAmount(double healthAmount) {
		this.healthAmount = healthAmount;
	}

	public double getHealthPercent() {
		return healthPercent;
	}

	public void setHealthPercent(double healthPercent) {
		this.healthPercent = healthPercent;
	}

	public String getHealthAmountLevel() {
		return healthAmountLevel;
	}

	public void setHealthAmountLevel(String healthAmountLevel) {
		this.healthAmountLevel = healthAmountLevel;
	}

	public String getHealthPercentLevel() {
		return healthPercentLevel;
	}

	public void setHealthPercentLevel(String healthPercentLevel) {
		this.healthPercentLevel = healthPercentLevel;
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

	public class HealthChangeResult extends EffectResult {

		private final AttributeModifier modifier;

		public HealthChangeResult(int level, AttributeModifier modifier) {
			super(level);
			this.modifier = modifier;
		}

		public AttributeModifier getModifier() {
			return modifier;
		}
	}

}