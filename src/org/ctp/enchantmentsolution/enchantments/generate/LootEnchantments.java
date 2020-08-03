package org.ctp.enchantmentsolution.enchantments.generate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemData;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enchantments.helper.Level;
import org.ctp.enchantmentsolution.enchantments.helper.LevelList;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;

public abstract class LootEnchantments extends GenerateEnchantments {

	private LevelList levelList;
	private EnchantmentList[] list;

	public LootEnchantments(Player player, ItemStack item, EnchantmentLocation location) {
		this(player, item, 0, location);
	}

	public LootEnchantments(Player player, ItemStack item, int bookshelves, EnchantmentLocation location) {
		super(player, item, location);

		levelList = new LevelList(bookshelves);

		list = new EnchantmentList[levelList.getList().length];
		Player p = null;
		if (getPlayer() != null) p = getPlayer().getPlayer();
		for(int i = 0; i < list.length; i++) {
			Level level = levelList.getList()[i];
			if (level.getLevel() > -1) list[i] = new EnchantmentList(p, level, new ItemData(item), location);
		}
	}

	public LevelList getLevelList() {
		return levelList;
	}

	public EnchantmentList[] getList() {
		return list;
	}
}
