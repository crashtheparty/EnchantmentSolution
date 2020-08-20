package org.ctp.enchantmentsolution.threads;

import java.util.*;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.Reflectionable;
import org.ctp.enchantmentsolution.utils.abilityhelpers.DrownedEntity;
import org.ctp.enchantmentsolution.utils.abilityhelpers.EntityAccuracy;

@SuppressWarnings("unused")
public class EntityThreads implements Runnable, Reflectionable {

	@Override
	public void run() {
		runMethod(this, "drowned");
		runMethod(this, "sandVeil");
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

	private void sandVeil() {
		if (!RegisterEnchantments.isEnabled(RegisterEnchantments.SAND_VEIL)) return;
		Iterator<EntityAccuracy> entities = EnchantmentSolution.getAccuracy().iterator();
		while (entities.hasNext()) {
			EntityAccuracy entity = entities.next();
			entity.minus();
			if (entity.getTicks() <= 0 || entity.getEntity() == null) entities.remove();
		}
	}
}
