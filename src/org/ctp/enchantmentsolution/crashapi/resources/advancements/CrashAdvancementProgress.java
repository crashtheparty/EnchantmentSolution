package org.ctp.enchantmentsolution.crashapi.resources.advancements;

import org.bukkit.OfflinePlayer;

public class CrashAdvancementProgress {

	private CrashAdvancement advancement;
	private String criteria;
	private int currentAmount;
	private OfflinePlayer player;

	public CrashAdvancementProgress(CrashAdvancement advancement, String criteria, int currentAmount, OfflinePlayer player) {
		this.advancement = advancement;
		this.criteria = criteria;
		this.currentAmount = currentAmount;
		this.player = player;
	}

	public CrashAdvancement getAdvancement() {
		return advancement;
	}

	public String getCriteria() {
		return criteria;
	}

	public int getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(int currentAmount) {
		this.currentAmount = currentAmount;
	}

	public OfflinePlayer getPlayer() {
		return player;
	}
}
