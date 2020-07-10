package org.ctp.enchantmentsolution.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.threads.SnapshotRunnable;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class GlobalPlayerListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (ConfigString.ENCHANTMENT_CHECK_ON_LOGIN.getBoolean()) Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
			SnapshotRunnable.updateInventory(player);
		}, 1l);
	}

}
