package org.ctp.enchantmentsolution.utils.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class DamageUtils {

	private ItemMeta meta;
	private int damage;
	private boolean damageable = false;
	
	private DamageUtils(ItemMeta item) {
		meta = item;
		if(item instanceof Damageable) {
			damageable = true;
			damage = ((Damageable) item).getDamage();
		}
	}
	
	private void setDamage(int damage) {
		((Damageable) meta).setDamage(damage);
		this.damage = damage;
	}
	
	private int getDamage() {
		return damage;
	}
	
	private boolean isDamageable() {
		return damageable;
	}
	
	private ItemMeta getItemMeta() {
		return meta;
	}
	
	public static ItemStack setDamage(ItemStack item, int damage) {
		DamageUtils utils = new DamageUtils(item.getItemMeta());
		if(utils.isDamageable()) {
			if(damage < 0) damage = 0;
			utils.setDamage(damage);
		}
		item.setItemMeta(utils.getItemMeta());
		return item;
	}
	
	public static int getDamage(ItemMeta meta) {
		DamageUtils utils = new DamageUtils(meta);
		if(utils.isDamageable()) {
			return utils.getDamage();
		}
		return 0;
	}
}
