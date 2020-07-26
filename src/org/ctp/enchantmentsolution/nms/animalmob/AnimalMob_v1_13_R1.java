package org.ctp.enchantmentsolution.nms.animalmob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.DyeColor;
import org.bukkit.entity.*;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Parrot.Variant;
import org.bukkit.entity.TropicalFish.Pattern;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.config.Configurable;
import org.ctp.crashapi.config.CrashConfigurations;
import org.ctp.enchantmentsolution.EnchantmentSolution;

@SuppressWarnings("deprecation")
public class AnimalMob_v1_13_R1 extends AnimalMob {

	private Type ocelotType;

	public AnimalMob_v1_13_R1(Creature mob, ItemStack item) {
		super(mob, item);
		if (mob instanceof Ocelot) {
			Ocelot ocelot = (Ocelot) mob;
			setOcelotType(ocelot.getCatType());
		}
	}

	public Type getOcelotType() {
		return ocelotType;
	}

	public void setOcelotType(Type ocelotType) {
		this.ocelotType = ocelotType;
	}

	@Override
	public void editProperties(Entity e, boolean b1, boolean b2) {
		super.editProperties(e, b1, b2);
		try {
			if (e instanceof Ocelot) {
				Ocelot ocelot = (Ocelot) e;
				ocelot.setCatType(getOcelotType());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void setConfig(Configurable config, int i) {
		this.setConfig(config, "animals.", i);
	}

	@Override
	public void setConfig(Configurable config, String location, int i) {
		super.setConfig(config, location, i);

		config.getConfig().set(location + i + ".ocelot_type", getOcelotType() != null ? getOcelotType().name() : null);
	}

	public static AnimalMob createFromConfig(Configurable config, int i) {
		AnimalMob mob = new AnimalMob();

		mob.setName(config.getString("animals." + i + ".name"));
		mob.setAge(config.getInt("animals." + i + ".age"));
		mob.setHealth(config.getDouble("animals." + i + ".health"));
		mob.setEntityID(config.getInt("animals." + i + ".entity_id"), false);
		mob.setOwner(config.getString("animals." + i + ".owner"));
		mob.setDomestication(config.getInt("animals." + i + ".domestication"));
		mob.setMaxDomestication(config.getInt("animals." + i + ".max_domestication"));
		mob.setJumpStrength(config.getDouble("animals." + i + ".jump_strength"));
		mob.setMovementSpeed(config.getDouble("animals." + i + ".movement_speed"));
		mob.setMaxHealth(config.getDouble("animals." + i + ".max_health"));
		mob.setCarryingChest(config.getBoolean("animals." + i + ".carrying_chest"));
		mob.setLlamaStrength(config.getInt("animals." + i + ".llama_strength"));
		mob.setPigSaddle(config.getBoolean("animals." + i + ".pig_saddle"));
		mob.setSheared(config.getBoolean("animals." + i + ".sheared"));
		mob.setPuffState(config.getInt("animals." + i + ".puff_state"));

		try {
			mob.setMob(EntityType.valueOf(config.getString("animals." + i + ".entity_type")));
		} catch (Exception ex) {}
		try {
			mob.setSheepColor(DyeColor.valueOf(config.getString("animals." + i + ".sheep_color")));
		} catch (Exception ex) {}
		try {
			mob.setWolfCollar(DyeColor.valueOf(config.getString("animals." + i + ".wolf_collar")));
		} catch (Exception ex) {}
		try {
			mob.setHorseStyle(Style.valueOf(config.getString("animals." + i + ".horse_style")));
		} catch (Exception ex) {}
		try {
			mob.setHorseColor(Color.valueOf(config.getString("animals." + i + ".horse_color")));
		} catch (Exception ex) {}
		try {
			mob.setLlamaColor(org.bukkit.entity.Llama.Color.valueOf(config.getString("animals." + i + ".llama_color")));
		} catch (Exception ex) {}
		try {
			mob.setParrotVariant(Variant.valueOf(config.getString("animals." + i + ".parrot_variant")));
		} catch (Exception ex) {}
		try {
			((AnimalMob_v1_13_R1) mob).setOcelotType(Type.valueOf(config.getString("animals." + i + ".ocelot_type")));
		} catch (Exception ex) {}
		try {
			mob.setRabbitType(org.bukkit.entity.Rabbit.Type.valueOf(config.getString("animals." + i + ".rabbit_type")));
		} catch (Exception ex) {}
		try {
			mob.setSaddle(CrashConfigurations.getItemStack(config, "animals." + i + ".saddle"));
		} catch (Exception ex) {}
		try {
			mob.setArmor(CrashConfigurations.getItemStack(config, "animals." + i + ".armor"));
		} catch (Exception ex) {}
		try {
			mob.setTropicalBodyColor(DyeColor.valueOf(config.getString("animals." + i + ".tropical_body_color")));
		} catch (Exception ex) {}
		try {
			mob.setTropicalPatternColor(DyeColor.valueOf(config.getString("animals." + i + ".tropical_pattern_color")));
		} catch (Exception ex) {}
		try {
			mob.setTropicalPattern(Pattern.valueOf(config.getString("animals." + i + ".tropical_pattern")));
		} catch (Exception ex) {}

		Map<Integer, ItemStack> inventoryItems = new HashMap<Integer, ItemStack>();
		List<String> inventoryKeys = config.getConfig().getConfigurationInfo("animals." + i + ".inventory_items");
		for(String key: inventoryKeys) {
			String keyNum = key.substring(key.lastIndexOf('.') + 1);
			try {
				int num = Integer.parseInt(keyNum);
				inventoryItems.put(num, CrashConfigurations.getItemStack(config, key));
				config.getConfig().removeKey(key);
			} catch (Exception ex) {}
		}
		mob.setInventoryItems(inventoryItems);

		for(String key: config.getConfig().getConfigurationInfo("animals." + i))
			config.getConfig().removeKey(key);
		EnchantmentSolution.addAnimals(mob);
		return mob;
	}

}
