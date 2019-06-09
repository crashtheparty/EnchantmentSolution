package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Snow;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.McMMO;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemBreakType;

public class WidthHeightListener extends EnchantmentListener{
	
	private static List<Block> IGNORE_BLOCKS = new ArrayList<Block>();

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(!canRun(event, false, DefaultEnchantments.WIDTH_PLUS_PLUS, DefaultEnchantments.HEIGHT_PLUS_PLUS)) return;
		if(IGNORE_BLOCKS.contains(event.getBlock())) {
			IGNORE_BLOCKS.remove(event.getBlock());
			return;
		}
		if(McMMO.getIgnoredPlayers() != null && McMMO.getIgnoredPlayers().contains(player)) {
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
				int start = 1;
				int blocksBroken = 0;
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
									item = player.getInventory().getItemInMainHand();
									if(item == null || item.getType() == Material.AIR) return;
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
										int exp = 0;
										if(!Enchantments.hasEnchantment(item, Enchantment.SILK_TOUCH)) {
											switch(newEvent.getBlock().getType()) {
											case COAL_ORE:
												exp = (int) (Math.random() * 3);
												break;
											case DIAMOND_ORE:
											case EMERALD_ORE:
												exp = (int) (Math.random() * 5) + 3;
												break;
											case LAPIS_ORE:
											case NETHER_QUARTZ_ORE:
												exp = (int) (Math.random() * 4) + 2;
												break;
											case REDSTONE_ORE:
												exp = (int) (Math.random() * 5) + 1;
												break;
											case SPAWNER:
												exp = (int) (Math.random() * 29) + 15;
												break;
											default:
												break;
											}
										}
										newEvent.setExpToDrop(exp);
										Bukkit.getServer().getPluginManager().callEvent(newEvent);
										if(item != null && newEvent.getBlock().getType() != Material.AIR && !newEvent.isCancelled()) {
											if(newEvent.getBlock().getType().equals(Material.SNOW) && ItemBreakType.getType(item.getType()).getBreakTypes().contains(Material.SNOW)) {
												int num = ((Snow) newEvent.getBlock().getBlockData()).getLayers();
												Item droppedItem = player.getWorld().dropItem(
														newEvent.getBlock().getLocation().add(0.5, 0.5, 0.5),
														new ItemStack(Material.SNOWBALL, num));
												droppedItem.setVelocity(new Vector(0,0,0));
											}
											blocksBroken ++;
											player.incrementStatistic(Statistic.MINE_BLOCK, event.getBlock().getType());
											player.incrementStatistic(Statistic.USE_ITEM, item.getType());
											newEvent.getBlock().breakNaturally(item);
											AbilityUtils.dropExperience(newEvent.getBlock().getLocation().add(0.5, 0.5, 0.5), newEvent.getExpToDrop());
											super.damageItem(player, item);
										}
									}
								}
							}
						}
					}
					start ++;
				}
				final int broken = blocksBroken;
				Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), new Runnable() {
					@Override
					public void run() {
						int blocksBroken = broken;
						AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(new NamespacedKey(EnchantmentSolution.getPlugin(), "enchantments/over_9000")));
						if(progress.getRemainingCriteria().size() > 0) {
							Iterator<String> iterator = progress.getRemainingCriteria().iterator();
							while(iterator.hasNext() && blocksBroken > 0) {
								String s = iterator.next();
								progress.awardCriteria(s);
								blocksBroken --;
							}
						}
					}
					
				}, 1l);
			}
		}
	}
}
