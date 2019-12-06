package org.ctp.enchantmentsolution.listeners.chestloot;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.ctp.enchantmentsolution.nms.ChestPopulateNMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class ChestLootListener implements Listener{

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(!shouldPopulate()) {
			return;
		}
		Block block = event.getBlock();
		if(block.getType() == Material.HOPPER) {
			ChestLoot loot = new ChestLoot(block);
			loot.checkBlocks();
			try {
				List<Block> blockLoot = loot.getLootToCheck();
				for(Block bLoot : blockLoot) {
					ChestPopulateNMS.populateChest(event.getPlayer(), bLoot);
				}
			} catch (ChestLootException e) {
				e.printStackTrace();
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(!shouldPopulate()) {
			return;
		}
		Block block = event.getBlock();
		if(block.getType() == Material.CHEST) {
			if(ChestPopulateNMS.isLootChest(block)) {
				ChestPopulateNMS.populateChest(event.getPlayer(), block);
			}
		}
	}

	@EventHandler
	public void onEntityExplosion(EntityExplodeEvent event) {
		if(!shouldPopulate()) {
			return;
		}
		for(Block block : event.blockList()) {
			if(block.getType() == Material.CHEST) {
				if(ChestPopulateNMS.isLootChest(block)) {
					ChestPopulateNMS.populateChest(event.getEntity() instanceof Player ? (Player) event.getEntity() : null, block);
				}
			}
		}
	}

	@EventHandler
	public void onVehicleMove(VehicleMoveEvent event) {
		if(!shouldPopulate()) {
			return;
		}
		Vehicle vehicle = event.getVehicle();
		if(vehicle.getType().equals(EntityType.MINECART_CHEST)){
			if(ChestPopulateNMS.isLootCart(vehicle)) {
				ChestPopulateNMS.populateCart(null, vehicle);
			}
		}
	}

	@EventHandler
	public void onVehicleDestroy(VehicleDestroyEvent event) {
		if(!shouldPopulate()) {
			return;
		}
		Vehicle vehicle = event.getVehicle();
		if(vehicle.getType().equals(EntityType.MINECART_CHEST)){
			if(ChestPopulateNMS.isLootCart(vehicle)) {
				ChestPopulateNMS.populateCart(event.getAttacker() instanceof Player ? (Player) event.getAttacker() : null, vehicle);
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(!shouldPopulate()) {
			return;
		}
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) {
				return; // off hand packet, ignore.
			}
			Block block = event.getClickedBlock();
			if(block != null) {
				if(block.getType() == Material.CHEST) {
					if(ChestPopulateNMS.isLootChest(block)) {
						ChestPopulateNMS.populateChest(event.getPlayer(), block);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if(!shouldPopulate()) {
			return;
		}
		Entity entity = event.getRightClicked();
		if(entity instanceof Vehicle) {
			Vehicle vehicle = (Vehicle) entity;
			if(vehicle.getType().equals(EntityType.MINECART_CHEST)){
				if(ChestPopulateNMS.isLootCart(vehicle)) {
					ChestPopulateNMS.populateCart(event.getPlayer(), vehicle);
				}
			}
		}
	}

	private boolean shouldPopulate() {
		return ConfigString.USE_LOOT.getBoolean("chests.use");
	}

}
