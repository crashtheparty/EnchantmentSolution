package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.Lootable;
import org.ctp.crashapi.CrashAPI;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.damage.SacrificeEvent;
import org.ctp.enchantmentsolution.events.drops.ButcherEvent;
import org.ctp.enchantmentsolution.events.entity.HusbandryEvent;
import org.ctp.enchantmentsolution.events.player.ExpShareEvent;
import org.ctp.enchantmentsolution.events.player.ExpShareEvent.ExpShareType;
import org.ctp.enchantmentsolution.events.player.PillageEvent;
import org.ctp.enchantmentsolution.events.player.RecyclerEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.RecyclerDrops;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

@SuppressWarnings("unused")
public class AfterEffectsListener extends Enchantmentable {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		runMethod(this, "butcher", event, EntityDeathEvent.class);
		runMethod(this, "husbandry", event, EntityDeathEvent.class);
		runMethod(this, "pillage", event, EntityDeathEvent.class);
		runMethod(this, "recycler", event, EntityDeathEvent.class);
		runMethod(this, "expShare", event, EntityDeathEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		runMethod(this, "sacrifice", event, PlayerDeathEvent.class);
	}

	private void butcher(EntityDeathEvent event) {
		if (!canRun(RegisterEnchantments.BUTCHER, event)) return;
		LivingEntity entity = event.getEntity();
		if (event.getEntity() instanceof Player) return;
		Player player = entity.getKiller();
		if (player == null || isDisabled(player, RegisterEnchantments.BUTCHER)) return;
		if (entity instanceof Ageable && ((Ageable) entity).isAdult()) {
			ItemStack killItem = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(killItem, RegisterEnchantments.BUTCHER)) {
				List<ItemStack> drops = event.getDrops();
				int level = EnchantmentUtils.getLevel(killItem, RegisterEnchantments.BUTCHER);
				List<ItemStack> newDrops = new ArrayList<ItemStack>();
				for(ItemStack drop: drops)
					if (Arrays.asList(Material.BEEF, Material.COOKED_BEEF, Material.CHICKEN, Material.COOKED_CHICKEN, Material.COD, Material.COOKED_COD, Material.MUTTON, Material.COOKED_MUTTON, Material.PORKCHOP, Material.COOKED_PORKCHOP, Material.RABBIT, Material.COOKED_RABBIT, Material.SALMON, Material.COOKED_SALMON).contains(drop.getType())) {
						ItemStack extraDrops = new ItemStack(drop.getType());
						int multiply = (int) (Math.random() * 2 * level);
						int extraNum = drop.getAmount() * (multiply - 1);
						if (extraNum > 0) {
							extraDrops.setAmount(extraNum);
							ButcherEvent butcher = new ButcherEvent(player, level, Arrays.asList(extraDrops));

							Bukkit.getPluginManager().callEvent(butcher);

							if (!butcher.isCancelled()) {
								newDrops.addAll(butcher.getDrops());
								if (extraDrops.getType().name().contains("COOKED_")) AdvancementUtils.awardCriteria(player, ESAdvancement.MEAT_READY_TO_EAT, "beef");
							}
						}
					}
				for(ItemStack drop: newDrops)
					drops.add(drop);
			} ;
		}
	}

	private void expShare(EntityDeathEvent event) {
		if (!canRun(RegisterEnchantments.EXP_SHARE, event)) return;
		if (event.getEntity() instanceof Player) return;
		Player player = event.getEntity().getKiller();
		if (player == null || isDisabled(player, RegisterEnchantments.EXP_SHARE)) return;
		ItemStack killItem = player.getInventory().getItemInMainHand();
		if (EnchantmentUtils.hasEnchantment(killItem, RegisterEnchantments.EXP_SHARE)) {
			int exp = event.getDroppedExp();
			if (exp > 0) {
				int level = EnchantmentUtils.getLevel(killItem, RegisterEnchantments.EXP_SHARE);

				ExpShareEvent experienceEvent = new ExpShareEvent(player, level, ExpShareType.MOB, exp, AbilityUtils.setExp(exp, level));
				Bukkit.getPluginManager().callEvent(experienceEvent);

				if (!experienceEvent.isCancelled() && experienceEvent.getNewExp() >= 0) event.setDroppedExp(experienceEvent.getNewExp());
			}
		}
	}

	private void husbandry(EntityDeathEvent event) {
		if (!canRun(RegisterEnchantments.HUSBANDRY, event)) return;
		LivingEntity entity = event.getEntity();
		if (event.getEntity() instanceof Player) return;
		Player player = entity.getKiller();
		if (player == null || isDisabled(player, RegisterEnchantments.HUSBANDRY)) return;
		if (entity instanceof Ageable && ((Ageable) entity).isAdult()) {
			ItemStack killItem = player.getInventory().getItemInMainHand();

			if (EnchantmentUtils.hasEnchantment(killItem, RegisterEnchantments.HUSBANDRY)) {
				int level = EnchantmentUtils.getLevel(killItem, RegisterEnchantments.HUSBANDRY);
				double chance = 0.05 * (1 + level);
				HusbandryEvent husbandry = new HusbandryEvent(entity, player, entity.getLocation(), level, chance);
				Bukkit.getPluginManager().callEvent(husbandry);

				if (!husbandry.isCancelled()) {
					double random = Math.random();
					if (chance > random) Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
						Entity e = husbandry.getSpawnWorld().spawnEntity(husbandry.getSpawnLocation(), husbandry.getEntityType());
						e = AnimalMob.setHusbandry((Creature) entity, (Creature) e);
						if (e != null && e instanceof Ageable) ((Ageable) e).setBaby();
						AdvancementUtils.awardCriteria(player, ESAdvancement.WILDLIFE_CONSERVATION, e.getType().name().toLowerCase(Locale.ROOT));
					}, 1l);
				}
			}
		}
	}

	private void pillage(EntityDeathEvent event) {
		if (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 3) {
			if (!canRun(RegisterEnchantments.PILLAGE, event)) return;
			LivingEntity entity = event.getEntity();
			if (entity instanceof Lootable && entity.getKiller() != null) {
				Player player = entity.getKiller();
				if (player == null || isDisabled(player, RegisterEnchantments.PILLAGE)) return;
				ItemStack item = player.getInventory().getItemInOffHand();
				if (item == null || !EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.PILLAGE)) {
					item = player.getInventory().getItemInMainHand();
					if (EnchantmentUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_MOBS)) return;
				}
				if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.PILLAGE)) {
					int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.PILLAGE);
					PillageEvent pillage = new PillageEvent(player, level);
					Bukkit.getPluginManager().callEvent(pillage);

					if (!pillage.isCancelled()) {
						event.getDrops().clear();
						List<EnchantmentLevel> levels = EnchantmentUtils.getEnchantmentLevels(item);
						EnchantmentUtils.addEnchantmentToItem(item, CERegister.FORTUNE, level);
						LootContext.Builder contextBuilder = new LootContext.Builder(event.getEntity().getLocation());
						contextBuilder.killer(player);
						contextBuilder.lootedEntity(event.getEntity());
						contextBuilder.lootingModifier(level);
						LootContext context = contextBuilder.build();
						Collection<ItemStack> items = ((Lootable) entity).getLootTable().populateLoot(new Random(), context);
						event.getDrops().addAll(items);
						EnchantmentUtils.removeAllEnchantments(item, true);
						EnchantmentUtils.addEnchantmentsToItem(item, levels);
						if (event.getEntity().getType() == EntityType.PILLAGER) AdvancementUtils.awardCriteria(player, ESAdvancement.LOOK_WHAT_YOU_MADE_ME_DO, "pillage");
					}
				}
			}
		}
	}

	private void recycler(EntityDeathEvent event) {
		if (!canRun(RegisterEnchantments.RECYCLER, event)) return;
		if (event.getEntity() instanceof Player) return;
		Player player = event.getEntity().getKiller();
		if (player == null || isDisabled(player, RegisterEnchantments.RECYCLER)) return;
		ItemStack killItem = player.getInventory().getItemInMainHand();
		if (EnchantmentUtils.hasEnchantment(killItem, RegisterEnchantments.RECYCLER)) {
			int exp = event.getDroppedExp();
			int recyclerExp = 0;
			boolean willRecycle = false;

			for(ItemStack item: event.getDrops())
				if (RecyclerDrops.isRecycleable(item.getType())) for(int i = 1; i <= item.getAmount(); i++) {
					willRecycle = true;
					recyclerExp += RecyclerDrops.getExperience(item.getType());
				}
			if (willRecycle) {
				RecyclerEvent recyclerEvent = new RecyclerEvent(player, exp, exp + recyclerExp);
				Bukkit.getPluginManager().callEvent(recyclerEvent);

				if (!recyclerEvent.isCancelled() && recyclerEvent.getNewExp() >= 0) {
					event.setDroppedExp(recyclerEvent.getNewExp());
					int finalExp = recyclerEvent.getNewExp() - recyclerEvent.getOldExp();
					AdvancementUtils.awardCriteria(player, ESAdvancement.ENVIRONMENTAL_PROTECTION, "experience", finalExp);
					Iterator<ItemStack> items = event.getDrops().iterator();
					while (items.hasNext()) {
						ItemStack item = items.next();
						if (RecyclerDrops.isRecycleable(item.getType())) items.remove();
					}
				}
			}
		}
	}

	private void sacrifice(PlayerDeathEvent event) {
		if (!canRun(RegisterEnchantments.SACRIFICE, event)) return;
		Player player = event.getEntity();
		if (player == null || isDisabled(player, RegisterEnchantments.SACRIFICE)) return;
		ItemStack chest = player.getInventory().getChestplate();
		if (EnchantmentUtils.hasEnchantment(chest, RegisterEnchantments.SACRIFICE)) {
			int level = EnchantmentUtils.getLevel(chest, RegisterEnchantments.SACRIFICE);
			int playerLevel = player.getLevel();
			double damage = playerLevel / (3.0D / level);
			Entity killer = player.getKiller();
			LivingEntity living = null;

			if (killer != null) {
				if (killer instanceof LivingEntity) living = (LivingEntity) killer;
			} else if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
				if (nEvent.getDamager() instanceof Projectile) {
					Projectile proj = (Projectile) nEvent.getDamager();
					if (proj.getShooter() instanceof LivingEntity) living = (LivingEntity) proj.getShooter();
				} else if (nEvent.getDamager() instanceof LivingEntity) living = (LivingEntity) nEvent.getDamager();
			}
			if (living != null) {
				SacrificeEvent sacrifice = new SacrificeEvent(living, player, damage);
				Bukkit.getPluginManager().callEvent(sacrifice);

				if (!sacrifice.isCancelled()) {
					((LivingEntity) sacrifice.getEntity()).damage(sacrifice.getNewDamage(), sacrifice.getDamager());
					if (sacrifice.getEntity() instanceof Player) {
						Player dead = (Player) sacrifice.getEntity();
						if (dead.getHealth() <= 0) {
							ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
							esPlayer.setSacrificeAdvancement(true);
						}
					}
				}
			}
		}
	}
}
