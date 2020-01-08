package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class FlowerGiftEvent extends InteractEvent {

	private ItemStack flower;
	private Location dropLocation;

	public FlowerGiftEvent(Player who, ItemStack item, Block block, ItemStack flower, Location dropLocation) {
		super(who, new EnchantmentLevel(CERegister.FLOWER_GIFT, 1), item, block);
		setFlower(flower);
		setDropLocation(dropLocation);
	}

	public ItemStack getFlower() {
		return flower;
	}

	public void setFlower(ItemStack flower) {
		this.flower = flower;
	}

	public Location getDropLocation() {
		return dropLocation;
	}

	public void setDropLocation(Location dropLocation) {
		this.dropLocation = dropLocation;
	}

}
