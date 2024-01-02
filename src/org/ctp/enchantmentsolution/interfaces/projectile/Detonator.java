package org.ctp.enchantmentsolution.interfaces.projectile;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.entity.DetonatorExplosionEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.projectile.HitEntityIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.projectile.ProjectileHasMetadataCondition;
import org.ctp.enchantmentsolution.interfaces.effects.projectile.CreateExplosionEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class Detonator extends CreateExplosionEffect {

	public Detonator() {
		super(RegisterEnchantments.DETONATOR, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, 0, "0.5 + 0.5 * %level%", false, true, true, true, true, new ProjectileHitCondition[] { new ProjectileHasMetadataCondition(false, "detonator"), new HitEntityIsTypeCondition(true, new MobData("CREEPER")) });
	}

	@Override
	public CreateExplosionResult run(LivingEntity shooter, LivingEntity damaged, Projectile projectile, ItemStack[] items, ProjectileHitEvent event) {
		CreateExplosionResult result = super.run(shooter, damaged, projectile, items, event);
		if (result.getLevel() == 0 || result.getExplosionSize() == 0) return null;
		Location loc = projectile.getLocation();
		DetonatorExplosionEvent detonator = new DetonatorExplosionEvent(shooter, result.getLevel(), loc, shooter, (float) result.getExplosionSize(), true, true, true);
		Bukkit.getPluginManager().callEvent(detonator);

		if (!detonator.isCancelled()) {
			boolean delay = detonator.willDelayExplosion();
			if (delay) Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
				boolean explode = loc.getWorld().createExplosion(detonator.getLoc(), detonator.getSize(), detonator.willSetFire(), detonator.willSetBlocks());
				if (event.getEntity() != null) event.getEntity().remove();
				if (explode && detonator.getEntity() instanceof Player) {
					Player player = (Player) detonator.getEntity();
					AdvancementUtils.awardCriteria(player, ESAdvancement.CARPET_BOMBS, "explosion", 1);
				}
			}, 0l);
			else {
				boolean explode = loc.getWorld().createExplosion(detonator.getLoc(), detonator.getSize(), detonator.willSetFire(), detonator.willSetBlocks());
				if (event.getEntity() != null) event.getEntity().remove();
				if (explode && detonator.getEntity() instanceof Player) {
					Player player = (Player) detonator.getEntity();
					AdvancementUtils.awardCriteria(player, ESAdvancement.CARPET_BOMBS, "explosion", 1);
				}
			}
			return result;
		}
		return null;
	}
}
