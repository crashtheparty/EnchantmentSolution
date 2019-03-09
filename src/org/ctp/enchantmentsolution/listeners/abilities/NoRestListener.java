package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class NoRestListener implements Runnable {

	private static List<NoRestPlayer> HAS_NOREST = new ArrayList<NoRestPlayer>();

	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.NO_REST)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!contains(player)) {
				try {
					ItemStack helmet = player.getInventory().getHelmet();
					NoRestPlayer noRestPlayer = new NoRestPlayer(player, helmet);
					HAS_NOREST.add(noRestPlayer);
				} catch(Exception ex) { }
			}
		}
		for(int i = HAS_NOREST.size() - 1; i >= 0; i--) {
			NoRestPlayer noRestPlayer = HAS_NOREST.get(i);
			Player player = noRestPlayer.getPlayer();
			if(player != null && Bukkit.getOnlinePlayers().contains(player)) {
				ItemStack helmet = player.getInventory().getHelmet();
				noRestPlayer.setHelmet(helmet);
				if(noRestPlayer.hasNoRest()) {
					player.setStatistic(Statistic.TIME_SINCE_REST, 0);
				}
			}else {
				HAS_NOREST.remove(noRestPlayer);
			}
		}
	}
	
	private boolean contains(Player player) {
		for(NoRestPlayer noRest : HAS_NOREST) {
			if(noRest.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
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
