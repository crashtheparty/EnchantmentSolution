package org.ctp.enchantmentsolution.utils.player;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.ctp.crashapi.item.ItemSlot;
import org.ctp.crashapi.item.ItemSlotType;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffect;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffectType;

public class ESEntity {

	private LivingEntity entity;
	private ESPlayer player;
	private List<CustomPotionEffect> activeEffects;
	private HashMap<String, Object> customValues;

	public ESEntity(LivingEntity entity) {
		this.entity = entity;
		if (entity instanceof Player) player = EnchantmentSolution.getESPlayer((Player) entity);
		activeEffects = new ArrayList<CustomPotionEffect>();
		customValues = new HashMap<String, Object>();
	}

	public ItemStack getMainHand() {
		if (!(hasInventory())) return new ItemStack(Material.AIR);
		return getInventory().getItemInMainHand();
	}

	private boolean hasInventory() {
		return entity instanceof InventoryHolder && ((InventoryHolder) entity).getInventory() instanceof PlayerInventory;
	}

	private PlayerInventory getInventory() {
		return (PlayerInventory) ((InventoryHolder) entity).getInventory();
	}

	public ItemStack getOffhand() {
		if (!(hasInventory())) return new ItemStack(Material.AIR);
		return getInventory().getItemInOffHand();
	}

	public ItemStack[] getArmor() {
		ItemStack[] armor = new ItemStack[4];
		if (!(hasInventory())) return armor;
		armor[0] = getInventory().getHelmet();
		armor[1] = getInventory().getChestplate();
		armor[2] = getInventory().getLeggings();
		armor[3] = getInventory().getBoots();

		return armor;
	}

	public ItemSlot[] getArmorAndType() {
		ItemSlot[] armor = new ItemSlot[4];
		if (!(hasInventory())) return armor;
		armor[0] = new ItemSlot(getInventory().getHelmet(), ItemSlotType.HELMET);
		armor[1] = new ItemSlot(getInventory().getChestplate(), ItemSlotType.CHESTPLATE);
		armor[2] = new ItemSlot(getInventory().getLeggings(), ItemSlotType.LEGGINGS);
		armor[3] = new ItemSlot(getInventory().getBoots(), ItemSlotType.BOOTS);

		return armor;
	}

	public ItemStack[] getEquipped() {
		ItemStack[] equipped = new ItemStack[6];
		if (!(hasInventory())) return equipped;
		equipped[0] = getInventory().getHelmet();
		equipped[1] = getInventory().getChestplate();
		equipped[2] = getInventory().getLeggings();
		equipped[3] = getInventory().getBoots();
		equipped[4] = getInventory().getItemInMainHand();
		equipped[5] = getInventory().getItemInOffHand();

		return equipped;
	}

	public ItemSlot[] getEquippedAndType() {
		ItemSlot[] equipped = new ItemSlot[6];
		if (!(hasInventory())) return equipped;
		equipped[0] = new ItemSlot(getInventory().getHelmet(), ItemSlotType.HELMET);
		equipped[1] = new ItemSlot(getInventory().getChestplate(), ItemSlotType.CHESTPLATE);
		equipped[2] = new ItemSlot(getInventory().getLeggings(), ItemSlotType.LEGGINGS);
		equipped[3] = new ItemSlot(getInventory().getBoots(), ItemSlotType.BOOTS);
		equipped[4] = new ItemSlot(getInventory().getItemInMainHand(), ItemSlotType.MAIN_HAND);
		equipped[5] = new ItemSlot(getInventory().getItemInOffHand(), ItemSlotType.OFF_HAND);

		return equipped;
	}

	public void setEquippedAndType(ItemSlot[] slots) {
		if (!(hasInventory())) return;
		for(ItemSlot slot: slots) {
			// try to prevent causing issues with raids
			if (slot.getItem().getType() == Material.WHITE_BANNER) continue;
			entity.getEquipment().setItem(slot.getType().getEquipmentSlot(), slot.getItem());
		}
	}

	public ItemSlot[] getInventoryItemsAndType() {
		ItemSlot[] equipped = new ItemSlot[41];
		if (!(hasInventory())) return equipped;
		PlayerInventory inv = getInventory();

		for(int i = 0; i < 36; i++)
			if (i == inv.getHeldItemSlot()) equipped[i] = new ItemSlot(inv.getContents()[i], ItemSlotType.MAIN_HAND);
			else
				equipped[i] = new ItemSlot(inv.getContents()[i], ItemSlotType.getTypeFromSlot(i));
		equipped[36] = new ItemSlot(inv.getHelmet(), ItemSlotType.HELMET);
		equipped[37] = new ItemSlot(inv.getChestplate(), ItemSlotType.CHESTPLATE);
		equipped[38] = new ItemSlot(inv.getLeggings(), ItemSlotType.LEGGINGS);
		equipped[39] = new ItemSlot(inv.getBoots(), ItemSlotType.BOOTS);
		equipped[40] = new ItemSlot(inv.getItemInOffHand(), ItemSlotType.OFF_HAND);
		return equipped;
	}

	public ItemSlot[] getInventoryItemsAndType(ItemSlot item) {
		ItemSlot[] equipped = new ItemSlot[41];
		if (!(hasInventory())) return equipped;
		PlayerInventory inv = getInventory();

		for(int i = 0; i < 36; i++)
			if (i == inv.getHeldItemSlot()) {
				ItemStack it = inv.getContents()[i];
				if (item.getType() == ItemSlotType.MAIN_HAND) it = item.getItem();
				equipped[i] = new ItemSlot(it, ItemSlotType.MAIN_HAND);
			} else {
				ItemStack it = inv.getContents()[i];
				if (item.getType() == ItemSlotType.getTypeFromSlot(i)) it = item.getItem();
				equipped[i] = new ItemSlot(it, ItemSlotType.getTypeFromSlot(i));
			}
		equipped[36] = new ItemSlot(item.getType() == ItemSlotType.HELMET ? item.getItem() : inv.getHelmet(), ItemSlotType.HELMET);
		equipped[37] = new ItemSlot(item.getType() == ItemSlotType.CHESTPLATE ? item.getItem() : inv.getChestplate(), ItemSlotType.CHESTPLATE);
		equipped[38] = new ItemSlot(item.getType() == ItemSlotType.LEGGINGS ? item.getItem() : inv.getLeggings(), ItemSlotType.LEGGINGS);
		equipped[39] = new ItemSlot(item.getType() == ItemSlotType.BOOTS ? item.getItem() : inv.getBoots(), ItemSlotType.BOOTS);
		equipped[40] = new ItemSlot(item.getType() == ItemSlotType.OFF_HAND ? item.getItem() : inv.getItemInOffHand(), ItemSlotType.OFF_HAND);
		return equipped;
	}

	public CustomPotionEffect addCustomPotionEffect(CustomPotionEffect effect, boolean override) {
		if (activeEffects.size() == 0) {
			activeEffects.add(effect);
			return effect;
		} else {
			Iterator<CustomPotionEffect> iter = activeEffects.iterator();
			boolean shouldAdd = true;
			while (iter.hasNext()) {
				CustomPotionEffect active = iter.next();
				if (active.getType() == effect.getType() && active.getAmplifier() == effect.getAmplifier()) {
					shouldAdd = false;
					active.addDuration(effect);
				}
			}
			if (shouldAdd) {
				activeEffects.add(effect);
				return effect;
			}
		}
		return null;
	}

	public boolean removeCustomPotionEffect(CustomPotionEffect effect, int amplifier) {
		if (activeEffects.size() == 0) return false;
		else {
			boolean removed = false;
			Iterator<CustomPotionEffect> iter = activeEffects.iterator();
			while (iter.hasNext()) {
				CustomPotionEffect active = iter.next();
				if (active.getAmplifier() >= amplifier) {
					removed = true;
					iter.remove();
					CustomPotionEffect highestAmplifier = getHighestAmplifiers().get(effect.getType());
					if (highestAmplifier == null) active.removePotionEffect(entity);
				}
			}
			return removed;
		}
	}

	public Map<CustomPotionEffectType, CustomPotionEffect> getHighestAmplifiers() {
		Map<CustomPotionEffectType, CustomPotionEffect> highestAmplifier = new LinkedHashMap<CustomPotionEffectType, CustomPotionEffect>();
		Iterator<CustomPotionEffect> iter = activeEffects.iterator();
		while (iter.hasNext()) {
			CustomPotionEffect active = iter.next();
			CustomPotionEffect highest = highestAmplifier.get(active.getType());
			if (highest == null || highest.getAmplifier() < active.getAmplifier()) highestAmplifier.put(active.getType(), active);
		}
		return highestAmplifier;
	}

	public void runPotionEffects() {
		Map<CustomPotionEffectType, CustomPotionEffect> highestAmplifier = getHighestAmplifiers();

		Iterator<Entry<CustomPotionEffectType, CustomPotionEffect>> iter2 = highestAmplifier.entrySet().iterator();

		while (iter2.hasNext()) {
			Entry<CustomPotionEffectType, CustomPotionEffect> entry = iter2.next();
			entry.getValue().runDamage(entity);
		}

		Iterator<CustomPotionEffect> iter3 = activeEffects.iterator();
		while (iter3.hasNext()) {
			CustomPotionEffect active = iter3.next();
			active.run();
			if (active.getDuration() <= 0) iter3.remove();
		}
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public ESPlayer getPlayer() {
		return player;
	}

	public boolean hasCustomPotionEffects() {
		return activeEffects.size() > 0;
	}

	public boolean hasCustomPotionEffect(CustomPotionEffectType type) {
		for(CustomPotionEffect effect: activeEffects)
			if (effect.getType() == type) return true;
		return false;
	}

	public CustomPotionEffect getHighestPotionEffect(CustomPotionEffectType type) {
		Map<CustomPotionEffectType, CustomPotionEffect> highestAmplifier = getHighestAmplifiers();

		return highestAmplifier.get(type);
	}

	@SuppressWarnings("unchecked")
	public <E> E getCustomValue(Class<E> clazz, String field) {
		Object value = customValues.get(field);
		if (value == null || !clazz.isInstance(value)) return null;
		return (E) value;
	}

	public <E> void setCustomValue(String field, E value) {
		customValues.put(field, value);
	}

}
