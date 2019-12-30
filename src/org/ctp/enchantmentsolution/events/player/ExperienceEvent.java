package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public class ExperienceEvent extends ESPlayerEvent {

	private boolean cancelled = false;
	private final ExpShareType type;
	private final int oldExp;
	private int newExp;

	public ExperienceEvent(Player who, int level, final ExpShareType type, int oldExp, int newExp) {
		super(who, new EnchantmentLevel(CERegister.EXP_SHARE, level));
		this.type = type;
		this.oldExp = oldExp;
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
