package org.ctp.enchantmentsolution.interfaces.damaged;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.modify.player.PushbackEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagedBlockingCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagedIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.VectorEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class Pushback extends VectorEffect {

	public Pushback() {
		super(RegisterEnchantments.PUSHBACK, EnchantmentMultipleType.STACK, EnchantmentItemLocation.DEFENDED, EventPriority.HIGHEST, new Vector(0, 0, 0), "-%x% / %d2% * %level% * 0.5", "%y% / %d2% * %level% * 0.5", "-%z% / %d2% * %level% * 0.5", "%entity_x% / 2.0 - %x%", "%entity_y% / 2.0", "%entity_y%", "%entity_z% / 2.0 - %z%", new DamageCondition[] { new DamagedIsTypeCondition(false, new MobData("PLAYER")), new DamagedBlockingCondition(false) });
	}

	@Override
	public VectorResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		setEntity(damager);
		setEntityVector(damager.getVelocity());

		VectorResult result = super.run(damager, damaged, items, event);
		int level = result.getLevel();
		if (level == 0) return null;

		PushbackEvent pushback = new PushbackEvent((Player) damaged, level, result.getOriginal(), result.getEnding(), (LivingEntity) damager);
		Bukkit.getPluginManager().callEvent(pushback);

		if (!pushback.isCancelled()) {
			AdvancementUtils.awardCriteria((Player) damaged, ESAdvancement.KNOCKBACK_REVERSED, "knockback");
			damager.setVelocity(pushback.getNewVector());
			return new VectorResult(pushback.getEnchantment().getLevel(), pushback.getVector(), pushback.getNewVector());
		}
		return null;
	}
}