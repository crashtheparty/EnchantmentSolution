package org.ctp.enchantmentsolution.listeners.advancements;

import java.util.*;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.ctp.crashapi.data.MobData;
import org.ctp.crashapi.utils.LocationUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.OverkillDeath;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class AdvancementEntityListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof LivingEntity) {
			LivingEntity killed = (LivingEntity) entity;
			Player killer = killed.getKiller();
			if (killer != null) {
				ItemStack mainHand = killer.getInventory().getItemInMainHand();
				if (EnchantmentUtils.hasEnchantment(mainHand, RegisterEnchantments.LANCER) && killer.getVehicle() instanceof AbstractHorse && killed.getType() == EntityType.SKELETON && killed.getVehicle().getType() == EntityType.SKELETON_HORSE) AdvancementUtils.awardCriteria(killer, ESAdvancement.FOUR_HORSEMEN, "skeleton");
				if (EnchantmentUtils.hasEnchantment(mainHand, RegisterEnchantments.BANE_OF_ANTHROPOIDS)) {
					List<MobData> types = Arrays.asList(new MobData("PIGLIN"), new MobData("VILLAGER"), new MobData("WANDERING_TRADER"), new MobData("ENDERMAN"), new MobData("EVOKER"), new MobData("PIGLIN_BRUTE"), new MobData("PILLAGER"), new MobData("WITCH"), new MobData("VINDICATOR"));
					for(MobData type: types)
						if (type.getEntity() == killed.getType()) AdvancementUtils.awardCriteria(killer, ESAdvancement.APOCALYPSE_NOW, "human", 1);
				}
				if (EnchantmentUtils.hasEnchantment(mainHand, RegisterEnchantments.KNOCKUP)) AdvancementUtils.awardCriteria(killer, ESAdvancement.NOT_THAT_KIND, killed.getType().name().toLowerCase(Locale.ROOT));
				if (killed instanceof Player && EnchantmentUtils.hasEnchantment(mainHand, RegisterEnchantments.QUICK_STRIKE)) AdvancementUtils.awardCriteria(killer, ESAdvancement.PRE_COMBAT_UPDATE, "combat_update");
				if (EnchantmentUtils.hasEnchantment(mainHand, RegisterEnchantments.BRINE)) {
					EntityType t = killed.getType();
					if (t == EntityType.DROWNED) AdvancementUtils.awardCriteria(killer, ESAdvancement.NOT_VERY_EFFECTIVE, "drowned");
					else if (t == EntityType.RAVAGER || t == EntityType.ENDER_DRAGON || t == EntityType.WITHER || t == EntityType.ELDER_GUARDIAN) AdvancementUtils.awardCriteria(killer, ESAdvancement.SUPER_EFFECTIVE, "boss");
					else if (new MobData("WARDEN").hasEntity() && t == new MobData("WARDEN").getEntity()) AdvancementUtils.awardCriteria(killer, ESAdvancement.SUPER_EFFECTIVE, "boss");
				}
				if (EnchantmentUtils.hasEnchantment(mainHand, RegisterEnchantments.EXP_SHARE) && killed.getType() == EntityType.ENDER_DRAGON && AdvancementUtils.awardCriteria(killer, ESAdvancement.MOTHERLOAD, "dragon")) {
					event.getDrops().add(new ItemStack(Material.DRAGON_HEAD));
					event.getDrops().add(new ItemStack(Material.DRAGON_EGG, 4));
					event.getDrops().add(new ItemStack(Material.DRAGON_BREATH, 64));
				}
				ItemStack chestplate = killer.getInventory().getChestplate();
				if (EnchantmentUtils.hasEnchantment(chestplate, RegisterEnchantments.GUNG_HO) && killed.getType() == EntityType.WITHER) AdvancementUtils.awardCriteria(killer, ESAdvancement.DANGER_DEFEATED, "wither");
			}
			EntityDamageEvent damageEvent = entity.getLastDamageCause();
			if (damageEvent instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) damageEvent).getDamager() instanceof Projectile) {
				Projectile p = (Projectile) ((EntityDamageByEntityEvent) damageEvent).getDamager();
				if (p.getMetadata("deflection") != null && p.getMetadata("deflection").size() > 0) for(MetadataValue meta: p.getMetadata("deflection")) {
					OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(meta.asString()));
					if (player.getPlayer() != null) AdvancementUtils.awardCriteria(player.getPlayer(), ESAdvancement.DEFLECTION, "shield");
				}
				if (p instanceof Arrow) {
					Arrow arrow = (Arrow) p;
					if (arrow.getMetadata("sniper") != null && arrow.getMetadata("sniper").size() > 0) for(MetadataValue meta: arrow.getMetadata("sniper")) {
						Location loc = LocationUtils.stringToLocation(meta.asString());
						if (killed.getLocation().distance(loc) >= 100) AdvancementUtils.awardCriteria(killer, ESAdvancement.WHERE_DID_THAT_COME_FROM, "sniper");
					}
					if (arrow.getMetadata("overkill") != null && arrow.getMetadata("overkill").size() > 0) for(MetadataValue meta: arrow.getMetadata("overkill")) {
						UUID uuid = UUID.fromString(meta.asString());
						OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
						if (player != null && player.isOnline()) {
							ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player.getPlayer());
							esPlayer.addOverkillDeath(new OverkillDeath());
						}
						break;
					}

				}
				if (p instanceof Snowball) {
					Snowball snowball = (Snowball) p;
					if (snowball.getMetadata("frosty") != null && snowball.getMetadata("frosty").size() > 0 && event.getEntityType() == EntityType.BLAZE) {
						Player player = Bukkit.getPlayer(UUID.fromString(snowball.getMetadata("frosty").get(0).asString()));
						if (player != null) AdvancementUtils.awardCriteria(player, ESAdvancement.BEFORE_I_MELT_AWAY, "blaze");
					}
				}
				if (p instanceof Fireball) {
					Fireball fireball = (Fireball) p;
					if (fireball.getMetadata("zeal") != null && fireball.getMetadata("zeal").size() > 0 && event.getEntityType() == EntityType.GHAST) {
						Player player = Bukkit.getPlayer(UUID.fromString(fireball.getMetadata("zeal").get(0).asString()));
						if (player != null) AdvancementUtils.awardCriteria(player, ESAdvancement.KILL_THE_MESSENGER, "ghast");
					}
				}
			}
			if (killed instanceof Player) {
				Player player = (Player) killed;
				if (player.getLastDamageCause().getCause() == DamageCause.FALL) {
					ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
					for(ItemStack i: esPlayer.getArmor())
						if (EnchantmentUtils.hasEnchantment(i, RegisterEnchantments.PLYOMETRICS)) {
							esPlayer.setPlyometricsAdvancement(true);
							break;
						}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof LivingEntity && entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			LivingEntity damaged = (LivingEntity) entity;
			EntityDamageByEntityEvent damageEntityEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
			Entity damager = damageEntityEvent.getDamager();
			if (damager instanceof Player) {
				Player human = (Player) damager;
				ItemStack item = human.getInventory().getItemInMainHand();
				if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.FLING) && damaged.getType() == EntityType.PLAYER) Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
					if (damaged.isDead()) AdvancementUtils.awardCriteria(human, ESAdvancement.YEET, "player");
				}, 0l);
				if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.FLING) && damaged.getType() == EntityType.PIG) {
					boolean passengerPlayer = false;
					for(Entity p: damaged.getPassengers())
						if (p.getType() == EntityType.PLAYER) passengerPlayer = true;
					if (passengerPlayer) Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
						if (damaged.isDead()) AdvancementUtils.awardCriteria(human, ESAdvancement.FLYING_BACON, "pig");
					}, 0l);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (damager instanceof Projectile) {
			Projectile p = (Projectile) damager;
			if (p instanceof Snowball) {
				Snowball snowball = (Snowball) p;
				MobData data = new MobData("snowman");
				if (data.getEntity() == null) data = new MobData("snow_golem");
				if (snowball.getMetadata("frosty") != null && snowball.getMetadata("frosty").size() > 0 && event.getEntityType() == data.getEntity()) {
					Player player = Bukkit.getPlayer(UUID.fromString(snowball.getMetadata("frosty").get(0).asString()));
					if (player != null) AdvancementUtils.awardCriteria(player, ESAdvancement.THE_SNOWMAN, "snowball");
				}
			}
		}
	}
}
