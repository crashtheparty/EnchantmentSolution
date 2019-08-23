package org.ctp.enchantmentsolution.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.AnvilNMS;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.GrindstoneUtils;

public class Grindstone implements InventoryData{

	private Player player;
	private Inventory inventory;
	private List<ItemStack> playerItems;
	private ItemStack combinedItem;
	private Block block;
	private boolean takeEnchantments = false, opening;

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
			takeEnchantments = false;
			int size = 27;
			if(ConfigUtils.useLegacyGrindstone()) {
				size = 36;
			}
			Inventory inv = Bukkit.createInventory(null, size, ChatUtils.getMessage(getCodes(), "grindstone.name"));
			inv = open(inv);
	
			ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta mirrorMeta = mirror.getItemMeta();
			mirrorMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.mirror"));
			mirror.setItemMeta(mirrorMeta);
			for(int i = 0; i < 27; i++) {
				switch(i) {
				case 2:
				case 7:
				case 11:
					inv.setItem(i, new ItemStack(Material.AIR));
					break;
				case 16:
					if (ConfigUtils.grindstoneTakeEnchantments()) {
						inv.setItem(i, new ItemStack(Material.AIR));
					} else {
						inv.setItem(i, mirror);
					}
					break;
				default:
					inv.setItem(i, mirror);
					break;
				}
			}
			
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
			} else if(ConfigUtils.grindstoneTakeEnchantments() 
					&& playerItems.size() == 2 && GrindstoneUtils.canTakeEnchantments(playerItems.get(0), playerItems.get(1))) {
				combinedItem = GrindstoneUtils.takeEnchantments(getPlayer(), playerItems.get(0), playerItems.get(1));
				int levelCost = GrindstoneUtils.getEnchantmentCost(playerItems.get(0));
				takeEnchantments = true;
				if(levelCost > player.getLevel()) {
					combine = new ItemStack(Material.RED_STAINED_GLASS_PANE);
					ItemMeta combineMeta = combine.getItemMeta();
					HashMap<String, Object> codes = getCodes();
					codes.put("%levelCost%", levelCost);
					combineMeta.setDisplayName(ChatUtils.getMessage(codes, "grindstone.cannot-take-enchantments"));
					combine.setItemMeta(combineMeta);
				} else {
					combine = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
					List<String> lore = new ArrayList<String>();
					lore.addAll(ChatUtils.getMessages(getCodes(), "grindstone.take-enchantments-lore"));
					ItemMeta combineMeta = combine.getItemMeta();
					HashMap<String, Object> codes = getCodes();
					codes.put("%levelCost%", levelCost);
					combineMeta.setDisplayName(ChatUtils.getMessage(codes, "grindstone.take-enchantments"));
					combineMeta.setLore(lore);
					combine.setItemMeta(combineMeta);
				}
			} else if (playerItems.size() == 2){
				combinedItem = null;
				combine = new ItemStack(Material.RED_STAINED_GLASS_PANE);
				ItemMeta combineMeta = combine.getItemMeta();
				combineMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.cannot-combine"));
				combine.setItemMeta(combineMeta);
			} else if (playerItems.size() == 1) {
				if(playerItems.get(0).getItemMeta().hasEnchants() || (playerItems.get(0).getType() == Material.ENCHANTED_BOOK)) {
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
					lore.addAll(ChatUtils.getMessages(getCodes(), "grindstone.no-enchants-lore"));
					combineMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "grindstone.no-enchants"));
					combineMeta.setLore(lore);
					combine.setItemMeta(combineMeta);
				}
			} else {
				combinedItem = null;
				combine = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
				ItemMeta combineMeta = combine.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.addAll(ChatUtils.getMessages(getCodes(), "grindstone.no-items-lore"));
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
				if (!takeEnchantments) {
					combinedItem = AnvilNMS.setRepairCost(combinedItem, 0);
				} else {
					if(ConfigUtils.grindstoneTakeRepairCost()) {
						combinedItem = AnvilNMS.setRepairCost(combinedItem, AnvilNMS.getRepairCost(playerItems.get(0)));
					} else {
						combinedItem = AnvilNMS.setRepairCost(combinedItem, 0);
					}
				}
				inv.setItem(7, combinedItem);
			}
			inv.setItem(5, combine);
			
			if(ConfigUtils.useLegacyGrindstone()) {
				for(int i = 27; i < 36; i++) {
					inv.setItem(i, mirror);
				}
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
	
	public void combine() {
		if(inventory.getItem(5).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
			if(takeEnchantments) {
				ItemUtils.giveItemToPlayer(player, combinedItem, player.getLocation(), false);
				int levelCost = GrindstoneUtils.getEnchantmentCost(playerItems.get(0));
				if(player.getGameMode() != GameMode.CREATIVE){
					player.setLevel(player.getLevel() - levelCost);
				}
				combinedItem = null;
				playerItems.remove(1);
				if(ConfigUtils.grindstoneDestroyItem()) {
					playerItems.remove(0);
				} else {
					playerItems.set(0, Enchantments.removeAllEnchantments(playerItems.get(0)));
				}
			} else {
				ItemStack one = null;
				ItemStack two = null;
				if(playerItems.size() > 0) {
					if(playerItems.size() > 1) {
						two = playerItems.get(1);
					}
					one = playerItems.get(0);
				}
				ItemUtils.giveItemToPlayer(player, combinedItem, player.getLocation(), false);
				AbilityUtils.dropExperience(block.getLocation().clone().add(new Location(block.getLocation().getWorld(), 0.5, 0.5, 0.5)), 
						GrindstoneUtils.getExperience(one, two));
				combinedItem = null;
				playerItems.clear();
			}
		} else {
			if(takeEnchantments) {
				ChatUtils.sendMessage(player, ChatUtils.getMessage(getCodes(), "grindstone.message-cannot-take-enchantments"));
			} else {
				ChatUtils.sendMessage(player, ChatUtils.getMessage(getCodes(), "grindstone.message-cannot-combine"));
			}
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
				ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
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
