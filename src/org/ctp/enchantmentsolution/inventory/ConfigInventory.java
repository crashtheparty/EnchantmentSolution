package org.ctp.enchantmentsolution.inventory;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.crashapi.config.yaml.YamlChild;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.inventory.Pageable;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.Anvil_GUI_NMS;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.DBUtils;

public class ConfigInventory implements InventoryData, Pageable {

	private Player player;
	private Inventory inventory;
	private Screen screen;
	private YamlConfigBackup config, backup;
	private YamlChild child;
	private HashMap<YamlConfigBackup, Boolean> isChanged = new HashMap<YamlConfigBackup, Boolean>();
	private boolean hasChanged = false;
	private final int PAGING = 36;
	private int page = 0;
	private String level = null, type = null;
	private boolean chat = false;
	private boolean opening;
	private Configurations configurations;

	public ConfigInventory(Player player) {
		this.player = player;
		screen = Screen.LIST_FILES;

		configurations = EnchantmentSolution.getPlugin().getConfigurations();
		change();
	}

	private void change() {
		isChanged.putAll(DBUtils.getDifferent(configurations.getConfig()));
		isChanged.putAll(DBUtils.getDifferent(configurations.getFishing()));
		isChanged.putAll(DBUtils.getDifferent(configurations.getLanguage()));
		isChanged.putAll(DBUtils.getDifferent(configurations.getEnchantments()));
		isChanged.putAll(DBUtils.getDifferent(configurations.getAdvancements()));
		isChanged.putAll(DBUtils.getDifferent(configurations.getRPG()));
		isChanged.putAll(DBUtils.getDifferent(configurations.getHardMode()));
		isChanged.putAll(DBUtils.getDifferent(configurations.getMinigames()));

		hasChanged = isChanged.containsValue(true);
	}

	private void change(YamlConfigBackup config) {
		isChanged.put(config, EnchantmentSolution.getPlugin().getDb().isConfigDifferent(config));

		hasChanged = isChanged.containsValue(true);
	}

	public void revert() {
		Chatable.get().sendMessage(player, "Reverting changes made in the config UI.");

		configurations.revert();

		change();
		listFiles();
	}

	public void saveAll() {
		Chatable.get().sendMessage(player, "Saving changes made in the config UI.");

		configurations.save();

		change();
		listFiles();
	}

	@Override
	public void setInventory() {
		setInventory(screen);
	}

	public void setInventory(Screen screen) {
		switch (screen) {
			case LIST_FILES:
				this.screen = screen;
				listFiles();
			default:
		}
	}

	public void listFiles() {
		screen = Screen.LIST_FILES;

		Inventory inv = Bukkit.createInventory(null, 27, "List Files");
		inv = open(inv);

		ItemStack configFile = new ItemStack(Material.COMMAND_BLOCK);
		ItemMeta configFileMeta = configFile.getItemMeta();
		configFileMeta.setDisplayName(ChatColor.GOLD + configurations.getConfig().getConfig().getFileName());
		configFile.setItemMeta(configFileMeta);
		inv.setItem(2, configFile);

		ItemStack fishingFile = new ItemStack(Material.FISHING_ROD);
		ItemMeta fishingFileMeta = fishingFile.getItemMeta();
		fishingFileMeta.setDisplayName(ChatColor.GOLD + configurations.getFishing().getConfig().getFileName());
		fishingFile.setItemMeta(fishingFileMeta);
		inv.setItem(3, fishingFile);

		ItemStack advancementsFile = new ItemStack(Material.KNOWLEDGE_BOOK);
		ItemMeta advancementsFileMeta = advancementsFile.getItemMeta();
		advancementsFileMeta.setDisplayName(ChatColor.GOLD + configurations.getAdvancements().getConfig().getFileName());
		advancementsFile.setItemMeta(advancementsFileMeta);
		inv.setItem(4, advancementsFile);

		ItemStack languageFile = new ItemStack(Material.BOOK);
		ItemMeta languageFileMeta = languageFile.getItemMeta();
		languageFileMeta.setDisplayName(ChatColor.GOLD + configurations.getLanguage().getConfig().getFileName());
		languageFile.setItemMeta(languageFileMeta);
		inv.setItem(5, languageFile);

		ItemStack enchantmentFile = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta enchantmentFileMeta = enchantmentFile.getItemMeta();
		enchantmentFileMeta.setDisplayName(ChatColor.GOLD + configurations.getEnchantments().getConfig().getFileName());
		enchantmentFile.setItemMeta(enchantmentFileMeta);
		inv.setItem(6, enchantmentFile);

		ItemStack minigamesFile = new ItemStack(Material.GOLDEN_SHOVEL);
		ItemMeta minigamesFileMeta = minigamesFile.getItemMeta();
		minigamesFileMeta.setDisplayName(ChatColor.GOLD + configurations.getMinigames().getConfig().getFileName());
		minigamesFile.setItemMeta(minigamesFileMeta);
		inv.setItem(12, minigamesFile);

		ItemStack rpgFile = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta rpgFileMeta = rpgFile.getItemMeta();
		rpgFileMeta.setDisplayName(ChatColor.GOLD + configurations.getRPG().getConfig().getFileName());
		rpgFile.setItemMeta(rpgFileMeta);
		inv.setItem(13, rpgFile);

		ItemStack hardModeFile = new ItemStack(Material.SPAWNER);
		ItemMeta hardModeFileMeta = hardModeFile.getItemMeta();
		hardModeFileMeta.setDisplayName(ChatColor.GOLD + configurations.getHardMode().getConfig().getFileName());
		hardModeFile.setItemMeta(hardModeFileMeta);
		inv.setItem(14, hardModeFile);

		ItemStack save = null;
		ItemStack revert = null;

		if (hasChanged) {
			save = new ItemStack(Material.NAME_TAG);
			revert = new ItemStack(Material.FIREWORK_STAR);
		} else {
			save = new ItemStack(Material.BARRIER);
			revert = new ItemStack(Material.BARRIER);
		}

		ItemMeta saveMeta = save.getItemMeta();
		saveMeta.setDisplayName(ChatColor.GOLD + "Save Changes");
		save.setItemMeta(saveMeta);
		inv.setItem(21, save);

		ItemMeta revertMeta = revert.getItemMeta();
		revertMeta.setDisplayName(ChatColor.GOLD + "Revert Changes");
		revert.setItemMeta(revertMeta);
		inv.setItem(23, revert);
	}

	public void listConfigDetails(YamlConfigBackup config, YamlConfigBackup backup) {
		listConfigDetails(config, backup, null, 1);
	}

	public void listConfigDetails(YamlConfigBackup config, YamlConfigBackup backup, String level) {
		level = ChatColor.stripColor(level);
		listConfigDetails(config, backup, level, 1);
	}

	public void listConfigDetails(YamlConfigBackup config, YamlConfigBackup backup, String level, int page) {
		level = ChatColor.stripColor(level);
		setPage(page);
		screen = Screen.LIST_DETAILS;
		this.config = config;
		setBackup(backup);
		setLevel(level);
		String title = "List Config Details";
		String revertTitle = "Revert to Backup";
		List<String> keys = config.getLevelEntryKeys(level);

		if (backup != null) {
			screen = Screen.LIST_BACKUP_DETAILS;
			keys = backup.getLevelEntryKeys(level);
			title = "List Config Backup Details";
			revertTitle = "Revert to This Backup";
		}

		if (PAGING * (page - 1) >= keys.size() && page != 1) {
			listConfigDetails(config, backup, level, page - 1);
			return;
		}

		Inventory inv = Bukkit.createInventory(null, 54, title);
		inv = open(inv);

		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if (keys.size() <= index) break;
			String key = keys.get(index);

			ItemStack keyItem = new ItemStack(Material.PAPER);
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "Config Value");
			List<String> names = new ArrayList<String>();
			if (backup != null) names.addAll(Arrays.asList(ChatColor.GRAY + "Path: " + ChatColor.WHITE + key, ChatColor.GRAY + "Type: " + ChatColor.WHITE + backup.getType(key), ChatColor.GRAY + "Value: " + ChatColor.WHITE + backup.getStringValue(key)));
			else {
				String leftClick = null;
				if (config.getType(key) == "boolean") leftClick = ChatColor.WHITE + "Left click to toggle boolean.";
				else if (config.getType(key) == "enum") leftClick = ChatColor.WHITE + "Left click to edit enum.";
				else
					leftClick = ChatColor.WHITE + "Left click to edit with anvil.";
				names.addAll(Arrays.asList(ChatColor.GRAY + "Path: " + ChatColor.WHITE + key, ChatColor.GRAY + "Type: " + ChatColor.WHITE + config.getType(key), ChatColor.GRAY + "Value: " + ChatColor.WHITE + config.getStringValue(key), leftClick));
				String rightClick = config.getType(key) == "string" ? ChatColor.WHITE + "Right click to edit in chat." : "";
				if (rightClick != "") names.add(rightClick);
			}
			keyItemMeta.setLore(names);
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}

		ItemStack revert = new ItemStack(Material.FIREWORK_STAR);
		ItemMeta revertMeta = revert.getItemMeta();
		revertMeta.setDisplayName(ChatColor.GOLD + revertTitle);
		revert.setItemMeta(revertMeta);
		inv.setItem(50, revert);

		inv.setItem(48, goBack());

		if (keys.size() > PAGING * page) inv.setItem(53, nextPage());
		if (page != 1) inv.setItem(45, previousPage());
	}

	public void listDetails(YamlConfigBackup config, YamlConfigBackup backup, String level, String type, int page) {
		level = ChatColor.stripColor(level);
		setPage(page);
		screen = Screen.LIST_EDIT;
		this.config = config;
		this.backup = backup;
		setLevel(level);
		this.type = type;
		String title = "Config List Details";

		List<String> keys = config.getStringListCombined(level);

		if (backup != null) {
			screen = Screen.LIST_BACKUP_LIST;
			keys = backup.getStringListCombined(level);
			title = "Backup Config List Details";
		}

		if (keys == null) return;

		if (PAGING * (page - 1) >= keys.size() && page != 1) {
			listDetails(config, backup, level, type, page - 1);
			return;
		}

		Inventory inv = Bukkit.createInventory(null, 54, title);
		inv = open(inv);

		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if (keys.size() <= index) break;
			String key = keys.get(index);

			ItemStack keyItem = new ItemStack(Material.PAPER);
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "List Value");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Value: " + ChatColor.WHITE + key);
			if (backup == null) lore.add(ChatColor.GRAY + "Left Click to Delete");
			keyItemMeta.setLore(lore);
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}

		int goBackSlot = 49;
		if (backup == null) {
			goBackSlot = 48;
			ItemStack add = new ItemStack(Material.NAME_TAG);
			ItemMeta addMeta = add.getItemMeta();
			addMeta.setDisplayName(ChatColor.GOLD + "Add New Value");
			addMeta.setLore(Arrays.asList(ChatColor.WHITE + "Left Click to Open Anvil", ChatColor.WHITE + "Right Click to Open Chat"));
			add.setItemMeta(addMeta);
			inv.setItem(50, add);
		}

		inv.setItem(goBackSlot, goBack());

		if (keys.size() > PAGING * page) inv.setItem(53, nextPage());
		if (page != 1) inv.setItem(45, previousPage());
	}

	public void listEnumDetails(YamlConfigBackup config, String level, String type, int page) {
		level = ChatColor.stripColor(level);
		setPage(page);
		screen = Screen.LIST_ENUM;
		this.config = config;
		setLevel(level);
		this.type = type;

		List<String> enums = config.getEnums(level);
		if (enums == null) return;

		if (PAGING * (page - 1) >= enums.size() && page != 1) {
			listEnumDetails(config, level, type, page - 1);
			return;
		}

		Inventory inv = Bukkit.createInventory(null, 54, "Config Enum Details");
		inv = open(inv);

		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if (enums.size() <= index) break;
			String key = enums.get(index);

			ItemStack keyItem = new ItemStack(Material.GOLDEN_APPLE);
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Value: " + ChatColor.WHITE + key);
			if (key.equals(config.getStringValue(level))) {
				keyItem.setType(Material.ENCHANTED_GOLDEN_APPLE);
				lore.add(ChatColor.GRAY + "Selected: " + ChatColor.WHITE + "true");
			} else
				lore.add(ChatColor.GRAY + "Selected: " + ChatColor.WHITE + "false");
			lore.add(ChatColor.WHITE + "Left Click to Select");
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "Enum Value");
			keyItemMeta.setLore(lore);
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}

		inv.setItem(49, goBack());

		if (enums.size() > PAGING * page) inv.setItem(53, nextPage());
		if (page != 1) inv.setItem(45, previousPage());
	}

	public void listEnumListShow(YamlConfigBackup config, String level, String type, int page) {
		level = ChatColor.stripColor(level);
		setPage(page);
		screen = Screen.LIST_ENUM_LIST_SHOW;
		this.config = config;
		setLevel(level);
		this.type = type;

		List<String> enums = config.getStringListCombined(level);
		if (enums == null) return;

		if (PAGING * (page - 1) >= enums.size() && page != 1) {
			listEnumListShow(config, level, type, page - 1);
			return;
		}

		Inventory inv = Bukkit.createInventory(null, 54, "Config Enum List Details");
		inv = open(inv);

		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if (enums.size() <= index) break;
			String key = enums.get(index);

			ItemStack keyItem = new ItemStack(Material.PAPER);
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Value: " + ChatColor.WHITE + key);
			lore.add(ChatColor.WHITE + "Left Click to Remove from List");
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "Enum Value");
			keyItemMeta.setLore(lore);
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}

		inv.setItem(48, goBack());

		if (enums.size() > PAGING * page) inv.setItem(53, nextPage());
		if (page != 1) inv.setItem(45, previousPage());

		ItemStack add = new ItemStack(Material.NAME_TAG);
		ItemMeta addMeta = add.getItemMeta();
		addMeta.setDisplayName(ChatColor.GOLD + "Add New Enum Value");
		add.setItemMeta(addMeta);
		inv.setItem(50, add);
	}

	public void listEnumListEdit(YamlConfigBackup config, String level, String type, int page) {
		level = ChatColor.stripColor(level);
		setPage(page);
		screen = Screen.LIST_ENUM_LIST_EDIT;
		this.config = config;
		setLevel(level);
		this.type = type;

		List<String> enums = new ArrayList<String>();
		if (config.getEnums(level) == null) return;
		for(String s: config.getEnums(level))
			if (!config.getStringListCombined(level).contains(s)) enums.add(s);

		if (PAGING * (page - 1) >= enums.size() && page != 1) {
			listEnumListEdit(config, level, type, page - 1);
			return;
		}

		Inventory inv = Bukkit.createInventory(null, 54, "Config Enum List Details");
		inv = open(inv);

		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if (enums.size() <= index) break;
			String key = enums.get(index);

			ItemStack keyItem = new ItemStack(Material.GOLDEN_APPLE);
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Value: " + ChatColor.WHITE + key);
			lore.add(ChatColor.WHITE + "Left Click to Add to List");
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "Enum Value");
			keyItemMeta.setLore(lore);
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}

		inv.setItem(49, goBack());

		if (enums.size() > PAGING * page) inv.setItem(53, nextPage());
		if (page != 1) inv.setItem(45, previousPage());
	}

	public void listBackup(YamlConfigBackup config, int page) {
		setPage(page);
		screen = Screen.LIST_BACKUP;
		this.config = config;
		backup = null;
		level = null;

		List<Integer> backups = EnchantmentSolution.getPlugin().getDb().getBackups(config);
		if (backups == null) return;

		if (PAGING * (page - 1) >= backups.size() && page != 1) {
			listEnumDetails(config, level, type, page - 1);
			return;
		}

		Inventory inv = Bukkit.createInventory(null, 54, "List Config Backups");
		inv = open(inv);

		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if (backups.size() <= index) break;
			int key = backups.get(index);

			ItemStack keyItem = new ItemStack(Material.FIREWORK_STAR);
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "Backup Value");
			keyItemMeta.setLore(Arrays.asList(ChatColor.WHITE + "Num: " + ChatColor.GRAY + key));
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}

		inv.setItem(49, goBack());

		if (backups.size() > PAGING * page) inv.setItem(53, nextPage());
		if (page != 1) inv.setItem(45, previousPage());
	}

	public void removeFromList(int num) {
		List<String> keys = config.getStringListCombined(level);
		if (keys == null) return;

		num += PAGING * (page - 1);

		keys.remove(num);
		setPath(level, keys);
	}

	public void addToList(String value) {
		List<String> keys = config.getStringListCombined(level);
		if (keys == null) return;
		keys.add(value);
		setPath(level, keys);
	}

	public void openAnvil(String path, String type) {
		level = path;
		this.type = type;
		Anvil_GUI_NMS.createAnvil(player, this);
	}

	public void openChat(String path, String type) {
		level = path;
		this.type = type;

		setChat(true);
		Chatable.get().sendMessage(player, "Enter the string message into the chat.");
		player.closeInventory();
	}

	public void setPath(String path, Object value) {
		config.setConfigPath(path, value);

		change(config);
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public Block getBlock() {
		return null;
	}

	@Override
	public void close(boolean external) {
		if (!external) player.closeInventory();
		EnchantmentSolution.getPlugin().removeInventory(this);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(List<ItemStack> items) {}

	public HashMap<String, Object> getCodes() {
		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%player%", player.getName());
		return codes;
	}

	public YamlChild getChild() {
		return child;
	}

	public void setChild(YamlChild child) {
		this.child = child;
	}

	public boolean isHasChanged() {
		return hasChanged;
	}

	public void setHasChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}

	public YamlConfigBackup getConfig() {
		return config;
	}

	public void setConfig(YamlConfigBackup config) {
		this.config = config;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = ChatColor.stripColor(level);
	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
	public void setPage(int page) {
		this.page = page;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public enum Screen {
		LIST_FILES(), LIST_DETAILS(), LIST_EDIT(), LIST_ENUM(), LIST_BACKUP(), LIST_BACKUP_DETAILS(),
		LIST_BACKUP_LIST(), LIST_ENUM_LIST_SHOW(), LIST_ENUM_LIST_EDIT();
	}

	@Override
	public void setItemName(String name) {
		switch (type) {
			case "integer":
				int value = 0;
				try {
					value = Integer.parseInt(name);
					Chatable.get().sendMessage(player, "Set path " + level + " to " + value + ".");
					setPath(level, value);
				} catch (Exception e) {
					Chatable.get().sendMessage(player, "Entered value not an integer.");
				}
				break;
			case "double":
				double d = 0;
				try {
					d = Double.parseDouble(name);
					Chatable.get().sendMessage(player, "Set path " + level + " to " + d + ".");
					setPath(level, d);
				} catch (Exception e) {
					Chatable.get().sendMessage(player, "Entered value not an integer.");
				}
				break;
			case "enum":
			case "string":
				Chatable.get().sendMessage(player, "Set path " + level + " to " + name + ".");
				setPath(level, name);
				break;
			case "list":
			case "enum_list":
				Chatable.get().sendMessage(player, "Added " + name + " to path " + level + ".");
				addToList(name);
				break;
		}
	}

	public void reopenFromAnvil(boolean limitLevel) {
		inventory = null;
		if (type.equals("list")) listDetails(getConfig(), null, level, type, page);
		else if (level == null) listConfigDetails(getConfig(), null);
		else if (level.indexOf(".") > -1) {
			if (limitLevel) listConfigDetails(getConfig(), null, level.substring(0, level.lastIndexOf(".")));
			else
				listConfigDetails(getConfig(), null, level);
		} else if (limitLevel) listConfigDetails(getConfig(), null, null);
		else
			listConfigDetails(getConfig(), null, level);
	}

	public boolean isChat() {
		return chat;
	}

	public void setChat(boolean chat) {
		this.chat = chat;
	}

	public YamlConfigBackup getBackup() {
		return backup;
	}

	public void setBackup(YamlConfigBackup backup) {
		this.backup = backup;
	}

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

	@Override
	public ChatUtils getChat() {
		return Chatable.get();
	}
}
