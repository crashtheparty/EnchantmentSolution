package org.ctp.enchantmentsolution.events.interact;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.Cooldownable;
import org.ctp.enchantmentsolution.events.Droppable;

public class FlowerGiftEvent extends InteractEvent implements Cooldownable, Droppable {

	private List<ItemStack> drops;
	private Location dropLocation;
	private boolean cooldown;
	private int cooldownTicks, exp;

	public FlowerGiftEvent(Player who, ItemStack item, Block block, List<ItemStack> drops, int exp, Location dropLocation) {
		super(who, new EnchantmentLevel(CERegister.FLOWER_GIFT, 1), item, block);
		this.drops = drops;
		setDropLocation(dropLocation);
		cooldown = true;
		cooldownTicks = 20;
		this.exp = exp;
	}

	@Override
	public Location getDropLocation() {
		return dropLocation;
	}

	@Override
	public void setDropLocation(Location dropLocation) {
		this.dropLocation = dropLocation;
	}

	@Override
	public boolean useCooldown() {
		return cooldown;
	}

	@Override
	public void setCooldown(boolean cooldown) {
		this.cooldown = cooldown;
	}

	@Override
	public int getCooldownTicks() {
		return cooldownTicks;
	}

	@Override
	public void setCooldownTicks(int cooldownTicks) {
		this.cooldownTicks = cooldownTicks;
	}

	@Override
	public List<ItemStack> getDrops() {
		return drops;
	}

	@Override
	public int getExp() {
		return exp;
	}

	@Override
	public void setExp(int exp) {
		this.exp = exp;
	}

}
