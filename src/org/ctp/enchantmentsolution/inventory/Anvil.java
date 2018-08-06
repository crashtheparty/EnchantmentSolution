package org.ctp.enchantmentsolution.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.AnvilNMS;
import org.ctp.enchantmentsolution.utils.AnvilUtils;
import org.ctp.enchantmentsolution.utils.AnvilUtils.RepairType;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ItemUtils;

public class Anvil {

	private Player player;
	private Inventory inventory;
	private List<ItemStack> playerItems;
	private boolean openingNew;
	private ItemStack combinedItem;
	private boolean isRenaming;
	private Block block;

	public Anvil(Player player, Block block) {
		this.setPlayer(player);
		this.setBlock(block);
		this.playerItems = new ArrayList<ItemStack>();
	}

	public void setInventory() {
		setInventory(playerItems);
	}

	public void setInventory(List<ItemStack> items) {
		try {
			openingNew = true;
			Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLUE
					+ "Anvil");
	
			ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta mirrorMeta = mirror.getItemMeta();
			mirrorMeta.setDisplayName(ChatColor.WHITE + "");
			mirror.setItemMeta(mirrorMeta);
			inv.setItem(0, mirror);
			inv.setItem(1, mirror);
			inv.setItem(2, mirror);
			inv.setItem(3, mirror);
			inv.setItem(5, mirror);
			inv.setItem(6, mirror);
			inv.setItem(7, mirror);
			inv.setItem(8, mirror);
			inv.setItem(9, mirror);
			inv.setItem(11, mirror);
			inv.setItem(13, mirror);
			inv.setItem(15, mirror);
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
			
			ItemStack rename = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
			ItemMeta renameMeta = rename.getItemMeta();
			renameMeta.setDisplayName(ChatColor.GREEN + "Rename Items");
			rename.setItemMeta(renameMeta);
			
			ItemStack combine = null;
			if(playerItems.size() == 2 && AnvilUtils.canCombineItems(playerItems.get(0), playerItems.get(1))) {
				int repairCost = getRepairCost();
				int playerLevel = player.getLevel();
				List<String> lore = new ArrayList<String>();
				if(player.getGameMode().equals(GameMode.CREATIVE) || repairCost <= playerLevel) {
					combine = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
					lore.add(ChatColor.GREEN + "Level Cost: " + ChatColor.BLUE + "" + repairCost);
				}else {
					combine = new ItemStack(Material.RED_STAINED_GLASS_PANE);
					if(!player.getGameMode().equals(GameMode.CREATIVE) && repairCost > 60) {
						lore.add(ChatColor.RED + "Level Cost: " + ChatColor.BLUE + "Cannot Repair This Item");
					}else {
						lore.add(ChatColor.RED + "Level Cost: " + ChatColor.BLUE + "" + repairCost);
					}
				}
				ItemMeta combineMeta = combine.getItemMeta();
				combineMeta.setDisplayName(ChatColor.GREEN + "Combine Items");
				combineMeta.setLore(lore);
				combine.setItemMeta(combineMeta);
				combine.setAmount(repairCost);
			}else {
				combine = new ItemStack(Material.BARRIER);
				ItemMeta combineMeta = combine.getItemMeta();
				combineMeta.setDisplayName(ChatColor.RED + "Can't Combine Items");
				combine.setItemMeta(combineMeta);
			}
			
			ItemStack barrierRename = new ItemStack(Material.BARRIER);
			ItemMeta barrierRenameMeta = barrierRename.getItemMeta();
			barrierRenameMeta.setDisplayName(ChatColor.RED + "Can't Rename Item(s)");
			barrierRename.setItemMeta(barrierRenameMeta);
			
			if(playerItems.size() == 1) {
				inv.setItem(4, rename);
			}else {
				inv.setItem(4, barrierRename);
			}
			
			if(playerItems.size() >= 1) {
				ItemStack item = playerItems.get(0);
				inv.setItem(10, item);
			}
			if(playerItems.size() == 2) {
				ItemStack item = playerItems.get(1);
				inv.setItem(12, item);
				combinedItem = ItemUtils.combineItems(playerItems.get(0), playerItems.get(1));
				inv.setItem(16, combinedItem);
			}else {
				combinedItem = null;
			}
			inv.setItem(14, combine);
			inventory = inv;
			player.openInventory(inv);
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

	public boolean addItem(ItemStack item) {
		if (playerItems.size() >= 2) {
			return false;
		}
		playerItems.add(item);
		return true;
	}

	public boolean removeItem(ItemStack item) {
		return playerItems.remove(item);
	}

	public boolean removeItem(int slot) {
		if(slot == 10) {
			slot = 0;
		}else if(slot == 12) {
			slot = 1;
		}
		return playerItems.remove(slot) != null;
	}

	public List<ItemStack> getItems() {
		return playerItems;
	}

	public boolean openingNew() {
		if (openingNew) {
			openingNew = false;
			return true;
		}
		return false;
	}
	
	public void setOpeningNew(boolean opening) {
		openingNew = opening;
	}
	
	public int getRepairCost() {
		int repairCost = 0;
		RepairType type = AnvilUtils.RepairType.getRepairType(playerItems.get(0), playerItems.get(1));
		if(!type.equals(RepairType.RENAME) && playerItems.get(0).getDurability() != 0) {
			repairCost += 2;
		}
		int repairCostOne = AnvilNMS.getRepairCost(playerItems.get(0));
		int repairCostTwo = AnvilNMS.getRepairCost(playerItems.get(1));
		repairCost += repairCostOne + repairCostTwo;
		
		if(type.equals(RepairType.COMBINE)) {
			repairCost += Enchantments.combineEnchantmentsLevel(playerItems.get(0), playerItems.get(1));
		}else if(type.equals(RepairType.REPAIR)) {
			repairCost += ItemUtils.repairItem(playerItems.get(0), playerItems.get(1));
		}
		
		return Math.max(repairCost / Enchantments.getLevelDivisor(), 1);
	}
	
	public void combine() {
		if(inventory.getItem(14).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
			RepairType type = AnvilUtils.RepairType.getRepairType(playerItems.get(0), playerItems.get(1));
			player.setLevel(player.getLevel() - getRepairCost());
			int itemOneRepair = AnvilNMS.getRepairCost(playerItems.get(0));
			int itemTwoRepair = AnvilNMS.getRepairCost(playerItems.get(1));
			if(itemOneRepair > itemTwoRepair) {
				combinedItem = AnvilNMS.setRepairCost(combinedItem, itemOneRepair * 2 + 1);
			}else {
				combinedItem = AnvilNMS.setRepairCost(combinedItem, itemTwoRepair * 2 + 1);
			}
			ItemUtils.giveItemToPlayer(player, combinedItem, player.getLocation());
			if(type.equals(RepairType.REPAIR)) {
				ItemStack repairItem = playerItems.get(1).clone();
				repairItem.setAmount(repairItem.getAmount() - ItemUtils.repairItem(playerItems.get(0), repairItem));
				ItemUtils.giveItemToPlayer(player, repairItem, player.getLocation());
			}
			combinedItem = null;
			playerItems.clear();
			checkAnvilBreak();
		}else {
			ChatUtils.sendMessage(player, "You may not combine these items!");
		}
	}
	
	public boolean isRenaming() {
		return isRenaming;
	}
	
	public void setRenaming(boolean renaming) {
		isRenaming = renaming;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
	
	public void setItemName(String name) {
		if(playerItems.size() == 1) {
			ItemStack item = playerItems.remove(0);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(name);
			item.setItemMeta(meta);
			ItemUtils.giveItemToPlayer(player, item, player.getLocation());
		}
	}
	
	public void checkAnvilBreak() {
		double chance = .12;
		double roll = Math.random();
		if(chance > roll) {
			Material material = Material.AIR;
			switch(block.getType()) {
			case ANVIL:
				material = Material.CHIPPED_ANVIL;
				break;
			case CHIPPED_ANVIL:
				material = Material.DAMAGED_ANVIL;
				break;
			default:
				
			}
			block.setType(material);
			if(material == Material.AIR) {
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
				for(int i = EnchantmentSolution.ANVILS.size() - 1; i >= 0; i--) {
					Anvil anvil = EnchantmentSolution.ANVILS.get(i);
					if(anvil.getBlock().getLocation().equals(block.getLocation())) {
						anvil.close(false);
					}
				}
			} else {
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
			}
		} else {
			block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
		}
	}
	
	public void close(boolean external) {
		for(ItemStack item : getItems()){
			ItemUtils.giveItemToPlayer(player, item, player.getLocation());
		}
		if(!external) {
			player.closeInventory();
		}
		EnchantmentSolution.ANVILS.remove(this);
	}
}
