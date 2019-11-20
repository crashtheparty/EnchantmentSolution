package org.ctp.enchantmentsolution.threads;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
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
		runMethod(this, "drowned");
		runMethod(this, "magicGuard");
		runMethod(this, "sandVeil");
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
						AdvancementUtils.awardCriteria(((Player) entity.getAttackerEntity()),
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
		if(!RegisterEnchantments.isEnabled(RegisterEnchantments.SAND_VEIL)) return;
		Iterator<EntityAccuracy> entities = EnchantmentSolution.getAccuracy().iterator();
		while (entities.hasNext()) {
			EntityAccuracy entity = entities.next();
			entity.minus();
			if (entity.getTicks() <= 0 || entity.getEntity() == null) {
				entities.remove();
			}
		}
	}
}
