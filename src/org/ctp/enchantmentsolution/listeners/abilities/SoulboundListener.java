package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class SoulboundListener implements Listener{
	
	public static HashMap<String, List<ItemStack>> SOUL_ITEMS = new HashMap<>();
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.SOULBOUND)) return;
		List<ItemStack> items = event.getDrops();
		List<ItemStack> newItems = new ArrayList<ItemStack>();
		List<ItemStack> playerItems = new ArrayList<ItemStack>();
		Player player = event.getEntity();
		
		ItemStack killItem = null;
		if(player.getKiller() != null){
			if(player.getKiller() instanceof Player) {
				killItem = player.getKiller().getInventory().getItemInMainHand();
			}
		}
		if(killItem != null && DefaultEnchantments.isEnabled(DefaultEnchantments.SOUL_REAPER)) {
			getSoulReaper(event, player, killItem);
		} else {
			for(ItemStack item : items){
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.SOULBOUND)){
					newItems.add(item);
				}else{
					playerItems.add(item);
				}
			}
			event.getDrops().clear();
			for(ItemStack item : playerItems){
				event.getDrops().add(item);
			}
			SOUL_ITEMS.put(player.getUniqueId().toString(), newItems);
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		Player player = event.getPlayer();
		
		if(SOUL_ITEMS.get(player.getUniqueId().toString()) != null){
			for(ItemStack item : SOUL_ITEMS.get(player.getUniqueId().toString())){
				player.getInventory().addItem(item);
			}
		}
		SOUL_ITEMS.put(player.getUniqueId().toString(), null);
	}
	
	private void getSoulReaper(PlayerDeathEvent event, Player player, ItemStack killItem) {
		List<ItemStack> items = event.getDrops();
		List<ItemStack> newItems = new ArrayList<ItemStack>();
		List<ItemStack> playerItems = new ArrayList<ItemStack>();
		
		boolean stealItems = false;
		if(ItemType.HOES.getItemTypes().contains(killItem.getType()) && Enchantments.hasEnchantment(killItem, DefaultEnchantments.SOUL_REAPER)){
			double chance = Math.random();
			if(chance > 0.50){
				stealItems = true;
			}
		}
		if(stealItems){
			player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 250, 0.2, 2, 0.2);
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
		}else{
			for(ItemStack item : items){
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.SOULBOUND)){
					newItems.add(item);
				}else{
					playerItems.add(item);
				}
			}
			event.getDrops().clear();
			for(ItemStack item : playerItems){
				event.getDrops().add(item);
			}
		}
		SOUL_ITEMS.put(player.getUniqueId().toString(), newItems);
	}

}
