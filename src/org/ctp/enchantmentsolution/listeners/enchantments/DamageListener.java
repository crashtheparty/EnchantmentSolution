package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.*;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.ctp.crashapi.CrashAPI;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.damage.*;
import org.ctp.enchantmentsolution.events.entity.DetonateCreeperEvent;
import org.ctp.enchantmentsolution.events.modify.LagEvent;
import org.ctp.enchantmentsolution.events.modify.PushbackEvent;
import org.ctp.enchantmentsolution.events.potion.MagicGuardPotionEvent;
import org.ctp.enchantmentsolution.events.potion.PotionAfflictEvent;
import org.ctp.enchantmentsolution.events.teleport.WarpEntityEvent;
import org.ctp.enchantmentsolution.events.teleport.WarpPlayerEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.mcmmo.McMMOHandler;
import org.ctp.enchantmentsolution.nms.AnimalMobNMS;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.abilityhelpers.DrownedEntity;
import org.ctp.enchantmentsolution.utils.abilityhelpers.EntityAccuracy;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ParticleEffect;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

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
		runMethod(this, "detonator", event, EntityDamageByEntityEvent.class);
		runMethod(this, "drowned", event, EntityDamageByEntityEvent.class);
		runMethod(this, "gungHo", event, EntityDamageByEntityEvent.class);
		runMethod(this, "hollowPoint", event, EntityDamageByEntityEvent.class);
		runMethod(this, "irenesLasso", event, EntityDamageByEntityEvent.class);
		runMethod(this, "knockUp", event, EntityDamageByEntityEvent.class);
		runMethod(this, "pacified", event, EntityDamageByEntityEvent.class);
		runMethod(this, "pushback", event, EntityDamageByEntityEvent.class);
		runMethod(this, "sandVeil", event, EntityDamageByEntityEvent.class);
		runMethod(this, "shockAspect", event, EntityDamageByEntityEvent.class);
		runMethod(this, "streak", event, EntityDamageByEntityEvent.class);
		runMethod(this, "stoneThrow", event, EntityDamageByEntityEvent.class);
		runMethod(this, "warp", event, EntityDamageByEntityEvent.class);
		runMethod(this, "potions", event, EntityDamageByEntityEvent.class);
		runMethod(this, "ironDefense", event, EntityDamageByEntityEvent.class);
		runMethod(this, "lifeDrain", event, EntityDamageByEntityEvent.class);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		runMethod(this, "magicGuard", event, EntityDamageEvent.class);
		runMethod(this, "magmaWalker", event, EntityDamageEvent.class);
	}

	@EventHandler
	public void onPlayerItemDamage(PlayerItemDamageEvent event) {
		runMethod(this, "tank", event, PlayerItemDamageEvent.class);
	}

	private void brine(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.BRINE, event)) return;
		Entity entity = event.getDamager();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (isDisabled(player, RegisterEnchantments.BRINE)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.BRINE)) {
				Entity damaged = event.getEntity();
				if (item != null && damaged instanceof LivingEntity) {
					LivingEntity living = (LivingEntity) damaged;
					AttributeInstance a = living.getAttribute(Attribute.GENERIC_MAX_HEALTH);
					double maxHealth = a.getValue();
					double health = living.getHealth();
					if (health <= maxHealth / 2) {
						BrineEvent brine = new BrineEvent(living, player, event.getDamage(), event.getDamage() * 2);
						Bukkit.getPluginManager().callEvent(brine);

						if (!brine.isCancelled()) event.setDamage(brine.getNewDamage());
					}
				}
			}
		}
	}

	private void curseOfLag(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.CURSE_OF_LAG, event)) return;
		Entity entity = event.getDamager();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (isDisabled(player, RegisterEnchantments.CURSE_OF_LAG)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_LAG)) {
				LagEvent lag = new LagEvent(player, player.getLocation(), AbilityUtils.createEffects(player));

				Bukkit.getPluginManager().callEvent(lag);
				if (!lag.isCancelled() && lag.getEffects().size() > 0) {
					Location loc = lag.getLocation();
					for(ParticleEffect effect: lag.getEffects())
						loc.getWorld().spawnParticle(effect.getParticle(), loc, effect.getNum(), effect.getVarX(), effect.getVarY(), effect.getVarZ());
					if (lag.getSound() != null) loc.getWorld().playSound(loc, lag.getSound(), lag.getVolume(), lag.getPitch());
					AdvancementUtils.awardCriteria(player, ESAdvancement.LAAAGGGGGG, "lag");
				}
			}
		}
	}

	private void detonator(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.DETONATOR, event)) return;
		Entity damager = event.getDamager();
		Entity entity = event.getEntity();
		if (damager instanceof Projectile && damager.hasMetadata("detonator") && entity instanceof Creeper) {
			Creeper creeper = (Creeper) entity;
			int level = damager.getMetadata("detonator").get(0).asInt();
			Entity e = null;
			if (((Projectile) damager).getShooter() instanceof Entity) e = (Entity) ((Projectile) damager).getShooter();
			DetonateCreeperEvent detonator = new DetonateCreeperEvent(creeper, level, creeper.getMaxFuseTicks() - (level - 1) * 5, e);

			if (!detonator.isCancelled()) {
				if (((Projectile) damager).getShooter() instanceof Player) {
					Player player = (Player) ((Projectile) damager).getShooter();
					if (creeper.getMaxFuseTicks() > detonator.getDetonateTicks()) AdvancementUtils.awardCriteria(player, ESAdvancement.BLAST_OFF, "creeper");
				}
				creeper.setMaxFuseTicks(detonator.getDetonateTicks());
				creeper.ignite();
			}

		}
	}

	private void drowned(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.DROWNED, event)) return;
		if (event.getDamager() instanceof Trident) {
			Trident trident = (Trident) event.getDamager();
			if (trident.hasMetadata("drowned")) {
				HumanEntity human = (HumanEntity) trident.getShooter();
				List<MetadataValue> values = trident.getMetadata("drowned");
				for(MetadataValue value: values)
					if (value.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
						int level = value.asInt();
						if (event.getEntity() instanceof LivingEntity) applyDrowned(level, human, (LivingEntity) event.getEntity(), event);
					}
			}
		} else if (event.getDamager() instanceof HumanEntity) {
			HumanEntity human = (HumanEntity) event.getDamager();
			if (human instanceof Player && isDisabled((Player) human, RegisterEnchantments.DROWNED)) return;
			ItemStack item = human.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.DROWNED)) {
				int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.DROWNED);
				if (event.getEntity() instanceof LivingEntity) applyDrowned(level, human, (LivingEntity) event.getEntity(), event);
			}
		}
	}

	private void gungHo(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.GUNG_HO, event)) return;
		Entity damager = event.getDamager();
		Player player = null;
		if (damager instanceof Player) player = (Player) damager;
		if (player != null) {
			if (isDisabled(player, RegisterEnchantments.GUNG_HO)) return;
			ItemStack item = player.getInventory().getChestplate();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.GUNG_HO)) {
				Entity damaged = event.getEntity();
				if (damaged instanceof LivingEntity) {
					GungHoEvent gungHo = new GungHoEvent((LivingEntity) damaged, player, event.getDamage(), event.getDamage() * 3);
					Bukkit.getPluginManager().callEvent(gungHo);
					if (!gungHo.isCancelled()) event.setDamage(gungHo.getNewDamage());
				}
			}
		}
	}

	private void hollowPoint(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.HOLLOW_POINT, event)) return;
		Entity damager = event.getDamager();
		Entity entity = event.getEntity();
		if (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof LivingEntity && entity instanceof LivingEntity && damager.hasMetadata("hollow_point")) {
			LivingEntity livingEntity = (LivingEntity) entity;
			LivingEntity living = (LivingEntity) ((Projectile) damager).getShooter();
			if (living instanceof Player && isDisabled((Player) living, RegisterEnchantments.HOLLOW_POINT)) return;
			for(ItemStack i: livingEntity.getEquipment().getArmorContents()) {
				if (i == null) continue;
				String[] split = i.getType().name().split("_");
				String s = split[split.length - 1];
				if (s.endsWith("BOOTS") || s.endsWith("CHESTPLATE") || s.endsWith("HELMET") || s.endsWith("LEGGINGS") || s.endsWith("ELYTRA")) {
					HollowPointEvent hollowPoint = new HollowPointEvent(livingEntity, living, event.getDamage(), event.getDamage() * 1.25);
					Bukkit.getPluginManager().callEvent(hollowPoint);

					if (!hollowPoint.isCancelled()) event.setDamage(hollowPoint.getNewDamage());

					break;
				}
			}
		}
	}

	private void irenesLasso(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.IRENES_LASSO, event)) return;
		Entity attacker = event.getDamager();
		Entity attacked = event.getEntity();
		if (attacker instanceof Player && (attacked instanceof Animals || attacked instanceof WaterMob)) {
			Player player = (Player) attacker;
			if (isDisabled(player, RegisterEnchantments.IRENES_LASSO)) return;
			Creature animals = (Creature) attacked;
			ItemStack attackItem = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(attackItem, RegisterEnchantments.IRENES_LASSO)) {
				if (!AnimalMobNMS.canAddMob()) return;
				event.setCancelled(true);
				int max = EnchantmentUtils.getLevel(attackItem, RegisterEnchantments.IRENES_LASSO);
				int current = 0;
				boolean animalRemove = false;
				for(AnimalMob animal: EnchantmentSolution.getAnimals()) {
					if (animal == null) {
						Chatable.get().sendWarning("An animal is null for some reason: this animal will be removed.");
						animalRemove = true;
					}
					if (animal.inItem(attackItem)) current++;
				}
				if (current >= max) return;
				LassoDamageEvent lasso = new LassoDamageEvent(animals, max, player);
				Bukkit.getPluginManager().callEvent(lasso);
				if (!lasso.isCancelled()) {
					if (attacked instanceof Tameable) {
						if (((Tameable) attacked).getOwner() != null && !((Tameable) attacked).getOwner().getUniqueId().equals(player.getUniqueId())) {
							Chatable.get().sendMessage(player, Chatable.get().getMessage(ChatUtils.getCodes(), "irenes_lasso.error"));
							return;
						}
						String type = attacked.getType().name().toLowerCase(Locale.ROOT);
						AdvancementUtils.awardCriteria(player, ESAdvancement.THORGY, type);
						AdvancementUtils.awardCriteria(player, ESAdvancement.FREE_PETS, type);
					}
					McMMOHandler.customName(attacked);
					EnchantmentSolution.addAnimals(AnimalMobNMS.getMob(animals, attackItem));
					attacked.remove();
				}
				if (animalRemove) {
					Iterator<AnimalMob> mobs = EnchantmentSolution.getAnimals().iterator();
					while (mobs.hasNext()) {
						AnimalMob mob = mobs.next();
						if (mob == null) mobs.remove();
					}
				}
			}
		}
	}

	private void ironDefense(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.IRON_DEFENSE, event)) return;
		if (!ESArrays.getContactCauses().contains(event.getCause())) return;
		Entity attacked = event.getEntity();
		Entity attacker = event.getDamager();
		if (attacker instanceof AreaEffectCloud) return;
		if (attacked instanceof HumanEntity) {
			HumanEntity player = (HumanEntity) attacked;
			if (player instanceof Player && isDisabled((Player) player, RegisterEnchantments.IRON_DEFENSE)) return;
			ItemStack shield = player.getEquipment().getItemInOffHand();
			if (shield == null) return;
			if (player.isBlocking()) return;
			if (EnchantmentUtils.hasEnchantment(shield, RegisterEnchantments.IRON_DEFENSE)) {
				int level = EnchantmentUtils.getLevel(shield, RegisterEnchantments.IRON_DEFENSE);
				double percentage = .1 + .05 * level;
				double originalDamage = event.getDamage();
				double damage = originalDamage * percentage;
				int shieldDamage = (int) damage;
				if (shieldDamage < damage) shieldDamage += 1;
				damage = originalDamage - damage;

				IronDefenseEvent ironDefense = new IronDefenseEvent(player, level, originalDamage, damage, shield, shieldDamage);
				Bukkit.getPluginManager().callEvent(ironDefense);

				if (!ironDefense.isCancelled()) {
					event.setDamage(ironDefense.getNewDamage());
					DamageUtils.damageItem(player, ironDefense.getShield(), ironDefense.getShieldDamage());

					if (player instanceof Player) {
						if ((int) (ironDefense.getNewDamage() * 10) > 0 && CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 1) ((Player) player).incrementStatistic(Statistic.DAMAGE_BLOCKED_BY_SHIELD, (int) (ironDefense.getNewDamage() * 10));
						if (player.getHealth() <= ironDefense.getDamage() && player.getHealth() > ironDefense.getNewDamage()) AdvancementUtils.awardCriteria((Player) player, ESAdvancement.IRON_MAN, "blocked");
					}
				}
			}
		}
	}

	private void knockUp(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.KNOCKUP, event)) return;
		Entity attacker = event.getDamager();
		Entity attacked = event.getEntity();
		if (event.getCause() == DamageCause.ENTITY_SWEEP_ATTACK) return; // knockback doesn't use sweep attacks, so neither should knockup
		if (attacker instanceof Player && attacked instanceof LivingEntity) {
			Player player = (Player) attacker;
			if (isDisabled(player, RegisterEnchantments.KNOCKUP)) return;
			ItemStack attackItem = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(attackItem, RegisterEnchantments.KNOCKUP)) {
				int level = EnchantmentUtils.getLevel(attackItem, RegisterEnchantments.KNOCKUP);
				double levelMultiplier = 0.18;
				KnockUpEvent knockUp = new KnockUpEvent((LivingEntity) attacked, level, player, event.getDamage(), 0.275184010449 + levelMultiplier * level);
				Bukkit.getPluginManager().callEvent(knockUp);
				if (!knockUp.isCancelled()) {
					event.setDamage(knockUp.getNewDamage());
					Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), (Runnable) () -> {
						double knockup = knockUp.getKnockUp();
						if (attacked.isDead()) knockup /= 1.5;
						attacked.setVelocity(new Vector(attacked.getVelocity().getX(), knockup, attacked.getVelocity().getZ()));
					}, 0l);
				}
			}
		}
	}

	private void lifeDrain(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.LIFE_DRAIN, event)) return;
		Entity attacked = event.getEntity();
		Entity attacker = event.getDamager();
		if (attacked instanceof LivingEntity && attacker instanceof LivingEntity) if (attacker instanceof HumanEntity) {
			HumanEntity human = (HumanEntity) attacker;
			if (human instanceof Player && isDisabled((Player) human, RegisterEnchantments.LIFE_DRAIN)) return;
			ItemStack item = human.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.LIFE_DRAIN)) {
				double damage = event.getDamage() / 2;
				int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.LIFE_DRAIN);
				double healthBack = damage * 0.10 * level;
				LifeDrainEvent lifeDrain = new LifeDrainEvent((LivingEntity) attacked, level, human, event.getDamage(), damage, healthBack);
				Bukkit.getPluginManager().callEvent(lifeDrain);

				if (!lifeDrain.isCancelled()) {
					event.setDamage(lifeDrain.getNewDamage());
					healthBack = lifeDrain.getHealthBack();
					if (human instanceof Player && human.getHealth() < human.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) AdvancementUtils.awardCriteria((Player) human, ESAdvancement.REPLENISHED, "life");
					if (healthBack + human.getHealth() > human.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) human.setHealth(human.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					else
						human.setHealth(healthBack + human.getHealth());
				}
			}
		}
	}

	private void magicGuard(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.MAGIC_GUARD, event)) return;
		if (event.getDamager() instanceof AreaEffectCloud) if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (isDisabled(player, RegisterEnchantments.MAGIC_GUARD)) return;
			ItemStack shield = player.getInventory().getItemInOffHand();
			if (shield.getType().equals(Material.SHIELD)) if (EnchantmentUtils.hasEnchantment(shield, RegisterEnchantments.MAGIC_GUARD)) {
				MagicGuardPotionEvent magicGuard = new MagicGuardPotionEvent(player, ((AreaEffectCloud) event.getDamager()).getBasePotionData().getType().getEffectType());
				Bukkit.getPluginManager().callEvent(magicGuard);

				if (!magicGuard.isCancelled()) event.setCancelled(true);
			}
		}
	}

	private void potions(EntityDamageByEntityEvent event) {
		try {
			potion(event, RegisterEnchantments.BLINDNESS, PotionEffectType.BLINDNESS);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			potion(event, RegisterEnchantments.VENOM, PotionEffectType.POISON);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			potion(event, RegisterEnchantments.TRUANT, PotionEffectType.SLOW);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			potion(event, RegisterEnchantments.WITHERING, PotionEffectType.WITHER);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void potion(EntityDamageByEntityEvent event, Enchantment enchantment, PotionEffectType type) {
		if (!canRun(enchantment, event)) return;
		Entity attacker = event.getDamager();
		Entity attacked = event.getEntity();
		if (attacker instanceof HumanEntity && attacked instanceof LivingEntity) {
			HumanEntity human = (HumanEntity) attacker;
			if (human instanceof Player && isDisabled((Player) human, enchantment)) return;
			LivingEntity living = (LivingEntity) attacked;
			ItemStack item = human.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, enchantment)) {
				int level = EnchantmentUtils.getLevel(item, enchantment);
				int ticks = 8 * 20;
				PotionEffect previousEffect = null;
				boolean override = false;
				for(PotionEffect activeEffect: living.getActivePotionEffects())
					if (activeEffect.getType() == type) {
						override = true;
						previousEffect = activeEffect;
						break;
					}

				PotionAfflictEvent potionAfflict = new PotionAfflictEvent(living, human, new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchantment), level), type, ticks, level - 1, previousEffect, override);
				Bukkit.getPluginManager().callEvent(potionAfflict);

				if (!potionAfflict.isCancelled()) {
					living.addPotionEffect(new PotionEffect(potionAfflict.getType(), potionAfflict.getDuration(), potionAfflict.getLevel()));
					if (human instanceof Player && living instanceof Bat && potionAfflict.getType() == PotionEffectType.BLINDNESS) AdvancementUtils.awardCriteria((Player) human, ESAdvancement.BLIND_AS_A_BAT, "blindness");
					if (human instanceof Player && living instanceof Skeleton && !(living instanceof WitherSkeleton) && potionAfflict.getType() == PotionEffectType.WITHER) AdvancementUtils.awardCriteria((Player) human, ESAdvancement.SPOOKY_SCARY_SKELETON, "skeleton_skull");
					if (human instanceof Player && potionAfflict.getType() == PotionEffectType.POISON && human != living) AdvancementUtils.awardCriteria((Player) human, ESAdvancement.SPIDER_SENSES, "venom");
					if (living instanceof Player && potionAfflict.getType() == PotionEffectType.SLOW) AdvancementUtils.awardCriteria((Player) living, ESAdvancement.SLACKIN, "truant");
				}
			}
		} else if (attacker instanceof Projectile) {
			Projectile projectile = (Projectile) attacker;
			if (projectile.hasMetadata(enchantment.getKey().getKey())) {
				int ticks = 8 * 20;
				int level = projectile.getMetadata(enchantment.getKey().getKey()).get(0).asInt();
				if (attacked instanceof LivingEntity) {
					LivingEntity living = (LivingEntity) attacked;
					PotionEffect previousEffect = null;
					boolean override = false;
					for(PotionEffect activeEffect: living.getActivePotionEffects())
						if (activeEffect.getType() == type) {
							override = true;
							previousEffect = activeEffect;
							break;
						}

					PotionAfflictEvent potionAfflict = new PotionAfflictEvent(living, (LivingEntity) projectile.getShooter(), new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchantment), level), type, ticks, level - 1, previousEffect, override);
					Bukkit.getPluginManager().callEvent(potionAfflict);
					ProjectileSource shooter = projectile.getShooter();

					if (!potionAfflict.isCancelled()) {
						living.addPotionEffect(new PotionEffect(potionAfflict.getType(), potionAfflict.getDuration(), potionAfflict.getLevel()));
						if (shooter instanceof Player && living instanceof Bat && potionAfflict.getType() == PotionEffectType.BLINDNESS) AdvancementUtils.awardCriteria((Player) shooter, ESAdvancement.BLIND_AS_A_BAT, "blindness");
						if (shooter instanceof Player && living instanceof Skeleton && !(living instanceof WitherSkeleton) && potionAfflict.getType() == PotionEffectType.WITHER) AdvancementUtils.awardCriteria((Player) shooter, ESAdvancement.SPOOKY_SCARY_SKELETON, "skeleton_skull");
						if (shooter instanceof Player && potionAfflict.getType() == PotionEffectType.POISON && shooter != living) AdvancementUtils.awardCriteria((Player) shooter, ESAdvancement.SPIDER_SENSES, "venom");
						if (living instanceof Player && potionAfflict.getType() == PotionEffectType.SLOW) AdvancementUtils.awardCriteria((Player) living, ESAdvancement.SLACKIN, "truant");
					}
				}
			}
		}
	}

	private void pacified(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.PACIFIED, event)) return;
		Entity entity = event.getDamager();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (isDisabled(player, RegisterEnchantments.PACIFIED)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.PACIFIED)) {
				int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.PACIFIED);
				Entity damaged = event.getEntity();
				if (damaged instanceof Tameable && ((Tameable) damaged).getOwner().getUniqueId().equals(player.getUniqueId())) {
					LivingEntity living = (LivingEntity) damaged;
					double damage = event.getDamage();
					double newDamage = Math.max(damage * (1 - .15 * level), 0);
					PacifiedEvent pacified = new PacifiedEvent(living, player, level, damage, newDamage);
					Bukkit.getPluginManager().callEvent(pacified);

					if (!pacified.isCancelled()) {
						event.setDamage(pacified.getNewDamage());
						if (damage > living.getHealth() && living.getHealth() > pacified.getNewDamage()) AdvancementUtils.awardCriteria(player, ESAdvancement.SAVING_GRACE, "animal");
					}
				}
			}
		}
	}

	private void pushback(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.PUSHBACK, event)) return;

		Entity damaged = event.getEntity();
		Entity damager = event.getDamager();
		if (damaged instanceof Player && damager instanceof LivingEntity) {
			Player player = (Player) damaged;
			if (isDisabled(player, RegisterEnchantments.PUSHBACK)) return;
			LivingEntity living = (LivingEntity) damager;
			ItemStack item = player.getInventory().getItemInOffHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.PUSHBACK) && player.isBlocking()) {
				int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.PUSHBACK);
				double d0 = Math.sin(player.getLocation().getYaw() * 0.017453292F);
				double d1 = -Math.cos(player.getLocation().getYaw() * 0.017453292F);
				Vector v0 = player.getVelocity();
				Vector v1 = new Vector(d0, 0.0D, d1);
				double d2 = Math.sqrt(v1.getX() * v1.getX() + v1.getY() * v1.getY() + v1.getZ() * v1.getZ());
				if (d2 < 1.0E-4D) v1 = new Vector(0, 0, 0);
				else
					v1 = new Vector(v1.getX() / d2 * level * 0.5F, v1.getY() / d2 * level * 0.5F, v1.getZ() / d2 * level * 0.5F);

				PushbackEvent pushback = new PushbackEvent(player, level, v0, new Vector(v0.getX() / 2.0D - v1.getX(), living.isOnGround() ? Math.min(0.4D, v0.getY() / 2.0D + level * 0.5F) : v0.getY(), v0.getZ() / 2.0D - v1.getZ()), living);
				Bukkit.getPluginManager().callEvent(pushback);

				if (!pushback.isCancelled()) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.KNOCKBACK_REVERSED, "knockback");
					living.setVelocity(pushback.getNewVector());
				}

			}
		}
	}

	private void sandVeil(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.SAND_VEIL, event)) return;
		Entity damager = event.getDamager();
		if (damager instanceof Projectile) if (((Projectile) damager).getShooter() instanceof Entity) damager = (Entity) ((Projectile) damager).getShooter();
		else
			return;
		if (damager == null) return;
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

			SandVeilMissEvent sandVeil = new SandVeilMissEvent((LivingEntity) event.getEntity(), ea.getLevel(), (LivingEntity) damager, event.getDamage(), true);
			Bukkit.getPluginManager().callEvent(sandVeil);

			if (!sandVeil.isCancelled() && accuracy <= random) {
				event.setCancelled(true);
				if (sandVeil.isParticles()) event.getEntity().getWorld().spawnParticle(Particle.CLOUD, event.getEntity().getLocation(), 200, 0.2, 0.2, 0.2);
				AdvancementUtils.awardCriteria(ea.getAttacker(), ESAdvancement.MISSED, "sand", 1);
			}
		}
		if (!event.isCancelled()) if (damager instanceof Player && event.getEntity() instanceof LivingEntity) {
			Player player = (Player) damager;
			if (isDisabled(player, RegisterEnchantments.SAND_VEIL)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.SAND_VEIL)) {
				int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.SAND_VEIL);
				double accuracy = 1 - level * .05;
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

				if (ea != null && accuracy > ea.getAccuracy()) accuracy = ea.getAccuracy();

				SandVeilEvent sandVeil = new SandVeilEvent(entity, level, player, event.getDamage(), event.getDamage(), 160, accuracy);
				Bukkit.getPluginManager().callEvent(sandVeil);

				if (!sandVeil.isCancelled()) if (ea != null) {
					ea.setAccuracy(sandVeil.getAccuracy());
					ea.setTicks(ea.getTicks());
					ea.setLevel(level);
				} else
					EnchantmentSolution.addAccuracy(new EntityAccuracy(player, entity, sandVeil.getAccuracy(), sandVeil.getTicks(), level));
			}
		}
	}

	private void shockAspect(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.SHOCK_ASPECT, event)) return;
		if (event.getDamage() == 0) return;
		Entity entity = event.getDamager();
		Entity damaged = event.getEntity();
		if (entity instanceof Player && damaged instanceof LivingEntity) {
			Player player = (Player) entity;
			if (isDisabled(player, RegisterEnchantments.SHOCK_ASPECT)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.SHOCK_ASPECT) && item.getType() != Material.BOOK) {
				int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.SHOCK_ASPECT);
				double chance = .05 + 0.1 * level;
				double random = Math.random();

				ShockAspectEvent shockAspect = new ShockAspectEvent((LivingEntity) damaged, level, player, event.getDamage(), event.getDamage(), chance, damaged.getLocation().clone());
				Bukkit.getPluginManager().callEvent(shockAspect);

				if (!shockAspect.isCancelled() && shockAspect.getChance() > random) {
					Location loc = shockAspect.getLocation();
					loc.getWorld().strikeLightning(loc);
					if (event.getEntityType() == EntityType.CREEPER) AdvancementUtils.awardCriteria(player, ESAdvancement.SUPER_CHARGED, "lightning");
				}
			}
		}
	}

	private void stoneThrow(EntityDamageByEntityEvent event) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			if (!canRun(RegisterEnchantments.STONE_THROW, event)) return;
			switch (event.getEntityType().name()) {
				case "BAT":
				case "BEE":
				case "BLAZE":
				case "ENDER_DRAGON":
				case "GHAST":
				case "PARROT":
				case "PHANTOM":
				case "VEX":
				case "WITHER":
					LivingEntity entity = (LivingEntity) event.getEntity();
					if (event.getDamager() instanceof Arrow) {
						Arrow arrow = (Arrow) event.getDamager();
						ProjectileSource shooter = arrow.getShooter();
						if (shooter instanceof HumanEntity) {
							HumanEntity human = (HumanEntity) shooter;
							if (human instanceof Player && isDisabled((Player) human, RegisterEnchantments.STONE_THROW)) return;
							ItemStack item = human.getInventory().getItemInOffHand();
							if (item == null || !EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.STONE_THROW)) item = human.getInventory().getItemInMainHand();
							if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.STONE_THROW)) {
								int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.STONE_THROW);
								double percentage = .4 * level + 1.2;
								int extraDamage = (int) (percentage * event.getDamage() + .5);

								StoneThrowEvent stoneThrow = new StoneThrowEvent(entity, level, human, event.getDamage(), extraDamage);
								Bukkit.getPluginManager().callEvent(stoneThrow);
								if (!stoneThrow.isCancelled()) {
									event.setDamage(stoneThrow.getNewDamage());

									if (human instanceof Player && entity instanceof Phantom) {
										Phantom phantom = (Phantom) entity;
										if (phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() == phantom.getHealth() && phantom.getHealth() <= extraDamage) AdvancementUtils.awardCriteria((Player) human, ESAdvancement.JUST_DIE_ALREADY, "phantom");
									}
									if (human instanceof Player && entity instanceof EnderDragon) AdvancementUtils.awardCriteria((Player) human, ESAdvancement.UNDERKILL, "dragon");
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

	private void streak(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.STREAK, event)) return;
		Entity entity = event.getDamager();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (isDisabled(player, RegisterEnchantments.STREAK)) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.STREAK)) {
				Entity damaged = event.getEntity();
				ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
				if (damaged instanceof LivingEntity) {
					LivingEntity living = (LivingEntity) damaged;
					int num = esPlayer.getStreak();
					double damage = event.getDamage();
					double newDamage = damage * (1 + 0.01 * num);
					StreakEvent streak = new StreakEvent(living, player, damage, newDamage);
					Bukkit.getPluginManager().callEvent(streak);

					if (!streak.isCancelled()) event.setDamage(streak.getNewDamage());
				}
			}
		}
	}

	private void tank(PlayerItemDamageEvent event) {
		if (!canRun(RegisterEnchantments.TANK, event) || isDisabled(event.getPlayer(), RegisterEnchantments.TANK)) return;
		ItemStack item = event.getItem();
		if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.TANK)) {
			int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.TANK);
			double chance = level * 1.0D / (level + 1);
			int damage = event.getDamage();
			for(int i = 0; i < event.getDamage(); i++) {
				double random = Math.random();
				if (chance > random) damage--;
			}
			TankEvent tank = new TankEvent(event.getPlayer(), level, item, event.getDamage(), damage);
			Bukkit.getPluginManager().callEvent(tank);

			if (!tank.isCancelled()) event.setDamage(tank.getNewDamage());
		}
	}

	private void warp(EntityDamageByEntityEvent event) {
		if (!canRun(RegisterEnchantments.WARP, event)) return;
		if (event.getDamage() == 0) return;
		Entity attacked = event.getEntity();
		if (attacked instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) attacked;
			if (entity instanceof Player && isDisabled((Player) entity, RegisterEnchantments.TELEPATHY)) return;
			ItemStack leggings = entity.getEquipment().getLeggings();
			if (leggings == null) return;
			if (EnchantmentUtils.hasEnchantment(leggings, RegisterEnchantments.WARP)) {
				int level = EnchantmentUtils.getLevel(leggings, RegisterEnchantments.WARP);
				List<Location> locsToTp = new ArrayList<Location>();
				for(int x = -(level + 4); x <= level + 5; x++)
					for(int y = -(level + 4); y <= level + 5; y++) {
						if (y < 1) continue;
						for(int z = -(level + 4); z <= level + 5; z++) {
							if (x == y && x == 0 && z == 0) continue;
							Location tp = new Location(entity.getWorld(), entity.getLocation().getBlockX() + x, entity.getLocation().getBlockY() + y, entity.getLocation().getBlockZ() + z);
							World playerWorld = entity.getWorld();
							World tpWorld = tp.getWorld();
							int blockX = tp.getBlockX();
							int blockY = tp.getBlockY();
							int blockZ = tp.getBlockZ();
							if (!playerWorld.getBlockAt(new Location(tpWorld, blockX, blockY + 1, blockZ)).getType().isSolid() && !entity.getWorld().getBlockAt(new Location(tpWorld, blockX, blockY, blockZ)).getType().isSolid() && entity.getWorld().getBlockAt(new Location(tpWorld, blockX, blockY - 1, blockZ)).getType().isSolid()) locsToTp.add(tp);

						}
					}
				double chance = .25;
				double random = Math.random();
				if (chance > random && locsToTp.size() > 0) {
					int randomLoc = (int) (Math.random() * locsToTp.size());
					Location toTeleport = locsToTp.get(randomLoc);

					if (entity instanceof Player) {
						Player player = (Player) entity;
						WarpPlayerEvent warp = new WarpPlayerEvent(player, player.getLocation(), toTeleport, locsToTp, level);
						Bukkit.getPluginManager().callEvent(warp);

						if (!warp.isCancelled()) {
							World world = warp.getTo().getWorld();
							world.spawnParticle(Particle.PORTAL, warp.getTo(), 50, 0.2, 2, 0.2);
							world.spawnParticle(Particle.PORTAL, warp.getFrom(), 50, 0.2, 2, 0.2);
							world.playSound(warp.getTo(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

							warp.getTo().setYaw(warp.getPlayer().getLocation().getYaw());
							warp.getTo().setPitch(warp.getPlayer().getLocation().getPitch());
							warp.getPlayer().teleport(warp.getTo());
							if (event.getDamager() instanceof Enderman) AdvancementUtils.awardCriteria(warp.getPlayer(), ESAdvancement.IM_YOU_BUT_SHORTER, "enderpearl");
						}
					} else {
						WarpEntityEvent warp = new WarpEntityEvent(entity, entity.getLocation(), toTeleport, locsToTp, level);
						Bukkit.getPluginManager().callEvent(warp);

						if (!warp.isCancelled()) {
							World world = warp.getTo().getWorld();
							world.spawnParticle(Particle.PORTAL, warp.getTo(), 50, 0.2, 2, 0.2);
							world.spawnParticle(Particle.PORTAL, warp.getFrom(), 50, 0.2, 2, 0.2);
							world.playSound(warp.getTo(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

							warp.getTo().setYaw(warp.getEntity().getLocation().getYaw());
							warp.getTo().setPitch(warp.getEntity().getLocation().getPitch());
							warp.getEntity().teleport(warp.getTo());
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
			DrownedEvent drowned = new DrownedEvent(entity, level, human, damage, damage, true, ticks);
			Bukkit.getPluginManager().callEvent(drowned);

			if (!drowned.isCancelled()) {
				if (drowned.willApplyEffect()) EnchantmentSolution.addDrowned(new DrownedEntity((LivingEntity) drowned.getEntity(), (HumanEntity) drowned.getDamager(), drowned.getTicks(), level));
				event.setDamage(drowned.getNewDamage());
			}
		}
	}

	private void magicGuard(EntityDamageEvent event) {
		if (!canRun(RegisterEnchantments.MAGIC_GUARD, event)) return;
		if (event.getCause() == DamageCause.MAGIC || event.getCause() == DamageCause.POISON) if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (isDisabled(player, RegisterEnchantments.MAGIC_GUARD)) return;
			ItemStack shield = player.getInventory().getItemInOffHand();
			if (shield.getType().equals(Material.SHIELD)) if (EnchantmentUtils.hasEnchantment(shield, RegisterEnchantments.MAGIC_GUARD)) {
				MagicGuardPotionEvent magicGuard = new MagicGuardPotionEvent(player, event.getCause() == DamageCause.POISON ? PotionEffectType.POISON : PotionEffectType.HARM);
				Bukkit.getPluginManager().callEvent(magicGuard);

				if (!magicGuard.isCancelled()) event.setCancelled(true);
			}
		}
	}

	private void magmaWalker(EntityDamageEvent event) {
		if (!canRun(RegisterEnchantments.MAGMA_WALKER, event)) return;
		if (event.getCause() == DamageCause.HOT_FLOOR && event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (isDisabled(player, RegisterEnchantments.MAGMA_WALKER)) return;
			ItemStack boots = player.getInventory().getBoots();
			if (EnchantmentUtils.hasEnchantment(boots, RegisterEnchantments.MAGMA_WALKER)) event.setCancelled(true);
		}
	}
}
