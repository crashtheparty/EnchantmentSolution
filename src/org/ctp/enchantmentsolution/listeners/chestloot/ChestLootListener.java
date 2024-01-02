package org.ctp.enchantmentsolution.listeners.chestloot;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.loot.LootUtils;

public class ChestLootListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (!shouldPopulate()) return;
		Block block = event.getBlock();
		if (block.getType() == Material.HOPPER) {
			ChestLoot loot = new ChestLoot(block);
			loot.checkBlocks();
			try {
				List<Block> blockLoot = loot.getLootToCheck();
				for(Block bLoot: blockLoot)
					LootUtils.populateLootChest(event.getPlayer(), bLoot);
			} catch (ChestLootException e) {
				e.printStackTrace();
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (!shouldPopulate()) return;
		Block block = event.getBlock();
		LootUtils.populateLootChest(event.getPlayer(), block);
	}

	@EventHandler
	public void onEntityExplosion(EntityExplodeEvent event) {
		if (!shouldPopulate()) return;
		for(Block block: event.blockList())
			LootUtils.populateLootChest(event.getEntity() instanceof Player ? (Player) event.getEntity() : null, block);
	}

	@EventHandler
	public void onVehicleDestroy(VehicleDestroyEvent event) {
		if (!shouldPopulate()) return;
		Vehicle vehicle = event.getVehicle();
		LootUtils.populateLootCart(event.getAttacker() instanceof Player ? (Player) event.getAttacker() : null, vehicle);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!shouldPopulate()) return;
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) return; // off hand packet, ignore.
			Block block = event.getClickedBlock();
			LootUtils.populateLootChest(event.getPlayer(), block);
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (!shouldPopulate()) return;
		Entity entity = event.getRightClicked();
		LootUtils.populateLootCart(event.getPlayer(), entity);
	}

	private boolean shouldPopulate() {
		return ConfigString.USE_ALL_CHEST_LOOT.getBoolean();
	}

}
