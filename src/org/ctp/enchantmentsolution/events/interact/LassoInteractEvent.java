package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.events.InteractEvent;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;

public class LassoInteractEvent extends InteractEvent {

	private AnimalMob animal;
	private BlockFace face;
	
	public LassoInteractEvent(Player who, ItemStack item, Block block, BlockFace face, AnimalMob animal) {
		super(who, item, block);
		this.setAnimal(animal);
		this.setFace(face);
	}

	public AnimalMob getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalMob animal) {
		this.animal = animal;
	}

	public BlockFace getFace() {
		return face;
	}

	public void setFace(BlockFace face) {
		this.face = face;
	}

}
