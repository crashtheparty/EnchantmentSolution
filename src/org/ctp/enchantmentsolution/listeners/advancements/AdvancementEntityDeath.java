package org.ctp.enchantmentsolution.listeners.advancements;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.LocationUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.OverkillDeath;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class AdvancementEntityDeath implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof LivingEntity) {
			LivingEntity killed = (LivingEntity) entity;
			Player killer = killed.getKiller();
			if (killer != null) {
				ItemStack mainHand = killer.getInventory().getItemInMainHand();
				if (ItemUtils.hasEnchantment(mainHand, RegisterEnchantments.KNOCKUP)) AdvancementUtils.awardCriteria(killer, ESAdvancement.NOT_THAT_KIND, killed.getType().name().toLowerCase());
				if (killed instanceof Player && ItemUtils.hasEnchantment(mainHand, RegisterEnchantments.QUICK_STRIKE)) AdvancementUtils.awardCriteria(killer, ESAdvancement.PRE_COMBAT_UPDATE, "combat_update");
				if (ItemUtils.hasEnchantment(mainHand, RegisterEnchantments.BRINE) && mainHand.getType().name().endsWith("SWORD")) if (killed.getType() == EntityType.DROWNED) AdvancementUtils.awardCriteria(killer, ESAdvancement.NOT_VERY_EFFECTIVE, killed.getType().name().toLowerCase());
				else if (killed.getType() == EntityType.ENDER_DRAGON || killed.getType() == EntityType.WITHER || killed.getType() == EntityType.ELDER_GUARDIAN) AdvancementUtils.awardCriteria(killer, ESAdvancement.SUPER_EFFECTIVE, "boss");
				else if (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 3) if (killed.getType() == EntityType.RAVAGER) AdvancementUtils.awardCriteria(killer, ESAdvancement.SUPER_EFFECTIVE, "boss");
				if (ItemUtils.hasEnchantment(mainHand, RegisterEnchantments.EXP_SHARE)) if (killed.getType() == EntityType.ENDER_DRAGON) if (AdvancementUtils.awardCriteria(killer, ESAdvancement.MOTHERLOAD, "dragon")) {
					event.getDrops().add(new ItemStack(Material.DRAGON_HEAD));
					event.getDrops().add(new ItemStack(Material.DRAGON_EGG, 4));
					event.getDrops().add(new ItemStack(Material.DRAGON_BREATH, 64));
				}
				ItemStack chestplate = killer.getInventory().getChestplate();
				if (chestplate != null && ItemUtils.hasEnchantment(chestplate, RegisterEnchantments.GUNG_HO) && killed.getType() == EntityType.WITHER) AdvancementUtils.awardCriteria(killer, ESAdvancement.DANGER_DEFEATED, "wither");
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
						OverkillDeath.addDeath(uuid);
						break;
					}

				}
			}
		}
	}

}
