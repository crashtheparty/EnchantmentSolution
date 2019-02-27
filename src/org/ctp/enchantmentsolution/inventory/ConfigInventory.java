package org.ctp.enchantmentsolution.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.Anvil_GUI_NMS;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.config.YamlChild;
import org.ctp.enchantmentsolution.utils.config.YamlConfigBackup;
import org.ctp.enchantmentsolution.utils.save.ConfigFiles;

public class ConfigInventory implements InventoryData{

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
	
	public ConfigInventory(Player player) {
		this.player = player;
		this.screen = Screen.LIST_FILES;
		
		change();
	}
	
	public void change() {
		ConfigFiles files = EnchantmentSolution.getConfigFiles();
		isChanged.put(files.getDefaultConfig(), EnchantmentSolution.getDb().isConfigDifferent(files.getDefaultConfig()));
		isChanged.put(files.getFishingConfig(), EnchantmentSolution.getDb().isConfigDifferent(files.getFishingConfig()));
		isChanged.put(files.getLanguageFile(), EnchantmentSolution.getDb().isConfigDifferent(files.getLanguageFile()));
		isChanged.put(files.getEnchantmentConfig(), EnchantmentSolution.getDb().isConfigDifferent(files.getEnchantmentConfig()));
		isChanged.put(files.getEnchantmentAdvancedConfig(), EnchantmentSolution.getDb().isConfigDifferent(files.getEnchantmentAdvancedConfig()));

		hasChanged = isChanged.containsValue(true);
	}
	
	public void change(YamlConfigBackup config) {
		isChanged.put(config, EnchantmentSolution.getDb().isConfigDifferent(config));
		
		hasChanged = isChanged.containsValue(true);
	}
	
	public void revert() {
		ChatUtils.sendMessage(player, "Reverting changes made in the config UI.");
		
		EnchantmentSolution.getConfigFiles().revert();
		
		change();
		listFiles();
	}
	
	public void saveAll() {
		ChatUtils.sendMessage(player, "Saving changes made in the config UI.");
		
		EnchantmentSolution.getConfigFiles().save();
		
		change();
		listFiles();
	}
	
	public void setInventory() {
		setInventory(screen);
	}

	public void setInventory(Screen screen) {
		switch(screen) {
		case LIST_FILES:
			this.screen = screen;
			listFiles();
		default:
		}
	}
	
	public void listFiles() {
		screen = Screen.LIST_FILES;
		
		Inventory inv = Bukkit.createInventory(null, 27, "List Files");
		
		ItemStack configFile = new ItemStack(Material.COMMAND_BLOCK);
		ItemMeta configFileMeta = configFile.getItemMeta();
		configFileMeta.setDisplayName(ChatColor.GOLD + EnchantmentSolution.getConfigFiles().getDefaultConfig().getFileName());
		configFile.setItemMeta(configFileMeta);
		inv.setItem(2, configFile);
		
		ItemStack fishingFile = new ItemStack(Material.FISHING_ROD);
		ItemMeta fishingFileMeta = fishingFile.getItemMeta();
		fishingFileMeta.setDisplayName(ChatColor.GOLD + EnchantmentSolution.getConfigFiles().getFishingConfig().getFileName());
		fishingFile.setItemMeta(fishingFileMeta);
		inv.setItem(3, fishingFile);
		
		ItemStack languageFile = new ItemStack(Material.BOOK);
		ItemMeta languageFileMeta = languageFile.getItemMeta();
		languageFileMeta.setDisplayName(ChatColor.GOLD + EnchantmentSolution.getConfigFiles().getLanguageFile().getFileName());
		languageFile.setItemMeta(languageFileMeta);
		inv.setItem(4, languageFile);
		
		ItemStack enchantmentFile = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta enchantmentFileMeta = enchantmentFile.getItemMeta();
		enchantmentFileMeta.setDisplayName(ChatColor.GOLD + EnchantmentSolution.getConfigFiles().getEnchantmentConfig().getFileName());
		enchantmentFile.setItemMeta(enchantmentFileMeta);
		inv.setItem(5, enchantmentFile);
		
		ItemStack enchantmentFileAdvanced = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
		ItemMeta enchantmentFileAdvancedMeta = enchantmentFileAdvanced.getItemMeta();
		enchantmentFileAdvancedMeta.setDisplayName(ChatColor.GOLD + EnchantmentSolution.getConfigFiles().getEnchantmentAdvancedConfig().getFileName());
		enchantmentFileAdvanced.setItemMeta(enchantmentFileAdvancedMeta);
		inv.setItem(6, enchantmentFileAdvanced);
		
		ItemStack save = null;
		ItemStack revert = null;
		
		if(hasChanged) {
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
		
		inventory = inv;
		player.openInventory(inv);
	}
	
	public void listConfigDetails(YamlConfigBackup config) {
		listConfigDetails(config, null, 1);
	}
	
	public void listConfigDetails(YamlConfigBackup config, String level) {
		listConfigDetails(config, level, 1);
	}
	
	public void listConfigDetails(YamlConfigBackup config, String level, int page) {
		this.setPage(page);
		screen = Screen.LIST_DETAILS;
		this.config = config;
		this.setLevel(level);
		
		List<String> keys = config.getLevelEntryKeys(level);
		
		if(PAGING * (page - 1) >= keys.size() && page != 1) {
			listConfigDetails(config, level, page - 1);
			return;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, "List Config Details");
		
		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if(keys.size() <= index) break;
			String key = keys.get(index);
			
			ItemStack keyItem = new ItemStack(Material.PAPER);
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "Config Value");
			List<String> names = new ArrayList<String>();
			String leftClick = null;
			if(config.getType(key) == "boolean") {
				leftClick = ChatColor.WHITE + "Left click to toggle boolean.";
			} else if(config.getType(key) == "enum") {
				leftClick = ChatColor.WHITE + "Left click to edit enum.";
			} else {
				leftClick = ChatColor.WHITE + "Left click to edit with anvil.";
			}
			names.addAll(Arrays.asList(ChatColor.GRAY + "Path: " + ChatColor.WHITE + key, 
					ChatColor.GRAY + "Type: " + ChatColor.WHITE + config.getType(key), ChatColor.GRAY + "Value: " + ChatColor.WHITE + config.getStringValue(key),
					leftClick));
			String rightClick = config.getType(key) == "string" ? ChatColor.WHITE + "Right click to edit in chat." : "";
			if(rightClick != "") {
				names.add(rightClick);
			}
			keyItemMeta.setLore(names);
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}
		
		ItemStack revert = new ItemStack(Material.FIREWORK_STAR);
		ItemMeta revertMeta = revert.getItemMeta();
		revertMeta.setDisplayName(ChatColor.GOLD + "Revert to Backup");
		revert.setItemMeta(revertMeta);
		inv.setItem(50, revert);
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatColor.GOLD + "Go Back");
		goBack.setItemMeta(goBackMeta);
		inv.setItem(48, goBack);
		
		if(keys.size() > PAGING * page) {
			ItemStack nextPage = new ItemStack(Material.ARROW);
			ItemMeta nextPageMeta = nextPage.getItemMeta();
			nextPageMeta.setDisplayName(ChatColor.BLUE + "Next Page");
			nextPage.setItemMeta(nextPageMeta);
			inv.setItem(53, nextPage);
		}
		if(page != 1) {
			ItemStack prevPage = new ItemStack(Material.ARROW);
			ItemMeta prevPageMeta = prevPage.getItemMeta();
			prevPageMeta.setDisplayName(ChatColor.BLUE + "Previous Page");
			prevPage.setItemMeta(prevPageMeta);
			inv.setItem(45, prevPage);
		}
		
		inventory = inv;
		player.openInventory(inv);
	}
	
	public void listDetails(YamlConfigBackup config, String level, String type, int page) {
		this.setPage(page);
		screen = Screen.LIST_EDIT;
		this.config = config;
		this.setLevel(level);
		this.type = type;
		
		List<String> keys = config.getStringListCombined(level);
		
		if(keys == null) {
			return;
		}
		
		if(PAGING * (page - 1) >= keys.size() && page != 1) {
			listDetails(config, level, type, page - 1);
			return;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, "Config List Details");
		
		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if(keys.size() <= index) break;
			String key = keys.get(index);
			
			ItemStack keyItem = new ItemStack(Material.PAPER);
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "List Value");
			keyItemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Value: " + ChatColor.WHITE + key, 
					ChatColor.GRAY + "Left Click to Delete"));
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}
		
		ItemStack add = new ItemStack(Material.NAME_TAG);
		ItemMeta addMeta = add.getItemMeta();
		addMeta.setDisplayName(ChatColor.GOLD + "Add New Value");
		addMeta.setLore(Arrays.asList(ChatColor.WHITE + "Left Click to Open Anvil", ChatColor.WHITE + "Right Click to Open Chat"));
		add.setItemMeta(addMeta);
		inv.setItem(50, add);
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatColor.GOLD + "Go Back");
		goBack.setItemMeta(goBackMeta);
		inv.setItem(48, goBack);
		
		if(keys.size() > PAGING * page) {
			ItemStack nextPage = new ItemStack(Material.ARROW);
			ItemMeta nextPageMeta = nextPage.getItemMeta();
			nextPageMeta.setDisplayName(ChatColor.BLUE + "Next Page");
			nextPage.setItemMeta(nextPageMeta);
			inv.setItem(53, nextPage);
		}
		if(page != 1) {
			ItemStack prevPage = new ItemStack(Material.ARROW);
			ItemMeta prevPageMeta = prevPage.getItemMeta();
			prevPageMeta.setDisplayName(ChatColor.BLUE + "Previous Page");
			prevPage.setItemMeta(prevPageMeta);
			inv.setItem(45, prevPage);
		}
		
		inventory = inv;
		player.openInventory(inv);
	}
	
	public void listEnumDetails(YamlConfigBackup config, String level, String type, int page) {
		this.setPage(page);
		screen = Screen.LIST_ENUM;
		this.config = config;
		this.setLevel(level);
		this.type = type;
		
		List<String> enums = config.getEnums(level);
		if(enums == null) {
			return;
		}
		
		if(PAGING * (page - 1) >= enums.size() && page != 1) {
			listEnumDetails(config, level, type, page - 1);
			return;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, "Config Enum Details");
		
		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if(enums.size() <= index) break;
			String key = enums.get(index);
			
			ItemStack keyItem = new ItemStack(Material.GOLDEN_APPLE);
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Value: " + ChatColor.WHITE + key);
			if(key.equals(config.getStringValue(level))) {
				keyItem.setType(Material.ENCHANTED_GOLDEN_APPLE);
				lore.add(ChatColor.GRAY + "Selected: " + ChatColor.WHITE + "true");
			} else {
				lore.add(ChatColor.GRAY + "Selected: " + ChatColor.WHITE + "false");
			}
			lore.add(ChatColor.WHITE + "Left Click to Select");
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "Enum Value");
			keyItemMeta.setLore(lore);
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatColor.GOLD + "Go Back");
		goBack.setItemMeta(goBackMeta);
		inv.setItem(49, goBack);
		
		if(enums.size() > PAGING * page) {
			ItemStack nextPage = new ItemStack(Material.ARROW);
			ItemMeta nextPageMeta = nextPage.getItemMeta();
			nextPageMeta.setDisplayName(ChatColor.BLUE + "Next Page");
			nextPage.setItemMeta(nextPageMeta);
			inv.setItem(53, nextPage);
		}
		if(page != 1) {
			ItemStack prevPage = new ItemStack(Material.ARROW);
			ItemMeta prevPageMeta = prevPage.getItemMeta();
			prevPageMeta.setDisplayName(ChatColor.BLUE + "Previous Page");
			prevPage.setItemMeta(prevPageMeta);
			inv.setItem(45, prevPage);
		}
		
		inventory = inv;
		player.openInventory(inv);
	}
	
	public void listEnumListShow(YamlConfigBackup config, String level, String type, int page) {
		this.setPage(page);
		screen = Screen.LIST_ENUM_LIST_SHOW;
		this.config = config;
		this.setLevel(level);
		this.type = type;
		
		List<String> enums = config.getStringListCombined(level);
		if(enums == null) {
			return;
		}
		
		if(PAGING * (page - 1) >= enums.size() && page != 1) {
			listEnumListShow(config, level, type, page - 1);
			return;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, "Config Enum List Details");
		
		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if(enums.size() <= index) break;
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
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatColor.GOLD + "Go Back");
		goBack.setItemMeta(goBackMeta);
		inv.setItem(48, goBack);
		
		if(enums.size() > PAGING * page) {
			ItemStack nextPage = new ItemStack(Material.ARROW);
			ItemMeta nextPageMeta = nextPage.getItemMeta();
			nextPageMeta.setDisplayName(ChatColor.BLUE + "Next Page");
			nextPage.setItemMeta(nextPageMeta);
			inv.setItem(53, nextPage);
		}
		if(page != 1) {
			ItemStack prevPage = new ItemStack(Material.ARROW);
			ItemMeta prevPageMeta = prevPage.getItemMeta();
			prevPageMeta.setDisplayName(ChatColor.BLUE + "Previous Page");
			prevPage.setItemMeta(prevPageMeta);
			inv.setItem(45, prevPage);
		}
		
		ItemStack add = new ItemStack(Material.NAME_TAG);
		ItemMeta addMeta = add.getItemMeta();
		addMeta.setDisplayName(ChatColor.GOLD + "Add New Enum Value");
		add.setItemMeta(addMeta);
		inv.setItem(50, add);
		
		inventory = inv;
		player.openInventory(inv);
	}
	
	public void listEnumListEdit(YamlConfigBackup config, String level, String type, int page) {
		this.setPage(page);
		screen = Screen.LIST_ENUM_LIST_EDIT;
		this.config = config;
		this.setLevel(level);
		this.type = type;
		
		List<String> enums = config.getEnums(level);
		if(enums == null) {
			return;
		}
		
		for(String s : config.getStringListCombined(level)) {
			enums.remove(s);
		}
		
		if(PAGING * (page - 1) >= enums.size() && page != 1) {
			listEnumListEdit(config, level, type, page - 1);
			return;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, "Config Enum List Details");
		
		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if(enums.size() <= index) break;
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
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatColor.GOLD + "Go Back");
		goBack.setItemMeta(goBackMeta);
		inv.setItem(49, goBack);
		
		if(enums.size() > PAGING * page) {
			ItemStack nextPage = new ItemStack(Material.ARROW);
			ItemMeta nextPageMeta = nextPage.getItemMeta();
			nextPageMeta.setDisplayName(ChatColor.BLUE + "Next Page");
			nextPage.setItemMeta(nextPageMeta);
			inv.setItem(53, nextPage);
		}
		if(page != 1) {
			ItemStack prevPage = new ItemStack(Material.ARROW);
			ItemMeta prevPageMeta = prevPage.getItemMeta();
			prevPageMeta.setDisplayName(ChatColor.BLUE + "Previous Page");
			prevPage.setItemMeta(prevPageMeta);
			inv.setItem(45, prevPage);
		}
		
		inventory = inv;
		player.openInventory(inv);
	}
	
	public void listBackup(YamlConfigBackup config, int page) {
		this.setPage(page);
		screen = Screen.LIST_BACKUP;
		this.config = config;
		this.backup = null;
		this.level = null;
		
		List<Integer> backups = EnchantmentSolution.getDb().getBackups(config);
		if(backups == null) {
			return;
		}
		
		if(PAGING * (page - 1) >= backups.size() && page != 1) {
			listEnumDetails(config, level, type, page - 1);
			return;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, "List Config Backups");
		
		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if(backups.size() <= index) break;
			int key = backups.get(index);
			
			ItemStack keyItem = new ItemStack(Material.FIREWORK_STAR);
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "Backup Value");
			keyItemMeta.setLore(Arrays.asList(ChatColor.WHITE + "Num: " + ChatColor.GRAY + key));
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatColor.GOLD + "Go Back");
		goBack.setItemMeta(goBackMeta);
		inv.setItem(49, goBack);
		
		if(backups.size() > PAGING * page) {
			ItemStack nextPage = new ItemStack(Material.ARROW);
			ItemMeta nextPageMeta = nextPage.getItemMeta();
			nextPageMeta.setDisplayName(ChatColor.BLUE + "Next Page");
			nextPage.setItemMeta(nextPageMeta);
			inv.setItem(53, nextPage);
		}
		if(page != 1) {
			ItemStack prevPage = new ItemStack(Material.ARROW);
			ItemMeta prevPageMeta = prevPage.getItemMeta();
			prevPageMeta.setDisplayName(ChatColor.BLUE + "Previous Page");
			prevPage.setItemMeta(prevPageMeta);
			inv.setItem(45, prevPage);
		}
		
		inventory = inv;
		player.openInventory(inv);
	}
	
	public void listBackupConfigDetails(YamlConfigBackup config, YamlConfigBackup backup, String level, int page) {
		this.setPage(page);
		screen = Screen.LIST_BACKUP_DETAILS;
		this.config = config;
		this.setBackup(backup);
		this.setLevel(level);
		
		List<String> keys = backup.getLevelEntryKeys(level);
		
		if(PAGING * (page - 1) >= keys.size() && page != 1) {
			listBackupConfigDetails(config, backup, level, page - 1);
			return;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, "List Backup Config Details");
		
		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if(keys.size() <= index) break;
			String key = keys.get(index);
			
			ItemStack keyItem = new ItemStack(Material.PAPER);
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "Config Value");
			List<String> names = new ArrayList<String>();
			names.addAll(Arrays.asList(ChatColor.GRAY + "Path: " + ChatColor.WHITE + key, 
					ChatColor.GRAY + "Type: " + ChatColor.WHITE + backup.getType(key), ChatColor.GRAY + "Value: " + ChatColor.WHITE + backup.getStringValue(key)));
			keyItemMeta.setLore(names);
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}
		
		ItemStack revert = new ItemStack(Material.FIREWORK_STAR);
		ItemMeta revertMeta = revert.getItemMeta();
		revertMeta.setDisplayName(ChatColor.GOLD + "Revert to This Backup");
		revert.setItemMeta(revertMeta);
		inv.setItem(50, revert);
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatColor.GOLD + "Go Back");
		goBack.setItemMeta(goBackMeta);
		inv.setItem(48, goBack);
		
		if(keys.size() > PAGING * page) {
			ItemStack nextPage = new ItemStack(Material.ARROW);
			ItemMeta nextPageMeta = nextPage.getItemMeta();
			nextPageMeta.setDisplayName(ChatColor.BLUE + "Next Page");
			nextPage.setItemMeta(nextPageMeta);
			inv.setItem(53, nextPage);
		}
		if(page != 1) {
			ItemStack prevPage = new ItemStack(Material.ARROW);
			ItemMeta prevPageMeta = prevPage.getItemMeta();
			prevPageMeta.setDisplayName(ChatColor.BLUE + "Previous Page");
			prevPage.setItemMeta(prevPageMeta);
			inv.setItem(45, prevPage);
		}
		
		inventory = inv;
		player.openInventory(inv);
	}
	
	public void listBackupDetails(YamlConfigBackup config, YamlConfigBackup backup, String level, String type, int page) {
		this.setPage(page);
		screen = Screen.LIST_BACKUP_LIST;
		this.config = config;
		this.setBackup(backup);
		this.setLevel(level);
		this.type = type;
		
		List<String> keys = backup.getStringListCombined(level);
		
		if(keys == null) {
			return;
		}
		
		if(PAGING * (page - 1) >= keys.size() && page != 1) {
			listBackupDetails(config, backup, level, type, page - 1);
			return;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, "Backup Config List Details");
		
		for(int i = 0; i < PAGING; i++) {
			int index = i + PAGING * (page - 1);
			if(keys.size() <= index) break;
			String key = keys.get(index);
			
			ItemStack keyItem = new ItemStack(Material.PAPER);
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatColor.GOLD + "List Value");
			keyItemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Value: " + ChatColor.WHITE + key));
			keyItem.setItemMeta(keyItemMeta);
			inv.setItem(i, keyItem);
		}
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatColor.GOLD + "Go Back");
		goBack.setItemMeta(goBackMeta);
		inv.setItem(49, goBack);
		
		if(keys.size() > PAGING * page) {
			ItemStack nextPage = new ItemStack(Material.ARROW);
			ItemMeta nextPageMeta = nextPage.getItemMeta();
			nextPageMeta.setDisplayName(ChatColor.BLUE + "Next Page");
			nextPage.setItemMeta(nextPageMeta);
			inv.setItem(53, nextPage);
		}
		if(page != 1) {
			ItemStack prevPage = new ItemStack(Material.ARROW);
			ItemMeta prevPageMeta = prevPage.getItemMeta();
			prevPageMeta.setDisplayName(ChatColor.BLUE + "Previous Page");
			prevPage.setItemMeta(prevPageMeta);
			inv.setItem(45, prevPage);
		}
		
		inventory = inv;
		player.openInventory(inv);
	}
	
	public void removeFromList(int num) {
		List<String> keys = config.getStringListCombined(level);
		if(keys == null) {
			return;
		}
		
		num += (PAGING * (page - 1));
		
		keys.remove(num);
		setPath(level, keys);
	}
	
	public void addToList(String value) {
		List<String> keys = config.getStringListCombined(level);
		if(keys == null) {
			return;
		}
		keys.add(value);
		setPath(level, keys);
	}
	
	public void openAnvil(String path, String type) {
		this.level = path;
		this.type = type;
		Anvil_GUI_NMS.createAnvil(player, this);
	}
	
	public void openChat(String path, String type) {
		this.level = path;
		this.type = type;
		
		setChat(true);
		ChatUtils.sendMessage(player, "Enter the string message into the chat.");
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
		if(!external) {
			player.closeInventory();
		}
		EnchantmentSolution.removeInventory(this);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(List<ItemStack> items) {
	}
	
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
		this.level = level;
	}

	public int getPage() {
		return page;
	}

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

	public enum Screen{
		LIST_FILES(), LIST_DETAILS(), LIST_EDIT(), LIST_ENUM(), LIST_BACKUP(), LIST_BACKUP_DETAILS(), LIST_BACKUP_LIST(), LIST_ENUM_LIST_SHOW(), LIST_ENUM_LIST_EDIT();
	}

	@Override
	public void setItemName(String name) {
		switch(type) {
		case "integer":
			int value = 0;
			try {
				value = Integer.parseInt(name);
				ChatUtils.sendMessage(player, "Set path " + level + " to " + value + ".");
				setPath(level, value);
			} catch(Exception e) {
				ChatUtils.sendMessage(player, "Entered value not an integer.");
			}
			break;
		case "double":
			double d = 0;
			try {
				d = Double.parseDouble(name);
				ChatUtils.sendMessage(player, "Set path " + level + " to " + d + ".");
				setPath(level, d);
			} catch(Exception e) {
				ChatUtils.sendMessage(player, "Entered value not an integer.");
			}
			break;
		case "enum":
		case "string":
			ChatUtils.sendMessage(player, "Set path " + level + " to " + name + ".");
			setPath(level, name);
			break;
		case "list":
		case "enum_list":
			ChatUtils.sendMessage(player, "Added " + name + " to path " + level + ".");
			addToList(name);
			break;
		}
	}
	
	public void reopenFromAnvil(boolean limitLevel) {
		if(type.equals("list")) {
			listDetails(getConfig(), level, type, page);
		} else if(level == null) {
			listConfigDetails(getConfig(), null);
		} else if(level.indexOf(".") > -1) {
			if(limitLevel) {
				listConfigDetails(getConfig(), level.substring(0, level.lastIndexOf(".")));
			} else {
				listConfigDetails(getConfig(), level);
			}
		} else {
			if(limitLevel) {
				listConfigDetails(getConfig(), null);
			} else {
				listConfigDetails(getConfig(), level);
			}
		}
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

}
