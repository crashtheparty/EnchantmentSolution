package org.ctp.enchantmentsolution.interfaces.effects.soulbound;

import java.util.Collection;
import java.util.HashMap;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.events.soul.SoulboundEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.SoulboundCondition;
import org.ctp.enchantmentsolution.interfaces.effects.SoulboundEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class StealSoulItemEffect extends SoulboundEffect {

	private double steal = 0;
	private String stealLevel = "0";
	private boolean useSteal, useStealLevel;

	public StealSoulItemEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double steal, String stealLevel, boolean useSteal, boolean useStealLevel, SoulboundCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.steal = steal;
		this.stealLevel = stealLevel;
		this.useSteal = useSteal;
		this.useStealLevel = useStealLevel;
	}

	@Override
	public StealSoulItemResult run(Entity killer, Player killed, ItemStack[] items, Collection<ItemStack> soulDrops, SoulboundEvent event) {
		int level = getLevel(items);

		double steal = 0;
		if (willUseSteal()) steal += getSteal();
		if (willUseStealLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getStealLevel(), codes);
			steal += MathUtils.eval(percent);
		}
		steal = Math.max(steal, 0);
		if (steal % 1 > 0) {
			double chance = steal % 1;
			if (chance > Math.random()) steal++;
		}
		return new StealSoulItemResult(level, (int) steal);
	}

	public double getSteal() {
		return steal;
	}

	public void setSteal(double steal) {
		this.steal = steal;
	}

	public String getStealLevel() {
		return stealLevel;
	}

	public void setStealLevel(String stealLevel) {
		this.stealLevel = stealLevel;
	}

	public boolean willUseSteal() {
		return useSteal;
	}

	public void setUseSteal(boolean useSteal) {
		this.useSteal = useSteal;
	}

	public boolean willUseStealLevel() {
		return useStealLevel;
	}

	public void setUseStealLevel(boolean useStealLevel) {
		this.useStealLevel = useStealLevel;
	}

	public class StealSoulItemResult extends EffectResult {

		private final int number;

		public StealSoulItemResult(int level, int number) {
			super(level);
			this.number = number;
		}

		public int getNumber() {
			return number;
		}
	}

}
