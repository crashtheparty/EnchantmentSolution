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

public class ToughnessEffect extends EquipAttributeEffect {

	private double toughnessAmount = 0, toughnessPercent = 1;
	private String toughnessAmountLevel = "0", toughnessPercentLevel = "1";
	private boolean useAmount, usePercent, useAmountLevel, usePercentLevel;

	public ToughnessEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double toughnessAmount, double toughnessPercent, String toughnessAmountLevel, String toughnessPercentLevel, boolean useAmount, boolean usePercent,
	boolean useAmountLevel, boolean usePercentLevel, UUID attrUUID, String attrName, Operation operation, EquipCondition[] conditions) {
		super(enchantment, type, location, priority, Attribute.GENERIC_ARMOR_TOUGHNESS, attrUUID, attrName, operation, conditions);
		this.toughnessAmount = toughnessAmount;
		this.toughnessPercent = toughnessPercent;
		this.toughnessAmountLevel = toughnessAmountLevel;
		this.toughnessPercentLevel = toughnessPercentLevel;
		this.useAmount = useAmount;
		this.usePercent = usePercent;
		this.useAmountLevel = useAmountLevel;
		this.usePercentLevel = usePercentLevel;
	}

	@Override
	public ToughnessResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		int level = getLevel(items);
		if (level == 0) return new ToughnessResult(level, new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation(), null));
		AttributeInstance a = ((Attributable) entity).getAttribute(getAttr());
		double tough = a.getBaseValue();
		if (willUsePercent()) tough *= getToughnessPercent();
		if (willUseAmount()) tough += getToughnessAmount();
		if (willUsePercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getToughnessPercentLevel(), codes);
			tough *= MathUtils.eval(percent);
		}
		if (willUseAmountLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getToughnessAmountLevel(), codes);
			tough += MathUtils.eval(percent);
		}
		tough = tough - a.getBaseValue();
		AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), tough, getOperation(), null);
		return new ToughnessResult(level, modifier);
	}

	public double getToughnessAmount() {
		return toughnessAmount;
	}

	public void setToughnessAmount(double toughnessAmount) {
		this.toughnessAmount = toughnessAmount;
	}

	public double getToughnessPercent() {
		return toughnessPercent;
	}

	public void setToughnessPercent(double toughnessPercent) {
		this.toughnessPercent = toughnessPercent;
	}

	public String getToughnessPercentLevel() {
		return toughnessPercentLevel;
	}

	public void setToughnessPercentLevel(String toughnessPercentLevel) {
		this.toughnessPercentLevel = toughnessPercentLevel;
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

	public String getToughnessAmountLevel() {
		return toughnessAmountLevel;
	}

	public void setToughnessAmountLevel(String toughnessAmountLevel) {
		this.toughnessAmountLevel = toughnessAmountLevel;
	}

	public class ToughnessResult extends EffectResult {

		private final AttributeModifier modifier;

		public ToughnessResult(int level, AttributeModifier modifier) {
			super(level);
			this.modifier = modifier;
		}

		public AttributeModifier getModifier() {
			return modifier;
		}

	}

}
