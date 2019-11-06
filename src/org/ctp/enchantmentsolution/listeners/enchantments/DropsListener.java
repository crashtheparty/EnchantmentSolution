package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.drops.BeheadingEvent;
import org.ctp.enchantmentsolution.events.drops.TransmutationEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abillityhelpers.TransmutationLoot;

@SuppressWarnings("unused")
public class DropsListener extends Enchantmentable {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		runMethod(this, "beheading", event, EntityDeathEvent.class);
		runMethod(this, "transmutation", event, EntityDeathEvent.class);
	}

	private void beheading(EntityDeathEvent event) {
		if (!canRun(RegisterEnchantments.BEHEADING, event)) {
			return;
		}
		Entity entity = event.getEntity();
		Player killer = event.getEntity().getKiller();
		if (killer != null) {
			if (ItemUtils.hasEnchantment(killer.getInventory().getItemInMainHand(), RegisterEnchantments.BEHEADING)) {
				double chance = ItemUtils.getLevel(killer.getInventory().getItemInMainHand(),
						RegisterEnchantments.BEHEADING) * .05;
				double random = Math.random();
				if (chance > random) {
					List<ItemStack> skulls = new ArrayList<ItemStack>();
					if (entity instanceof WitherSkeleton) {
						skulls.add(new ItemStack(Material.WITHER_SKELETON_SKULL));
					} else if (entity instanceof Skeleton) {
						skulls.add(new ItemStack(Material.SKELETON_SKULL));
					} else if (entity instanceof Zombie) {
						skulls.add(new ItemStack(Material.ZOMBIE_HEAD));
					} else if (entity instanceof Creeper) {
						skulls.add(new ItemStack(Material.CREEPER_HEAD));
					} else if (entity instanceof Player) {
						ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
						SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
						skullMeta.setOwningPlayer(((Player) entity));
						// TODO: move this text to language.yml
						skullMeta.setDisplayName(ChatColor.DARK_RED + ((Player) entity).getName() + "'s Skull");
						skull.setItemMeta(skullMeta);
						skulls.add(skull);
					} else if (entity instanceof EnderDragon) {
						skulls.add(new ItemStack(Material.DRAGON_HEAD));
					}

					BeheadingEvent beheading = new BeheadingEvent(killer, skulls, event.getDrops(), false, true);
					Bukkit.getPluginManager().callEvent(beheading);

					if (!beheading.isCancelled()) {
						List<ItemStack> newDrops = new ArrayList<ItemStack>();
						if (!beheading.isOverride()) {
							newDrops.addAll(beheading.getOriginalDrops());
							if (heads(Material.WITHER_SKELETON_SKULL, beheading.getOriginalDrops(),
									beheading.getNewDrops()) >= 2) {
								AdvancementUtils.awardCriteria(killer, ESAdvancement.DOUBLE_HEADER, "wither_skull");
							}
						}
						newDrops.addAll(beheading.getNewDrops());
						if (event instanceof PlayerDeathEvent) {
							if (((PlayerDeathEvent) event).getKeepInventory() && !beheading.isKeepInventoryOverride()) {
								return;
							}
						}
						if (heads(Material.PLAYER_HEAD, newDrops, null) > 0) {
							AdvancementUtils.awardCriteria(killer, ESAdvancement.HEADHUNTER, "player_head");
						}

						event.getDrops().clear();
						for(ItemStack drop: newDrops) {
							event.getDrops().add(drop);
						}
					}
				}
			}
		}
	}

	private void transmutation(EntityDeathEvent event) {
		if (!canRun(RegisterEnchantments.TRANSMUTATION, event)) {
			return;
		}
		if (event.getEntity() instanceof Player) {
			return;
		}
		EntityDamageEvent e = event.getEntity().getLastDamageCause();
		if (e instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent entityDamage = (EntityDamageByEntityEvent) e;
			if (entityDamage.getDamager() instanceof Projectile) {
				Projectile p = (Projectile) entityDamage.getDamager();
				if (p.hasMetadata("transmutation")) {
					for(MetadataValue meta: p.getMetadata("transmutation")) {
						if (meta.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
							handleTransmutation(event);
						}
					}
				}
			} else if (event.getEntity().getKiller() != null) {
				Player killer = event.getEntity().getKiller();
				if (ItemUtils.hasEnchantment(killer.getInventory().getItemInMainHand(),
						RegisterEnchantments.TRANSMUTATION)) {
					handleTransmutation(event);
				}
			}
		}
	}

	private int heads(Material type, List<ItemStack> original, List<ItemStack> newDrops) {
		int skulls = 0;
		if (original != null) {
			for(ItemStack i: original) {
				if (i.getType() == type) {
					skulls += i.getAmount();
				}
			}
		}
		if (newDrops != null) {
			for(ItemStack i: newDrops) {
				if (i.getType() == type) {
					skulls += i.getAmount();
				}
			}
		}
		return skulls;
	}

	private void handleTransmutation(EntityDeathEvent event) {
		List<ItemStack> drops = new ArrayList<ItemStack>();
		boolean override = true;
		boolean changedLoot = false;
		if (event.getEntity() instanceof Wither) {
			override = false;
			changedLoot = true;
			for(int i = 0; i < 64; i++) {
				drops.add(TransmutationLoot.getLoot(event.getEntity().getKiller()));
			}
		} else {
			for(ItemStack item: event.getDrops()) {
				if (!TransmutationLoot.isTransmutatedLoot(item)) {
					changedLoot = true;
					drops.add(TransmutationLoot.getLoot(event.getEntity().getKiller()));
				}
			}
		}

		if (!changedLoot) {
			AdvancementUtils.awardCriteria(event.getEntity().getKiller(), ESAdvancement.POSEIDONS_DAY_OFF, "day_off");
			return;
		}

		TransmutationEvent transmutation = new TransmutationEvent(event.getEntity().getKiller(), drops,
				event.getDrops(), override);
		Bukkit.getPluginManager().callEvent(transmutation);

		if (!transmutation.isCancelled()) {
			List<ItemStack> newDrops = new ArrayList<ItemStack>();
			if (!transmutation.isOverride()) {
				newDrops.addAll(transmutation.getOriginalDrops());
			}
			newDrops.addAll(transmutation.getNewDrops());
			if (!override) {
				AdvancementUtils.awardCriteria(event.getEntity().getKiller(), ESAdvancement.CERBERUS, "obsidian");
			}
			event.getDrops().clear();
			for(ItemStack drop: newDrops) {
				event.getDrops().add(drop);
			}
		}
	}
}
