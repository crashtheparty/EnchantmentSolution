package org.ctp.enchantmentsolution.interfaces.movement;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.modify.player.IcarusLaunchEvent;
import org.ctp.enchantmentsolution.events.player.PlayerChangeCoordsEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.MovementCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.movement.IsGlidingCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.movement.LookingAtAngleCondition;
import org.ctp.enchantmentsolution.interfaces.effects.movement.ChangePlayerVelocityEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class Icarus extends ChangePlayerVelocityEffect {

	public Icarus() {
		super(RegisterEnchantments.ICARUS, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.EQUIPPED, EventPriority.HIGHEST, "0", "log((2 * %level% + 8) / 5) + 1.5", "0", "(log((2 * %level% + 8) / 5) + 1.5) / 2", "1", "(log((2 * %level% + 8) / 5) + 1.5) / 2", true, true, new MovementCondition[] { new IsGlidingCondition(false), new LookingAtAngleCondition(-10, true, true) });
	}

	@Override
	public ChangePlayerVelocityResult run(Player player, ItemStack[] items, PlayerChangeCoordsEvent event) {
		ChangePlayerVelocityResult result = super.run(player, items, event);

		int level = result.getLevel();
		if (level == 0) return null;

		double addX = result.getAddX();
		double addY = result.getAddY();
		double addZ = result.getAddZ();

		double multX = result.getMultX();
		double multY = result.getMultY();
		double multZ = result.getMultZ();

		ItemStack item = items[0];

		int num_breaks = DamageUtils.damageItem(player, item, level * 5, 1, false).getDamageAmount();
		if (DamageUtils.getDamage(item) + num_breaks >= DamageUtils.getMaxDamage(item)) {
			AdvancementUtils.awardCriteria(player, ESAdvancement.TOO_CLOSE, "failure");
			player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 5, 2, 2, 2);
			return null;
		}

		IcarusLaunchEvent icarus = new IcarusLaunchEvent(player, level, addX, addY, addZ, multX, multY, multZ);
		Bukkit.getPluginManager().callEvent(icarus);

		if (!icarus.isCancelled()) {
			Vector pV = player.getVelocity().clone();
			if (isAddVelocity()) pV.add(new Vector(addX, addY, addZ));
			if (isMultVelocity()) pV.multiply(new Vector(multX, multY, multZ));
			player.setVelocity(pV);
			player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 250, 2, 2, 2);
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);

			ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
			esPlayer.setCooldown(getEnchantment(), icarus.getCooldownTicks());
			return new ChangePlayerVelocityResult(level, addX, addY, addZ, multX, multY, multZ);
		}

		return null;
	}

}
