package org.ctp.enchantmentsolution.utils.items;

import java.util.HashMap;

import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.utils.MathUtils;

public class ItemDrop {

	private final MatData type;
	private final String min, max, chance;
	private final boolean normAmount;

	private ItemDrop(MatData type, String min, String max, String chance, boolean normAmount) {
		this.type = type;
		this.min = min;
		this.max = max;
		this.chance = chance;
		this.normAmount = normAmount;
	}

	public static ItemDrop fromConfig(YamlConfig config, String location) {
		MatData data = new MatData(config.getString(location + "type"));
		if (!data.hasMaterial()) {
			Chatable.sendDebug("Item with name " + data.getMaterialName() + " is not present in this version. Ignoring.");
			return null;
		}
		return new ItemDrop(data, config.getString(location + "min"), config.getString(location + "max"), config.getString(location + "chance"), config.getBoolean(location + "normAmount"));
	}

	public MatData getType() {
		return type;
	}

	public String getMin() {
		return min;
	}

	public String getMax() {
		return max;
	}

	public String getChance() {
		return chance;
	}
	
	public double getChance(int level, int fortune, int looting) {
		HashMap<String, Object> codes = ChatUtils.getCodes();
		codes.put("%level%", level);
		codes.put("%fortune%", fortune);
		codes.put("%looting%", looting);
		String chanceString = Chatable.get().getMessage(getChance(), codes);
		return MathUtils.eval(chanceString);
	}
	
	public int getAmount(int level, int fortune, int looting, int originalAmount) {
		if (isNormAmount()) return originalAmount;
		HashMap<String, Object> codes = ChatUtils.getCodes();
		codes.put("%level%", level);
		codes.put("%fortune%", fortune);
		codes.put("%looting%", looting);
		String minString = Chatable.get().getMessage(getMin(), codes);
		int min = (int) MathUtils.eval(minString);
		String maxString = Chatable.get().getMessage(getMax(), codes);
		int max = (int) MathUtils.eval(maxString);
		return MathUtils.randomIntWithChance(min, max);
	}

	public boolean isNormAmount() {
		return normAmount;
	}

	@Override
	public String toString() {
		return "ItemDrop[type=" + type.getMaterialName() + ", min=" + min + ", max=" + max + ", chance=" + chance + ", normAmount=" + normAmount + "]";
	}

}
