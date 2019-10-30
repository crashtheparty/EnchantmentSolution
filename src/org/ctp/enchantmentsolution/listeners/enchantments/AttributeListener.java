package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.Attributable;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ArmorEquipEvent;
import org.ctp.enchantmentsolution.events.AttributeEvent;
import org.ctp.enchantmentsolution.events.ItemEquipEvent;
import org.ctp.enchantmentsolution.events.potion.MagicGuardPotionEvent;
import org.ctp.enchantmentsolution.events.potion.PotionEventType;
import org.ctp.enchantmentsolution.events.potion.UnrestPotionEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.ItemSlotType;

public class AttributeListener extends Enchantmentable {

	private Map<Enchantment, Attributable[]> attributes = new HashMap<Enchantment, Attributable[]>();
	
	public AttributeListener() {
		attributes.put(RegisterEnchantments.ARMORED, new Attributable[] {Attributable.ARMORED} );
		attributes.put(RegisterEnchantments.QUICK_STRIKE, new Attributable[] {Attributable.QUICK_STRIKE} );
		attributes.put(RegisterEnchantments.LIFE, new Attributable[] {Attributable.LIFE} );
		attributes.put(RegisterEnchantments.GUNG_HO, new Attributable[] {Attributable.GUNG_HO} );
		attributes.put(RegisterEnchantments.TOUGHNESS, new Attributable[] {Attributable.TOUGHNESS_HELMET,
				Attributable.TOUGHNESS_CHESTPLATE, Attributable.TOUGHNESS_LEGGINGS, Attributable.TOUGHNESS_BOOTS} );
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onArmorEquip(ArmorEquipEvent event) {
		itemEquip(event.getPlayer(), event.getOldArmorPiece(), event.getType(), false);
		itemEquip(event.getPlayer(), event.getNewArmorPiece(), event.getType(), true);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onItemEquip(ItemEquipEvent event) {
		itemEquip(event.getPlayer(), event.getOldItem(), event.getSlot(), false);
		itemEquip(event.getPlayer(), event.getNewItem(), event.getSlot(), true);
	}
	
	private void itemEquip(Player player, ItemStack item, ItemSlotType type, boolean equip) {
		if(item != null && item.hasItemMeta()) {
			Iterator<Entry<Enchantment, Integer>> iterator = item.getItemMeta().getEnchants().entrySet().iterator();
			
			while(iterator.hasNext()) {
				Entry<Enchantment, Integer> entry = iterator.next();
				if(attributes.containsKey(entry.getKey())) {
					for(Attributable a : attributes.get(entry.getKey())) {
						if(a.getType() == type) {
							AttributeEvent attrEvent = new AttributeEvent(player, new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(entry.getKey()), 
									entry.getValue()), equip ? null : a.getAttrName(), equip ? a.getAttrName() : null);
							Bukkit.getPluginManager().callEvent(attrEvent);

							if(!attrEvent.isCancelled()) {
								if(equip) {
									a.addModifier(player, entry.getValue());
								} else {
									a.removeModifier(player);
								}
							}
							
							break;
						}
					}
				} else if (entry.getKey() == RegisterEnchantments.UNREST) {
					if(type == ItemSlotType.HELMET) {
						UnrestPotionEvent event = new UnrestPotionEvent(player, equip ? PotionEventType.ADD : PotionEventType.REMOVE);
						Bukkit.getPluginManager().callEvent(event);
	
						if(!event.isCancelled()) {
							if(equip) {
								if(player.getStatistic(Statistic.TIME_SINCE_REST) < 96000) {
									player.setStatistic(Statistic.TIME_SINCE_REST, 96000);
								}
								player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000000, 0, false, false), true);
							} else {
								player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 160, 0, false, false), true);
							}
						}
					}
				} else if (entry.getKey() == RegisterEnchantments.NO_REST) {
					if(type == ItemSlotType.HELMET) {
						if(equip) {
							if(player.getStatistic(Statistic.TIME_SINCE_REST) > 0) {
								player.setStatistic(Statistic.TIME_SINCE_REST, 0);
							}
						}
					}
				} else if (entry.getKey() == RegisterEnchantments.MAGIC_GUARD) {
					if(type == ItemSlotType.OFF_HAND) {
						if(equip) {
							for(PotionEffectType potionEffect : ESArrays.getBadPotions()) {
								if(player.hasPotionEffect(potionEffect)) {
									MagicGuardPotionEvent event = new MagicGuardPotionEvent(player, potionEffect);
									Bukkit.getPluginManager().callEvent(event);
									
									if(!event.isCancelled()) {
										player.removePotionEffect(potionEffect);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
