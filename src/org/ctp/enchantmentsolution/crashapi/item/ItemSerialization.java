package org.ctp.enchantmentsolution.crashapi.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.ctp.enchantmentsolution.crashapi.CrashAPIPlugin;
import org.ctp.enchantmentsolution.crashapi.utils.ChatUtils;

public class ItemSerialization {

	private static List<ItemSerialization> serial = new ArrayList<ItemSerialization>();
	private final CrashAPIPlugin plugin;
	
	public static ItemSerialization getItemSerial(CrashAPIPlugin plugin) {
		for (ItemSerialization s : serial)
			if (s.getPlugin().getName().equals(plugin.getName())) return s;
		ItemSerialization s = new ItemSerialization(plugin);
		serial.add(s);
		return s;
	}
	
	private ItemSerialization(CrashAPIPlugin plugin) {
		this.plugin = plugin;
	}
	
	public CrashAPIPlugin getPlugin() {
		return plugin;
	}
	
	public String itemToString(ItemStack item) {
		String itemString = "";
		if (item.getType() != null) itemString = itemString + "name@" + item.getType();
		itemString = itemString + " amount@" + item.getAmount();
		if (MatData.isAir(item.getType())) return itemString;
		if (item.getItemMeta().getDisplayName() != null && !item.getItemMeta().getDisplayName().equals("")) itemString = itemString + " item_name@" + item.getItemMeta().getDisplayName().replace(" ", "_").replace("§", "&");
		if (item.getItemMeta() instanceof Damageable) {
			Damageable damage = (Damageable) item.getItemMeta();
			if (damage.hasDamage()) itemString = itemString + " damage@" + damage.getDamage();
		}
		Map<Enchantment, Integer> isEnch = item.getEnchantments();
		if (isEnch.size() > 0) for(Map.Entry<Enchantment, Integer> ench: isEnch.entrySet())
			itemString = itemString + " enchant@" + ench.getKey().getKey().getNamespace() + "+" + ench.getKey().getKey().getKey() + "@" + ench.getValue();

		List<String> isLore = item.getItemMeta().getLore();
		if (item.getItemMeta().getLore() != null && isLore.size() != 0) for(String lore: isLore)
			itemString = itemString + " lore@" + lore.replace(" ", "_").replace("§", "&");

		if (item.getType().equals(Material.PLAYER_HEAD)) itemString = itemString + " owner@" + ((SkullMeta) item.getItemMeta()).getOwningPlayer();

		return itemString;
	}

	@SuppressWarnings("deprecation")
	public ItemStack stringToItem(String itemString) {
		ItemStack is = null;
		Boolean createdItemStack = Boolean.valueOf(false);

		String[] serializedItem = itemString.split(" ");
		for(String itemInfo: serializedItem) {
			String[] itemAttribute = itemInfo.split("@");
			if (itemAttribute[0].equals("name")) {
				is = new ItemStack(Material.getMaterial(itemAttribute[1]));
				createdItemStack = Boolean.valueOf(true);
			} else if (itemAttribute[0].equals("damage") && createdItemStack.booleanValue()) {
				ItemMeta im = is.getItemMeta();
				if (im instanceof Damageable) ((Damageable) im).setDamage(Integer.valueOf(itemAttribute[1]).intValue());
				is.setItemMeta(im);
			} else if (itemAttribute[0].equals("amount") && createdItemStack.booleanValue()) is.setAmount(Integer.valueOf(itemAttribute[1]).intValue());
			else if (itemAttribute[0].equals("item_name") && createdItemStack.booleanValue()) {
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemAttribute[1].replace("_", " ")));
				is.setItemMeta(im);
			} else if (itemAttribute[0].equals("enchant") && createdItemStack.booleanValue()) {
				NamespacedKey key = null;
				String[] enchString = itemAttribute[1].split("\\+");
				if (enchString[0].equalsIgnoreCase("minecraft")) key = NamespacedKey.minecraft(enchString[1]);
				else
					key = new NamespacedKey(Bukkit.getPluginManager().getPlugin(enchString[0]), enchString[1]);
				if (key == null) ChatUtils.getUtils(plugin).sendToConsole(Level.WARNING, "Key is null.");

				if (Enchantment.getByKey(key) != null) is.addUnsafeEnchantment(Enchantment.getByKey(key), Integer.valueOf(itemAttribute[2]).intValue());
				else {
					ChatUtils.getUtils(plugin).sendWarning("Wrong enchantment name: " + itemAttribute[1]);
					ChatUtils.getUtils(plugin).sendWarning("Please fix the name in config!");
				}
			} else if (itemAttribute[0].equals("lore") && createdItemStack.booleanValue()) {
				ItemMeta im = is.getItemMeta();
				List<String> il = new ArrayList<String>();

				if (is.getItemMeta().getLore() != null) for(String lore: is.getItemMeta().getLore())
					if (lore != null) il.add(ChatColor.translateAlternateColorCodes('&', lore.replace("_", " ")));
				il.add(ChatColor.translateAlternateColorCodes('&', itemAttribute[1].replace("_", " ")));
				im.setLore(il);
				is.setItemMeta(im);
			} else if (itemAttribute[0].equals("owner") && createdItemStack.booleanValue()) {
				SkullMeta im = (SkullMeta) is.getItemMeta();
				im.setOwner(itemAttribute[1]);
				is.setItemMeta(im);
			}
		}
		return is;
	}

	public String itemToData(ItemStack item) {
		String metadata = itemToString(item);
		String[] serializedItem = metadata.split(" ");
		for(String itemInfo: serializedItem) {
			String[] itemAttribute = itemInfo.split("@");
			if (itemAttribute[0].equals("name") || itemAttribute[0].equals("amount")) {
				metadata = metadata.replace(itemInfo + " ", "");
				metadata = metadata.replace(itemInfo, "");
			}
		}

		return metadata.trim();
	}

	public ItemStack dataToItem(Material material, int amount, String metadata) {
		return stringToItem("name@" + material + " amount@" + amount + " " + metadata);
	}
}