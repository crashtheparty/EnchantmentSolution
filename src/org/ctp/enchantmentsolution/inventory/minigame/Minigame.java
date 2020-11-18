package org.ctp.enchantmentsolution.inventory.minigame;

import java.util.*;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.inventory.Pageable;
import org.ctp.crashapi.item.ItemData;
import org.ctp.crashapi.item.ItemType;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.generate.MinigameEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.inventory.minigame.MinigameItem.MinigameItemType;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.MinigameUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class Minigame implements InventoryData, Pageable {

	private Player player;
	private Inventory inventory;
	private Block block;
	private boolean opening;
	private static List<MinigameItem> MINIGAME_ITEMS = null;
	private static Map<UUID, Map<MinigameItem, Integer>> TIMES_USED = new HashMap<UUID, Map<MinigameItem, Integer>>();
	private final List<Integer> fastLocations = Arrays.asList(10, 12, 14, 16, 19, 21, 23, 25, 28, 30, 32, 34, 37, 39, 41, 43);
	private final List<Integer> mondaysLocations = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43);
	private final List<Integer> customLocationsSeven = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43);
	private final List<Integer> customLocationsNine = Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44);
	private Screen screen;
	private int page = 1;

	public Minigame(Player player, Block block) {
		setPlayer(player);
		this.block = block;
	}

	@Override
	public void setInventory() {
		screen = Screen.FAST;
		try {
			screen = Screen.valueOf(ConfigString.MINIGAME_TYPE.getString().toUpperCase(Locale.ROOT));
		} catch (Exception ex) {}
		try {
			Configurations c = EnchantmentSolution.getPlugin().getConfigurations();
			if (screen == Screen.FAST) {
				Inventory inv = Bukkit.createInventory(null, 54, Chatable.get().getMessage(getCodes(), "minigame.name"));
				inv = open(inv);
				ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
				ItemMeta mirrorMeta = mirror.getItemMeta();
				mirrorMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "minigame.mirror"));
				mirror.setItemMeta(mirrorMeta);
				for(int i = 0; i < 54; i++)
					inv.setItem(i, mirror);

				List<ItemType> types = ItemType.getUniqueEnchantableTypes();
				for(int i = 0; i < 16; i++) {
					ItemType type = types.get(i);
					int slot = fastLocations.get(i);
					ItemStack item = new ItemStack(getMaterial(type));
					ItemMeta itemMeta = item.getItemMeta();
					HashMap<String, Object> itemCodes = getCodes();
					itemCodes.put("%name%", type.getDisplayName());
					itemMeta.setDisplayName(Chatable.get().getMessage(itemCodes, "minigame.fast.item"));
					itemMeta.setLore(Chatable.get().getMessages(getCodes(), "minigame.fast.item_lore"));
					item.setItemMeta(itemMeta);

					inv.setItem(slot, item);
				}
			} else if (screen == Screen.MONDAYS) {
				Inventory inv = Bukkit.createInventory(null, 54, Chatable.get().getMessage(getCodes(), "minigame.name"));
				inv = open(inv);
				ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
				ItemMeta mirrorMeta = mirror.getItemMeta();
				mirrorMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "minigame.mirror"));
				mirror.setItemMeta(mirrorMeta);
				for(int i = 0; i < 54; i++)
					inv.setItem(i, mirror);

				List<String> enchants = getEnchants();

				for(int i = 0; i < mondaysLocations.size(); i++) {
					if (enchants.size() <= i) break;
					int num = i + mondaysLocations.size() * (page - 1);
					String name = c.getMinigames().getString("mondays.enchantments." + enchants.get(num) + ".enchantment");
					if (name == null) continue;
					CustomEnchantment enchant = RegisterEnchantments.getByName(name);
					int slot = mondaysLocations.get(i);
					ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
					ItemMeta itemMeta = item.getItemMeta();
					HashMap<String, Object> itemCodes = getCodes();
					itemCodes.put("%name%", enchant == null ? name.equalsIgnoreCase("random") ? c.getLanguage().getString("enchantment.random") : name : enchant.getDisplayName());
					HashMap<String, Object> itemLoreCodes = getCodes();
					itemLoreCodes.put("%cost%", c.getMinigames().getInt("mondays.enchantments." + enchants.get(num) + ".cost"));
					itemMeta.setDisplayName(Chatable.get().getMessage(itemCodes, "minigame.mondays.item"));
					itemMeta.setLore(Chatable.get().getMessages(itemLoreCodes, "minigame.mondays.item_lore"));
					item.setItemMeta(itemMeta);

					inv.setItem(slot, item);
				}
				if (enchants.size() > mondaysLocations.size() * page) inv.setItem(53, nextPage());
				if (page != 1) inv.setItem(45, previousPage());
			} else if (screen == Screen.CUSTOM) {
				Inventory inv = Bukkit.createInventory(null, 54, Chatable.get().getMessage(getCodes(), "minigame.name"));
				inv = open(inv);
				ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
				ItemMeta mirrorMeta = mirror.getItemMeta();
				mirrorMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "minigame.mirror"));
				mirror.setItemMeta(mirrorMeta);
				for(int i = 0; i < 54; i++)
					inv.setItem(i, mirror);

				int paging = getPaging();
				List<MinigameItem> items = getMinigameCustomItems();
				for(int i = 0; i < paging; i++) {
					if (items.size() <= i) break;
					int num = i + paging * (page - 1);
					MinigameItem item = items.get(num);
					if (item == null) continue;
					int slot = paging % 9 == 0 ? customLocationsNine.get(i) : customLocationsSeven.get(i);
					ItemStack show = new ItemStack(item.getShow().hasMaterial() ? item.getShow().getMaterial() : Material.ENCHANTED_BOOK);
					ItemMeta showMeta = show.getItemMeta();
					HashMap<String, Object> codes = getCodes();
					codes.put("%material%", item.getEnchant().hasMaterial() ? item.getEnchant().getMaterial().name() : item.getEnchant().getMaterialName());
					showMeta.setDisplayName(Chatable.get().getMessage(codes, "minigame.custom.item.name"));
					int lvlCost = item.getLvlCost();
					int lapisCost = item.getLapisCost();
					double economyCost = item.getEconomyCost();
					Map<MinigameItem, Integer> timesUsed = TIMES_USED.get(player.getUniqueId());
					int times = 0;
					if (timesUsed != null && timesUsed.get(item) != null) times = timesUsed.get(item);

					if (item.willIncreaseLevelCost()) lvlCost += times * item.getLvlExtraCost();
					if (item.willIncreaseLapisCost()) lapisCost += times * item.getLapisExtraCost();
					if (item.willIncreaseEconomyCost()) economyCost += times * item.getEconomyExtraCost();
					if (item.getLvlMaxCost() > 0 && lvlCost > item.getLvlMaxCost()) lvlCost = item.getLvlMaxCost();
					if (item.getLapisMaxCost() > 0 && lapisCost > item.getLapisMaxCost()) lapisCost = item.getLapisMaxCost();
					if (item.getEconomyMaxCost() > 0 && economyCost > item.getEconomyMaxCost()) economyCost = item.getEconomyMaxCost();

					if (item.getType() == MinigameItemType.ENCHANTMENT) {
						List<String> lore = new ArrayList<String>();
						for(EnchantmentLevel level: item.getLevels()) {
							HashMap<String, Object> loreCodes = getCodes();
							loreCodes.put("%enchantment%", level.getEnchant().getDisplayName());
							loreCodes.put("%level%", level.getLevel());
							lore.add(Chatable.get().getMessage(loreCodes, "minigame.custom.item.enchantment"));
						}
						if (lvlCost > 0) {
							HashMap<String, Object> loreCodes = getCodes();
							loreCodes.put("%lvl_cost%", lvlCost);
							lore.add(Chatable.get().getMessage(loreCodes, "minigame.custom.item.enchantment_level_cost"));
						}
						if (lapisCost > 0) {
							HashMap<String, Object> loreCodes = getCodes();
							loreCodes.put("%lapis_cost%", lapisCost);
							lore.add(Chatable.get().getMessage(loreCodes, "minigame.custom.item.enchantment_lapis_cost"));
						}
						if (economyCost > 0) {
							HashMap<String, Object> loreCodes = getCodes();
							loreCodes.put("%economy_cost%", MinigameUtils.format(economyCost));
							loreCodes.put("%money_name%", MinigameUtils.getMoneyName());
							loreCodes.put("%money_sign%", MinigameUtils.getMoneySign());
							lore.add(Chatable.get().getMessage(loreCodes, "minigame.custom.item.enchantment_economy_cost"));
						}
						showMeta.setLore(lore);
					} else {
						List<String> lore = new ArrayList<String>();
						HashMap<String, Object> multipleCodes = getCodes();
						multipleCodes.put("%multiple%", item.getType().isMultiple());
						lore.addAll(Chatable.get().getMessages(multipleCodes, "minigame.custom.item.enchantment_multiple"));
						if (lvlCost > 0) {
							HashMap<String, Object> loreCodes = getCodes();
							loreCodes.put("%lvl_cost%", lvlCost);
							lore.add(Chatable.get().getMessage(loreCodes, "minigame.custom.item.enchantment_level_cost"));
						}
						if (lapisCost > 0) {
							HashMap<String, Object> loreCodes = getCodes();
							loreCodes.put("%lapis_cost%", lapisCost);
							lore.add(Chatable.get().getMessage(loreCodes, "minigame.custom.item.enchantment_lapis_cost"));
						}
						if (economyCost > 0) {
							HashMap<String, Object> loreCodes = getCodes();
							loreCodes.put("%economy_cost%", MinigameUtils.format(economyCost));
							loreCodes.put("%money_name%", MinigameUtils.getMoneyName());
							loreCodes.put("%money_sign%", MinigameUtils.getMoneySign());
							lore.add(Chatable.get().getMessage(loreCodes, "minigame.custom.item.enchantment_economy_cost"));
						}
						showMeta.setLore(lore);
					}
					show.setItemMeta(showMeta);

					inv.setItem(slot, show);
				}
				if (items.size() > paging * page) inv.setItem(53, nextPage());
				if (page != 1) inv.setItem(45, previousPage());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public void close(boolean external) {
		if (EnchantmentSolution.getPlugin().hasInventory(this)) {
			EnchantmentSolution.getPlugin().removeInventory(this);
			if (!external) player.getOpenInventory().close();
		}
	}

	@Override
	public void setInventory(List<ItemStack> items) {}

	@Override
	public void setItemName(String name) {}

	@Override
	public Inventory open(Inventory inv) {
		opening = true;
		if (inventory == null) {
			inventory = inv;
			player.openInventory(inv);
		} else if (inv.getSize() == inventory.getSize()) {
			inv = player.getOpenInventory().getTopInventory();
			inventory = inv;
		} else {
			inventory = inv;
			player.openInventory(inv);
		}
		for(int i = 0; i < inventory.getSize(); i++)
			inventory.setItem(i, new ItemStack(Material.AIR));
		if (opening) opening = false;
		return inv;
	}

	@Override
	public List<ItemStack> getItems() {
		return null;
	}

	public HashMap<String, Object> getCodes() {
		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%player%", player.getName());
		return codes;
	}

	public enum Screen {
		FAST(), MONDAYS(), CUSTOM();
	}

	public Screen getScreen() {
		return screen;
	}

	public Material getMaterial(ItemType type) {
		Material m = Material.BOOK;
		for(ItemData data: type.getEnchantMaterials())
			if (data.getMaterial() != Material.BOOK && data.getMaterial() != Material.ENCHANTED_BOOK) {
				m = data.getMaterial();
				break;
			}
		return m;
	}

	public void addFastEnchantment(int slot) {
		int num = fastLocations.indexOf(slot);
		ItemType type = ItemType.getUniqueEnchantableTypes().get(num);

		ItemStack item = new ItemStack(getMaterial(type));
		MinigameEnchantments enchants = GenerateUtils.generateMinigameEnchants(player, item, block);
		EnchantmentList[] list = enchants.getList();
		List<EnchantmentList> lists = new ArrayList<EnchantmentList>();
		if (list != null) for(EnchantmentList l: list)
			if (l != null) lists.add(l);
		int random = (int) (Math.random() * lists.size());

		List<EnchantmentLevel> levels = lists.get(random).getEnchantments();

		int i = 0;

		while ((levels == null || levels.size() == 0) && lists.size() > i) {
			levels = lists.get(i).getEnchantments();
			random = i;
			i++;
		}
		if (levels == null || levels.size() == 0) {
			Chatable.get().sendWarning("Item couldn't find EnchantmentSolution enchantments.");
			return;
		}
		item.setType(Material.BOOK);
		item = EnchantmentUtils.addEnchantmentsToItem(item, levels);

		boolean useBooks = ConfigString.USE_ENCHANTED_BOOKS.getBoolean();
		if (item.getType() == Material.BOOK && useBooks) item = EnchantmentUtils.convertToEnchantedBook(item);
		int lvlCost = MinigameUtils.getTableLevelCost(random + 1);
		int lapisCost = MinigameUtils.getTableLapisCost(random + 1);
		int lapis = MinigameUtils.getLapis(player);
		double economyCost = MinigameUtils.getTableEconomyCost(0);
		double playerMoney = MinigameUtils.getPlayerMoney(player);
		if ((lvlCost <= player.getLevel() || player.getGameMode() == GameMode.CREATIVE) && (lapisCost <= lapis || player.getGameMode() == GameMode.CREATIVE) && economyCost <= playerMoney) {
			if (player.getGameMode() != GameMode.CREATIVE) {
				player.setLevel(player.getLevel() - lvlCost);
				MinigameUtils.removeLapis(player, lapisCost);
			}
			MinigameUtils.removePlayerMoney(player, economyCost);
			ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
		} else
			cannotGetEnchantment(lvlCost, lapis, lapisCost, playerMoney, economyCost);
	}

	public void addMondaysEnchantment(int slot) {
		int num = mondaysLocations.indexOf(slot) + mondaysLocations.size() * (page - 1);
		List<String> enchants = getEnchants();

		String s = enchants.get(num);
		Configurations c = Configurations.getConfigurations();
		String name = c.getMinigames().getString("mondays.enchantments." + s + ".enchantment");
		CustomEnchantment ench = RegisterEnchantments.getByName(name);

		if (ench == null && name.equalsIgnoreCase("random")) {
			List<EnchantmentLevel> levels = GenerateUtils.generateBookLoot(player, new ItemStack(Material.BOOK), EnchantmentLocation.NONE);
			int tries = 50;
			while (levels.size() == 0 && tries > 0) {
				tries--;
				levels = GenerateUtils.generateBookLoot(player, new ItemStack(Material.BOOK), EnchantmentLocation.NONE);
			}
			if (levels.size() > 0) ench = levels.get(0).getEnchant();
		}
		if (ench != null) {
			ItemStack item = new ItemStack(Material.BOOK);
			EnchantmentUtils.addEnchantmentToItem(item, ench, 1);
			boolean useBooks = ConfigString.USE_ENCHANTED_BOOKS.getBoolean();
			if (item.getType() == Material.BOOK && useBooks) item = EnchantmentUtils.convertToEnchantedBook(item);
			String location = "mondays.enchantments." + s + ".costs.";
			int lvlCost = 0;
			int lapisCost = 0;
			int lapis = MinigameUtils.getLapis(player);
			double economyCost = 0;
			double playerMoney = MinigameUtils.getPlayerMoney(player);
			for(String string: c.getMinigames().getStringList(location + "use")) {
				if (string.equalsIgnoreCase("level")) lvlCost = c.getMinigames().getInt(location + ".level");
				if (string.equalsIgnoreCase("lapis")) lapisCost = c.getMinigames().getInt(location + ".lapis");
				if (string.equalsIgnoreCase("economy")) economyCost = MinigameUtils.getTableEconomyCost(c.getMinigames().getInt(location + ".economy"));
			}
			if ((lvlCost <= player.getLevel() || player.getGameMode() == GameMode.CREATIVE) && (lapisCost <= lapis || player.getGameMode() == GameMode.CREATIVE) && economyCost <= playerMoney) {
				if (player.getGameMode() != GameMode.CREATIVE) {
					player.setLevel(player.getLevel() - lvlCost);
					MinigameUtils.removeLapis(player, lapisCost);
				}
				MinigameUtils.removePlayerMoney(player, economyCost);
				ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
			} else
				cannotGetEnchantment(lvlCost, lapis, lapisCost, playerMoney, economyCost);
		} else if (name.equalsIgnoreCase("random")) Chatable.get().sendMessage(player, Chatable.get().getMessage(getCodes(), "minigame.invalid_random_enchant"));
		else {
			HashMap<String, Object> codes = getCodes();
			codes.put("%name%", name);
			Chatable.get().sendMessage(player, Chatable.get().getMessage(codes, "minigame.invalid_enchant_name"));
		}
	}

	public void addCustomEnchantment(int slot) {
		List<Integer> list = getPaging() % 9 == 0 ? customLocationsNine : customLocationsSeven;
		int num = list.indexOf(slot) + list.size() * (page - 1);
		MinigameItem item = getMinigameCustomItems().get(num);
		if (item != null) {
			ItemStack enchant = new ItemStack(item.getEnchant().getMaterial());
			boolean useBooks = ConfigString.USE_ENCHANTED_BOOKS.getBoolean();
			if (enchant.getType() == Material.BOOK && useBooks) enchant = EnchantmentUtils.convertToEnchantedBook(enchant);
			int lvlCost = item.getLvlCost();
			int lapisCost = item.getLapisCost();
			int lapis = MinigameUtils.getLapis(player);
			double economyCost = item.getEconomyCost();
			double playerMoney = MinigameUtils.getPlayerMoney(player);
			Map<MinigameItem, Integer> timesUsed = TIMES_USED.get(player.getUniqueId());
			int times = 0;
			if (timesUsed != null && timesUsed.get(item) != null) times = timesUsed.get(item);

			if (item.willIncreaseLevelCost()) lvlCost += times * item.getLvlExtraCost();
			if (item.willIncreaseLapisCost()) lapisCost += times * item.getLapisExtraCost();
			if (item.willIncreaseEconomyCost()) economyCost += times * item.getEconomyExtraCost();
			if (item.getLvlMaxCost() > 0 && lvlCost > item.getLvlMaxCost()) lvlCost = item.getLvlMaxCost();
			if (item.getLapisMaxCost() > 0 && lapisCost > item.getLapisMaxCost()) lapisCost = item.getLapisMaxCost();
			if (item.getEconomyMaxCost() > 0 && economyCost > item.getEconomyMaxCost()) economyCost = item.getEconomyMaxCost();
			if (item.getType() == MinigameItemType.ENCHANTMENT) enchant = EnchantmentUtils.addEnchantmentsToItem(enchant, item.getLevels());
			else
				enchant = GenerateUtils.generateMinigameLoot(player, enchant, block, item);
			if ((lvlCost <= player.getLevel() || player.getGameMode() == GameMode.CREATIVE) && (lapisCost <= lapis || player.getGameMode() == GameMode.CREATIVE) && economyCost <= playerMoney) {
				if (player.getGameMode() != GameMode.CREATIVE) {
					player.setLevel(player.getLevel() - lvlCost);
					MinigameUtils.removeLapis(player, lapisCost);
				}
				MinigameUtils.removePlayerMoney(player, economyCost);
				ItemUtils.giveItemToPlayer(player, enchant, player.getLocation(), false);
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
				Map<MinigameItem, Integer> itemHash = new HashMap<MinigameItem, Integer>();
				if (!TIMES_USED.containsKey(player.getUniqueId())) {
					itemHash.put(item, 1);
					TIMES_USED.put(player.getUniqueId(), itemHash);
				} else {
					itemHash = TIMES_USED.get(player.getUniqueId());
					itemHash.put(item, times + 1);
					TIMES_USED.put(player.getUniqueId(), itemHash);
				}
			} else
				cannotGetEnchantment(lvlCost, lapis, lapisCost, playerMoney, economyCost);
		} else {
			HashMap<String, Object> codes = getCodes();
			Chatable.get().sendMessage(player, Chatable.get().getMessage(codes, "minigame.invalid_item"));
		}
	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
	public void setPage(int page) {
		this.page = page;
	}

	private List<String> getEnchants() {
		YamlConfig config = Configurations.getConfigurations().getMinigames().getConfig();
		List<String> paths = config.getLevelEntryKeysAtLevel("mondays.enchantments");
		paths.sort((o1, o2) -> {
			Integer i1 = null, i2 = null;
			try {
				i1 = Integer.parseInt(o1);
			} catch (NumberFormatException ex1) {}
			try {
				i2 = Integer.parseInt(o2);
			} catch (NumberFormatException ex2) {}
			if (i1 != null && i2 == null) return 1;
			else if (i1 == null && i2 != null) return -1;
			else if (i1 != null && i2 != null) return i1.intValue() - i2.intValue();
			return o1.compareTo(o2);
		});
		return paths;
	}

	private int getPaging() {
		int paging = ConfigString.MINIGAME_CUSTOM_PAGING.getInt();
		if (Arrays.asList(7, 9, 14, 18, 21, 27, 28, 36).contains(paging)) return paging;
		return 28;
	}

	private List<MinigameItem> getMinigameCustomItems() {
		if (MINIGAME_ITEMS == null) {
			YamlConfig config = Configurations.getConfigurations().getMinigames().getConfig();
			List<String> keys = config.getLevelEntryKeys("custom.items");
			List<MinigameItem> items = new ArrayList<MinigameItem>();
			for(String key: keys) {
				List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
				for(String s: config.getStringList(key + ".enchantments")) {
					EnchantmentLevel level = new EnchantmentLevel(s, config);
					if (level != null) levels.add(level);
				}
				try {
					items.add(new MinigameItem(new MatData(config.getString(key + ".material.show")), new MatData(config.getString(key + ".material.enchant")), MinigameItemType.valueOf(config.getString(key + ".type").toUpperCase(Locale.ROOT)), config.getInt(key + ".costs.level"), config.getInt(key + ".costs.extra_level_cost_per_use"), config.getInt(key + ".costs.max_level_cost"), config.getInt(key + ".costs.lapis"), config.getInt(key + ".costs.extra_lapis_cost_per_use"), config.getInt(key + ".costs.max_lapis_cost"), config.getDouble(key + ".costs.economy"), config.getDouble(key + ".costs.extra_economy_cost_per_use"), config.getDouble(key + ".costs.max_economy_cost"), config.getStringList(key + ".costs.use"), config.getInt(key + ".books.min"), config.getInt(key + ".books.max"), config.getInt(key + ".levels.min"), config.getInt(key + ".levels.max"), config.getInt(key + ".slot"), config.getBoolean(key + ".costs.use_level_cost_increase"), config.getBoolean(key + ".costs.use_lapis_cost_increase"), config.getBoolean(key + ".costs.use_economy_cost_increase"), levels));
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
				}
			}

			MINIGAME_ITEMS = new ArrayList<MinigameItem>();
			for(MinigameItem item: items) {
				while (MINIGAME_ITEMS.size() <= item.getSlot())
					MINIGAME_ITEMS.add(null);
				MINIGAME_ITEMS.set(item.getSlot(), item);
			}
		}

		return MINIGAME_ITEMS;
	}

	public static void reset() {
		MINIGAME_ITEMS = null;
		TIMES_USED = new HashMap<UUID, Map<MinigameItem, Integer>>();

		EnchantmentSolution.getPlugin().closeInventories(Minigame.class);
	}

	@Override
	public ChatUtils getChat() {
		return Chatable.get();
	}

	private void cannotGetEnchantment(int lvlCost, int lapis, int lapisCost, double playerMoney, double economyCost) {
		HashMap<String, Object> codes = getCodes();
		HashMap<String, Object> lvlCodes = getCodes();
		HashMap<String, Object> lapisCodes = getCodes();
		HashMap<String, Object> economyCodes = getCodes();
		lvlCodes.put("%player_level%", player.getLevel());
		lvlCodes.put("%level_cost%", lvlCost);
		lapisCodes.put("%player_lapis%", lapis);
		lapisCodes.put("%lapis_cost%", lapisCost);
		economyCodes.put("%money_name%", MinigameUtils.getMoneyName());
		economyCodes.put("%money_sign%", MinigameUtils.getMoneySign());
		economyCodes.put("%player_economy%", MinigameUtils.format(playerMoney));
		economyCodes.put("%economy_cost%", MinigameUtils.format(economyCost));
		if (lvlCost > 0 && lvlCost > player.getLevel()) codes.put("%level%", "\n" + Chatable.get().getMessage(lvlCodes, "minigame.not_enough_levels"));
		else
			codes.put("%level%", "");
		if (lapisCost > 0 && lapisCost > lapis) codes.put("%lapis%", "\n" + Chatable.get().getMessage(lapisCodes, "minigame.not_enough_lapis"));
		else
			codes.put("%lapis%", "");
		if (economyCost > 0 && economyCost > playerMoney) codes.put("%economy%", "\n" + Chatable.get().getMessage(economyCodes, "minigame.not_enough_money"));
		else
			codes.put("%economy%", "");

		Chatable.get().sendMessage(player, Chatable.get().getMessage(codes, "minigame.cannot_get_enchantment"));
	}

}
