package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.listeners.abilities.mcmmo.McMMOHandler;
import org.ctp.enchantmentsolution.utils.AbilityUtilities;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class TelepathyListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.TELEPATHY)) return;
		Player player = event.getPlayer();
		if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			if (Enchantments
					.hasEnchantment(item, DefaultEnchantments.TELEPATHY)) {
				if(Enchantments.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
					Collection<ItemStack> fortuneItems = AbilityUtilities.getFortuneItems(item, event.getBlock(), event.getBlock().getDrops(item));
					for (ItemStack drop : fortuneItems) {
						ItemUtils.giveItemToPlayer(player, drop, player.getLocation());
					}
				} else if(Enchantments.hasEnchantment(item, Enchantment.SILK_TOUCH) && AbilityUtilities.getSilkTouchItem(event.getBlock(), item) != null) {
					ItemUtils.giveItemToPlayer(player, AbilityUtilities.getSilkTouchItem(event.getBlock(), item), player.getLocation());
				} else {
					if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
						ItemStack smelted = AbilityUtilities.getSmelteryItem(event.getBlock(), item);
						if(smelted != null) {
							ItemUtils.giveItemToPlayer(player, smelted, player.getLocation());
						} else {
							for (ItemStack drop : event.getBlock().getDrops(item)) {
								ItemUtils.giveItemToPlayer(player, drop, player.getLocation());
							}
						}
					}else {
						for (ItemStack drop : event.getBlock().getDrops(item)) {
							ItemUtils.giveItemToPlayer(player, drop, player.getLocation());
						}
					}
				}
				int unbreaking = Enchantments.getLevel(item, Enchantment.DURABILITY);
				double chance = (1.0D) / (unbreaking + 1.0D);
				double random = Math.random();
				if(chance > random) {
					item.setDurability((short) (item.getDurability() + 1));
					if(item.getDurability() > item.getType().getMaxDurability()) {
						player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
					}
				}
				if(Bukkit.getPluginManager().isPluginEnabled("mcMMO")) {
					McMMOHandler.handleMcMMO(event);
				}
				event.getPlayer().giveExp(event.getExpToDrop());
				event.getBlock().setType(Material.AIR);
			}
		}
	}
}
