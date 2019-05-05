package org.ctp.enchantmentsolution.listeners.legacy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;

public class UpdateEnchantments implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		updateEnchantments(player.getInventory());
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			updateEnchantments(player.getInventory());
		}
	}
	
	public void updateEnchantments(PlayerInventory inv) {
		if(EnchantmentSolution.getPlugin().getConfigFiles().updateLegacyEnchantments()) {
			for(int i = 0; i < 36; i++) {
				ItemStack item = inv.getItem(i);
				if(item != null) {
					if(item.getItemMeta() != null) {
						ItemMeta meta = item.getItemMeta();
						List<String> lore = meta.getLore();
						if(lore != null) {
							List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
							for(String s : lore) {
								String enchantment = ChatColor.stripColor(s);
								EnchantmentLevel level = StringUtils.returnEnchantmentLevel(enchantment, meta);
								if(level != null) {
									String enchName = ChatUtils.hideText("legacy") + "" + ChatColor.GRAY + 
											StringUtils.returnEnchantmentName(level.getEnchant(), level.getLevel());
									if(s.equals(enchName)) {
										levels.add(level);
									}
								}
							}
							if(levels.size() > 0) {
								meta.setLore(null);
								item.setItemMeta(meta);
								Enchantments.addEnchantmentsToItem(item, levels);
							}
						}
					}
				}
			}
		}
	}
}
