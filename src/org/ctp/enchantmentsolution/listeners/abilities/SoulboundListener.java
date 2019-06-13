package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class SoulboundListener extends EnchantmentListener{
	
	public static HashMap<String, List<ItemStack>> SOUL_ITEMS = new HashMap<>();
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event){
		if(!canRun(DefaultEnchantments.SOULBOUND, event)) return;
		List<ItemStack> items = event.getDrops();
		List<ItemStack> newItems = new ArrayList<ItemStack>();
		List<ItemStack> playerItems = new ArrayList<ItemStack>();
		Player player = event.getEntity();
		if(event.getKeepInventory()) return;
		
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
		if(!canRun(DefaultEnchantments.SOULBOUND, event)) return;
		Player player = event.getPlayer();
		HashMap<Material, Boolean> diamonds = new HashMap<Material, Boolean>();
		diamonds.put(Material.DIAMOND_AXE, false);
		diamonds.put(Material.DIAMOND_BOOTS, false);
		diamonds.put(Material.DIAMOND_CHESTPLATE, false);
		diamonds.put(Material.DIAMOND_HELMET, false);
		diamonds.put(Material.DIAMOND_HOE, false);
		diamonds.put(Material.DIAMOND_LEGGINGS, false);
		diamonds.put(Material.DIAMOND_PICKAXE, false);
		diamonds.put(Material.DIAMOND_SHOVEL, false);
		diamonds.put(Material.DIAMOND_SWORD, false);
		if(SOUL_ITEMS.get(player.getUniqueId().toString()) != null){
			for(ItemStack item : SOUL_ITEMS.get(player.getUniqueId().toString())){
				AdvancementUtils.awardCriteria(player, ESAdvancement.KEPT_ON_HAND, "soulbound");
				if(diamonds.containsKey(item.getType())) {
					diamonds.put(item.getType(), true);
				}
				player.getInventory().addItem(item);
			}
		}
		if(!diamonds.containsValue(false)) {
			AdvancementUtils.awardCriteria(player, ESAdvancement.READY_AFTER_DEATH, "soulbound");
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
			AdvancementUtils.awardCriteria(player.getKiller(), ESAdvancement.FEAR_THE_REAPER, "reaper");
			player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 250, 0.2, 2, 0.2);
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
			for(ItemStack item : items){
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.SOUL_REAPER)){
					AdvancementUtils.awardCriteria(player.getKiller(), ESAdvancement.REAPED_THE_REAPER, "reaper");
				}
			}
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
