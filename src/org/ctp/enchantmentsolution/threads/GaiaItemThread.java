package org.ctp.enchantmentsolution.threads;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GaiaUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GaiaUtils.GaiaTrees;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class GaiaItemThread extends EnchantmentThread {

	private final Item item;
	private final GaiaTrees tree;

	public GaiaItemThread(ESPlayer player, Item item, GaiaTrees tree) {
		super(player);
		this.item = item;
		this.tree = tree;
	}

	@Override
	public void run() {
		if (item.isOnGround()) {
			Location loc = item.getLocation();
			Block below = loc.getBlock().getRelative(BlockFace.DOWN);
			for(MatData m: tree.getGrowable())
				if (m.getMaterial() == below.getType() && loc.getBlock().isEmpty()) {
					loc.getBlock().setType(item.getItemStack().getType());
					GaiaUtils.addLocation(loc);
					AdvancementUtils.awardCriteria(getPlayer().getOnlinePlayer(), ESAdvancement.REFORESTATION, "tree", 1);
				}
			item.remove();
			remove();
		}
	}

	public Item getItem() {
		return item;
	}

	@Override
	protected void remove() {
		super.remove();
	}

}
