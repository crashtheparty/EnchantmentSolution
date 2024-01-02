package org.ctp.enchantmentsolution.interfaces.effects.block;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.utils.items.EnchantmentDrops;
import org.ctp.enchantmentsolution.utils.items.Interactions;

public abstract class ChangeBlockDropEffect extends BlockDropItemEffect {

	private final EnchantmentDrops drops;

	public ChangeBlockDropEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	EnchantmentDrops drops, BlockCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.drops = drops;
	}

	@Override
	public ChangeBlockDropResult run(Player player, ItemStack[] items, BlockData brokenData, BlockDropItemEvent event) {
		int level = getLevel(items);

		Interactions interactions = null;
		for(Interactions i: drops.getDrops())
			for(MatData data: i.getInteract())
				if (data.getMaterial() == brokenData.getMaterial()) {
					interactions = i;
					break;
				}
		List<ItemStack> oldDrops = new ArrayList<ItemStack>();
		for(Item i: event.getItems())
			oldDrops.add(i.getItemStack());

		return new ChangeBlockDropResult(level, interactions, oldDrops);
	}

	public class ChangeBlockDropResult extends EffectResult {

		private final Interactions interactions;
		private final List<ItemStack> oldDrops;

		public ChangeBlockDropResult(int level, Interactions interactions, List<ItemStack> oldDrops) {
			super(level);
			this.interactions = interactions;
			this.oldDrops = oldDrops;
		}

		public Interactions getInteractions() {
			return interactions;
		}

		public List<ItemStack> getOldDrops() {
			return oldDrops;
		}
	}

}
