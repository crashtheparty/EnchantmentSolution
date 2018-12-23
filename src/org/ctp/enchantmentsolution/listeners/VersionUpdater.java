package org.ctp.enchantmentsolution.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class VersionUpdater implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(EnchantmentSolution.NEWEST_VERSION == false) {
			if(player.hasPermission("enchantmentsolution.version-updater")) {
				ChatUtils.sendMessage(player, "New version of EnchantmentSolution is available! Download it here: ", "https://www.spigotmc.org/resources/enchantment-solution.59556/");
			}
		}
	}
}
