package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
//import org.ctp.enchantmentsolution.nms.McMMO;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemBreakType;
import org.bukkit.event.Listener;

public class WidthHeightListener implements Listener{
	
	private static List<Block> IGNORE_BLOCKS = new ArrayList<Block>();

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(!(DefaultEnchantments.isEnabled(DefaultEnchantments.WIDTH_PLUS_PLUS) && DefaultEnchantments.isEnabled(DefaultEnchantments.HEIGHT_PLUS_PLUS))) return;
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
			boolean hasWidthHeight = false;
			if (DefaultEnchantments.isEnabled(DefaultEnchantments.WIDTH_PLUS_PLUS) && Enchantments
					.hasEnchantment(item, DefaultEnchantments.WIDTH_PLUS_PLUS)) {
				hasWidthHeight = true;
				float yaw = player.getLocation().getYaw() % 360;
				while(yaw < 0) {
					yaw += 360;
				}
				if((yaw <= 45) || (yaw > 135 && yaw <= 225) || (yaw > 315)) {
					xt = Enchantments.getLevel(item, DefaultEnchantments.WIDTH_PLUS_PLUS);
				} else {
					zt = Enchantments.getLevel(item, DefaultEnchantments.WIDTH_PLUS_PLUS);
				}
				
			}
			if (DefaultEnchantments.isEnabled(DefaultEnchantments.HEIGHT_PLUS_PLUS) && Enchantments
					.hasEnchantment(item, DefaultEnchantments.HEIGHT_PLUS_PLUS)) {
				hasWidthHeight = true;
				float pitch = player.getLocation().getPitch();
				float yaw = player.getLocation().getYaw() % 360;
				while(yaw < 0) {
					yaw += 360;
				}
				if(pitch > 53 || pitch <= -53) {
					if((yaw <= 45) || (yaw > 135 && yaw <= 225) || (yaw > 315)) {
						zt = Enchantments.getLevel(item, DefaultEnchantments.HEIGHT_PLUS_PLUS);
					} else {
						xt = Enchantments.getLevel(item, DefaultEnchantments.HEIGHT_PLUS_PLUS);
					}
				} else {
					yt = Enchantments.getLevel(item, DefaultEnchantments.HEIGHT_PLUS_PLUS);
				}
				
			}
			Material original = event.getBlock().getType();
			if(hasWidthHeight && ItemBreakType.getType(item.getType()) != null && ItemBreakType.getType(item.getType()).getBreakTypes() != null 
					&& ItemBreakType.getType(item.getType()).getBreakTypes().contains(original)) {
				for(int x = - xt; x <= xt; x++) {
					for(int y = - yt; y <= yt; y++) {
						for(int z = - zt; z <= zt; z++) {
							item = player.getInventory().getItemInMainHand();
							if(item == null) return;
							if(x == 0 && y == 0 && z == 0) {
								continue;
							}
							Block block = event.getBlock().getRelative(x, y, z);
							if(original != Material.OBSIDIAN && block.getType() == Material.OBSIDIAN) {
								continue;
							}
							if(ItemBreakType.getType(item.getType()).getBreakTypes().contains(block.getType())) {
								IGNORE_BLOCKS.add(block);
								BlockBreakEvent newEvent = new BlockBreakEvent(block, player);
								Bukkit.getServer().getPluginManager().callEvent(newEvent);
								if(item != null && newEvent.getBlock().getType() != Material.AIR) {
									if(!Enchantments.hasEnchantment(item, Enchantment.SILK_TOUCH)) {
										switch(newEvent.getBlock().getType()) {
										case COAL_ORE:
											AbilityUtils.dropExperience(newEvent.getBlock().getLocation().add(0.5, 0.5, 0.5), (int) (Math.random() * 3));
											break;
										case DIAMOND_ORE:
										case EMERALD_ORE:
											AbilityUtils.dropExperience(newEvent.getBlock().getLocation().add(0.5, 0.5, 0.5), (int) (Math.random() * 5) + 3);
											break;
										case LAPIS_ORE:
										case NETHER_QUARTZ_ORE:
											AbilityUtils.dropExperience(newEvent.getBlock().getLocation().add(0.5, 0.5, 0.5), (int) (Math.random() * 4) + 2);
											break;
										case REDSTONE_ORE:
											AbilityUtils.dropExperience(newEvent.getBlock().getLocation().add(0.5, 0.5, 0.5), (int) (Math.random() * 5) + 1);
											break;
										case SPAWNER:
											AbilityUtils.dropExperience(newEvent.getBlock().getLocation().add(0.5, 0.5, 0.5), (int) (Math.random() * 29) + 15);
											break;
										default:
											break;
										}
									}
									//McMMO.handleMcMMO(newEvent);
									newEvent.getBlock().breakNaturally(item);
									int unbreaking = Enchantments.getLevel(item, Enchantment.DURABILITY);
									double chance = (1.0D) / (unbreaking + 1.0D);
									double random = Math.random();
									if(chance > random) {
										DamageUtils.setDamage(item, DamageUtils.getDamage(item.getItemMeta()) + 1);
										if(DamageUtils.getDamage(item.getItemMeta()) > item.getType().getMaxDurability()) {
											player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
											player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
