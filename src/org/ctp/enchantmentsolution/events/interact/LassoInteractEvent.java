package org.ctp.enchantmentsolution.events.interact;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.abilityhelpers.LassoMob;

public class LassoInteractEvent extends InteractEvent {

	private final LassoMob lasso;
	private BlockFace face;

	public LassoInteractEvent(Player who, int level, ItemStack item, Block block, BlockFace face, LassoMob lasso) {
		super(who, new EnchantmentLevel(CERegister.IRENES_LASSO, level), item, block);
		this.lasso = lasso;
		setFace(face);
	}

	public LassoMob getLassoMob() {
		return lasso;
	}

	public BlockFace getFace() {
		return face;
	}

	public void setFace(BlockFace face) {
		this.face = face;
	}

}
