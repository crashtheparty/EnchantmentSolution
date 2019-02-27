package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.LocationUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemSerialization;
import org.ctp.enchantmentsolution.utils.items.nms.ItemPlaceType;
import org.bukkit.event.Listener;

public class WandListener implements Listener{
	
	private static List<Block> IGNORE_BLOCKS = new ArrayList<Block>();
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(!(DefaultEnchantments.isEnabled(DefaultEnchantments.WAND))) return;
		if(IGNORE_BLOCKS.contains(event.getBlock())) {
			IGNORE_BLOCKS.remove(event.getBlock());
			return;
		}
		if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			int xt = 0;
			int yt = 0;
			int zt = 0;
			if (Enchantments.hasEnchantment(item, DefaultEnchantments.WAND)) {
				ItemStack offhand = player.getInventory().getItemInOffHand();
				if(!ItemPlaceType.getPlaceTypes().contains(offhand.getType())) return;
				float yaw = player.getLocation().getYaw() % 360;
				float pitch = player.getLocation().getPitch();
				while(yaw < 0) {
					yaw += 360;
				}
				Block clickedBlock = event.getBlock();
				if(pitch > 53 || pitch <= -53) {
					xt = Enchantments.getLevel(item, DefaultEnchantments.WAND);
					zt = Enchantments.getLevel(item, DefaultEnchantments.WAND);
				} else {
					yt = Enchantments.getLevel(item, DefaultEnchantments.WAND);
					if((yaw <= 45) || (yaw > 135 && yaw <= 225) || (yaw > 315)) {
						xt = Enchantments.getLevel(item, DefaultEnchantments.WAND);
					} else {
						zt = Enchantments.getLevel(item, DefaultEnchantments.WAND);
					}
				}
				Location rangeOne = new Location(clickedBlock.getWorld(), clickedBlock.getX() - xt, clickedBlock.getY() - yt, clickedBlock.getZ() - zt);
				Location rangeTwo = new Location(clickedBlock.getWorld(), clickedBlock.getX() + xt, clickedBlock.getY() + yt, clickedBlock.getZ() + zt);
				
				if(LocationUtils.getIntersecting(rangeOne, rangeTwo, player.getLocation(), player.getEyeLocation())) {
					return;
				}
				item = player.getInventory().getItemInOffHand();
				int remove = 0;
				for(int x = - xt; x <= xt; x++) {
					for(int y = - yt; y <= yt; y++) {
						for(int z = - zt; z <= zt; z++) {
							item = player.getInventory().getItemInOffHand();
							if(item == null || item.getType() == Material.AIR) return;
							Block block = clickedBlock.getRelative(x, y, z);
							if(x == 0 && y == 0 && z == 0) continue;
							if(!block.getType().isSolid()) {
								IGNORE_BLOCKS.add(block);
								BlockPlaceEvent newEvent = new BlockPlaceEvent(block, clickedBlock.getState(), clickedBlock, item, player, true, EquipmentSlot.OFF_HAND);
								Bukkit.getServer().getPluginManager().callEvent(newEvent);
								if(item != null && !newEvent.isCancelled()) {
									block.setBlockData(newEvent.getBlockReplacedState().getBlockData());
									remove ++;
								}
							}
						}
					}
				}
				if(remove(player, item, remove)) {
					remove(player, item, 1);
				}
				damageItem(event);
			}
		}
	}
	
	private boolean remove(Player player, ItemStack item, int remove) {
		for(int i = 0; i < 36; i++) {
			if(remove == 0) break;
			ItemStack removeItem = player.getInventory().getItem(i);
			if(removeItem != null) { 
				if(removeItem.getType() == item.getType() && ItemSerialization.itemToData(removeItem).equals(ItemSerialization.itemToData(item))) {
					int left = removeItem.getAmount() - remove;
					if(left < 0) {
						remove -= removeItem.getAmount();
						left = 0;
					} else {
						remove = 0;
					}
					if(left == 0) {
						player.getInventory().setItem(i, new ItemStack(Material.AIR));
					} else {
						removeItem.setAmount(left);
						player.getInventory().setItem(i, removeItem);
					}
				}
			}
		}
		if(remove > 0) {
			item.setAmount(item.getAmount() - remove); 
			if(item.getAmount() <= 0) {
				item.setType(Material.AIR);
			}
			return false;
		}
		return true;
	}
	
	private void damageItem(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
			return;
		ItemStack item = player.getInventory().getItemInMainHand();
		int unbreaking = Enchantments.getLevel(item, Enchantment.DURABILITY);
		double chance = (1.0D) / (unbreaking + 2.0D);
		double random = Math.random();
		if (chance > random) {
			DamageUtils.setDamage(item, DamageUtils.getDamage(item.getItemMeta()) + 1);
			if (DamageUtils.getDamage(item.getItemMeta()) > item.getType().getMaxDurability()) {
				player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
			}
		}
	}
}
