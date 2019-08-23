package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.Lootable;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.listeners.abilities.helpers.DrownedEntity;
import org.ctp.enchantmentsolution.listeners.abilities.helpers.TransmutationLoot;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.ItemType;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

@SuppressWarnings("unused")
public class DeathListener extends EnchantmentListener{

	private static List<UUID> SACRIFICE_ADVANCEMENT = new ArrayList<UUID>();
	private static HashMap<String, List<ItemStack>> SOUL_ITEMS = new HashMap<>();
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		runMethod(this, "beheading", event, EntityDeathEvent.class);
		runMethod(this, "drowned", event, EntityDeathEvent.class);
		runMethod(this, "expShare", event, EntityDeathEvent.class);
		runMethod(this, "pillage", event, EntityDeathEvent.class);
		runMethod(this, "transmutation", event, EntityDeathEvent.class);
	}
	
	private void beheading(EntityDeathEvent event) {
		if(!canRun(DefaultEnchantments.BEHEADING, event)) return;
		Entity entity = event.getEntity();
		Player killer = event.getEntity().getKiller();
		if(killer != null){
			if(Enchantments.hasEnchantment(killer.getInventory().getItemInMainHand(), DefaultEnchantments.BEHEADING)){
				double chance = Enchantments.getLevel(killer.getInventory().getItemInMainHand(), DefaultEnchantments.BEHEADING) * .05;
				double random = Math.random();
				if(chance > random){
					if(entity instanceof WitherSkeleton){
						ItemStack skull = new ItemStack(Material.WITHER_SKELETON_SKULL);
						for(ItemStack drop : event.getDrops()) {
							if(drop.getType().equals(Material.WITHER_SKELETON_SKULL)) {
								AdvancementUtils.awardCriteria(killer, ESAdvancement.DOUBLE_HEADER, "wither_skull");
								break;
							}
						}
						event.getDrops().add(skull);
					}else if(entity instanceof Skeleton){
						ItemStack skull = new ItemStack(Material.SKELETON_SKULL);
						event.getDrops().add(skull);
					}else if(entity instanceof Zombie){
						ItemStack skull = new ItemStack(Material.ZOMBIE_HEAD);
						event.getDrops().add(skull);
					}else if(entity instanceof Creeper){
						ItemStack skull = new ItemStack(Material.CREEPER_HEAD);
						event.getDrops().add(skull);
					}else if(entity instanceof Player){
						ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
						SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
						skullMeta.setOwningPlayer(((Player) entity));
						skullMeta.setDisplayName(ChatColor.DARK_RED + ((Player) entity).getName() + "'s Skull");
						skull.setItemMeta(skullMeta);
						if(((PlayerDeathEvent) event).getKeepInventory() == true) {
							ItemUtils.dropItem(skull, entity.getLocation());
						} else {
							event.getDrops().add(skull);
						}
						AdvancementUtils.awardCriteria(killer, ESAdvancement.HEADHUNTER, "player_head");
					}else if(entity instanceof EnderDragon){
						ItemStack skull = new ItemStack(Material.DRAGON_HEAD);
						event.getDrops().add(skull);
					}
				}
			}
		}
	}
	
	private void drowned(EntityDeathEvent event) {
		if(!canRun(DefaultEnchantments.DROWNED, event)) return;
		List<DrownedEntity> entities = DrownedEntity.ENTITIES;
		for(int i = entities.size() - 1; i >= 0; i--) {
			DrownedEntity entity = entities.get(i);
			if(entity.getHurtEntity().equals(event.getEntity())) {
				entities.remove(entity);
				break;
			}
		}
	}
	
	private void expShare(EntityDeathEvent event){
		if(!canRun(DefaultEnchantments.EXP_SHARE, event)) return;
		if(event.getEntity() instanceof Player){
			return;
		}
		Entity killer = event.getEntity().getKiller();
		if(killer instanceof Player){
			Player player = (Player) killer;
			ItemStack killItem = player.getInventory().getItemInMainHand();
			if(killItem != null && Enchantments.hasEnchantment(killItem, DefaultEnchantments.EXP_SHARE)){
				int exp = event.getDroppedExp();
				int level = Enchantments.getLevel(killItem, DefaultEnchantments.EXP_SHARE);
				event.setDroppedExp(AbilityUtils.setExp(exp, level));
			}
		}
	}
	
	private void pillage(EntityDeathEvent event) {
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			if(!canRun(DefaultEnchantments.PILLAGE, event)) return;
			if(event.getEntity().getKiller() != null) {
				Player player = event.getEntity().getKiller();
				ItemStack item = player.getInventory().getItemInOffHand();
				if(item == null || !Enchantments.hasEnchantment(item, DefaultEnchantments.PILLAGE)) {
					item = player.getInventory().getItemInMainHand();
					if(Enchantments.hasEnchantment(item, Enchantment.LOOT_BONUS_MOBS)) return;
				}
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.PILLAGE)) {
					event.getDrops().clear();
					int level = Enchantments.getLevel(item, DefaultEnchantments.PILLAGE);
					List<EnchantmentLevel> levels = Enchantments.getEnchantmentLevels(item);
					Enchantments.addEnchantmentToItem(item, DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), level);
					LootContext.Builder contextBuilder = new LootContext.Builder(event.getEntity().getLocation());
					contextBuilder.killer(player);
					contextBuilder.lootedEntity(event.getEntity());
					contextBuilder.lootingModifier(level);
					LootContext context = contextBuilder.build();
					Collection<ItemStack> items = ((Lootable)event.getEntity()).getLootTable().populateLoot(new Random(), context);
					event.getDrops().addAll(items);
					Enchantments.removeAllEnchantments(item);
					Enchantments.addEnchantmentsToItem(item, levels);
					if(event.getEntity().getType() == EntityType.PILLAGER) {
						AdvancementUtils.awardCriteria(player, ESAdvancement.LOOK_WHAT_YOU_MADE_ME_DO, "pillage");
					}
				}
			}
		}
	}
	
	private void transmutation(EntityDeathEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.TRANSMUTATION)) return;
		if(event.getEntity() instanceof Player) return;
		EntityDamageEvent e = event.getEntity().getLastDamageCause();
		if(e instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent entityDamage = (EntityDamageByEntityEvent) e;
			if(entityDamage.getDamager() instanceof Projectile) {
				Projectile p = (Projectile) entityDamage.getDamager();
				if(p.hasMetadata("transmutation")) {
					for(MetadataValue meta : p.getMetadata("transmutation")) {
						if(meta.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
							if(event.getEntity() instanceof Wither) {
								AdvancementUtils.awardCriteria(event.getEntity().getKiller(), ESAdvancement.CERBERUS, "obsidian");
								for(int i = 0; i < 64; i++) {
									event.getDrops().add(TransmutationLoot.getLoot(event.getEntity().getKiller()));
								}
								return;
							}
							boolean changedLoot = false;
							for(int i = 0; i < event.getDrops().size(); i++) {
								ItemStack item = event.getDrops().get(i);
								if(!TransmutationLoot.isTransmutatedLoot(item)) {
									changedLoot = true;
									event.getDrops().set(i, TransmutationLoot.getLoot(event.getEntity().getKiller()));
								}
							}
							if(!changedLoot) {
								AdvancementUtils.awardCriteria(event.getEntity().getKiller(), ESAdvancement.POSEIDONS_DAY_OFF, "day_off");
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event){
		runMethod(this, "sacrifice", event, PlayerDeathEvent.class);
		runMethod(this, "soulbound", event, PlayerDeathEvent.class);
	}
	
	private void sacrifice(PlayerDeathEvent event) {
		if(!canRun(DefaultEnchantments.SACRIFICE, event)) return;
		Player player = event.getEntity();
		ItemStack chest = player.getInventory().getChestplate();
		if(chest != null) {
			if(Enchantments.hasEnchantment(chest, DefaultEnchantments.SACRIFICE)) {
				int level = Enchantments.getLevel(chest, DefaultEnchantments.SACRIFICE);
				int playerLevel = player.getLevel();
				double damage = (playerLevel) / (8.0D / level);
				Entity killer = player.getKiller();
				if(killer != null) {
					if(killer instanceof Damageable) {
						((Damageable) killer).damage(damage);
						if(((Damageable) killer).getHealth() <= 0) {
							SACRIFICE_ADVANCEMENT.add(player.getUniqueId());
						}
					}
				}else{
					if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
						 EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) event
				                    .getEntity().getLastDamageCause();
						 if(nEvent.getDamager() instanceof Projectile) {
							 Projectile proj = (Projectile) nEvent.getDamager();
							 if (proj.getShooter() instanceof Damageable) {
								((Damageable) proj.getShooter()).damage(damage);
								if(((Damageable) proj.getShooter()).getHealth() <= 0) {
									SACRIFICE_ADVANCEMENT.add(player.getUniqueId());
								}
			                }
						 }else{
							 if (nEvent.getDamager() instanceof Damageable) {
								((Damageable) nEvent.getDamager()).damage(damage);
								if(((Damageable) nEvent.getDamager()).getHealth() <= 0) {
									SACRIFICE_ADVANCEMENT.add(player.getUniqueId());
								}
			                }
						 }
					}
				}
			}
		}
	}
	
	private void soulbound(PlayerDeathEvent event) {
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
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		runMethod(this, "sacrifice", event, PlayerRespawnEvent.class);
		runMethod(this, "soulbound", event, PlayerRespawnEvent.class);
	}
	
	private void sacrifice(PlayerRespawnEvent event) {
		if(SACRIFICE_ADVANCEMENT.contains(event.getPlayer().getUniqueId())) {
			AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.DIVINE_RETRIBUTION, "retribution");
			SACRIFICE_ADVANCEMENT.remove(event.getPlayer().getUniqueId());
		}
	}
	
	private void soulbound(PlayerRespawnEvent event){
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
