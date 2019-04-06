package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.nms.ItemMoisturizeType;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;

public class MoisturizeListener extends EnchantmentListener{
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(!canRun(DefaultEnchantments.MOISTURIZE, event)) return;
		
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getHand() == EquipmentSlot.OFF_HAND) {
		        return; // off hand packet, ignore.
		    }
			ItemStack item = event.getItem();
			if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.MOISTURIZE)) {
				Block block = event.getClickedBlock();
				ItemMoisturizeType type = ItemMoisturizeType.getType(block.getType());
				if(type != null) {
					switch(type.getName().toLowerCase()) {
					case "wet":
						block.setType(ItemMoisturizeType.getWet(block.getType()));
						super.damageItem(event.getPlayer(), item);
						event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
						event.getPlayer().incrementStatistic(Statistic.USE_ITEM, item.getType());
						break;
					case "unsmelt":
						block.setType(ItemMoisturizeType.getUnsmelt(block.getType()));
						super.damageItem(event.getPlayer(), item);
						event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
						event.getPlayer().incrementStatistic(Statistic.USE_ITEM, item.getType());
						break;
					case "waterlog":
						if(block.getBlockData() instanceof Waterlogged) {
							Waterlogged water = (Waterlogged) block.getBlockData();
							if(block.getState() instanceof Container) {
								if(event.getPlayer().isSneaking()) {
									if(!water.isWaterlogged()) {
										water.setWaterlogged(true);
										block.setBlockData((BlockData) water);
										super.damageItem(event.getPlayer(), item);
										event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
										event.getPlayer().incrementStatistic(Statistic.USE_ITEM, item.getType());
										event.setCancelled(true);
									}
								}
							} else {
								if(!water.isWaterlogged()) {
									water.setWaterlogged(true);
									block.setBlockData((BlockData) water);
									super.damageItem(event.getPlayer(), item);
									event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
									event.getPlayer().incrementStatistic(Statistic.USE_ITEM, item.getType());
								}
							}
						}
						break;
					}
				}
			}
		}
	}

}
