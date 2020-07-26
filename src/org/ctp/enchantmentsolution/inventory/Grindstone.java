package org.ctp.enchantmentsolution.inventory;

import java.util.*;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.crashapi.utils.LocationUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.generate.GrindstoneEnchantments;
import org.ctp.enchantmentsolution.nms.AnvilNMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class Grindstone implements InventoryData {

	private Player player;
	private Inventory inventory;
	private List<ItemStack> playerItems;
	private GrindstoneEnchantments grindstone;
	private Block block;
	private boolean takeEnchantments = false, opening;

	public Grindstone(Player player, Block block) {
		setPlayer(player);
		setBlock(block);
		playerItems = new ArrayList<ItemStack>();
	}

	@Override
	public void setInventory() {
		setInventory(playerItems);
	}

	@Override
	public void setInventory(List<ItemStack> items) {
		try {
			takeEnchantments = false;
			boolean grindstoneTakeEnchantments = ConfigString.TAKE_ENCHANTMENTS.getBoolean();
			int size = 27;
			if (ConfigUtils.useLegacyGrindstone()) size = 36;
			Inventory inv = Bukkit.createInventory(null, size, Chatable.get().getMessage(getCodes(), "grindstone.name"));
			inv = open(inv);

			ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta mirrorMeta = mirror.getItemMeta();
			mirrorMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "grindstone.mirror"));
			mirror.setItemMeta(mirrorMeta);
			for(int i = 0; i < 27; i++)
				switch (i) {
					case 2:
					case 7:
					case 11:
						inv.setItem(i, new ItemStack(Material.AIR));
						break;
					case 16:
						if (grindstoneTakeEnchantments) inv.setItem(i, new ItemStack(Material.AIR));
						else
							inv.setItem(i, mirror);
						break;
					default:
						inv.setItem(i, mirror);
						break;
				}

			ItemStack combine = null;
			ItemStack combinedItem = null;
			ItemStack first = null;
			ItemStack second = null;
			if (playerItems.size() == 2) {
				first = playerItems.get(0);
				second = playerItems.get(1);
			} else if (playerItems.size() == 1) first = playerItems.get(0);

			grindstone = GrindstoneEnchantments.getGrindstoneEnchantments(player, first, second);

			if (playerItems.size() == 2) {
				if (grindstone.canCombine()) {
					combinedItem = grindstone.getCombinedItem();
					combine = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
					List<String> lore = new ArrayList<String>();
					lore.add(Chatable.get().getMessage(getCodes(), "grindstone.combine-lore"));
					ItemMeta combineMeta = combine.getItemMeta();
					combineMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "grindstone.combine"));
					combineMeta.setLore(lore);
					combine.setItemMeta(combineMeta);
				} else if (grindstone.canTakeEnchantments()) {
					combinedItem = grindstone.getTakenItem();
					int levelCost = grindstone.getTakeCost();
					takeEnchantments = true;
					if (levelCost > player.getLevel()) {
						combine = new ItemStack(Material.RED_STAINED_GLASS_PANE);
						ItemMeta combineMeta = combine.getItemMeta();
						HashMap<String, Object> codes = getCodes();
						codes.put("%levelCost%", levelCost);
						combineMeta.setDisplayName(Chatable.get().getMessage(codes, "grindstone.cannot-take-enchantments"));
						combine.setItemMeta(combineMeta);
					} else {
						combine = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
						List<String> lore = new ArrayList<String>();
						lore.addAll(Chatable.get().getMessages(getCodes(), "grindstone.take-enchantments-lore"));
						ItemMeta combineMeta = combine.getItemMeta();
						HashMap<String, Object> codes = getCodes();
						codes.put("%levelCost%", levelCost);
						combineMeta.setDisplayName(Chatable.get().getMessage(codes, "grindstone.take-enchantments"));
						combineMeta.setLore(lore);
						combine.setItemMeta(combineMeta);
					}
				} else {
					combinedItem = null;
					combine = new ItemStack(Material.RED_STAINED_GLASS_PANE);
					ItemMeta combineMeta = combine.getItemMeta();
					combineMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "grindstone.cannot-combine"));
					combine.setItemMeta(combineMeta);
				}
			} else if (playerItems.size() == 1) {
				if (playerItems.get(0).getItemMeta().hasEnchants() || playerItems.get(0).getType() == Material.ENCHANTED_BOOK) {
					combinedItem = grindstone.getCombinedItem();
					combine = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
					ItemMeta combineMeta = combine.getItemMeta();
					combineMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "grindstone.remove-enchants"));
					combine.setItemMeta(combineMeta);
				} else {
					combinedItem = null;
					combine = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
					ItemMeta combineMeta = combine.getItemMeta();
					List<String> lore = new ArrayList<String>();
					lore.addAll(Chatable.get().getMessages(getCodes(), "grindstone.no-enchants-lore"));
					combineMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "grindstone.no-enchants"));
					combineMeta.setLore(lore);
					combine.setItemMeta(combineMeta);
				}
			} else {
				combinedItem = null;
				combine = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
				ItemMeta combineMeta = combine.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.addAll(Chatable.get().getMessages(getCodes(), "grindstone.no-items-lore"));
				combineMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "grindstone.no-items"));
				combineMeta.setLore(lore);
				combine.setItemMeta(combineMeta);
			}

			for(int i = 0; i < playerItems.size(); i++)
				if (i == 0) inv.setItem(2, playerItems.get(i));
				else if (i == 1) inv.setItem(11, playerItems.get(i));

			if (combinedItem != null) {
				if (!takeEnchantments) combinedItem = AnvilNMS.setRepairCost(combinedItem, 0);
				else if (ConfigString.SET_REPAIR_COST.getBoolean()) combinedItem = AnvilNMS.setRepairCost(combinedItem, AnvilNMS.getRepairCost(playerItems.get(0)));
				else
					combinedItem = AnvilNMS.setRepairCost(combinedItem, 0);
				inv.setItem(7, combinedItem);
			}
			inv.setItem(5, combine);

			if (ConfigUtils.useLegacyGrindstone()) {
				for(int i = 27; i < 36; i++)
					inv.setItem(i, mirror);
				ItemStack anvil = new ItemStack(Material.ANVIL);
				ItemMeta anvilMeta = anvil.getItemMeta();
				anvilMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "grindstone.anvil"));
				anvilMeta.setLore(Arrays.asList(Chatable.get().getMessage(getCodes(), "grindstone.switch-to-anvil")));
				anvil.setItemMeta(anvilMeta);
				inv.setItem(31, anvil);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (playerItems.size() - 1 >= 0) {
				ItemStack item = playerItems.get(playerItems.size() - 1);
				if (removeItem(playerItems.size() - 1)) ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
			}
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
	public Inventory getInventory() {
		return inventory;
	}

	public void combine() {
		if (inventory.getItem(5).getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
			if (takeEnchantments) {
				int levelCost = grindstone.getTakeCost();
				if (player.getGameMode() != GameMode.CREATIVE) {
					if (levelCost > player.getLevel()) {
						Chatable.get().sendMessage(player, Chatable.get().getMessage(getCodes(), "grindstone.message-cannot-combine"));
						return;
					}
					player.setLevel(player.getLevel() - levelCost);
				}
				if (player.getGameMode() != GameMode.CREATIVE) player.setLevel(player.getLevel() - levelCost);
				ItemUtils.giveItemToPlayer(player, grindstone.getTakenItem(), player.getLocation(), false);
				playGrindstoneSound();
				grindstone = null;
				playerItems.remove(1);
				if (ConfigString.DESTROY_TAKE_ITEM.getBoolean()) playerItems.remove(0);
				else
					playerItems.set(0, EnchantmentUtils.removeAllEnchantments(playerItems.get(0), true));
			} else {
				ItemUtils.giveItemToPlayer(player, grindstone.getCombinedItem(), player.getLocation(), false);
				playGrindstoneSound();
				if (block == null) LocationUtils.dropExperience(player.getLocation(), grindstone.getExperience(), true);
				else
					LocationUtils.dropExperience(block.getLocation(), grindstone.getExperience(), true);
				grindstone = null;
				playerItems.clear();
			}
		} else if (takeEnchantments) Chatable.get().sendMessage(player, Chatable.get().getMessage(getCodes(), "grindstone.message-cannot-take-enchantments"));
		else
			Chatable.get().sendMessage(player, Chatable.get().getMessage(getCodes(), "grindstone.message-cannot-combine"));
	}

	public boolean addItem(ItemStack item) {
		if (playerItems.size() >= 2) return false;
		if (item.getItemMeta() instanceof Damageable || item.getItemMeta().hasEnchants()) {
			playerItems.add(item);
			return true;
		}
		return false;
	}

	public boolean removeItem(int slot) {
		if (slot == 2) slot = 0;
		else if (slot == 11) slot = 1;
		return playerItems.remove(slot) != null;
	}

	@Override
	public List<ItemStack> getItems() {
		return playerItems;
	}

	@Override
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public void setItemName(String name) {

	}

	@Override
	public void close(boolean external) {
		if (EnchantmentSolution.getPlugin().hasInventory(this)) {
			for(ItemStack item: getItems())
				ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
			EnchantmentSolution.getPlugin().removeInventory(this);
			if (!external) player.closeInventory();
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

	private void playGrindstoneSound() {
		Sound sound = Sound.BLOCK_ANVIL_USE;
		try {
			sound = Sound.valueOf("BLOCK_GRINDSTONE_USE");
		} catch (Exception ex) {}
		if (block == null) player.getWorld().playSound(player.getLocation(), sound, 1, 1);
		else
			block.getWorld().playSound(block.getLocation(), sound, 1, 1);
	}
}
