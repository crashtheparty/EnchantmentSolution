package org.ctp.enchantmentsolution.events.damage;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.player.ItemRepairEvent;

public class ForceFeedEvent extends ItemRepairEvent {

	private float exhaust;

	public ForceFeedEvent(Player who, int level, ItemStack item, float exhaust) {
		super(who, new EnchantmentLevel(CERegister.FORCE_FEED, level), item, level);
		this.exhaust = exhaust;
	}

	public float getExhaust() {
		return exhaust;
	}

	public void setExhaust(float exhaust) {
		this.exhaust = exhaust;
	}

}
