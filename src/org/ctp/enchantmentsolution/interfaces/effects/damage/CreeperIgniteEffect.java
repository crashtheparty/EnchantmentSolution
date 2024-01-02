package org.ctp.enchantmentsolution.interfaces.effects.damage;

import java.util.HashMap;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityDamageEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class CreeperIgniteEffect extends EntityDamageEffect {

	private final int minTicks = 1;
	private int ticks = 0;
	private String ticksLevel = "0";
	private boolean useTicks, useTicksLevel, ignite;

	public CreeperIgniteEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority, boolean ignite,
	int ticks, String ticksLevel, boolean useTicks, boolean useTicksLevel, DamageCondition... conditions) {
		super(enchantment, type, location, priority, conditions);
		setIgnite(ignite);
		setTicks(ticks);
		setTicksLevel(ticksLevel);
		setUseTicks(useTicks);
		setUseTicksLevel(useTicksLevel);
	}

	@Override
	public CreeperResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		int level = getLevel(items);

		int ticks = ((Creeper) damaged).getFuseTicks();

		if (willUseTicksLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getTicksLevel(), codes);
			ticks -= MathUtils.eval(percent);
		}
		if (willUseTicks()) ticks -= getTicks();
		ticks = Math.max(ticks, 1);

		return new CreeperResult(level, ignite, ticks);
	}

	public int getMinTicks() {
		return minTicks;
	}

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

	public String getTicksLevel() {
		return ticksLevel;
	}

	public void setTicksLevel(String ticksLevel) {
		this.ticksLevel = ticksLevel;
	}

	public boolean willUseTicks() {
		return useTicks;
	}

	public void setUseTicks(boolean useTicks) {
		this.useTicks = useTicks;
	}

	public boolean willUseTicksLevel() {
		return useTicksLevel;
	}

	public void setUseTicksLevel(boolean useTicksLevel) {
		this.useTicksLevel = useTicksLevel;
	}

	public boolean willIgnite() {
		return ignite;
	}

	public void setIgnite(boolean ignite) {
		this.ignite = ignite;
	}

	public class CreeperResult extends EffectResult {

		private final boolean ignite;
		private final int ticks;

		public CreeperResult(int level, boolean ignite, int ticks) {
			super(level);
			this.ignite = ignite;
			this.ticks = ticks;
		}

		public boolean isIgnite() {
			return ignite;
		}

		public int getTicks() {
			return ticks;
		}
	}

}
