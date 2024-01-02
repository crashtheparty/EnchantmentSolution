package org.ctp.enchantmentsolution.interfaces.effects.interact;

import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.effects.PlayerInteractEffect;

public abstract class UpdateLightEffect extends PlayerInteractEffect {

	private int startLevel;
	private boolean increase;

	public UpdateLightEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	int startLevel, boolean increase, InteractCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.startLevel = startLevel;
		this.increase = increase;
	}

	@Override
	public UpdateLightResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		int level = getLevel(items);

		Block block = event.getClickedBlock();
		if (block != null) {
			int lightLevel = 0;
			int previousLevel = 0;
			block = block.getRelative(event.getBlockFace());
			if (MatData.isAir(block.getType())) lightLevel = startLevel;
			else if (block.getType() == new MatData("LIGHT").getMaterial()) {
				Levelled l = (Levelled) block.getBlockData();
				previousLevel = l.getLevel();
				if (increase) lightLevel = l.getLevel() + 1;
				else
					lightLevel = l.getLevel() - 1;
			}
			return new UpdateLightResult(level, block, lightLevel, previousLevel);
		}

		return new UpdateLightResult(level, null, 0, 0);
	}

	public int getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}

	public boolean isIncrease() {
		return increase;
	}

	public void setIncrease(boolean increase) {
		this.increase = increase;
	}

	public class UpdateLightResult extends EffectResult {

		private final int lightLevel, previousLevel;
		private final Block block;

		public UpdateLightResult(int level, Block block, int lightLevel, int previousLevel) {
			super(level);
			this.block = block;
			this.lightLevel = lightLevel;
			this.previousLevel = previousLevel;
		}

		public int getLightLevel() {
			return lightLevel;
		}

		public Block getBlock() {
			return block;
		}

		public int getPreviousLevel() {
			return previousLevel;
		}
	}

}
