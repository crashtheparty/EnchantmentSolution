package org.ctp.enchantmentsolution.inventory.rpg;

import java.math.BigInteger;
import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.crashapi.inventory.InventoryData;
import org.ctp.crashapi.inventory.Pageable;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.rpg.RPGPlayer;
import org.ctp.enchantmentsolution.rpg.RPGUtils;

public class RPGInventory implements InventoryData, Pageable {

	private Player player;
	private RPGPlayer rpg;
	private Inventory inventory;
	private Screen screen = Screen.LIST;
	private final int PAGING = 21;
	private int page = 1, enchPage = 1;
	private boolean opening;
	private CustomEnchantment enchant;
	private final List<Integer> titleLocs = Arrays.asList(3, 4, 5);
	private final List<Integer> locations = Arrays.asList(19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43);
	private final List<Integer> pagination = Arrays.asList(45, 53);
	private List<Integer> allLocations = new ArrayList<Integer>();
	private EnchantmentLevel buying;

	public RPGInventory(Player player) {
		this.player = player;
		rpg = RPGUtils.getPlayer(player);

		allLocations.addAll(titleLocs);
		allLocations.addAll(locations);
		allLocations.addAll(pagination);
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public Block getBlock() {
		return null;
	}

	@Override
	public void close(boolean external) {
		if (EnchantmentSolution.getPlugin().hasInventory(this)) {
			EnchantmentSolution.getPlugin().removeInventory(this);
			if (!external) player.getOpenInventory().close();
		}
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public void setInventory() {
		try {
			if (screen == Screen.LIST) {
				Inventory inv = Bukkit.createInventory(null, 54, Chatable.get().getMessage(getCodes(), "rpg.name"));
				inv = open(inv);

				ItemStack title = new ItemStack(Material.BOOK);
				ItemMeta titleMeta = title.getItemMeta();
				HashMap<String, Object> titleCodes = getCodes();
				titleCodes.put("%page%", page);
				titleMeta.setDisplayName(Chatable.get().getMessage(titleCodes, "rpg.title"));
				title.setItemMeta(titleMeta);
				inv.setItem(4, title);

				ItemStack instructions = new ItemStack(Material.WRITTEN_BOOK);
				ItemMeta instructionsMeta = instructions.getItemMeta();
				instructionsMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "rpg.instructions"));
				instructionsMeta.setLore(Chatable.get().getMessages(getCodes(), "rpg.instructions_lore"));
				instructions.setItemMeta(instructionsMeta);
				inv.setItem(3, instructions);

				ItemStack info = new ItemStack(Material.SUNFLOWER);
				ItemMeta infoMeta = info.getItemMeta();
				HashMap<String, Object> infoCodes = getCodes();
				infoCodes.put("%level%", rpg.getLevel());
				infoCodes.put("%experience%", rpg.getExperience());
				infoCodes.put("%points%", rpg.getPoints());
				infoCodes.put("%total_points%", RPGUtils.getPointsForLevel(rpg.getLevel()));
				infoMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "rpg.info"));
				infoMeta.setLore(Chatable.get().getMessages(infoCodes, "rpg.info_lore"));
				info.setItemMeta(infoMeta);
				inv.setItem(5, info);

				ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
				ItemMeta mirrorMeta = mirror.getItemMeta();
				mirrorMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "rpg.mirror"));
				mirror.setItemMeta(mirrorMeta);

				for(int i = 0; i < 54; i++)
					if (!allLocations.contains(i)) inv.setItem(i, mirror);

				List<CustomEnchantment> enchantments = RegisterEnchantments.getRegisteredEnchantmentsAlphabetical();

				for(int i = 0; i < PAGING; i++) {
					int num = PAGING * (page - 1) + i;
					if (enchantments.size() <= num) continue;
					CustomEnchantment enchant = enchantments.get(num);
					Enchantment ench = enchant.getRelativeEnchantment();

					ItemStack enchantment = new ItemStack(Material.BOOK);
					ItemMeta enchantmentMeta = enchantment.getItemMeta();
					if (rpg.hasEnchantment(ench, 1)) enchantment.setType(Material.ENCHANTED_BOOK);
					HashMap<String, Object> enchCodes = getCodes();
					enchCodes.put("%name%", enchant.getDisplayName());
					enchantmentMeta.setDisplayName(Chatable.get().getMessage(enchCodes, "rpg.enchantments"));
					HashMap<String, Object> enchLoreCodes = getCodes();
					String path = "rpg.enchantments_lore_unlocked";
					int levelPlusOne = rpg.getMaxLevel(enchant.getRelativeEnchantment()) + 1;
					enchLoreCodes.put("%level%", rpg.getMaxLevel(enchant.getRelativeEnchantment()));
					if (levelPlusOne - 1 < enchant.getMaxLevel()) {
						path = "rpg.enchantments_lore";
						BigInteger points = RPGUtils.getPointsForEnchantment(player, enchant.getRelativeEnchantment(), levelPlusOne);
						String pointsString = points.intValue() + "";
						if (points.intValue() == -2) pointsString = Chatable.get().getMessage(getCodes(), "rpg.no-permission");
						else if (points.intValue() == -1) pointsString = Chatable.get().getMessage(getCodes(), "rpg.invalid-enchantment");
						enchLoreCodes.put("%points%", pointsString);
						enchLoreCodes.put("%level_plus_one%", levelPlusOne);
					}
					enchantmentMeta.setLore(Chatable.get().getMessages(enchLoreCodes, path));
					enchantment.setItemMeta(enchantmentMeta);

					int loc = (int) (i / 7.0D) * 2 + 19 + i;
					inv.setItem(loc, enchantment);
				}

				if (enchantments.size() > PAGING * page) inv.setItem(53, nextPage());
				else
					inv.setItem(53, mirror);
				if (page != 1) inv.setItem(45, previousPage());
				else
					inv.setItem(45, mirror);
				inv.setItem(0, mirror);
			} else if (screen == Screen.ENCHANTMENT) {
				HashMap<String, Object> nameCodes = getCodes();
				nameCodes.put("%enchantment%", enchant.getName());
				Inventory inv = Bukkit.createInventory(null, 54, Chatable.get().getMessage(nameCodes, "rpg.name_enchantment"));
				inv = open(inv);

				ItemStack title = new ItemStack(Material.BOOK);
				ItemMeta titleMeta = title.getItemMeta();
				HashMap<String, Object> titleCodes = getCodes();
				titleCodes.put("%page%", enchPage);
				titleMeta.setDisplayName(Chatable.get().getMessage(titleCodes, "rpg.title"));
				title.setItemMeta(titleMeta);
				inv.setItem(4, title);

				ItemStack instructions = new ItemStack(Material.WRITTEN_BOOK);
				ItemMeta instructionsMeta = instructions.getItemMeta();
				instructionsMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "rpg.instructions"));
				instructionsMeta.setLore(Chatable.get().getMessages(getCodes(), "rpg.instructions_lore"));
				instructions.setItemMeta(instructionsMeta);
				inv.setItem(3, instructions);

				ItemStack info = new ItemStack(Material.SUNFLOWER);
				ItemMeta infoMeta = info.getItemMeta();
				HashMap<String, Object> infoCodes = getCodes();
				infoCodes.put("%level%", rpg.getLevel());
				infoCodes.put("%experience%", rpg.getExperience());
				infoCodes.put("%points%", rpg.getPoints());
				infoCodes.put("%total_points%", RPGUtils.getPointsForLevel(rpg.getLevel()));
				infoMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "rpg.info"));
				infoMeta.setLore(Chatable.get().getMessages(infoCodes, "rpg.info_lore"));
				info.setItemMeta(infoMeta);
				inv.setItem(5, info);

				ItemStack mirror = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
				ItemMeta mirrorMeta = mirror.getItemMeta();
				mirrorMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "rpg.mirror"));
				mirror.setItemMeta(mirrorMeta);

				for(int i = 0; i < 54; i++)
					if (!allLocations.contains(i)) inv.setItem(i, mirror);

				List<Integer> levels = new ArrayList<Integer>();
				for(int i = 1; i <= enchant.getMaxLevel(); i++)
					levels.add(i);

				for(int i = 0; i < PAGING; i++) {
					int num = PAGING * (enchPage - 1) + i;
					if (levels.size() <= num) continue;
					int level = levels.get(i);

					ItemStack levelItem = new ItemStack(Material.BOOK);
					ItemMeta levelItemMeta = levelItem.getItemMeta();
					if (rpg.hasEnchantment(enchant.getRelativeEnchantment(), level)) levelItem.setType(Material.ENCHANTED_BOOK);
					HashMap<String, Object> enchCodes = getCodes();
					enchCodes.put("%name%", enchant.getDisplayName());
					levelItemMeta.setDisplayName(Chatable.get().getMessage(enchCodes, "rpg.enchantment_levels"));
					HashMap<String, Object> enchLoreCodes = getCodes();
					String path = "rpg.enchantment_levels_lore_unlocked";
					if (rpg.getMaxLevel(enchant.getRelativeEnchantment()) < level) {
						path = "rpg.enchantment_levels_lore";
						BigInteger points = RPGUtils.getPointsForEnchantment(player, enchant.getRelativeEnchantment(), level);
						String pointsString = points.intValue() + "";
						if (points.intValue() == -2) pointsString = Chatable.get().getMessage(getCodes(), "rpg.no-permission");
						else if (points.intValue() == -1) pointsString = Chatable.get().getMessage(getCodes(), "rpg.invalid-enchantment");
						enchLoreCodes.put("%points%", pointsString);
						enchLoreCodes.put("%level%", level);
					}
					levelItemMeta.setLore(Chatable.get().getMessages(enchLoreCodes, path));
					levelItem.setItemMeta(levelItemMeta);

					int loc = (int) (i / 7.0D) * 2 + 19 + i;
					inv.setItem(loc, levelItem);
				}

				if (levels.size() > PAGING * enchPage) inv.setItem(53, nextPage());
				else
					inv.setItem(53, mirror);
				if (enchPage != 1) inv.setItem(45, previousPage());
				else
					inv.setItem(45, mirror);
				inv.setItem(0, goBack());
			} else if (screen == Screen.CONFIRM) {
				HashMap<String, Object> buyingCodes = getCodes();
				buyingCodes.put("%enchantment%", buying.getEnchant().getDisplayName());
				buyingCodes.put("%level%", buying.getLevel());
				Inventory inv = Bukkit.createInventory(null, 9, Chatable.get().getMessage(buyingCodes, "rpg.name_confirm"));
				inv = open(inv);

				ItemStack cancel = new ItemStack(Material.REDSTONE_BLOCK);
				ItemMeta cancelMeta = cancel.getItemMeta();
				cancelMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "rpg.cancel"));
				cancelMeta.setLore(Chatable.get().getMessages(getCodes(), "rpg.cancel_lore"));
				cancel.setItemMeta(cancelMeta);
				inv.setItem(2, cancel);

				ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
				ItemMeta confirmMeta = confirm.getItemMeta();
				HashMap<String, Object> confirmCodes = getCodes();
				confirmCodes.put("%enchantment%", buying.getEnchant().getDisplayName());
				confirmCodes.put("%level%", buying.getLevel());
				confirmCodes.put("%total_points%", RPGUtils.getBuyPoints(rpg, buying));
				confirmMeta.setDisplayName(Chatable.get().getMessage(getCodes(), "rpg.confirm"));
				confirmMeta.setLore(Chatable.get().getMessages(confirmCodes, "rpg.confirm_lore"));
				confirm.setItemMeta(confirmMeta);
				inv.setItem(6, confirm);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void setInventory(List<ItemStack> items) {
		setInventory();
	}

	@Override
	public void setItemName(String name) {}

	public void setEnchantment(int slot) {
		int loc = locations.indexOf(slot);
		int num = PAGING * (page - 1) + loc;
		enchant = RegisterEnchantments.getRegisteredEnchantmentsAlphabetical().get(num);
		screen = Screen.ENCHANTMENT;
		setInventory();
	}

	public void setList() {
		screen = Screen.LIST;
		enchant = null;
		enchPage = 1;
		setInventory();
	}

	public boolean isEnchantment(int slot) {
		return locations.contains(slot);
	}

	public void buyEnchantmentLevel(int slot) {
		List<Integer> levels = new ArrayList<Integer>();
		for(int i = 1; i <= enchant.getMaxLevel(); i++)
			levels.add(i);
		int loc = locations.indexOf(slot);
		int num = PAGING * (enchPage - 1) + loc;
		if (levels.size() >= num) {
			int level = levels.get(num);
			if (rpg.canBuy(enchant.getRelativeEnchantment(), level)) {
				screen = Screen.CONFIRM;
				buying = new EnchantmentLevel(enchant, level);
			}
		}
	}

	public void buy() {
		rpg.giveEnchantment(buying);
		screen = Screen.ENCHANTMENT;
		HashMap<String, Object> codes = getCodes();
		codes.put("%enchantment%", buying.getEnchant().getDisplayName());
		codes.put("%level%", buying.getLevel());
		Chatable.get().sendMessage(player, Chatable.get().getMessage(codes, "rpg.unlock"));
		buying = null;
		setInventory();
	}

	public void cancel() {
		screen = Screen.ENCHANTMENT;
		buying = null;
		setInventory();
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

	@Override
	public List<ItemStack> getItems() {
		return null;
	}

	public enum Screen {
		LIST(), ENCHANTMENT(), CONFIRM();
	}

	public HashMap<String, Object> getCodes() {
		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%player%", player.getName());
		return codes;
	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
	public void setPage(int page) {
		this.page = page;
	}

	public Screen getScreen() {
		return screen;
	}

	@Override
	public ChatUtils getChat() {
		return Chatable.get();
	}

}
