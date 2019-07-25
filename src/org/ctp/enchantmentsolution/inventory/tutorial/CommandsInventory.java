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

public class CommandsInventory implements InventoryData{

	private Player player;
	private Inventory inventory;
	private boolean opening;
	
	public CommandsInventory(Player player) {
		this.player = player;
	}
	
	public void setInventory() {
		Inventory inv = Bukkit.createInventory(null, 27, ChatUtils.getMessage(getCodes(), "tutorial.commands_inventory.name"));
		inv = open(inv);
		
		int i = 0;
		
		if(player.hasPermission("enchantmentsolution.command.enchant")) {
			ItemStack command = new ItemStack(Material.PAPER);
			ItemMeta commandMeta = command.getItemMeta();
			commandMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.commands_inventory.enchant.name"));
			commandMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.commands_inventory.enchant.lore"));
			command.setItemMeta(commandMeta);
			inv.setItem(i, command);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.command.enchantunsafe")) {
			ItemStack command = new ItemStack(Material.PAPER);
			ItemMeta commandMeta = command.getItemMeta();
			commandMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.commands_inventory.enchantunsafe.name"));
			commandMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.commands_inventory.enchantunsafe.lore"));
			command.setItemMeta(commandMeta);
			inv.setItem(i, command);
			i++;
		}
		
		if(true) {
			ItemStack command = new ItemStack(Material.PAPER);
			ItemMeta commandMeta = command.getItemMeta();
			commandMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.commands_inventory.info.name"));
			commandMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.commands_inventory.info.lore"));
			command.setItemMeta(commandMeta);
			inv.setItem(i, command);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.command.removeenchant")) {
			ItemStack command = new ItemStack(Material.PAPER);
			ItemMeta commandMeta = command.getItemMeta();
			commandMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.commands_inventory.removeenchant.name"));
			commandMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.commands_inventory.removeenchant.lore"));
			command.setItemMeta(commandMeta);
			inv.setItem(i, command);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.command.reload")) {
			ItemStack command = new ItemStack(Material.PAPER);
			ItemMeta commandMeta = command.getItemMeta();
			commandMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.commands_inventory.reload.name"));
			commandMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.commands_inventory.reload.lore"));
			command.setItemMeta(commandMeta);
			inv.setItem(i, command);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.command.edit")) {
			ItemStack command = new ItemStack(Material.PAPER);
			ItemMeta commandMeta = command.getItemMeta();
			commandMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.commands_inventory.esconfig.name"));
			commandMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.commands_inventory.esconfig.lore"));
			command.setItemMeta(commandMeta);
			inv.setItem(i, command);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.command.reset")) {
			ItemStack command = new ItemStack(Material.PAPER);
			ItemMeta commandMeta = command.getItemMeta();
			commandMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.commands_inventory.esreset.name"));
			commandMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.commands_inventory.esreset.lore"));
			command.setItemMeta(commandMeta);
			inv.setItem(i, command);
			i++;
		}
		
		if(player.hasPermission("enchantmentsolution.command.debug")) {
			ItemStack command = new ItemStack(Material.PAPER);
			ItemMeta commandMeta = command.getItemMeta();
			commandMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.commands_inventory.esdebug.name"));
			commandMeta.setLore(ChatUtils.getMessages(getCodes(), "tutorial.commands_inventory.esdebug.lore"));
			command.setItemMeta(commandMeta);
			inv.setItem(i, command);
			i++;
		}
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "tutorial.pagination.go_back"));
		goBack.setItemMeta(goBackMeta);
		inv.setItem(18, goBack);
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
