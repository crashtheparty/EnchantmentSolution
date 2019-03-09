package org.ctp.enchantmentsolution.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.GrindstoneUtils;

public class Grindstone implements InventoryData{

	private Player player;
	private Inventory inventory;
	private List<ItemStack> playerItems;
	private ItemStack combinedItem;
	private Block block;

	public Grindstone(Player player, Block block) {
		this.setPlayer(player);
		this.setBlock(block);
		this.playerItems = new ArrayList<ItemStack>();
	}

	public void setInventory() {
		setInventory(playerItems);
	}

	public void setInventory(List<ItemStack> items) {
		try {
			int size = 27;
			if(EnchantmentSolution.getPlugin().getConfigFiles().useLegacyGrindstone()) {
				size = 36;
			}
			Inventory inv = Bukkit.createInventory(null, size, ChatUtils.getMessage(getCodes(), "grindstone.name"));
	
			ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta mirrorMeta = mirror.getItemMeta();
			mirrorMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.mirror"));
			mirror.setItemMeta(mirrorMeta);
			inv.setItem(0, mirror);
			inv.setItem(1, mirror);
			inv.setItem(3, mirror);
			inv.setItem(4, mirror);
			inv.setItem(6, mirror);
			inv.setItem(8, mirror);
			inv.setItem(9, mirror);
			inv.setItem(10, mirror);
			inv.setItem(12, mirror);
			inv.setItem(13, mirror);
			inv.setItem(14, mirror);
			inv.setItem(15, mirror);
			inv.setItem(16, mirror);
			inv.setItem(17, mirror);
			inv.setItem(18, mirror);
			inv.setItem(19, mirror);
			inv.setItem(20, mirror);
			inv.setItem(21, mirror);
			inv.setItem(22, mirror);
			inv.setItem(23, mirror);
			inv.setItem(24, mirror);
			inv.setItem(25, mirror);
			inv.setItem(26, mirror);
			
			ItemStack combine;
			if(playerItems.size() == 2 && GrindstoneUtils.canCombineItems(playerItems.get(0), playerItems.get(1))) {
				combinedItem = GrindstoneUtils.combineItems(getPlayer(), playerItems.get(0), playerItems.get(1));
				combine = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
				List<String> lore = new ArrayList<String>();
				lore.add(ChatUtils.getMessage(getCodes(), "grindstone.combine-lore"));
				ItemMeta combineMeta = combine.getItemMeta();
				combineMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.combine"));
				combineMeta.setLore(lore);
				combine.setItemMeta(combineMeta);
			} else if (playerItems.size() == 2){
				combinedItem = null;
				combine = new ItemStack(Material.RED_STAINED_GLASS_PANE);
				ItemMeta combineMeta = combine.getItemMeta();
				combineMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.cannot-combine"));
				combine.setItemMeta(combineMeta);
			} else if (playerItems.size() == 1) {
				combinedItem = GrindstoneUtils.combineItems(getPlayer(), playerItems.get(0));
				combine = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
				ItemMeta combineMeta = combine.getItemMeta();
				combineMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.remove-enchants"));
				combine.setItemMeta(combineMeta);
			} else {
				combinedItem = null;
				combine = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
				ItemMeta combineMeta = combine.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatUtils.getMessage(getCodes(), "grindstone.no-items-lore"));
				combineMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.no-items"));
				combineMeta.setLore(lore);
				combine.setItemMeta(combineMeta);
			}
			
			for(int i = 0; i < playerItems.size(); i++) {
				if(i == 0) {
					inv.setItem(2, playerItems.get(i));
				} else if (i == 1) {
					inv.setItem(11, playerItems.get(i));
				}
			}
			
			if(combinedItem != null) {
				inv.setItem(7, combinedItem);
			}
			inv.setItem(5, combine);
			inventory = inv;
			player.openInventory(inv);
			
			if(EnchantmentSolution.getPlugin().getConfigFiles().useLegacyGrindstone()) {
				inv.setItem(27, mirror);
				inv.setItem(28, mirror);
				inv.setItem(29, mirror);
				inv.setItem(30, mirror);
				inv.setItem(32, mirror);
				inv.setItem(33, mirror);
				inv.setItem(34, mirror);
				inv.setItem(35, mirror);
				ItemStack anvil = new ItemStack(Material.ANVIL);
				ItemMeta anvilMeta = anvil.getItemMeta();
				anvilMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.anvil"));
				anvilMeta.setLore(Arrays.asList(ChatUtils.getMessage(getCodes(), "grindstone.switch-to-anvil")));
				anvil.setItemMeta(anvilMeta);
				inv.setItem(31, anvil);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			if(playerItems.size() - 1 >= 0) {
				ItemStack item = playerItems.get(playerItems.size() - 1);
				if(removeItem(playerItems.size() - 1)) {
					ItemUtils.giveItemToPlayer(player, item, player.getLocation());
				}
			}
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	public void combine() {
		if(inventory.getItem(5).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
//			int itemOneRepair = AnvilNMS.getRepairCost(playerItems.get(0));
//			int itemTwoRepair = AnvilNMS.getRepairCost(playerItems.get(1));
//			if(itemOneRepair > itemTwoRepair) {
//				combinedItem = AnvilNMS.setRepairCost(combinedItem, itemOneRepair * 2 + 1);
//			}else {
//				combinedItem = AnvilNMS.setRepairCost(combinedItem, itemTwoRepair * 2 + 1);
//			}
			ItemUtils.giveItemToPlayer(player, combinedItem, player.getLocation());
			AbilityUtils.dropExperience(block.getLocation().clone().add(new Location(block.getLocation().getWorld(), 0.5, 0.5, 0.5)), 10);
			combinedItem = null;
			playerItems.clear();
		} else {
			ChatUtils.sendMessage(player, ChatUtils.getMessage(getCodes(), "grindstone.message-cannot-combine"));
		}
	}

	public boolean addItem(ItemStack item) {
		if (playerItems.size() >= 2) {
			return false;
		}
		if(item.getItemMeta() instanceof Damageable || item.getItemMeta().hasEnchants()) {
			playerItems.add(item);
			return true;
		}
		return false;
	}

	public boolean removeItem(ItemStack item) {
		return playerItems.remove(item);
	}

	public boolean removeItem(int slot) {
		if(slot == 2) {
			slot = 0;
		}else if(slot == 11) {
			slot = 1;
		}
		return playerItems.remove(slot) != null;
	}

	public List<ItemStack> getItems() {
		return playerItems;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
	
	public void setItemName(String name) {
		
	}
	
	public void close(boolean external) {  
		if(EnchantmentSolution.getPlugin().hasInventory(this)) {
			for(ItemStack item : getItems()){
				ItemUtils.giveItemToPlayer(player, item, player.getLocation());
			}
			EnchantmentSolution.getPlugin().removeInventory(this);
			if(!external) {
				player.closeInventory();
			}
		}
	}

	public HashMap<String, Object> getCodes() {
		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%player%", player.getName());
		return codes;
	}
}
