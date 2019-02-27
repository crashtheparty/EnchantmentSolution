package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class LifeListener implements Runnable, Listener{

	private Player player;
	private int scheduler;
	private static HashMap<String, ItemStack> equipment = new HashMap<String, ItemStack>();
	private static HashMap<String, LifeListener> schedulers = new HashMap<String, LifeListener>();
	
	public LifeListener(Player player) {
		this.player = player;
	}
	
	@EventHandler
	public static void onPlayerLogin(PlayerLoginEvent event){
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.LIFE)) return;
		LifeListener life = new LifeListener(event.getPlayer());
		int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.PLUGIN, life, 20l, 20l);
		life.setScheduler(scheduler);
		schedulers.put(event.getPlayer().getUniqueId().toString(), life);
	}
	
	@EventHandler
	public static void onPlayerQuit(PlayerQuitEvent event){
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.LIFE)) return;
		LifeListener life = schedulers.get(event.getPlayer().getUniqueId().toString());
		if(life != null){
			Bukkit.getScheduler().cancelTask(life.getScheduler());
			schedulers.put(event.getPlayer().getUniqueId().toString(), null);
		}
	}
	
	public void run() {
		ItemStack equips = player.getInventory().getChestplate();
		ItemStack previous = equipment.get(player.getName());
		try {
			if(equips == null && previous != null){
				doUnequip(previous);
			}else if(equips != null && previous == null){
				doEquip(equips);
			}else if(previous != null && equips != null){
				if (!equips.toString().equalsIgnoreCase(
						previous.toString())) {
					doUnequip(previous);
					doEquip(equips);
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}

		equipment.put(player.getName(), equips);
	}

	private void doEquip(ItemStack item) {
		if(!Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO) && Enchantments.hasEnchantment(item, DefaultEnchantments.LIFE)){
			int level = Enchantments.getLevel(item, DefaultEnchantments.LIFE);
			AttributeInstance a = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			a.setBaseValue(a.getDefaultValue() + 4 * level);
		}
	}

	private void doUnequip(ItemStack item) {
		if(!Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO) && Enchantments.hasEnchantment(item, DefaultEnchantments.LIFE)){
			AttributeInstance a = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			a.setBaseValue(a.getDefaultValue());
			if(player.getHealth() > a.getBaseValue()) {
				player.setHealth(a.getBaseValue());
			}
		}
	}



	public int getScheduler() {
		return scheduler;
	}



	public void setScheduler(int scheduler) {
		this.scheduler = scheduler;
	}
	
}