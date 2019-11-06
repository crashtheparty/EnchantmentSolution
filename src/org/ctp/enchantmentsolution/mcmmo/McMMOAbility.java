package org.ctp.enchantmentsolution.mcmmo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.VersionUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

import com.gmail.nossr50.datatypes.skills.SuperAbilityType;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityDeactivateEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityEvent;
import com.gmail.nossr50.events.skills.repair.McMMOPlayerRepairCheckEvent;

public class McMMOAbility implements Listener {
	private static List<Player> IGNORE_PLAYERS = new ArrayList<Player>();

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOAbilityActivated(McMMOPlayerAbilityActivateEvent event) {
		if (isTreeFeller(event) && !IGNORE_PLAYERS.contains(event.getPlayer())) {
			IGNORE_PLAYERS.add(event.getPlayer());
		}
	}

	private boolean isTreeFeller(McMMOPlayerAbilityEvent event) {
		if (VersionUtils.getMcMMOType().equals("Overhaul")) {
			if (event.getAbility().equals(SuperAbilityType.TREE_FELLER)) {
				return true;
			}
		} else {
			Class<?> clazz = null;
			Class<?> abilityClazz = null;
			try {
				clazz = Class.forName("com.gmail.nossr50.datatypes.skills.AbilityType");
				abilityClazz = Class.forName("com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityEvent");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (clazz != null && abilityClazz != null) {
				for(Object obj: clazz.getEnumConstants()) {
					String name = obj.toString().toUpperCase();
					if (name.equals("TREE_FELLER")) {
						try {
							Method method = abilityClazz.getDeclaredMethod("getAbility");
							Object returnType = method.invoke(event);
							if (returnType.toString().toUpperCase().equals(name)) {
								return true;
							}
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException
								| IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return false;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOPlayerRepairCheck(McMMOPlayerRepairCheckEvent event) {
		ItemStack item = event.getRepairedObject();
		Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (!event.isCancelled()) {
					ItemMeta meta = item.getItemMeta();
					if (meta != null) {
						List<String> lore = new ArrayList<String>();
						List<String> previousLore = meta.getLore();
						if (previousLore != null) {
							for(String l: previousLore) {
								if (!StringUtils.isEnchantment(l)) {
									lore.add(l);
								}
							}
						}
						Iterator<Entry<Enchantment, Integer>> enchantmentLevels = meta.getEnchants().entrySet()
								.iterator();
						while (enchantmentLevels.hasNext()) {
							Entry<Enchantment, Integer> entry = enchantmentLevels.next();
							CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(entry.getKey());
							if (custom != null && entry.getKey() instanceof CustomEnchantmentWrapper) {
								lore.add(ChatUtils.hideText("solution") + "" + ChatColor.GRAY + custom.getName());
							}
						}
						meta = ItemUtils.setLore(meta, lore);
						item.setItemMeta(meta);
					}
				}
			}

		}, 1l);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOAbilityDeactivated(McMMOPlayerAbilityDeactivateEvent event) {
		if (isTreeFeller(event)) {
			IGNORE_PLAYERS.remove(event.getPlayer());
		}
	}

	public static List<Player> getIgnored() {
		return IGNORE_PLAYERS;
	}
}
