package org.ctp.enchantmentsolution.mcmmo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.VersionUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

import com.gmail.nossr50.datatypes.skills.SuperAbilityType;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityDeactivateEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityEvent;
import com.gmail.nossr50.events.skills.repair.McMMOPlayerRepairCheckEvent;
import com.gmail.nossr50.events.skills.salvage.McMMOPlayerSalvageCheckEvent;

public class McMMOAbility implements Listener {
	private static List<Player> IGNORE_PLAYERS = new ArrayList<Player>();

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOAbilityActivated(McMMOPlayerAbilityActivateEvent event) {
		if (isTreeFeller(event) && !IGNORE_PLAYERS.contains(event.getPlayer())) IGNORE_PLAYERS.add(event.getPlayer());
	}

	private boolean isTreeFeller(McMMOPlayerAbilityEvent event) {
		if (VersionUtils.getMcMMOType().equals("Overhaul")) {
			if (event.getAbility().equals(SuperAbilityType.TREE_FELLER)) return true;
		} else {
			Class<?> clazz = null;
			Class<?> abilityClazz = null;
			try {
				clazz = Class.forName("com.gmail.nossr50.datatypes.skills.AbilityType");
				abilityClazz = Class.forName("com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityEvent");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (clazz != null && abilityClazz != null) for(Object obj: clazz.getEnumConstants()) {
				String name = obj.toString().toUpperCase(Locale.ROOT);
				if (name.equals("TREE_FELLER")) try {
					Method method = abilityClazz.getDeclaredMethod("getAbility");
					Object returnType = method.invoke(event);
					if (returnType.toString().toUpperCase(Locale.ROOT).equals(name)) return true;
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOPlayerRepairCheck(McMMOPlayerRepairCheckEvent event) {
		updateItem(event, event.getRepairedObject());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOPlayerSalvageCheck(McMMOPlayerSalvageCheckEvent event) {
		updateItem(event, event.getEnchantedBook());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOAbilityDeactivated(McMMOPlayerAbilityDeactivateEvent event) {
		if (isTreeFeller(event)) IGNORE_PLAYERS.remove(event.getPlayer());
	}

	private void updateItem(Cancellable event, ItemStack item) {
		if (item == null) return;
		List<EnchantmentLevel> previousLevels = EnchantmentUtils.getEnchantmentLevels(item);
		Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), (Runnable) () -> {
			if (event.isCancelled() || item == null) return;
			ItemMeta meta = item.getItemMeta();
			if (meta != null) {
				Iterator<Entry<Enchantment, Integer>> enchantmentLevels = meta.getEnchants().entrySet().iterator();
				if (item.getType() == Material.ENCHANTED_BOOK) enchantmentLevels = ((EnchantmentStorageMeta) meta).getStoredEnchants().entrySet().iterator();
				List<EnchantmentLevel> levels = new ArrayList<EnchantmentLevel>();
				while (enchantmentLevels.hasNext()) {
					Entry<Enchantment, Integer> entry = enchantmentLevels.next();
					levels.add(new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(entry.getKey()), entry.getValue()));
				}
				for(EnchantmentLevel l: previousLevels)
					EnchantmentUtils.removeEnchantmentFromItem(item, l.getEnchant());
				EnchantmentUtils.addEnchantmentsToItem(item, levels);
			}
		}, 1l);
	}

	public static List<Player> getIgnored() {
		return IGNORE_PLAYERS;
	}
}
