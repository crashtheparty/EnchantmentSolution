package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.player.ModifyActionEvent;

public class ExhaustionEvent extends ModifyActionEvent {

	private int curseLevel;
	private float exhaustionTick;

	public ExhaustionEvent(Player who, int curseLevel, float exhaustionTick) {
		super(who, new EnchantmentLevel(CERegister.CURSE_OF_EXHAUSTION, 1));
		setCurseLevel(curseLevel);
		setExhaustionTick(exhaustionTick);
	}

	public int getCurseLevel() {
		return curseLevel;
	}

	public void setCurseLevel(int curseLevel) {
		this.curseLevel = curseLevel;
	}

	public float getExhaustionTick() {
		return exhaustionTick;
	}

	public void setExhaustionTick(float exhaustionTick) {
		this.exhaustionTick = exhaustionTick;
	}

}
