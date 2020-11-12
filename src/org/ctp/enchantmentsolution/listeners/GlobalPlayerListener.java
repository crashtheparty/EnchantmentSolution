package org.ctp.enchantmentsolution.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.events.ItemAddEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.player.PlayerChangeCoordsEvent;
import org.ctp.enchantmentsolution.rpg.RPGPlayer;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class GlobalPlayerListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(event.getPlayer());
		Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
			esPlayer.reloadPlayer();
			RPGPlayer rpg = RPGUtils.getPlayer(esPlayer.getPlayer());
			rpg.giveEnchantment((EnchantmentLevel) null);
		}, 0l);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		if (esPlayer != null && esPlayer.canFly(false)) esPlayer.logoutFlyer();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInvent(ItemAddEvent event) {
		ItemStack item = event.getItem();
		List<EnchantmentLevel> levels = EnchantmentUtils.getEnchantmentLevels(item);
		for(EnchantmentLevel level: levels)
			AdvancementUtils.awardCriteria(event.getPlayer(), level.getEnchant());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {
		double oldX = event.getFrom().getX();
		double oldY = event.getFrom().getY();
		double oldZ = event.getFrom().getZ();
		double newX = event.getTo().getX();
		double newY = event.getTo().getY();
		double newZ = event.getTo().getZ();

		if (oldX != newX || oldY != newY || oldZ != newZ) {
			PlayerChangeCoordsEvent change = new PlayerChangeCoordsEvent(event.getPlayer(), event.getFrom(), event.getTo());

			Bukkit.getPluginManager().callEvent(change);
			if (change.isCancelled()) event.setCancelled(true);
		}
	}
}
