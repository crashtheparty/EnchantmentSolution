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
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class NoRestListener implements Listener, Runnable {

	private static List<NoRestPlayer> HAS_NOREST = new ArrayList<NoRestPlayer>();
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.NO_REST)) return;
		Bukkit.getScheduler().runTaskLater(EnchantmentSolution.PLUGIN, new Runnable(){
			
			@Override
			public void run() {
				Player player = event.getPlayer();
				ItemStack helmet = player.getInventory().getHelmet();
				if(helmet != null) {
					helmet = null;
				}
				NoRestPlayer unrestPlayer = new NoRestPlayer(player, helmet);
				
				HAS_NOREST.add(unrestPlayer);
			}
		}, 0l);
		
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.NO_REST)) return;
		NoRestPlayer remove = null;
		for(NoRestPlayer unrestPlayer : HAS_NOREST) {
			if(unrestPlayer.getPlayer().getUniqueId().toString().equals(event.getPlayer().getUniqueId().toString())) {
				unrestPlayer.setHelmet(null);
				remove = unrestPlayer;
				break;
			}
		}
		if(remove != null) {
			HAS_NOREST.remove(remove);
		}
	}

	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.NO_REST)) return;
		for(NoRestPlayer unrestPlayer : HAS_NOREST) {
			Player player = unrestPlayer.getPlayer();
			if(player != null && Bukkit.getOnlinePlayers().contains(player)) {
				ItemStack helmet = player.getInventory().getHelmet();
				unrestPlayer.setHelmet(helmet);
				if(unrestPlayer.hasNoRest()) {
					player.setStatistic(Statistic.TIME_SINCE_REST, 0);
				}
			}else {
				HAS_NOREST.remove(unrestPlayer);
			}
		}
	}
	
	protected class NoRestPlayer{
		
		private Player player;
		private ItemStack helmet;
		
		public NoRestPlayer(Player player, ItemStack helmet) {
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
		
		public boolean hasNoRest() {
			if(helmet == null) {
				return false;
			}
			return Enchantments.hasEnchantment(helmet, DefaultEnchantments.NO_REST);
		}
		
	}
	
}
