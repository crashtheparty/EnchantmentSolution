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
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.nms.PersistenceNMS;
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
				if (enchant instanceof CustomEnchantmentWrapper) newLevels.add(new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchant), level));
				meta.removeEnchant(enchant);
			}
			meta = enchantmentStorage;
			newItem.setItemMeta(meta);
			for(EnchantmentLevel level: newLevels)
				PersistenceNMS.addEnchantment(newItem, new EnchantmentLevel(level.getEnchant(), level.getLevel()));
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
				if (enchant instanceof CustomEnchantmentWrapper) newLevels.add(new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchant), level));
				enchantmentStorage.removeStoredEnchant(enchant);
			}
			newItem.setItemMeta(meta);
			for(EnchantmentLevel level: newLevels)
				PersistenceNMS.addEnchantment(newItem, new EnchantmentLevel(level.getEnchant(), level.getLevel()));
		}
		return newItem;
	}

	public static List<EnchantmentLevel> getEnchantmentLevels(ItemStack item) {
		List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
		if (item != null && item.getItemMeta() != null) {
			ItemMeta meta = item.getItemMeta();
			Map<Enchantment, Integer> enchantments = meta.getEnchants();
			if (item.getType() == Material.ENCHANTED_BOOK) enchantments = ((EnchantmentStorageMeta) meta).getStoredEnchants();
			for(Iterator<Entry<Enchantment, Integer>> it = enchantments.entrySet().iterator(); it.hasNext();) {
				Entry<Enchantment, Integer> e = it.next();
				levels.add(new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(e.getKey()), e.getValue()));
			}
		}
		return levels;
	}

	public static boolean isEnchantable(ItemStack item) {
		if (item == null) return false;
		ItemMeta meta = item.getItemMeta();
		if (meta.hasEnchants() || item.getType() == Material.ENCHANTED_BOOK && ((EnchantmentStorageMeta) meta).hasStoredEnchants()) return false;
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
			if (item.getType() == Material.ENCHANTED_BOOK) ((EnchantmentStorageMeta) meta).addStoredEnchant(level.getEnchant().getRelativeEnchantment(), level.getLevel(), true);
			else
				meta.addEnchant(level.getEnchant().getRelativeEnchantment(), level.getLevel(), true);
			item.setItemMeta(meta);
			if (!item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && level.getEnchant().getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				PersistenceNMS.removeEnchantment(item, level.getEnchant());
				PersistenceNMS.addEnchantment(item, level);
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
		if (enchantment instanceof CustomEnchantment) PersistenceNMS.removeEnchantment(item, enchantment);
		ItemMeta meta = item.getItemMeta();
		if (hasEnchantment(item, enchantment.getRelativeEnchantment()) && meta instanceof EnchantmentStorageMeta) ((EnchantmentStorageMeta) meta).removeStoredEnchant(enchantment.getRelativeEnchantment());
		else if (hasEnchantment(item, enchantment.getRelativeEnchantment())) meta.removeEnchant(enchantment.getRelativeEnchantment());
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack removeAllEnchantments(ItemStack item, boolean removeCurses) {
		for(CustomEnchantment enchantment: RegisterEnchantments.getEnchantments())
			if (!removeCurses || !enchantment.isCurse()) item = removeEnchantmentFromItem(item, enchantment);
		return item;
	}

	public static boolean hasEnchantment(ItemStack item, Enchantment enchant) {
		if (item != null && item.getItemMeta() != null) {
			Map<Enchantment, Integer> enchantments = item.getItemMeta().getEnchants();
			if (item.getType() == Material.ENCHANTED_BOOK) enchantments = ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants();
			for(Iterator<Entry<Enchantment, Integer>> it = enchantments.entrySet().iterator(); it.hasNext();) {
				Entry<Enchantment, Integer> e = it.next();
				if (e.getKey().equals(enchant)) return true;
			}
		}
		return false;
	}

	public static boolean hasOneEnchantment(ItemStack item, Enchantment... enchantments) {
		for(Enchantment e: enchantments)
			if (hasEnchantment(item, e)) return true;
		return false;
	}

	public static int getTotalEnchantments(ItemStack item) {
		if (item.getItemMeta() != null) {
			ItemMeta meta = item.getItemMeta();
			Map<Enchantment, Integer> enchantments = meta.getEnchants();
			if (item.getType() == Material.ENCHANTED_BOOK) enchantments = ((EnchantmentStorageMeta) meta).getStoredEnchants();
			if (enchantments == null) return 0;
			return enchantments.size();
		}
		return 0;
	}

	public static int getLevel(ItemStack item, Enchantment enchant) {
		if (item.getItemMeta() != null) {
			ItemMeta meta = item.getItemMeta();
			Map<Enchantment, Integer> enchantments = meta.getEnchants();
			if (item.getType() == Material.ENCHANTED_BOOK) enchantments = ((EnchantmentStorageMeta) meta).getStoredEnchants();
			for(Iterator<Entry<Enchantment, Integer>> it = enchantments.entrySet().iterator(); it.hasNext();) {
				Entry<Enchantment, Integer> e = it.next();
				if (e.getKey().equals(enchant)) return e.getValue();
			}
		}
		return 0;
	}

	public static boolean canAddEnchantment(CustomEnchantment customEnchant, ItemStack item) {
		ItemMeta meta = item.clone().getItemMeta();
		Map<Enchantment, Integer> enchants = meta.getEnchants();
		if (item.getType().equals(Material.ENCHANTED_BOOK)) enchants = ((EnchantmentStorageMeta) meta).getStoredEnchants();
		else if (!customEnchant.canAnvilItem(new ItemData(item))) return false;
		for(Iterator<Entry<Enchantment, Integer>> it = enchants.entrySet().iterator(); it.hasNext();) {
			Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			for(CustomEnchantment custom: RegisterEnchantments.getRegisteredEnchantments())
				if (custom.getRelativeEnchantment().equals(enchant)) if (CustomEnchantment.conflictsWith(customEnchant, custom) && !customEnchant.equals(custom)) return false;
		}
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
