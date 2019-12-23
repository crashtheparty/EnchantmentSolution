package org.ctp.enchantmentsolution.threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemType;
import org.ctp.enchantmentsolution.events.player.ContagionEvent;
import org.ctp.enchantmentsolution.events.potion.MagicGuardPotionEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.Reflectionable;
import org.ctp.enchantmentsolution.utils.abillityhelpers.DrownedEntity;
import org.ctp.enchantmentsolution.utils.abillityhelpers.EntityAccuracy;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

@SuppressWarnings("unused")
public class MiscRunnable implements Runnable, Reflectionable {

	@Override
	public void run() {
		runMethod(this, "contagionCurse");
		runMethod(this, "drowned");
		runMethod(this, "magicGuard");
		runMethod(this, "sandVeil");
	}

	private void contagionCurse() {
		double chance = 0.0005;
		List<CustomEnchantment> enchantments = new ArrayList<CustomEnchantment>();
		for(CustomEnchantment enchantment : RegisterEnchantments.getRegisteredEnchantments()) {
			if(enchantment.getRelativeEnchantment() == RegisterEnchantments.CURSE_OF_CONTAGION) {
				continue;
			}
			if(enchantment.isCurse()) {
				enchantments.add(enchantment);
			}
		}
		if(enchantments.size() > 0) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				double random = Math.random();
				double playerChance = 0;
				List<ItemStack> items = new ArrayList<ItemStack>();
				for(ItemStack item : player.getInventory().getContents()) {
					if(item != null && ItemType.ALL.getItemTypes().contains(item.getType())) {
						items.add(item);
						if(ItemUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_CONTAGION)) {
							playerChance += chance;
						}
					}
				}
				if(playerChance > random) {
					if(items.size() > 0) {
						int randomItemInt = (int)(Math.random() * items.size());
						ItemStack randomItem = items.get(randomItemInt);
						if(Math.random() >= 0.5 && randomItem != null && !ItemUtils.hasEnchantment(randomItem, RegisterEnchantments.CURSE_OF_CONTAGION)) {
							callContagionCurse(player, randomItem, RegisterEnchantments.getCustomEnchantment(RegisterEnchantments.CURSE_OF_CONTAGION));
							continue;
						}
						List<CustomEnchantment> curses = new ArrayList<CustomEnchantment>();
						curses.addAll(enchantments);
						for(int i = curses.size() - 1; i >= 0; i--) {
							CustomEnchantment curse = curses.get(i);
							if(!ItemUtils.canAddEnchantment(curse, randomItem)) {
								curses.remove(i);
							}
						}
						while(curses.size() > 0) {
							int randomCursesInt = (int)(Math.random() * curses.size());
							CustomEnchantment curse = curses.get(randomCursesInt);
							if(randomItem != null && !ItemUtils.hasEnchantment(randomItem, curse.getRelativeEnchantment())) {
								callContagionCurse(player, randomItem, curse);
								break;
							}
							curses.remove(randomCursesInt);
						}
					}
				}
			}
		}
	}

	private void drowned() {
		if (!canRun(RegisterEnchantments.DROWNED)) {
			return;
		}
		List<DrownedEntity> entities = EnchantmentSolution.getDrowned();
		Map<UUID, Integer> attackers = new HashMap<UUID, Integer>();
		Iterator<DrownedEntity> iterator = entities.iterator();
		while (iterator.hasNext()) {
			DrownedEntity entity = iterator.next();
			entity.inflictDamage();
			if (entity.getDamageTime() <= 0) {
				iterator.remove();
			} else {
				if (attackers.containsKey(entity.getAttackerEntity().getUniqueId())) {
					attackers.put(entity.getAttackerEntity().getUniqueId(),
					attackers.get(entity.getAttackerEntity().getUniqueId()) + 1);
					if (attackers.get(entity.getAttackerEntity().getUniqueId()) >= 3
					&& entity.getAttackerEntity() instanceof Player) {
						AdvancementUtils.awardCriteria((Player) entity.getAttackerEntity(),
						ESAdvancement.SEVEN_POINT_EIGHT, "drowning");
					}
				} else {
					attackers.put(entity.getAttackerEntity().getUniqueId(), 1);
				}
			}
		}
	}

	private void magicGuard() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.MAGIC_GUARD)) {
			return;
		}
		for(Player player: Bukkit.getOnlinePlayers()) {
			ItemStack shield = player.getInventory().getItemInOffHand();
			if (shield.getType().equals(Material.SHIELD)
			&& ItemUtils.hasEnchantment(shield, RegisterEnchantments.MAGIC_GUARD)) {
				for(PotionEffect effect: player.getActivePotionEffects()) {
					if (ESArrays.getBadPotions().contains(effect.getType())) {
						MagicGuardPotionEvent event = new MagicGuardPotionEvent(player, effect.getType());
						Bukkit.getPluginManager().callEvent(event);

						if (!event.isCancelled()) {
							player.removePotionEffect(effect.getType());
						}
					}
				}
			}
		}
	}

	private void sandVeil() {
		if(!RegisterEnchantments.isEnabled(RegisterEnchantments.SAND_VEIL)) {
			return;
		}
		Iterator<EntityAccuracy> entities = EnchantmentSolution.getAccuracy().iterator();
		while (entities.hasNext()) {
			EntityAccuracy entity = entities.next();
			entity.minus();
			if (entity.getTicks() <= 0 || entity.getEntity() == null) {
				entities.remove();
			}
		}
	}

	private void callContagionCurse(Player player, ItemStack item, CustomEnchantment curse) {
		List<Sound> sounds = new ArrayList<Sound>();
		sounds.add(Sound.ENTITY_ELDER_GUARDIAN_AMBIENT);
		sounds.add(Sound.BLOCK_ENCHANTMENT_TABLE_USE);
		ContagionEvent event = new ContagionEvent(player, item, curse, 1, sounds);
		Bukkit.getPluginManager().callEvent(event);

		if(!event.isCancelled()) {
			ItemUtils.addEnchantmentToItem(event.getItem(), event.getCurse(), event.getLevel());
			for(Sound s : event.getSounds()) {
				player.getWorld().playSound(player.getLocation(), s, event.getVolume(), event.getPitch());
			}
		}
	}
}
