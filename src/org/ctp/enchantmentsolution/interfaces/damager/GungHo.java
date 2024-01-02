package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.GungHoEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.DamagerEffect;

public class GungHo extends DamagerEffect {

	public GungHo() {
		super(RegisterEnchantments.GUNG_HO, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.WEARING, EventPriority.HIGHEST, 0, 3, "0", "1", false, true, false, false, new DamageCondition[0]);
	}

	@Override
	public DamagerResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		DamagerResult result = super.run(damager, damaged, items, event);
		if (result.getLevel() == 0) return null;
		double damage = result.getDamage();
		GungHoEvent gungHo = new GungHoEvent((LivingEntity) damaged, (Player) damager, event.getDamage(), damage);
		Bukkit.getPluginManager().callEvent(gungHo);
		if (!gungHo.isCancelled()) {
			event.setDamage(gungHo.getNewDamage());
			return new DamagerResult(result.getLevel(), gungHo.getNewDamage());
		}
		return null;
	}
}