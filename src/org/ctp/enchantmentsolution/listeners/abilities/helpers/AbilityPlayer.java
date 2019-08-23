package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public abstract class AbilityPlayer {

	private Player player;
	private ItemStack item, previousItem;
	private Enchantment enchantment;
	private boolean alwaysRefresh;
	
	public AbilityPlayer(Player player, ItemStack item, Enchantment enchantment) {
		this.player = player;
		this.enchantment = enchantment;
		this.setItem(item, true);
		alwaysRefresh = false;
	}
	
	public AbilityPlayer(Player player, ItemStack item, Enchantment enchantment, boolean alwaysRefresh) {
		this.player = player;
		this.enchantment = enchantment;
		this.setItem(item, true);
		this.setAlwaysRefresh(alwaysRefresh);
	}

	private void setAlwaysRefresh(boolean alwaysRefresh) {
		this.alwaysRefresh = alwaysRefresh;
	}
	
	public boolean willAlwaysRefresh() {
		return alwaysRefresh;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ItemStack getItem() {
		return item;
	}
	
	public void setItem(ItemStack item) {
		setItem(item, false);
	}

	public void setItem(ItemStack item, boolean constructor) {
		this.previousItem = this.item;
		if(item == null && previousItem != null){
			doUnequip(previousItem);
		}else if(item != null && previousItem == null){
			doEquip(item);
		}else if(previousItem != null && item != null){
			if (!item.toString().equalsIgnoreCase(
					previousItem.toString()) || willAlwaysRefresh()) {
				doUnequip(previousItem);
				doEquip(item);
			}
		}else if(item == null && previousItem == null && constructor) {
			doUnequip();
		}
		this.item = item;
	}

	public ItemStack getPreviousItem() {
		return previousItem;
	}

	public void setPreviousItem(ItemStack previousItem) {
		this.previousItem = previousItem;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public void setEnchantment(Enchantment enchantment) {
		this.enchantment = enchantment;
	}
	
	protected void addModifier(AttributeInstance instance, AttributeModifier modifier) {
		try {
			if (!hasExactAttribute(instance, modifier) && hasAttribute(instance, modifier)) {
				List<AttributeModifier> override = new ArrayList<AttributeModifier>();
				for (AttributeModifier m : instance.getModifiers()) {
					if(m.getUniqueId().equals(modifier.getUniqueId())) {
						override.add(m);
					}
				}
				for(AttributeModifier m : override) {
					AttributeModifier newModifier = new AttributeModifier(UUID.randomUUID(), m.getName(), m.getAmount(), m.getOperation(), m.getSlot());
					while(hasAttribute(instance, newModifier)) {
						newModifier = new AttributeModifier(UUID.randomUUID(), m.getName(), m.getAmount(), m.getOperation(), m.getSlot());
					}
					instance.getModifiers().remove(m);
					instance.getModifiers().add(newModifier);
				}
				if(modifier.getName().equals("armored")) {
					AdvancementUtils.awardCriteria(getPlayer(), ESAdvancement.ARMORED_EVOLUTION, "armored");
				}
				instance.addModifier(modifier);
			} else if (!hasAttribute(instance, modifier)) {
				if(modifier.getName().equals("armored")) {
					AdvancementUtils.awardCriteria(getPlayer(), ESAdvancement.ARMORED_EVOLUTION, "armored");
				}
				instance.addModifier(modifier);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected void removeModifier(AttributeInstance instance, AttributeModifier modifier) {
		try {
			if (hasExactAttribute(instance, modifier)) {
				instance.removeModifier(modifier);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private boolean hasExactAttribute(AttributeInstance instance, AttributeModifier modifier) {
		for(AttributeModifier m : instance.getModifiers()) {
			if(m.getUniqueId().equals(modifier.getUniqueId()) && m.getName().equals(modifier.getName())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasAttribute(AttributeInstance instance, AttributeModifier modifier) {
		for(AttributeModifier m : instance.getModifiers()) {
			if(m.getUniqueId().equals(modifier.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	
	protected abstract void doEquip(ItemStack item);
	
	protected abstract void doUnequip(ItemStack item);
	
	protected abstract void doUnequip();
}
