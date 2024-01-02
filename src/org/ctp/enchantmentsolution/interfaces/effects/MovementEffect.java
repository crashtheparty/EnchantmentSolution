package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.events.player.PlayerChangeCoordsEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.MovementCondition;

public abstract class MovementEffect extends EnchantmentEffect {

	private final MovementCondition[] conditions;

	public MovementEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	MovementCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Player player, PlayerChangeCoordsEvent event) {
		for(MovementCondition condition: conditions)
			if (!condition.metCondition(player, event)) return false;
		return true;
	}

	public abstract EffectResult run(Player player, ItemStack[] items, PlayerChangeCoordsEvent event);

	public static MovementCondition[] addCondition(MovementCondition[] conditions, MovementCondition newCondition) {
		MovementCondition[] cond = new MovementCondition[conditions.length + 1];
		for(int i = 0; i < conditions.length; i++)
			cond[i] = conditions[i];
		cond[cond.length - 1] = newCondition;
		return cond;
	}

}
