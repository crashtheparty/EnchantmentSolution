package org.ctp.enchantmentsolution.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;
import org.ctp.enchantmentsolution.enchantments.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.PlayerLevels;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.RomanNumerals;

public class EnchantmentTable {

	private Player player;
	private Inventory inventory;
	private List<ItemStack> playerItems;
	private int books;
	private boolean openingNew;

	public EnchantmentTable(Player player, int books) {
		this.setPlayer(player);
		this.playerItems = new ArrayList<ItemStack>();
		this.setBooks(books);
	}

	public void setInventory() {
		setInventory(playerItems);
	}

	public void setInventory(List<ItemStack> items) {
		openingNew = true;
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLUE
				+ "Enchantment Table");

		ItemStack topLeft = new ItemStack(Material.BOOK);
		ItemMeta topLeftMeta = topLeft.getItemMeta();
		topLeftMeta.setDisplayName("Enchantment Instructions.");
		topLeftMeta.setLore(Arrays.asList(
				"Click items to put them on the left.",
				"You will see a list of books with the level",
				" and lapis needed to enchant.", "Select a book to enchant.",
				"Select the item again to remove.",
				"You may see up to 4 items at a time."));
		topLeft.setItemMeta(topLeftMeta);
		inv.setItem(0, topLeft);

		ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta mirrorMeta = mirror.getItemMeta();
		mirrorMeta.setDisplayName(ChatColor.WHITE + "");
		mirror.setItemMeta(mirrorMeta);
		
		for(int i = 0; i < 54; i++) {
			if(i % 9 == 1 || i % 9 == 2 || i / 9 == 1) {
				inv.setItem(i, mirror);
			}
		}

		ItemStack lapisOne = new ItemStack(Material.LAPIS_LAZULI);
		ItemMeta lapisOneMeta = lapisOne.getItemMeta();

		List<Integer> levelList = PlayerLevels.getIntList(player, books);
		if (levelList == null) {
			new PlayerLevels(getBooks(), getPlayer(), Material.AIR);
			levelList = PlayerLevels.getIntList(player, books);
			if (levelList == null) {
				ChatUtils.sendMessage(player, "There was an error generating enchantments.");
				return;
			}
		}

		lapisOneMeta.setDisplayName("Level 1 Enchant.");
		lapisOneMeta.setLore(Arrays.asList(
				"Lvl Req: " + levelList.get(0) + ".", "Lvl Cost: 1."));
		lapisOne.setItemMeta(lapisOneMeta);

		inv.setItem(3, lapisOne);

		ItemStack lapisTwo = new ItemStack(Material.LAPIS_LAZULI, 2);
		ItemMeta lapisTwoMeta = lapisTwo.getItemMeta();
		lapisTwoMeta.setDisplayName("Level 2 Enchant.");
		lapisTwoMeta.setLore(Arrays.asList(
				"Lvl Req: " + levelList.get(1) + ".", "Lvl Cost: 2."));
		lapisTwo.setItemMeta(lapisTwoMeta);

		inv.setItem(4, lapisTwo);

		ItemStack lapisThree = new ItemStack(Material.LAPIS_LAZULI, 3);
		ItemMeta lapisThreeMeta = lapisThree.getItemMeta();
		lapisThreeMeta.setDisplayName("Level 3 Enchant.");
		lapisThreeMeta.setLore(Arrays.asList("Lvl Req: " + levelList.get(2)
				+ ".", "Lvl Cost: 3."));
		lapisThree.setItemMeta(lapisThreeMeta);

		inv.setItem(5, lapisThree);

		if (levelList.size() < 4 || levelList.get(3) == -1) {
			String message = "";
			if (levelList.size() < 4) {
				message = "Level 50 enchantments disabled.";
			} else {
				message = "Requires 15 bookshelves around table.";
			}
			ItemStack disable = new ItemStack(Material.BARRIER);
			ItemMeta disableFour = disable.getItemMeta();
			disableFour.setDisplayName("Level 4 Enchant.");
			disableFour.setLore(Arrays.asList(message));
			disable.setItemMeta(disableFour);

			inv.setItem(6, disable);

			ItemMeta disableFive = disable.getItemMeta();
			disableFive.setDisplayName("Level 5 Enchant.");
			disableFive.setLore(Arrays.asList(message));
			disable.setItemMeta(disableFive);

			inv.setItem(7, disable);

			ItemMeta disableSix = disable.getItemMeta();
			disableSix.setDisplayName("Level 6 Enchant.");
			disableSix.setLore(Arrays.asList(message));
			disable.setItemMeta(disableSix);

			inv.setItem(8, disable);
		} else {
			ItemStack lapisFour = new ItemStack(Material.LAPIS_LAZULI, 4);
			ItemMeta lapisFourMeta = lapisFour.getItemMeta();
			lapisFourMeta.setDisplayName("Level 4 Enchant.");
			lapisFourMeta.setLore(Arrays.asList("Lvl Req: " + levelList.get(3)
					+ ".", "Lvl Cost: 4."));
			lapisFour.setItemMeta(lapisFourMeta);

			inv.setItem(6, lapisFour);

			ItemStack lapisFive = new ItemStack(Material.LAPIS_LAZULI, 5);
			ItemMeta lapisFiveMeta = lapisFive.getItemMeta();
			lapisFiveMeta.setDisplayName("Level 5 Enchant.");
			lapisFiveMeta.setLore(Arrays.asList("Lvl Req: " + levelList.get(4)
					+ ".", "Lvl Cost: 5."));
			lapisFive.setItemMeta(lapisFiveMeta);

			inv.setItem(7, lapisFive);

			ItemStack lapisSix = new ItemStack(Material.LAPIS_LAZULI, 6);
			ItemMeta lapisSixMeta = lapisSix.getItemMeta();
			lapisSixMeta.setDisplayName("Level 6 Enchant.");
			lapisSixMeta.setLore(Arrays.asList(
					"Lvl Req: " + levelList.get(5) + ".", "Lvl Cost: 6."));
			lapisSix.setItemMeta(lapisSixMeta);

			inv.setItem(8, lapisSix);
		}
		
		mirror = new ItemStack(Material.RED_STAINED_GLASS_PANE);
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
						if (name == null) {
							name = item.getType().name();
						}
						bookMeta.setDisplayName(name + " Level " + (extra - 2)
								+ " Enchants");
						String lapisString = ChatColor.GREEN + "Lapis Cost: "
								+ (extra - 2);
						String levelReq = ChatColor.GREEN + "Level Req: "
								+ levelList.get(extra - 3);
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
							lapisString = ChatColor.RED + "Lapis Cost: "
									+ (extra - 2);
						}
						if (player.getLevel() < levelList.get(extra - 3)
								&& player.getGameMode().equals(
										GameMode.SURVIVAL)) {
							levelReq = ChatColor.RED + "Level Req: "
									+ levelList.get(extra - 3);
						}
						bookMeta.setLore(Arrays.asList(
								levelReq,
								lapisString,
								RomanNumerals.returnEnchantmentName(enchants
										.get(0).getEnchant(), enchants.get(0)
										.getLevel())
										+ "..."));
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

	public int getBooks() {
		return books;
	}

	public void setBooks(int books) {
		this.books = books;
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

	public boolean openingNew() {
		if (openingNew) {
			openingNew = false;
			return true;
		}
		return false;
	}

	public void enchantItem(int slot, int level) {
		ItemStack enchantableItem = playerItems.get(slot);
		int itemSlot = 17 + (9 * slot) + (4 + level);
		ItemStack enchantItem = inventory.getItem(itemSlot);
		ItemMeta meta = enchantItem.getItemMeta();
		for (String string : meta.getLore()) {
			if (string.contains(ChatColor.RED + "")) {
				ChatUtils.sendMessage(player, "You do not meet the requirements to enchant this item.");
				return;
			}
		}
		PlayerLevels levels = PlayerLevels.getPlayerLevels(getBooks(), player,
				enchantableItem.getType());
		if (levels == null) {
			ChatUtils.sendMessage(player, "This item does not have enchantments generated.");
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
}
