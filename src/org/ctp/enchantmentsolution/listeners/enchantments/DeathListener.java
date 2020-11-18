package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.ctp.crashapi.item.MobHeads;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.drops.BeheadingEvent;
import org.ctp.enchantmentsolution.events.drops.TransmutationEvent;
import org.ctp.enchantmentsolution.events.entity.StreakDeathEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.TransmutationLoot;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

@SuppressWarnings("unused")
public class DeathListener extends Enchantmentable {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		runMethod(this, "beheading", event, EntityDeathEvent.class);
		runMethod(this, "streak", event, EntityDeathEvent.class);
		runMethod(this, "transmutation", event, EntityDeathEvent.class);
	}

	private void beheading(EntityDeathEvent event) {
		if (!canRun(RegisterEnchantments.BEHEADING, event)) return;
		Entity entity = event.getEntity();
		Player killer = event.getEntity().getKiller();
		if (killer == null || isDisabled(killer, RegisterEnchantments.BEHEADING)) return;
		if (entity instanceof LivingEntity && killer != null) {
			LivingEntity living = (LivingEntity) entity;
			if (EnchantmentUtils.hasEnchantment(killer.getInventory().getItemInMainHand(), RegisterEnchantments.BEHEADING)) {
				int level = EnchantmentUtils.getLevel(killer.getInventory().getItemInMainHand(), RegisterEnchantments.BEHEADING);
				double chance = level * .05;
				double random = Math.random();
				if (chance > random) {
					List<ItemStack> skulls = new ArrayList<ItemStack>();
					ItemStack skull = MobHeads.getMobHead(living, killer.hasPermission("enchantmentsolution.abilities.player-skulls"), killer.hasPermission("enchantmentsolution.abilities.custom-skulls"));
					if (skull != null) {
						skulls.add(skull);
						BeheadingEvent beheading = new BeheadingEvent(killer, (LivingEntity) entity, level, skulls, event.getDrops(), false, true);
						Bukkit.getPluginManager().callEvent(beheading);

						if (!beheading.isCancelled()) {
							List<ItemStack> newDrops = new ArrayList<ItemStack>();
							if (!beheading.isOverride()) {
								newDrops.addAll(beheading.getOriginalDrops());
								if (heads(Material.WITHER_SKELETON_SKULL, beheading.getOriginalDrops(), beheading.getDrops()) >= 2) AdvancementUtils.awardCriteria(killer, ESAdvancement.DOUBLE_HEADER, "wither_skull");
							}
							newDrops.addAll(beheading.getDrops());
							if (event instanceof PlayerDeathEvent && ((PlayerDeathEvent) event).getKeepInventory() && !beheading.isKeepInventoryOverride()) return;
							if (living instanceof Player) AdvancementUtils.awardCriteria(killer, ESAdvancement.HEADHUNTER, "player_head");

							event.getDrops().clear();
							for(ItemStack drop: newDrops)
								event.getDrops().add(drop);
						}
					}
				}
			}
		}
	}

	private void streak(EntityDeathEvent event) {
		if (!canRun(RegisterEnchantments.STREAK, event)) return;
		EntityDamageEvent e = event.getEntity().getLastDamageCause();
		if (e instanceof EntityDamageByEntityEvent) {
			if (event.getEntity().getKiller() != null) {
				Player killer = event.getEntity().getKiller();
				if (killer == null || isDisabled(killer, RegisterEnchantments.STREAK)) return;
				if (EnchantmentUtils.hasEnchantment(killer.getInventory().getItemInMainHand(), RegisterEnchantments.STREAK)) {
					StreakDeathEvent streak = new StreakDeathEvent(event.getEntity(), killer);
					if (!streak.isCancelled()) {
						ESPlayer player = EnchantmentSolution.getESPlayer(killer);
						player.addToStreak(streak.getEntity());
					}
				}
			}
		}
	}

	private void transmutation(EntityDeathEvent event) {
		if (!canRun(RegisterEnchantments.TRANSMUTATION, event)) return;
		if (event.getEntity() instanceof Player) return;
		EntityDamageEvent e = event.getEntity().getLastDamageCause();
		if (e instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent entityDamage = (EntityDamageByEntityEvent) e;
			if (entityDamage.getDamager() instanceof Projectile) {
				Projectile p = (Projectile) entityDamage.getDamager();
				if (p.hasMetadata("transmutation")) for(MetadataValue meta: p.getMetadata("transmutation"))
					if (meta.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) handleTransmutation(event);
			} else if (event.getEntity().getKiller() != null) {
				Player killer = event.getEntity().getKiller();
				if (EnchantmentUtils.hasEnchantment(killer.getInventory().getItemInMainHand(), RegisterEnchantments.TRANSMUTATION)) handleTransmutation(event);
			}
		}
	}

	private int heads(Material type, List<ItemStack> original, List<ItemStack> newDrops) {
		int skulls = 0;
		if (original != null) for(ItemStack i: original)
			if (i.getType() == type) skulls += i.getAmount();
		if (newDrops != null) for(ItemStack i: newDrops)
			if (i.getType() == type) skulls += i.getAmount();
		return skulls;
	}

	private void handleTransmutation(EntityDeathEvent event) {
		Player player = event.getEntity().getKiller();
		if (player == null || isDisabled(player, RegisterEnchantments.TRANSMUTATION)) return;
		List<ItemStack> drops = new ArrayList<ItemStack>();
		boolean override = true;
		boolean changedLoot = false;
		if (event.getEntity() instanceof Wither) {
			override = false;
			changedLoot = true;
			for(int i = 0; i < 64; i++)
				drops.add(TransmutationLoot.getLoot(event.getEntity().getKiller()));
		} else
			for(ItemStack item: event.getDrops())
				if (!TransmutationLoot.isTransmutatedLoot(item)) {
					changedLoot = true;
					drops.add(TransmutationLoot.getLoot(event.getEntity().getKiller()));
				}

		if (!changedLoot) {
			AdvancementUtils.awardCriteria(event.getEntity().getKiller(), ESAdvancement.POSEIDONS_DAY_OFF, "day_off");
			return;
		}

		TransmutationEvent transmutation = new TransmutationEvent(player, event.getEntity(), drops, event.getDrops(), override);
		Bukkit.getPluginManager().callEvent(transmutation);

		if (!transmutation.isCancelled()) {
			List<ItemStack> newDrops = new ArrayList<ItemStack>();
			if (!transmutation.isOverride()) newDrops.addAll(transmutation.getOriginalDrops());
			newDrops.addAll(transmutation.getDrops());
			if (!override) AdvancementUtils.awardCriteria(player, ESAdvancement.CERBERUS, "obsidian");
			event.getDrops().clear();
			for(ItemStack drop: newDrops)
				event.getDrops().add(drop);
		}
	}
}
