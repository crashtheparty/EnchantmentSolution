package org.ctp.enchantmentsolution.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.helper.UpdateItem;
import org.ctp.enchantmentsolution.utils.AnvilUtils;
import org.ctp.enchantmentsolution.utils.AnvilUtils.RepairType;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.JobsUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class Anvil implements InventoryData{

	private Player player;
	private Inventory inventory;
	private List<ItemStack> playerItems;
	private UpdateItem combinedItem;
	private Block block;
	private boolean inLegacy;
	private boolean opening;

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
			if(ConfigUtils.useAnvilInGui() || ConfigUtils.useLegacyGrindstone()) {
				size = 45;
			}
			Inventory inv = Bukkit.createInventory(null, size, ChatUtils.getMessage(getCodes(), "anvil.name"));
			inv = open(inv);
	
			ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta mirrorMeta = mirror.getItemMeta();
			mirrorMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "anvil.mirror"));
			mirror.setItemMeta(mirrorMeta);
			int num_mirrors = 27;
			if(ConfigUtils.useAnvilInGui() || ConfigUtils.useLegacyGrindstone()) {
				num_mirrors = 45;
			}
			for(int i = 0; i < num_mirrors; i++) {
				if(i != 4 && i != 10 && i != 12 && i != 16) {
					inv.setItem(i, mirror);
				}
			}
			if(ConfigUtils.useAnvilInGui() && ConfigUtils.useLegacyGrindstone()) {
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
			} else if (ConfigUtils.useAnvilInGui()) {
				ItemStack anvil = new ItemStack(Material.ANVIL);
				ItemMeta anvilMeta = anvil.getItemMeta();
				anvilMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "anvil.legacy-gui"));
				anvilMeta.setLore(ChatUtils.getMessages(getCodes(), "anvil.legacy-gui-warning"));
				anvil.setItemMeta(anvilMeta);
				inv.setItem(31, anvil);
			} else if (ConfigUtils.useLegacyGrindstone()) {
				ItemStack grindstone = new ItemStack(Material.SMOOTH_STONE);
				ItemMeta grindstoneMeta = grindstone.getItemMeta();
				grindstoneMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.legacy-open"));
				grindstone.setItemMeta(grindstoneMeta);
				inv.setItem(31, grindstone);
			}
			
			ItemStack rename = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
			ItemMeta renameMeta = rename.getItemMeta();
			renameMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "anvil.rename"));
			rename.setItemMeta(renameMeta);
			
			ItemStack combine = null;
			if(playerItems.size() == 2 && AnvilUtils.canCombineItems(playerItems.get(0), playerItems.get(1))) {
				combinedItem = new UpdateItem(player, playerItems.get(0), playerItems.get(1));
				int repairCost = combinedItem.getRepairCost();
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
				if(combinedItem != null) {
					inv.setItem(16, combinedItem.getCombinedItem());
				}
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
	
	public void combine() {
		if(inventory.getItem(14).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
			combinedItem.update();
			if(player.getGameMode() != GameMode.CREATIVE) {
				if (combinedItem.getRepairCost() > player.getLevel()) {
					ChatUtils.sendMessage(player, ChatUtils.getMessage(getCodes(), "anvil.message-cannot-combine"));
					return;
				}
				player.setLevel(player.getLevel() - combinedItem.getRepairCost());
			}
			ItemUtils.giveItemToPlayer(player, combinedItem.getCombinedItem(), player.getLocation(), false);
			if(combinedItem.getRepairType().equals(RepairType.REPAIR)) {
				ItemUtils.giveItemToPlayer(player, combinedItem.getItemTwoLeftover(), player.getLocation(), false);
			}
			if(EnchantmentSolution.getPlugin().isJobsEnabled()) {
				JobsUtils.sendAnvilAction(player, playerItems.get(1), combinedItem.getCombinedItem());
			}
			combinedItem = null;
			playerItems.clear();

			AnvilUtils.checkAnvilBreak(player, block, this);
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

	@Override
	public Inventory open(Inventory inv) {
		opening = true;
		if(inventory == null || isInLegacy()) {
			inLegacy = false;
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
