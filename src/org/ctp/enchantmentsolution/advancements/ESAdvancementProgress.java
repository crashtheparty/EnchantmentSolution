package org.ctp.enchantmentsolution.advancements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;

public class ESAdvancementProgress {

	private static List<ESAdvancementProgress> PROGRESS = new ArrayList<ESAdvancementProgress>();
	private ESAdvancement advancement;
	private String criteria;
	private int currentAmount;
	private OfflinePlayer player;
	
	private ESAdvancementProgress(ESAdvancement advancement, String criteria, int currentAmount, OfflinePlayer player) {
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
	
	public static ESAdvancementProgress getAdvancementProgress(OfflinePlayer player, ESAdvancement advancement, String criteria) {
		for(ESAdvancementProgress progress : PROGRESS) {
			if(progress.getPlayer().equals(player) && progress.getAdvancement() == advancement && progress.getCriteria().equals(criteria)) {
				return progress;
			}
		}
		ESAdvancementProgress progress = new ESAdvancementProgress(advancement, criteria, 0, player);
		PROGRESS.add(progress);
		return progress;
	}
}
