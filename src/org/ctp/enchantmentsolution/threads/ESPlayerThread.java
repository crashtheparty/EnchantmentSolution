package org.ctp.enchantmentsolution.threads;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class ESPlayerThread implements Runnable {

	private static HashMap<OfflinePlayer, ESPlayerThread> THREADS = new HashMap<OfflinePlayer, ESPlayerThread>();
	private final ESPlayer esPlayer;
	private int scheduler;

	private ESPlayerThread(ESPlayer esPlayer) {
		this.esPlayer = esPlayer;
	}

	public static ESPlayerThread getThread(ESPlayer player) {
		if (THREADS.containsKey(player.getPlayer())) return THREADS.get(player.getPlayer());
		ESPlayerThread thread = new ESPlayerThread(player);
		int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), thread, 0l, 1l);
		thread.setScheduler(scheduler);
		THREADS.put(player.getPlayer(), thread);
		return thread;
	}

	@Override
	public void run() {
		if (esPlayer.isOnline()) {
			esPlayer.runHWD();
			esPlayer.runGaia();
		}
	}

	public int getScheduler() {
		return scheduler;
	}

	public void setScheduler(int scheduler) {
		this.scheduler = scheduler;
	}

}
