package org.ctp.enchantmentsolution.interfaces.effects.block;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ParticleEffect;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;

public class LagBlockBreakEffect extends BlockBreakEffect {

	private final int effectMinimum, effectMultiple, numMinimum, numMultiple;
	private final Particle[] whitelist, blacklist;
	private Location loc;

	public LagBlockBreakEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority, int effectMinimum,
	int effectMultiple, int numMinimum, int numMultiple, Particle[] whitelist, Particle[] blacklist, BlockCondition... conditions) {
		super(enchantment, type, location, priority, conditions);
		this.effectMinimum = effectMinimum;
		this.effectMultiple = effectMultiple;
		this.numMinimum = numMinimum;
		this.numMultiple = numMultiple;
		this.whitelist = whitelist;
		this.blacklist = blacklist;
	}

	@Override
	public LagResult run(Player player, ItemStack[] items, BlockData brokenData, BlockBreakEvent event) {
		int level = getLevel(items);

		ParticleEffect[] effects = AbilityUtils.createEffects(effectMultiple, effectMinimum, numMultiple, numMinimum, blacklist, whitelist);
		return new LagResult(level, effects, loc);
	}

	public int getEffectMultiple() {
		return effectMultiple;
	}

	public int getEffectMinimum() {
		return effectMinimum;
	}

	public int getNumMultiple() {
		return numMultiple;
	}

	public int getNumMinimum() {
		return numMinimum;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public class LagResult extends EffectResult {

		private final ParticleEffect[] particles;
		private final Location location;

		public LagResult(int level, ParticleEffect[] particles, Location location) {
			super(level);
			this.particles = particles;
			this.location = location;
		}

		public ParticleEffect[] getParticles() {
			return particles;
		}

		public Location getLocation() {
			return location;
		}
	}

}
