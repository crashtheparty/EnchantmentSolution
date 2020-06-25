package org.ctp.enchantmentsolution.events;

public interface Cooldownable {

	public boolean useCooldown();

	void setCooldown(boolean cooldown);
	
	public int getCooldownTicks();
	
	public void setCooldownTicks(int ticks);
}
