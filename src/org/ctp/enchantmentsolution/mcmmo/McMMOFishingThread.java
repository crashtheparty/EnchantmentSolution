package org.ctp.enchantmentsolution.mcmmo;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class McMMOFishingThread implements Runnable {

	private Player player;
	private ItemStack item;
	private int run = 3, scheduler, xp;
	private McMMOFishing fishing;

	public McMMOFishingThread(Player player, ItemStack item, int xp, McMMOFishing fishing) {
		this.player = player;
		this.item = item;
		this.xp = xp;
		this.fishing = fishing;
	}

	@Override
	public void run() {
		if (run <= 0) fishing.remove(this);
		run--;
	}

	public int getScheduler() {
		return scheduler;
	}

	public void setScheduler(int s) {
		scheduler = s;
	}

	public ItemStack getItem() {
		return item;
	}

	public Player getPlayer() {
		return player;
	}

	public int getXp() {
		return xp;
	}
}
