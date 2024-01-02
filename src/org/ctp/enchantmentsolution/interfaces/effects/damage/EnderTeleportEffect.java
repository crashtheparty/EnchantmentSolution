package org.ctp.enchantmentsolution.interfaces.effects.damage;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityDamageEffect;

public abstract class EnderTeleportEffect extends EntityDamageEffect {

	private boolean useParticleTo, useParticleFrom, useSoundTo, useSoundFrom;
	private Location to, from;

	public EnderTeleportEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	boolean useParticleTo, boolean useParticleFrom, boolean useSoundTo, boolean useSoundFrom, DamageCondition... conditions) {
		super(enchantment, type, location, priority, conditions);
		setUseParticleTo(useParticleTo);
		setUseParticleFrom(useParticleFrom);
		setUseSoundTo(useSoundTo);
		setUseSoundFrom(useSoundFrom);
	}

	@Override
	public EnderTeleportResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		int level = getLevel(items);

		Random random = new Random();

		double x1 = from.getX();
		double y1 = from.getY();
		double z1 = from.getZ();

		// vanilla Endermen code
		// double x2 = x1 + (random.nextDouble() - 0.5D) * 64.0D;
		// double y2 = y1 + (random.nextInt(64) - 32);
		// double z2 = z1 + (random.nextDouble() - 0.5D) * 64.0D;
		double x2 = x1 + (random.nextDouble() - 0.5D) * 8.0D * level;
		double y2 = y1 + 2 * level; // make it the highest local level so it can check all locations in the valid
									// set
		double z2 = z1 + (random.nextDouble() - 0.5D) * 8.0D * level;

		double y3 = y2;

		World world = from.getWorld();
		Location loc = new Location(world, x2, y2, z2);
		boolean flag1 = false;

		if (world.isChunkLoaded((int) (x1 / 16), (int) (z1 / 16))) {
			while (!flag1 && loc.getY() > world.getMinHeight() && y1 - 2 * level <= y3 && y1 + 2 * level >= y3) {
				Location clone = loc.clone();
				Location clone1 = clone.clone().add(0, 1, 0);
				Location loc1 = clone.clone().add(0, -1, 0);
				Material type = loc1.getBlock().getType();

				if (type.isSolid() && !clone.getBlock().getType().isSolid() && !clone1.getBlock().getType().isSolid()) flag1 = true;
				else {
					--y3;
					loc = loc1;
				}
			}

			if (flag1) {
				loc = new Location(world, x2, y3, z2);
				Location loc2 = loc.add(0, 1, 0);
				if (loc2.getBlock().isLiquid()) loc = new Location(world, x1, y1, z1);
			}
		}

		to = loc;
		return new EnderTeleportResult(level, flag1, from, to);
	}

	public Location getTo() {
		return to;
	}

	public void setTo(Location to) {
		this.to = to;
	}

	public Location getFrom() {
		return from;
	}

	public void setFrom(Location from) {
		this.from = from;
	}

	public boolean willUseParticleTo() {
		return useParticleTo;
	}

	public void setUseParticleTo(boolean useParticleTo) {
		this.useParticleTo = useParticleTo;
	}

	public boolean willUseParticleFrom() {
		return useParticleFrom;
	}

	public void setUseParticleFrom(boolean useParticleFrom) {
		this.useParticleFrom = useParticleFrom;
	}

	public boolean willUseSoundTo() {
		return useSoundTo;
	}

	public void setUseSoundTo(boolean useSoundTo) {
		this.useSoundTo = useSoundTo;
	}

	public boolean willUseSoundFrom() {
		return useSoundFrom;
	}

	public void setUseSoundFrom(boolean useSoundFrom) {
		this.useSoundFrom = useSoundFrom;
	}

	public class EnderTeleportResult extends EffectResult {

		private final Location from, to;
		private final boolean safeLocation;

		public EnderTeleportResult(int level, boolean safeLocation, Location from, Location to) {
			super(level);
			this.from = from;
			this.to = to;
			this.safeLocation = safeLocation;
		}

		public Location getFrom() {
			return from;
		}

		public Location getTo() {
			return to;
		}

		public boolean teleport() {
			return !to.equals(from);
		}

		public boolean isSafeLocation() {
			return safeLocation;
		}
	}

}
