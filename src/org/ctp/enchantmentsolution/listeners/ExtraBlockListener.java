package org.ctp.enchantmentsolution.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class ExtraBlockListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		// TODO: add the walker listeners blocks to the listener
		// if(WalkerListener.getBlocks(DefaultEnchantments.VOID_WALKER).contains(event.getBlock()))
		// {
		// event.setCancelled(true);
		// AdvancementUtils.awardCriteria(event.getPlayer(),
		// ESAdvancement.DETERMINED_CHEATER, "cheater");
		// }
		// if(WalkerListener.getBlocks(DefaultEnchantments.MAGMA_WALKER).contains(event.getBlock()))
		// {
		// event.setCancelled(true);
		// }

		// TODO: create break shulker boxes event
		// if(ItemUtils.getShulkerBoxes().contains(event.getBlock().getType())) {
		// if(!Enchantments.hasEnchantment(event.getPlayer().getInventory().getItemInMainHand(),
		// DefaultEnchantments.TELEPATHY)) {
		// Player player = event.getPlayer();
		// ItemStack item = player.getInventory().getItemInMainHand();
		// Block block = event.getBlock();
		// if(block.getMetadata("soulbound").size() > 0) {
		// event.setCancelled(true);
		// Collection<ItemStack> drops = block.getDrops();
		// Iterator<ItemStack> i = drops.iterator();
		// while(i.hasNext()) {
		// ItemStack drop = i.next();
		// if(ItemUtils.getShulkerBoxes().contains(drop.getType())) {
		// BlockStateMeta im = (BlockStateMeta) drop.getItemMeta();
		// Container container = (Container) block.getState();
		// im.setBlockState(container);
		// if(block.getMetadata("shulker_name") != null) {
		// for(MetadataValue value : event.getBlock().getMetadata("shulker_name")) {
		// im.setDisplayName(value.asString());
		// }
		// }
		// drop.setItemMeta(im);
		// drop = Enchantments.addEnchantmentsToItem(drop,
		// Arrays.asList(new
		// EnchantmentLevel(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SOULBOUND),
		// 1)));
		// Location fallback = block.getLocation().clone().add(0.5, 0.5, 0.5);
		// Item droppedItem = player.getWorld().dropItem(
		// fallback,
		// drop);
		// droppedItem.setVelocity(new Vector(0,0,0));
		// droppedItem.teleport(fallback);
		// i.remove();
		// }
		// }
		// giveItems(event.getPlayer(), item, block, drops);
		// damageItem(event);
		// block.setType(Material.AIR);
		// }
		// }
		// }
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
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
