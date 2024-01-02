package org.ctp.enchantmentsolution.listeners;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.events.EquipEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.player.PlayerChangeCoordsEvent;
import org.ctp.enchantmentsolution.rpg.RPGPlayer;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class GlobalPlayerListener extends Enchantmentable {

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
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		if (esPlayer != null) {
			if (!canRun(RegisterEnchantments.SOULBOUND, event) || isDisabled(player, RegisterEnchantments.SOULBOUND)) return;
			HashMap<Material, Boolean> diamonds = new HashMap<Material, Boolean>();
			// TODO: add netherite tools and armor to this as well
			diamonds.put(Material.DIAMOND_AXE, false);
			diamonds.put(Material.DIAMOND_BOOTS, false);
			diamonds.put(Material.DIAMOND_CHESTPLATE, false);
			diamonds.put(Material.DIAMOND_HELMET, false);
			diamonds.put(Material.DIAMOND_HOE, false);
			diamonds.put(Material.DIAMOND_LEGGINGS, false);
			diamonds.put(Material.DIAMOND_PICKAXE, false);
			diamonds.put(Material.DIAMOND_SHOVEL, false);
			diamonds.put(Material.DIAMOND_SWORD, false);
			List<ItemStack> items = esPlayer.getSoulItems();
			if (items != null) {
				for(ItemStack item: items) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.KEPT_ON_HAND, "soulbound");
					if (diamonds.containsKey(item.getType())) diamonds.put(item.getType(), true);
					player.getInventory().addItem(item);
				}
				if (!diamonds.containsValue(false)) AdvancementUtils.awardCriteria(player, ESAdvancement.READY_AFTER_DEATH, "soulbound");
			}
			esPlayer.removeSoulItems();
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		if (esPlayer != null) esPlayer.removeHWDModels();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEquip(EquipEvent event) {
		ItemStack item = event.getNewItem();
		List<EnchantmentLevel> levels = EnchantmentUtils.getEnchantmentLevels(item);
		for(EnchantmentLevel level: levels)
			if (event.getEntity() instanceof Player) AdvancementUtils.awardCriteria((Player) event.getEntity(), level.getEnchant());
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerEggThrow(PlayerEggThrowEvent event) {
		if (event.getEgg().hasMetadata("hatch_egg")) event.setHatching(false);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof AbstractArrow && event.getEntity().hasMetadata("no_pickup")) {
			AbstractArrow arrow = (AbstractArrow) event.getEntity();
			arrow.setPickupStatus(PickupStatus.DISALLOWED);
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		ESPlayer player = EnchantmentSolution.getESPlayer(event.getPlayer());
		if (player != null) player.removeHWDModels(event.getItemDrop().getItemStack());
	}

	@EventHandler
	public void onPlayerDropItem(InventoryEvent event) {
		for(HumanEntity viewer: event.getViewers())
			if (viewer instanceof Player) {
				ESPlayer player = EnchantmentSolution.getESPlayer((Player) viewer);
				if (player != null) player.removeInvalidHWDModels();
			}
	}
}
