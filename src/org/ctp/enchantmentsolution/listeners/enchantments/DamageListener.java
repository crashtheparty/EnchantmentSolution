package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.*;
import org.ctp.enchantmentsolution.events.modify.LagEvent;
import org.ctp.enchantmentsolution.events.potion.MagicGuardPotionEvent;
import org.ctp.enchantmentsolution.events.teleport.WarpEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.mcmmo.McMMOHandler;
import org.ctp.enchantmentsolution.nms.AnimalMobNMS;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.abillityhelpers.DrownedEntity;
import org.ctp.enchantmentsolution.utils.abillityhelpers.EntityAccuracy;
import org.ctp.enchantmentsolution.utils.abillityhelpers.ParticleEffect;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

@SuppressWarnings("unused")
public class DamageListener extends Enchantmentable {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		runMethod(this, "brine", event, EntityDamageByEntityEvent.class);
		runMethod(this, "magicGuard", event, EntityDamageByEntityEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntityHighest(EntityDamageByEntityEvent event) {
		runMethod(this, "curseOfLag", event, EntityDamageByEntityEvent.class);
		runMethod(this, "drowned", event, EntityDamageByEntityEvent.class);
		runMethod(this, "irenesLasso", event, EntityDamageByEntityEvent.class);
		runMethod(this, "ironDefense", event, EntityDamageByEntityEvent.class);
		runMethod(this, "knockUp", event, EntityDamageByEntityEvent.class);
		runMethod(this, "sandVeil", event, EntityDamageByEntityEvent.class);
		runMethod(this, "shockAspect", event, EntityDamageByEntityEvent.class);
		runMethod(this, "stoneThrow", event, EntityDamageByEntityEvent.class);
		runMethod(this, "warp", event, EntityDamageByEntityEvent.class);
		runMethod(this, "test", event, EntityDamageByEntityEvent.class);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		runMethod(this, "magicGuard", event, EntityDamageEvent.class);
	}

	@EventHandler
	public void onPlayerItemDamage(PlayerItemDamageEvent event) {
		runMethod(this, "tank", event, PlayerItemDamageEvent.class);
	}

	private void brine(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.BRINE, event)) {
			return;
		}
		Entity entity = event.getDamager();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (ItemUtils.hasEnchantment(item, RegisterEnchantments.BRINE)) {
				Entity damaged = event.getEntity();
				if (damaged instanceof LivingEntity) {
					LivingEntity living = (LivingEntity) damaged;
					AttributeInstance a = living.getAttribute(Attribute.GENERIC_MAX_HEALTH);
					double maxHealth = a.getValue();
					double health = living.getHealth();
					if (health <= maxHealth / 2) {
						BrineEvent brine = new BrineEvent(living, player, event.getDamage(), event.getDamage() * 2);
						Bukkit.getPluginManager().callEvent(brine);

						if (!brine.isCancelled()) {
							event.setDamage(brine.getNewDamage());
						}
					}
				}
			}
		}
	}

	private void curseOfLag(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.CURSE_OF_LAG, event)) {
			return;
		}
		Entity entity = event.getDamager();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_LAG)) {
				LagEvent lag = new LagEvent(player, player.getLocation(), AbilityUtils.createEffects(player));

				Bukkit.getPluginManager().callEvent(lag);
				if (!lag.isCancelled() && lag.getEffects().size() > 0) {
					Location loc = lag.getLocation();
					for(ParticleEffect effect: lag.getEffects()) {
						loc.getWorld().spawnParticle(effect.getParticle(), loc, effect.getNum(), effect.getVarX(),
						effect.getVarY(), effect.getVarZ());
					}
					if (lag.getSound() != null) {
						loc.getWorld().playSound(loc, lag.getSound(), lag.getVolume(), lag.getPitch());
					}
					AdvancementUtils.awardCriteria(player, ESAdvancement.LAAAGGGGGG, "lag");
				}
			}
		}
	}

	private void drowned(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.DROWNED, event)) {
			return;
		}
		if (event.getDamager() instanceof Trident) {
			Trident trident = (Trident) event.getDamager();
			if (trident.hasMetadata("drowned")) {
				HumanEntity human = (HumanEntity) trident.getShooter();
				List<MetadataValue> values = trident.getMetadata("drowned");
				for(MetadataValue value: values) {
					if (value.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
						int level = value.asInt();
						if (event.getEntity() instanceof LivingEntity) {
							applyDrowned(level, human, (LivingEntity) event.getEntity(), event);
						}
					}
				}
			}
		} else {
			if (event.getDamager() instanceof HumanEntity) {
				HumanEntity human = (HumanEntity) event.getDamager();
				ItemStack item = human.getInventory().getItemInMainHand();
				if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.DROWNED)) {
					int level = ItemUtils.getLevel(item, RegisterEnchantments.DROWNED);
					if (event.getEntity() instanceof LivingEntity) {
						applyDrowned(level, human, (LivingEntity) event.getEntity(), event);
					}
				}
			}
		}
	}

	private void gungHo(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.GUNG_HO, event)) {
			return;
		}
		Entity damager = event.getDamager();
		Player player = null;
		if (damager instanceof Projectile) {
			Projectile projectile = (Projectile) event.getDamager();
			if (projectile instanceof Snowball || projectile instanceof Egg) {
				return;
			}
			if (projectile.getShooter() instanceof Player) {
				player = (Player) projectile.getShooter();
			}
		} else if (damager instanceof Player) {
			player = (Player) damager;
		}
		if (player != null) {
			ItemStack item = player.getInventory().getChestplate();
			if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.GUNG_HO)) {
				Entity damaged = event.getEntity();
				if (damaged instanceof LivingEntity) {
					GungHoEvent gungHo = new GungHoEvent((LivingEntity) damaged, player, event.getDamage(),
					event.getDamage() * 3);
					Bukkit.getPluginManager().callEvent(gungHo);
					if (!gungHo.isCancelled()) {
						event.setDamage(gungHo.getNewDamage());
					}
				}
			}
		}
	}

	private void irenesLasso(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.IRENES_LASSO, event)) {
			return;
		}
		Entity attacker = event.getDamager();
		Entity attacked = event.getEntity();
		if (attacker instanceof Player && attacked instanceof Animals) {
			Player player = (Player) attacker;
			Animals animals = (Animals) attacked;
			ItemStack attackItem = player.getInventory().getItemInMainHand();
			if (attackItem != null && ItemUtils.hasEnchantment(attackItem, RegisterEnchantments.IRENES_LASSO)) {
				if(!AnimalMobNMS.canAddMob()) {
					return;
				}
				event.setCancelled(true);
				int max = ItemUtils.getLevel(attackItem, RegisterEnchantments.IRENES_LASSO);
				int current = 0;
				for(AnimalMob animal: EnchantmentSolution.getAnimals()) {
					if (animal.inItem(attackItem)) {
						current++;
					}
				}
				if (current >= max) {
					return;
				}
				LassoDamageEvent lasso = new LassoDamageEvent(animals, player);
				Bukkit.getPluginManager().callEvent(lasso);
				if (!lasso.isCancelled()) {
					if (attacked instanceof Tameable) {
						if (((Tameable) attacked).getOwner() != null
						&& !((Tameable) attacked).getOwner().getUniqueId().equals(player.getUniqueId())) {
							ChatUtils.sendMessage(player,
							ChatUtils.getMessage(ChatUtils.getCodes(), "irenes_lasso.error"));
							return;
						}
						String type = attacked.getType().name().toLowerCase();
						AdvancementUtils.awardCriteria(player, ESAdvancement.THORGY, type);
						AdvancementUtils.awardCriteria(player, ESAdvancement.FREE_PETS, type);
					}
					McMMOHandler.customName(attacked);
					EnchantmentSolution.addAnimals(AnimalMobNMS.getMob(animals, attackItem));
					attacked.remove();
				}
			}
		}
	}

	private void test(EntityDamageByEntityEvent event) {
		ChatUtils.sendInfo("Entity: " + event.getEntityType());
		ChatUtils.sendInfo("Entity: " + event.getEntity().getType());
	}

	private void ironDefense(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.IRON_DEFENSE, event)) {
			return;
		}
		if (!ESArrays.getContactCauses().contains(event.getCause())) {
			return;
		}
		Entity attacked = event.getEntity();
		Entity attacker = event.getDamager();
		if (attacker instanceof AreaEffectCloud) {
			return;
		}
		if (attacked instanceof HumanEntity) {
			HumanEntity player = (HumanEntity) attacked;
			ItemStack shield = player.getEquipment().getItemInOffHand();
			if (shield == null) {
				return;
			}
			if (player.isBlocking()) {
				return;
			}
			if (ItemUtils.hasEnchantment(shield, RegisterEnchantments.IRON_DEFENSE)) {
				int level = ItemUtils.getLevel(shield, RegisterEnchantments.IRON_DEFENSE);
				double percentage = .1 + .05 * level;
				double originalDamage = event.getDamage();
				double damage = originalDamage * percentage;
				int shieldDamage = (int) damage;
				if (shieldDamage < damage) {
					shieldDamage += 1;
				}

				IronDefenseEvent ironDefense = new IronDefenseEvent(player, originalDamage, damage, shield,
				shieldDamage);
				Bukkit.getPluginManager().callEvent(ironDefense);

				if (!ironDefense.isCancelled()) {
					event.setDamage(originalDamage - damage);
					DamageUtils.damageItem(player, shield, shieldDamage);

					if (player instanceof Player) {
						if ((int) (damage * 10) > 0
						&& EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 1) {
							((Player) player).incrementStatistic(Statistic.DAMAGE_BLOCKED_BY_SHIELD,
							(int) (damage * 10));
						}
						if (player.getHealth() <= originalDamage && player.getHealth() > originalDamage - damage) {
							AdvancementUtils.awardCriteria((Player) player, ESAdvancement.IRON_MAN, "blocked");
						}
					}
				}
			}
		}
	}

	private void knockUp(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.KNOCKUP, event)) {
			return;
		}
		Entity attacker = event.getDamager();
		Entity attacked = event.getEntity();
		if (attacker instanceof Player && attacked instanceof LivingEntity) {
			Player player = (Player) attacker;
			ItemStack attackItem = player.getInventory().getItemInMainHand();
			if (ItemUtils.hasEnchantment(attackItem, RegisterEnchantments.KNOCKUP)) {
				int level = ItemUtils.getLevel(attackItem, RegisterEnchantments.KNOCKUP);
				double levelMultiplier = 0.18;
				KnockUpEvent knockUp = new KnockUpEvent((LivingEntity) attacked, player, event.getDamage(),
				0.275184010449 + levelMultiplier * level);
				Bukkit.getPluginManager().callEvent(knockUp);
				if (!knockUp.isCancelled()) {
					event.setDamage(knockUp.getNewDamage());
					Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), (Runnable) () -> {
						double knockup = knockUp.getKnockUp();
						if (attacked.isDead()) {
							knockup /= 1.5;
						}
						attacked.setVelocity(
						new Vector(attacked.getVelocity().getX(), knockup, attacked.getVelocity().getZ()));
					}, 0l);
				}
			}
		}
	}

	private void magicGuard(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.MAGIC_GUARD, event)) {
			return;
		}
		if (event.getDamager() instanceof AreaEffectCloud) {
			if (event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				ItemStack shield = player.getInventory().getItemInOffHand();
				if (shield.getType().equals(Material.SHIELD)) {
					if (ItemUtils.hasEnchantment(shield, RegisterEnchantments.MAGIC_GUARD)) {
						MagicGuardPotionEvent magicGuard = new MagicGuardPotionEvent(player,
						((AreaEffectCloud) event.getDamager()).getBasePotionData().getType().getEffectType());
						Bukkit.getPluginManager().callEvent(magicGuard);

						if (!magicGuard.isCancelled()) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

	private void sandVeil(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.SAND_VEIL, event)) {
			return;
		}
		Entity damager = event.getDamager();
		if (damager instanceof Projectile) {
			if (((Projectile) damager).getShooter() instanceof Entity) {
				damager = (Entity) ((Projectile) damager).getShooter();
			} else {
				return;
			}
		}
		if (damager == null) {
			return;
		}
		EntityAccuracy ea = null;
		Iterator<EntityAccuracy> iterator = EnchantmentSolution.getAccuracy().iterator();
		while (iterator.hasNext()) {
			EntityAccuracy entry = iterator.next();
			if (damager.equals(entry.getEntity())) {
				ea = entry;
				break;
			}
		}
		if (ea != null && event.getEntity() instanceof LivingEntity && damager instanceof LivingEntity) {
			double accuracy = ea.getAccuracy();
			double random = Math.random();

			SandVeilMissEvent sandVeil = new SandVeilMissEvent((LivingEntity) event.getEntity(), (LivingEntity) damager,
			event.getDamage(), true, accuracy);
			Bukkit.getPluginManager().callEvent(sandVeil);

			if (!sandVeil.isCancelled()) {
				if (sandVeil.getAccuracy() <= random) {
					event.setCancelled(true);
					if (sandVeil.isParticles()) {
						event.getEntity().getWorld().spawnParticle(Particle.CLOUD, event.getEntity().getLocation(), 200,
						0.2, 0.2, 0.2);
					}
					AdvancementUtils.awardCriteria(ea.getAttacker(), ESAdvancement.MISSED, "sand", 1);
				} else {
					event.setDamage(sandVeil.getNewDamage());
				}
			}
		}
		if (!event.isCancelled()) {
			if (damager instanceof Player && event.getEntity() instanceof LivingEntity) {
				Player player = (Player) damager;
				ItemStack item = player.getInventory().getItemInMainHand();
				if (ItemUtils.hasEnchantment(item, RegisterEnchantments.SAND_VEIL)) {
					double accuracy = 1 - ItemUtils.getLevel(item, RegisterEnchantments.SAND_VEIL) * .05;
					LivingEntity entity = (LivingEntity) event.getEntity();
					ea = null;
					iterator = EnchantmentSolution.getAccuracy().iterator();
					while (iterator.hasNext()) {
						EntityAccuracy entry = iterator.next();
						if (entry.getEntity().equals(entity)) {
							ea = entry;
							break;
						}
					}

					if (ea != null && accuracy > ea.getAccuracy()) {
						accuracy = ea.getAccuracy();
					}

					SandVeilEvent sandVeil = new SandVeilEvent(entity, player, event.getDamage(), event.getDamage(),
					160, accuracy);
					Bukkit.getPluginManager().callEvent(sandVeil);

					if (!sandVeil.isCancelled()) {
						if (ea != null) {
							ea.setAccuracy(sandVeil.getAccuracy());
							ea.setTicks(ea.getTicks());
						} else {
							EnchantmentSolution.addAccuracy(
							new EntityAccuracy(player, entity, sandVeil.getAccuracy(), sandVeil.getTicks()));
						}
					}
				}
			}
		}
	}

	private void shockAspect(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.SHOCK_ASPECT, event)) {
			return;
		}
		if (event.getDamage() == 0) {
			return;
		}
		Entity entity = event.getDamager();
		Entity damaged = event.getEntity();
		if (entity instanceof Player && damaged instanceof LivingEntity) {
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (ItemUtils.hasEnchantment(item, RegisterEnchantments.SHOCK_ASPECT) && item.getType() != Material.BOOK) {
				int level = ItemUtils.getLevel(item, RegisterEnchantments.SHOCK_ASPECT);
				double chance = .05 + 0.1 * level;
				double random = Math.random();

				ShockAspectEvent shockAspect = new ShockAspectEvent((LivingEntity) damaged, player, event.getDamage(),
				event.getDamage(), chance, damaged.getLocation().clone());
				Bukkit.getPluginManager().callEvent(shockAspect);

				if (!shockAspect.isCancelled() && shockAspect.getChance() > random) {
					Location loc = shockAspect.getLocation();
					loc.getWorld().strikeLightning(loc);
					if (event.getEntityType() == EntityType.CREEPER) {
						AdvancementUtils.awardCriteria(player, ESAdvancement.SUPER_CHARGED, "lightning");
					}
				}
			}
		}
	}

	private void stoneThrow(EntityDamageByEntityEvent event) {
		if (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			if (!canRun(RegisterEnchantments.STONE_THROW, event)) {
				return;
			}
			switch (event.getEntityType()) {
				case BAT:
				case BLAZE:
				case ENDER_DRAGON:
				case GHAST:
				case PARROT:
				case PHANTOM:
				case VEX:
				case WITHER:
					LivingEntity entity = (LivingEntity) event.getEntity();
					if (event.getDamager() instanceof Arrow) {
						Arrow arrow = (Arrow) event.getDamager();
						ProjectileSource shooter = arrow.getShooter();
						if (shooter instanceof HumanEntity) {
							HumanEntity human = (HumanEntity) shooter;
							ItemStack item = human.getInventory().getItemInOffHand();
							if (item == null || !ItemUtils.hasEnchantment(item, RegisterEnchantments.STONE_THROW)) {
								item = human.getInventory().getItemInMainHand();
							}
							if (ItemUtils.hasEnchantment(item, RegisterEnchantments.STONE_THROW)) {
								int level = ItemUtils.getLevel(item, RegisterEnchantments.STONE_THROW);
								double percentage = .4 * level + 1.2;
								int extraDamage = (int) (percentage * event.getDamage() + .5);

								StoneThrowEvent stoneThrow = new StoneThrowEvent(entity, human, event.getDamage(),
								extraDamage);
								Bukkit.getPluginManager().callEvent(stoneThrow);
								if (!stoneThrow.isCancelled()) {
									event.setDamage(stoneThrow.getNewDamage());

									if (human instanceof Player && entity instanceof Phantom) {
										Phantom phantom = (Phantom) entity;
										if (phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() == phantom
										.getHealth() && phantom.getHealth() <= extraDamage) {
											AdvancementUtils.awardCriteria((Player) human,
											ESAdvancement.JUST_DIE_ALREADY, "phantom");
										}
									}
									if (human instanceof Player && entity instanceof EnderDragon) {
										AdvancementUtils.awardCriteria((Player) human, ESAdvancement.UNDERKILL,
										"dragon");
									}
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

	private void tank(PlayerItemDamageEvent event) {
		if (!canRun(RegisterEnchantments.TANK, event)) {
			return;
		}
		ItemStack item = event.getItem();
		if (ItemUtils.hasEnchantment(item, RegisterEnchantments.TANK)) {
			int level = ItemUtils.getLevel(item, RegisterEnchantments.TANK);
			double chance = level * 1.0D / (level + 1);
			int damage = event.getDamage();
			for(int i = 0; i < event.getDamage(); i++) {
				double random = Math.random();
				if (chance > random) {
					damage--;
				}
			}
			TankEvent tank = new TankEvent(event.getPlayer(), item, event.getDamage(), damage);
			Bukkit.getPluginManager().callEvent(tank);

			if (!tank.isCancelled()) {
				event.setDamage(tank.getNewDamage());
			}
		}
	}

	private void warp(EntityDamageByEntityEvent event) {
		if(!canRun(RegisterEnchantments.WARP, event)) {
			return;
		}
		if(event.getDamage() == 0) {
			return;
		}
		Entity attacked = event.getEntity();
		if(attacked instanceof LivingEntity){
			LivingEntity player = (LivingEntity) attacked;
			ItemStack leggings = player.getEquipment().getLeggings();
			if(leggings == null) {
				return;
			}
			if(ItemUtils.hasEnchantment(leggings, RegisterEnchantments.WARP)){
				int level = ItemUtils.getLevel(leggings, RegisterEnchantments.WARP);
				List<Location> locsToTp = new ArrayList<Location>();
				for(int x = - (level + 4); x <= level + 5; x++){
					for(int y = - (level + 4); y <= level + 5; y++){
						if(y < 1) {
							continue;
						}
						for(int z = - (level + 4); z <= level + 5; z++){
							ChatUtils.sendInfo(x + " " + y + " " + z);
							if(x == y && x == 0 && z == 0){
								continue;
							}
							Location tp = new Location(player.getWorld(), player.getLocation().getBlockX() + x, player.getLocation().getBlockY() + y,
							player.getLocation().getBlockZ() + z);
							World playerWorld = player.getWorld();
							World tpWorld = tp.getWorld();
							int blockX = tp.getBlockX();
							int blockY = tp.getBlockY();
							int blockZ = tp.getBlockZ();
							if(!playerWorld.getBlockAt(new Location(tpWorld, blockX, blockY + 1, blockZ)).getType().isSolid()
							&& !player.getWorld().getBlockAt(new Location(tpWorld, blockX, blockY, blockZ)).getType().isSolid()
							&& player.getWorld().getBlockAt(new Location(tpWorld, blockX, blockY - 1, blockZ)).getType().isSolid()){
								locsToTp.add(tp);
							}

						}
					}
				}
				double chance = .25;
				double random = Math.random();
				ChatUtils.sendInfo(chance + " " + random + " " + locsToTp.size());
				if(chance > random && locsToTp.size() > 0){
					int randomLoc = (int) (Math.random() * locsToTp.size());
					Location toTeleport = locsToTp.get(randomLoc);

					WarpEvent warp = new WarpEvent(player, player.getLocation(), toTeleport, locsToTp);
					Bukkit.getPluginManager().callEvent(warp);

					if(!warp.isCancelled()) {
						World world = warp.getTo().getWorld();
						world.spawnParticle(Particle.PORTAL, warp.getTo(), 50, 0.2, 2, 0.2);
						world.spawnParticle(Particle.PORTAL, warp.getFrom(), 50, 0.2, 2, 0.2);
						world.playSound(warp.getTo(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

						warp.getTo().setYaw(warp.getEntity().getLocation().getYaw());
						warp.getTo().setPitch(warp.getEntity().getLocation().getPitch());
						warp.getEntity().teleport(warp.getTo());
						if(warp.getEntity() instanceof Player && event.getDamager() instanceof Enderman) {
							AdvancementUtils.awardCriteria((Player) warp.getEntity(), ESAdvancement.IM_YOU_BUT_SHORTER, "enderpearl");
						}
					}
				}
			}
		}
	}

	private void applyDrowned(int level, HumanEntity human, LivingEntity entity, EntityDamageByEntityEvent event) {
		int ticks = (1 + level * 3) * 20;
		double damage = event.getDamage();
		if (!(entity instanceof WaterMob) && !(entity instanceof Drowned) && !(entity instanceof Guardian)) {
			DrownedEvent drowned = new DrownedEvent(entity, human, damage, damage, true, ticks);
			Bukkit.getPluginManager().callEvent(drowned);

			if (!drowned.isCancelled()) {
				if (drowned.willApplyEffect()) {
					EnchantmentSolution.addDrowned(new DrownedEntity((LivingEntity) drowned.getEntity(),
					(HumanEntity) drowned.getDamager(), drowned.getTicks()));
				}
				event.setDamage(drowned.getNewDamage());
			}
		}
	}

	private void magicGuard(EntityDamageEvent event) {
		if (!canRun(RegisterEnchantments.MAGIC_GUARD, event)) {
			return;
		}
		if (event.getCause() == DamageCause.MAGIC || event.getCause() == DamageCause.POISON) {
			if (event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				ItemStack shield = player.getInventory().getItemInOffHand();
				if (shield.getType().equals(Material.SHIELD)) {
					if (ItemUtils.hasEnchantment(shield, RegisterEnchantments.MAGIC_GUARD)) {
						MagicGuardPotionEvent magicGuard = new MagicGuardPotionEvent(player,
						event.getCause() == DamageCause.POISON ? PotionEffectType.POISON
						: PotionEffectType.HARM);
						Bukkit.getPluginManager().callEvent(magicGuard);

						if (!magicGuard.isCancelled()) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
