package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.LifeDrainEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.HealthRestoreEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class LifeDrain extends HealthRestoreEffect {

	public LifeDrain() {
		super(RegisterEnchantments.LIFE_DRAIN, EnchantmentMultipleType.STACK, EnchantmentItemLocation.ATTACKED, EventPriority.MONITOR, 0, 0, "0", "0.10 * %level%", false, false, false, true, new DamageCondition[0]);
	}

	@Override
	public HealthResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		HealthResult result = super.run(damager, damaged, items, event);
		int level = result.getLevel();
		if (level == 0) return null;
		double restore = result.getRestore();

		LivingEntity living = (LivingEntity) damager;

		LifeDrainEvent lifeDrain = new LifeDrainEvent((LivingEntity) damaged, level, living, event.getDamage(), restore);
		Bukkit.getPluginManager().callEvent(lifeDrain);

		if (!lifeDrain.isCancelled()) {
			event.setDamage(lifeDrain.getNewDamage());
			restore = lifeDrain.getHealthBack();
			if (living instanceof Player && restore > 0) {
				Player player = (Player) living;
				if (player.getHealth() < player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) AdvancementUtils.awardCriteria(player, ESAdvancement.REPLENISHED, "life");
			}
			if (restore + living.getHealth() > living.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) living.setHealth(living.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			else
				living.setHealth(restore + living.getHealth());
			return new HealthResult(level, restore);
		}
		return null;
	}

}
