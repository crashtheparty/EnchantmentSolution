package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.DoubleChest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.McMMO;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class TelepathyListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (!DefaultEnchantments.isEnabled(DefaultEnchantments.TELEPATHY))
			return;
		Player player = event.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
			return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			if (Enchantments.hasEnchantment(item, DefaultEnchantments.TELEPATHY)) {
				Collection<ItemStack> drops = event.getBlock().getDrops(item);
				if (event.getBlock().getType() == Material.SHULKER_BOX) {
					Iterator<ItemStack> i = drops.iterator();
					while(i.hasNext()) {
						ItemStack drop = i.next();
						if(drop.getType() == Material.SHULKER_BOX) {
							BlockStateMeta im = (BlockStateMeta) drop.getItemMeta();
							im.setBlockState(event.getBlock().getState());
							drop.setItemMeta(im);
							ItemUtils.giveItemToPlayer(player, drop, player.getLocation());
							i.remove();
						}
					}
					giveItems(player, item, event.getBlock(), drops);
					damageItem(event);
					return;
				} else if (event.getBlock().getState() instanceof Container) {
					Iterator<ItemStack> i = drops.iterator();
					while(i.hasNext()) {
						ItemStack drop = i.next();
						ItemUtils.giveItemToPlayer(player, drop, player.getLocation());
					}
					Container container = (Container) event.getBlock().getState();
					if(container.getInventory().getHolder() instanceof DoubleChest) {
						DoubleChest doubleChest = (DoubleChest) container.getInventory().getHolder();
						if (doubleChest.getLeftSide().getInventory().getLocation().equals(container.getLocation())) {
							Inventory inv = doubleChest.getRightSide().getInventory();
							for(int j = 0; j < 27; j++) {
								ItemStack drop = inv.getItem(j);
								if(drop != null) {
									ItemUtils.giveItemToPlayer(player, drop, player.getLocation());
								}
								inv.setItem(j, new ItemStack(Material.AIR));
							}
						} else {
							Inventory inv = doubleChest.getRightSide().getInventory();
							for(int j = 27; j < 54; j++) {
								ItemStack drop = inv.getItem(j);
								if(drop != null) {
									ItemUtils.giveItemToPlayer(player, drop, player.getLocation());
								}
								inv.setItem(j, new ItemStack(Material.AIR));
							}
						}
					} else {
						for(ItemStack drop : container.getInventory().getContents()) {
							if(drop != null) {
								ItemUtils.giveItemToPlayer(player, drop, player.getLocation());
							}
						}
						container.getInventory().clear();
					}
					damageItem(event);
					return;
				}
				giveItems(player, item, event.getBlock(), drops);
				if (Enchantments.hasEnchantment(item, DefaultEnchantments.GOLD_DIGGER)) {
					ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, event.getBlock());
					if (goldDigger != null) {
						event.getPlayer().giveExp(GoldDiggerListener.GoldDiggerCrop.getExp(event.getBlock().getType(),
								Enchantments.getLevel(item, DefaultEnchantments.GOLD_DIGGER)));
						ItemUtils.giveItemToPlayer(player, goldDigger, player.getLocation());
					}
				}
				damageItem(event);
			}
		}
	}
	
	private void damageItem(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
			return;
		ItemStack item = player.getInventory().getItemInMainHand();
		int unbreaking = Enchantments.getLevel(item, Enchantment.DURABILITY);
		double chance = (1.0D) / (unbreaking + 1.0D);
		double random = Math.random();
		if (chance > random) {
			DamageUtils.setDamage(item, DamageUtils.getDamage(item.getItemMeta()) + 1);
			if (DamageUtils.getDamage(item.getItemMeta()) > item.getType().getMaxDurability()) {
				player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
			}
		}
		McMMO.handleMcMMO(event);
		event.getPlayer().giveExp(event.getExpToDrop());
		event.getBlock().setType(Material.AIR);
	}
	
	private void giveItems(Player player, ItemStack item, Block block, Collection<ItemStack> drops) {
		if (Enchantments.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			Collection<ItemStack> fortuneItems = AbilityUtils.getFortuneItems(item, block,
					drops);
			for(ItemStack drop: fortuneItems) {
				ItemUtils.giveItemToPlayer(player, drop, player.getLocation());
			}
		} else if (Enchantments.hasEnchantment(item, Enchantment.SILK_TOUCH)
				&& AbilityUtils.getSilkTouchItem(block, item) != null) {
			ItemUtils.giveItemToPlayer(player, AbilityUtils.getSilkTouchItem(block, item),
					player.getLocation());
		} else {
			if (Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
				ItemStack smelted = AbilityUtils.getSmelteryItem(block, item);
				if (smelted != null) {
					ItemUtils.giveItemToPlayer(player, smelted, player.getLocation());
				} else {
					for(ItemStack drop: drops) {
						ItemUtils.giveItemToPlayer(player, drop, player.getLocation());
					}
				}
			} else {
				for(ItemStack drop: drops) {
					ItemUtils.giveItemToPlayer(player, drop, player.getLocation());
				}
			}
		}
	}
}
