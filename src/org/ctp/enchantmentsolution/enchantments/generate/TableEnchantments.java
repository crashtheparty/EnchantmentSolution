package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enchantments.helper.Level;
import org.ctp.enchantmentsolution.enchantments.helper.LevelList;

public class TableEnchantments extends GenerateEnchantments {

	private Map<Material, EnchantmentList[]> enchantmentList = new HashMap<Material, EnchantmentList[]>();
	private LevelList levelList;
	private int bookshelves;
	private static List<TableEnchantments> TABLES = new ArrayList<TableEnchantments>();

	public static TableEnchantments getTableEnchantments(Player player, ItemStack item, int bookshelves,
	boolean treasure) {
		for(TableEnchantments enchantments: TABLES) {
			if (enchantments.isSimilar(player, bookshelves)) {
				if (item != null) {
					if (!enchantments.hasEnchantments(item.getType())) {
						enchantments.generateEnchantments(item.getType());
					}
				}
				return enchantments;
			}
		}

		TableEnchantments enchantments = new TableEnchantments(player, bookshelves, treasure);
		if (item != null) {
			enchantments.generateEnchantments(item.getType());
		}
		TABLES.add(enchantments);
		return enchantments;
	}

	public static void removeAllTableEnchantments() {
		Iterator<TableEnchantments> iterator = TABLES.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
	}

	public static void removeTableEnchantments(Player player) {
		Iterator<TableEnchantments> iterator = TABLES.iterator();
		while (iterator.hasNext()) {
			TableEnchantments enchantments = iterator.next();
			if (enchantments.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) {
				iterator.remove();
			}
		}
	}

	private TableEnchantments(Player player, int bookshelves, boolean treasure) {
		super(player, null, treasure);
		this.bookshelves = bookshelves;
		levelList = new LevelList(bookshelves);
	}

	public boolean hasEnchantments(Material m) {
		return enchantmentList.containsKey(m);
	}

	public void generateEnchantments(Material m) {
		if (!hasEnchantments(m)) {
			EnchantmentList[] list = new EnchantmentList[levelList.getList().length];
			for(int i = 0; i < list.length; i++) {
				Level level = levelList.getList()[i];
				if (level != null && level.getLevel() > -1) {
					list[i] = new EnchantmentList(getPlayer(), level, m, isTreasure());
				}
			}

			enchantmentList.put(m, list);
		}
	}

	public EnchantmentList[] getEnchantments(Material m) {
		if (!hasEnchantments(m)) {
			generateEnchantments(m);
		}
		return enchantmentList.get(m);
	}

	public int getBookshelves() {
		return bookshelves;
	}

	public LevelList getLevelList() {
		return levelList;
	}

	public boolean isSimilar(Player player, int bookshelves) {
		if (getPlayer().getUniqueId().equals(player.getUniqueId())) {
			setPlayer(player);
			return this.bookshelves == bookshelves;
		}
		return false;
	}
}
