package org.ctp.enchantmentsolution.threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.Reflectionable;
import org.ctp.enchantmentsolution.utils.abillityhelpers.FrequentFlyerPlayer;
import org.ctp.enchantmentsolution.utils.abillityhelpers.IcarusDelay;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

import com.sun.istack.internal.NotNull;

@SuppressWarnings("unused")
public class ElytraRunnable implements Runnable, Reflectionable {

	private static List<FrequentFlyerPlayer> PLAYERS = new ArrayList<FrequentFlyerPlayer>();
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
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.FREQUENT_FLYER)) return;

		Iterator<FrequentFlyerPlayer> iterator = PLAYERS.iterator();
		while (iterator.hasNext()) {
			FrequentFlyerPlayer ffPlayer = iterator.next();
			Player player = ffPlayer.getPlayer();
			if (player == null || !player.isOnline()) {
				iterator.remove();
				continue;
			}
			ItemStack elytra = player.getInventory().getChestplate();
			if (elytra != null) {
				ffPlayer.setElytra(elytra);
				if (player.isFlying() && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) ffPlayer.minus();
			} else if (elytra == null && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
				ffPlayer.setElytra(null);
				ffPlayer.setCanFly(false);
			}
		}
	}

	private void icarus() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.ICARUS)) return;
		List<IcarusDelay> icarusDelay = IcarusDelay.getIcarusDelay();
		for(int i = icarusDelay.size() - 1; i >= 0; i--) {
			IcarusDelay icarus = icarusDelay.get(i);
			icarus.minusDelay();
			if (icarus.getDelay() <= 0) {
				icarusDelay.remove(icarus);
				Player player = icarus.getPlayer().getPlayer();
				player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 250, 2, 2, 2);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
			}
		}
	}

	public static void addFlyer(@NotNull Player player, ItemStack elytra) {
		if (!contains(player)) {
			ChatUtils.sendInfo("Does not contain player");
			ChatUtils.sendInfo(elytra == null ? "null" : elytra.toString());
			if (elytra != null && ItemUtils.hasEnchantment(elytra, RegisterEnchantments.FREQUENT_FLYER)) { 
				FrequentFlyerPlayer ffPlayer = new FrequentFlyerPlayer(player, elytra);
				Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
					if(player.isGliding() || player.isFlying() || player.isInsideVehicle() || player.isRiptiding() || player.isSleeping() || player.isSwimming() || player.getLocation().getBlock().getType() == Material.WATER) return;
					
					if (ffPlayer.canFly() && player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) player.setFlying(true);
				}, 4l);
				PLAYERS.add(ffPlayer);
			}
		}
	}
	
	private static boolean contains(Player player) {
		for(FrequentFlyerPlayer ffPlayer : PLAYERS)
			if (ffPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return true;
		return false;
	}
}
