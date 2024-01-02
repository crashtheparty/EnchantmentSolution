package org.ctp.enchantmentsolution.interfaces.effects.block;

import java.util.HashMap;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class BlockChangeExpEffect extends BlockExpEffect {
	
	private double expAmount, expPercent, expChance;
	private String expAmountLevel, expPercentLevel;
	private boolean useExpAmount, useExpAmountLevel, useExpPercent, useExpPercentLevel, useExpChance;

	public BlockChangeExpEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double expAmount, String expAmountLevel, double expPercent, String expPercentLevel, boolean useExpAmount, boolean useExpAmountLevel,
	boolean useExpPercent, boolean useExpPercentLevel, double expChance, boolean useExpChance, BlockCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.expAmount = expAmount;
		this.expAmountLevel = expAmountLevel;
		this.expPercent = expPercent;
		this.expPercentLevel = expPercentLevel;
		this.useExpAmount = useExpAmount;
		this.useExpAmountLevel = useExpAmountLevel;
		this.useExpPercent = useExpPercent;
		this.useExpPercentLevel = useExpPercentLevel;
		this.setExpChance(expChance);
		this.setUseExpChance(useExpChance);
	}

	@Override
	public BlockChangeExpResult run(Player player, ItemStack[] items, BlockData brokenData, BlockExpEvent event) {
		int level = getLevel(items);
		
		double exp = event.getExpToDrop();
		if (willUseExpPercent()) exp *= getExpPercent();
		if (willUseExpAmount()) exp += getExpAmount();
		if (willUseExpPercentLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getExpPercentLevel(), codes);
			exp *= MathUtils.eval(percent);
		}
		if (willUseExpAmountLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getExpAmountLevel(), codes);
			exp += MathUtils.eval(percent);
		}
		exp = Math.max(exp, 0);
		
		int j = (int) exp;
		if (exp > 0 && useExpChance) for(int i = 0; i < j * level; i++) {
			double chance = expChance;
			double random = Math.random();
			if (chance > random) exp++;
		}
		
		return new BlockChangeExpResult(level, (int) exp);
	}

	public double getExpAmount() {
		return expAmount;
	}

	public void setExpAmount(double expAmount) {
		this.expAmount = expAmount;
	}

	public String getExpAmountLevel() {
		return expAmountLevel;
	}

	public void setExpAmountLevel(String expAmountLevel) {
		this.expAmountLevel = expAmountLevel;
	}

	public boolean willUseExpAmount() {
		return useExpAmount;
	}

	public void setUseExpAmount(boolean useExpAmount) {
		this.useExpAmount = useExpAmount;
	}

	public boolean willUseExpAmountLevel() {
		return useExpAmountLevel;
	}

	public void setUseExpAmountLevel(boolean useExpAmountLevel) {
		this.useExpAmountLevel = useExpAmountLevel;
	}

	public double getExpPercent() {
		return expPercent;
	}

	public void setExpPercent(double expPercent) {
		this.expPercent = expPercent;
	}

	public String getExpPercentLevel() {
		return expPercentLevel;
	}

	public void setExpPercentLevel(String expPercentLevel) {
		this.expPercentLevel = expPercentLevel;
	}

	public boolean willUseExpPercent() {
		return useExpPercent;
	}

	public void setUseExpPercent(boolean useExpPercent) {
		this.useExpPercent = useExpPercent;
	}

	public boolean willUseExpPercentLevel() {
		return useExpPercentLevel;
	}

	public void setUseExpPercentLevel(boolean useExpPercentLevel) {
		this.useExpPercentLevel = useExpPercentLevel;
	}

	public double getExpChance() {
		return expChance;
	}

	public void setExpChance(double expChance) {
		this.expChance = expChance;
	}

	public boolean willUseExpChance() {
		return useExpChance;
	}

	public void setUseExpChance(boolean useExpChance) {
		this.useExpChance = useExpChance;
	}

	public class BlockChangeExpResult extends EffectResult {

		private final int exp;

		public BlockChangeExpResult(int level, int exp) {
			super(level);
			this.exp = exp;
		}

		public int getExp() {
			return exp;
		}
	}

}
