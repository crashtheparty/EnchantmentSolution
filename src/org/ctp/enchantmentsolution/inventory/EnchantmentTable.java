package org.ctp.enchantmentsolution.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.PlayerLevels;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ItemUtils;
import org.ctp.enchantmentsolution.utils.RomanNumerals;

public class EnchantmentTable implements InventoryData {

	private Player player;
	private Inventory inventory;
	private List<ItemStack> playerItems;
	private Block block;

	public EnchantmentTable(Player player, Block block) {
		this.setPlayer(player);
		this.playerItems = new ArrayList<ItemStack>();
		this.block = block;
	}

	public void setInventory() {
		setInventory(playerItems);
	}

	public void setInventory(List<ItemStack> items) {
		Inventory inv = Bukkit.createInventory(null, 54, ChatUtils.getMessage(getCodes(), "table.name"));

		ItemStack topLeft = new ItemStack(Material.BOOK);
		ItemMeta topLeftMeta = topLeft.getItemMeta();
		topLeftMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "table.instructions-title"));
		topLeftMeta.setLore(ChatUtils.getMessages(getCodes(), "table.instructions"));
		topLeft.setItemMeta(topLeftMeta);
		inv.setItem(0, topLeft);

		ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta mirrorMeta = mirror.getItemMeta();
		mirrorMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "table.black-mirror"));
		mirror.setItemMeta(mirrorMeta);
		
		for(int i = 0; i < 54; i++) {
			if(i % 9 == 1 || i % 9 == 2 || i / 9 == 1) {
				inv.setItem(i, mirror);
			}
		}

		List<Integer> levelList = PlayerLevels.getIntList(player, getBooks());
		if (levelList == null) {
			new PlayerLevels(getBooks(), getPlayer(), Material.AIR);
			levelList = PlayerLevels.getIntList(player, getBooks());
			if (levelList == null) {
				ChatUtils.sendMessage(player, ChatUtils.getMessage(getCodes(), "table.generate-enchants-error"));
				return;
			}
		}

		ItemStack lapisOne = new ItemStack(Material.LAPIS_LAZULI);
		ItemMeta lapisOneMeta = lapisOne.getItemMeta();
		HashMap<String, Object> loreCodes = getCodes();
		loreCodes.put("%level%", 1);
		loreCodes.put("%levelReq%", levelList.get(0));
		lapisOneMeta.setDisplayName(ChatUtils.getMessage(loreCodes, "table.enchant-level"));
		lapisOneMeta.setLore(ChatUtils.getMessages(loreCodes, "table.enchant-level-lore"));
		lapisOne.setItemMeta(lapisOneMeta);

		inv.setItem(3, lapisOne);

		ItemStack lapisTwo = new ItemStack(Material.LAPIS_LAZULI, 2);
		ItemMeta lapisTwoMeta = lapisTwo.getItemMeta();
		loreCodes.put("%level%", 2);
		loreCodes.put("%levelReq%", levelList.get(1));
		lapisTwoMeta.setDisplayName(ChatUtils.getMessage(loreCodes, "table.enchant-level"));
		lapisTwoMeta.setLore(ChatUtils.getMessages(loreCodes, "table.enchant-level-lore"));
		lapisTwo.setItemMeta(lapisTwoMeta);

		inv.setItem(4, lapisTwo);

		ItemStack lapisThree = new ItemStack(Material.LAPIS_LAZULI, 3);
		ItemMeta lapisThreeMeta = lapisThree.getItemMeta();
		loreCodes.put("%level%", 3);
		loreCodes.put("%levelReq%", levelList.get(2));
		lapisThreeMeta.setDisplayName(ChatUtils.getMessage(loreCodes, "table.enchant-level"));
		lapisThreeMeta.setLore(ChatUtils.getMessages(loreCodes, "table.enchant-level-lore"));
		lapisThree.setItemMeta(lapisThreeMeta);

		inv.setItem(5, lapisThree);

		if (levelList.size() < 4 || levelList.get(3) == -1) {
			String message = "";
			if (levelList.size() < 4) {
				message = ChatUtils.getMessage(getCodes(), "table.level-fifty-disabled");
			} else {
				message = ChatUtils.getMessage(getCodes(), "table.level-fifty-disabled");
			}
			ItemStack disable = new ItemStack(Material.BARRIER);
			ItemMeta disableFour = disable.getItemMeta();
			loreCodes.put("%level%", 4);
			disableFour.setDisplayName(ChatUtils.getMessage(loreCodes, "table.enchant-level"));
			disableFour.setLore(Arrays.asList(message));
			disable.setItemMeta(disableFour);

			inv.setItem(6, disable);

			ItemMeta disableFive = disable.getItemMeta();
			loreCodes.put("%level%", 5);
			disableFive.setDisplayName(ChatUtils.getMessage(loreCodes, "table.enchant-level"));
			disableFive.setLore(Arrays.asList(message));
			disable.setItemMeta(disableFive);

			inv.setItem(7, disable);

			ItemMeta disableSix = disable.getItemMeta();
			loreCodes.put("%level%", 6);
			disableSix.setDisplayName(ChatUtils.getMessage(loreCodes, "table.enchant-level"));
			disableSix.setLore(Arrays.asList(message));
			disable.setItemMeta(disableSix);

			inv.setItem(8, disable);
		} else {
			ItemStack lapisFour = new ItemStack(Material.LAPIS_LAZULI, 4);
			ItemMeta lapisFourMeta = lapisFour.getItemMeta();
			loreCodes.put("%level%", 4);
			loreCodes.put("%levelReq%", levelList.get(3));
			lapisFourMeta.setDisplayName(ChatUtils.getMessage(loreCodes, "table.enchant-level"));
			lapisFourMeta.setLore(ChatUtils.getMessages(loreCodes, "table.enchant-level-lore"));
			lapisFour.setItemMeta(lapisFourMeta);

			inv.setItem(6, lapisFour);

			ItemStack lapisFive = new ItemStack(Material.LAPIS_LAZULI, 5);
			ItemMeta lapisFiveMeta = lapisFive.getItemMeta();
			loreCodes.put("%level%", 5);
			loreCodes.put("%levelReq%", levelList.get(4));
			lapisFiveMeta.setDisplayName(ChatUtils.getMessage(loreCodes, "table.enchant-level"));
			lapisFiveMeta.setLore(ChatUtils.getMessages(loreCodes, "table.enchant-level-lore"));
			lapisFive.setItemMeta(lapisFiveMeta);

			inv.setItem(7, lapisFive);

			ItemStack lapisSix = new ItemStack(Material.LAPIS_LAZULI, 6);
			ItemMeta lapisSixMeta = lapisSix.getItemMeta();
			loreCodes.put("%level%", 6);
			loreCodes.put("%levelReq%", levelList.get(5));
			lapisSixMeta.setDisplayName(ChatUtils.getMessage(loreCodes, "table.enchant-level"));
			lapisSixMeta.setLore(ChatUtils.getMessages(loreCodes, "table.enchant-level-lore"));
			lapisSix.setItemMeta(lapisSixMeta);

			inv.setItem(8, lapisSix);
		}
		
		mirror = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		mirrorMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "table.red-mirror"));
		mirror.setItemMeta(mirrorMeta);

		int start = 18;
		for (int i = 0; i < playerItems.size(); i++) {
			ItemStack item = playerItems.get(i);
			inv.setItem(start, item);

			if (Enchantments.isEnchantable(item)) {
				PlayerLevels levels = PlayerLevels.getPlayerLevels(getBooks(),
						player, item.getType());
				if (levels == null) {
					levels = new PlayerLevels(getBooks(), player,
							item.getType());
				}

				int extra = 3;
				for (List<EnchantmentLevel> enchants : levels.getEnchants()) {
					if (enchants.size() > 0) {
						ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
						ItemMeta bookMeta = book.getItemMeta();
						String name = item.getItemMeta().getDisplayName();
						if (name == null || name.equals("")) {
							name = item.getType().name();
						}
						loreCodes = getCodes();
						loreCodes.put("%level%", extra - 2);
						loreCodes.put("%name%", name);
						bookMeta.setDisplayName(ChatUtils.getMessage(loreCodes, "table.item-enchant-name"));

						loreCodes = getCodes();
						loreCodes.put("%cost%", extra - 2);
						
						String lapisString = ChatUtils.getMessage(loreCodes, "table.lapis-cost-okay");
						int numLapis = 0;
						for (int j = 1; j <= 64; j++) {
							ItemStack lapisStack = new ItemStack(Material.LAPIS_LAZULI, j);
							if (player.getInventory().contains(lapisStack)) {
								numLapis += j;
							}
						}
						if (numLapis < (extra - 2)
								&& player.getGameMode().equals(
										GameMode.SURVIVAL)) {
							lapisString = ChatUtils.getMessage(loreCodes, "table.lapis-cost-lack");
						}
						loreCodes.remove("%cost%");
						loreCodes.put("%levelReq%", levelList.get(extra - 3));
						String levelReq = ChatUtils.getMessage(loreCodes, "table.level-cost-okay");
						if (player.getLevel() < levelList.get(extra - 3)
								&& player.getGameMode().equals(
										GameMode.SURVIVAL)) {
							levelReq = ChatUtils.getMessage(loreCodes, "table.level-cost-lack");
						}
						loreCodes.remove("%levelReq%");
						loreCodes.put("%enchant%", RomanNumerals.returnEnchantmentName(enchants
								.get(0).getEnchant(), enchants.get(0)
								.getLevel()));
						bookMeta.setLore(Arrays.asList(
								levelReq,
								lapisString,
								ChatUtils.getMessage(loreCodes, "table.enchant-name")));
						book.setItemMeta(bookMeta);
						inv.setItem(start + extra, book);
					} else {
						inv.setItem(extra + start, mirror);
					}
					extra++;
					if (extra >= 9)
						break;

				}
				if(levels.getEnchants().size() == 0 || extra < 9) {
					for(int j = extra; j < 9; j++) {
						inv.setItem(j + start, mirror);
					}
				}
			} else {
				for(int j = 3; j < 9; j++) {
					inv.setItem(j + start, mirror);
				}
			}

			start += 9;
		}
		
		for(int i = start; i < 54; i++) {
			if(i % 9 != 1 && i % 9 != 2) {
				inv.setItem(i, mirror);
			}
		}
		inventory = inv;
		player.openInventory(inv);
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
		if (playerItems.size() >= 4) {
			return false;
		}
		playerItems.add(item);
		return true;
	}

	public boolean removeItem(ItemStack item) {
		return playerItems.remove(item);
	}

	public boolean removeItem(ItemStack item, int slot) {
		int newSlot = (slot / 9) - 2;
		if (playerItems.get(newSlot).equals(item)) {
			return playerItems.remove(newSlot) != null;
		}
		return false;
	}

	public List<ItemStack> getItems() {
		return playerItems;
	}

	public void enchantItem(int slot, int level) {
		ItemStack enchantableItem = playerItems.get(slot);
		int itemSlot = 17 + (9 * slot) + (4 + level);
		ItemStack enchantItem = inventory.getItem(itemSlot);
		ItemMeta meta = enchantItem.getItemMeta();
		for (String string : meta.getLore()) {
			if (string.contains(ChatColor.RED + "")) {
				ChatUtils.sendMessage(player, ChatUtils.getMessage(getCodes(), "table.lack-reqs"));
				return;
			}
		}
				
		PlayerLevels levels = PlayerLevels.getPlayerLevels(getBooks(), player,
				enchantableItem.getType());
		if (levels == null) {
			ChatUtils.sendMessage(player, ChatUtils.getMessage(getCodes(), "table.lack-enchants"));
			setInventory(playerItems);
			return;
		}
		if (player.getGameMode().equals(GameMode.SURVIVAL)) {
			player.setLevel(player.getLevel() - level - 1);
			Dye lapis = new Dye();
			lapis.setColor(DyeColor.BLUE);
			ItemStack lapisStack = new ItemStack(Material.LAPIS_LAZULI, level + 1);
			player.getInventory().removeItem(lapisStack);
		}
		enchantableItem = Enchantments.addEnchantmentsToItem(enchantableItem,
				levels.getEnchants().get(level));
		playerItems.set(slot, enchantableItem);
		PlayerLevels.removePlayerLevels(player);
		setInventory(playerItems);
	}

	@Override
	public Block getBlock() {
		return block;
	}
	
	public int getBooks() {
		return Enchantments.getBookshelves(block.getLocation());
	}

	@Override
	public void close(boolean external) {
		for(ItemStack item : getItems()){
			ItemUtils.giveItemToPlayer(player, item, player.getLocation());
		}
		if(!external) {
			player.closeInventory();
		}
		EnchantmentSolution.removeInventory(this);
	}

	public HashMap<String, Object> getCodes() {
		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%player%", player.getName());
		return codes;
	}

	@Override
	public void setItemName(String name) {
		return;
	}
}
