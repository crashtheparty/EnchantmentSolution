package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class ArmoredListener implements Runnable{
	
	public static List<ArmoredPlayer> ARMORED = new ArrayList<ArmoredPlayer>();
	
	private boolean isArmored(Player player) {
		for(ArmoredPlayer armoredPlayer : ARMORED) {
			if(armoredPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.ARMORED)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!isArmored(player)) {
				try {
					ItemStack elytra = player.getInventory().getChestplate();
					if(elytra != null && !elytra.getType().equals(Material.ELYTRA)) {
						elytra = null;
					}
					ArmoredPlayer armoredPlayer = new ArmoredPlayer(player, elytra);
					ARMORED.add(armoredPlayer);
				} catch(Exception ex) { }
			}
		}
		for(int i = ARMORED.size() - 1; i >= 0; i--) {
			ArmoredPlayer armoredPlayer = ARMORED.get(i);
			Player player = armoredPlayer.getPlayer();
			if (player != null && Bukkit.getOnlinePlayers().contains(player)) {
				ItemStack elytra = player.getInventory().getChestplate();
				if (elytra != null && elytra.getType().equals(Material.ELYTRA)) {
					armoredPlayer.setElytra(elytra);
				} else {
					if((elytra == null || !elytra.getType().equals(Material.ELYTRA)) && !player.getGameMode().equals(GameMode.CREATIVE) 
							&& !player.getGameMode().equals(GameMode.SPECTATOR)) {
						armoredPlayer.setElytra(null);
					}
				}
			} else {
				ARMORED.remove(armoredPlayer);
			}
		}
	}
	protected class ArmoredPlayer{
		
		private Player player;
		private ItemStack elytra;
		private ItemStack previousElytra;
		
		public ArmoredPlayer(Player player, ItemStack elytra) {
			this.player = player;
			this.setElytra(elytra);
		}

		public Player getPlayer() {
			return player;
		}

		public ItemStack getElytra() {
			return elytra;
		}

		public void setElytra(ItemStack elytra) {
			previousElytra = this.elytra;
			if(elytra == null && previousElytra != null){
				doUnequip(previousElytra);
			}else if(elytra != null && previousElytra == null){
				doEquip(elytra);
			}else if(previousElytra != null && elytra != null){
				if (!elytra.toString().equalsIgnoreCase(
						previousElytra.toString())) {
					doUnequip(previousElytra);
					doEquip(elytra);
				}
			}
			this.elytra = elytra;
		}

		private void doEquip(ItemStack item) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.ARMORED)){
				int level = Enchantments.getLevel(item, DefaultEnchantments.ARMORED);
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_ARMOR);
				AttributeModifier modifier = new AttributeModifier(UUID.fromString("cccccccc-fefe-fefe-fefe-000000000000"), "armored_armor", 
						2 * level, Operation.ADD_NUMBER);
				if(!a.getModifiers().contains(modifier)) {
					a.addModifier(modifier);
					AdvancementUtils.awardCriteria(player, ESAdvancement.ARMORED_EVOLUTION, "armored");
				}
			}
		}

		private void doUnequip(ItemStack item) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.ARMORED)){
				int level = Enchantments.getLevel(item, DefaultEnchantments.ARMORED);
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_ARMOR);
				AttributeModifier modifier = new AttributeModifier(UUID.fromString("cccccccc-fefe-fefe-fefe-000000000000"), "armored_armor", 
						2 * level, Operation.ADD_NUMBER);
				if(a.getModifiers().contains(modifier)) {
					a.removeModifier(modifier);
				}
			}
		}
		
	}

}
