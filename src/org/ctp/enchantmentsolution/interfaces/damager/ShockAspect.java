package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.ShockAspectEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.LightningChanceEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class ShockAspect extends LightningChanceEffect {

	public ShockAspect() {
		super(RegisterEnchantments.SHOCK_ASPECT, EnchantmentMultipleType.STACK, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 0, "0.05 + 0.1 * %level%", false, true, new DamageCondition[0]);
	}

	@Override
	public LightningResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		LightningResult result = super.run(damager, damaged, items, event);

		int level = result.getLevel();
		if (level == 0) return null;
		double chance = result.getChance();
		double random = Math.random();
		Location location = result.getLocation();

		ShockAspectEvent shockAspect = new ShockAspectEvent((LivingEntity) damaged, level, chance, location);
		Bukkit.getPluginManager().callEvent(shockAspect);

		if (!shockAspect.isCancelled() && shockAspect.getChance() > random) {
			location.getWorld().strikeLightning(shockAspect.getLocation());
			if (damager instanceof Player && event.getEntityType() == EntityType.CREEPER) AdvancementUtils.awardCriteria((Player) damager, ESAdvancement.SUPER_CHARGED, "lightning");
			return new LightningResult(shockAspect.getEnchantment().getLevel(), shockAspect.getChance(), shockAspect.getLocation());
		}
		return null;
	}

}
