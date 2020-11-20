package org.ctp.enchantmentsolution.mcmmo;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.crashapi.utils.LocationUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.drops.SmelteryBonusDropsEvent;
import org.ctp.enchantmentsolution.events.drops.TelepathyBonusDropsEvent;
import org.ctp.enchantmentsolution.utils.VersionUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.SmelteryMaterial;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.items.SmelteryUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.api.ItemSpawnReason;
import com.gmail.nossr50.datatypes.meta.BonusDropMeta;
import com.gmail.nossr50.events.items.McMMOItemSpawnEvent;

public class McMMOBlockDrops {

	protected static void handleBlockDrops(BlockDropItemEvent event, ItemStack item, Enchantment enchantment) {
		if (VersionUtils.getMcMMOType().equals("Overhaul")) {
			Location loc = LocationUtils.offset(event.getBlock().getLocation());
			BlockState state = event.getBlockState();
			if (state.getMetadata(mcMMO.BONUS_DROPS_METAKEY).size() > 0) for(MetadataValue value: state.getMetadata(mcMMO.BONUS_DROPS_METAKEY)) {
				Collection<ItemStack> drops = getDoubleDrops(event);
				ESPlayer player = EnchantmentSolution.getESPlayer(event.getPlayer());

				if (enchantment == RegisterEnchantments.TELEPATHY && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.TELEPATHY)) {
					TelepathyBonusDropsEvent telepathyBonus = new TelepathyBonusDropsEvent(event.getPlayer(), drops, value.asInt());
					Bukkit.getPluginManager().callEvent(telepathyBonus);

					if (!telepathyBonus.isCancelled()) {
						int num = telepathyBonus.getMultiplyAmount();
						while (num > 0) {
							player.addTelepathyItems(drops);
							num--;
						}
					}
				} else if (enchantment == RegisterEnchantments.SMELTERY && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.SMELTERY)) for(ItemStack drop: drops) {
					SmelteryMaterial smelted = SmelteryUtils.getSmelteryItem(state.getBlockData(), drop, item);
					if (smelted != null) {
						SmelteryBonusDropsEvent smelteryBonus = new SmelteryBonusDropsEvent(event.getPlayer(), Arrays.asList(smelted.getSmelted()), value.asInt());
						Bukkit.getPluginManager().callEvent(smelteryBonus);

						if (!smelteryBonus.isCancelled()) {
							int num = smelteryBonus.getMultiplyAmount();
							while (num > 0) {
								if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.TELEPATHY)) player.addTelepathyItems(drops);
								else
									ItemUtils.dropItems(smelteryBonus.getDrops(), loc);
								num--;
							}
						}
					}
				}
			}
		}
	}

	// Taken directly from mcMMO's
	// com.gmail.nossr50.listeners.BlockListener.onBlockDropItemEvent(BlockDropItemEvent
	// event)
	// Edited slightly with com.gmail.nossr50.util.Misc.spawnItemNaturally(Location
	// location, ItemStack itemStack, ItemSpawnReason itemSpawnReason)
	private static Collection<ItemStack> getDoubleDrops(BlockDropItemEvent event) {
		Collection<ItemStack> drops = new ArrayList<ItemStack>();
		HashSet<Material> uniqueMaterials = new HashSet<>();
		boolean dontRewardTE = false;
		int blockCount = 0;

		for(Item item: event.getItems()) {
			uniqueMaterials.add(item.getItemStack().getType());

			if (item.getItemStack().getType().isBlock()) blockCount++;
		}

		if (uniqueMaterials.size() > 1) dontRewardTE = true;

		if (blockCount <= 1) for(Item item: event.getItems()) {
			ItemStack is = new ItemStack(item.getItemStack());

			if (is.getAmount() <= 0) continue;

			if (dontRewardTE) if (!is.getType().isBlock()) continue;

			if (event.getBlock().getMetadata(mcMMO.BONUS_DROPS_METAKEY).size() > 0) {
				BonusDropMeta bonusDropMeta = (BonusDropMeta) event.getBlock().getMetadata(mcMMO.BONUS_DROPS_METAKEY).get(0);
				int bonusCount = bonusDropMeta.asInt();

				if (bonusCount > 0) {
					if (is.getType() == Material.AIR || event.getBlockState().getLocation().getWorld() == null) continue;
					is.setAmount(bonusCount);

					McMMOItemSpawnEvent mcmmoEvent = new McMMOItemSpawnEvent(event.getBlockState().getLocation(), is, ItemSpawnReason.BONUS_DROPS);
					mcMMO.p.getServer().getPluginManager().callEvent(mcmmoEvent);

					if (mcmmoEvent.isCancelled()) continue;
					drops.add(is);
				}
			}
		}

		if (event.getBlock().hasMetadata(mcMMO.BONUS_DROPS_METAKEY)) event.getBlock().removeMetadata(mcMMO.BONUS_DROPS_METAKEY, mcMMO.p);
		return drops;
	}
}
