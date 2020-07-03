package org.ctp.enchantmentsolution.utils.compatibility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.nms.PersistenceNMS;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

import com.spawnchunk.auctionhouse.AuctionHouse;
import com.spawnchunk.auctionhouse.modules.Listing;

public class AuctionHouseUtils {

	public static void resetAuctionHouse() {
		Iterator<Entry<Long, Listing>> iterator = AuctionHouse.listings.getListings().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, Listing> entry = iterator.next();
			Listing l = entry.getValue();
			ItemStack item = l.getItem();
			if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
				List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
				for(String lore: item.getItemMeta().getLore())
					if (lore != null && PersistenceNMS.isEnchantment(lore)) {
						lore = ChatColor.stripColor(lore);
						EnchantmentLevel level = PersistenceNMS.returnEnchantmentLevel(lore, item.getItemMeta());
						if (level != null) levels.add(level);
					}
				for(EnchantmentLevel level: levels) {
					item = ItemUtils.removeEnchantmentFromItem(item, level.getEnchant());
					item = ItemUtils.addEnchantmentToItem(item, level.getEnchant(), level.getLevel());
				}
			}
			l.setItem(item);
		}
	}
}
