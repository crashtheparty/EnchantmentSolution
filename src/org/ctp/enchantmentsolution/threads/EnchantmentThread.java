package org.ctp.enchantmentsolution.threads;

import org.bukkit.Bukkit;
import org.ctp.enchantmentsolution.utils.Reflectionable;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public abstract class EnchantmentThread implements Runnable, Reflectionable {

	private final ESPlayer player;
	private int scheduler;
	private boolean running;

	protected EnchantmentThread(ESPlayer player) {
		this.player = player;
		running = false;
	}

	public ESPlayer getPlayer() {
		return player;
	}

	public int getScheduler() {
		return scheduler;
	}

	public void setScheduler(int scheduler) {
		this.scheduler = scheduler;
		running = true;
	}

	public boolean isRunning() {
		return running;
	}
	
	protected void remove() {
		Bukkit.getScheduler().cancelTask(getScheduler());
		running = false;
		scheduler = 0;
	}

}
