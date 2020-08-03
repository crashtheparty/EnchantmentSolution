package org.ctp.enchantmentsolution.nms.animalmob;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Parrot.Variant;
import org.bukkit.entity.TropicalFish.Pattern;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.config.Configurable;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.nms.PersistenceNMS;

public class AnimalMob {

	private EntityType mob;
	private String name, owner;
	private double health, jumpStrength, movementSpeed, maxHealth;
	private ItemStack item;
	private int age, entityID, domestication, maxDomestication, llamaStrength, puffState;
	private DyeColor sheepColor, wolfCollar, tropicalBodyColor, tropicalPatternColor;
	private boolean isHorse, carryingChest, pigSaddle, sheared, angry, tamed;
	private Map<Integer, ItemStack> inventoryItems;
	private ItemStack saddle, armor;
	private Color horseColor;
	private org.bukkit.entity.Llama.Color llamaColor;
	private Style horseStyle;
	private Variant parrotVariant;
	private org.bukkit.entity.Rabbit.Type rabbitType;
	private Pattern tropicalPattern;

	public AnimalMob(Creature mob, ItemStack item) {
		if (item != null) setItem(item);
		setMob(mob.getType());
		setName(mob.getCustomName());
		setHealth(mob.getHealth());
		if (mob instanceof Animals) setAge(((Animals) mob).getAge());
		if (mob instanceof PufferFish) {
			PufferFish pufferFish = (PufferFish) mob;
			setPuffState(pufferFish.getPuffState());
		}
		if (mob instanceof TropicalFish) {
			TropicalFish tropicalFish = (TropicalFish) mob;
			setTropicalBodyColor(tropicalFish.getBodyColor());
			setTropicalPatternColor(tropicalFish.getPatternColor());
			setTropicalPattern(tropicalFish.getPattern());
		}
		if (mob instanceof Sheep) {
			Sheep sheep = (Sheep) mob;
			setSheepColor(sheep.getColor());
			setSheared(sheep.isSheared());
		}
		if (mob instanceof Parrot) {
			Parrot parrot = (Parrot) mob;
			setParrotVariant(parrot.getVariant());
		}
		if (mob instanceof Tameable) {
			Tameable tameable = (Tameable) mob;
			setTamed(tameable.isTamed());
			if (tameable.getOwner() != null) setOwner(tameable.getOwner().getUniqueId().toString());
		}
		if (mob instanceof Pig) {
			Pig pig = (Pig) mob;
			setPigSaddle(pig.hasSaddle());
		}
		if (mob instanceof Wolf) {
			Wolf wolf = (Wolf) mob;
			setWolfCollar(wolf.getCollarColor());
			setAngry(wolf.isAngry());
		}
		if (mob instanceof Rabbit) {
			Rabbit rabbit = (Rabbit) mob;
			setRabbitType(rabbit.getRabbitType());
		}
		if (mob instanceof AbstractHorse) {
			inventoryItems = new HashMap<Integer, ItemStack>();
			AbstractHorse aHorse = (AbstractHorse) mob;
			setDomestication(aHorse.getDomestication());
			setMaxDomestication(aHorse.getMaxDomestication());
			setJumpStrength(aHorse.getJumpStrength());
			setMovementSpeed(aHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());
			setMaxHealth(aHorse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
			setSaddle(aHorse.getInventory().getSaddle());
			if (mob instanceof Horse) {
				Horse horse = (Horse) mob;
				setHorseColor(horse.getColor());
				setHorseStyle(horse.getStyle());
				setArmor(horse.getInventory().getArmor());
			}
			if (mob instanceof Llama) {
				Llama llama = (Llama) mob;
				setLlamaColor(llama.getColor());
				setLlamaStrength(llama.getStrength());
			}
			if (aHorse instanceof ChestedHorse) {
				ChestedHorse cHorse = (ChestedHorse) aHorse;
				setCarryingChest(cHorse.isCarryingChest());
				for(int i = 2; i < cHorse.getInventory().getSize(); i++)
					inventoryItems.put(i, cHorse.getInventory().getItem(i));
			}
		}
	}

	protected AnimalMob() {

	}

	public void setConfig(Configurable config, int i) {
		setConfig(config, "animals.", i);
	}

	public void setConfig(Configurable configurable, String location, int i) {
		YamlConfig config = configurable.getConfig();
		config.set(location + i + ".entity_type", getMob().name());
		config.set(location + i + ".name", getName());
		config.set(location + i + ".age", getAge());
		config.set(location + i + ".health", getHealth());
		config.set(location + i + ".entity_id", getEntityID());
		config.set(location + i + ".color", getSheepColor() != null ? getSheepColor().toString() : null);
		config.set(location + i + ".owner", getOwner());
		config.set(location + i + ".domestication", getDomestication());
		config.set(location + i + ".max_domestication", getMaxDomestication());
		config.set(location + i + ".tamed", isTamed());
		config.set(location + i + ".jump_strength", getJumpStrength());
		config.set(location + i + ".movement_speed", getMovementSpeed());
		config.set(location + i + ".max_health", getMaxHealth());
		config.set(location + i + ".carrying_chest", isCarryingChest());
		config.set(location + i + ".jump_strength", getJumpStrength());
		config.set(location + i + ".llama_strength", getLlamaStrength());
		config.set(location + i + ".pig_saddle", hasPigSaddle());
		config.set(location + i + ".sheared", isSheared());
		config.set(location + i + ".puff_state", getPuffState());
		config.set(location + i + ".sheep_color", getSheepColor() != null ? getSheepColor().name() : null);
		config.set(location + i + ".wolf_collar", getWolfCollar() != null ? getWolfCollar().name() : null);
		config.set(location + i + ".horse_style", getHorseStyle() != null ? getHorseStyle().name() : null);
		config.set(location + i + ".horse_color", getHorseColor() != null ? getHorseColor().name() : null);
		config.set(location + i + ".llama_color", getLlamaColor() != null ? getLlamaColor().name() : null);
		config.set(location + i + ".rabbit_type", getRabbitType() != null ? getRabbitType().name() : null);
		config.set(location + i + ".tropical_body_color", getTropicalBodyColor() != null ? getTropicalBodyColor().name() : null);
		config.set(location + i + ".tropical_pattern_color", getTropicalPatternColor() != null ? getTropicalPatternColor().name() : null);
		config.set(location + i + ".tropical_pattern", getTropicalPattern() != null ? getTropicalPattern().name() : null);
		config.set(location + i + ".parrot_variant", getParrotVariant() != null ? getParrotVariant().name() : null);
		config.set(location + i + ".saddle", getSaddle());
		config.set(location + i + ".armor", getArmor());

		for(int k = 2; k < 17; k++)
			if (inventoryItems != null && inventoryItems.get(k) != null) config.set("animals." + i + ".inventory_items." + k, inventoryItems.get(k));
	}

	public void editProperties(Entity e, boolean b1, boolean b2) {
		try {
			e.setCustomName(getName());
			if (e instanceof Creature) {
				Creature creature = (Creature) e;
				if (creature instanceof Ageable && b2) {
					Ageable a = (Ageable) creature;
					a.setBaby();
				}
				if (creature instanceof PufferFish) ((PufferFish) creature).setPuffState(puffState);
				if (creature instanceof TropicalFish) {
					if (tropicalBodyColor == null) tropicalBodyColor = DyeColor.WHITE;
					if (tropicalPatternColor == null) tropicalPatternColor = DyeColor.WHITE;
					if (tropicalPattern == null) tropicalPattern = Pattern.SUNSTREAK;
					((TropicalFish) creature).setBodyColor(tropicalBodyColor);
					((TropicalFish) creature).setPatternColor(tropicalPatternColor);
					((TropicalFish) creature).setPattern(tropicalPattern);
				}
				if (creature instanceof Sheep) {
					if (sheepColor == null) sheepColor = DyeColor.WHITE;
					((Sheep) creature).setColor(sheepColor);
					((Sheep) creature).setSheared(isSheared());
				}
				if (creature instanceof Parrot) {
					if (parrotVariant == null) parrotVariant = Variant.RED;
					((Parrot) creature).setVariant(parrotVariant);
				}
				if (creature instanceof Tameable) {
					Tameable tameable = (Tameable) creature;
					tameable.setTamed(isTamed());
					if (owner != null) {
						Entity eOwner = Bukkit.getEntity(UUID.fromString(owner));
						if (eOwner != null && eOwner instanceof AnimalTamer) tameable.setOwner((AnimalTamer) eOwner);
					}
				}
				if (creature instanceof Pig) {
					Pig pig = (Pig) creature;
					pig.setSaddle(hasPigSaddle());
				}
				if (creature instanceof Wolf) {
					Wolf wolf = (Wolf) creature;
					wolf.setCollarColor(getWolfCollar());
					wolf.setAngry(isAngry());
				}
				if (creature instanceof Rabbit) {
					Rabbit rabbit = (Rabbit) creature;
					rabbit.setRabbitType(getRabbitType());
				}
				if (creature instanceof AbstractHorse) {
					AbstractHorse aHorse = (AbstractHorse) creature;
					if (!b2) aHorse.getInventory().setSaddle(getSaddle());
					if (creature instanceof Horse) {
						Horse horse = (Horse) creature;
						horse.setColor(getHorseColor());
						horse.setStyle(getHorseStyle());
						horse.getInventory().setArmor(getArmor());
					}
					if (creature instanceof Llama) {
						Llama llama = (Llama) creature;
						llama.setColor(getLlamaColor());
						llama.setStrength(getLlamaStrength());
					}
					aHorse.setDomestication(getDomestication());
					aHorse.setJumpStrength(getJumpStrength());
					aHorse.setMaxDomestication(getMaxDomestication());
					aHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(getMovementSpeed());
					aHorse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(getMaxHealth());
					if (aHorse instanceof ChestedHorse) {
						ChestedHorse cHorse = (ChestedHorse) aHorse;
						if (isCarryingChest() && !b2) {
							cHorse.setCarryingChest(isCarryingChest());
							for(int i = 2; i < inventoryItems.size(); i++)
								cHorse.getInventory().setItem(i, inventoryItems.get(i));
						}
					}
				}
				if (b1) creature.setHealth(getHealth());
				if (creature instanceof Animals) ((Animals) creature).setAge(getAge());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void generateID() {
		int base = 100;
		for(AnimalMob animal: EnchantmentSolution.getAnimals())
			if (animal.getEntityID() > base) base = animal.getEntityID();
		setEntityID(base + 1, true);
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
		generateID();
	}

	public EntityType getMob() {
		return mob;
	}

	public void setMob(EntityType mob) {
		this.mob = mob;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getEntityID() {
		return entityID;
	}

	public void setEntityID(int entityID, boolean addLore) {
		this.entityID = entityID;
		if (addLore) PersistenceNMS.addAnimal(item, entityID);
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public double getJumpStrength() {
		return jumpStrength;
	}

	public void setJumpStrength(double jumpStrength) {
		this.jumpStrength = jumpStrength;
	}

	public int getDomestication() {
		return domestication;
	}

	public void setDomestication(int domestication) {
		this.domestication = domestication;
	}

	public boolean isHorse() {
		return isHorse;
	}

	public void setHorse(boolean isHorse) {
		this.isHorse = isHorse;
	}

	public boolean isCarryingChest() {
		return carryingChest;
	}

	public void setCarryingChest(boolean carryingChest) {
		this.carryingChest = carryingChest;
	}

	public double getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(double movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}

	public Map<Integer, ItemStack> getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(Map<Integer, ItemStack> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

	public Color getHorseColor() {
		return horseColor;
	}

	public void setHorseColor(Color horseColor) {
		this.horseColor = horseColor;
	}

	public Style getHorseStyle() {
		return horseStyle;
	}

	public void setHorseStyle(Style horseStyle) {
		this.horseStyle = horseStyle;
	}

	public org.bukkit.entity.Llama.Color getLlamaColor() {
		return llamaColor;
	}

	public void setLlamaColor(org.bukkit.entity.Llama.Color llamaColor) {
		this.llamaColor = llamaColor;
	}

	public int getMaxDomestication() {
		return maxDomestication;
	}

	public void setMaxDomestication(int maxDomestication) {
		this.maxDomestication = maxDomestication;
	}

	public ItemStack getSaddle() {
		return saddle;
	}

	public void setSaddle(ItemStack saddle) {
		this.saddle = saddle;
	}

	public ItemStack getArmor() {
		return armor;
	}

	public void setArmor(ItemStack armor) {
		this.armor = armor;
	}

	public DyeColor getSheepColor() {
		return sheepColor;
	}

	public void setSheepColor(DyeColor sheepColor) {
		this.sheepColor = sheepColor;
	}

	public Variant getParrotVariant() {
		return parrotVariant;
	}

	public void setParrotVariant(Variant parrotVariant) {
		this.parrotVariant = parrotVariant;
	}

	public int getLlamaStrength() {
		return llamaStrength;
	}

	public void setLlamaStrength(int llamaStrength) {
		this.llamaStrength = llamaStrength;
	}

	public boolean hasPigSaddle() {
		return pigSaddle;
	}

	public void setPigSaddle(boolean pigSaddle) {
		this.pigSaddle = pigSaddle;
	}

	public DyeColor getWolfCollar() {
		return wolfCollar;
	}

	public void setWolfCollar(DyeColor wolfCollar) {
		this.wolfCollar = wolfCollar;
	}

	public boolean isAngry() {
		return angry;
	}

	public void setAngry(boolean angry) {
		this.angry = angry;
	}

	public boolean isSheared() {
		return sheared;
	}

	public void setSheared(boolean sheared) {
		this.sheared = sheared;
	}

	public org.bukkit.entity.Rabbit.Type getRabbitType() {
		return rabbitType;
	}

	public void setRabbitType(org.bukkit.entity.Rabbit.Type rabbitType) {
		this.rabbitType = rabbitType;
	}

	public boolean inItem(ItemStack attackItem) {
		return getItem() != null && getItem().equals(attackItem) || PersistenceNMS.getAnimalIDsFromItem(attackItem).contains(getEntityID());
	}

	public boolean inItem(ItemStack item, int entityID) {
		return getItem() != null && item.equals(getItem()) || entityID == getEntityID();
	}

	public int getPuffState() {
		return puffState;
	}

	public void setPuffState(int puffState) {
		this.puffState = puffState;
	}

	public DyeColor getTropicalBodyColor() {
		return tropicalBodyColor;
	}

	public void setTropicalBodyColor(DyeColor tropicalBodyColor) {
		this.tropicalBodyColor = tropicalBodyColor;
	}

	public DyeColor getTropicalPatternColor() {
		return tropicalPatternColor;
	}

	public void setTropicalPatternColor(DyeColor tropicalPatternColor) {
		this.tropicalPatternColor = tropicalPatternColor;
	}

	public Pattern getTropicalPattern() {
		return tropicalPattern;
	}

	public void setTropicalPattern(Pattern tropicalPattern) {
		this.tropicalPattern = tropicalPattern;
	}

	public static Entity setHusbandry(Creature entity, Creature e) {
		AnimalMob animal = new AnimalMob(entity, null);
		animal.editProperties(e, false, true);
		return e;
	}

	public boolean isTamed() {
		return tamed;
	}

	public void setTamed(boolean tamed) {
		this.tamed = tamed;
	}
}
