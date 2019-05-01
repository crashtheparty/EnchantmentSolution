package org.ctp.enchantmentsolution.listeners.abilities.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;

import com.gmail.nossr50.datatypes.skills.AbilityType;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityDeactivateEvent;
import com.gmail.nossr50.events.skills.repair.McMMOPlayerRepairCheckEvent;

public class McMMOClassicAbility implements Listener{

	private static List<Player> IGNORE_PLAYERS = new ArrayList<Player>();
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOAbilityActivated(McMMOPlayerAbilityActivateEvent event) {
		if(event.getAbility().equals(AbilityType.TREE_FELLER) && !IGNORE_PLAYERS.contains(event.getPlayer())) {
			IGNORE_PLAYERS.add(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOPlayerRepairCheck(McMMOPlayerRepairCheckEvent event) {
		ItemStack item = event.getRepairedObject();
		Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if(!event.isCancelled()) {
					ItemMeta meta = item.getItemMeta();
					if(meta != null) {
						List<String> lore = new ArrayList<String>();
						List<String> previousLore = meta.getLore();
						if(previousLore != null) {
							for(String l : previousLore) {
								if(!StringUtils.isEnchantment(l)) {
									lore.add(l);
								}
							}
						}
						Iterator<Entry<Enchantment, Integer>> enchantmentLevels = meta.getEnchants().entrySet().iterator();
						while(enchantmentLevels.hasNext()) {
							Entry<Enchantment, Integer> entry = enchantmentLevels.next();
							CustomEnchantment custom = DefaultEnchantments.getCustomEnchantment(entry.getKey());
							if(custom != null && entry.getKey() instanceof CustomEnchantmentWrapper) {
								lore.add(ChatUtils.hideText("solution") + "" + ChatColor.GRAY + custom.getName());
							}
						}
						meta.setLore(lore);
						item.setItemMeta(meta);
					}
				}
			}
			
		}, 1l);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOAbilityDeactivated(McMMOPlayerAbilityDeactivateEvent event) {
		if(event.getAbility().equals(AbilityType.TREE_FELLER)) {
			IGNORE_PLAYERS.remove(event.getPlayer());
		}
	}
	
	public static List<Player> getIgnored(){
		return IGNORE_PLAYERS;
	}
}
