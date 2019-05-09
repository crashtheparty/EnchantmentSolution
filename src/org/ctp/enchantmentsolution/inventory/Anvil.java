package org.ctp.enchantmentsolution.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
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
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.JobsUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class Anvil implements InventoryData{

	private Player player;
	private Inventory inventory;
	private List<ItemStack> playerItems;
	private ItemStack combinedItem;
	private Block block;
	private boolean inLegacy;

	public Anvil(Player player, Block block) {
		this.setPlayer(player);
		this.setBlock(block);
		this.playerItems = new ArrayList<ItemStack>();
	}

	public void setInventory() {
		setInventory(playerItems);
	}

	public void setInventory(List<ItemStack> items) {
		if(block.getType() == Material.AIR) return;
		try {
			int size = 27;
			if(ConfigUtils.useDefaultAnvil() || ConfigUtils.useLegacyGrindstone()) {
				size = 45;
			}
			Inventory inv = Bukkit.createInventory(null, size, ChatUtils.getMessage(getCodes(), "anvil.name"));
			if(inventory == null || isInLegacy()) {
				inLegacy = false;
				inventory = inv;
				player.openInventory(inv);
			} else {
				inv = player.getOpenInventory().getTopInventory();
				inventory = inv;
			}
	
			ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta mirrorMeta = mirror.getItemMeta();
			mirrorMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "anvil.mirror"));
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
			if(ConfigUtils.useDefaultAnvil() || ConfigUtils.useLegacyGrindstone()) {
				inv.setItem(27, mirror);
				inv.setItem(28, mirror);
				inv.setItem(29, mirror);
				inv.setItem(30, mirror);
				inv.setItem(31, mirror);
				inv.setItem(32, mirror);
				inv.setItem(33, mirror);
				inv.setItem(34, mirror);
				inv.setItem(35, mirror);
				inv.setItem(36, mirror);
				inv.setItem(37, mirror);
				inv.setItem(38, mirror);
				inv.setItem(39, mirror);
				inv.setItem(40, mirror);
				inv.setItem(41, mirror);
				inv.setItem(42, mirror);
				inv.setItem(43, mirror);
				inv.setItem(44, mirror);
				if(ConfigUtils.useDefaultAnvil() && ConfigUtils.useLegacyGrindstone()) {
					ItemStack anvil = new ItemStack(Material.ANVIL);
					ItemMeta anvilMeta = anvil.getItemMeta();
					anvilMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "anvil.legacy-gui"));
					anvilMeta.setLore(ChatUtils.getMessages(getCodes(), "anvil.legacy-gui-warning"));
					anvil.setItemMeta(anvilMeta);
					inv.setItem(30, anvil);
					ItemStack grindstone = new ItemStack(Material.SMOOTH_STONE);
					ItemMeta grindstoneMeta = grindstone.getItemMeta();
					grindstoneMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.legacy-open"));
					grindstone.setItemMeta(grindstoneMeta);
					inv.setItem(32, grindstone);
				} else if (ConfigUtils.useDefaultAnvil()) {
					ItemStack anvil = new ItemStack(Material.ANVIL);
					ItemMeta anvilMeta = anvil.getItemMeta();
					anvilMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "anvil.legacy-gui"));
					anvilMeta.setLore(ChatUtils.getMessages(getCodes(), "anvil.legacy-gui-warning"));
					anvil.setItemMeta(anvilMeta);
					inv.setItem(31, anvil);
				} else {
					ItemStack grindstone = new ItemStack(Material.SMOOTH_STONE);
					ItemMeta grindstoneMeta = grindstone.getItemMeta();
					grindstoneMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.legacy-open"));
					grindstone.setItemMeta(grindstoneMeta);
					inv.setItem(31, grindstone);
				}
			}
			
			ItemStack rename = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
			ItemMeta renameMeta = rename.getItemMeta();
			renameMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "anvil.rename"));
			rename.setItemMeta(renameMeta);
			
			ItemStack combine = null;
			if(playerItems.size() == 2 && AnvilUtils.canCombineItems(playerItems.get(0), playerItems.get(1))) {
				int repairCost = getRepairCost();
				int playerLevel = player.getLevel();
				List<String> lore = new ArrayList<String>();
				if(player.getGameMode().equals(GameMode.CREATIVE) || repairCost <= playerLevel) {
					if (!player.getGameMode().equals(GameMode.CREATIVE) && repairCost > ConfigUtils.getMaxRepairLevel()) {
						combine = new ItemStack(Material.RED_STAINED_GLASS_PANE);
						HashMap<String, Object> loreCodes = getCodes();
						loreCodes.put("%repairCost%", repairCost);
						lore.add(ChatUtils.getMessage(loreCodes, "anvil.cannot-repair"));
					} else {
						combine = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
						HashMap<String, Object> loreCodes = getCodes();
						loreCodes.put("%repairCost%", repairCost);
						lore.add(ChatUtils.getMessage(loreCodes, "anvil.repair-cost"));
					}
				}else {
					combine = new ItemStack(Material.RED_STAINED_GLASS_PANE);
					if (!player.getGameMode().equals(GameMode.CREATIVE) && repairCost > ConfigUtils.getMaxRepairLevel()) {
						lore.add(ChatUtils.getMessage(getCodes(), "anvil.cannot-repair"));
					} else {
						HashMap<String, Object> loreCodes = getCodes();
						loreCodes.put("%repairCost%", repairCost);
						lore.add(ChatUtils.getMessage(loreCodes, "anvil.repair-cost-high"));
					}
				}
				ItemMeta combineMeta = combine.getItemMeta();
				combineMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "anvil.combine"));
				combineMeta.setLore(lore);
				combine.setItemMeta(combineMeta);
				combine.setAmount(repairCost);
			} else {
				combine = new ItemStack(Material.BARRIER);
				ItemMeta combineMeta = combine.getItemMeta();
				combineMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "anvil.cannot-combine"));
				combine.setItemMeta(combineMeta);
			}
			
			ItemStack barrierRename = new ItemStack(Material.BARRIER);
			ItemMeta barrierRenameMeta = barrierRename.getItemMeta();
			barrierRenameMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "anvil.cannot-rename"));
			barrierRename.setItemMeta(barrierRenameMeta);
			
			inv.setItem(10, new ItemStack(Material.AIR));
			inv.setItem(12, new ItemStack(Material.AIR));
			inv.setItem(16, new ItemStack(Material.AIR));
			
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
				combinedItem = ItemUtils.combineItems(player, playerItems.get(0), playerItems.get(1));
				inv.setItem(16, combinedItem);
			}else {
				combinedItem = null;
			}
			inv.setItem(14, combine);
		}catch(Exception ex) {
			ex.printStackTrace();
			if(playerItems.size() - 1 >= 0) {
				ItemStack item = playerItems.get(playerItems.size() - 1);
				if(removeItem(playerItems.size() - 1)) {
					ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
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

	public int getRepairCost() {
		int repairCost = 0;
		RepairType type = AnvilUtils.RepairType.getRepairType(playerItems.get(0), playerItems.get(1));
		if(!type.equals(RepairType.RENAME) && DamageUtils.getDamage(playerItems.get(0).getItemMeta()) != 0) {
			repairCost += 2;
		}
		int repairCostOne = AnvilNMS.getRepairCost(playerItems.get(0));
		int repairCostTwo = AnvilNMS.getRepairCost(playerItems.get(1));
		repairCost += repairCostOne + repairCostTwo;
		
		if(type.equals(RepairType.COMBINE)) {
			repairCost += Enchantments.combineEnchantmentsLevel(player, playerItems.get(0), playerItems.get(1));
		}else if(type.equals(RepairType.REPAIR)) {
			repairCost += ItemUtils.repairItem(playerItems.get(0), playerItems.get(1));
		}
		
		return Math.max(repairCost / ConfigUtils.getLevelDivisor(), 1);
	}
	
	public void combine() {
		if(inventory.getItem(14).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
			RepairType type = AnvilUtils.RepairType.getRepairType(playerItems.get(0), playerItems.get(1));
			if(player.getGameMode() != GameMode.CREATIVE){
				player.setLevel(player.getLevel() - getRepairCost());
			}
			int itemOneRepair = AnvilNMS.getRepairCost(playerItems.get(0));
			int itemTwoRepair = AnvilNMS.getRepairCost(playerItems.get(1));
			if(itemOneRepair > itemTwoRepair) {
				combinedItem = AnvilNMS.setRepairCost(combinedItem, itemOneRepair * 2 + 1);
			}else {
				combinedItem = AnvilNMS.setRepairCost(combinedItem, itemTwoRepair * 2 + 1);
			}
			ItemUtils.giveItemToPlayer(player, combinedItem, player.getLocation(), false);
			if(type.equals(RepairType.REPAIR)) {
				ItemStack repairItem = playerItems.get(1).clone();
				repairItem.setAmount(repairItem.getAmount() - ItemUtils.repairItem(playerItems.get(0), repairItem));
				ItemUtils.giveItemToPlayer(player, repairItem, player.getLocation(), false);
			}
			if(EnchantmentSolution.getPlugin().isJobsEnabled()) {
				JobsUtils.sendAnvilAction(player, playerItems.get(1), combinedItem);
			}
			combinedItem = null;
			playerItems.clear();

			checkAnvilBreak();
		}else {
			ChatUtils.sendMessage(player, ChatUtils.getMessage(getCodes(), "anvil.message-cannot-combine"));
		}
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
			ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
		}
	}
	
	public void checkAnvilBreak() {
		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
			return;
		}
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
			if(material == Material.AIR) {
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
				close(false);
				block.setType(material);
			} else {
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
				BlockFace facing = ((Directional) block.getBlockData()).getFacing();
				block.setType(material);
				Directional d = (Directional) block.getBlockData();
				d.setFacing(facing);
				block.setBlockData((BlockData) d);
			}
		} else {
			block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
		}
	}
	
	public void close(boolean external) {
		if(EnchantmentSolution.getPlugin().hasInventory(this)) {
			for(ItemStack item : getItems()){
				ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
			}
			EnchantmentSolution.getPlugin().removeInventory(this);
			if(!external) {
				player.getOpenInventory().close();
			}
		}
	}

	public HashMap<String, Object> getCodes() {
		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%player%", player.getName());
		return codes;
	}

	public boolean isInLegacy() {
		return inLegacy;
	}

	public void setInLegacy(boolean inLegacy) {
		this.inLegacy = inLegacy;
	}
}
