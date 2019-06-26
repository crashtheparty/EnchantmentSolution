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

public class LifeListener implements Runnable{

	private static List<LifePlayer> PLAYERS = new ArrayList<LifePlayer>();
	
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.LIFE)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!contains(player)) {
				try {
					ItemStack chestplate = player.getInventory().getChestplate();
					LifePlayer lifePlayer = new LifePlayer(player, chestplate);
					PLAYERS.add(lifePlayer);
				} catch(Exception ex) { }
			}
		}
		for(int i = PLAYERS.size() - 1; i >= 0; i--) {
			LifePlayer player = PLAYERS.get(i);
			Player p = player.getPlayer();
			if(p != null && Bukkit.getOnlinePlayers().contains(p)) {
				ItemStack equips = p.getInventory().getChestplate();
				player.setChestplate(equips);
			} else {
				PLAYERS.remove(player);
			}
		}
	}
	
	private boolean contains(Player player) {
		for(LifePlayer life : PLAYERS) {
			if(life.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	
	protected static class LifePlayer{
		
		private Player player;
		private ItemStack chestplate, previousChestplate;
		
		public LifePlayer(Player player, ItemStack chestplate) {
			this.player = player;
			this.chestplate = chestplate;
		}

		public Player getPlayer() {
			return player;
		}

		public ItemStack getChestplate() {
			return chestplate;
		}
		
		public void setChestplate(ItemStack chestplate) {
			this.previousChestplate = this.chestplate;
			if(chestplate == null && previousChestplate != null){
				doUnequip(previousChestplate);
			}else if(chestplate != null && previousChestplate == null){
				doEquip(chestplate);
			}else if(previousChestplate != null && chestplate != null){
				if (!chestplate.toString().equalsIgnoreCase(
						previousChestplate.toString())) {
					doUnequip(previousChestplate);
					doEquip(chestplate);
				}
			}
			this.chestplate = chestplate;
		}

		private void doEquip(ItemStack item) {
			if(!Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO) && Enchantments.hasEnchantment(item, DefaultEnchantments.LIFE)){
				int level = Enchantments.getLevel(item, DefaultEnchantments.LIFE);
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
				AttributeModifier modifier = new AttributeModifier(UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000000000"), "life_health", 
						4 * level, Operation.ADD_NUMBER);
				AttributeModifier legacyModifier = new AttributeModifier(UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000000000"), "generic.maxHealth", 
						4 * level, Operation.ADD_NUMBER);
				if(a.getModifiers().contains(legacyModifier)) {
					a.removeModifier(legacyModifier);
				}
				if(!a.getModifiers().contains(modifier)) {
					a.addModifier(modifier);
				}
			}
		}

		private void doUnequip(ItemStack item) {
			if(!Enchantments.hasEnchantment(item, DefaultEnchantments.GUNG_HO) && Enchantments.hasEnchantment(item, DefaultEnchantments.LIFE)){
				int level = Enchantments.getLevel(item, DefaultEnchantments.LIFE);
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
				AttributeModifier modifier = new AttributeModifier(UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000000000"), "life_health", 
						4 * level, Operation.ADD_NUMBER);
				AttributeModifier legacyModifier = new AttributeModifier(UUID.fromString("eeeeeeee-fefe-fefe-fefe-000000000000"), "generic.maxHealth", 
						4 * level, Operation.ADD_NUMBER);
				if(a.getModifiers().contains(modifier)) {
					a.removeModifier(modifier);
				}
				if(a.getModifiers().contains(legacyModifier)) {
					a.removeModifier(legacyModifier);
				}
				if(player.getHealth() > a.getBaseValue()) {
					player.setHealth(a.getBaseValue());
				}
			}
		}
	}
}