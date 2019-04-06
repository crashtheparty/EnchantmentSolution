package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.McMMO;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;

public class SmelteryListener extends EnchantmentListener{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if(!canRun(DefaultEnchantments.SMELTERY, event)) return;
		Block blockBroken = event.getBlock();
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		if(item != null) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
				ItemStack smelted = AbilityUtils.getSmelteryItem(blockBroken, item);
				if(smelted != null) {
					if(!DefaultEnchantments.isEnabled(DefaultEnchantments.TELEPATHY) || !Enchantments.hasEnchantment(item, DefaultEnchantments.TELEPATHY)) {
						switch(event.getBlock().getType()) {
						case IRON_ORE:
						case GOLD_ORE:
							AbilityUtils.dropExperience(blockBroken.getLocation().add(0.5, 0.5, 0.5), (int) (Math.random() * 3) + 1);
							break;
						default:
							break;
						}
						player.incrementStatistic(Statistic.MINE_BLOCK, event.getBlock().getType());
						player.incrementStatistic(Statistic.USE_ITEM, item.getType());
						McMMO.handleMcMMO(event);
						super.damageItem(player, item);
						event.getBlock().setType(Material.AIR);
						Item droppedItem = player.getWorld().dropItem(
								blockBroken.getLocation().add(0.5, 0.5, 0.5),
								smelted);
						droppedItem.setVelocity(new Vector(0,0,0));
					}
				}
			}
		}
	}
}
