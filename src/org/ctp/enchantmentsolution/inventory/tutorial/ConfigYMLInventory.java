package org.ctp.enchantmentsolution.inventory.tutorial;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class ConfigYMLInventory implements InventoryData{

	private Player player;
	private Inventory inventory;
	private boolean opening;
	
	public ConfigYMLInventory(Player player) {
		this.player = player;
	}
	
	public void setInventory() {
		Inventory inv = Bukkit.createInventory(null, 54, ChatUtils.getMessage(getCodes(), "tutorial.config_yml_inventory.name"));
		inv = open(inv);
		
		// anvil.level_divisor, anvil.max_repair_level, anvil.default_use
		ItemStack anvil = new ItemStack(Material.ANVIL);
		ItemMeta anvilMeta = anvil.getItemMeta();
		anvilMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.config_yml_inventory.anvil.name"));
		anvilMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.config_yml_inventory.anvil.lore"));
		anvil.setItemMeta(anvilMeta);
		inv.setItem(0, anvil);
		
		// enchanting_table.enchanting_type
		ItemStack enchantingType = new ItemStack(Material.ENCHANTING_TABLE);
		ItemMeta enchantingTypeMeta = enchantingType.getItemMeta();
		enchantingTypeMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.config_yml_inventory.enchanting_type.name"));
		enchantingTypeMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.config_yml_inventory.enchanting_type.lore"));
		enchantingType.setItemMeta(enchantingTypeMeta);
		inv.setItem(1, enchantingType);
		
		// enchanting_table.lapis_in_table, enchanting_table.reset_enchantments_advanced, enchanting_table.use_enchanted_books, enchanting_table.decay
		ItemStack enchantingTable = new ItemStack(Material.ENCHANTING_TABLE);
		ItemMeta enchantingTableMeta = enchantingTable.getItemMeta();
		enchantingTableMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.config_yml_inventory.enchanting_table.name"));
		enchantingTableMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.config_yml_inventory.enchanting_table.lore"));
		enchantingTable.setItemMeta(enchantingTableMeta);
		inv.setItem(2, enchantingTable);
		
		// starter, language_file, reset_language, language
		ItemStack language = new ItemStack(Material.BOOK);
		ItemMeta languageMeta = language.getItemMeta();
		languageMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.config_yml_inventory.language.name"));
		languageMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.config_yml_inventory.language.lore"));
		language.setItemMeta(languageMeta);
		inv.setItem(3, language);
		
		// max_enchantments, disable_enchant_method, protection_conflicts, update_legacy_enchantments
		ItemStack enchantments = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta enchantmentsMeta = enchantments.getItemMeta();
		enchantmentsMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.config_yml_inventory.enchantments.name"));
		enchantmentsMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.config_yml_inventory.enchantments.lore"));
		enchantments.setItemMeta(enchantmentsMeta);
		inv.setItem(4, enchantments);
		
		// version.get_latest, version.get_experimental, use_comments
		ItemStack version = new ItemStack(Material.PAPER);
		ItemMeta versionMeta = version.getItemMeta();
		versionMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.config_yml_inventory.version.name"));
		versionMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.config_yml_inventory.version.lore"));
		version.setItemMeta(versionMeta);
		inv.setItem(5, version);
		
		// grindstone.take_enchantments, grindstone.set_repair_cost, grindstone.destroy_take_item
		ItemStack grindstone = new ItemStack(Material.SMOOTH_STONE);
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			grindstone.setType(Material.GRINDSTONE);
		}
		ItemMeta grindstoneMeta = grindstone.getItemMeta();
		grindstoneMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.config_yml_inventory.grindstone.name"));
		grindstoneMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.config_yml_inventory.grindstone.lore"));
		grindstone.setItemMeta(grindstoneMeta);
		inv.setItem(6, grindstone);
		
		// chest_loot, mob_loot, fishing_loot, villager_trades
		ItemStack loot = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta lootMeta = loot.getItemMeta();
		lootMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.config_yml_inventory.loot.name"));
		lootMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.config_yml_inventory.loot.lore"));
		loot.setItemMeta(lootMeta);
		inv.setItem(7, loot);
		
		// loots....
		ItemStack loots = new ItemStack(Material.CHEST_MINECART);
		ItemMeta lootsMeta = loots.getItemMeta();
		lootsMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.config_yml_inventory.loots.name"));
		lootsMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.config_yml_inventory.loots.lore"));
		loots.setItemMeta(lootsMeta);
		inv.setItem(8, loots);
		
		ItemStack advancements = new ItemStack(Material.KNOWLEDGE_BOOK);
		ItemMeta advancementsMeta = advancements.getItemMeta();
		advancementsMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.config_yml_inventory.advancements.name"));
		advancementsMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.config_yml_inventory.advancements.lore"));
		advancements.setItemMeta(advancementsMeta);
		inv.setItem(9, advancements);
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "tutorial.pagination.go_back"));
		goBack.setItemMeta(goBackMeta);
		inv.setItem(45, goBack);
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
		EnchantmentSolution.getPlugin().removeInventory(this);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(List<ItemStack> items) {
		setInventory();
	}
	
	public HashMap<String, Object> getCodes() {
		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%player%", player.getName());
		return codes;
	}

	@Override
	public void setItemName(String name) {
		
	}

	@Override
	public Inventory open(Inventory inv) {
		opening = true;
		if(inventory == null) {
			inventory = inv;
			player.openInventory(inv);
		} else {
			if(inv.getSize() == inventory.getSize()) {
				inv = player.getOpenInventory().getTopInventory();
				inventory = inv;
			} else {
				inventory = inv;
				player.openInventory(inv);
			}
		}
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}
		if(opening) {
			opening = false;
		}
		return inv;
	}
}
