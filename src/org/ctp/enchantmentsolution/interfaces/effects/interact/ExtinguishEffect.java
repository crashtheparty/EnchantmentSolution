package org.ctp.enchantmentsolution.interfaces.effects.interact;

import org.bukkit.Sound;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.interact.ActionIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.interact.BlockIsLitCondition;
import org.ctp.enchantmentsolution.interfaces.effects.PlayerInteractEffect;

public class ExtinguishEffect extends PlayerInteractEffect {

	public ExtinguishEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	InteractCondition[] conditions) {
		super(enchantment, type, location, priority, addCondition(addCondition(conditions, new BlockIsLitCondition(false)), new ActionIsTypeCondition(false, Action.RIGHT_CLICK_BLOCK)));
	}

	@Override
	public ExtinguishResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		int level = getLevel(items);
		Sound sound = Sound.BLOCK_WATER_AMBIENT;
		return new ExtinguishResult(level, sound);
	}

	public class ExtinguishResult extends EffectResult {

		private final Sound sound;

		public ExtinguishResult(int level, Sound sound) {
			super(level);
			this.sound = sound;
		}

		public Sound getSound() {
			return sound;
		}
	}
}
