package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.UUID;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.ctp.crashapi.nms.DamageEvent;
import org.ctp.crashapi.utils.LocationUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.api.ApiEnchantList;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.HollowPointDamageEvent;
import org.ctp.enchantmentsolution.events.entity.DetonatorExplosionEvent;
import org.ctp.enchantmentsolution.events.modify.HardBounceEvent;
import org.ctp.enchantmentsolution.events.modify.LagEvent;
import org.ctp.enchantmentsolution.events.modify.SniperLaunchEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ParticleEffect;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

@SuppressWarnings("unused")
public class ProjectileListener extends Enchantmentable {

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		runMethod(this, "curseOfLag", event, ProjectileLaunchEvent.class);
		runMethod(this, "detonator", event, ProjectileLaunchEvent.class);
		runMethod(this, "drowned", event, ProjectileLaunchEvent.class);
		runMethod(this, "hollowPoint", event, ProjectileLaunchEvent.class);
		runMethod(this, "potions", event, ProjectileLaunchEvent.class);
		runMethod(this, "sniper", event, ProjectileLaunchEvent.class);
		runMethod(this, "transmutation", event, ProjectileLaunchEvent.class);
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		runMethod(this, "detonator", event, ProjectileHitEvent.class);
		runMethod(this, "hardBounce", event, ProjectileHitEvent.class);
		runMethod(this, "hollowPoint", event, ProjectileHitEvent.class);
		runMethod(this, "overkill", event, ProjectileHitEvent.class);
		runMethod(this, "splatterFest", event, ProjectileHitEvent.class);
	}

	@EventHandler
	public void onPlayerEggThrow(PlayerEggThrowEvent event) {
		runMethod(this, "splatterFest", event, PlayerEggThrowEvent.class);
	}

	private void curseOfLag(ProjectileLaunchEvent event) {
		if (!canRun(RegisterEnchantments.CURSE_OF_LAG, event)) return;
		Projectile proj = event.getEntity();
		if (proj.getShooter() instanceof Player) {
			Player player = (Player) proj.getShooter();
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

	private void detonator(ProjectileLaunchEvent event) {
		if (!canRun(RegisterEnchantments.DETONATOR, event)) return;

		HumanEntity entity = null;
		if (event.getEntity().getShooter() instanceof HumanEntity) {
			entity = (HumanEntity) event.getEntity().getShooter();
			if (entity instanceof Player && isDisabled((Player) entity, RegisterEnchantments.DETONATOR)) return;
			ItemStack item = entity.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.DETONATOR)) event.getEntity().setMetadata("detonator", new FixedMetadataValue(EnchantmentSolution.getPlugin(), EnchantmentUtils.getLevel(item, RegisterEnchantments.DETONATOR)));
		}
	}

	private void drowned(ProjectileLaunchEvent event) {
		if (!canRun(RegisterEnchantments.DROWNED, event)) return;
		Projectile proj = event.getEntity();
		if (proj instanceof Trident) {
			Trident trident = (Trident) proj;
			if (trident.getShooter() instanceof Player) {
				Player player = (Player) trident.getShooter();
				if (isDisabled(player, RegisterEnchantments.DROWNED)) return;
				ItemStack tridentItem = player.getInventory().getItemInMainHand();
				if (EnchantmentUtils.hasEnchantment(tridentItem, RegisterEnchantments.DROWNED)) trident.setMetadata("drowned", new FixedMetadataValue(EnchantmentSolution.getPlugin(), EnchantmentUtils.getLevel(tridentItem, RegisterEnchantments.DROWNED)));
			}
		}
	}

	private void hollowPoint(ProjectileLaunchEvent event) {
		if (!canRun(RegisterEnchantments.HOLLOW_POINT, event)) return;

		HumanEntity entity = null;
		if (event.getEntity().getShooter() instanceof HumanEntity) {
			entity = (HumanEntity) event.getEntity().getShooter();
			if (entity instanceof Player && isDisabled((Player) entity, RegisterEnchantments.HOLLOW_POINT)) return;
			if (EnchantmentUtils.hasEnchantment(entity.getInventory().getItemInMainHand(), RegisterEnchantments.HOLLOW_POINT)) event.getEntity().setMetadata("hollow_point", new FixedMetadataValue(EnchantmentSolution.getPlugin(), 1));
		}
	}

	private void sniper(ProjectileLaunchEvent event) {
		if (!canRun(RegisterEnchantments.SNIPER, event)) return;
		Projectile proj = event.getEntity();
		if (proj instanceof Arrow) {
			Arrow arrow = (Arrow) proj;
			if (arrow.getShooter() instanceof Player) {
				Player player = (Player) arrow.getShooter();
				if (isDisabled(player, RegisterEnchantments.SNIPER)) return;
				ItemStack bow = player.getInventory().getItemInMainHand();
				if (EnchantmentUtils.hasEnchantment(bow, RegisterEnchantments.SNIPER)) {
					int level = EnchantmentUtils.getLevel(bow, RegisterEnchantments.SNIPER);
					double speed = 1 + 0.1 * level * level;

					SniperLaunchEvent sniper = new SniperLaunchEvent(player, level, speed);
					Bukkit.getPluginManager().callEvent(sniper);

					if (!sniper.isCancelled() && sniper.getSpeed() > 0) {
						arrow.setMetadata("sniper", new FixedMetadataValue(EnchantmentSolution.getPlugin(), LocationUtils.locationToString(player.getLocation())));
						Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), (Runnable) () -> arrow.setVelocity(arrow.getVelocity().multiply(sniper.getSpeed())), 0l);
					}
				}
			}
		}
	}

	private void transmutation(ProjectileLaunchEvent event) {
		if (!canRun(RegisterEnchantments.TRANSMUTATION, event)) return;
		Projectile proj = event.getEntity();
		if (proj instanceof Trident) {
			Trident trident = (Trident) proj;
			if (trident.getShooter() instanceof Player) {
				Player player = (Player) trident.getShooter();
				if (isDisabled(player, RegisterEnchantments.TRANSMUTATION)) return;
				ItemStack tridentItem = player.getInventory().getItemInMainHand();
				if (EnchantmentUtils.hasEnchantment(tridentItem, RegisterEnchantments.TRANSMUTATION)) trident.setMetadata("transmutation", new FixedMetadataValue(EnchantmentSolution.getPlugin(), 1));
			}
		}
	}

	private void potions(ProjectileLaunchEvent event) {
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

	private void potion(ProjectileLaunchEvent event, Enchantment enchantment, PotionEffectType type) {
		if (!canRun(enchantment, event)) return;
		Projectile proj = event.getEntity();
		if (proj instanceof Trident) {
			Trident trident = (Trident) proj;
			if (trident.getShooter() instanceof HumanEntity) {
				HumanEntity player = (HumanEntity) trident.getShooter();
				if (player instanceof Player && isDisabled((Player) player, enchantment)) return;
				ItemStack tridentItem = player.getInventory().getItemInMainHand();
				if (tridentItem != null && tridentItem.getType() == Material.TRIDENT && EnchantmentUtils.hasEnchantment(tridentItem, enchantment)) proj.setMetadata(enchantment.getKey().getKey(), new FixedMetadataValue(EnchantmentSolution.getPlugin(), ApiEnchantList.getLevel(tridentItem, enchantment)));
			}
		} else if (proj instanceof Arrow) {
			Arrow arrow = (Arrow) proj;
			if (arrow.getShooter() instanceof HumanEntity) {
				HumanEntity entity = (HumanEntity) arrow.getShooter();
				if (entity instanceof Player && isDisabled((Player) entity, enchantment)) return;
				ItemStack bow = entity.getInventory().getItemInMainHand();
				if (EnchantmentUtils.hasEnchantment(bow, enchantment)) proj.setMetadata(enchantment.getKey().getKey(), new FixedMetadataValue(EnchantmentSolution.getPlugin(), ApiEnchantList.getLevel(bow, enchantment)));
			}
		}
	}

	private void detonator(ProjectileHitEvent event) {
		if (!canRun(RegisterEnchantments.DETONATOR, event)) return;
		if (event.getEntity().getShooter() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) event.getEntity().getShooter();
			if (entity instanceof Player && isDisabled((Player) entity, RegisterEnchantments.DETONATOR)) return;
			if (event.getHitEntity() != null && event.getHitEntity().getType() == EntityType.CREEPER) return;
			if (event.getEntity().hasMetadata("detonator")) {
				Location loc = event.getEntity().getLocation();
				int level = event.getEntity().getMetadata("detonator").get(0).asInt();

				Entity e = null;
				if (event.getEntity().getShooter() instanceof Entity) e = (Entity) event.getEntity().getShooter();
				DetonatorExplosionEvent detonator = new DetonatorExplosionEvent(entity, level, loc, e, (float) (0.5 + 0.5 * level), true, true, false);
				Bukkit.getPluginManager().callEvent(detonator);

				if (!detonator.isCancelled()) if (detonator.willDelayExplosion()) Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
					handleDetonatorExplosion(event, detonator, loc);
				}, 1l);
				else
					handleDetonatorExplosion(event, detonator, loc);
			}
		}
	}

	private void handleDetonatorExplosion(ProjectileHitEvent event, DetonatorExplosionEvent detonator, Location loc) {
		boolean explode = loc.getWorld().createExplosion(detonator.getLoc(), detonator.getSize(), detonator.willSetFire(), detonator.willSetBlocks());
		if (event.getEntity() != null) event.getEntity().remove();
		if (explode && detonator.getEntity() instanceof Player) {
			Player player = (Player) detonator.getEntity();
			AdvancementUtils.awardCriteria(player, ESAdvancement.CARPET_BOMBS, "explosion", 1);
		}
	}

	private void hardBounce(ProjectileHitEvent event) {
		if (!canRun(RegisterEnchantments.HARD_BOUNCE, event)) return;
		Entity e = event.getHitEntity();
		Projectile p = event.getEntity();
		if (e != null && e instanceof Player) {
			Player player = (Player) e;
			if (isDisabled(player, RegisterEnchantments.HARD_BOUNCE)) return;
			if (player.isBlocking()) {
				ItemStack shield = player.getInventory().getItemInMainHand();
				if (shield.getType() != Material.SHIELD) shield = player.getInventory().getItemInOffHand();
				if (EnchantmentUtils.hasEnchantment(shield, RegisterEnchantments.HARD_BOUNCE)) {
					int level = EnchantmentUtils.getLevel(shield, RegisterEnchantments.HARD_BOUNCE);

					HardBounceEvent hardBounce = new HardBounceEvent(player, level, 2 + 2 * level);
					Bukkit.getPluginManager().callEvent(hardBounce);

					if (!hardBounce.isCancelled()) {
						p.setMetadata("deflection", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
						Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), (Runnable) () -> {
							Vector v = p.getVelocity().clone();
							if (v.getY() < 0) v.setY(-v.getY());
							else if (v.getY() == 0) v.setY(0.1);
							v.multiply(hardBounce.getSpeed());
							p.setVelocity(v);
						}, 1l);
					}
				}
			}
		}
	}

	private void hollowPoint(ProjectileHitEvent event) {
		if (!canRun(RegisterEnchantments.HOLLOW_POINT, event)) return;
		if (event.getHitEntity() != null && event.getEntity().hasMetadata("hollow_point") && event.getEntity() instanceof Arrow && event.getEntity().getShooter() instanceof LivingEntity) {
			Arrow arrow = (Arrow) event.getEntity();
			LivingEntity entity = (LivingEntity) arrow.getShooter();
			if (entity instanceof Player) {
				Player player = (Player) entity;
				if (isDisabled(player, RegisterEnchantments.HOLLOW_POINT)) return;
				if (event.getHitEntity().getType() == EntityType.ENDERMAN || event.getHitEntity().getType() == EntityType.WITHER && ((Wither) event.getHitEntity()).getHealth() <= ((Wither) event.getHitEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2) {
					double damage = DamageEvent.getArrowDamage(entity, arrow);
					HollowPointDamageEvent hollowPoint = new HollowPointDamageEvent(event.getEntity(), event.getHitEntity(), DamageCause.PROJECTILE, damage);
					Bukkit.getPluginManager().callEvent(hollowPoint);
					if (!hollowPoint.isCancelled()) {
						DamageEvent.damageEntity((LivingEntity) hollowPoint.getEntity(), player, "arrow", (float) hollowPoint.getDamage());
						event.getEntity().remove();
						AdvancementUtils.awardCriteria(player, ESAdvancement.PENETRATION, "arrow");
					}
				}
			}
		}
	}

	private void overkill(ProjectileHitEvent event) {
		if (!canRun(RegisterEnchantments.OVERKILL, event)) return;
		if (event.getEntity() instanceof Arrow && event.getEntity().hasMetadata("no_pickup")) {
			Arrow arrow = (Arrow) event.getEntity();
			arrow.setPickupStatus(PickupStatus.DISALLOWED);
		}
	}

	private void splatterFest(ProjectileHitEvent event) {
		if (!canRun(RegisterEnchantments.SPLATTER_FEST, event)) return;
		if (event.getHitEntity() != null && event.getEntityType() == EntityType.EGG) {
			Projectile entity = event.getEntity();
			if (entity.hasMetadata("splatter_fest")) for(MetadataValue meta: entity.getMetadata("splatter_fest")) {
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(meta.asString()));
				if (offlinePlayer != null) if (event.getHitEntity() instanceof Player) {
					Player player = (Player) event.getHitEntity();
					if (player.getUniqueId().toString().equals(offlinePlayer.getUniqueId().toString())) AdvancementUtils.awardCriteria(player, ESAdvancement.EGGED_BY_MYSELF, "egg");
				} else if (event.getHitEntity().getType() == EntityType.CHICKEN) if (offlinePlayer.getPlayer() != null) AdvancementUtils.awardCriteria(offlinePlayer.getPlayer(), ESAdvancement.CHICKEN_OR_THE_EGG, "egg");
			}
		}
	}

	private void splatterFest(PlayerEggThrowEvent event) {
		if (!canRun(RegisterEnchantments.SPLATTER_FEST, event)) return;
		if (event.getEgg().hasMetadata("hatch_egg")) event.setHatching(false);
	}
}
