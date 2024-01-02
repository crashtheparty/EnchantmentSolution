package org.ctp.enchantmentsolution.events.modify.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.Cooldownable;

public class IcarusLaunchEvent extends ModifyPlayerVelocityEvent implements Cooldownable {

	private int cooldownTicks;
	private boolean cooldown;

	public IcarusLaunchEvent(Player who, int level, double speedX, double speedY, double speedZ) {
		super(who, new EnchantmentLevel(CERegister.ICARUS, level), speedX, speedY, speedZ);
		cooldown = true;
		cooldownTicks = 600;
	}

	public IcarusLaunchEvent(Player who, int level, double addX, double addY, double addZ, double multX, double multY, double multZ) {
		super(who, new EnchantmentLevel(CERegister.ICARUS, level), addX, addY, addZ);
		cooldown = true;
		cooldownTicks = 600;
	}

	@Override
	public boolean useCooldown() {
		return cooldown;
	}

	@Override
	public void setCooldown(boolean cooldown) {
		this.cooldown = cooldown;
	}

	@Override
	public int getCooldownTicks() {
		return cooldownTicks;
	}

	@Override
	public void setCooldownTicks(int cooldownTicks) {
		this.cooldownTicks = cooldownTicks;
	}

}
