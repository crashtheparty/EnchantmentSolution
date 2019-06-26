package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.data.type.Snow;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.nms.McMMO;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemBreakType;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class TelepathyListener extends EnchantmentListener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if(!canRun(DefaultEnchantments.TELEPATHY, event)) return;
		Player player = event.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
			return;
		Block block = event.getBlock();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			if (Enchantments.hasEnchantment(item, DefaultEnchantments.TELEPATHY)) {
				if(block.getRelative(BlockFace.DOWN).getType() == Material.LAVA) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.NO_PANIC, "lava");
				}
				Collection<ItemStack> drops = block.getDrops(item);
				if (ItemUtils.getShulkerBoxes().contains(block.getType())) {
					Iterator<ItemStack> i = drops.iterator();
					while(i.hasNext()) {
						ItemStack drop = i.next();
						if(ItemUtils.getShulkerBoxes().contains(drop.getType())) {
							BlockStateMeta im = (BlockStateMeta) drop.getItemMeta();
							Container container = (Container) block.getState();
							im.setBlockState(container);
							if(block.getMetadata("shulker_name") != null) {
								for(MetadataValue value : block.getMetadata("shulker_name")) {
									im.setDisplayName(value.asString());
								}
							}
							drop.setItemMeta(im);
							if(block.getMetadata("soulbound").size() > 0) {
								drop = Enchantments.addEnchantmentsToItem(drop, Arrays.asList(
										new EnchantmentLevel(DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.SOULBOUND), 1)));
							}
							ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
							i.remove();
							AdvancementUtils.awardCriteria(player, ESAdvancement.HEY_IT_WORKS, "shulker_box");
						}
					}
					giveItems(player, item, block, drops);
					damageItem(event);
					return;
				} else if (block.getState() instanceof Container) {
					Iterator<ItemStack> i = drops.iterator();
					while(i.hasNext()) {
						ItemStack drop = i.next();
						ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
					}
					Container container = (Container) block.getState();
					if(container.getInventory().getHolder() instanceof DoubleChest) {
						DoubleChest doubleChest = (DoubleChest) container.getInventory().getHolder();
						if (doubleChest.getLeftSide().getInventory().getLocation().equals(container.getLocation())) {
							Inventory inv = doubleChest.getLeftSide().getInventory();
							for(int j = 0; j < 27; j++) {
								ItemStack drop = inv.getItem(j);
								if(drop != null) {
									ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
								}
								inv.setItem(j, new ItemStack(Material.AIR));
							}
						} else {
							Inventory inv = doubleChest.getRightSide().getInventory();
							for(int j = 27; j < 54; j++) {
								ItemStack drop = inv.getItem(j);
								if(drop != null) {
									ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
								}
								inv.setItem(j, new ItemStack(Material.AIR));
							}
						}
					} else {
						for(ItemStack drop : container.getInventory().getContents()) {
							if(drop != null) {
								ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
							}
						}
						container.getInventory().clear();
					}
					damageItem(event);
					return;
				}
				if(block.getType().equals(Material.SNOW) && ItemBreakType.getType(item.getType()).getBreakTypes().contains(Material.SNOW)) {
					int num = ((Snow) block.getBlockData()).getLayers();
					drops.add(new ItemStack(Material.SNOWBALL, num));
				}
				giveItems(player, item, block, drops);
				if (Enchantments.hasEnchantment(item, DefaultEnchantments.GOLD_DIGGER)) {
					ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, block);
					if (goldDigger != null) {
						event.getPlayer().giveExp(GoldDiggerListener.GoldDiggerCrop.getExp(block.getType(),
								Enchantments.getLevel(item, DefaultEnchantments.GOLD_DIGGER)));
						ItemUtils.giveItemToPlayer(player, goldDigger, player.getLocation(), true);
					}
				}
				damageItem(event);
			}
		}
	}
	
	private void damageItem(BlockBreakEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
			switch(event.getBlock().getType()) {
			case IRON_ORE:
			case GOLD_ORE:
				event.setExpToDrop((int) (Math.random() * 3) + 1);
				break;
			default:
				break;
			}
		}
		AbilityUtils.giveExperience(player, event.getExpToDrop());
		player.incrementStatistic(Statistic.MINE_BLOCK, event.getBlock().getType());
		player.incrementStatistic(Statistic.USE_ITEM, item.getType());
		super.damageItem(player, item);
		McMMO.handleMcMMO(event, item);
		event.getBlock().setType(Material.AIR);
	}
	
	private void giveItems(Player player, ItemStack item, Block block, Collection<ItemStack> drops) {
		if (Enchantments.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			Collection<ItemStack> fortuneItems = AbilityUtils.getFortuneItems(item, block,
					drops);
			for(ItemStack drop: fortuneItems) {
				ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
			}
		} else if (Enchantments.hasEnchantment(item, Enchantment.SILK_TOUCH)
				&& AbilityUtils.getSilkTouchItem(block, item) != null) {
			ItemUtils.giveItemToPlayer(player, AbilityUtils.getSilkTouchItem(block, item),
					player.getLocation(), true);
		} else {
			if (Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
				ItemStack smelted = AbilityUtils.getSmelteryItem(block, item);
				if (smelted != null) {
					ItemUtils.giveItemToPlayer(player, smelted, player.getLocation(), true);
				} else {
					for(ItemStack drop: drops) {
						ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
					}
				}
			} else {
				for(ItemStack drop: drops) {
					ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
				}
			}
		}
	}
}
