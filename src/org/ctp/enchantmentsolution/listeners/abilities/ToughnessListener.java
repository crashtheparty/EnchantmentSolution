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
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class ToughnessListener implements Runnable{

	private static List<ToughnessPlayer> PLAYERS = new ArrayList<ToughnessPlayer>();
	
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.LIFE)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!contains(player)) {
				try {
					ToughnessPlayer toughnessPlayer = new ToughnessPlayer(player, player.getInventory().getArmorContents());
					PLAYERS.add(toughnessPlayer);
				} catch(Exception ex) { }
			}
		}
		for(int i = PLAYERS.size() - 1; i >= 0; i--) {
			ToughnessPlayer player = PLAYERS.get(i);
			Player p = player.getPlayer();
			if(p != null && Bukkit.getOnlinePlayers().contains(p)) {
				player.setContents(p.getInventory().getArmorContents());
			} else {
				PLAYERS.remove(player);
			}
		}
	}
	
	private boolean contains(Player player) {
		for(ToughnessPlayer toughness : PLAYERS) {
			if(toughness.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	
	protected static class ToughnessPlayer{
		
		private Player player;
		private ItemStack[] armorContents, previousArmorContents;
		
		public ToughnessPlayer(Player player, ItemStack[] contents) {
			this.player = player;
			this.armorContents = contents;
		}

		public Player getPlayer() {
			return player;
		}

		public ItemStack[] getContents() {
			return armorContents;
		}
		
		public void setContents(ItemStack[] contents) {
			this.previousArmorContents = this.armorContents;
			
			for(int i = 0; i < contents.length; i++) {
				ItemStack item = contents[i];
				ItemStack previousItem = this.previousArmorContents[i];
				
				Slot slot = null;
				switch(i) {
				case 0:
					slot = Slot.HELMET;
					break;
				case 1:
					slot = Slot.CHESTPLATE;
					break;
				case 2:
					slot = Slot.LEGGINGS;
					break;
				case 3:
					slot = Slot.BOOTS;
					break;
				}
				
				if(slot != null) {
					if(item == null && previousItem != null){
						doUnequip(previousItem, slot);
					}else if(item != null && previousItem == null){
						doEquip(item, slot);
					}else if(previousItem != null && item != null){
						if (!item.toString().equalsIgnoreCase(
								previousItem.toString())) {
							doUnequip(previousItem, slot);
							doEquip(item, slot);
						}
					}
				}
			}
			
			
			this.armorContents = contents;
		}

		private void doEquip(ItemStack item, Slot slot) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.TOUGHNESS)){
				int level = Enchantments.getLevel(item, DefaultEnchantments.TOUGHNESS);
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
				AttributeModifier modifier = new AttributeModifier(slot.getUUID(), slot.getName(), level, Operation.ADD_NUMBER);
				if(!a.getModifiers().contains(modifier)) {
					a.addModifier(modifier);
				}
				if(a.getValue() >= 20) {
					AdvancementUtils.awardCriteria(player, ESAdvancement.GRAPHENE_ARMOR, "toughness");
				}
			}
		}

		private void doUnequip(ItemStack item, Slot slot) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.TOUGHNESS)){
				int level = Enchantments.getLevel(item, DefaultEnchantments.TOUGHNESS);
				AttributeInstance a = player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
				AttributeModifier modifier = new AttributeModifier(slot.getUUID(), slot.getName(), level, Operation.ADD_NUMBER);
				if(a.getModifiers().contains(modifier)) {
					a.removeModifier(modifier);
				}
			}
		}
	}
	
	private enum Slot{
		HELMET(UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001000"), "helmet_toughness"), 
		CHESTPLATE(UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001100"), "chestplate_toughness"), 
		LEGGINGS(UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001110"), "leggings_toughness"), 
		BOOTS(UUID.fromString("bbbbbbbb-fefe-fefe-fefe-000000001111"), "boots_toughness");
		
		private UUID uuid;
		private String name;
		
		Slot(UUID uuid, String name){
			this.uuid = uuid;
			this.name = name;
		}
		
		public UUID getUUID() {
			return uuid;
		}
		
		public String getName() {
			return name;
		}
	}
}