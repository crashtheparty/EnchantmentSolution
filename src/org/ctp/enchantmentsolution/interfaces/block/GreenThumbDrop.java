package org.ctp.enchantmentsolution.interfaces.block;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.blocks.GreenThumbEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.interfaces.effects.block.ReplantCropEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.Crop;

public class GreenThumbDrop extends ReplantCropEffect {

	public GreenThumbDrop() {
		super(RegisterEnchantments.GREEN_THUMB, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGH, new BlockCondition[0]);
	}

	@Override
	public ReplantCropResult run(Player player, ItemStack[] items, BlockData brokenData, BlockDropItemEvent event) {
		ReplantCropResult result = super.run(player, items, brokenData, event);
		if (result.getLevel() == 0) return null;

		GreenThumbEvent greenThumb = new GreenThumbEvent(event.getBlock(), brokenData, player, result.getOverrideItems(), result.getOverrideItems(), true, 0, result.getDropStack());

		Bukkit.getPluginManager().callEvent(greenThumb);

		if (!greenThumb.isCancelled()) {
			Block block = event.getBlock();
			ItemStack dropStack = greenThumb.getSeed();
			Crop c = Crop.getCrop(brokenData.getMaterial());
			Item dropItem = result.getDropItem();
			dropStack.setAmount(dropStack.getAmount() - 1);
			if (dropStack.getAmount() == 0) dropStack.setType(Material.AIR);
			if (dropItem != null) dropItem.setItemStack(dropStack);
			Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
				block.setType(c.getBlock().getMaterial());
				Ageable newAge = (Ageable) block.getBlockData();
				newAge.setAge(0);
				AdvancementUtils.awardCriteria(player, ESAdvancement.THUMBS_UP, "replant");
			}, 0l);
			return new ReplantCropResult(result.getLevel(), dropItem, dropStack, result.getOverrideItems());
		}
		return null;
	}

}
