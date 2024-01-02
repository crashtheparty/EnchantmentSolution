package org.ctp.enchantmentsolution.interfaces.effects.projectile;

import org.bukkit.attribute.Attribute;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;
import org.ctp.enchantmentsolution.interfaces.effects.ProjectileHitDamagerEffect;

public abstract class DamageInvulnerableEffect extends ProjectileHitDamagerEffect {

	public DamageInvulnerableEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	ProjectileHitCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
	}

	@Override
	public DamageInvulnerableResult run(LivingEntity shooter, LivingEntity damaged, Projectile projectile, ItemStack[] items, ProjectileHitEvent event) {
		return new DamageInvulnerableResult(getLevel(items), event.getHitEntity().getType() == EntityType.ENDERMAN || event.getHitEntity().getType() == EntityType.WITHER && ((Wither) event.getHitEntity()).getHealth() <= ((Wither) event.getHitEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2);
	}

	public class DamageInvulnerableResult extends EffectResult {

		private final boolean damage;

		public DamageInvulnerableResult(int level, boolean damage) {
			super(level);
			this.damage = damage;
		}

		public boolean canDamage() {
			return damage;
		}
	}

}
