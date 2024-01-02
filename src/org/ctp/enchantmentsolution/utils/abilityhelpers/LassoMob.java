package org.ctp.enchantmentsolution.utils.abilityhelpers;

import java.util.Locale;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.config.Configurable;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.nms.MobNMS;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.persistence.PersistenceUtils;

public class LassoMob {

	private final EnchantmentWrapper enchantment;
	private ItemStack item;
	private Entity entity;
	private EntityType type;
	private String nbtData;
	private int entityID;

	public LassoMob(Entity entity, ItemStack item, EnchantmentWrapper enchantment) {
		if (item != null) setItem(item);
		setType(entity.getType());
		setEntity(entity);
		nbtData = MobNMS.getNBTData(entity, false);
		this.enchantment = enchantment;
	}

	public LassoMob(EnchantmentWrapper enchantment) {
		this.enchantment = enchantment;
	}

	public Entity spawnEntity(Location loc) {
		Entity e = loc.getWorld().spawnEntity(loc, type);
		MobNMS.setNBTData(e, nbtData);

		return e;
	}

	public void setConfig(Configurable config, int i) {
		setConfig(config, "lasso." + enchantment + ".", i);
	}

	public void setConfig(Configurable configurable, String location, int i) {
		YamlConfig config = configurable.getConfig();
		config.set(location + i + ".enchantment", enchantment.getKey().getKey().toLowerCase(Locale.ROOT));
		config.set(location + i + ".nbt_data", nbtData);
	}

	public ItemStack getItem() {
		return item;
	}

	protected void setItem(ItemStack item) {
		this.item = item;
		generateID();
	}

	public Entity getEntity() {
		return entity;
	}

	protected void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getNbtData() {
		return nbtData;
	}

	public void setNbtData(String nbtData) {
		this.nbtData = nbtData;
	}

	private void generateID() {
		int base = 100;
		for(LassoMob lasso: EnchantmentSolution.getLassoMobs(enchantment))
			if (lasso.getEntityID() > base) base = lasso.getEntityID();
		setEntityID(base + 1, true);
	}

	public boolean inItem(ItemStack attackItem) {
		return getItem() != null && getItem().equals(attackItem) || PersistenceUtils.getAnimalIDsFromItem(attackItem).contains(getEntityID());
	}

	public boolean inItem(ItemStack item, int entityID) {
		return getItem() != null && item.equals(getItem()) || entityID == getEntityID();
	}

	public void editProperties(Entity e, boolean b1) {
		try {
			e = MobNMS.setNBTData(e, nbtData);
			if (e instanceof Creature) {
				Creature creature = (Creature) e;
				if (b1) creature.setHealth(creature.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int getEntityID() {
		return entityID;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

	public void setEntityID(int entityID, boolean addLore) {
		this.entityID = entityID;
		if (addLore) PersistenceUtils.addAnimal(item, entityID);
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public String getLocation() {
		return "lasso." + enchantment.getKey().getKey() + "." + entityID;
	}

	public String getLocation(int i) {
		return "lasso." + enchantment.getKey().getKey() + "." + i;
	}

	@Override
	public String toString() {
		return "LassoMob[enchantment=" + enchantment.getKey().getKey() + ", entityID=" + entityID + ", nbtData=" + nbtData + ", entityType=" + type + "]";
	}

}
