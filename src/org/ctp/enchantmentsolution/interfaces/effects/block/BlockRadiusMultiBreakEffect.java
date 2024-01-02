package org.ctp.enchantmentsolution.interfaces.effects.block;

import java.util.HashMap;

import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class BlockRadiusMultiBreakEffect extends BlockMultiBreakEffect {
	private final String widthChange, heightChange, depthChange;
	private final boolean changeWidth, changeHeight, changeDepth, rounded, depthCloser;

	public BlockRadiusMultiBreakEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	String widthChange, String heightChange, String depthChange, boolean changeWidth, boolean changeHeight, boolean changeDepth, boolean rounded,
	boolean depthCloser, BlockCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.widthChange = widthChange;
		this.heightChange = heightChange;
		this.depthChange = depthChange;
		this.changeWidth = changeWidth;
		this.changeHeight = changeHeight;
		this.changeDepth = changeDepth;
		this.rounded = rounded;
		this.depthCloser = depthCloser;
	}

	@Override
	public BlockRadiusMultiBreakResult run(Player player, ItemStack[] items, BlockData brokenData, BlockBreakEvent event) {
		int level = getLevel(items);
		
		double width = 0;
		double height = 0;
		double depth = 0;
		
		HashMap<String, Object> codes = ChatUtils.getCodes();
		codes.put("%level%", level);
		
		String widthChange = Chatable.get().getMessage(getWidthChange(), codes);
		String heightChange = Chatable.get().getMessage(getHeightChange(), codes);
		String depthChange = Chatable.get().getMessage(getDepthChange(), codes);
		
		if (isChangeWidth()) width += MathUtils.eval(widthChange);
		if (isChangeHeight()) height += MathUtils.eval(heightChange);
		if (isChangeDepth()) depth += MathUtils.eval(depthChange);
		
		return new BlockRadiusMultiBreakResult(level, (int) width, (int) height, (int) depth);
	}

	public String getWidthChange() {
		return widthChange;
	}

	public String getHeightChange() {
		return heightChange;
	}

	public String getDepthChange() {
		return depthChange;
	}

	public boolean isChangeWidth() {
		return changeWidth;
	}

	public boolean isChangeHeight() {
		return changeHeight;
	}

	public boolean isChangeDepth() {
		return changeDepth;
	}

	public boolean isRounded() {
		return rounded;
	}

	public boolean isDepthCloser() {
		return depthCloser;
	}

	public class BlockRadiusMultiBreakResult extends EffectResult {

		private final int width, height, depth;

		public BlockRadiusMultiBreakResult(int level, int width, int height, int depth) {
			super(level);
			this.height = height >= 0 ? height : 0;
			this.width = width >= 0 ? width : 0;
			this.depth = depth >= 0 ? depth : 0;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public int getDepth() {
			return depth;
		}
	}

}
