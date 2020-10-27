package org.ctp.enchantmentsolution.listeners.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.ctp.crashapi.CrashAPI;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.Anvil;
import org.ctp.enchantmentsolution.inventory.EnchantmentTable;
import org.ctp.enchantmentsolution.inventory.LegacyAnvil;
import org.ctp.enchantmentsolution.inventory.minigame.Minigame;
import org.ctp.enchantmentsolution.nms.playerinteract.PlayerInteract_v1_14;
import org.ctp.enchantmentsolution.utils.AnvilUtils;
import org.ctp.enchantmentsolution.utils.MinigameUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class PlayerInteract implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) return; // off hand packet, ignore.
			Block block = event.getClickedBlock();
			if (block.getType() == Material.ENCHANTING_TABLE && MinigameUtils.isEnabled()) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), () -> {
					if (event.isCancelled()) return;
					Player player = event.getPlayer();
					InventoryData inv = EnchantmentSolution.getPlugin().getInventory(player);
					if (inv == null) {
						inv = new Minigame(player, block);
						EnchantmentSolution.getPlugin().addInventory(inv);
					} else if (!(inv instanceof Minigame)) {
						inv.close(true);
						inv = new Minigame(player, block);
						EnchantmentSolution.getPlugin().addInventory(inv);
					}
					inv.setInventory();
				}, 1l);

				return;
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), () -> {}, 1l);
			if (block.getType() == Material.ENCHANTING_TABLE && (ConfigString.CUSTOM_TABLE.getBoolean() || ConfigString.LEVEL_FIFTY.getBoolean())) Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), () -> {
				if (event.isCancelled()) return;
				Player player = event.getPlayer();
				InventoryData inv = EnchantmentSolution.getPlugin().getInventory(player);
				if (inv == null) {
					inv = new EnchantmentTable(player, block);
					EnchantmentSolution.getPlugin().addInventory(inv);
				} else if (!(inv instanceof EnchantmentTable)) {
					inv.close(true);
					inv = new EnchantmentTable(player, block);
					EnchantmentSolution.getPlugin().addInventory(inv);
				}
				inv.setInventory(null);
			}, 1l);
			if ((block.getType() == Material.ANVIL || block.getType() == Material.CHIPPED_ANVIL || block.getType() == Material.DAMAGED_ANVIL) && ConfigString.CUSTOM_ANVIL.getBoolean()) {
				if (AnvilUtils.hasLegacyAnvil(event.getPlayer())) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), () -> {
						if (event.isCancelled()) return;
						Player player = event.getPlayer();
						InventoryData inv = EnchantmentSolution.getPlugin().getInventory(player);
						if (inv == null) {
							inv = new LegacyAnvil(player, block, player.getOpenInventory().getTopInventory());
							EnchantmentSolution.getPlugin().addInventory(inv);
						} else if (!(inv instanceof LegacyAnvil)) {
							inv.close(true);
							inv = new LegacyAnvil(player, block, player.getOpenInventory().getTopInventory());
							EnchantmentSolution.getPlugin().addInventory(inv);
						}
						inv.setInventory(null);
					}, 1l);
					return;
				}
				Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentSolution.getPlugin(), () -> {
					if (event.isCancelled()) return;
					Player player = event.getPlayer();
					InventoryData inv = EnchantmentSolution.getPlugin().getInventory(player);
					if (inv == null) {
						inv = new Anvil(player, block);
						EnchantmentSolution.getPlugin().addInventory(inv);
					} else if (!(inv instanceof Anvil)) {
						inv.close(true);
						inv = new Anvil(player, block);
						EnchantmentSolution.getPlugin().addInventory(inv);
					}
					inv.setInventory(null);
				}, 1l);
			}
			if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 3) PlayerInteract_v1_14.onPlayerInteract(event);
		}
	}

}
