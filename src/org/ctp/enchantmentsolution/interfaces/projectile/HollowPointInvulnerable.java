package org.ctp.enchantmentsolution.interfaces.projectile;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.crashapi.nms.DamageNMS;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.HollowPointDamageEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.projectile.HitEntityExistsCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.projectile.ProjectileIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.projectile.ShooterIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.projectile.DamageInvulnerableEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class HollowPointInvulnerable extends DamageInvulnerableEffect {

	public HollowPointInvulnerable() {
		super(RegisterEnchantments.HOLLOW_POINT, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, new ProjectileHitCondition[] { new HitEntityExistsCondition(false), new ShooterIsTypeCondition(false, new MobData("PLAYER")), new ProjectileIsTypeCondition(false, new MobData("ARROW")) });
	}

	@Override
	public DamageInvulnerableResult run(LivingEntity shooter, LivingEntity damaged, Projectile projectile, ItemStack[] items, ProjectileHitEvent event) {
		DamageInvulnerableResult result = super.run(shooter, damaged, projectile, items, event);

		if (result.getLevel() == 0 || !result.canDamage()) return null;
		Player player = (Player) shooter;
		Arrow arrow = (Arrow) projectile;
		double damage = DamageNMS.getArrowDamage(shooter, arrow);
		HollowPointDamageEvent hollowPoint = new HollowPointDamageEvent(event.getEntity(), event.getHitEntity(), DamageCause.PROJECTILE, damage);
		Bukkit.getPluginManager().callEvent(hollowPoint);
		if (!hollowPoint.isCancelled()) {
			DamageNMS.damageEntity((LivingEntity) hollowPoint.getEntity(), player, "arrow", (float) hollowPoint.getDamage());
			event.getEntity().remove();
			AdvancementUtils.awardCriteria(player, ESAdvancement.PENETRATION, "arrow");
		}
		return null;
	}

}
