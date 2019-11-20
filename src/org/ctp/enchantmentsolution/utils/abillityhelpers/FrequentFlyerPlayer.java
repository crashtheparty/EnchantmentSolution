package org.ctp.enchantmentsolution.utils.abillityhelpers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.player.FrequentFlyerEvent;
import org.ctp.enchantmentsolution.events.player.FrequentFlyerEvent.FFType;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class FrequentFlyerPlayer {

	private Player player;
	private ItemStack elytra;
	private ItemStack previousElytra;
	private boolean canFly;
	private int underLimit, aboveLimit, under, above;

	public FrequentFlyerPlayer(Player player, ItemStack elytra) {
		this.player = player;
		this.setElytra(elytra, true);
	}

	public Player getPlayer() {
		return player;
	}

	public ItemStack getElytra() {
		return elytra;
	}

	public void setElytra(ItemStack elytra) {
		boolean reset = false;

		if (elytra != null && previousElytra != null
		&& !elytra.toString().equalsIgnoreCase(previousElytra.toString())) {
			reset = true;
		}
		setElytra(elytra, reset);
	}

	public void setElytra(ItemStack elytra, boolean reset) {
		previousElytra = this.elytra;
		this.elytra = elytra;
		underLimit = 0;
		aboveLimit = 0;
		boolean fly = false;
		if (elytra != null && ItemUtils.hasEnchantment(elytra, RegisterEnchantments.FREQUENT_FLYER)) {
			int level = ItemUtils.getLevel(elytra, RegisterEnchantments.FREQUENT_FLYER);
			underLimit = level * 4 * 20;
			aboveLimit = level * 20;
			if (DamageUtils.getDamage(elytra.getItemMeta()) < 400) {
				fly = true;
			}
		}
		setCanFly(fly);
		if (reset) {
			under = underLimit;
			above = aboveLimit;
		}
	}

	public boolean canFly() {
		return canFly;
	}

	public void setCanFly(boolean canFly) {
		boolean modifyCanFly = canFly || player.getGameMode().equals(GameMode.CREATIVE)
		|| player.getGameMode().equals(GameMode.SPECTATOR);
		FrequentFlyerEvent event = null;
		if (this.canFly && !modifyCanFly) {
			if (elytra != null && elytra.toString().equalsIgnoreCase(previousElytra.toString())
			&& DamageUtils.getDamage(elytra.getItemMeta()) >= 400) {
				event = new FrequentFlyerEvent(player, FFType.BREAK_ELYTRA);
			} else if (!player.hasPermission("enchantmentsolution.enable-flight")) {
				event = new FrequentFlyerEvent(player, FFType.REMOVE_FLIGHT);
			}
		} else if (!this.canFly && modifyCanFly) {
			event = new FrequentFlyerEvent(player, FFType.ALLOW_FLIGHT);
		}
		if (event != null) {
			Bukkit.getPluginManager().callEvent(event);
			if (!event.isCancelled()) {
				this.canFly = modifyCanFly;
				if (this.canFly || !player.hasPermission("enchantmentsolution.enable-flight")) {
					player.setAllowFlight(this.canFly);
					if (player.isFlying() && !this.canFly) {
						player.setFlying(false);
					}
				}
			}
		}
	}

	public int getUnder() {
		return under;
	}

	public void minus() {
		if (player.getLocation().getY() >= 12000) {
			AdvancementUtils.awardCriteria(player, ESAdvancement.CRUISING_ALTITUDE, "elytra");
		}
		if (player.getLocation().getY() > 255) {
			above = above - 1;
			if (above <= 0) {
				DamageUtils.damageItem(player, elytra);
				if (DamageUtils.getDamage(elytra.getItemMeta()) >= 400) {
					setCanFly(false);
				}
				above = aboveLimit;
				player.getInventory().setChestplate(elytra);
			}
		} else {
			under = under - 1;
			if (under <= 0) {
				DamageUtils.damageItem(player, elytra);
				if (DamageUtils.getDamage(elytra.getItemMeta()) >= 400) {
					setCanFly(false);
				}
				player.getInventory().setChestplate(elytra);
				under = underLimit;
			}
		}
	}

	public int getAbove() {
		return above;
	}

}