package org.ctp.enchantmentsolution.utils.items;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.crashapi.compatibility.MMOUtils;
import org.ctp.crashapi.item.*;
import org.ctp.enchantmentsolution.enchantments.*;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.persistence.PersistenceUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class EnchantmentUtils {

	public static ItemStack convertToEnchantedBook(ItemStack item) {
		ItemStack newItem = new ItemStack(Material.ENCHANTED_BOOK, item.getAmount());
		EnchantmentStorageMeta enchantmentStorage = (EnchantmentStorageMeta) newItem.getItemMeta();

		ItemMeta meta = item.getItemMeta();
		List<EnchantmentLevel> newLevels = new ArrayList<EnchantmentLevel>();
		if (meta != null && meta.getEnchants().size() > 0) {
			for(Iterator<Entry<Enchantment, Integer>> it = meta.getEnchants().entrySet().iterator(); it.hasNext();) {
				Entry<Enchantment, Integer> e = it.next();
				Enchantment enchant = e.getKey();
				int level = e.getValue();
				enchantmentStorage.addStoredEnchant(enchant, level, true);
				newLevels.add(new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchant), level));
				meta.removeEnchant(enchant);
			}
			meta = enchantmentStorage;
			newItem.setItemMeta(meta);
			for(EnchantmentLevel level: newLevels)
				PersistenceUtils.addEnchantments(newItem, Arrays.asList(new EnchantmentLevel(level.getEnchant(), level.getLevel())));
		}
		return newItem;
	}

	public static ItemStack convertToRegularBook(ItemStack item) {
		ItemStack newItem = new ItemStack(Material.BOOK, item.getAmount());
		EnchantmentStorageMeta enchantmentStorage = (EnchantmentStorageMeta) item.getItemMeta();

		ItemMeta meta = newItem.getItemMeta();
		List<EnchantmentLevel> newLevels = new ArrayList<EnchantmentLevel>();

		if (enchantmentStorage != null && enchantmentStorage.getStoredEnchants().size() > 0) {
			for(Iterator<Entry<Enchantment, Integer>> it = enchantmentStorage.getStoredEnchants().entrySet().iterator(); it.hasNext();) {
				Entry<Enchantment, Integer> e = it.next();
				Enchantment enchant = e.getKey();
				int level = e.getValue();
				meta.addEnchant(enchant, level, true);
				newLevels.add(new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchant), level));
				enchantmentStorage.removeStoredEnchant(enchant);
			}
			newItem.setItemMeta(meta);
			for(EnchantmentLevel level: newLevels)
				PersistenceUtils.addEnchantments(newItem, Arrays.asList(new EnchantmentLevel(level.getEnchant(), level.getLevel())));
		}
		return newItem;
	}

	public static List<EnchantmentLevel> getEnchantmentLevels(ItemStack item) {
		return PersistenceUtils.getEnchantments(item);
	}

	public static boolean isEnchantable(ItemStack item) {
		if (item == null) return false;
		if (PersistenceUtils.getEnchantments(item).size() > 0) return false;
		List<ItemData> all = new ArrayList<ItemData>();
		all.addAll(ItemType.ALL.getEnchantMaterials());
		for(String s: ConfigString.EXTRA_ENCHANTING_MATERIALS.getStringList()) {
			MatData data = new MatData(s);
			if (data.getMaterial() != null) all.add(new ItemData(data.getMaterial(), null, null));
		}
		if (ItemData.contains(all, item.getType())) return true;
		if (item.getType().equals(Material.BOOK)) return true;
		return false;
	}

	public static ItemStack addEnchantmentsToItem(ItemStack item, List<EnchantmentLevel> levels) {
		if (levels == null) return item;
		ItemMeta meta = item.getItemMeta();
		if (meta == null) return item;
		List<EnchantmentLevel> remove = new ArrayList<EnchantmentLevel>();
		for(EnchantmentLevel level: levels) {
			if (level == null || level.getEnchant() == null) continue;
			if (level.getLevel() < 1) {
				remove.add(level);
				continue;
			}
			Enchantment en = level.getEnchant().getRelativeEnchantment().getRelativeEnchantment();
			if (en != null && item.getType() == Material.ENCHANTED_BOOK) ((EnchantmentStorageMeta) meta).addStoredEnchant(en, level.getLevel(), true);
			else if (en != null) meta.addEnchant(en, level.getLevel(), true);
			item.setItemMeta(meta);
			if (!item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && level.getEnchant().getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				PersistenceUtils.removeEnchantments(item, level.getEnchant());
				PersistenceUtils.addEnchantments(item, Arrays.asList(level));
			}
			meta = item.getItemMeta();
		}
		item.setItemMeta(meta);
		for(EnchantmentLevel level: remove)
			removeEnchantmentFromItem(item, level.getEnchant());
		return item;
	}

	public static ItemStack addEnchantmentToItem(ItemStack item, CustomEnchantment enchantment, int level) {
		return addEnchantmentsToItem(item, Arrays.asList(new EnchantmentLevel(enchantment, level)));
	}

	public static ItemStack removeEnchantmentFromItem(ItemStack item, CustomEnchantment enchantment) {
		if (enchantment == null) return item;
		if (enchantment instanceof CustomEnchantment) PersistenceUtils.removeEnchantments(item, enchantment);
		ItemMeta meta = item.getItemMeta();
		Enchantment en = enchantment.getRelativeEnchantment().getRelativeEnchantment();
		if (en != null && meta instanceof EnchantmentStorageMeta) ((EnchantmentStorageMeta) meta).removeStoredEnchant(en);
		else if (en != null) meta.removeEnchant(en);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack removeCursesFromItem(ItemStack item) {
		for(CustomEnchantment enchantment: RegisterEnchantments.getEnchantments())
			if (enchantment.isCurse()) item = removeEnchantmentFromItem(item, enchantment);
		return item;
	}

	public static ItemStack removeAllEnchantments(ItemStack item, boolean removeCurses) {
		for(CustomEnchantment enchantment: RegisterEnchantments.getEnchantments())
			if (!removeCurses || !enchantment.isCurse()) item = removeEnchantmentFromItem(item, enchantment);
		return item;
	}

	public static boolean hasEnchantment(ItemStack item, EnchantmentWrapper enchant) {
		return PersistenceUtils.hasEnchantment(item, enchant);
	}

	public static boolean hasEnchantment(ItemStack item, Enchantment enchantment) {
		EnchantmentWrapper wrapper = RegisterEnchantments.getByKey(enchantment.getKey());
		return hasEnchantment(item, wrapper);
	}

	public static boolean hasOneEnchantment(ItemStack item, EnchantmentWrapper... enchantments) {
		for(EnchantmentWrapper e: enchantments)
			if (hasEnchantment(item, e)) return true;
		return false;
	}

	public static int getTotalEnchantments(ItemStack item) {
		List<EnchantmentLevel> enchantments = PersistenceUtils.getEnchantments(item);
		return enchantments == null ? 0 : enchantments.size();
	}

	public static int getLevel(ItemStack item, EnchantmentWrapper enchant) {
		return PersistenceUtils.getLevel(item, enchant);
	}

	public static int getLevel(ItemStack item, Enchantment enchantment) {
		EnchantmentWrapper wrapper = RegisterEnchantments.getByKey(enchantment.getKey());
		return getLevel(item, wrapper);
	}

	public static EnchantmentLevel getEnchantmentLevel(ItemStack item, EnchantmentWrapper enchant) {
		return PersistenceUtils.getEnchantmentLevel(item, enchant);
	}

	public static boolean canAddEnchantment(CustomEnchantment customEnchant, ItemStack item) {
		List<EnchantmentLevel> levels = getEnchantmentLevels(item);
		if (!customEnchant.canAnvilItem(new ItemData(item))) return false;
		for(EnchantmentLevel level: levels)
			for(CustomEnchantment custom: RegisterEnchantments.getRegisteredEnchantments())
				if (custom.equals(level.getEnchant()) && CustomEnchantment.conflictsWith(customEnchant, custom) && !customEnchant.equals(custom)) return false;
		return true;
	}

	public static ItemStack getSoulboundShulkerBox(BlockState state, ItemStack eventItem) {
		BlockStateMeta im = (BlockStateMeta) eventItem.getItemMeta();
		Container container = (Container) state;
		im.setBlockState(container);
		if (state.getMetadata("soulbound").size() > 0) eventItem = addEnchantmentsToItem(eventItem, Arrays.asList(new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(RegisterEnchantments.SOULBOUND), 1)));
		return eventItem;
	}

	public static boolean checkItemType(ItemData item, CustomItemType type) {
		if (type.getVanilla() == VanillaItemType.VANILLA) {
			MatData data = new MatData(type.getType().split(":")[1]);
			return data.hasMaterial() && data.getMaterial() == item.getMaterial();
		}
		return MMOUtils.check(item, type);
	}

	public static int getBookshelves(Location loc) {
		int bookshelves = 0;
		for(int x = loc.getBlockX() - 2; x < loc.getBlockX() + 3; x++)
			for(int y = loc.getBlockY(); y < loc.getBlockY() + 2; y++)
				for(int z = loc.getBlockZ() - 2; z < loc.getBlockZ() + 3; z++)
					if (x == loc.getBlockX() - 2 || x == loc.getBlockX() + 2 || z == loc.getBlockZ() - 2 || z == loc.getBlockZ() + 2) {
						Location bookshelf = new Location(loc.getWorld(), x, y, z);
						if (bookshelf.getBlock().getType().equals(Material.BOOKSHELF)) bookshelves++;
					}
		if (ConfigString.LEVEL_FIFTY.getBoolean()) {
			if (bookshelves > 23) bookshelves = 23;
		} else if (bookshelves > 15) bookshelves = 15;
		return bookshelves;
	}
}
