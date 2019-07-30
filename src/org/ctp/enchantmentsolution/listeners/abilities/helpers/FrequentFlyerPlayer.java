package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

public class FrequentFlyerPlayer extends AbilityPlayer{

	private int underLimit, aboveLimit, under, above;
	private boolean canFly;

	public FrequentFlyerPlayer(Player player, ItemStack item) {
		super(player, item, DefaultEnchantments.FREQUENT_FLYER);
		setPreviousItem(null);
		setItem(item);
	}
	
	public void setItem(ItemStack item) {
		boolean reset = false;
		
		if(item != null && getPreviousItem() != null && !item.toString().equalsIgnoreCase(getPreviousItem().toString())) {
			reset = true;
		}
		setItem(item, reset);
	}
	
	private void setItem(ItemStack item, boolean reset) {
		setPreviousItem(getItem());
		super.setItem(item);
		underLimit = 0;
		aboveLimit = 0;
		boolean fly = false;
		if(item != null && Enchantments.hasEnchantment(item, getEnchantment())) {
			int level = Enchantments.getLevel(item, getEnchantment());
			underLimit = level * 4 * 20;
			aboveLimit = level * 20;
			if(DamageUtils.getDamage(item.getItemMeta()) < 400) {
				fly = true;
			}
		}
		setCanFly(fly);
		if(reset) {
			under = underLimit;
			above = aboveLimit;
		}
	}
	
	public boolean canFly() {
		return canFly;
	}

	public void setCanFly(boolean canFly) {
		this.canFly = canFly || getPlayer().getGameMode().equals(GameMode.CREATIVE) 
				|| getPlayer().getGameMode().equals(GameMode.SPECTATOR);
		if(this.canFly || !getPlayer().hasPermission("enchantmentsolution.enable-flight")) {
			getPlayer().setAllowFlight(this.canFly);
			if(getPlayer().isFlying() && !this.canFly) {
				getPlayer().setFlying(false);
			}
		}
	}

	public int getUnder() {
		return under;
	}
	
	public void minus() {
		if(getPlayer().getLocation().getY() >= 12000) {
			AdvancementUtils.awardCriteria(getPlayer(), ESAdvancement.CRUISING_ALTITUDE, "elytra"); 
		}
		if(getPlayer().getLocation().getY() > 255) {
			above = above - 1;
			if(above <= 0) {
				double random = Math.random();
				double chance = (1.0D/(Enchantments.getLevel(getItem(), Enchantment.DURABILITY)+1));
				int durabilityChange = 1;
				if(chance < random) {
					durabilityChange = 0;
				}
				DamageUtils.setDamage(getItem(), (DamageUtils.getDamage(getItem().getItemMeta()) + durabilityChange));
				if(DamageUtils.getDamage(getItem().getItemMeta()) >= 400) {
					setCanFly(false);
				}
				above = aboveLimit;
				getPlayer().getInventory().setChestplate(getItem());
			}
		}else{
			under = under - 1;
			if(under <= 0) {
				double random = Math.random();
				double chance = (1.0D/(Enchantments.getLevel(getItem(), Enchantment.DURABILITY)+1));
				int durabilityChange = 1;
				if(chance < random) {
					durabilityChange = 0;
				}
				DamageUtils.setDamage(getItem(), (DamageUtils.getDamage(getItem().getItemMeta()) + durabilityChange));
				if(DamageUtils.getDamage(getItem().getItemMeta()) >= 400) {
					setCanFly(false);
				}
				getPlayer().getInventory().setChestplate(getItem());
				under = underLimit;
			}
		}
	}

	public int getAbove() {
		return above;
	}

	@Override
	protected void doEquip(ItemStack item) {
		
	}

	@Override
	protected void doUnequip(ItemStack item) {
		
	}

}
