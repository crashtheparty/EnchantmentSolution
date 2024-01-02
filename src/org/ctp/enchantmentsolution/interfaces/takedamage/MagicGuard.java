package org.ctp.enchantmentsolution.interfaces.takedamage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.MagmaWalkerHotFloorEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.TakeDamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.takedamage.PreventDamageCauseEffect;

public class MagicGuard extends PreventDamageCauseEffect {

	public MagicGuard() {
		super(RegisterEnchantments.MAGIC_GUARD, EnchantmentMultipleType.ALL, EnchantmentItemLocation.DEFENDED, EventPriority.NORMAL, new DamageCause[] { DamageCause.MAGIC, DamageCause.POISON }, new TakeDamageCondition[0]);
	}
	
	@Override
	public PreventDamageCauseResult run(Entity damaged, ItemStack[] items, EntityDamageEvent event) {
		PreventDamageCauseResult result = super.run(damaged, items, event);
		if (result.getLevel() == 0) return null;
		for (DamageCause cause : result.getCauses())
			if (cause == event.getCause()) {
				MagmaWalkerHotFloorEvent hotFloor = new MagmaWalkerHotFloorEvent((LivingEntity) damaged, result.getLevel());
				Bukkit.getPluginManager().callEvent(hotFloor);
				if (!hotFloor.isCancelled()) {
					event.setCancelled(true);
					return new PreventDamageCauseResult(result.getLevel(), new DamageCause[] { cause });
				}
			}
		return null;
	}

}
