package org.ctp.enchantmentsolution.interfaces.effects.interact;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.interact.ActionIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.PlayerInteractEffect;

public abstract class WaterLogEffect extends PlayerInteractEffect {

	private boolean allowWaterlog, allowDry;

	public WaterLogEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	boolean allowWaterlog, boolean allowDry, InteractCondition[] conditions) {
		super(enchantment, type, location, priority, addCondition(conditions, new ActionIsTypeCondition(false, Action.RIGHT_CLICK_BLOCK)));
		this.allowWaterlog = allowWaterlog;
		this.allowDry = allowDry;
	}

	@Override
	public WaterLogResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		int level = getLevel(items);
		ItemStack item = items[0];

		Block block = event.getClickedBlock();
		Sound sound = Sound.ENTITY_SHEEP_SHEAR;

		if (block.getBlockData() instanceof Waterlogged) {
			Waterlogged water = (Waterlogged) block.getBlockData();
			if (water.isWaterlogged() && allowDry && (block.getType().isInteractable() && player.isSneaking() || !block.getType().isInteractable())) return new WaterLogResult(level, block, sound, item, false);
			else if (!water.isWaterlogged() && allowWaterlog && (block.getType().isInteractable() && player.isSneaking() || !block.getType().isInteractable())) return new WaterLogResult(level, block, sound, item, true);
		}

		return new WaterLogResult(0, null, null, null, false);
	}

	public boolean isAllowWaterlog() {
		return allowWaterlog;
	}

	public void setAllowWaterlog(boolean allowWaterlog) {
		this.allowWaterlog = allowWaterlog;
	}

	public boolean isAllowDry() {
		return allowDry;
	}

	public void setAllowDry(boolean allowDry) {
		this.allowDry = allowDry;
	}

	public class WaterLogResult extends EffectResult {

		private final Block block;
		private final Sound sound;
		private final ItemStack item;
		private final boolean waterlogging;

		public WaterLogResult(int level, Block block, Sound sound, ItemStack item, boolean waterlogging) {
			super(level);
			this.block = block;
			this.sound = sound;
			this.item = item;
			this.waterlogging = waterlogging;
		}

		public Block getBlock() {
			return block;
		}

		public Sound getSound() {
			return sound;
		}

		public ItemStack getItem() {
			return item;
		}

		public boolean isWaterlogging() {
			return waterlogging;
		}
	}

}
