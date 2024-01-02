package org.ctp.enchantmentsolution.interfaces.effects.block;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.block.BlockIsAgeableCondition;
import org.ctp.enchantmentsolution.utils.abilityhelpers.Crop;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public abstract class ReplantCropEffect extends BlockDropItemEffect {

	public ReplantCropEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	BlockCondition[] conditions) {
		super(enchantment, type, location, priority, addCondition(conditions, new BlockIsAgeableCondition(false)));
	}

	@Override
	public ReplantCropResult run(Player player, ItemStack[] items, BlockData brokenData, BlockDropItemEvent event) {
		int level = getLevel(items);

		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);

		Ageable age = (Ageable) brokenData;
		Material mat = brokenData.getMaterial();
		if (!Crop.hasBlock(mat) || Crop.hasBlock(mat) && age.getAge() != age.getMaximumAge()) return new ReplantCropResult(0, null, null, null);
		Crop c = Crop.getCrop(mat);
		Item dropItem = null;
		ItemStack dropStack = null;
		List<ItemStack> oldItems = new ArrayList<ItemStack>();
		List<ItemStack> overrideItems = new ArrayList<ItemStack>();
		for(Item i: event.getItems()) {
			if (i.getItemStack().getType() == c.getSeed().getMaterial()) {
				dropItem = i;
				dropStack = i.getItemStack();
				ItemStack clone = dropStack.clone();
				if (clone.getAmount() <= 1) {
					clone.setType(Material.AIR);
					clone.setAmount(0);
				} else
					clone.setAmount(clone.getAmount() - 1);
				oldItems.add(clone);
			} else
				oldItems.add(i.getItemStack());
			overrideItems.add(i.getItemStack());
		}
		if (dropStack == null) for(ItemStack i: esPlayer.getInventoryItems())
			if (i != null && i.getType() == c.getSeed().getMaterial()) {
				dropStack = i;
				break;
			}

		return new ReplantCropResult(level, dropItem, dropStack, overrideItems);
	}

	public static BlockCondition[] addCondition(BlockCondition[] conditions, BlockCondition newCondition) {
		BlockCondition[] cond = new BlockCondition[conditions.length + 1];
		for(int i = 0; i < conditions.length; i++)
			cond[i] = conditions[i];
		cond[cond.length - 1] = newCondition;
		return cond;
	}

	public class ReplantCropResult extends EffectResult {

		private final Item dropItem;
		private final ItemStack dropStack;
		private final List<ItemStack> overrideItems;

		public ReplantCropResult(int level, Item dropItem, ItemStack dropStack, List<ItemStack> overrideItems) {
			super(level);
			this.dropItem = dropItem;
			this.dropStack = dropStack;
			this.overrideItems = overrideItems;
		}

		public Item getDropItem() {
			return dropItem;
		}

		public ItemStack getDropStack() {
			return dropStack;
		}

		public List<ItemStack> getOverrideItems() {
			return overrideItems;
		}
	}

}
