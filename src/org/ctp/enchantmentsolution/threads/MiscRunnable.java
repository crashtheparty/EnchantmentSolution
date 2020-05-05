package org.ctp.enchantmentsolution.threads;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.ForceFeedEvent;
import org.ctp.enchantmentsolution.events.modify.ExhaustionEvent;
import org.ctp.enchantmentsolution.events.player.ContagionEvent;
import org.ctp.enchantmentsolution.events.potion.MagicGuardPotionEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.Reflectionable;
import org.ctp.enchantmentsolution.utils.abilityhelpers.*;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

@SuppressWarnings("unused")
public class MiscRunnable implements Runnable, Reflectionable {

	private static List<ContagionPlayer> CONTAGION = new ArrayList<ContagionPlayer>();
	private static List<ExhaustionPlayer> EXHAUSTION = new ArrayList<ExhaustionPlayer>();
	private static List<ForceFeedPlayer> FEED = new ArrayList<ForceFeedPlayer>();

	@Override
	public void run() {
		runMethod(this, "contagionCurse");
		runMethod(this, "drowned");
		runMethod(this, "exhaustionCurse");
		runMethod(this, "forceFeed");
		runMethod(this, "magicGuard");
		runMethod(this, "sandVeil");
	}

	private void contagionCurse() {
		if (!canRun(RegisterEnchantments.CURSE_OF_CONTAGION)) return;
		List<CustomEnchantment> enchantments = RegisterEnchantments.getCurseEnchantments();
		if (enchantments.size() > 0) {
			Iterator<ContagionPlayer> iter = CONTAGION.iterator();
			while (iter.hasNext()) {
				ContagionPlayer cPlayer = iter.next();
				Player player = cPlayer.getPlayer();
				List<ItemStack> items = cPlayer.getCurseableItems();
				if (cPlayer.getChance() <= 0 || items.size() == 0) {
					iter.remove();
					continue;
				}
				double random = Math.random();
				if (cPlayer.getChance() > random) {
					int randomItemInt = (int) (Math.random() * items.size());
					ItemStack randomItem = items.get(randomItemInt);
					if (Math.random() >= 0.5 && randomItem != null && !ItemUtils.hasEnchantment(randomItem, RegisterEnchantments.CURSE_OF_CONTAGION)) {
						callContagionCurse(player, randomItem, RegisterEnchantments.getCustomEnchantment(RegisterEnchantments.CURSE_OF_CONTAGION));
						continue;
					}
					List<CustomEnchantment> curses = new ArrayList<CustomEnchantment>();
					curses.addAll(enchantments);
					for(int i = curses.size() - 1; i >= 0; i--) {
						CustomEnchantment curse = curses.get(i);
						if (!ItemUtils.canAddEnchantment(curse, randomItem)) curses.remove(i);
					}
					while (curses.size() > 0) {
						int randomCursesInt = (int) (Math.random() * curses.size());
						CustomEnchantment curse = curses.get(randomCursesInt);
						if (randomItem != null && !ItemUtils.hasEnchantment(randomItem, curse.getRelativeEnchantment())) {
							callContagionCurse(player, randomItem, curse);
							break;
						}
						curses.remove(randomCursesInt);
					}
				}
			}
		}
	}

	private void drowned() {
		if (!canRun(RegisterEnchantments.DROWNED)) return;
		List<DrownedEntity> entities = EnchantmentSolution.getDrowned();
		Map<UUID, Integer> attackers = new HashMap<UUID, Integer>();
		Iterator<DrownedEntity> iterator = entities.iterator();
		while (iterator.hasNext()) {
			DrownedEntity entity = iterator.next();
			entity.inflictDamage();
			if (entity.getDamageTime() <= 0) iterator.remove();
			else if (attackers.containsKey(entity.getAttackerEntity().getUniqueId())) {
				attackers.put(entity.getAttackerEntity().getUniqueId(), attackers.get(entity.getAttackerEntity().getUniqueId()) + 1);
				if (attackers.get(entity.getAttackerEntity().getUniqueId()) >= 3 && entity.getAttackerEntity() instanceof Player) AdvancementUtils.awardCriteria((Player) entity.getAttackerEntity(), ESAdvancement.SEVEN_POINT_EIGHT, "drowning");
			} else
				attackers.put(entity.getAttackerEntity().getUniqueId(), 1);
		}
	}

	private void exhaustionCurse() {
		Iterator<ExhaustionPlayer> iter = EXHAUSTION.iterator();
		while (iter.hasNext()) {
			ExhaustionPlayer eplayer = iter.next();
			Player player = eplayer.getPlayer();
			if (player != null && player.isOnline() && eplayer.getExhaustion() > 0) {
				eplayer.setCurrentExhaustion();
				float change = eplayer.getPastExhaustion() - eplayer.getCurrentExhaustion();
				int exhaustionCurse = eplayer.getExhaustion();
				if (exhaustionCurse > 0 && change > 0) {
					ExhaustionEvent event = new ExhaustionEvent(player, exhaustionCurse, change);
					Bukkit.getPluginManager().callEvent(event);

					if (!event.isCancelled() && event.getMultiplier() > 0) {
						player.setExhaustion((float) (player.getExhaustion() + event.getExhaustionTick() * event.getMultiplier()));
						if (event.getMultiplier() >= 3.0D) AdvancementUtils.awardCriteria(player, ESAdvancement.HIGH_METABOLISM, "exhaustion");
					}
					eplayer.setCurrentExhaustion();
				}
			} else
				iter.remove();
		}
	}

	private void forceFeed() {
		Iterator<ForceFeedPlayer> iter = FEED.iterator();
		while (iter.hasNext()) {
			ForceFeedPlayer fPlayer = iter.next();
			Player player = fPlayer.getPlayer();
			List<ItemStack> items = fPlayer.getForceFeedItems();
			if (items.size() == 0) {
				iter.remove();
				continue;
			}
			double rand = Math.random();
			if (fPlayer.getChance() > rand) {
				Collections.shuffle(items);
				ItemStack item = items.get(0);
				int damage = DamageUtils.getDamage(item.getItemMeta());
				if (damage > 0) {
					ForceFeedEvent forceFeed = new ForceFeedEvent(player, ItemUtils.getLevel(item, RegisterEnchantments.FORCE_FEED), item, 2);
					Bukkit.getPluginManager().callEvent(forceFeed);
					if (!forceFeed.isCancelled() && forceFeed.getRepair() > 0) {
						damage -= forceFeed.getRepair();
						if (damage < 0) damage = 0;
						DamageUtils.setDamage(item, damage);
						player.setExhaustion(player.getExhaustion() + forceFeed.getExhaust());
					}
				}
			}
		}
	}

	private void magicGuard() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.MAGIC_GUARD)) return;
		for(Player player: Bukkit.getOnlinePlayers()) {
			ItemStack shield = player.getInventory().getItemInOffHand();
			if (shield.getType().equals(Material.SHIELD) && ItemUtils.hasEnchantment(shield, RegisterEnchantments.MAGIC_GUARD)) for(PotionEffect effect: player.getActivePotionEffects())
				if (ESArrays.getBadPotions().contains(effect.getType())) {
					MagicGuardPotionEvent event = new MagicGuardPotionEvent(player, effect.getType());
					Bukkit.getPluginManager().callEvent(event);

					if (!event.isCancelled()) player.removePotionEffect(effect.getType());
				}
		}
	}

	private void sandVeil() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.SAND_VEIL)) return;
		Iterator<EntityAccuracy> entities = EnchantmentSolution.getAccuracy().iterator();
		while (entities.hasNext()) {
			EntityAccuracy entity = entities.next();
			entity.minus();
			if (entity.getTicks() <= 0 || entity.getEntity() == null) entities.remove();
		}
	}

	public static void addContagion(Player player) {
		Iterator<ContagionPlayer> iter = CONTAGION.iterator();
		while (iter.hasNext()) {
			ContagionPlayer cplayer = iter.next();
			if (cplayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return;
		}
		CONTAGION.add(new ContagionPlayer(player));
	}

	public static void addExhaustion(Player player) {
		Iterator<ExhaustionPlayer> iter = EXHAUSTION.iterator();
		while (iter.hasNext()) {
			ExhaustionPlayer eplayer = iter.next();
			if (eplayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return;
		}
		EXHAUSTION.add(new ExhaustionPlayer(player));
	}

	public static void addFeed(Player player) {
		Iterator<ForceFeedPlayer> iter = FEED.iterator();
		while (iter.hasNext()) {
			ForceFeedPlayer fplayer = iter.next();
			if (fplayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return;
		}
		FEED.add(new ForceFeedPlayer(player));
	}

	private void callContagionCurse(Player player, ItemStack item, CustomEnchantment curse) {
		List<Sound> sounds = new ArrayList<Sound>();
		sounds.add(Sound.ENTITY_ELDER_GUARDIAN_AMBIENT);
		sounds.add(Sound.BLOCK_ENCHANTMENT_TABLE_USE);
		ContagionEvent event = new ContagionEvent(player, item, curse, 1, sounds);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			ItemUtils.addEnchantmentToItem(event.getItem(), event.getCurse(), event.getLevel());
			if (event.getCurse() == CERegister.CURSE_OF_CONTAGION) AdvancementUtils.awardCriteria(player, ESAdvancement.PLAGUE_INC, "contagion");
			if (hasAllCurses(item)) AdvancementUtils.awardCriteria(player, ESAdvancement.EXTERMINATION, "contagion");
			for(Sound s: event.getSounds())
				player.getWorld().playSound(player.getLocation(), s, event.getVolume(), event.getPitch());
		}
	}

	private boolean hasAllCurses(ItemStack item) {
		boolean noCurse = true;
		for(CustomEnchantment enchantment: RegisterEnchantments.getEnchantments())
			if (enchantment.isCurse() && ItemUtils.canAddEnchantment(enchantment, item) && !ItemUtils.hasEnchantment(item, enchantment.getRelativeEnchantment())) {
				noCurse = false;
				break;
			}
		return noCurse;
	}
}
