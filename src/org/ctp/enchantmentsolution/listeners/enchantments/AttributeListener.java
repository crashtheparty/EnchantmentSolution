package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.World.Environment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ctp.crashapi.events.ArmorEquipEvent;
import org.ctp.crashapi.events.ArmorEquipEvent.EquipMethod;
import org.ctp.crashapi.events.ItemAddEvent;
import org.ctp.crashapi.events.ItemEquipEvent;
import org.ctp.crashapi.events.ItemEquipEvent.HandMethod;
import org.ctp.crashapi.item.ItemSlot;
import org.ctp.crashapi.item.ItemSlotType;
import org.ctp.crashapi.nms.DamageEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.*;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.potion.*;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.threads.*;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ItemEquippedSlot;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class AttributeListener extends Enchantmentable {

	private Map<Enchantment, Attributable> attributes = new HashMap<Enchantment, Attributable>();
	private Map<Enchantment, PotionEffectType> potions = new HashMap<Enchantment, PotionEffectType>();

	public AttributeListener() {
		attributes.put(RegisterEnchantments.ARMORED, Attributable.ARMORED);
		attributes.put(RegisterEnchantments.QUICK_STRIKE, Attributable.QUICK_STRIKE);
		attributes.put(RegisterEnchantments.LIFE, Attributable.LIFE);
		attributes.put(RegisterEnchantments.GUNG_HO, Attributable.GUNG_HO);
		attributes.put(RegisterEnchantments.TOUGHNESS, Attributable.TOUGHNESS);

		potions.put(RegisterEnchantments.JOGGERS, PotionEffectType.SPEED);
		potions.put(RegisterEnchantments.PLYOMETRICS, PotionEffectType.JUMP);
		potions.put(RegisterEnchantments.WATER_BREATHING, PotionEffectType.WATER_BREATHING);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onArmorEquip(ArmorEquipEvent event) {
		itemEquip(event.getPlayer(), event.getOldArmorPiece(), event.getType(), false, event.getMethod() == EquipMethod.JOIN);
		itemEquip(event.getPlayer(), event.getNewArmorPiece(), event.getType(), true, event.getMethod() == EquipMethod.JOIN);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemEquip(ItemEquipEvent event) {
		itemEquip(event.getPlayer(), event.getOldItem(), event.getSlot(), false, event.getMethod() == HandMethod.JOIN);
		itemEquip(event.getPlayer(), event.getNewItem(), event.getSlot(), true, event.getMethod() == HandMethod.JOIN);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemAdd(ItemAddEvent event) {
		ItemStack item = event.getItem();
		if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_CONTAGION)) {
			ContagionCurseThread thread = ContagionCurseThread.createThread(event.getPlayer());
			if (!thread.isRunning()) {
				int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), thread, 0l, 1l);
				thread.setScheduler(scheduler);
			}
		}
	}

	private void itemEquip(Player player, ItemStack item, ItemSlotType type, boolean equip, boolean join) {
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		if (item != null && item.hasItemMeta()) {
			Iterator<EnchantmentLevel> iterator = EnchantmentUtils.getEnchantmentLevels(item).iterator();

			start: while (iterator.hasNext()) {
				EnchantmentLevel level = iterator.next();
				if (level == null || level.getEnchant() == null || !level.getEnchant().isEnabled() && equip) continue; // not an ES enchantment, or possibly a
				// disabled enchantment, so don't
				// bother
				Enchantment relative = level.getEnchant().getRelativeEnchantment();
				if (attributes.containsKey(relative)) {
					Attributable a = attributes.get(relative);
					for(ItemEquippedSlot slot: a.getTypes())
						if (slot.getType() == type) {
							Iterator<AttributeLevel> iter = esPlayer.getAttributes().iterator();
							if (equip) {
								if (a.getEnchantment() == RegisterEnchantments.LIFE) while (iter.hasNext()) {
									AttributeLevel attr = iter.next();
									Enchantment enchantment = attr.getAttribute().getEnchantment();
									if (enchantment == RegisterEnchantments.GUNG_HO) continue start;
								}
								else if (a.getEnchantment() == RegisterEnchantments.GUNG_HO) while (iter.hasNext()) {
									AttributeLevel attr = iter.next();
									Enchantment enchantment = attr.getAttribute().getEnchantment();
									if (enchantment == RegisterEnchantments.LIFE) {
										EnchantmentLevel attrLevel = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchantment), attr.getLevel());
										iter.remove();
										Attributable.removeAttribute(player, attrLevel, attr.getAttribute(), attr.getSlot());
										DamageEvent.updateHealth(player);
									}
								}
								else if (!a.doesAllowMultiple()) {
									boolean hasOther = false;
									while (iter.hasNext()) {
										AttributeLevel attr = iter.next();
										EnchantmentLevel attrLevel = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(attr.getAttribute().getEnchantment()), attr.getLevel());
										if (attr.getAttribute().getEnchantment() == a.getEnchantment() && level.getLevel() >= attr.getLevel()) {
											iter.remove();
											Attributable.removeAttribute(player, attrLevel, attr.getAttribute(), attr.getSlot());
										} else if (attr.getAttribute().getEnchantment() == a.getEnchantment()) hasOther = true;
									}
									if (hasOther) return;
								}
								Attributable.addAttribute(player, level, a, slot);
							} else {
								Attributable.removeAttribute(player, level, a, slot);

								if (a.getEnchantment() == RegisterEnchantments.GUNG_HO) {
									ItemSlot gungHo = null;
									List<ItemSlot> life = new ArrayList<ItemSlot>();

									for(ItemSlotType s: ItemSlotType.ARMOR) {
										if (s == slot.getType()) continue;
										ItemStack armor = esPlayer.getEquipped()[s.getSlot() - 5];
										if (EnchantmentUtils.hasEnchantment(armor, RegisterEnchantments.GUNG_HO)) {
											gungHo = new ItemSlot(armor, s);
											break;
										} else if (EnchantmentUtils.hasEnchantment(armor, RegisterEnchantments.LIFE)) life.add(new ItemSlot(armor, s));
									}
									if (gungHo != null) {
										Attributable gh = Attributable.GUNG_HO;
										for(ItemEquippedSlot s: gh.getTypes())
											if (gungHo.getType() == s.getType()) {
												Enchantment ench = gh.getEnchantment();
												EnchantmentLevel enchLevel = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(ench), EnchantmentUtils.getLevel(gungHo.getItem(), ench));
												Attributable.addAttribute(player, enchLevel, gh, s);
											}
									} else if (life.size() > 0) {
										Attributable l = Attributable.LIFE;
										for(ItemSlot is: life)
											for(ItemEquippedSlot s: l.getTypes()) {
												Enchantment ench = l.getEnchantment();
												EnchantmentLevel enchLevel = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(ench), EnchantmentUtils.getLevel(is.getItem(), ench));
												if (is.getType() == s.getType()) Attributable.addAttribute(player, enchLevel, l, s);
											}
									}
								} else if (!a.doesAllowMultiple()) {
									ItemSlot ench = null;
									for(ItemEquippedSlot s: a.getTypes()) {
										if (s.getType() == slot.getType()) continue;
										int slotNum = s.getType().getSlot() - 5;
										if (slotNum > 10 && s.getType() == ItemSlotType.MAIN_HAND) slotNum = player.getInventory().getHeldItemSlot();
										if (slotNum > 10 && s.getType() == ItemSlotType.OFF_HAND) slotNum = 40;
										ItemStack armor = esPlayer.getEquipped()[slotNum];
										Enchantment enchant = a.getEnchantment();
										if (EnchantmentUtils.hasEnchantment(armor, enchant) && (ench == null || EnchantmentUtils.getLevel(ench.getItem(), enchant) > EnchantmentUtils.getLevel(armor, enchant))) ench = new ItemSlot(armor, s.getType());
									}
									if (ench != null) {
										Attributable att = Attributable.valueOf(a.getEnchantment().getKey().getKey().toUpperCase(Locale.ROOT));
										for(ItemEquippedSlot s: att.getTypes()) {
											Enchantment enchantment = att.getEnchantment();
											EnchantmentLevel enchLevel = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(enchantment), EnchantmentUtils.getLevel(ench.getItem(), enchantment));
											if (ench.getType() == s.getType()) Attributable.addAttribute(player, enchLevel, att, s);
										}
									}
								}
							}
						}
				} else if (level.getEnchant() == CERegister.UNREST) {
					if (type == ItemSlotType.HELMET) {
						UnrestPotionEvent event = new UnrestPotionEvent(player, equip ? PotionEventType.ADD : PotionEventType.REMOVE);
						Bukkit.getPluginManager().callEvent(event);
						if (!event.isCancelled() && equip) {
							if (player.getStatistic(Statistic.TIME_SINCE_REST) < 96000) player.setStatistic(Statistic.TIME_SINCE_REST, 96000);
							player.removePotionEffect(PotionEffectType.NIGHT_VISION);
							player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000000, 0, false, false));
						} else if (!event.isCancelled() && !equip) {
							player.removePotionEffect(PotionEffectType.NIGHT_VISION);
							player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 160, 0, false, false));
						}
					}
				} else if (level.getEnchant() == CERegister.NO_REST) {
					if (type == ItemSlotType.HELMET && equip) {
						if (player.getStatistic(Statistic.TIME_SINCE_REST) > 72000 && player.getWorld().getEnvironment() == Environment.NORMAL && player.getWorld().getTime() > 12540 && player.getWorld().getTime() < 23459) AdvancementUtils.awardCriteria(player, ESAdvancement.COFFEE_BREAK, "coffee");
						if (player.getStatistic(Statistic.TIME_SINCE_REST) > 0) player.setStatistic(Statistic.TIME_SINCE_REST, 0);
					}
				} else if (level.getEnchant() == CERegister.MAGIC_GUARD && type == ItemSlotType.OFF_HAND && equip) {
					for(PotionEffectType potionEffect: ESArrays.getBadPotions())
						if (player.hasPotionEffect(potionEffect)) {
							MagicGuardPotionEvent event = new MagicGuardPotionEvent(player, potionEffect);
							Bukkit.getPluginManager().callEvent(event);

							if (!event.isCancelled()) player.removePotionEffect(potionEffect);
						} else { /* placeholder */ }

					MagicGuardThread thread = MagicGuardThread.createThread(player);
					if (!thread.isRunning()) {
						int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), thread, 0l, 1l);
						thread.setScheduler(scheduler);
					}
				} else if (ItemSlotType.ARMOR.contains(type) && potions.containsKey(relative)) {
					PotionEffectType effectType = potions.get(relative);
					PotionEffectEvent event = null;
					if (equip) event = new PotionEffectArmorAddEvent(player, level, effectType);
					else
						event = new PotionEffectArmorRemoveEvent(player, level, effectType);
					Bukkit.getPluginManager().callEvent(event);

					if (equip) player.addPotionEffect(new PotionEffect(event.getType(), 10000000, level.getLevel() - 1));
					else
						player.removePotionEffect(event.getType());
				} else if (relative == RegisterEnchantments.FREQUENT_FLYER && equip) {
					FrequentFlyerThread thread = FrequentFlyerThread.createThread(player);
					if (!thread.isRunning()) {
						int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), thread, 0l, 1l);
						thread.setScheduler(scheduler);
					}
				} else if (relative == RegisterEnchantments.FORCE_FEED && equip) {
					ForceFeedThread thread = ForceFeedThread.createThread(player);
					if (!thread.isRunning()) {
						int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), thread, 0l, 1l);
						thread.setScheduler(scheduler);
					}
				} else if (relative == RegisterEnchantments.CURSE_OF_EXHAUSTION && equip) {
					ExhaustionCurseThread thread = ExhaustionCurseThread.createThread(player);
					if (!thread.isRunning()) {
						int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), thread, 0l, 1l);
						thread.setScheduler(scheduler);
					}
				} else if (relative == RegisterEnchantments.CURSE_OF_INSTABILITY && equip) {
					InstabilityCurseThread thread = InstabilityCurseThread.createThread(player);
					if (!thread.isRunning()) {
						int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), thread, 0l, 1l);
						thread.setScheduler(scheduler);
					}
				}
			}
		}
	}
}
