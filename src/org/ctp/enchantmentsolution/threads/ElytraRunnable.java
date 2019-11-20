package org.ctp.enchantmentsolution.threads;

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
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.Reflectionable;
import org.ctp.enchantmentsolution.utils.abillityhelpers.FrequentFlyerPlayer;
import org.ctp.enchantmentsolution.utils.abillityhelpers.IcarusDelay;

@SuppressWarnings("unused")
public class ElytraRunnable implements Runnable, Reflectionable {

	private Map<UUID, FrequentFlyerPlayer> PLAYERS = new HashMap<UUID, FrequentFlyerPlayer>();
	private int run;

	@Override
	public void run() {
		runMethod(this, "frequentFlyer");
		if (run % 20 == 0) {
			runMethod(this, "icarus");
			run = 0;
		}
		run++;
	}
	
	private void frequentFlyer() {
		if(!RegisterEnchantments.isEnabled(RegisterEnchantments.FREQUENT_FLYER)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			try {
				if(!PLAYERS.containsKey(player.getUniqueId())) {
					ItemStack elytra = player.getInventory().getChestplate();
					if(elytra != null && !elytra.getType().equals(Material.ELYTRA)) {
						elytra = null;
					}
					FrequentFlyerPlayer ffPlayer = new FrequentFlyerPlayer(player, elytra);
					if(ffPlayer.canFly() && !player.isOnGround() && player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
						player.setFlying(true);
					}
					PLAYERS.put(player.getUniqueId(), ffPlayer);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		Iterator<Entry<UUID, FrequentFlyerPlayer>> iterator = PLAYERS.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<UUID, FrequentFlyerPlayer> entry = iterator.next();
			Player player = Bukkit.getPlayer(entry.getKey());
			if(player == null || !player.isOnline()) {
				iterator.remove();
				continue;
			}
			FrequentFlyerPlayer ffPlayer = (FrequentFlyerPlayer) entry.getValue();
			ItemStack elytra = ffPlayer.getPlayer().getInventory().getChestplate();
			if (elytra != null && elytra.getType().equals(Material.ELYTRA)) {
				ffPlayer.setElytra(elytra);
				if (player.isFlying() && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
					ffPlayer.minus();
				}
			} else {
				if((elytra == null || !elytra.getType().equals(Material.ELYTRA)) && !player.getGameMode().equals(GameMode.CREATIVE) 
						&& !player.getGameMode().equals(GameMode.SPECTATOR)) {
					ffPlayer.setElytra(null);
					ffPlayer.setCanFly(false);
				}
			}
		}
	}
	
	private void icarus() {
		if(!RegisterEnchantments.isEnabled(RegisterEnchantments.ICARUS)) return;
		List<IcarusDelay> icarusDelay = IcarusDelay.getIcarusDelay();
		for(int i = icarusDelay.size() - 1; i >= 0; i--) {
			IcarusDelay icarus = icarusDelay.get(i);
			icarus.minusDelay();
			if(icarus.getDelay() <= 0) {
				icarusDelay.remove(icarus);
				Player player = icarus.getPlayer().getPlayer();
				player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 250, 2, 2, 2);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
			}
		}
	}
}
