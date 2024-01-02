package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.PacifiedEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagerIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagerTamedAnimalCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.DamagerEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class Pacified extends DamagerEffect {

	public Pacified() {
		super(RegisterEnchantments.PACIFIED, EnchantmentMultipleType.ALL, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 0, 1, "0", "1 - (.15 * %level%)", false, false, false, true, new DamageCondition[] { new DamagerTamedAnimalCondition(false), new DamagerIsTypeCondition(false, new MobData("PLAYER")) });
	}

	@Override
	public DamagerResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		DamagerResult result = super.run(damager, damaged, items, event);
		double damage = result.getDamage();
		int level = result.getLevel();
		if (level == 0) return null;
		Player player = (Player) damager;
		LivingEntity living = (LivingEntity) damaged;

		PacifiedEvent pacified = new PacifiedEvent(living, player, level, event.getDamage(), damage);
		Bukkit.getPluginManager().callEvent(pacified);

		if (!pacified.isCancelled()) {
			event.setDamage(pacified.getNewDamage());
			if (damage > living.getHealth() && living.getHealth() > pacified.getNewDamage()) AdvancementUtils.awardCriteria(player, ESAdvancement.SAVING_GRACE, "animal");
			return new DamagerResult(pacified.getEnchantment().getLevel(), pacified.getDamage());
		}
		return null;
	}

}
