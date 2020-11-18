package org.ctp.enchantmentsolution.listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.blocks.BlockBreakMultiEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.BlockUtils;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GaiaUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GaiaUtils.GaiaTrees;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class ExtraBlockListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (GaiaUtils.hasLocation(event.getBlock().getLocation())) {
			for(GaiaTrees tree: GaiaTrees.values())
				if (event.getBlock().getType() == tree.getSapling().getMaterial()) {
					event.getBlock().setType(Material.AIR);
					event.setCancelled(true);
					AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.SCOURGE_OF_THE_FOREST, "tree");
				}
			GaiaUtils.removeLocation(event.getBlock().getLocation());
		}

		if (ConfigString.MULTI_BLOCK_ASYNC.getBoolean() && BlockUtils.multiBlockBreakContains(event.getBlock().getLocation()) && !(event instanceof BlockBreakMultiEvent)) event.setCancelled(true);

		if (WalkerUtils.hasBlock(event.getBlock())) {
			event.setCancelled(true);
			if (WalkerUtils.getWalker(event.getBlock()).getEnchantment() == RegisterEnchantments.VOID_WALKER) AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.DETERMINED_CHEATER, "cheater");
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockDropItem(BlockDropItemEvent event) {
		BlockState state = event.getBlockState();
		BlockData data = state.getBlockData();
		List<Material> shulker = ESArrays.getShulkerBoxes();

		for(Item i: event.getItems()) {
			ItemStack eventItem = i.getItemStack();
			if (shulker.contains(data.getMaterial()) && shulker.contains(eventItem.getType())) {
				Block block = event.getBlock();
				if (block.getMetadata("soulbound").size() > 0) {
					eventItem = EnchantmentUtils.getSoulboundShulkerBox(state, eventItem);
					i.setItemStack(eventItem);
				}
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
