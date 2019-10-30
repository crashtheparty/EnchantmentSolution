package org.ctp.enchantmentsolution.utils.compatibility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

import com.spawnchunk.auctionhouse.AuctionHouse;
import com.spawnchunk.auctionhouse.modules.Listing;

public class AuctionHouseUtils {

	public static void resetAuctionHouse() {
		Iterator<Entry<Long, Listing>> iterator = AuctionHouse.listings.getListings().entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Long, Listing> entry = iterator.next();
			Listing l = entry.getValue();
			ItemStack item = l.getItemStack();
			if(item.hasItemMeta()) {
				List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
				for(String lore : item.getItemMeta().getLore()) {
					if(lore != null && StringUtils.isEnchantment(lore)) {
						lore = ChatColor.stripColor(lore);
						EnchantmentLevel level = StringUtils.returnEnchantmentLevel(lore, item.getItemMeta());
						if(level != null) {
							levels.add(level);
						}
					}
				}
				for(EnchantmentLevel level : levels) {
					item = ItemUtils.removeEnchantmentFromItem(item, level.getEnchant());
					item = ItemUtils.addEnchantmentToItem(item, level.getEnchant(), level.getLevel());
				}
			}
			l.setItemStack(item);
		}
	}
}
