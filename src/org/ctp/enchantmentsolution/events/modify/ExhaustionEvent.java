package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class ExhaustionEvent extends ModifyActionEvent {

	private double multiplier;
	private final float exhaustionTick;

	public ExhaustionEvent(Player who, double multiplier, float exhaustionTick) {
		super(who, new EnchantmentLevel(CERegister.CURSE_OF_EXHAUSTION, 1));
		setMultiplier(multiplier);
		this.exhaustionTick = exhaustionTick;
	}

	public double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}

	public float getExhaustionTick() {
		return exhaustionTick;
	}

}
