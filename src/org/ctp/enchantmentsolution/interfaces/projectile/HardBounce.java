package org.ctp.enchantmentsolution.interfaces.projectile;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.modify.player.HardBounceEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.projectile.IsBlockingCondition;
import org.ctp.enchantmentsolution.interfaces.effects.projectile.ReboundProjectileEffect;

public class HardBounce extends ReboundProjectileEffect {

	public HardBounce() {
		super(RegisterEnchantments.HARD_BOUNCE, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.DEFENDED, EventPriority.NORMAL, 0, "2 + 2 * %level%", false, true, false, new ProjectileHitCondition[] { new IsBlockingCondition(false) });
	}

	@Override
	public ReboundProjectileResult run(LivingEntity damaged, LivingEntity shooter, Projectile projectile, ItemStack[] items, ProjectileHitEvent event) {
		ReboundProjectileResult result = super.run(damaged, shooter, projectile, items, event);

		int level = result.getLevel();
		if (level == 0) return null;
		Player player = (Player) damaged;

		HardBounceEvent hardBounce = new HardBounceEvent(player, level, 2 + 2 * level);
		Bukkit.getPluginManager().callEvent(hardBounce);

		if (!hardBounce.isCancelled()) {
			projectile.setMetadata("deflection", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
			Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), (Runnable) () -> {
				Vector v = projectile.getVelocity().clone();
				if (v.getY() < 0) v.setY(-v.getY());
				else if (v.getY() == 0) v.setY(0.1);
				double speed = hardBounce.getSpeed();
				if (willFlipVelocity()) speed *= -1;
				v.multiply(speed);
				projectile.setVelocity(v);
			}, 1l);
			return result;
		}

		return null;
	}

}
