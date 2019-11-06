package org.ctp.enchantmentsolution.utils.items;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class DamageUtils {

	private ItemMeta meta;
	private int damage;
	private boolean damageable = false;

	private DamageUtils(ItemMeta item) {
		meta = item;
		if (item instanceof Damageable && !item.isUnbreakable()) {
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
		if (utils.isDamageable()) {
			if (damage < 0) {
				damage = 0;
			}
			utils.setDamage(damage);
		}
		item.setItemMeta(utils.getItemMeta());
		return item;
	}

	public static int getDamage(ItemMeta meta) {
		DamageUtils utils = new DamageUtils(meta);
		if (utils.isDamageable()) {
			return utils.getDamage();
		}
		return 0;
	}

	public static ItemStack damageItem(HumanEntity player, ItemStack item) {
		return damageItem(player, item, 1.0D, 1.0D);
	}

	public static ItemStack damageItem(HumanEntity player, ItemStack item, double damage) {
		return damageItem(player, item, damage, 1.0D);
	}

	public static ItemStack damageItem(HumanEntity player, ItemStack item, double damage, double extraChance) {
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
			return null;
		}
		int numBreaks = 0;
		int unbreaking = ItemUtils.getLevel(item, Enchantment.DURABILITY);
		for(int i = 0; i < damage; i++) {
			double chance = (1.0D) / (unbreaking + extraChance);
			double random = Math.random();
			if (chance > random) {
				numBreaks++;
			}
		}
		;
		if (numBreaks > 0) {
			DamageUtils.setDamage(item, DamageUtils.getDamage(item.getItemMeta()) + numBreaks);
			if (DamageUtils.getDamage(item.getItemMeta()) > item.getType().getMaxDurability()) {
				ItemStack deadItem = item.clone();
				PlayerItemBreakEvent event = new PlayerItemBreakEvent((Player) player, item);
				Bukkit.getPluginManager().callEvent(event);
				player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				player.getWorld().spawnParticle(Particle.BLOCK_CRACK, player.getEyeLocation(), 1, item.getType());
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
				if (player instanceof Player) {
					Player p = (Player) player;
					p.incrementStatistic(Statistic.BREAK_ITEM, item.getType());
				}
				return deadItem;
			}
		}
		return item;
	}
}
