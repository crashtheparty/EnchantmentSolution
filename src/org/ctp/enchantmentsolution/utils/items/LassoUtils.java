package org.ctp.enchantmentsolution.utils.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.config.Configurable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.persistence.PersistenceUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.LassoMob;

public class LassoUtils {

	public static int getNumEntities(ItemStack item, EnchantmentWrapper enchantment) {
		return getMobs(item, enchantment).size();
	}

	public static List<LassoMob> getMobs(ItemStack item, EnchantmentWrapper enchantment) {
		List<LassoMob> mobs = EnchantmentSolution.getLassoMobs(enchantment);
		List<LassoMob> inItem = new ArrayList<LassoMob>();
		for(LassoMob mob: mobs)
			if (PersistenceUtils.getAnimalIDsFromItem(item).contains(mob.getEntityID())) inItem.add(mob);
		return inItem;
	}

	public static LassoMob createFromConfig(Configurable config, int i, EnchantmentWrapper enchantment) {
		LassoMob mob = new LassoMob(enchantment);
		String location = mob.getLocation();

		mob.setNbtData(config.getString(location + ".nbt_data"));
		mob.setType(EntityType.valueOf(config.getString(location + ".entity_type").toUpperCase(Locale.ROOT)));
		config.getConfig().removeKey(location);

		EnchantmentSolution.addLassoMob(enchantment, mob);
		return mob;
	}

}
