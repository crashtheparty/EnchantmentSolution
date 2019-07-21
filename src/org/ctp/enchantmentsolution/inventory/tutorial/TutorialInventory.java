package org.ctp.enchantmentsolution.inventory.tutorial;

import java.util.ArrayList;
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

public class TutorialInventory implements InventoryData{

	private Player player;
	private Inventory inventory;
	private boolean opening;
	private List<Class<? extends InventoryData>> classes;
	
	public TutorialInventory(Player player) {
		this.player = player;
	}
	
	public void setInventory() {
		classes = new ArrayList<Class<? extends InventoryData>>();
		Inventory inv = Bukkit.createInventory(null, 27, ChatUtils.getMessage(getCodes(), "tutorial.name"));
		inv = open(inv);
		
		int i = 0;
		ItemStack enchantments = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta enchantmentsMeta = enchantments.getItemMeta();
		enchantmentsMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.enchantments.name"));
		enchantmentsMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.enchantments.lore"));
		enchantments.setItemMeta(enchantmentsMeta);
		inv.setItem(i, enchantments);
		classes.add(EnchantmentListInventory.class);
		i++;
		
		if(player.hasPermission("enchantmentsolution.commands.enchantinfo.commands")) {
			ItemStack config = new ItemStack(Material.PAPER);
			ItemMeta configMeta = config.getItemMeta();
			configMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.commands.name"));
			configMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.commands.lore"));
			config.setItemMeta(configMeta);
			inv.setItem(i, config);
			classes.add(CommandsInventory.class);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.commands.enchantinfo.config_yml")) {
			ItemStack config = new ItemStack(Material.PAPER);
			ItemMeta configMeta = config.getItemMeta();
			configMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.config_yml.name"));
			configMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.config_yml.lore"));
			config.setItemMeta(configMeta);
			inv.setItem(i, config);
//			classes.add(ConfigYMLInventory.class);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.commands.enchantinfo.enchantments_yml")) {
			ItemStack config = new ItemStack(Material.PAPER);
			ItemMeta configMeta = config.getItemMeta();
			configMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.enchantments_yml.name"));
			configMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.enchantments_yml.lore"));
			config.setItemMeta(configMeta);
			inv.setItem(i, config);
//			classes.add(EnchantmentsYMLInventory.class);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.commands.enchantinfo.enchantments_advanced_yml")) {
			ItemStack config = new ItemStack(Material.PAPER);
			ItemMeta configMeta = config.getItemMeta();
			configMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.enchantments_advanced_yml.name"));
			configMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.enchantments_advanced_yml.lore"));
			config.setItemMeta(configMeta);
			inv.setItem(i, config);
//			classes.add(EnchantmentsAdvancedYMLInventory.class);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.commands.enchantinfo.fishing_yml")) {
			ItemStack config = new ItemStack(Material.PAPER);
			ItemMeta configMeta = config.getItemMeta();
			configMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.fishing_yml.name"));
			configMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.fishing_yml.lore"));
			config.setItemMeta(configMeta);
			inv.setItem(i, config);
//			classes.add(FishingYMLInventory.class);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.commands.enchantinfo.language_yml")) {
			ItemStack config = new ItemStack(Material.PAPER);
			ItemMeta configMeta = config.getItemMeta();
			configMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.language_yml.name"));
			configMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.language_yml.lore"));
			config.setItemMeta(configMeta);
			inv.setItem(i, config);
//			classes.add(LanguageYMLInventory.class);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.commands.enchantinfo.other")) {
			ItemStack config = new ItemStack(Material.PAPER);
			ItemMeta configMeta = config.getItemMeta();
			configMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.other.name"));
			configMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.other.lore"));
			config.setItemMeta(configMeta);
			inv.setItem(i, config);
//			classes.add(OtherInventory.class);
			i++;
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

	public void open(int slot) {
		this.close(false);
		
		if(classes.get(slot).isAssignableFrom(EnchantmentListInventory.class)) {
			EnchantmentListInventory inv = new EnchantmentListInventory(player);
			EnchantmentSolution.getPlugin().addInventory(inv);
			inv.setInventory();
		}
	}
}
