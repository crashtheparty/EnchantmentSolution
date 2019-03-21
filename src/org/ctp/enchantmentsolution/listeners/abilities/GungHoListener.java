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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class GungHoListener extends EnchantmentListener implements Runnable{

	private static List<GungHoPlayer> PLAYERS = new ArrayList<GungHoPlayer>();
	
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.GUNG_HO)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!contains(player)) {
				try {
					ItemStack chestplate = player.getInventory().getChestplate();
					GungHoPlayer gungHoPlayer = new GungHoPlayer(player, chestplate);
					PLAYERS.add(gungHoPlayer);
				} catch(Exception ex) { }
			}
		}
		for(int i = PLAYERS.size() - 1; i >= 0; i--) {
			GungHoPlayer player = PLAYERS.get(i);
			Player p = player.getPlayer();
			if(p != null && Bukkit.getOnlinePlayers().contains(p)) {
				ItemStack equips = p.getInventory().getChestplate();
				player.setChestplate(equips);
			} else {
				PLAYERS.remove(player);
			}
		}
	}
	
	private boolean contains(Player player) {
		for(GungHoPlayer gungHo : PLAYERS) {
			if(gungHo.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.GUNG_HO, event)) return;
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
				if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO)) {
					Entity damaged = event.getEntity();
					if(damaged instanceof LivingEntity) {
						event.setDamage(event.getDamage() * 3);
					}
				}
			}
		}
	}
	
	protected static class GungHoPlayer{
		
		private Player player;
		private ItemStack chestplate, previousChestplate;
		
		public GungHoPlayer(Player player, ItemStack chestplate) {
			this.player = player;
			this.chestplate = chestplate;
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

		private void doEquip(ItemStack item) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO)){
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
				a.setBaseValue(a.getDefaultValue() / 2);
				if(player.getHealth() > a.getBaseValue()) {
					player.setHealth(a.getBaseValue());
				}
			}
		}

		private void doUnequip(ItemStack item) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO)){
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
				a.setBaseValue(a.getDefaultValue());
			}
		}
	}
	
}