package org.ctp.enchantmentsolution.mcmmo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.generate.FishingEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.drops.SmelteryBonusDropsEvent;
import org.ctp.enchantmentsolution.events.drops.TelepathyBonusDropsEvent;
import org.ctp.enchantmentsolution.utils.VersionUtils;
import org.ctp.enchantmentsolution.utils.abillityhelpers.SmelteryMaterial;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;
import org.ctp.enchantmentsolution.utils.items.SmelteryUtils;
import org.ctp.enchantmentsolution.utils.items.TelepathyUtils;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.config.Config;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.listeners.BlockListener;

public class McMMOHandler {

	public static void handleMcMMO(BlockBreakEvent event, ItemStack item) {
		if(EnchantmentSolution.getPlugin().getMcMMOType().equals("Disabled")) {
			return;
		}
		BlockListener listener = new BlockListener(mcMMO.p);
		listener.onBlockBreakHigher(event);
		listener.onBlockBreak(event);

		if (VersionUtils.getMcMMOType().equals("Overhaul")) {
			Block block = event.getBlock();
			BlockState state = event.getBlock().getState();

			if (ItemUtils.hasEnchantment(item, RegisterEnchantments.TELEPATHY)) {
				if (!Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.MINING, block.getType())
				&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.HERBALISM, block.getType())
				&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.WOODCUTTING, block.getType())) {
					return;
				}
				Collection<ItemStack> drops = TelepathyUtils.handleTelepathyBonus(event, event.getPlayer(), item, block);
				if (state.getMetadata(mcMMO.BONUS_DROPS_METAKEY).size() > 0) {
					for(MetadataValue value: state.getMetadata(mcMMO.BONUS_DROPS_METAKEY)) {
						TelepathyBonusDropsEvent telepathyBonus = new TelepathyBonusDropsEvent(event.getPlayer(), drops, value.asInt());
						Bukkit.getPluginManager().callEvent(telepathyBonus);

						if(!telepathyBonus.isCancelled()) {
							int num = telepathyBonus.getMultiplyAmount();
							while (num > 0) {
								ItemUtils.giveItemsToPlayer(telepathyBonus.getPlayer(), telepathyBonus.getDrops(), telepathyBonus.getPlayer().getLocation(), true);
								num--;
							}
						}

						event.getBlock().getState().removeMetadata(mcMMO.BONUS_DROPS_METAKEY, mcMMO.p);
					}
				}
			} else if (ItemUtils.hasEnchantment(item, RegisterEnchantments.SMELTERY)) {
				if (!Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.MINING, block.getType())
				&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.HERBALISM, block.getType())
				&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.WOODCUTTING, block.getType())) {
					return;
				}
				if (state.getMetadata(mcMMO.BONUS_DROPS_METAKEY).size() > 0) {
					for(MetadataValue value: state.getMetadata(mcMMO.BONUS_DROPS_METAKEY)) {
						SmelteryMaterial smelted = SmelteryUtils.getSmelteryItem(block, item);
						SmelteryBonusDropsEvent smelteryBonus = new SmelteryBonusDropsEvent(event.getPlayer(), Arrays.asList(smelted.getSmelted()), value.asInt());
						Bukkit.getPluginManager().callEvent(smelteryBonus);

						if(!smelteryBonus.isCancelled()) {
							int num = smelteryBonus.getMultiplyAmount();
							while (num > 0) {
								ItemUtils.dropItems(smelteryBonus.getDrops(), event.getBlock().getLocation());
								num--;
							}
						}
						event.getBlock().getState().removeMetadata(mcMMO.BONUS_DROPS_METAKEY, mcMMO.p);
					}
				}
			} else {
				for(ItemStack drop: event.getBlock().getDrops(item)) {
					if (!Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.MINING, drop.getType())
					&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.HERBALISM, drop.getType())
					&& !Config.getInstance().getDoubleDropsEnabled(PrimarySkillType.WOODCUTTING, drop.getType())) {
						continue;
					}
					if (state.getMetadata(mcMMO.BONUS_DROPS_METAKEY).size() > 0) {
						for(MetadataValue value: state.getMetadata(mcMMO.BONUS_DROPS_METAKEY)) {
							int num = value.asInt();
							while (num > 0) {
								event.getBlock().getState().getWorld().dropItemNaturally(block.getState().getLocation(),
								drop);
								num--;
							}
							event.getBlock().getState().removeMetadata(mcMMO.BONUS_DROPS_METAKEY, mcMMO.p);
						}
					}
				}
			}
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

	public static List<EnchantmentLevel> getEnchants(Player player, ItemStack treasure) {
		FishingEnchantments ench = FishingEnchantments.getFishingEnchantments(player, treasure);
		if(ench != null) {
			return ench.getEnchantmentList().getEnchantments();
		}
		return null;
	}
}
