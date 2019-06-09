package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.LocationUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemSerialization;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemPlaceType;

public class WandListener extends EnchantmentListener{
	
	private static List<Block> IGNORE_BLOCKS = new ArrayList<Block>();
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(!canRun(DefaultEnchantments.WAND, event)) return;
		if(IGNORE_BLOCKS.contains(event.getBlock())) {
			IGNORE_BLOCKS.remove(event.getBlock());
			return;
		}
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
				int start = 0;
				boolean removed = false;
				while(start <= xt || start <= yt || start <= zt) {
					int xBegin = start;
					int yBegin = start;
					int zBegin = start;
					if(xBegin > xt) {
						xBegin = xt;
					}
					if(yBegin > yt) {
						yBegin = yt;
					}
					if(zBegin > zt) {
						zBegin = zt;
					}
					for(int x = - xBegin; x <= xBegin; x++) {
						for(int y = - yBegin; y <= yBegin; y++) {
							for(int z = - zBegin; z <= zBegin; z++) {
								if((Math.abs(x) == xBegin && Math.abs(y) == yBegin) 
										|| (Math.abs(x) == xBegin && Math.abs(z) == zBegin) 
										|| (Math.abs(y) == yBegin && Math.abs(z) == zBegin)) {
									item = player.getInventory().getItemInOffHand();
									if(x == 0 && y == 0 && z == 0) {
										removed = remove(player, item, 1);
										continue;
									}
									if(item == null || item.getType() == Material.AIR) continue;
									Block block = clickedBlock.getRelative(x, y, z);
									if(!block.getType().isSolid()) {
										IGNORE_BLOCKS.add(block);
										if(block.getType() == Material.TORCH) {
											AdvancementProgress progress = player.getAdvancementProgress(
													Bukkit.getAdvancement(ESAdvancement.BREAKER_BREAKER.getNamespace()));
											if(progress.getRemainingCriteria().contains("torch")) {
												progress.awardCriteria("torch");
											}
										}
										Collection<ItemStack> drops = block.getDrops();
										BlockData oldData = block.getBlockData();
										Material oldType = block.getType();
										block.setType(item.getType());
										if(block.getBlockData() instanceof Directional) {
											Directional directional = (Directional) block.getBlockData();
											directional.setFacing(((Directional) clickedBlock).getFacing());
											block.setBlockData((BlockData) directional);
										}
										if(block.getBlockData() instanceof Orientable) {
											Orientable orientable = (Orientable) block.getBlockData();
											orientable.setAxis(((Orientable) clickedBlock).getAxis());
											block.setBlockData((BlockData) orientable);
										}
										if(block.getBlockData() instanceof Rotatable) {
											Rotatable rotatable = (Rotatable) block.getBlockData();
											rotatable.setRotation(((Rotatable) clickedBlock).getRotation());
											block.setBlockData((BlockData) rotatable);
										}
										BlockPlaceEvent newEvent = new BlockPlaceEvent(block, block.getState(), clickedBlock, item, player, true, EquipmentSlot.OFF_HAND);
										Bukkit.getServer().getPluginManager().callEvent(newEvent);
										if(item != null && !newEvent.isCancelled()) {
											player.incrementStatistic(Statistic.USE_ITEM, item.getType());
											block.setBlockData(newEvent.getBlockReplacedState().getBlockData());
											removed = remove(player, item, 1);
											for(ItemStack drop : drops) {
												block.getWorld().dropItem(newEvent.getBlock().getLocation(), drop);
											}
										} else {
											IGNORE_BLOCKS.remove(block);
											block.setType(oldType);
											block.setBlockData(oldData);
										}
									}
								}
							}
						}
					}
					start ++;
				}
				if(!removed) {
					ItemStack removedItem = item.clone();
					removedItem.setAmount(1);
					ItemUtils.giveItemToPlayer(player, removedItem, player.getLocation(), false);
				}
				damageItem(event);
			}
		}
	}
	
	private boolean remove(Player player, ItemStack item, int remove) {
		if(player.getGameMode() == GameMode.CREATIVE) {
			return false;
		}
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
			return true;
		}
		return false;
	}
	
	private void damageItem(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
			return;
		ItemStack item = player.getInventory().getItemInMainHand();
		ItemStack deadItem = super.damageItem(player, item, 1, 2);
		if(DamageUtils.getDamage(deadItem.getItemMeta()) > deadItem.getType().getMaxDurability()) {
			AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(ESAdvancement.DID_YOU_REALLY_WAND_TO_DO_THAT.getNamespace()));
			if(progress.getRemainingCriteria().contains("break")) {
				progress.awardCriteria("break");
			}
		}
	}
}
