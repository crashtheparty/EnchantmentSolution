package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class QuickStrikeListener implements Runnable{
	
	public static List<QuickStrikePlayer> QUICK_STRIKE = new ArrayList<QuickStrikePlayer>();
	
	private boolean isQuickStrike(Player player) {
		for(QuickStrikePlayer quickStrikePlayer : QUICK_STRIKE) {
			if(quickStrikePlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.QUICK_STRIKE)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!isQuickStrike(player)) {
				try {
					ItemStack weapon = player.getInventory().getItemInMainHand();
					QuickStrikePlayer quickStrikePlayer = new QuickStrikePlayer(player, weapon);
					QUICK_STRIKE.add(quickStrikePlayer);
				} catch(Exception ex) { }
			}
		}
		for(int i = QUICK_STRIKE.size() - 1; i >= 0; i--) {
			QuickStrikePlayer quickStrikePlayer = QUICK_STRIKE.get(i);
			Player player = quickStrikePlayer.getPlayer();
			if (player != null && Bukkit.getOnlinePlayers().contains(player)) {
				ItemStack weapon = player.getInventory().getItemInMainHand();
				quickStrikePlayer.setWeapon(weapon);
			} else {
				QUICK_STRIKE.remove(quickStrikePlayer);
			}
		}
	}
	protected class QuickStrikePlayer{
		
		private Player player;
		private ItemStack weapon;
		private ItemStack previousWeapon;
		
		public QuickStrikePlayer(Player player, ItemStack weapon) {
			this.player = player;
			this.setWeapon(weapon);
		}

		public Player getPlayer() {
			return player;
		}

		public ItemStack getWeapon() {
			return weapon;
		}

		public void setWeapon(ItemStack weapon) {
			previousWeapon = this.weapon;
			if(weapon == null && previousWeapon != null){
				doUnequip(previousWeapon);
			}else if(weapon != null && previousWeapon == null){
				doEquip(weapon);
			}else if(previousWeapon != null && weapon != null){
				if (!weapon.toString().equalsIgnoreCase(
						previousWeapon.toString())) {
					doUnequip(previousWeapon);
					doEquip(weapon);
				}
			}
			this.weapon = weapon;
		}

		private void doEquip(ItemStack item) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.QUICK_STRIKE)){
				int level = Enchantments.getLevel(item, DefaultEnchantments.QUICK_STRIKE);
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
				AttributeModifier modifier = new AttributeModifier(UUID.fromString("dddddddd-fefe-fefe-fefe-000000000000"), "quick_strike_armor", 
						0.5 * level, Operation.ADD_SCALAR);
				if(!a.getModifiers().contains(modifier)) {
					a.addModifier(modifier);
				}
			}
		}

		private void doUnequip(ItemStack item) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.QUICK_STRIKE)){
				int level = Enchantments.getLevel(item, DefaultEnchantments.QUICK_STRIKE);
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
				AttributeModifier modifier = new AttributeModifier(UUID.fromString("dddddddd-fefe-fefe-fefe-000000000000"), "quick_strike_armor", 
						0.5 * level, Operation.ADD_SCALAR);
				if(a.getModifiers().contains(modifier)) {
					a.removeModifier(modifier);
				}
			}
		}
		
	}

}
