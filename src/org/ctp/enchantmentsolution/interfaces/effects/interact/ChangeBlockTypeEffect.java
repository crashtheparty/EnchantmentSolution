package org.ctp.enchantmentsolution.interfaces.effects.interact;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.interact.ActionIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.PlayerInteractEffect;
import org.ctp.enchantmentsolution.utils.items.EnchantmentDrops;
import org.ctp.enchantmentsolution.utils.items.Interactions;

public abstract class ChangeBlockTypeEffect extends PlayerInteractEffect {

	private final EnchantmentDrops drops;

	public ChangeBlockTypeEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	EnchantmentDrops drops, InteractCondition[] conditions) {
		super(enchantment, type, location, priority, addCondition(conditions, new ActionIsTypeCondition(false, Action.RIGHT_CLICK_BLOCK)));
		this.drops = drops;
	}

	@Override
	public ChangeBlockTypeResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		int level = getLevel(items);

		Interactions interactions = null;
		for(Interactions i: drops.getDrops())
			for(MatData data: i.getInteract())
				if (data.getMaterial() == event.getClickedBlock().getType()) {
					interactions = i;
					break;
				}

		return new ChangeBlockTypeResult(level, interactions);
	}

	public EnchantmentDrops getDrops() {
		return drops;
	}

	public class ChangeBlockTypeResult extends EffectResult {

		private final Interactions interactions;

		public ChangeBlockTypeResult(int level, Interactions interactions) {
			super(level);
			this.interactions = interactions;
		}

		public Interactions getInteractions() {
			return interactions;
		}
	}

}
