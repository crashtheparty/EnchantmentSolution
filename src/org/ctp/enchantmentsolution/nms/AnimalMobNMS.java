package org.ctp.enchantmentsolution.nms;

import org.bukkit.entity.Creature;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.CrashAPI;
import org.ctp.crashapi.config.Configurable;
import org.ctp.enchantmentsolution.nms.animalmob.*;

public class AnimalMobNMS {

	public static AnimalMob getMob(Creature animal, ItemStack item) {
		switch (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				return new AnimalMob_v1_13_R1(animal, item);
			case 2:
			case 3:
				return new AnimalMob_v1_13_R2(animal, item);
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				return new AnimalMob_v1_14_R1(animal, item);
			case 9:
			case 10:
			case 11:
				return new AnimalMob_v1_15_R1(animal, item);
			case 12:
				return new AnimalMob_v1_16_R1(animal, item);
			case 13:
			case 14:
				return new AnimalMob_v1_16_R2(animal, item);
			case 15:
				return new AnimalMob_v1_16_R3(animal, item);
		}
		return null;
	}

	public static boolean canAddMob() {
		return CrashAPI.getPlugin().getBukkitVersion().getVersionNumber() > 0;
	}

	public static AnimalMob getFromConfig(Configurable config, int i) {
		switch (CrashAPI.getPlugin().getBukkitVersion().getVersionNumber()) {
			case 1:
				return AnimalMob_v1_13_R1.createFromConfig(config, i);
			case 2:
			case 3:
				return AnimalMob_v1_13_R2.createFromConfig(config, i);
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				return AnimalMob_v1_14_R1.createFromConfig(config, i);
			case 9:
			case 10:
			case 11:
				return AnimalMob_v1_15_R1.createFromConfig(config, i);
			case 12:
				return AnimalMob_v1_16_R1.createFromConfig(config, i);
			case 13:
			case 14:
				return AnimalMob_v1_16_R2.createFromConfig(config, i);
			case 15:
				return AnimalMob_v1_16_R3.createFromConfig(config, i);
		}
		return null;
	}
}
