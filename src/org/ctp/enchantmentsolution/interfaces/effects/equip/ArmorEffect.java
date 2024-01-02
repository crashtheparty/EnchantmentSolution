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

public abstract class ArmorEffect extends EquipAttributeEffect {

	private double armorAmount = 0, armorPercent = 1;
	private String armorAmountLevel = "0", armorPercentLevel = "1";
	private boolean useAmount, usePercent, useAmountLevel, usePercentLevel;

	public ArmorEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double armorAmount, double armorPercent, String armorAmountLevel, String armorPercentLevel, boolean useAmount, boolean usePercent,
	boolean useAmountLevel, boolean usePercentLevel, UUID attrUUID, String attrName, Operation operation, EquipCondition[] conditions) {
		super(enchantment, type, location, priority, Attribute.GENERIC_ARMOR, attrUUID, attrName, operation, conditions);
		setArmorAmount(armorAmount);
		setArmorPercent(armorPercent);
		setArmorAmountLevel(armorAmountLevel);
		setArmorPercentLevel(armorPercentLevel);
		setUseAmount(useAmount);
		setUsePercent(usePercent);
		setUseAmountLevel(useAmountLevel);
		setUsePercentLevel(usePercentLevel);
	}

	@Override
	public ArmorResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		int level = getLevel(items);
		if (level == 0) return new ArmorResult(level, new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation(), null));
		AttributeInstance a = ((Attributable) entity).getAttribute(getAttr());
		double tough = a.getBaseValue();
		if (willUsePercent()) tough *= getArmorPercent();
		if (willUseAmount()) tough += getArmorAmount();
		if (willUsePercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getArmorPercentLevel(), codes);
			tough *= MathUtils.eval(percent);
		}
		if (willUseAmountLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getArmorAmountLevel(), codes);
			tough += MathUtils.eval(percent);
		}
		tough = tough - a.getBaseValue();
		AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), tough, getOperation(), null);
		return new ArmorResult(level, modifier);
	}

	public double getArmorAmount() {
		return armorAmount;
	}

	public void setArmorAmount(double armorAmount) {
		this.armorAmount = armorAmount;
	}

	public double getArmorPercent() {
		return armorPercent;
	}

	public void setArmorPercent(double armorPercent) {
		this.armorPercent = armorPercent;
	}

	public String getArmorAmountLevel() {
		return armorAmountLevel;
	}

	public void setArmorAmountLevel(String armorAmountLevel) {
		this.armorAmountLevel = armorAmountLevel;
	}

	public String getArmorPercentLevel() {
		return armorPercentLevel;
	}

	public void setArmorPercentLevel(String armorPercentLevel) {
		this.armorPercentLevel = armorPercentLevel;
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

	public class ArmorResult extends EffectResult {

		private final AttributeModifier modifier;

		public ArmorResult(int level, AttributeModifier modifier) {
			super(level);
			this.modifier = modifier;
		}

		public AttributeModifier getModifier() {
			return modifier;
		}

	}

}
