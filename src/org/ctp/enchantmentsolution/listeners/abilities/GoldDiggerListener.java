package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;

public class GoldDiggerListener extends EnchantmentListener {

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if(!canRun(DefaultEnchantments.GOLD_DIGGER, event)) return;
		if(event.isCancelled()) return;
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			if (!Enchantments
					.hasEnchantment(item, DefaultEnchantments.TELEPATHY)) {
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.GOLD_DIGGER)) {
					Location loc = event.getBlock().getLocation().clone().add(0.5, 0.5, 0.5);
					ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, event.getBlock());
					if(goldDigger != null) {
						AbilityUtils.dropExperience(loc, 
								GoldDiggerCrop.getExp(event.getBlock().getType(), Enchantments.getLevel(item, DefaultEnchantments.GOLD_DIGGER)));
						Item droppedItem = player.getWorld().dropItem(
								loc,
								goldDigger);
						droppedItem.setVelocity(new Vector(0,0,0));
						player.incrementStatistic(Statistic.USE_ITEM, item.getType());
						super.damageItem(player, item);
					}
				}
			}
		}
	}
	
	
	public enum GoldDiggerCrop {
		WHEAT(Material.WHEAT, 2), CARROTS(Material.CARROTS, 2), POTATOES(Material.POTATOES, 2), BEETROOTS(Material.BEETROOTS, 2),
		NETHER_WARTS(Material.NETHER_WART, 3);
		
		private Material material;
		private int exp;
		
		private GoldDiggerCrop(Material material, int exp) {
			this.material = material;
			this.exp = exp;
		}

		public Material getMaterial() {
			return material;
		}

		public int getExp() {
			return exp;
		}
		
		public static int getExp(Material material, int level) {
			for(GoldDiggerCrop value : values()) {
				if(value.getMaterial() == material) {
					return level * value.getExp();
				}
			}
			return 0;
		}
	}
}