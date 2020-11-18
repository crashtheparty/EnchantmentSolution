package org.ctp.enchantmentsolution.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffectType;

public class ESArrays {
	private static List<String> SHULKER_BOXES = Arrays.asList("BLACK_SHULKER_BOX", "BLUE_SHULKER_BOX", "BROWN_SHULKER_BOX", "CYAN_SHULKER_BOX", "GRAY_SHULKER_BOX", "GREEN_SHULKER_BOX", "LIGHT_BLUE_SHULKER_BOX", "LIME_SHULKER_BOX", "MAGENTA_SHULKER_BOX", "ORANGE_SHULKER_BOX", "PINK_SHULKER_BOX", "PURPLE_SHULKER_BOX", "RED_SHULKER_BOX", "LIGHT_GRAY_SHULKER_BOX", "WHITE_SHULKER_BOX", "YELLOW_SHULKER_BOX", "SHULKER_BOX");

	private static List<String> BAD_POTIONS = Arrays.asList("BAD_OMEN", "BLINDNESS", "CONFUSION", "HARM", "HUNGER", "POISON", "SLOW", "SLOW_DIGGING", "UNLUCK", "WEAKNESS", "WITHER");

	private static List<String> CONTACT_CAUSES = Arrays.asList("BLOCK_EXPLOSION", "CONTACT", "CUSTOM", "ENTITY_ATTACK", "ENTITY_EXPLOSION", "ENTITY_SWEEP_ATTACK", "LIGHTNING", "PROJECTILE", "THORNS");

	public static List<PotionEffectType> getBadPotions() {
		return changeValues(BAD_POTIONS, PotionEffectType.class);
	}

	public static List<Material> getShulkerBoxes() {
		return changeValues(SHULKER_BOXES, Material.class);
	}

	public static List<DamageCause> getContactCauses() {
		return changeValues(CONTACT_CAUSES, DamageCause.class);
	}

	@SuppressWarnings({ "unchecked" })
	private static <E> List<E> changeValues(List<String> original, Class<E> clazz) {
		List<E> list = new ArrayList<E>();
		for(String s: original)
			try {
				Method method = clazz.getMethod(clazz.equals(PotionEffectType.class) ? "getByName" : "valueOf", String.class);
				Object value = method.invoke(clazz, s);
				if (clazz.isAssignableFrom(value.getClass())) list.add((E) value);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		return list;
	}
}
