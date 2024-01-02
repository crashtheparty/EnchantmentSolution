package org.ctp.enchantmentsolution.interfaces.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.modify.player.LagPlayerEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.interfaces.effects.block.LagBlockBreakEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ParticleEffect;

public class LagCurseBlockBreak extends LagBlockBreakEffect {

	public LagCurseBlockBreak() {
		super(RegisterEnchantments.CURSE_OF_LAG, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, 2, 5, 11, 400, new Particle[0], new Particle[0], new BlockCondition[0]);
	}

	@Override
	public LagResult run(Player player, ItemStack[] items, BlockData brokenData, BlockBreakEvent event) {
		setLoc(event.getBlock().getLocation());
		LagResult result = super.run(player, items, brokenData, event);
		if (result.getLevel() == 0) return null;
		Location location = result.getLocation();
		ParticleEffect[] effects = result.getParticles();

		LagPlayerEvent lag = new LagPlayerEvent(player, location, effects);
		Bukkit.getPluginManager().callEvent(lag);
		if (!lag.isCancelled() && lag.getEffects().length > 0) {
			Location loc = lag.getLocation();
			for(ParticleEffect effect: lag.getEffects())
				loc.getWorld().spawnParticle(effect.getParticle(), loc, effect.getNum(), effect.getVarX(), effect.getVarY(), effect.getVarZ());
			if (lag.getSound() != null) loc.getWorld().playSound(loc, lag.getSound(), lag.getVolume(), lag.getPitch());
			AdvancementUtils.awardCriteria(player, ESAdvancement.LAAAGGGGGG, "lag");
			return result;
		}

		return null;
	}

}
