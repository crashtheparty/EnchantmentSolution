package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class AttributeEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	private boolean cancelled;
	private String removeModifier, addModifier;
	private EnchantmentLevel enchantment;

	public AttributeEvent(Player who, EnchantmentLevel enchantment, String removeModifier, String addModifier) {
		super(who);
		setEnchantment(enchantment);
		setRemoveModifier(removeModifier);
		setAddModifier(addModifier);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public String getRemoveModifier() {
		return removeModifier;
	}

	public void setRemoveModifier(String removeModifier) {
		this.removeModifier = removeModifier;
	}

	public String getAddModifier() {
		return addModifier;
	}

	public void setAddModifier(String addModifier) {
		this.addModifier = addModifier;
	}

	public EnchantmentLevel getEnchantment() {
		return enchantment;
	}

	public void setEnchantment(EnchantmentLevel enchantment) {
		this.enchantment = enchantment;
	}
}
