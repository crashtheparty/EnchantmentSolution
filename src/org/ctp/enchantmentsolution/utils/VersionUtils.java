package org.ctp.enchantmentsolution.utils;

import org.ctp.crashapi.CrashAPI;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class VersionUtils {

	public static String getESVersionName() {
		return EnchantmentSolution.getPlugin().getPluginVersion().getCurrent().getVersionName();
	}

	public static int[] getVersionNumbers() {
		return CrashAPI.getPlugin().getBukkitVersion().getVersionNumbers();
	}
	
	public static boolean isSimilarOrAbove(int i, int j, int k) {
		return CrashAPI.getPlugin().getBukkitVersion().isSimilarOrAbove(getVersionNumbers(), i, j, k);
	}
	
	public static boolean isAbove(int[] ints) {
		int[] version = getVersionNumbers();
		return version[0] > ints[0] && version[1] > ints[1] && version[2] > ints[2];
	}
	
	public static boolean isBelow(int[] ints) {
		int[] version = getVersionNumbers();
		return version[0] < ints[0] && version[1] < ints[1] && version[2] < ints[2];
	}

	public static boolean isZero(int[] ints) {
		return ints[0] == 0 && ints[1] == 0 && ints[2] == 0;
	}

	public static String getMinecraftAPIVersion() {
		return CrashAPI.getPlugin().getBukkitVersion().getAPIVersion();
	}

	public static String getMcMMOType() {
		return EnchantmentSolution.getPlugin().getMcMMOType();
	}

	public static String getMinecraftVersion() {
		return CrashAPI.getPlugin().getBukkitVersion().getVersion();
	}

	public static boolean isBelowOrZero(int[] version) {
		return isBelow(version) || isZero(version);
	}

}
