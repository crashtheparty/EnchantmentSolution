package org.ctp.enchantmentsolution.threads;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class WikiThread implements Runnable {

	private int run;

	@Override
	public void run() {
		if (ConfigString.WIKI_ON_TIMER.getBoolean()) {
			if (run == ConfigString.WIKI_TIMER.getInt()) {
				for(Player player: Bukkit.getOnlinePlayers())
					Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
						Chatable.get().sendMessage(player, Chatable.get().getMessage(ChatUtils.getCodes(), "wiki.message"), ConfigString.WIKI_URL.getString());
					}, 0l);
				resetRunner();
			}
			run++;
		}
	}

	public void resetRunner() {
		run = 0;
	}

}
