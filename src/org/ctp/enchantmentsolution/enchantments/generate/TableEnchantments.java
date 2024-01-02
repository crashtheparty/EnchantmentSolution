package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enchantments.helper.Level;
import org.ctp.enchantmentsolution.enchantments.helper.LevelList;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class TableEnchantments extends GenerateEnchantments {

	private Map<ItemData, EnchantmentList[]> enchantmentList = new HashMap<ItemData, EnchantmentList[]>();
	private LevelList levelList;
	private int bookshelves;
	private final ESPlayer es;

	public static TableEnchantments getTableEnchantments(ESPlayer player, ItemStack item, int bookshelves) {
		TableEnchantments enchantments = new TableEnchantments(player.getOnlinePlayer(), bookshelves);
		player.getEnchantmentSeed(new ItemData(item), bookshelves);
		if (item != null) enchantments.generateEnchantments(new ItemData(item));
		return enchantments;
	}

	private TableEnchantments(Player player, int bookshelves) {
		super(player, null, EnchantmentLocation.TABLE);
		this.bookshelves = bookshelves;
		es = EnchantmentSolution.getESPlayer(player);
		levelList = new LevelList(bookshelves, es == null ? new Random() : new Random(es.getLevelSeed()));
	}

	private TableEnchantments(OfflinePlayer player, int bookshelves, boolean treasure, LevelList levelList,
	HashMap<ItemData, EnchantmentList[]> enchantmentList) {
		super(player, null, EnchantmentLocation.TABLE);
		this.bookshelves = bookshelves;
		es = EnchantmentSolution.getESPlayer(player);
		this.levelList = levelList;
		this.enchantmentList = enchantmentList;
	}

	public boolean hasEnchantments(ItemData item) {
		Iterator<Entry<ItemData, EnchantmentList[]>> iterator = enchantmentList.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<ItemData, EnchantmentList[]> entry = iterator.next();
			if (entry.getKey().equals(item)) return true;
		}
		return false;
	}

	public void generateEnchantments(ItemData item) {
		if (!hasEnchantments(item)) {
			Random random = new Random();
			if (es != null) random = new Random(es.getEnchantmentSeed(item, bookshelves));
			EnchantmentList[] list = new EnchantmentList[levelList.getList().length];
			for(int i = 0; i < list.length; i++) {
				Level level = levelList.getList()[i];
				if (level != null && level.getLevel() > -1) list[i] = new EnchantmentList(es, level, item, getLocation(), random);
			}

			enchantmentList.put(item, list);
		}
	}

	public EnchantmentList[] getEnchantments(ItemData item) {
		if (!hasEnchantments(item)) generateEnchantments(item);
		Iterator<Entry<ItemData, EnchantmentList[]>> iterator = enchantmentList.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<ItemData, EnchantmentList[]> entry = iterator.next();
			if (entry.getKey().equals(item)) return entry.getValue();
		}
		return new EnchantmentList[0];
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
