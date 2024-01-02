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

public abstract class AttackSpeedEffect extends EquipAttributeEffect {

	private double speedAmount = 0, speedPercent = 1;
	private String speedAmountLevel = "0", speedPercentLevel = "1";
	private boolean useAmount, usePercent, useAmountLevel, usePercentLevel;

	public AttackSpeedEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double speedAmount, double speedPercent, String speedAmountLevel, String speedPercentLevel, boolean useAmount, boolean usePercent,
	boolean useAmountLevel, boolean usePercentLevel, UUID attrUUID, String attrName, Operation operation, EquipCondition[] conditions) {
		super(enchantment, type, location, priority, Attribute.GENERIC_ATTACK_SPEED, attrUUID, attrName, operation, conditions);
		this.speedAmount = speedAmount;
		this.speedPercent = speedPercent;
		this.speedAmountLevel = speedAmountLevel;
		this.speedPercentLevel = speedPercentLevel;
		this.useAmount = useAmount;
		this.usePercent = usePercent;
		this.useAmountLevel = useAmountLevel;
		this.usePercentLevel = usePercentLevel;
	}

	@Override
	public SpeedResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		int level = getLevel(items);
		if (level == 0) return new SpeedResult(level, new AttributeModifier(getAttrUUID(), getAttrName(), 0, getOperation(), null));
		AttributeInstance a = ((Attributable) entity).getAttribute(getAttr());
		double speed = a.getBaseValue();
		if (willUsePercent()) speed *= getSpeedPercent();
		if (willUseAmount()) speed += getSpeedAmount();
		if (willUsePercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getSpeedPercentLevel(), codes);
			speed *= MathUtils.eval(percent);
		}
		if (willUseAmountLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getSpeedAmountLevel(), codes);
			speed += MathUtils.eval(percent);
		}
		speed = speed - a.getBaseValue();
		AttributeModifier modifier = new AttributeModifier(getAttrUUID(), getAttrName(), speed, getOperation(), null);
		return new SpeedResult(level, modifier);
	}

	public double getSpeedAmount() {
		return speedAmount;
	}

	public void setSpeedAmount(double speedAmount) {
		this.speedAmount = speedAmount;
	}

	public double getSpeedPercent() {
		return speedPercent;
	}

	public void setSpeedPercent(double speedPercent) {
		this.speedPercent = speedPercent;
	}

	public String getSpeedPercentLevel() {
		return speedPercentLevel;
	}

	public void setSpeedPercentLevel(String speedPercentLevel) {
		this.speedPercentLevel = speedPercentLevel;
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

	public String getSpeedAmountLevel() {
		return speedAmountLevel;
	}

	public void setSpeedAmountLevel(String speedAmountLevel) {
		this.speedAmountLevel = speedAmountLevel;
	}

	public class SpeedResult extends EffectResult {

		private final AttributeModifier modifier;

		public SpeedResult(int level, AttributeModifier modifier) {
			super(level);
			this.modifier = modifier;
		}

		public AttributeModifier getModifier() {
			return modifier;
		}

	}

}
