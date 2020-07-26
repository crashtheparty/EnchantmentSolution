package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.item.ItemData;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enchantments.helper.Level;
import org.ctp.enchantmentsolution.enchantments.helper.LevelList;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;

public class TableEnchantments extends GenerateEnchantments {

	private Map<ItemData, EnchantmentList[]> enchantmentList = new HashMap<ItemData, EnchantmentList[]>();
	private LevelList levelList;
	private int bookshelves;
	private static List<TableEnchantments> TABLES = new ArrayList<TableEnchantments>();

	public static TableEnchantments getTableEnchantments(Player player, ItemStack item, int bookshelves) {
		for(TableEnchantments enchantments: TABLES)
			if (enchantments.isSimilar(player, bookshelves)) {
				if (item != null) enchantments.generateEnchantments(new ItemData(item));
				return enchantments;
			}

		TableEnchantments enchantments = new TableEnchantments(player, bookshelves);
		if (item != null) enchantments.generateEnchantments(new ItemData(item));
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
			if (enchantments.getPlayer().getUniqueId().equals(player.getUniqueId())) iterator.remove();
		}
	}

	private TableEnchantments(Player player, int bookshelves) {
		super(player, null, EnchantmentLocation.TABLE);
		this.bookshelves = bookshelves;
		levelList = new LevelList(bookshelves);
	}

	private TableEnchantments(OfflinePlayer player, int bookshelves, boolean treasure, LevelList levelList,
	HashMap<ItemData, EnchantmentList[]> enchantmentList) {
		super(player, null, EnchantmentLocation.TABLE);
		this.bookshelves = bookshelves;
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
			EnchantmentList[] list = new EnchantmentList[levelList.getList().length];
			for(int i = 0; i < list.length; i++) {
				Level level = levelList.getList()[i];
				if (level != null && level.getLevel() > -1) list[i] = new EnchantmentList(getPlayer().getPlayer(), level, item, getLocation());
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

	public static void getFromConfig(YamlConfig config, int i) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(config.getString("enchanting_table." + i + ".player")));
		int bookshelves = config.getInt("enchanting_table." + i + ".bookshelves");
		boolean treasure = config.getBoolean("enchanting_table." + i + ".treasure");

		LevelList levelList = LevelList.fromConfig(config, i, bookshelves);

		HashMap<ItemData, EnchantmentList[]> enchantmentList = new HashMap<ItemData, EnchantmentList[]>();
		int j = 0;
		while (config.containsElements("enchanting_table." + i + ".enchantmentList." + j)) {
			EnchantmentList[] list = new EnchantmentList[6];
			String[] itemData = config.getString("enchanting_table." + i + ".enchantmentList." + j + ".itemdata").split(" ");
			ItemData item = new ItemData(new MatData(itemData[0]).getMaterial(), itemData[1], itemData[2]);
			if (item != null && item.getMaterial() != null) {
				for(Level level: levelList.getList())
					list[level.getSlot()] = EnchantmentList.fromConfig(config, i, j, level.getSlot(), player, level, item);
				enchantmentList.put(item, list);
			}
			j++;
		}
		TableEnchantments enchantments = new TableEnchantments(player, bookshelves, treasure, levelList, enchantmentList);
		TABLES.add(enchantments);
	}

	public static List<TableEnchantments> getAllTableEnchantments() {
		return TABLES;
	}

	public void setConfig(YamlConfig config, int i) {
		setConfig(config, "", i);
	}

	public void setConfig(YamlConfig config, String starting, int i) {
		config.set(starting + "enchanting_table." + i + ".player", getPlayer().getUniqueId().toString());
		config.set(starting + "enchanting_table." + i + ".bookshelves", getBookshelves());

		getLevelList().setConfig(config, i);

		Iterator<Entry<ItemData, EnchantmentList[]>> iterator = enchantmentList.entrySet().iterator();
		int j = 0;
		while (iterator.hasNext()) {
			Entry<ItemData, EnchantmentList[]> entry = iterator.next();
			ItemData item = entry.getKey();
			for(EnchantmentList l: entry.getValue())
				if (l != null) l.setConfig(config, starting, i, j, item);
			j++;
		}
	}
}
