package org.ctp.enchantmentsolution.utils.abilityhelpers;

import java.util.Locale;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.config.Configurable;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.nms.MobNMS;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.persistence.PersistenceUtils;

public class AnimalMob {

	private ItemStack item;
	private Entity entity;
	private EntityType type;
	private String nbtData;
	private int entityID;

	public AnimalMob(Entity entity, ItemStack item) {
		if (item != null) setItem(item);
		setType(entity.getType());
		setEntity(entity);
		nbtData = MobNMS.getNBTData(entity, false);
	}

	protected AnimalMob(Entity entity, ItemStack item, boolean husbandry) {
		if (item != null) setItem(item);
		setType(entity.getType());
		setEntity(entity);
		nbtData = MobNMS.getNBTData(entity, husbandry);
	}

	protected AnimalMob() {

	}

	public Entity spawnEntity(Location loc) {
		Entity e = loc.getWorld().spawnEntity(loc, type);
		MobNMS.setNBTData(e, nbtData);

		return e;
	}

	public void setConfig(Configurable config, int i) {
		setConfig(config, "animals.", i);
	}

	public void setConfig(Configurable configurable, String location, int i) {
		YamlConfig config = configurable.getConfig();
		config.set(location + i + ".entity_type", entity.getType().name().toLowerCase(Locale.ROOT));
		config.set(location + i + ".nbt_data", nbtData);
	}

	public static AnimalMob createFromConfig(Configurable config, int i) {
		AnimalMob mob = new AnimalMob();

		mob.setNbtData(config.getString("animals." + i + ".nbt_data"));
		mob.setType(EntityType.valueOf(config.getString("animals." + i + ".entity_type").toUpperCase(Locale.ROOT)));
		config.getConfig().removeKey("animals." + i);

		EnchantmentSolution.addAnimals(mob);
		return mob;
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

	protected void setNbtData(String nbtData) {
		this.nbtData = nbtData;
	}

	private void generateID() {
		int base = 100;
		for(AnimalMob animal: EnchantmentSolution.getAnimals())
			if (animal.getEntityID() > base) base = animal.getEntityID();
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

	public static Entity setHusbandry(Creature entity) {
		AnimalMob animal = new AnimalMob(entity, null, true);
		if (entity instanceof Ageable) {
			Entity e = animal.spawnEntity(entity.getLocation());
			((Ageable) e).setBaby();
			return e;
		}
		return null;
	}

	public static boolean canAddMob(Entity entity, Enchantment enchantment) {
		if (enchantment == RegisterEnchantments.IRENES_LASSO) return entity instanceof WaterMob || entity instanceof Animals;
		return false;
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
}
