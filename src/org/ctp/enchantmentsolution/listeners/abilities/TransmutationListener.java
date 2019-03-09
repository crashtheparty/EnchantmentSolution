package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

public class TransmutationListener implements Listener{
	
	private static Map<LivingEntity, ItemStack> HIT_ENTITY = new HashMap<LivingEntity, ItemStack>();
	private static Map<UUID, ItemStack> ENTITY_IDS = new HashMap<UUID, ItemStack>();
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event){
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.DROWNED)) return;
		Projectile proj = event.getEntity();
		if(proj instanceof Trident){
			Trident trident = (Trident) proj;
			if(trident.getShooter() instanceof Player){
				Player player = (Player) trident.getShooter();
				ItemStack tridentItem = player.getInventory().getItemInMainHand();
				if(trident != null) {
					ENTITY_IDS.put(trident.getUniqueId(), tridentItem);
				}
			}
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.TRANSMUTATION)) return;
		if(event.getEntity() instanceof Player) return;
		if(event.getEntity().getKiller() != null) {
			ItemStack killItem = event.getEntity().getKiller().getInventory().getItemInMainHand();
			if(HIT_ENTITY.containsKey(event.getEntity())) {
				killItem = HIT_ENTITY.get(event.getEntity());
			}
			if(Enchantments.hasEnchantment(killItem, DefaultEnchantments.TRANSMUTATION)) {
				for(int i = 0; i < event.getDrops().size(); i++) {
					ItemStack item = event.getDrops().get(i);
					if(!TransmutationLoot.isTransmutatedLoot(item)) {
						TransmutationLoot loot = TransmutationLoot.getRandomLoot();
						ItemStack lootItem = new ItemStack(loot.getMaterial(), (int) ((Math.random() * (loot.getMax() - loot.getMin())) + loot.getMin()));
						if(!loot.canStack()) {
							int random = (int) (Math.random() * loot.getMaterial().getMaxDurability());
							lootItem = DamageUtils.setDamage(lootItem, random);
						}
						event.getDrops().set(i, lootItem);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.TRANSMUTATION)) return;
		if(event.getHitEntity() instanceof LivingEntity) {
			if(event.getEntity() instanceof Trident) {
				Trident trident = (Trident) event.getEntity();
				ItemStack tridentItem = ENTITY_IDS.get(trident.getUniqueId());
				if(tridentItem != null) {
					HIT_ENTITY.put((LivingEntity) event.getHitEntity(), tridentItem);
					Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), new Runnable() {

						@Override
						public void run() {
							HIT_ENTITY.remove((LivingEntity) event.getHitEntity());
						}
						
					}, 1l);
					ENTITY_IDS.remove(trident.getUniqueId());
				}
			}
		}
	}
	
	private enum TransmutationLoot{
		RAW_SALMON(Material.SALMON, 100, 1, 2), RAW_COD(Material.COD, 100, 1, 2), TROPICAL_FISH(Material.TROPICAL_FISH, 50, 1, 1), 
		PUFFERFISH(Material.PUFFERFISH, 35, 1, 1), KELP(Material.KELP, 40, 1, 4), TRIDENT(Material.TRIDENT, 1, 1, 1), SCUTE(Material.SCUTE, 40, 1, 2), 
		TURTLE_EGG(Material.TURTLE_EGG, 10, 1, 1), INK_SAC(Material.INK_SAC, 100, 1, 4), PRISMARINE_SHARD(Material.PRISMARINE_SHARD, 80, 1, 3), 
		PRISMARINE_CRYSTALS(Material.PRISMARINE_CRYSTALS, 50, 1, 3), NAUTILUS_SHELL(Material.NAUTILUS_SHELL, 12, 1, 1), 
		HEART_OF_THE_SEA(Material.HEART_OF_THE_SEA, 2, 1, 1), BRAIN_CORAL(Material.BRAIN_CORAL, 20, 1, 3), BUBBLE_CORAL(Material.BUBBLE_CORAL, 20, 1, 3),
		FIRE_CORAL(Material.FIRE_CORAL, 20, 1, 3), HORN_CORAL(Material.HORN_CORAL, 20, 1, 3), TUBE_CORAL(Material.TUBE_CORAL, 20, 1, 3), 
		BRAIN_CORAL_FAN(Material.BRAIN_CORAL_FAN, 20, 1, 3), BUBBLE_CORAL_FAN(Material.BUBBLE_CORAL_FAN, 20, 1, 3), FIRE_CORAL_FAN(Material.FIRE_CORAL_FAN, 20, 1, 3), 
		HORN_CORAL_FAN(Material.HORN_CORAL_FAN, 20, 1, 3), TUBE_CORAL_FAN(Material.TUBE_CORAL_FAN, 20, 1, 3), BRAIN_CORAL_BLOCK(Material.BRAIN_CORAL_BLOCK, 20, 1, 2), 
		BUBBLE_CORAL_BLOCK(Material.BUBBLE_CORAL_BLOCK, 20, 1, 2), FIRE_CORAL_BLOCK(Material.FIRE_CORAL_BLOCK, 20, 1, 2), 
		HORN_CORAL_BLOCK(Material.HORN_CORAL_BLOCK, 20, 1, 2), TUBE_CORAL_BLOCK(Material.TUBE_CORAL_BLOCK, 20, 1, 2), SPONGE(Material.SPONGE, 50, 1, 1), 
		SEA_PICKLE(Material.SEA_PICKLE, 40, 1, 4);
		
		private Material material;
		private int chance, min, max;
		
		TransmutationLoot(Material material, int chance, int min, int max) {
			setMaterial(material);
			setChance(chance);
			setMin(min);
			setMax(max);
		}

		public Material getMaterial() {
			return material;
		}

		public void setMaterial(Material material) {
			this.material = material;
		}

		public int getChance() {
			return chance;
		}

		public void setChance(int chance) {
			this.chance = chance;
		}
		
		public boolean canStack() {
			if(this == TransmutationLoot.TRIDENT) return false;
			return true;
		}
		
		public static boolean isTransmutatedLoot(ItemStack item) {
			for(TransmutationLoot loot : values()) {
				if(loot.getMaterial().equals(item.getType())) {
					return true;
				}
			}
			return false;
		}
		
		public static TransmutationLoot getRandomLoot() {
			int chance = 0;
			for(TransmutationLoot loot : values()) {
				chance += loot.getChance();
			}
			
			int random = (int) (Math.random() * chance);
			
			for(TransmutationLoot loot : values()) {
				random -= loot.getChance();
				if(random <= 0) {
					return loot;
				}
			}
			return null;
		}

		public int getMin() {
			return min;
		}

		public void setMin(int min) {
			this.min = min;
		}

		public int getMax() {
			return max;
		}

		public void setMax(int max) {
			this.max = max;
		}
	}
	
}
