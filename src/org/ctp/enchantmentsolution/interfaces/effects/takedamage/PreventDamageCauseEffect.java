package org.ctp.enchantmentsolution.interfaces.effects.takedamage;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.TakeDamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityTakeDamageEffect;

public class PreventDamageCauseEffect extends EntityTakeDamageEffect {

	private DamageCause[] causes;

	public PreventDamageCauseEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	DamageCause[] causes, TakeDamageCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.causes = causes;
	}

	@Override
	public PreventDamageCauseResult run(Entity damaged, ItemStack[] items, EntityDamageEvent event) {
		int level = getLevel(items);
		
		return new PreventDamageCauseResult(level, causes);
	}

	public DamageCause[] getCauses() {
		return causes;
	}

	public void setCauses(DamageCause[] causes) {
		this.causes = causes;
	}

	public class PreventDamageCauseResult extends EffectResult {

		private final DamageCause[] causes;
		
		public PreventDamageCauseResult(int level, DamageCause[] causes) {
			super(level);
			this.causes = causes;
		}

		public DamageCause[] getCauses() {
			return causes;
		}
	}

}
