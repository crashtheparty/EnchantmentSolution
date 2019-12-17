package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class ModifyActionEvent extends ESPlayerEvent {

	private boolean cancelled;

	public ModifyActionEvent(Player who, EnchantmentLevel enchantment) {
		super(who, enchantment);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
