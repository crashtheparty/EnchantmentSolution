package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;

public abstract class PlayerInteractEffect extends EnchantmentEffect {

	private final InteractCondition[] conditions;

	public PlayerInteractEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	InteractCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Player player, PlayerInteractEvent event) {
		for(InteractCondition condition: conditions)
			if (!condition.metCondition(player, event)) return false;
		return true;
	}

	public abstract EffectResult run(Player player, ItemStack[] items, PlayerInteractEvent event);

	public static InteractCondition[] addCondition(InteractCondition[] conditions, InteractCondition newCondition) {
		InteractCondition[] cond = new InteractCondition[conditions.length + 1];
		for(int i = 0; i < conditions.length; i++)
			cond[i] = conditions[i];
		cond[cond.length - 1] = newCondition;
		return cond;
	}

}
