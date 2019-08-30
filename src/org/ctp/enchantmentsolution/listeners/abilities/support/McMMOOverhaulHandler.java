package org.ctp.enchantmentsolution.listeners.abilities.support;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.Fortune;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;
import org.ctp.enchantmentsolution.utils.items.SilkTouch;
import org.ctp.enchantmentsolution.utils.items.Smeltery;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.config.Config;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.listeners.BlockListener;

public class McMMOOverhaulHandler {

	public static void handleMcMMO(BlockBreakEvent event, ItemStack item) {
		BlockListener listener = new BlockListener(mcMMO.p);
		listener.onBlockBreakHigher(event);
		listener.onBlockBreak(event);

		Block block = event.getBlock();
		BlockState state = event.getBlock().getState();
		
		if (Enchantments.hasEnchantment(item, DefaultEnchantments.TELEPATHY)) {
			Collection<ItemStack> drops = event.getBlock().getDrops(item);
			if(state.getMetadata(mcMMO.BONUS_DROPS_METAKEY).size() > 0) {
				for(MetadataValue value : state.getMetadata(mcMMO.BONUS_DROPS_METAKEY)) {
					int num = value.asInt();
					// TODO: add a TelepathyBonusDrops event
					while(num > 0) {
						giveItems(event.getPlayer(), item, block, drops);
						num--;
					}
					event.getBlock().getState().removeMetadata(mcMMO.BONUS_DROPS_METAKEY, mcMMO.p);
				}
			}
		} else if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
			ItemStack smelted = Smeltery.getSmelteryItem(block, item);
			ItemStack drop = item.clone();
			// TODO: add a SmelteryBonusDrops event
			if(smelted != null) {
				drop = smelted.clone();
			}
			if (!Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.MINING, block.getType())
					&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.HERBALISM, block.getType())
					&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.WOODCUTTING, block.getType()))
				return;
			if(state.getMetadata(mcMMO.BONUS_DROPS_METAKEY).size() > 0) {
				for(MetadataValue value : state.getMetadata(mcMMO.BONUS_DROPS_METAKEY)) {
					int num = value.asInt();
					while(num > 0) {
						event.getBlock().getState().getWorld().dropItemNaturally(block.getState().getLocation(), drop);
						num--;
					}
					event.getBlock().getState().removeMetadata(mcMMO.BONUS_DROPS_METAKEY, mcMMO.p);
				}
			}
		} else {
			for(ItemStack drop: event.getBlock().getDrops(item)) {
				if (!Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.MINING, drop.getType())
						&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.HERBALISM, drop.getType())
						&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.WOODCUTTING, drop.getType()))
					continue;
				if(state.getMetadata(mcMMO.BONUS_DROPS_METAKEY).size() > 0) {
					for(MetadataValue value : state.getMetadata(mcMMO.BONUS_DROPS_METAKEY)) {
						int num = value.asInt();
						while(num > 0) {
							event.getBlock().getState().getWorld().dropItemNaturally(block.getState().getLocation(), drop);
							num--;
						}
						event.getBlock().getState().removeMetadata(mcMMO.BONUS_DROPS_METAKEY, mcMMO.p);
					}
				}
			}
		}
	}

	private static void giveItems(Player player, ItemStack item, Block block, Collection<ItemStack> drops) {
		if (Enchantments.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS)) {
			Collection<ItemStack> fortuneItems = Fortune.getFortuneItems(item, block, drops);
			for(ItemStack drop: fortuneItems) {
				ItemUtils.giveItemToPlayer(player, drop, player.getLocation(), true);
			}
		} else if (Enchantments.hasEnchantment(item, Enchantment.SILK_TOUCH)
				&& SilkTouch.getSilkTouchItem(block, item) != null) {
			ItemUtils.giveItemToPlayer(player, SilkTouch.getSilkTouchItem(block, item), player.getLocation(), true);
		} else {
			if (Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
				ItemStack smelted = Smeltery.getSmelteryItem(block, item);
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
