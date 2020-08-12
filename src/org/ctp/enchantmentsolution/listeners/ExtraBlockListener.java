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
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.blocks.BlockBreakMultiEvent;
import org.ctp.enchantmentsolution.mcmmo.McMMOHandler;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.BlockUtils;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GaiaUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GaiaUtils.GaiaTrees;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerUtils;
import org.ctp.enchantmentsolution.utils.compatibility.JobsUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class ExtraBlockListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (GaiaUtils.hasLocation(event.getBlock().getLocation())) {
			for (GaiaTrees tree : GaiaTrees.values())
				if (event.getBlock().getType() == tree.getSapling().getMaterial()) {
					event.getBlock().setType(Material.AIR);
					event.setCancelled(true);
				}
			GaiaUtils.removeLocation(event.getBlock().getLocation());
		}
		
		if (ConfigString.MULTI_BLOCK_ASYNC.getBoolean() && BlockUtils.multiBlockBreakContains(event.getBlock().getLocation()) && !(event instanceof BlockBreakMultiEvent)) event.setCancelled(true);

		if (WalkerUtils.hasBlock(event.getBlock())) {
			event.setCancelled(true);
			if (WalkerUtils.getWalker(event.getBlock()).getEnchantment() == RegisterEnchantments.VOID_WALKER) AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.DETERMINED_CHEATER, "cheater");
		}

		if (ESArrays.getShulkerBoxes().contains(event.getBlock().getType()) && !EnchantmentUtils.hasEnchantment(event.getPlayer().getInventory().getItemInMainHand(), RegisterEnchantments.TELEPATHY)) {
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			Block block = event.getBlock();
			if (block.getMetadata("soulbound").size() > 0) {
				event.setCancelled(true);
				Collection<ItemStack> drops = block.getDrops();

				drops = EnchantmentUtils.getSoulboundShulkerBox(player, block, drops);
				AbilityUtils.giveExperience(player, event.getExpToDrop());
				player.incrementStatistic(Statistic.MINE_BLOCK, event.getBlock().getType());
				player.incrementStatistic(Statistic.USE_ITEM, item.getType());
				DamageUtils.damageItem(player, item);
				McMMOHandler.handleMcMMO(event, item);
				if (EnchantmentSolution.getPlugin().isJobsEnabled()) JobsUtils.sendBlockBreakAction(event);
				event.getBlock().setType(Material.AIR);
				ItemUtils.dropItems(drops, block.getLocation());
				block.setType(Material.AIR);
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (ESArrays.getShulkerBoxes() != null && event.getItemInHand() != null) if (ESArrays.getShulkerBoxes().contains(event.getItemInHand().getType())) {
			ItemMeta meta = event.getItemInHand().getItemMeta();
			if (meta != null) {
				String name = meta.getDisplayName();
				if (name != null && !name.equals("")) event.getBlockPlaced().setMetadata("shulker_name", new FixedMetadataValue(EnchantmentSolution.getPlugin(), name));
				if (EnchantmentUtils.hasEnchantment(event.getItemInHand(), RegisterEnchantments.SOULBOUND)) event.getBlockPlaced().setMetadata("soulbound", new FixedMetadataValue(EnchantmentSolution.getPlugin(), true));
			}
		}
	}
}
