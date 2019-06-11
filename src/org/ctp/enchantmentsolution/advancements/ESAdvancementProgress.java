package org.ctp.enchantmentsolution.advancements;

import org.bukkit.OfflinePlayer;

public class ESAdvancementProgress {

	private ESAdvancement advancement;
	private String criteria;
	private int currentAmount;
	private OfflinePlayer player;
	
	public ESAdvancementProgress(ESAdvancement advancement, String criteria, int currentAmount, OfflinePlayer player) {
		this.advancement = advancement;
		this.criteria = criteria;
		this.currentAmount = currentAmount;
		this.player = player;
	}

	public ESAdvancement getAdvancement() {
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
