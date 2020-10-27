package org.ctp.enchantmentsolution.inventory;

import java.util.*;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.item.ItemData;
import org.ctp.crashapi.item.ItemSerialization;
import org.ctp.crashapi.item.ItemType;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.generate.TableEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentList;
import org.ctp.enchantmentsolution.enchantments.helper.LevelList;
import org.ctp.enchantmentsolution.nms.EnchantItemCriterion;
import org.ctp.enchantmentsolution.nms.PersistenceNMS;
import org.ctp.enchantmentsolution.utils.compatibility.JobsUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.config.Type;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class EnchantmentTable implements InventoryData {

	private Player player;
	private Inventory inventory;
	private List<ItemStack> playerItems;
	private ItemStack lapisStack;
	private Block block;
	private boolean opening;

	public EnchantmentTable(Player player, Block block) {
		setPlayer(player);
		playerItems = new ArrayList<ItemStack>();
		this.block = block;
	}

	@Override
	public void setInventory() {
		setInventory(playerItems);
	}

	@Override
	public void setInventory(List<ItemStack> items) {
		try {
			Inventory inv = Bukkit.createInventory(null, 54, Chatable.get().getMessage(getCodes(), "table.name"));
			inv = open(inv);
			boolean useLapis = ConfigString.LAPIS_IN_TABLE.getBoolean();

			ItemStack topLeft = new ItemStack(Material.BOOK);
			ItemMeta topLeftMeta = topLeft.getItemMeta();
			topLeftMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "table.instructions-title"));
			topLeftMeta.setLore(Chatable.get().getMessages(getCodes(), "table.instructions"));
			topLeft.setItemMeta(topLeftMeta);
			inv.setItem(0, topLeft);

			ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta mirrorMeta = mirror.getItemMeta();
			mirrorMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "table.black-mirror"));
			mirror.setItemMeta(mirrorMeta);

			for(int i = 0; i < 54; i++)
				if (i % 9 == 1 || i % 9 == 2 || i / 9 == 1) inv.setItem(i, mirror);

			TableEnchantments table = TableEnchantments.getTableEnchantments(player, null, getBooks());
			LevelList list = table.getLevelList();

			for(int i = 1; i <= 6; i++) {
				ItemStack lapis = new ItemStack(Material.LAPIS_LAZULI, i);
				ItemMeta lapisMeta = lapis.getItemMeta();
				HashMap<String, Object> loreCodes = getCodes();
				loreCodes.put("%level%", i);
				List<String> messages = new ArrayList<String>();
				if (i > 3 && (list.getList()[i - 1] == null || list.getList()[i - 1].getLevel() == -1)) {
					lapisMeta.setDisplayName(Chatable.get().getMessage(loreCodes, "table.enchant-level"));
					if (list.getList()[i - 1].getLevel() == -1) messages = Arrays.asList(Chatable.get().getMessage(getCodes(), "table.level-fifty-lack"));
					else
						messages = Arrays.asList(Chatable.get().getMessage(getCodes(), "table.level-fifty-disabled"));
					lapisMeta.setLore(messages);
				} else {
					loreCodes.put("%levelsTaken%", i);
					loreCodes.put("%levelReq%", list.getList()[i - 1].getLevel());
					lapisMeta.setDisplayName(Chatable.get().getMessage(loreCodes, "table.enchant-level"));
					lapisMeta.setLore(Chatable.get().getMessages(loreCodes, "table.enchant-level-lore"));
				}
				lapis.setItemMeta(lapisMeta);

				inv.setItem(i + 2, lapis);
			}

			mirror = new ItemStack(Material.RED_STAINED_GLASS_PANE);
			mirrorMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "table.red-mirror"));
			mirror.setItemMeta(mirrorMeta);

			int start = 18;

			for(int i = start; i < 54; i++)
				if (i % 9 != 1 && i % 9 != 2) inv.setItem(i, mirror);

			for(int i = 0; i < playerItems.size(); i++) {
				ItemStack item = playerItems.get(i);
				inv.setItem(start, item);

				if (EnchantmentUtils.isEnchantable(item)) {
					EnchantmentList[] enchantmentLists = table.getEnchantments(new ItemData(item));

					int extra = 3;
					for(EnchantmentList enchantmentList: enchantmentLists) {
						if (enchantmentList != null && enchantmentList.getEnchantments() != null && enchantmentList.getEnchantments().size() > 0) {
							List<EnchantmentLevel> enchants = enchantmentList.getEnchantments();
							ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
							ItemMeta bookMeta = book.getItemMeta();
							String name = item.getItemMeta().getDisplayName();
							if (name == null || name.equals("")) name = ConfigUtils.getString(Type.LANGUAGE, "vanilla." + ItemType.getUnlocalizedName(item.getType()));
							HashMap<String, Object> loreCodes = getCodes();
							loreCodes.put("%level%", extra - 2);
							loreCodes.put("%name%", name);
							bookMeta.setDisplayName(Chatable.get().getMessage(loreCodes, "table.item-enchant-name"));

							loreCodes = getCodes();
							loreCodes.put("%cost%", extra - 2);

							String lapisString = Chatable.get().getMessage(loreCodes, "table.lapis-cost-okay");
							int numLapis = 0;
							if (useLapis) {
								if (lapisStack != null) numLapis = lapisStack.getAmount();
							} else
								for(int j = 0; j < player.getInventory().getSize(); j++) {
									ItemStack checkLapis = player.getInventory().getItem(j);
									if (checkLapis != null && checkLapis.getType().equals(Material.LAPIS_LAZULI)) numLapis += checkLapis.getAmount();
								}
							if (numLapis < extra - 2 && player.getGameMode().equals(GameMode.SURVIVAL)) lapisString = Chatable.get().getMessage(loreCodes, "table.lapis-cost-lack");
							loreCodes.remove("%cost%");
							int levelReq = list.getList()[extra - 3].getLevel();
							loreCodes.put("%levelReq%", levelReq);
							String levelReqString = Chatable.get().getMessage(loreCodes, "table.level-cost-okay");
							if (player.getLevel() < levelReq && player.getGameMode().equals(GameMode.SURVIVAL)) levelReqString = Chatable.get().getMessage(loreCodes, "table.level-cost-lack");
							loreCodes.remove("%levelReq%");
							loreCodes.put("%levelsTaken%", extra - 2);
							String levelsTaken = Chatable.get().getMessage(loreCodes, "table.level-taken-okay");
							if (player.getLevel() < levelReq && player.getGameMode().equals(GameMode.SURVIVAL)) levelsTaken = Chatable.get().getMessage(loreCodes, "table.level-taken-lack");
							loreCodes.remove("%levelsTaken%");
							loreCodes.put("%enchant%", ChatColor.stripColor(PersistenceNMS.returnEnchantmentName(enchants.get(0).getEnchant(), enchants.get(0).getLevel())));
							bookMeta.setLore(Arrays.asList(levelReqString, lapisString, levelsTaken, Chatable.get().getMessage(loreCodes, "table.enchant-name")));
							book.setItemMeta(bookMeta);
							inv.setItem(start + extra, book);
						} else
							inv.setItem(extra + start, mirror);
						extra++;
						if (extra >= 9) break;

					}
				} else
					for(int j = 3; j < 9; j++)
						inv.setItem(j + start, mirror);

				start += 9;
			}

			if (useLapis) if (lapisStack == null) {
				ItemStack blueMirror = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
				ItemMeta blueMirrorMeta = blueMirror.getItemMeta();
				blueMirrorMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "table.blue-mirror"));
				blueMirrorMeta.setLore(Chatable.get().getMessages(getCodes(), "table.blue-mirror-lore"));
				blueMirror.setItemMeta(blueMirrorMeta);
				inv.setItem(10, blueMirror);
			} else
				inv.setItem(10, lapisStack);
		} catch (Exception ex) {
			ex.printStackTrace();
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

	public ItemStack addToLapisStack(ItemStack item) {
		if (ConfigString.LAPIS_IN_TABLE.getBoolean()) {
			ItemStack clone = item.clone();
			if (lapisStack == null) {
				lapisStack = item;
				return new ItemStack(Material.AIR);
			}
			ItemSerialization serial = EnchantmentSolution.getPlugin().getItemSerial();
			if (serial.itemToData(lapisStack).equals(serial.itemToData(clone))) if (lapisStack.getAmount() < lapisStack.getType().getMaxStackSize()) if (lapisStack.getAmount() + clone.getAmount() > lapisStack.getType().getMaxStackSize()) {
				clone.setAmount(lapisStack.getAmount() + clone.getAmount() - lapisStack.getType().getMaxStackSize());
				lapisStack.setAmount(lapisStack.getType().getMaxStackSize());
			} else {
				lapisStack.setAmount(lapisStack.getAmount() + clone.getAmount());
				clone = new ItemStack(Material.AIR);
			}
			return clone;
		}
		return item;
	}

	public ItemStack removeFromLapisStack(int amount) {
		if (lapisStack == null) return null;
		ItemStack clone = lapisStack.clone();
		if (clone.getAmount() > amount) lapisStack.setAmount(clone.getAmount() - amount);
		else
			lapisStack = null;
		return clone;
	}

	public ItemStack removeFromLapisStack() {
		if (lapisStack == null) return null;
		ItemStack clone = lapisStack.clone();
		lapisStack = null;
		return clone;
	}

	public boolean addItem(ItemStack item) {
		if (playerItems.size() >= 4) return false;
		playerItems.add(item);
		return true;
	}

	public boolean removeItem(ItemStack item, int slot) {
		int newSlot = slot / 9 - 2;
		if (playerItems.get(newSlot).equals(item)) return playerItems.remove(newSlot) != null;
		return false;
	}

	@Override
	public List<ItemStack> getItems() {
		return playerItems;
	}

	public void enchantItem(int slot, int level) {
		ItemStack enchantableItem = playerItems.get(slot);
		int itemSlot = 17 + 9 * slot + 4 + level;
		ItemStack enchantItem = inventory.getItem(itemSlot);
		ItemMeta meta = enchantItem.getItemMeta();
		for(String string: meta.getLore())
			if (string.contains(ChatColor.RED + "")) {
				Chatable.get().sendMessage(player, Chatable.get().getMessage(getCodes(), "table.lack-reqs"));
				return;
			}

		TableEnchantments table = TableEnchantments.getTableEnchantments(player, null, getBooks());
		if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
			player.setLevel(player.getLevel() - level - 1);
			int remove = level + 1;
			if (ConfigString.LAPIS_IN_TABLE.getBoolean()) removeFromLapisStack(remove);
			else
				for(int i = 0; i < player.getInventory().getSize(); i++) {
					if (remove == 0) break;
					ItemStack item = player.getInventory().getItem(i);
					if (item != null && item.getType() == Material.LAPIS_LAZULI) {
						if (item.getAmount() - remove <= 0) {
							remove -= item.getAmount();
							player.getInventory().setItem(i, new ItemStack(Material.AIR));
						} else {
							item.setAmount(item.getAmount() - remove);
							break;
						}
					} else {}
				}
		}
		List<EnchantmentLevel> enchLevels = table.getEnchantments(new ItemData(enchantableItem))[level].getEnchantments();
		if (playerItems.get(slot).getType() == Material.BOOK && ConfigString.USE_ENCHANTED_BOOKS.getBoolean()) enchantableItem = EnchantmentUtils.convertToEnchantedBook(enchantableItem);
		player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
		enchantableItem = EnchantmentUtils.addEnchantmentsToItem(enchantableItem, enchLevels);
		playerItems.set(slot, enchantableItem);

		TableEnchantments.removeTableEnchantments(player);
		setInventory(playerItems);
		player.setStatistic(Statistic.ITEM_ENCHANTED, player.getStatistic(Statistic.ITEM_ENCHANTED) + 1);
		EnchantItemCriterion.enchantItemTrigger(player, enchantableItem);
		if (EnchantmentSolution.getPlugin().isJobsEnabled()) JobsUtils.sendEnchantAction(player, enchantItem, enchantableItem, enchLevels);
	}

	@Override
	public Block getBlock() {
		return block;
	}

	public int getBooks() {
		return EnchantmentUtils.getBookshelves(block.getLocation());
	}

	@Override
	public void close(boolean external) {
		if (EnchantmentSolution.getPlugin().hasInventory(this)) {
			for(ItemStack item: getItems())
				ItemUtils.giveItemToPlayer(player, item, player.getLocation(), false);
			if (lapisStack != null) ItemUtils.giveItemToPlayer(player, lapisStack, player.getLocation(), false);
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
	public void setItemName(String name) {
		return;
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
}
