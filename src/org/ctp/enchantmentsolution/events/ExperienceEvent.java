package org.ctp.enchantmentsolution.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ExperienceEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	private boolean cancelled = false;
	private final ExpShareType type;
	private int oldExp, newExp;

	public ExperienceEvent(Player who, final ExpShareType type, int oldExp, int newExp) {
		super(who);
		this.type = type;
		setOldExp(oldExp);
		setNewExp(newExp);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public int getOldExp() {
		return oldExp;
	}

	public void setOldExp(int oldExp) {
		this.oldExp = oldExp;
	}

	public int getNewExp() {
		return newExp;
	}

	public void setNewExp(int newExp) {
		this.newExp = newExp;
	}

	public ExpShareType getType() {
		return type;
	}

	public enum ExpShareType {
		BLOCK(), MOB();
	}
}
