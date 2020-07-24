package org.ctp.enchantmentsolution.utils.items;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class DamageUtils {

	private ItemStack item;
	private int damage;
	private boolean damageable = false;
	private ItemMeta meta;

	private DamageUtils(ItemStack item) {
		this.item = item;
		meta = item.getItemMeta();
		if (meta != null && meta instanceof Damageable && !meta.isUnbreakable() && getMaxDamage(item) > 0) {
			damageable = true;
			damage = ((Damageable) meta).getDamage();
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

	private ItemStack returnItem() {
		item.setItemMeta(meta);
		return item;
	}

	private static boolean isDamageable(ItemStack item) {
		DamageUtils utils = new DamageUtils(item);
		return utils.isDamageable();
	}

	public static ItemStack setDamage(ItemStack item, int damage) {
		DamageUtils utils = new DamageUtils(item);
		if (utils.isDamageable()) {
			if (damage < 0) damage = 0;
			utils.setDamage(damage);
		}
		return utils.returnItem();
	}

	public static int getDamage(ItemStack item) {
		DamageUtils utils = new DamageUtils(item);
		if (utils.isDamageable()) return utils.getDamage();
		return 0;
	}

	public static int getMaxDamage(ItemStack item) {
		return item.getType().getMaxDurability();
	}

	public static boolean aboveMaxDamage(ItemStack item) {
		return new DamageUtils(item).getDamage() >= getMaxDamage(item);
	}

	public static int damageItem(HumanEntity player, ItemStack item) {
		return damageItem(player, item, 1.0D, 1.0D, true);
	}

	public static int damageItem(HumanEntity player, ItemStack item, double damage) {
		return damageItem(player, item, damage, 1.0D, true);
	}

	public static int damageItem(HumanEntity player, ItemStack item, double damage, double extraChance) {
		return damageItem(player, item, damage, extraChance, true);
	}

	public static int damageItem(HumanEntity player, ItemStack item, double damage, double extraChance,
	boolean breakItem) {
		if (item == null) throw new NullPointerException("The ES dev let you damage a null item, huh? Cool, but you shouldn't be doing that.");
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) || !DamageUtils.isDamageable(item)) return 0;
		int originalDamage = DamageUtils.getDamage(item);
		int numBreaks = 0;
		int unbreaking = ItemUtils.getLevel(item, Enchantment.DURABILITY);
		for(int i = 0; i < damage; i++) {
			double chance = 1.0D / (unbreaking + extraChance);
			double random = Math.random();
			if (chance > random) numBreaks++;
		}

		if (numBreaks > 0) {
			if (breakItem) {
				if (player instanceof Player) {
					PlayerItemDamageEvent event = new PlayerItemDamageEvent((Player) player, item, numBreaks);
					Bukkit.getPluginManager().callEvent(event);
					if (event.isCancelled()) return 0;
					numBreaks = event.getDamage();
				}
				DamageUtils.setDamage(item, DamageUtils.getDamage(item) + numBreaks);
				if (DamageUtils.aboveMaxDamage(item) && item.getType() != Material.ELYTRA) {
					PlayerItemBreakEvent event = new PlayerItemBreakEvent((Player) player, item);
					Bukkit.getPluginManager().callEvent(event);
					event.getBrokenItem().setAmount(0);
					DamageUtils.setDamage(event.getBrokenItem(), 0);
					player.getWorld().spawnParticle(Particle.ITEM_CRACK, player.getEyeLocation(), 1, item);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
					if (player instanceof Player) {
						Player p = (Player) player;
						p.incrementStatistic(Statistic.BREAK_ITEM, item.getType());
					}
					return DamageUtils.getMaxDamage(item) - originalDamage;
				} else if (item.getType() == Material.ELYTRA) return DamageUtils.getMaxDamage(item) - originalDamage;
			}
			return numBreaks;
		}
		return 0;
	}
}
