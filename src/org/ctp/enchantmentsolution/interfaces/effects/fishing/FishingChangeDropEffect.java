package org.ctp.enchantmentsolution.interfaces.effects.fishing;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.FishingCondition;
import org.ctp.enchantmentsolution.interfaces.effects.FishingEffect;
import org.ctp.enchantmentsolution.utils.items.EnchantmentDrops;
import org.ctp.enchantmentsolution.utils.items.Interactions;

public class FishingChangeDropEffect extends FishingEffect {

	private final EnchantmentDrops drops;
	
	public FishingChangeDropEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	EnchantmentDrops drops, FishingCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.drops = drops;
	}

	@Override
	public FishingChangeDropResult run(Player player, ItemStack[] items, PlayerFishEvent event) {
		int level = getLevel(items);
		Item item = (Item) event.getCaught();
		ItemStack drop = item.getItemStack();
		if (drop == null) return new FishingChangeDropResult(level, null, null);

		Interactions interactions = null;
		for(Interactions i: drops.getDrops())
			for(MatData data: i.getInteract())
				if (data.getMaterial() == drop.getType()) {
					interactions = i;
					break;
				}

		return new FishingChangeDropResult(level, interactions, drop);
	}

	public class FishingChangeDropResult extends EffectResult {

		private final Interactions interactions;
		private final ItemStack oldDrop;

		public FishingChangeDropResult(int level, Interactions interactions, ItemStack oldDrop) {
			super(level);
			this.interactions = interactions;
			this.oldDrop = oldDrop;
		}

		public Interactions getInteractions() {
			return interactions;
		}

		public ItemStack getOldDrop() {
			return oldDrop;
		}
	}

}
