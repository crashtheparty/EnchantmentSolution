package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class GungHoListener implements Listener{

	private static List<GungHoPlayer> PLAYERS = new ArrayList<GungHoPlayer>();
	
	@EventHandler
	public static void onPlayerLogin(PlayerLoginEvent event){
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.GUNG_HO)) return;
		for(GungHoPlayer player : PLAYERS) {
			if(player.getPlayer().getUniqueId().equals(event.getPlayer().getUniqueId())) {
				return;
			}
		}
		PLAYERS.add(new GungHoPlayer(event.getPlayer(), event.getPlayer().getInventory().getChestplate()));
	}
	
	@EventHandler
	public static void onPlayerQuit(PlayerQuitEvent event){
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.GUNG_HO)) return;
		GungHoPlayer remove = null;
		for(GungHoPlayer player : PLAYERS) {
			if(player.getPlayer().getUniqueId().equals(event.getPlayer().getUniqueId())) {
				remove = player;
				break;
			}
		}
		if(remove != null) {
			PLAYERS.remove(remove);
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.GUNG_HO)) return;
		Entity damager = event.getDamager();
		if(damager instanceof Projectile) {
			Projectile projectile = (Projectile) event.getDamager();
			if(projectile instanceof Snowball || projectile instanceof Egg) return;
			if(projectile.getShooter() instanceof Player) {
				Player player = (Player) projectile.getShooter();
				ItemStack item = player.getInventory().getChestplate();
				if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO)) {
					Entity damaged = event.getEntity();
					if(damaged instanceof LivingEntity) {
						event.setDamage(event.getDamage() * 3);
					}
				}
			}
		} else {
			if(damager instanceof Player) {
				Player player = (Player) damager;
				ItemStack item = player.getInventory().getChestplate();
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO)) {
					Entity damaged = event.getEntity();
					if(damaged instanceof LivingEntity) {
						event.setDamage(event.getDamage() * 3);
					}
				}
			}
		}
	}
	
	protected static class GungHoPlayer implements Runnable{
		
		private Player player;
		private ItemStack chestplate, previousChestplate;
		private int scheduler;
		
		public GungHoPlayer(Player player, ItemStack chestplate) {
			this.player = player;
			this.chestplate = chestplate;
			
			int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.PLUGIN, this, 20l, 20l);
			setScheduler(scheduler);
		}

		public Player getPlayer() {
			return player;
		}

		public ItemStack getChestplate() {
			return chestplate;
		}
		
		public void setChestplate(ItemStack chestplate) {
			this.previousChestplate = this.chestplate;
			if(chestplate == null && previousChestplate != null){
				doUnequip(previousChestplate);
			}else if(chestplate != null && previousChestplate == null){
				doEquip(chestplate);
			}else if(previousChestplate != null && chestplate != null){
				if (!chestplate.toString().equalsIgnoreCase(
						previousChestplate.toString())) {
					doUnequip(previousChestplate);
					doEquip(chestplate);
				}
			}
			this.chestplate = chestplate;
		}
		
		public void removeListener() {
			Bukkit.getScheduler().cancelTask(getScheduler());
			PLAYERS.remove(this);
		}

		private void doEquip(ItemStack item) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO)){
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
				a.setBaseValue(a.getDefaultValue() / 2);
			}
		}

		private void doUnequip(ItemStack item) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO)){
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
				a.setBaseValue(a.getDefaultValue());
				if(player.getHealth() > a.getBaseValue()) {
					player.setHealth(a.getBaseValue());
				}
			}
		}
		
		public void run() {
			ItemStack equips = player.getInventory().getChestplate();
			setChestplate(equips);
		}

		public int getScheduler() {
			return scheduler;
		}

		public void setScheduler(int scheduler) {
			this.scheduler = scheduler;
		}
	}
	
}