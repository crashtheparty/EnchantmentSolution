package org.ctp.enchantmentsolution.mcmmo;

import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockBreakEvent;
import org.ctp.enchantmentsolution.utils.VersionUtils;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.listeners.BlockListener;

public class McMMOHandler {
	
	public static void handleMcMMO(BlockBreakEvent event) {
		BlockListener listener = new BlockListener(mcMMO.p);
		listener.onBlockBreakHigher(event);
		listener.onBlockBreak(event);
		
		if (VersionUtils.getMcMMOType().equals("Overhaul")) {
//			Block block = event.getBlock();
//			BlockState state = event.getBlock().getState();
//			
//			if (Enchantments.hasEnchantment(item, DefaultEnchantments.TELEPATHY)) {
//				Collection<ItemStack> drops = event.getBlock().getDrops(item);
//				if(state.getMetadata(mcMMO.BONUS_DROPS_METAKEY).size() > 0) {
//					for(MetadataValue value : state.getMetadata(mcMMO.BONUS_DROPS_METAKEY)) {
//						int num = value.asInt();
//						// TODO: add a TelepathyBonusDrops event
//						while(num > 0) {
//							giveItems(event.getPlayer(), item, block, drops);
//							num--;
//						}
//						event.getBlock().getState().removeMetadata(mcMMO.BONUS_DROPS_METAKEY, mcMMO.p);
//					}
//				}
//			} else if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
//				ItemStack smelted = Smeltery.getSmelteryItem(block, item);
//				ItemStack drop = item.clone();
//				// TODO: add a SmelteryBonusDrops event
//				if(smelted != null) {
//					drop = smelted.clone();
//				}
//				if (!Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.MINING, block.getType())
//						&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.HERBALISM, block.getType())
//						&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.WOODCUTTING, block.getType()))
//					return;
//				if(state.getMetadata(mcMMO.BONUS_DROPS_METAKEY).size() > 0) {
//					for(MetadataValue value : state.getMetadata(mcMMO.BONUS_DROPS_METAKEY)) {
//						int num = value.asInt();
//						while(num > 0) {
//							event.getBlock().getState().getWorld().dropItemNaturally(block.getState().getLocation(), drop);
//							num--;
//						}
//						event.getBlock().getState().removeMetadata(mcMMO.BONUS_DROPS_METAKEY, mcMMO.p);
//					}
//				}
//			} else {
//				for(ItemStack drop: event.getBlock().getDrops(item)) {
//					if (!Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.MINING, drop.getType())
//							&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.HERBALISM, drop.getType())
//							&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.WOODCUTTING, drop.getType()))
//						continue;
//					if(state.getMetadata(mcMMO.BONUS_DROPS_METAKEY).size() > 0) {
//						for(MetadataValue value : state.getMetadata(mcMMO.BONUS_DROPS_METAKEY)) {
//							int num = value.asInt();
//							while(num > 0) {
//								event.getBlock().getState().getWorld().dropItemNaturally(block.getState().getLocation(), drop);
//								num--;
//							}
//							event.getBlock().getState().removeMetadata(mcMMO.BONUS_DROPS_METAKEY, mcMMO.p);
//						}
//					}
//				}
//			}
		}
	}

	public static void customName(Entity e) {
		if (e.hasMetadata(mcMMO.customNameKey)) {
			String oldName = e.getMetadata(mcMMO.customNameKey).get(0).asString();
			e.setCustomName(oldName);
		}
		if (e.hasMetadata(mcMMO.customVisibleKey)) {
			boolean oldNameVisible = e.getMetadata(mcMMO.customVisibleKey).get(0).asBoolean();
			e.setCustomNameVisible(oldNameVisible);
		}
	}
}
