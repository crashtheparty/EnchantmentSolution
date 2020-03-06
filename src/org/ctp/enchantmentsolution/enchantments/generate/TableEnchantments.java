package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enchantments.helper.Level;
import org.ctp.enchantmentsolution.enchantments.helper.LevelList;
import org.ctp.enchantmentsolution.enums.MatData;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfig;

public class TableEnchantments extends GenerateEnchantments {

	private Map<Material, EnchantmentList[]> enchantmentList = new HashMap<Material, EnchantmentList[]>();
	private LevelList levelList;
	private int bookshelves;
	private static List<TableEnchantments> TABLES = new ArrayList<TableEnchantments>();

	public static TableEnchantments getTableEnchantments(Player player, ItemStack item, int bookshelves,
	boolean treasure) {
		for(TableEnchantments enchantments: TABLES)
			if (enchantments.isSimilar(player, bookshelves)) {
				if (item != null) enchantments.generateEnchantments(item.getType());
				return enchantments;
			}

		TableEnchantments enchantments = new TableEnchantments(player, bookshelves, treasure);
		if (item != null) enchantments.generateEnchantments(item.getType());
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

	private TableEnchantments(Player player, int bookshelves, boolean treasure) {
		super(player, null, treasure);
		this.bookshelves = bookshelves;
		levelList = new LevelList(bookshelves);
	}
	
	private TableEnchantments(OfflinePlayer player, int bookshelves, boolean treasure, LevelList levelList, HashMap<Material, EnchantmentList[]> enchantmentList) {
		super(player, null, treasure);
		this.bookshelves = bookshelves;
		this.levelList = levelList;
		this.enchantmentList = enchantmentList;
	}

	public boolean hasEnchantments(Material m) {
		return enchantmentList.containsKey(m);
	}

	public void generateEnchantments(Material m) {
		if (!hasEnchantments(m)) {
			EnchantmentList[] list = new EnchantmentList[levelList.getList().length];
			for(int i = 0; i < list.length; i++) {
				Level level = levelList.getList()[i];
				if (level != null && level.getLevel() > -1) list[i] = new EnchantmentList(getPlayer().getPlayer(), level, m, isTreasure());
			}

			enchantmentList.put(m, list);
		}
	}

	public EnchantmentList[] getEnchantments(Material m) {
		if (!hasEnchantments(m)) generateEnchantments(m);
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

	public static void getFromConfig(YamlConfig config, int i) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(config.getString("enchanting_table." + i + ".player")));
		int bookshelves = config.getInt("enchanting_table." + i + ".bookshelves");
		boolean treasure = config.getBoolean("enchanting_table." + i + ".treasure");
		
		LevelList levelList = LevelList.fromConfig(config, i, bookshelves);
		
		HashMap<Material, EnchantmentList[]> enchantmentList = new HashMap<Material, EnchantmentList[]>();
		List<String> keys = config.getLevelEntryKeys("enchanting_table." + i + ".enchantmentList");
		for(String key : keys) {
			String mat = key.replace("enchanting_table." + i + ".enchantmentList.", "");
			EnchantmentList[] list = new EnchantmentList[6];
			MatData data = new MatData(mat);
			Material m = data.getMaterial();
			if(m != null) {
				for(Level level : levelList.getList())
					list[level.getSlot()] = EnchantmentList.fromConfig(config, i, mat, level.getSlot(), player, level, m, treasure);
				enchantmentList.put(m, list);
			}
		}
		TableEnchantments enchantments = new TableEnchantments(player, bookshelves, treasure, levelList, enchantmentList);
		TABLES.add(enchantments);
	}

	public static List<TableEnchantments> getAllTableEnchantments() {
		return TABLES;
	}

	public void setConfig(YamlConfig config, int i) {
		config.set("enchanting_table." + i + ".player", getPlayer().getUniqueId().toString());
		config.set("enchanting_table." + i + ".bookshelves", getBookshelves());
		config.set("enchanting_table." + i + ".treasure", isTreasure());
		
		getLevelList().setConfig(config, i);
		
		Iterator<Entry<Material, EnchantmentList[]>> iterator = enchantmentList.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Material, EnchantmentList[]> entry = iterator.next();
			Material m = entry.getKey();
			for(EnchantmentList l : entry.getValue())
				l.setConfig(config, i, m);
		}
	}
}
