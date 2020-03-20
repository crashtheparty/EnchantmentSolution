package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enchantments.helper.Level;
import org.ctp.enchantmentsolution.enchantments.helper.LevelList;
import org.ctp.enchantmentsolution.enums.ItemData;

public abstract class LootEnchantments extends GenerateEnchantments {

	private LevelList levelList;
	private EnchantmentList[] list;

	public LootEnchantments(Player player, ItemStack item, boolean treasure) {
		super(player, item, treasure);
	}

	public LootEnchantments(Player player, ItemStack item, int bookshelves, boolean treasure) {
		super(player, item, treasure);

		levelList = new LevelList(bookshelves);

		list = new EnchantmentList[levelList.getList().length];
		Player p = null;
		if (getPlayer() != null) p = getPlayer().getPlayer();
		for(int i = 0; i < list.length; i++) {
			Level level = levelList.getList()[i];
			if (level.getLevel() > -1) list[i] = new EnchantmentList(p, level, new ItemData(item), isTreasure());
		}
	}

	public LevelList getLevelList() {
		return levelList;
	}

	public EnchantmentList[] getList() {
		return list;
	}
}
