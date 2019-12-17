package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class IcarusLaunchEvent extends ModifySpeedEvent {

	private int secondDelay;
	
	public IcarusLaunchEvent(Player who, int level, double speed, int secondDelay) {
		super(who, new EnchantmentLevel(CERegister.ICARUS, level), speed);
		setSecondDelay(secondDelay);
	}

	public int getSecondDelay() {
		return secondDelay;
	}

	public void setSecondDelay(int secondDelay) {
		this.secondDelay = secondDelay;
	}

}
