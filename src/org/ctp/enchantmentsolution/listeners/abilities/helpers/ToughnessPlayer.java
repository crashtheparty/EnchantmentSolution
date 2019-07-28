package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import java.util.UUID;

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

public class ToughnessPlayer extends AbilityPlayer{
	
	private ItemStack[] contents = new ItemStack[4], previousContents = new ItemStack[4];

	public ToughnessPlayer(Player player, ItemStack[] contents) {
		super(player, null, DefaultEnchantments.TOUGHNESS);
		setContents(contents);
	}

	public void setContents(ItemStack[] contents) {
		this.previousContents = this.contents;
		
		for(int i = 0; i < contents.length; i++) {
			ItemStack item = contents[i];
			ItemStack previousItem = this.previousContents[i];
			
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
		
		this.contents = contents;
	}

	private void doEquip(ItemStack item, Slot slot) {
		if(Enchantments.hasEnchantment(item, getEnchantment())){
			int level = Enchantments.getLevel(item, DefaultEnchantments.TOUGHNESS);
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
			AttributeModifier modifier = new AttributeModifier(slot.getUUID(), slot.getName(), level, Operation.ADD_NUMBER);
			if(!a.getModifiers().contains(modifier)) {
				a.addModifier(modifier);
			}
			if(a.getValue() >= 20) {
				AdvancementUtils.awardCriteria(getPlayer(), ESAdvancement.GRAPHENE_ARMOR, "toughness");
			}
		}
	}

	private void doUnequip(ItemStack item, Slot slot) {
		if(Enchantments.hasEnchantment(item, getEnchantment())){
			int level = Enchantments.getLevel(item, DefaultEnchantments.TOUGHNESS);
			AttributeInstance a = getPlayer().getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
			AttributeModifier modifier = new AttributeModifier(slot.getUUID(), slot.getName(), level, Operation.ADD_NUMBER);
			if(a.getModifiers().contains(modifier)) {
				a.removeModifier(modifier);
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

	@Override
	protected void doEquip(ItemStack item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doUnequip(ItemStack item) {
		// TODO Auto-generated method stub
		
	}

}
