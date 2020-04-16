package org.ctp.enchantmentsolution.utils.items;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.*;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.compatibility.MMOUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class ItemUtils {

	public static void giveItemsToPlayer(Player player, Collection<ItemStack> drops, Location fallback,
	boolean statistic) {
		for(ItemStack drop: drops)
			giveItemToPlayer(player, drop, fallback, statistic);
	}

	public static void giveItemToPlayer(Player player, ItemStack item, Location fallback, boolean statistic) {
		HashMap<Integer, ItemStack> leftOver = new HashMap<Integer, ItemStack>();
		int amount = item.getAmount();
		leftOver.putAll(player.getInventory().addItem(item));
		Location fallbackClone = fallback.clone();
		boolean dropNaturally = ConfigString.DROP_ITEMS_NATURALLY.getBoolean();
		if (!leftOver.isEmpty()) for(Iterator<Entry<Integer, ItemStack>> it = leftOver.entrySet().iterator(); it.hasNext();) {
			Entry<Integer, ItemStack> e = it.next();
			amount -= e.getValue().getAmount();
			if (!dropNaturally) {
				Item droppedItem = fallbackClone.getWorld().dropItem(fallbackClone, e.getValue());
				droppedItem.setVelocity(new Vector(0, 0, 0));
				droppedItem.teleport(fallbackClone);
			} else
				fallbackClone.getWorld().dropItemNaturally(fallbackClone, item);
		}
		if (amount > 0 && statistic) player.incrementStatistic(Statistic.PICKUP, item.getType(), amount);
	}

	public static void dropItems(Collection<ItemStack> drops, Location loc) {
		for(ItemStack drop: drops)
			dropItem(drop, loc);
	}

	public static void dropItem(ItemStack item, Location loc) {
		Location location = loc.clone();
		if (!ConfigString.DROP_ITEMS_NATURALLY.getBoolean()) {
			Item droppedItem = location.getWorld().dropItem(location, item);
			droppedItem.setVelocity(new Vector(0, 0, 0));
			droppedItem.teleport(location);
		} else
			location.getWorld().dropItemNaturally(location, item);
	}

	public static ItemStack convertToEnchantedBook(ItemStack item) {
		ItemStack newItem = new ItemStack(Material.ENCHANTED_BOOK, item.getAmount());
		EnchantmentStorageMeta enchantmentStorage = (EnchantmentStorageMeta) newItem.getItemMeta();

		ItemMeta meta = item.getItemMeta();

		if (meta != null && meta.getEnchants().size() > 0) {
			List<String> lore = new ArrayList<String>();
			for(Iterator<Entry<Enchantment, Integer>> it = meta.getEnchants().entrySet().iterator(); it.hasNext();) {
				Entry<Enchantment, Integer> e = it.next();
				Enchantment enchant = e.getKey();
				int level = e.getValue();
				enchantmentStorage.addStoredEnchant(enchant, level, true);
				if (enchant instanceof CustomEnchantmentWrapper) lore.add(StringUtils.getEnchantmentString(new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchant), level)));
				meta.removeEnchant(enchant);
			}
			meta = enchantmentStorage;
			meta = setLore(meta, lore);
			newItem.setItemMeta(meta);
		}
		return newItem;
	}

	public static ItemStack convertToRegularBook(ItemStack item) {
		ItemStack newItem = new ItemStack(Material.BOOK, item.getAmount());
		EnchantmentStorageMeta enchantmentStorage = (EnchantmentStorageMeta) item.getItemMeta();

		ItemMeta meta = newItem.getItemMeta();

		if (enchantmentStorage != null && enchantmentStorage.getStoredEnchants().size() > 0) {
			List<String> lore = new ArrayList<String>();
			for(Iterator<Entry<Enchantment, Integer>> it = enchantmentStorage.getStoredEnchants().entrySet().iterator(); it.hasNext();) {
				Entry<Enchantment, Integer> e = it.next();
				Enchantment enchant = e.getKey();
				int level = e.getValue();
				meta.addEnchant(enchant, level, true);
				if (enchant instanceof CustomEnchantmentWrapper) lore.add(StringUtils.getEnchantmentString(new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchant), level)));
				enchantmentStorage.removeStoredEnchant(enchant);
			}
			meta = setLore(meta, lore);
			newItem.setItemMeta(meta);
		}
		return newItem;
	}

	public static ItemMeta setLore(ItemMeta meta, List<String> lore) {
		if (meta == null) return null;
		if (lore == null) {
			meta.setLore(new ArrayList<String>());
			return meta;
		}
		if (ConfigString.LORE_ON_TOP.getBoolean()) {
			List<String> enchantmentsFirst = new ArrayList<String>();
			for(String l: lore)
				if (StringUtils.isEnchantment(l)) enchantmentsFirst.add(l);
			for(String l: lore)
				if (!StringUtils.isEnchantment(l)) enchantmentsFirst.add(l);
			meta.setLore(enchantmentsFirst);
		}
		return meta;
	}

	public static List<EnchantmentLevel> getEnchantmentLevels(ItemStack item) {
		List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
		if (item.getItemMeta() != null) {
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
		if (ItemData.contains(ItemType.ALL.getEnchantMaterials(), item.getType())) return true;
		if (item.getType().equals(Material.BOOK)) return true;
		return false;
	}

	public static ItemStack addEnchantmentsToItem(ItemStack item, List<EnchantmentLevel> levels) {
		if (levels == null) return item;
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		List<String> previousLore = null;
		if (meta == null) return item;
		else
			previousLore = meta.getLore();
		for(EnchantmentLevel level: levels) {
			if (level == null || level.getEnchant() == null) continue;
			if (item.getType() == Material.ENCHANTED_BOOK) ((EnchantmentStorageMeta) meta).addStoredEnchant(level.getEnchant().getRelativeEnchantment(), level.getLevel(), true);
			else
				meta.addEnchant(level.getEnchant().getRelativeEnchantment(), level.getLevel(), true);
			if (!item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && level.getEnchant().getRelativeEnchantment() instanceof CustomEnchantmentWrapper) {
				previousLore = StringUtils.removeEnchantment(level.getEnchant(), previousLore);
				lore.add(StringUtils.getEnchantmentString(level));
			}
		}
		if (previousLore != null) for(String l: previousLore)
			lore.add(l);
		meta = ItemUtils.setLore(meta, lore);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack addEnchantmentToItem(ItemStack item, CustomEnchantment enchantment, int level) {
		return addEnchantmentsToItem(item, Arrays.asList(new EnchantmentLevel(enchantment, level)));
	}

	public static ItemStack removeEnchantmentFromItem(ItemStack item, CustomEnchantment enchantment) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) lore = new ArrayList<String>();
		if (enchantment == null) return item;
		if (enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper) lore = StringUtils.removeEnchantment(enchantment, meta.getEnchantLevel(enchantment.getRelativeEnchantment()), lore);
		if (hasEnchantment(item, enchantment.getRelativeEnchantment()) && meta instanceof EnchantmentStorageMeta) ((EnchantmentStorageMeta) meta).removeStoredEnchant(enchantment.getRelativeEnchantment());
		else if (hasEnchantment(item, enchantment.getRelativeEnchantment())) meta.removeEnchant(enchantment.getRelativeEnchantment());
		meta = ItemUtils.setLore(meta, lore);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack removeAllEnchantments(ItemStack item, boolean removeCurses) {
		for(CustomEnchantment enchantment: RegisterEnchantments.getEnchantments())
			if (!removeCurses || !enchantment.isCurse()) item = removeEnchantmentFromItem(item, enchantment);
		return item;
	}

	public static boolean hasEnchantment(ItemStack item, Enchantment enchant) {
		if (item.getItemMeta() != null) {
			Map<Enchantment, Integer> enchantments = item.getItemMeta().getEnchants();
			if (item.getType() == Material.ENCHANTED_BOOK) enchantments = ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants();
			for(Iterator<Entry<Enchantment, Integer>> it = enchantments.entrySet().iterator(); it.hasNext();) {
				Entry<Enchantment, Integer> e = it.next();
				if (e.getKey().equals(enchant)) return true;
			}
		}
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
		if (customEnchant.getDisabledItems().contains(item.getType())) return false;
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

	public static Collection<ItemStack> getSoulboundShulkerBox(Player player, Block block,
	Collection<ItemStack> drops) {
		Iterator<ItemStack> i = drops.iterator();
		Collection<ItemStack> items = new ArrayList<ItemStack>();
		while (i.hasNext()) {
			ItemStack drop = i.next();
			if (ESArrays.getShulkerBoxes().contains(drop.getType())) {
				BlockStateMeta im = (BlockStateMeta) drop.getItemMeta();
				Container container = (Container) block.getState();
				im.setBlockState(container);
				if (block.getMetadata("shulker_name") != null) for(MetadataValue value: block.getMetadata("shulker_name"))
					im.setDisplayName(value.asString());
				drop.setItemMeta(im);
				if (block.getMetadata("soulbound").size() > 0) drop = ItemUtils.addEnchantmentsToItem(drop, Arrays.asList(new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(RegisterEnchantments.SOULBOUND), 1)));
				items.add(drop);
			}
		}
		return items;
	}

	public static boolean checkItemType(ItemData item, ItemType itemType, CustomItemType type) {
		if (type == CustomItemType.VANILLA) return new MatData(itemType.getCustomString().split(":")[1]).hasMaterial();
		return MMOUtils.check(item, itemType, type);
	}
}
