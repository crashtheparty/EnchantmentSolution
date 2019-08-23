package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.listeners.abilities.helpers.*;

public class AbilityPlayerRunnable implements Runnable{
	private Map<UUID, List<AbilityPlayer>> PLAYERS = new HashMap<UUID, List<AbilityPlayer>>();
	
	@Override
	public void run() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			try {
				if(!PLAYERS.containsKey(player.getUniqueId())) {
					List<AbilityPlayer> abilities = new ArrayList<AbilityPlayer>();
					ItemStack elytra = player.getInventory().getChestplate();
					if(elytra != null && !elytra.getType().equals(Material.ELYTRA)) {
						elytra = null;
					}
					FrequentFlyerPlayer ffPlayer = new FrequentFlyerPlayer(player, elytra);
					if(ffPlayer.canFly() && !player.isOnGround() && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
						player.setFlying(true);
					}
					abilities.add(new ArmoredPlayer(player, player.getInventory().getChestplate()));
					abilities.add(ffPlayer);
					abilities.add(new GungHoPlayer(player, player.getInventory().getChestplate()));
					abilities.add(new LifePlayer(player, player.getInventory().getChestplate()));
					abilities.add(new NoRestPlayer(player, player.getInventory().getHelmet()));
					abilities.add(new QuickStrikePlayer(player, player.getInventory().getItemInMainHand()));
					abilities.add(new ToughnessPlayer(player, player.getInventory().getArmorContents()));
					abilities.add(new UnrestPlayer(player, player.getInventory().getHelmet()));
					PLAYERS.put(player.getUniqueId(), abilities);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		Iterator<Entry<UUID, List<AbilityPlayer>>> iterator = PLAYERS.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<UUID, List<AbilityPlayer>> entry = iterator.next();
			Player player = Bukkit.getPlayer(entry.getKey());
			if(player == null || !player.isOnline()) {
				iterator.remove();
				continue;
			}
			
			for(AbilityPlayer aPlayer : entry.getValue()) {
				if(!DefaultEnchantments.isEnabled(aPlayer.getEnchantment())) continue;
				try {
					if(aPlayer instanceof ToughnessPlayer) {
						ToughnessPlayer toughnessPlayer = (ToughnessPlayer) aPlayer;
						toughnessPlayer.setContents(aPlayer.getPlayer().getInventory().getArmorContents());
					} else if (aPlayer instanceof FrequentFlyerPlayer) {
						FrequentFlyerPlayer ffPlayer = (FrequentFlyerPlayer) aPlayer;
						ItemStack elytra = ffPlayer.getPlayer().getInventory().getChestplate();
						if (elytra != null && elytra.getType().equals(Material.ELYTRA)) {
							ffPlayer.setItem(elytra);
							if (player.isFlying() && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
								ffPlayer.minus();
							}
						} else {
							if((elytra == null || !elytra.getType().equals(Material.ELYTRA)) && !player.getGameMode().equals(GameMode.CREATIVE) 
									&& !player.getGameMode().equals(GameMode.SPECTATOR)) {
								ffPlayer.setItem(null);
								ffPlayer.setCanFly(false);
							}
						}
					} else if (aPlayer instanceof ArmoredPlayer || aPlayer instanceof GungHoPlayer || aPlayer instanceof LifePlayer) {
						aPlayer.setItem(aPlayer.getPlayer().getInventory().getChestplate());
					} else if (aPlayer instanceof NoRestPlayer || aPlayer instanceof UnrestPlayer) {
						aPlayer.setItem(aPlayer.getPlayer().getInventory().getHelmet());
					} else if (aPlayer instanceof QuickStrikePlayer) {
						aPlayer.setItem(aPlayer.getPlayer().getInventory().getItemInMainHand());
					}
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
