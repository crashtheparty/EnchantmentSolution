package org.ctp.enchantmentsolution.rpg.listener;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.ctp.crashapi.nms.ItemNMS;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.enums.VanillaEnchantment;
import org.ctp.enchantmentsolution.events.AttributeEvent;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;
import org.ctp.enchantmentsolution.events.blocks.*;
import org.ctp.enchantmentsolution.events.damage.*;
import org.ctp.enchantmentsolution.events.entity.*;
import org.ctp.enchantmentsolution.events.interact.ProjectileSpawnEvent;
import org.ctp.enchantmentsolution.events.player.BonusDropsEvent;
import org.ctp.enchantmentsolution.events.player.IcarusRefreshEvent;
import org.ctp.enchantmentsolution.events.potion.MagicGuardPotionEvent;
import org.ctp.enchantmentsolution.events.potion.PotionAfflictEvent;
import org.ctp.enchantmentsolution.events.potion.PotionEffectEvent;
import org.ctp.enchantmentsolution.events.soul.SoulReaperEvent;
import org.ctp.enchantmentsolution.events.teleport.WarpPlayerEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class RPGListener extends Enchantmentable implements Runnable {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (EnchantmentUtils.hasEnchantment(item, Enchantment.SILK_TOUCH) && ItemBreakType.getType(item.getType()) != null && ItemBreakType.getType(item.getType()).getSilkBreakTypes().contains(event.getBlock().getType())) giveExperience(player, Enchantment.SILK_TOUCH, EnchantmentUtils.getLevel(item, Enchantment.SILK_TOUCH));
		if (EnchantmentUtils.hasEnchantment(item, Enchantment.DIG_SPEED) && ItemBreakType.getType(item.getType()) != null && ItemBreakType.getType(item.getType()).getBreakTypes().contains(event.getBlock().getType())) giveExperience(player, Enchantment.DIG_SPEED, EnchantmentUtils.getLevel(item, Enchantment.DIG_SPEED));
		if (EnchantmentUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS) && ItemBreakType.getType(item.getType()) != null && ItemBreakType.getType(item.getType()).getFortuneBreakTypes().contains(event.getBlock().getType())) giveExperience(player, Enchantment.LOOT_BONUS_BLOCKS, EnchantmentUtils.getLevel(item, Enchantment.LOOT_BONUS_BLOCKS));
		ItemStack helmet = player.getInventory().getHelmet();
		if (EnchantmentUtils.hasEnchantment(helmet, Enchantment.WATER_WORKER) && player.getEyeLocation().getBlock().getType() == Material.WATER) giveExperience(player, Enchantment.WATER_WORKER, EnchantmentUtils.getLevel(helmet, Enchantment.WATER_WORKER));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockChange(BlockFormEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		if (event.getNewState().getType() == Material.FROSTED_ICE) {
			Collection<Entity> entities = event.getBlock().getWorld().getNearbyEntities(event.getBlock().getLocation(), 8, 2, 8);
			List<Player> players = new ArrayList<Player>();
			for(Entity entity: entities)
				if (entity instanceof Player) {
					Player player = (Player) entity;
					ItemStack boots = player.getInventory().getBoots();
					if (EnchantmentUtils.hasEnchantment(boots, Enchantment.FROST_WALKER)) players.add(player);
				}
			if (players.size() == 1) {
				Player player = players.get(0);
				ItemStack boots = player.getInventory().getBoots();
				giveExperience(player, Enchantment.FROST_WALKER, EnchantmentUtils.getLevel(boots, Enchantment.FROST_WALKER));
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		ProjectileSource entity = event.getEntity().getShooter();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, Enchantment.ARROW_INFINITE)) giveExperience(player, Enchantment.ARROW_INFINITE, EnchantmentUtils.getLevel(item, Enchantment.ARROW_INFINITE));
			if (EnchantmentUtils.hasEnchantment(item, Enchantment.QUICK_CHARGE)) giveExperience(player, Enchantment.QUICK_CHARGE, EnchantmentUtils.getLevel(item, Enchantment.QUICK_CHARGE));
			if (item != null && item.getType() == Material.TRIDENT && EnchantmentUtils.hasEnchantment(item, Enchantment.LOYALTY)) giveExperience(player, Enchantment.LOYALTY, EnchantmentUtils.getLevel(item, Enchantment.LOYALTY));
			if (EnchantmentUtils.hasEnchantment(item, VanillaEnchantment.MULTISHOT.getEnchantment())) giveExperience(player, VanillaEnchantment.MULTISHOT.getEnchantment(), EnchantmentUtils.getLevel(item, VanillaEnchantment.MULTISHOT.getEnchantment()));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerFish(PlayerFishEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		Player player = event.getPlayer();
		if (event.getState() == State.CAUGHT_FISH) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, Enchantment.LUCK)) giveExperience(player, Enchantment.LUCK, EnchantmentUtils.getLevel(item, Enchantment.LUCK));
			if (EnchantmentUtils.hasEnchantment(item, Enchantment.LURE)) giveExperience(player, Enchantment.LURE, EnchantmentUtils.getLevel(item, Enchantment.LURE));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		Player player = event.getPlayer();
		ItemStack item = event.getPlayer().getInventory().getBoots();
		if (player.getLocation().getBlock().getType() == Material.WATER && EnchantmentUtils.hasEnchantment(item, Enchantment.DEPTH_STRIDER)) {
			Location locTo = event.getTo();
			Location locFrom = event.getFrom();
			if (locTo.getX() != locFrom.getX() || locTo.getZ() != locFrom.getZ()) giveExperience(player, Enchantment.DEPTH_STRIDER, EnchantmentUtils.getLevel(item, Enchantment.DEPTH_STRIDER));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerItemDamage(PlayerItemDamageEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (EnchantmentUtils.hasEnchantment(item, Enchantment.DURABILITY)) giveExperience(player, Enchantment.DURABILITY, EnchantmentUtils.getLevel(item, Enchantment.DURABILITY));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDeath(EntityDeathEvent event) {
		if (!RPGUtils.isEnabled()) return;
		Entity killer = event.getEntity().getKiller();
		if (killer instanceof Player) {
			Player player = (Player) killer;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_MOBS)) giveExperience(player, Enchantment.LOOT_BONUS_MOBS, EnchantmentUtils.getLevel(item, Enchantment.LOOT_BONUS_MOBS));
			for(ItemStack i: player.getInventory().getArmorContents())
				if (EnchantmentUtils.hasEnchantment(i, RegisterEnchantments.UNREST)) {
					giveExperience(player, RegisterEnchantments.UNREST, EnchantmentUtils.getLevel(i, RegisterEnchantments.UNREST));
					return;
				}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityAirChange(EntityAirChangeEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getHelmet();
			if (EnchantmentUtils.hasEnchantment(item, Enchantment.OXYGEN)) giveExperience(player, Enchantment.OXYGEN, EnchantmentUtils.getLevel(item, Enchantment.OXYGEN));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRiptide(PlayerRiptideEvent event) {
		if (!RPGUtils.isEnabled()) return;
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		giveExperience(player, Enchantment.RIPTIDE, EnchantmentUtils.getLevel(item, Enchantment.RIPTIDE));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerItemMend(PlayerItemMendEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		Player player = event.getPlayer();
		giveExperience(player, Enchantment.MENDING, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLightWeight(LightWeightEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		Player player = event.getPlayer();
		giveExperience(player, RegisterEnchantments.LIGHT_WEIGHT, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamage(EntityDamageEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (Arrays.asList(DamageCause.BLOCK_EXPLOSION, DamageCause.ENTITY_EXPLOSION).contains(event.getCause())) for(ItemStack item: player.getInventory().getArmorContents())
				if (EnchantmentUtils.hasEnchantment(item, Enchantment.PROTECTION_EXPLOSIONS)) giveExperience(player, Enchantment.PROTECTION_EXPLOSIONS, EnchantmentUtils.getLevel(item, Enchantment.PROTECTION_EXPLOSIONS));
			if (Arrays.asList(DamageCause.FIRE, DamageCause.FIRE_TICK, DamageCause.LAVA).contains(event.getCause())) for(ItemStack item: player.getInventory().getArmorContents())
				if (EnchantmentUtils.hasEnchantment(item, Enchantment.PROTECTION_FIRE)) giveExperience(player, Enchantment.PROTECTION_FIRE, EnchantmentUtils.getLevel(item, Enchantment.PROTECTION_FIRE));
			if (DamageCause.FALL == event.getCause()) {
				ItemStack item = player.getInventory().getBoots();
				if (EnchantmentUtils.hasEnchantment(item, Enchantment.PROTECTION_FALL)) giveExperience(player, Enchantment.PROTECTION_FALL, EnchantmentUtils.getLevel(item, Enchantment.PROTECTION_FALL));
			}
			if (DamageCause.PROJECTILE == event.getCause()) for(ItemStack item: player.getInventory().getArmorContents())
				if (EnchantmentUtils.hasEnchantment(item, Enchantment.PROTECTION_PROJECTILE)) giveExperience(player, Enchantment.PROTECTION_PROJECTILE, EnchantmentUtils.getLevel(item, Enchantment.PROTECTION_PROJECTILE));
			if (!Arrays.asList(DamageCause.CUSTOM, DamageCause.MAGIC, DamageCause.STARVATION, DamageCause.VOID, DamageCause.SUICIDE).contains(event.getCause())) for(ItemStack item: player.getInventory().getArmorContents()) {
				if (EnchantmentUtils.hasEnchantment(item, Enchantment.PROTECTION_ENVIRONMENTAL)) giveExperience(player, Enchantment.PROTECTION_ENVIRONMENTAL, EnchantmentUtils.getLevel(item, Enchantment.PROTECTION_ENVIRONMENTAL));
				if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.ARMORED)) giveExperience(player, RegisterEnchantments.ARMORED, EnchantmentUtils.getLevel(item, RegisterEnchantments.ARMORED));
				if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.LIFE)) giveExperience(player, RegisterEnchantments.LIFE, EnchantmentUtils.getLevel(item, RegisterEnchantments.LIFE));
				if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.TOUGHNESS)) giveExperience(player, RegisterEnchantments.TOUGHNESS, EnchantmentUtils.getLevel(item, RegisterEnchantments.TOUGHNESS));
			}
		}

		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
			Entity e2 = e.getDamager();
			if (e2 instanceof Player) {
				Player player = (Player) e2;
				ItemStack item = player.getInventory().getItemInMainHand();
				if (entity.getType().isAlive() && item != null) {
					if (Arrays.asList("SPIDER", "CAVE_SPIDER", "BEE", "SILVERFISH", "ENDERMITE").contains(entity.getType().name()) && EnchantmentUtils.hasEnchantment(item, Enchantment.DAMAGE_ARTHROPODS)) giveExperience(player, Enchantment.DAMAGE_ARTHROPODS, EnchantmentUtils.getLevel(item, Enchantment.DAMAGE_ARTHROPODS));
					if (Arrays.asList("SKELETON", "STRAY", "WITHER_SKELETON", "ZOMBIE", "DROWNED", "HUSK", "ZOMBIE_PIGMAN", "ZOMBIE_VILLAGER", "PHANTOM", "WITHER", "SKELETON_HORSE", "ZOMBIE_HORSE", "ZOGLIN").contains(entity.getType().name()) && EnchantmentUtils.hasEnchantment(item, Enchantment.DAMAGE_UNDEAD)) giveExperience(player, Enchantment.DAMAGE_UNDEAD, EnchantmentUtils.getLevel(item, Enchantment.DAMAGE_UNDEAD));
					if (EnchantmentUtils.hasEnchantment(item, Enchantment.DAMAGE_ALL)) giveExperience(player, Enchantment.DAMAGE_ALL, EnchantmentUtils.getLevel(item, Enchantment.DAMAGE_ALL));
					if (e.getCause() == DamageCause.ENTITY_SWEEP_ATTACK && EnchantmentUtils.hasEnchantment(item, Enchantment.SWEEPING_EDGE)) giveExperience(player, Enchantment.SWEEPING_EDGE, EnchantmentUtils.getLevel(item, Enchantment.SWEEPING_EDGE));
					if (EnchantmentUtils.hasEnchantment(item, Enchantment.KNOCKBACK)) giveExperience(player, Enchantment.KNOCKBACK, EnchantmentUtils.getLevel(item, Enchantment.KNOCKBACK));
					if (!Arrays.asList("BLAZE", "ZOMBIE_PIGMAN", "ZOMBIE_PIGLIN", "ZOGLIN", "WITHER_SKELETON", "STRIDER").contains(entity.getType().name()) && EnchantmentUtils.hasEnchantment(item, Enchantment.FIRE_ASPECT)) giveExperience(player, Enchantment.FIRE_ASPECT, EnchantmentUtils.getLevel(item, Enchantment.FIRE_ASPECT));
					if (Arrays.asList("DOLPHIN", "GUARDIAN", "ELDER_GUARDIAN", "SQUID", "TURTLE", "COD", "SALMON", "PUFFERFISH", "TROPICAL_FISH").contains(entity.getType().name()) && EnchantmentUtils.hasEnchantment(item, Enchantment.IMPALING)) giveExperience(player, Enchantment.IMPALING, EnchantmentUtils.getLevel(item, Enchantment.IMPALING));
					if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.QUICK_STRIKE)) giveExperience(player, RegisterEnchantments.QUICK_STRIKE, EnchantmentUtils.getLevel(item, RegisterEnchantments.QUICK_STRIKE));
				}
			}
			if (e2 instanceof Player && event.getCause() == DamageCause.THORNS) {
				Player player = (Player) e2;
				for(ItemStack item: player.getInventory().getArmorContents())
					if (EnchantmentUtils.hasEnchantment(item, Enchantment.THORNS)) giveExperience(player, Enchantment.THORNS, EnchantmentUtils.getLevel(item, Enchantment.THORNS));
			}
			if (e2 instanceof Projectile && ((Projectile) e2).getShooter() instanceof Player) {
				Player player = (Player) ((Projectile) e2).getShooter();
				ItemStack item = player.getInventory().getItemInMainHand();
				if (entity.getType().isAlive() && item != null) {
					if (!Arrays.asList("BLAZE", "ZOMBIE_PIGMAN", "ZOMBIE_PIGLIN", "ZOGLIN", "WITHER_SKELETON", "STRIDER").contains(entity.getType().name()) && EnchantmentUtils.hasEnchantment(item, Enchantment.ARROW_FIRE)) giveExperience(player, Enchantment.ARROW_FIRE, EnchantmentUtils.getLevel(item, Enchantment.ARROW_FIRE));
					if (EnchantmentUtils.hasEnchantment(item, Enchantment.ARROW_DAMAGE)) giveExperience(player, Enchantment.ARROW_DAMAGE, EnchantmentUtils.getLevel(item, Enchantment.ARROW_DAMAGE));
					if (EnchantmentUtils.hasEnchantment(item, Enchantment.ARROW_KNOCKBACK)) giveExperience(player, Enchantment.ARROW_KNOCKBACK, EnchantmentUtils.getLevel(item, Enchantment.ARROW_KNOCKBACK));
					if (EnchantmentUtils.hasEnchantment(item, VanillaEnchantment.PIERCING.getEnchantment())) giveExperience(player, VanillaEnchantment.PIERCING.getEnchantment(), EnchantmentUtils.getLevel(item, VanillaEnchantment.PIERCING.getEnchantment()));

					if (e2 instanceof Trident) {
						ItemStack trident = ItemNMS.getTrident((Trident) e2);
						if (trident.getType() == Material.TRIDENT && EnchantmentUtils.hasEnchantment(trident, Enchantment.CHANNELING) && entity.getLocation().getBlock().getLightFromSky() >= 15 && entity.getWorld().getThunderDuration() > 0) giveExperience(player, Enchantment.CHANNELING, EnchantmentUtils.getLevel(trident, Enchantment.CHANNELING));
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onESPlayerEvent(ESPlayerEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		EnchantmentLevel level = event.getEnchantment();
		if (level == null || level.getEnchant() == null) return; // something went wrong here
		if (level.getEnchant().getRelativeEnchantment() instanceof ApiEnchantmentWrapper) return; // other plugins need to make these events go themselves
		if (level.getEnchant().isCurse()) return; // don't let custom curse enchantments do anything
		if (event instanceof AttributeEvent || event instanceof BonusDropsEvent || event instanceof IcarusRefreshEvent) return; // these aren't the events we
		// should be looking for
		if (event instanceof SoulReaperEvent) {
			SoulReaperEvent soul = (SoulReaperEvent) event;
			giveExperience(soul.getKiller(), level);
			return;
		}
		if (event instanceof ProjectileSpawnEvent) {
			ProjectileSpawnEvent spawn = (ProjectileSpawnEvent) event;
			if (spawn.willCancel()) return;
		}
		giveExperience(event.getPlayer(), level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onESBlockPlaceEvent(ESBlockPlaceEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		EnchantmentLevel level = event.getEnchantment();
		if (level.getEnchant().getRelativeEnchantment() instanceof ApiEnchantmentWrapper) return; // other plugins need to make these events go themselves
		if (level.getEnchant().isCurse()) return; // don't let custom curse enchantments do anything
		giveExperience(event.getPlayer(), level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onESBlockBreakEvent(ESBlockBreakEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		EnchantmentLevel level = event.getEnchantment();
		if (level.getEnchant().getRelativeEnchantment() instanceof ApiEnchantmentWrapper) return; // other plugins need to make these events go themselves
		if (level.getEnchant().isCurse()) return; // don't let custom curse enchantments do anything
		giveExperience(event.getPlayer(), level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMultiBlock(MultiBlockEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		EnchantmentLevel level = event.getEnchantment();
		if (level.getEnchant().getRelativeEnchantment() instanceof ApiEnchantmentWrapper) return; // other plugins need to make these events go themselves
		if (level.getEnchant().isCurse()) return; // don't let custom curse enchantments do anything
		if (event instanceof HeightWidthDepthEvent) {
			giveExperience(event.getPlayer(), ((HeightWidthDepthEvent) event).getEnchantmentWidth());
			giveExperience(event.getPlayer(), ((HeightWidthDepthEvent) event).getEnchantmentDepth());
		}
		giveExperience(event.getPlayer(), level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onWarpPlayer(WarpPlayerEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		EnchantmentLevel level = event.getEnchantment();
		giveExperience(event.getPlayer(), level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDetonateCreeper(DetonateCreeperEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		EnchantmentLevel level = event.getEnchantment();
		Entity entity = event.getDetonator();
		if (entity instanceof Player) giveExperience((Player) entity, level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDetonatorExplosion(DetonatorExplosionEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		EnchantmentLevel level = event.getEnchantment();
		Entity entity = event.getDetonator();
		if (entity instanceof Player) giveExperience((Player) entity, level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onESEntityDamageEntity(ESDamageEntityEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		EnchantmentLevel level = event.getEnchantment();
		if (level.getEnchant().getRelativeEnchantment() instanceof ApiEnchantmentWrapper) return; // other plugins need to make these events go themselves
		if (level.getEnchant().isCurse()) return; // don't let custom curse enchantments do anything

		Player player = null;

		if ((event instanceof StoneThrowEvent || event instanceof SandVeilEvent || event instanceof SacrificeEvent || event instanceof LassoDamageEvent || event instanceof KnockUpEvent || event instanceof ShockAspectEvent || event instanceof DrownedEvent || event instanceof BrineEvent || event instanceof GungHoEvent) && ((ESEntityDamageEntityEvent) event).getDamager() instanceof Player) player = (Player) ((ESEntityDamageEntityEvent) event).getDamager();
		else if (event instanceof IronDefenseEvent && event.getEntity() instanceof Player) player = (Player) event.getEntity();
		if (player != null) giveExperience(player, level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPotionEffect(PotionEffectEvent event) {
		if (!RPGUtils.isEnabled() || event.isCancelled()) return;
		EnchantmentLevel level = event.getEnchantment();
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (level.getEnchant().getRelativeEnchantment() instanceof ApiEnchantmentWrapper) return; // other plugins need to make these events go themselves
			if (level.getEnchant().isCurse()) return; // don't let custom curse enchantments do anything
			if (event instanceof MagicGuardPotionEvent) giveExperience(player, level);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPotionAfflict(PotionAfflictEvent event) {
		if (event.getAfflicter() instanceof Player) giveExperience((Player) event.getAfflicter(), event.getEnchantment());
	}

	@Override
	public void run() {
		if (!RPGUtils.isEnabled()) return;
		for(Player player: Bukkit.getOnlinePlayers()) {
			ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
			if (esPlayer.didTick()) {
				ItemStack item = esPlayer.getElytra();
				if (item != null) giveExperience(player, RegisterEnchantments.FREQUENT_FLYER, EnchantmentUtils.getLevel(item, RegisterEnchantments.FREQUENT_FLYER));
			}
			boolean wb = false, ur = false;
			int jog = 0, plyo = 0;
			ItemStack jogItem = null, plyoItem = null;
			for(ItemStack item: esPlayer.getArmor()) {
				if (!ur && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.NO_REST) && player.getWorld().getEnvironment() == Environment.NORMAL && player.getWorld().getTime() > 12540 && player.getWorld().getTime() < 23459) {
					giveExperience(player, RegisterEnchantments.NO_REST, EnchantmentUtils.getLevel(item, RegisterEnchantments.NO_REST));
					ur = true;
				}
				if (!wb && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.WATER_BREATHING) && esPlayer.getOnlinePlayer().getEyeLocation().getBlock().isLiquid()) {
					giveExperience(player, RegisterEnchantments.WATER_BREATHING, EnchantmentUtils.getLevel(item, RegisterEnchantments.WATER_BREATHING));
					wb = true;
				}
				if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.JOGGERS)) {
					int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.JOGGERS);
					if (level > jog) {
						jogItem = item;
						jog = level;
					}
				}
				if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.PLYOMETRICS)) {
					int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.PLYOMETRICS);
					if (level > plyo) {
						plyoItem = item;
						plyo = level;
					}
				}
			}
			if (jogItem != null) giveExperience(player, RegisterEnchantments.JOGGERS, jog);
			if (plyoItem != null) giveExperience(player, RegisterEnchantments.PLYOMETRICS, plyo);
		}
	}

	private void giveExperience(Player player, Enchantment enchant, int level) {
		if (level <= 0) return;
		EnchantmentLevel enchLevel = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchant), level);
		RPGUtils.giveExperience(player, enchLevel);
	}

	private void giveExperience(Player player, EnchantmentLevel level) {
		if (level.getLevel() <= 0) return;
		RPGUtils.giveExperience(player, level);
	}

}
