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
import org.ctp.enchantmentsolution.events.potion.PotionEffectEvent;
import org.ctp.enchantmentsolution.events.soul.SoulReaperEvent;
import org.ctp.enchantmentsolution.events.teleport.WarpPlayerEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.nms.ItemNMS;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.threads.ElytraRunnable;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class RPGListener extends Enchantmentable implements Runnable {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null && ItemUtils.hasEnchantment(item, Enchantment.SILK_TOUCH) && ItemBreakType.getType(item.getType()).getSilkBreakTypes().contains(event.getBlock().getType())) giveExperience(player, Enchantment.SILK_TOUCH, ItemUtils.getLevel(item, Enchantment.SILK_TOUCH));
		if (item != null && ItemUtils.hasEnchantment(item, Enchantment.DIG_SPEED) && ItemBreakType.getType(item.getType()).getBreakTypes().contains(event.getBlock().getType())) giveExperience(player, Enchantment.DIG_SPEED, ItemUtils.getLevel(item, Enchantment.DIG_SPEED));
		if (item != null && ItemUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_BLOCKS) && ItemBreakType.getType(item.getType()).getFortuneBreakTypes().contains(event.getBlock().getType())) giveExperience(player, Enchantment.LOOT_BONUS_BLOCKS, ItemUtils.getLevel(item, Enchantment.LOOT_BONUS_BLOCKS));
		ItemStack helmet = player.getInventory().getHelmet();
		if (helmet != null && ItemUtils.hasEnchantment(helmet, Enchantment.WATER_WORKER) && player.getEyeLocation().getBlock().getType() == Material.WATER) giveExperience(player, Enchantment.WATER_WORKER, ItemUtils.getLevel(helmet, Enchantment.WATER_WORKER));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockChange(BlockFormEvent event) {
		if (event.getNewState().getType() == Material.FROSTED_ICE) {
			Collection<Entity> entities = event.getBlock().getWorld().getNearbyEntities(event.getBlock().getLocation(), 8, 2, 8);
			List<Player> players = new ArrayList<Player>();
			for(Entity entity: entities)
				if (entity instanceof Player) {
					Player player = (Player) entity;
					ItemStack boots = player.getInventory().getBoots();
					if (boots != null && ItemUtils.hasEnchantment(boots, Enchantment.FROST_WALKER)) players.add(player);
				}
			if (players.size() == 1) {
				Player player = players.get(0);
				ItemStack boots = player.getInventory().getBoots();
				giveExperience(player, Enchantment.FROST_WALKER, ItemUtils.getLevel(boots, Enchantment.FROST_WALKER));
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		if (event.isCancelled()) return;
		ProjectileSource entity = event.getEntity().getShooter();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item != null && ItemUtils.hasEnchantment(item, Enchantment.ARROW_INFINITE)) giveExperience(player, Enchantment.ARROW_INFINITE, ItemUtils.getLevel(item, Enchantment.ARROW_INFINITE));
			if (item != null && ItemUtils.hasEnchantment(item, Enchantment.QUICK_CHARGE)) giveExperience(player, Enchantment.QUICK_CHARGE, ItemUtils.getLevel(item, Enchantment.QUICK_CHARGE));
			if (item != null && item.getType() == Material.TRIDENT && ItemUtils.hasEnchantment(item, Enchantment.LOYALTY)) giveExperience(player, Enchantment.LOYALTY, ItemUtils.getLevel(item, Enchantment.LOYALTY));
			if (item != null && ItemUtils.hasEnchantment(item, VanillaEnchantment.MULTISHOT.getEnchantment())) giveExperience(player, VanillaEnchantment.MULTISHOT.getEnchantment(), ItemUtils.getLevel(item, VanillaEnchantment.MULTISHOT.getEnchantment()));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerFish(PlayerFishEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		if (event.getState() == State.CAUGHT_FISH) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item != null && ItemUtils.hasEnchantment(item, Enchantment.LUCK)) giveExperience(player, Enchantment.LUCK, ItemUtils.getLevel(item, Enchantment.LUCK));
			if (item != null && ItemUtils.hasEnchantment(item, Enchantment.LURE)) giveExperience(player, Enchantment.LURE, ItemUtils.getLevel(item, Enchantment.LURE));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		ItemStack item = event.getPlayer().getInventory().getBoots();
		if (player.getLocation().getBlock().getType() == Material.WATER && item != null && ItemUtils.hasEnchantment(item, Enchantment.DEPTH_STRIDER)) {
			Location locTo = event.getTo();
			Location locFrom = event.getFrom();
			if (locTo.getX() != locFrom.getX() || locTo.getZ() != locFrom.getZ()) giveExperience(player, Enchantment.DEPTH_STRIDER, ItemUtils.getLevel(item, Enchantment.DEPTH_STRIDER));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerItemDamage(PlayerItemDamageEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null && ItemUtils.hasEnchantment(item, Enchantment.DURABILITY)) giveExperience(player, Enchantment.DURABILITY, ItemUtils.getLevel(item, Enchantment.DURABILITY));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) return;
		Entity killer = event.getEntity().getKiller();
		if (killer instanceof Player) {
			Player player = (Player) killer;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item != null && ItemUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_MOBS)) giveExperience(player, Enchantment.LOOT_BONUS_MOBS, ItemUtils.getLevel(item, Enchantment.LOOT_BONUS_MOBS));
			for(ItemStack i: player.getInventory().getArmorContents())
				if (i != null && ItemUtils.hasEnchantment(i, RegisterEnchantments.UNREST)) {
					giveExperience(player, RegisterEnchantments.UNREST, ItemUtils.getLevel(i, RegisterEnchantments.UNREST));
					return;
				}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityAirChange(EntityAirChangeEvent event) {
		if (event.isCancelled()) return;
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack item = player.getInventory().getHelmet();
			if (item != null && ItemUtils.hasEnchantment(item, Enchantment.OXYGEN)) giveExperience(player, Enchantment.OXYGEN, ItemUtils.getLevel(item, Enchantment.OXYGEN));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRiptide(PlayerRiptideEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		giveExperience(player, Enchantment.RIPTIDE, ItemUtils.getLevel(item, Enchantment.RIPTIDE));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerItemMend(PlayerItemMendEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		giveExperience(player, Enchantment.MENDING, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLightWeight(LightWeightEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		giveExperience(player, RegisterEnchantments.LIGHT_WEIGHT, 1);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled()) return;
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (Arrays.asList(DamageCause.BLOCK_EXPLOSION, DamageCause.ENTITY_EXPLOSION).contains(event.getCause())) for(ItemStack item: player.getInventory().getArmorContents())
				if (item != null && ItemUtils.hasEnchantment(item, Enchantment.PROTECTION_EXPLOSIONS)) giveExperience(player, Enchantment.PROTECTION_EXPLOSIONS, ItemUtils.getLevel(item, Enchantment.PROTECTION_EXPLOSIONS));
			if (Arrays.asList(DamageCause.FIRE, DamageCause.FIRE_TICK, DamageCause.LAVA).contains(event.getCause())) for(ItemStack item: player.getInventory().getArmorContents())
				if (item != null && ItemUtils.hasEnchantment(item, Enchantment.PROTECTION_FIRE)) giveExperience(player, Enchantment.PROTECTION_FIRE, ItemUtils.getLevel(item, Enchantment.PROTECTION_FIRE));
			if (DamageCause.FALL == event.getCause()) {
				ItemStack item = player.getInventory().getBoots();
				if (item != null && ItemUtils.hasEnchantment(item, Enchantment.PROTECTION_FALL)) giveExperience(player, Enchantment.PROTECTION_FALL, ItemUtils.getLevel(item, Enchantment.PROTECTION_FALL));
			}
			if (DamageCause.PROJECTILE == event.getCause()) for(ItemStack item: player.getInventory().getArmorContents())
				if (item != null && ItemUtils.hasEnchantment(item, Enchantment.PROTECTION_PROJECTILE)) giveExperience(player, Enchantment.PROTECTION_PROJECTILE, ItemUtils.getLevel(item, Enchantment.PROTECTION_PROJECTILE));
			if (!Arrays.asList(DamageCause.CUSTOM, DamageCause.MAGIC, DamageCause.STARVATION, DamageCause.VOID, DamageCause.SUICIDE).contains(event.getCause())) for(ItemStack item: player.getInventory().getArmorContents()) {
				if (item != null && ItemUtils.hasEnchantment(item, Enchantment.PROTECTION_ENVIRONMENTAL)) giveExperience(player, Enchantment.PROTECTION_ENVIRONMENTAL, ItemUtils.getLevel(item, Enchantment.PROTECTION_ENVIRONMENTAL));
				if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.ARMORED)) giveExperience(player, RegisterEnchantments.ARMORED, ItemUtils.getLevel(item, RegisterEnchantments.ARMORED));
				if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.LIFE)) giveExperience(player, RegisterEnchantments.LIFE, ItemUtils.getLevel(item, RegisterEnchantments.LIFE));
				if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.TOUGHNESS)) giveExperience(player, RegisterEnchantments.TOUGHNESS, ItemUtils.getLevel(item, RegisterEnchantments.TOUGHNESS));
			}
		}

		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
			Entity e2 = e.getDamager();
			if (e2 instanceof Player) {
				Player player = (Player) e2;
				ItemStack item = player.getInventory().getItemInMainHand();
				if (entity.getType().isAlive() && item != null) {
					if (Arrays.asList("SPIDER", "CAVE_SPIDER", "BEE", "SILVERFISH", "ENDERMITE").contains(entity.getType().name()) && ItemUtils.hasEnchantment(item, Enchantment.DAMAGE_ARTHROPODS)) giveExperience(player, Enchantment.DAMAGE_ARTHROPODS, ItemUtils.getLevel(item, Enchantment.DAMAGE_ARTHROPODS));
					if (Arrays.asList("SKELETON", "STRAY", "WITHER_SKELETON", "ZOMBIE", "DROWNED", "HUSK", "ZOMBIE_PIGMAN", "ZOMBIE_VILLAGER", "PHANTOM", "WITHER", "SKELETON_HORSE", "ZOMBIE_HORSE", "ZOGLIN").contains(entity.getType().name()) && ItemUtils.hasEnchantment(item, Enchantment.DAMAGE_UNDEAD)) giveExperience(player, Enchantment.DAMAGE_UNDEAD, ItemUtils.getLevel(item, Enchantment.DAMAGE_UNDEAD));
					if (ItemUtils.hasEnchantment(item, Enchantment.DAMAGE_ALL)) giveExperience(player, Enchantment.DAMAGE_ALL, ItemUtils.getLevel(item, Enchantment.DAMAGE_ALL));
					if (e.getCause() == DamageCause.ENTITY_SWEEP_ATTACK && ItemUtils.hasEnchantment(item, Enchantment.SWEEPING_EDGE)) giveExperience(player, Enchantment.SWEEPING_EDGE, ItemUtils.getLevel(item, Enchantment.SWEEPING_EDGE));
					if (ItemUtils.hasEnchantment(item, Enchantment.KNOCKBACK)) giveExperience(player, Enchantment.KNOCKBACK, ItemUtils.getLevel(item, Enchantment.KNOCKBACK));
					if (!Arrays.asList("BLAZE", "ZOMBIE_PIGMAN", "ZOMBIE_PIGLIN", "ZOGLIN", "WITHER_SKELETON", "STRIDER").contains(entity.getType().name()) && ItemUtils.hasEnchantment(item, Enchantment.FIRE_ASPECT)) giveExperience(player, Enchantment.FIRE_ASPECT, ItemUtils.getLevel(item, Enchantment.FIRE_ASPECT));
					if (Arrays.asList("DOLPHIN", "GUARDIAN", "ELDER_GUARDIAN", "SQUID", "TURTLE", "COD", "SALMON", "PUFFERFISH", "TROPICAL_FISH").contains(entity.getType().name()) && ItemUtils.hasEnchantment(item, Enchantment.IMPALING)) giveExperience(player, Enchantment.IMPALING, ItemUtils.getLevel(item, Enchantment.IMPALING));
					if (ItemUtils.hasEnchantment(item, RegisterEnchantments.QUICK_STRIKE)) giveExperience(player, RegisterEnchantments.QUICK_STRIKE, ItemUtils.getLevel(item, RegisterEnchantments.QUICK_STRIKE));
				}
			}
			if (e2 instanceof Player && event.getCause() == DamageCause.THORNS) {
				Player player = (Player) e2;
				for(ItemStack item: player.getInventory().getArmorContents())
					if (item != null && ItemUtils.hasEnchantment(item, Enchantment.THORNS)) giveExperience(player, Enchantment.THORNS, ItemUtils.getLevel(item, Enchantment.THORNS));
			}
			if (e2 instanceof Projectile && ((Projectile) e2).getShooter() instanceof Player) {
				Player player = (Player) ((Projectile) e2).getShooter();
				ItemStack item = player.getInventory().getItemInMainHand();
				if (entity.getType().isAlive() && item != null) {
					if (!Arrays.asList("BLAZE", "ZOMBIE_PIGMAN", "ZOMBIE_PIGLIN", "ZOGLIN", "WITHER_SKELETON", "STRIDER").contains(entity.getType().name()) && ItemUtils.hasEnchantment(item, Enchantment.ARROW_FIRE)) giveExperience(player, Enchantment.ARROW_FIRE, ItemUtils.getLevel(item, Enchantment.ARROW_FIRE));
					if (ItemUtils.hasEnchantment(item, Enchantment.ARROW_DAMAGE)) giveExperience(player, Enchantment.ARROW_DAMAGE, ItemUtils.getLevel(item, Enchantment.ARROW_DAMAGE));
					if (ItemUtils.hasEnchantment(item, Enchantment.ARROW_KNOCKBACK)) giveExperience(player, Enchantment.ARROW_KNOCKBACK, ItemUtils.getLevel(item, Enchantment.ARROW_KNOCKBACK));
					if (ItemUtils.hasEnchantment(item, VanillaEnchantment.PIERCING.getEnchantment())) giveExperience(player, VanillaEnchantment.PIERCING.getEnchantment(), ItemUtils.getLevel(item, VanillaEnchantment.PIERCING.getEnchantment()));

					if (e2 instanceof Trident) {
						ItemStack trident = ItemNMS.getTrident((Trident) e2);
						if (trident.getType() == Material.TRIDENT && ItemUtils.hasEnchantment(trident, Enchantment.CHANNELING) && entity.getLocation().getBlock().getLightFromSky() >= 15 && entity.getWorld().getThunderDuration() > 0) giveExperience(player, Enchantment.CHANNELING, ItemUtils.getLevel(trident, Enchantment.CHANNELING));
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onESPlayerEvent(ESPlayerEvent event) {
		EnchantmentLevel level = event.getEnchantment();
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
		EnchantmentLevel level = event.getEnchantment();
		if (level.getEnchant().getRelativeEnchantment() instanceof ApiEnchantmentWrapper) return; // other plugins need to make these events go themselves
		if (level.getEnchant().isCurse()) return; // don't let custom curse enchantments do anything
		giveExperience(event.getPlayer(), level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onESBlockBreakEvent(ESBlockBreakEvent event) {
		EnchantmentLevel level = event.getEnchantment();
		if (level.getEnchant().getRelativeEnchantment() instanceof ApiEnchantmentWrapper) return; // other plugins need to make these events go themselves
		if (level.getEnchant().isCurse()) return; // don't let custom curse enchantments do anything
		giveExperience(event.getPlayer(), level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMultiBlock(MultiBlockEvent event) {
		EnchantmentLevel level = event.getEnchantment();
		if (level.getEnchant().getRelativeEnchantment() instanceof ApiEnchantmentWrapper) return; // other plugins need to make these events go themselves
		if (level.getEnchant().isCurse()) return; // don't let custom curse enchantments do anything
		if (event instanceof HeightWidthEvent) giveExperience(event.getPlayer(), ((HeightWidthEvent) event).getEnchantmentWidth());
		giveExperience(event.getPlayer(), level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onWarpPlayer(WarpPlayerEvent event) {
		EnchantmentLevel level = event.getEnchantment();
		giveExperience(event.getPlayer(), level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDetonateCreeper(DetonateCreeperEvent event) {
		EnchantmentLevel level = event.getEnchantment();
		Entity entity = event.getDetonator();
		if (entity instanceof Player) giveExperience((Player) entity, level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDetonatorExplosion(DetonatorExplosionEvent event) {
		EnchantmentLevel level = event.getEnchantment();
		Entity entity = event.getDetonator();
		if (entity instanceof Player) giveExperience((Player) entity, level);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onESEntityDamageEntity(ESDamageEntityEvent event) {
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
		EnchantmentLevel level = event.getEnchantment();
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (level.getEnchant().getRelativeEnchantment() instanceof ApiEnchantmentWrapper) return; // other plugins need to make these events go themselves
			if (level.getEnchant().isCurse()) return; // don't let custom curse enchantments do anything
			if (event instanceof MagicGuardPotionEvent) giveExperience(player, level);
		}
	}

	@Override
	public void run() {
		for(Player player: Bukkit.getOnlinePlayers()) {
			if (ElytraRunnable.didTick(player)) {
				ItemStack item = player.getInventory().getChestplate();
				giveExperience(player, RegisterEnchantments.FREQUENT_FLYER, ItemUtils.getLevel(item, RegisterEnchantments.FREQUENT_FLYER));
			}
			for(ItemStack item: player.getInventory().getArmorContents())
				if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.NO_REST) && player.getWorld().getEnvironment() == Environment.NORMAL && player.getWorld().getTime() > 12540 && player.getWorld().getTime() < 23459) {
					giveExperience(player, RegisterEnchantments.NO_REST, ItemUtils.getLevel(item, RegisterEnchantments.NO_REST));
					break;
				}
		}
	}

	private void giveExperience(Player player, Enchantment enchant, int level) {
		EnchantmentLevel enchLevel = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchant), level);
		RPGUtils.giveExperience(player, enchLevel);
	}

	private void giveExperience(Player player, EnchantmentLevel level) {
		RPGUtils.giveExperience(player, level);
	}

}
