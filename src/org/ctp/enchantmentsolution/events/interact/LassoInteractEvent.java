package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.player.InteractEvent;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;

public class LassoInteractEvent extends InteractEvent {

	private final AnimalMob animal;
	private BlockFace face;

	public LassoInteractEvent(Player who, int level, ItemStack item, Block block, BlockFace face, AnimalMob animal) {
		super(who, new EnchantmentLevel(CERegister.IRENES_LASSO, level), item, block);
		this.animal = animal;
		setFace(face);
	}

	public AnimalMob getAnimal() {
		return animal;
	}

	public BlockFace getFace() {
		return face;
	}

	public void setFace(BlockFace face) {
		this.face = face;
	}

}
