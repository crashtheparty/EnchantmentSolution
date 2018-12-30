package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class UnrestListener implements Listener, Runnable {

	private static List<UnrestPlayer> HAS_UNREST = new ArrayList<UnrestPlayer>();
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.UNREST)) return;
		Bukkit.getScheduler().runTaskLater(EnchantmentSolution.PLUGIN, new Runnable(){
			
			@Override
			public void run() {
				Player player = event.getPlayer();
				ItemStack helmet = player.getInventory().getHelmet();
				if(helmet != null) {
					helmet = null;
				}
				UnrestPlayer unrestPlayer = new UnrestPlayer(player, helmet);
				
				HAS_UNREST.add(unrestPlayer);
			}
		}, 0l);
		
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.UNREST)) return;
		UnrestPlayer remove = null;
		for(UnrestPlayer unrestPlayer : HAS_UNREST) {
			if(unrestPlayer.getPlayer().getUniqueId().toString().equals(event.getPlayer().getUniqueId().toString())) {
				unrestPlayer.setHelmet(null);
				remove = unrestPlayer;
				break;
			}
		}
		if(remove != null) {
			HAS_UNREST.remove(remove);
		}
	}

	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.UNREST)) return;
		for(UnrestPlayer unrestPlayer : HAS_UNREST) {
			Player player = unrestPlayer.getPlayer();
			if(player != null && Bukkit.getOnlinePlayers().contains(player)) {
				ItemStack helmet = player.getInventory().getHelmet();
				unrestPlayer.setHelmet(helmet);
				if(unrestPlayer.hasUnrest()) {
					if(player.getStatistic(Statistic.TIME_SINCE_REST) < 96000) {
						player.setStatistic(Statistic.TIME_SINCE_REST, 96000);
					}
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 245, 0, false, false), true);
				}
			}else {
				HAS_UNREST.remove(unrestPlayer);
			}
		}
	}
	
	protected class UnrestPlayer{
		
		private Player player;
		private ItemStack helmet;
		
		public UnrestPlayer(Player player, ItemStack helmet) {
			this.player = player;
			this.setHelmet(helmet);
		}

		public Player getPlayer() {
			return player;
		}

		public ItemStack getHelmet() {
			return helmet;
		}
		
		public void setHelmet(ItemStack helmet) {
			this.helmet = helmet;
		}
		
		public boolean hasUnrest() {
			if(helmet == null) {
				return false;
			}
			return Enchantments.hasEnchantment(helmet, DefaultEnchantments.UNREST);
		}
		
	}
	
}
