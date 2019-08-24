package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.listeners.abilities.helpers.DrownedEntity;
import org.ctp.enchantmentsolution.listeners.abilities.helpers.EntityAccuracy;
import org.ctp.enchantmentsolution.nms.AnimalMobNMS;
import org.ctp.enchantmentsolution.nms.McMMO;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;

@SuppressWarnings("unused")
public class DamageListener extends EnchantmentListener{
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		runMethod(this, "brine", event, EntityDamageByEntityEvent.class);
		runMethod(this, "curseOfLag", event, EntityDamageByEntityEvent.class);
		runMethod(this, "drowned", event, EntityDamageByEntityEvent.class);
		runMethod(this, "gungHo", event, EntityDamageByEntityEvent.class);
		runMethod(this, "ironDefense", event, EntityDamageByEntityEvent.class);
		runMethod(this, "knockUp", event, EntityDamageByEntityEvent.class);
		runMethod(this, "magicGuard", event, EntityDamageByEntityEvent.class);
		runMethod(this, "sandVeil", event, EntityDamageByEntityEvent.class);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		runMethod(this, "magicGuard", event, EntityDamageEvent.class);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEntityDamageByEntityHighest(EntityDamageByEntityEvent event) {
		runMethod(this, "irenesLasso", event, EntityDamageByEntityEvent.class);
		runMethod(this, "shockAspect", event, EntityDamageByEntityEvent.class);
		runMethod(this, "stoneThrow", event, EntityDamageByEntityEvent.class);
		runMethod(this, "warp", event, EntityDamageByEntityEvent.class);
	}
	
	private void brine(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.BRINE, event)) return;
		Entity entity = event.getDamager();
		if(entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.BRINE)) {
				Entity damaged = event.getEntity();
				if(damaged instanceof LivingEntity) {
					LivingEntity living = (LivingEntity) damaged;
					AttributeInstance a = living.getAttribute(Attribute.GENERIC_MAX_HEALTH);
					double maxHealth = a.getValue();
					double health = living.getHealth();
					if(health <= maxHealth / 2) {
						event.setDamage(event.getDamage() * 2);
					}
				}
			}
		}
	}
	
	private void curseOfLag(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.CURSE_OF_LAG, event)) return;
		Entity entity = event.getDamager();
		if(entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.CURSE_OF_LAG)) {
				AbilityUtils.createEffects(player);
			}
		}
	}
	
	private void drowned(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.DROWNED, event)) return;
		if(event.getDamager() instanceof Trident) {
			Trident trident = (Trident) event.getDamager();
			if(trident.hasMetadata("drowned")) {
				HumanEntity human = (HumanEntity) trident.getShooter();
				List<MetadataValue> values = trident.getMetadata("drowned");
				for(MetadataValue value : values) {
					if(value.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
						int level = value.asInt();
						int ticks = (1 + level * 3) * 20;
						if(event.getEntity() instanceof LivingEntity) {
							LivingEntity entity = (LivingEntity) event.getEntity();
							if(!(entity instanceof WaterMob) && !(entity instanceof Drowned) && !(entity instanceof Guardian)) {
								DrownedEntity.addNewDrowned(entity, human, ticks);
							}
						}
					}
				}
			}
		} else {
			if(event.getDamager() instanceof HumanEntity) {
				HumanEntity human = (HumanEntity) event.getDamager();
				ItemStack item = human.getInventory().getItemInMainHand();
				if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.DROWNED)){
					int level = Enchantments.getLevel(item, DefaultEnchantments.DROWNED);
					int ticks = (1 + level * 3) * 20;
					if(event.getEntity() instanceof LivingEntity) {
						LivingEntity entity = (LivingEntity) event.getEntity();
						if(!(entity instanceof WaterMob) && !(entity instanceof Drowned) && !(entity instanceof Guardian)) {
							DrownedEntity.addNewDrowned(entity, human, ticks);
						}
					}
				}
			}
		}
	}
	
	private void gungHo(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.GUNG_HO, event)) return;
		Entity damager = event.getDamager();
		if(damager instanceof Projectile) {
			Projectile projectile = (Projectile) event.getDamager();
			if(projectile instanceof Snowball || projectile instanceof Egg) return;
			if(projectile.getShooter() instanceof Player) {
				Player player = (Player) projectile.getShooter();
				ItemStack item = player.getInventory().getChestplate();
				if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO)) {
					Entity damaged = event.getEntity();
					if(damaged instanceof LivingEntity) {
						event.setDamage(event.getDamage() * 3);
					}
				}
			}
		} else {
			if(damager instanceof Player) {
				Player player = (Player) damager;
				ItemStack item = player.getInventory().getChestplate();
				if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO)) {
					Entity damaged = event.getEntity();
					if(damaged instanceof LivingEntity) {
						event.setDamage(event.getDamage() * 3);
					}
				}
			}
		}
	}
	
	private void irenesLasso(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.IRENES_LASSO, event)) return;
		Entity attacker = event.getDamager();
		Entity attacked = event.getEntity();
		if(attacker instanceof Player && attacked instanceof Animals){
			Player player = (Player) attacker;
			ItemStack attackItem = player.getInventory().getItemInMainHand();
			if(attackItem != null && Enchantments.hasEnchantment(attackItem, DefaultEnchantments.IRENES_LASSO)){
				event.setCancelled(true);
				int max = Enchantments.getLevel(attackItem, DefaultEnchantments.IRENES_LASSO);
				int current = 0;
				for(AnimalMob animal : EnchantmentSolution.getAnimals()) {
					if((animal.getItem() != null && animal.getItem().equals(attackItem)) || StringUtils.getAnimalIDsFromItem(attackItem).contains(animal.getEntityID())) {
						current ++;
					}
				}
				if(current >= max) return;
				if(attacked instanceof Tameable) {
					if(((Tameable) attacked).getOwner() != null && !((Tameable) attacked).getOwner().getUniqueId().equals(player.getUniqueId())) {
						ChatUtils.sendMessage(player, ChatUtils.getMessage(ChatUtils.getCodes(), "irenes_lasso.error"));
						return;
					}
					String type = attacked.getType().name().toLowerCase();
					AdvancementUtils.awardCriteria(player, ESAdvancement.THORGY, type);
					AdvancementUtils.awardCriteria(player, ESAdvancement.FREE_PETS, type);
				}
				McMMO.customName(attacked);
				EnchantmentSolution.addAnimals(AnimalMobNMS.getMob((Animals) attacked, attackItem));
				attacked.remove();
			}
		}
	}

	private void ironDefense(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.IRON_DEFENSE, event)) return;
		if(!AbilityUtils.getContactCauses().contains(event.getCause())) return;
		Entity attacked = event.getEntity();
		Entity attacker = event.getDamager();
		if(attacker instanceof AreaEffectCloud) return;
		if(attacked instanceof HumanEntity){
			HumanEntity player = (HumanEntity) attacked;
			ItemStack shield = player.getEquipment().getItemInOffHand();
			if(shield == null) return;
			if(player.isBlocking()) return;
			if(Enchantments.hasEnchantment(shield, DefaultEnchantments.IRON_DEFENSE)){
				int level = Enchantments.getLevel(shield, DefaultEnchantments.IRON_DEFENSE);
				double percentage = .1 + .05 * level;
				double originalDamage = event.getDamage();
				double damage = originalDamage * percentage;
				event.setDamage(originalDamage - damage);
				int shieldDamage = (int) damage;
				if(shieldDamage < damage) shieldDamage += 1;
				super.damageItem(player, shield, shieldDamage);
				if(player instanceof Player) {
					if((int) (damage * 10) > 0 && EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 1) {
						((Player) player).incrementStatistic(Statistic.DAMAGE_BLOCKED_BY_SHIELD, (int) (damage * 10));
					}
					if(player.getHealth() <= originalDamage && player.getHealth() > originalDamage - damage) {
						AdvancementUtils.awardCriteria((Player) player, ESAdvancement.IRON_MAN, "blocked"); 
					}
				}
			}
		}
	}
	
	private void knockUp(EntityDamageByEntityEvent event){
		if(!canRun(DefaultEnchantments.KNOCKUP, event)) return;
		Entity attacker = event.getDamager();
		Entity attacked = event.getEntity();
		if(attacker instanceof Player){
			Player player = (Player) attacker;
			ItemStack attackItem = player.getInventory().getItemInMainHand();
			if(Enchantments.hasEnchantment(attackItem, DefaultEnchantments.KNOCKUP)){
				int level = Enchantments.getLevel(attackItem, DefaultEnchantments.KNOCKUP);
				Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), new Runnable(){

					@Override
					public void run() {
						double levelMultiplier = 0.18;
						attacked.setVelocity(
								new Vector(attacked.getVelocity().getX(), 0.275184010449 + levelMultiplier * level, attacked.getVelocity().getZ()));
					}
					
				}, 0l);
			}
		}
	}
	
	private void magicGuard(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.MAGIC_GUARD, event)) return;
		if(event.getDamager() instanceof AreaEffectCloud) {
			if(event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				ItemStack shield = player.getInventory().getItemInOffHand();
				if(shield.getType().equals(Material.SHIELD)) {
					if(Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	private void sandVeil(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.SAND_VEIL, event)) return;
		Entity damager = event.getDamager();
		if(damager instanceof Projectile) {
			if(((Projectile) damager).getShooter() instanceof Entity) {
				damager = (Entity) ((Projectile) damager).getShooter();
			} else {
				return;
			}
		}
		EntityAccuracy ea = null;
		for(EntityAccuracy entity : EntityAccuracy.getEntities()) {
			if(entity.getEntity().equals(damager)) {
				ea = entity;
				break;
			}
		}
		if(ea != null) {
			double accuracy = ea.getAccuracy();
			double random = Math.random();
			if(accuracy <= random) {
				event.setCancelled(true);
				event.getEntity().getWorld().spawnParticle(Particle.CLOUD, event.getEntity().getLocation(), 200, 0.2, 0.2, 0.2);
				AdvancementUtils.awardCriteria(ea.getAttacker(), ESAdvancement.MISSED, "sand", 1);
			}
		}
		if(!event.isCancelled()) {
			if(damager instanceof Player && event.getEntity() instanceof LivingEntity) {
				Player player = (Player) damager;
				ItemStack item = player.getInventory().getItemInMainHand();
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.SAND_VEIL)) {
					double accuracy = 1 - Enchantments.getLevel(item, DefaultEnchantments.SAND_VEIL) * .05;
					LivingEntity entity = (LivingEntity) event.getEntity();
					ea = null;
					for(EntityAccuracy entityAccuracy : EntityAccuracy.getEntities()) {
						if(entityAccuracy.getEntity().equals(entity)) {
							ea = entityAccuracy;
							break;
						}
					}
					if(ea == null) {
						EntityAccuracy.addEntity(player, entity, accuracy);
					} else {
						ea.resetRun();
						ea.setAccuracy(accuracy);
					}
				}
			}
		}
	}
	
	private void shockAspect(EntityDamageByEntityEvent event){
		if(!canRun(DefaultEnchantments.SHOCK_ASPECT, event)) return;
		if(event.getDamage() == 0) return;
		Entity entity = event.getDamager();
		if(entity instanceof Player){
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.SHOCK_ASPECT) && item.getType() != Material.BOOK){
				int level = Enchantments.getLevel(item, DefaultEnchantments.SHOCK_ASPECT);
				double chance = .05 + 0.1 * level;
				double random = Math.random();
				if(chance > random){
					World world = player.getLocation().getWorld();
					world.strikeLightning(event.getEntity().getLocation());
					if(event.getEntityType() == EntityType.CREEPER) {
						AdvancementUtils.awardCriteria(player, ESAdvancement.SUPER_CHARGED, "lightning");
					}
				}
			}
		}
	}
	
	private void stoneThrow(EntityDamageByEntityEvent event) {
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			if(!canRun(DefaultEnchantments.STONE_THROW, event)) return;
			switch(event.getEntityType()) {
			case BAT:
			case BLAZE:
			case ENDER_DRAGON:
			case GHAST:
			case PARROT:
			case PHANTOM:
			case VEX:
			case WITHER:
				if(event.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getDamager();
					ProjectileSource shooter = arrow.getShooter();
					if(shooter instanceof HumanEntity) {
						HumanEntity entity = (HumanEntity) shooter;
						ItemStack item = entity.getInventory().getItemInOffHand();
						if(item == null || !Enchantments.hasEnchantment(item, DefaultEnchantments.STONE_THROW)) {
							item = entity.getInventory().getItemInMainHand();
						}
						if(Enchantments.hasEnchantment(item, DefaultEnchantments.STONE_THROW)) {
							int level = Enchantments.getLevel(item, DefaultEnchantments.STONE_THROW);
							double percentage = .4 * level + 1.2;
							int extraDamage = (int) (percentage * event.getDamage() + .5);
							event.setDamage(extraDamage);
							if(entity instanceof Player && event.getEntity() instanceof Phantom) {
								Phantom phantom = (Phantom) event.getEntity();
								if(phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() == phantom.getHealth() && phantom.getHealth() <= extraDamage) {
									AdvancementUtils.awardCriteria((Player) entity, ESAdvancement.JUST_DIE_ALREADY, "phantom");
								}
							}
							if(entity instanceof Player && event.getEntity() instanceof EnderDragon) {
								AdvancementUtils.awardCriteria((Player) entity, ESAdvancement.UNDERKILL, "dragon");
							}
						}
					}
				}
				break;
			default:
				break;
			}
		}
	}
	
	private void warp(EntityDamageByEntityEvent event){
		if(!canRun(DefaultEnchantments.WARP, event)) return;
		if(event.getDamage() == 0) return;
		Entity attacked = event.getEntity();
		if(attacked instanceof LivingEntity){
			LivingEntity player = (LivingEntity) attacked;
			ItemStack leggings = player.getEquipment().getLeggings();
			if(leggings == null) return;
			if(Enchantments.hasEnchantment(leggings, DefaultEnchantments.WARP)){
				int level = Enchantments.getLevel(leggings, DefaultEnchantments.WARP);
				List<Location> locsToTp = new ArrayList<Location>();
				for(int x = - (level + 4); x <= level + 5; x++){
					for(int y = - (level + 4); y <= level + 5; y++){
						for(int z = - (level + 4); z <= level + 5; z++){
							if(x == y && x == 0 && z == 0){
								continue;
							}
							Location tp = new Location(player.getWorld(), player.getLocation().getBlockX() + x, player.getLocation().getBlockY() + y, 
									player.getLocation().getBlockZ() + z);
							if(player.getWorld().getBlockAt(new Location(tp.getWorld(), tp.getBlockX(), tp.getBlockY() + 1, tp.getBlockZ())).getType().equals(Material.AIR)
									&& player.getWorld().getBlockAt(new Location(tp.getWorld(), tp.getBlockX(), tp.getBlockY(), tp.getBlockZ())).getType().equals(Material.AIR)
									&& !(player.getWorld().getBlockAt(new Location(tp.getWorld(), tp.getBlockX(), tp.getBlockY() - 1, tp.getBlockZ())).getType().equals(Material.AIR))){
								locsToTp.add(tp);
							}
							
						}
					}
				}
				double chance = .25;
				double random = Math.random();
				if(chance > random && locsToTp.size() > 0){
					int randomLoc = (int) (Math.random() * locsToTp.size());
					Location toTeleport = locsToTp.get(randomLoc);
					World world = toTeleport.getWorld();
					world.spawnParticle(Particle.PORTAL, toTeleport, 50, 0.2, 2, 0.2);
					world.spawnParticle(Particle.PORTAL, attacked.getLocation(), 50, 0.2, 2, 0.2);
					world.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
					
					toTeleport.setYaw(player.getLocation().getYaw());
					toTeleport.setPitch(player.getLocation().getPitch());
					player.teleport(toTeleport);
					if(player instanceof Player && event.getDamager() instanceof Enderman) {
						AdvancementUtils.awardCriteria((Player) player, ESAdvancement.IM_YOU_BUT_SHORTER, "enderpearl"); 
					}
				}
			}
		}
	}
	
	private void magicGuard(EntityDamageEvent event) {
		if(!canRun(DefaultEnchantments.MAGIC_GUARD, event)) return;
		if(event.getCause().equals(DamageCause.MAGIC) || event.getCause().equals(DamageCause.POISON)) {
			if(event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				ItemStack shield = player.getInventory().getItemInOffHand();
				if(shield.getType().equals(Material.SHIELD)) {
					if(Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
