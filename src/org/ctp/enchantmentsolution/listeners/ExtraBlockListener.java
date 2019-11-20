package org.ctp.enchantmentsolution.listeners;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.mcmmo.McMMOHandler;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.abillityhelpers.WalkerUtils;
import org.ctp.enchantmentsolution.utils.compatibility.JobsUtils;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class ExtraBlockListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (WalkerUtils.hasBlock(event.getBlock())) {
			event.setCancelled(true);
			if (WalkerUtils.getWalker(event.getBlock()).getEnchantment() == RegisterEnchantments.VOID_WALKER) {
				AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.DETERMINED_CHEATER, "cheater");
			}
		}

		if (ESArrays.getShulkerBoxes().contains(event.getBlock().getType())) {
			if (!ItemUtils.hasEnchantment(event.getPlayer().getInventory().getItemInMainHand(),
			RegisterEnchantments.TELEPATHY)) {

			}
		}

		if (ESArrays.getShulkerBoxes().contains(event.getBlock().getType())) {
			if (!ItemUtils.hasEnchantment(event.getPlayer().getInventory().getItemInMainHand(),
			RegisterEnchantments.TELEPATHY)) {
				Player player = event.getPlayer();
				ItemStack item = player.getInventory().getItemInMainHand();
				Block block = event.getBlock();
				if (block.getMetadata("soulbound").size() > 0) {
					event.setCancelled(true);
					Collection<ItemStack> drops = block.getDrops();

					drops = ItemUtils.getSoulboundShulkerBox(player, block, drops);
					AbilityUtils.giveExperience(player, event.getExpToDrop());
					player.incrementStatistic(Statistic.MINE_BLOCK, event.getBlock().getType());
					player.incrementStatistic(Statistic.USE_ITEM, item.getType());
					DamageUtils.damageItem(player, item);
					McMMOHandler.handleMcMMO(event, item);
					JobsUtils.sendBlockBreakAction(event);
					event.getBlock().setType(Material.AIR);
					ItemUtils.dropItems(drops, block.getLocation(), true);
					block.setType(Material.AIR);
				}
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (ESArrays.getShulkerBoxes() != null && event.getItemInHand() != null) {
			if (ESArrays.getShulkerBoxes().contains(event.getItemInHand().getType())) {
				ItemMeta meta = event.getItemInHand().getItemMeta();
				if (meta != null) {
					String name = meta.getDisplayName();
					if (name != null && !name.equals("")) {
						event.getBlockPlaced().setMetadata("shulker_name",
						new FixedMetadataValue(EnchantmentSolution.getPlugin(), name));
					}
					if (ItemUtils.hasEnchantment(event.getItemInHand(), RegisterEnchantments.SOULBOUND)) {
						event.getBlockPlaced().setMetadata("soulbound",
						new FixedMetadataValue(EnchantmentSolution.getPlugin(), true));
					}
				}
			}
		}
	}

	// private void damageItem(BlockBreakEvent event) {
	// Player player = event.getPlayer();
	// ItemStack item = player.getInventory().getItemInMainHand();
	// if (player.getGameMode().equals(GameMode.CREATIVE) ||
	// player.getGameMode().equals(GameMode.SPECTATOR))
	// return;
	// int numBreaks = 0;
	// int unbreaking = ItemUtils.getLevel(item, Enchantment.DURABILITY);
	// for(int i = 0; i < 1; i++) {
	// double chance = (1.0D) / (unbreaking);
	// double random = Math.random();
	// if(chance > random) {
	// numBreaks ++;
	// }
	// };
	// if (numBreaks > 0) {
	// DamageUtils.setDamage(item, DamageUtils.getDamage(item.getItemMeta()) +
	// numBreaks);
	// if (DamageUtils.getDamage(item.getItemMeta()) >
	// item.getType().getMaxDurability()) {
	// player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
	// player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1,
	// 1);
	// player.incrementStatistic(Statistic.BREAK_ITEM, item.getType());
	// }
	// }
	// }

	// private void giveItems(Player player, ItemStack item, Block block,
	// Collection<ItemStack> drops) {
	// if (ItemUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
	// Collection<ItemStack> fortuneItems = AbilityUtils.getFortuneItems(item,
	// block,
	// drops);
	// for(ItemStack drop: fortuneItems) {
	// ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
	// }
	// } else if (Enchantments.hasEnchantment(item, Enchantment.SILK_TOUCH)
	// && AbilityUtils.getSilkTouchItem(block, item) != null) {
	// ItemUtils.giveItemToPlayer(player, AbilityUtils.getSilkTouchItem(block,
	// item),
	// player.getLocation(), true);
	// } else {
	// if (Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
	// ItemStack smelted = AbilityUtils.getSmelteryItem(block, item);
	// if (smelted != null) {
	// ItemUtils.giveItemToPlayer(player, smelted, player.getLocation(), true);
	// } else {
	// for(ItemStack drop: drops) {
	// ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
	// }
	// }
	// } else {
	// for(ItemStack drop: drops) {
	// ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
	// }
	// }
	// }
	// }
}
