package org.ctp.enchantmentsolution.interfaces.effects.interact;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.interact.ActionIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.PlayerInteractEffect;
import org.ctp.enchantmentsolution.utils.abilityhelpers.LassoMob;

public abstract class RespawnEntityEffect extends PlayerInteractEffect {

	public RespawnEntityEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	InteractCondition[] conditions) {
		super(enchantment, type, location, priority, addCondition(conditions, new ActionIsTypeCondition(false, Action.RIGHT_CLICK_BLOCK)));
	}

	@Override
	public RespawnEntityResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		int level = getLevel(items);
		
		LassoMob mob = EnchantmentSolution.getFirstLassoMob(getEnchantment(), items);
		ItemStack item = EnchantmentSolution.getFirstLassoItem(getEnchantment(), items);
		return new RespawnEntityResult(level, mob, item);
	}

	public class RespawnEntityResult extends EffectResult {

		private final LassoMob mob;
		private final ItemStack item;

		public RespawnEntityResult(int level, LassoMob mob, ItemStack item) {
			super(level);
			this.mob = mob;
			this.item = item;
		}

		public LassoMob getMob() {
			return mob;
		}

		public ItemStack getItem() {
			return item;
		}

	}

}
