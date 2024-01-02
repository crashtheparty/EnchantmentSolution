package org.ctp.enchantmentsolution.utils.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.utils.MathUtils;

public class Interactions {

	private final List<MatData> interact;
	private final String minExp, maxExp, minDrops, maxDrops;
	private final boolean override, poolChance;
	private final ItemDrop[] drops;
	
	private Interactions(List<MatData> interact, String minExp, String maxExp, String minDrops, String maxDrops, boolean override, boolean poolChance, ItemDrop[] drops) {
		this.interact = interact;
		this.minExp = minExp;
		this.maxExp = maxExp;
		this.minDrops = minDrops;
		this.maxDrops = maxDrops;
		this.override = override;
		this.poolChance = poolChance;
		this.drops = drops;
	}
	
	public static Interactions[] fromConfig(YamlConfig config, boolean custom) {
		String location = "";
		List<Interactions> interactions = new ArrayList<Interactions>();
		for (String l : config.getLevelEntryKeys(location)) {
			String loc = l + ".";
			List<MatData> data = new ArrayList<MatData>();
			for (String s : config.getStringList(loc + "interact")) {
				MatData m = new MatData(s);
				if (m.hasMaterial()) data.add(m);
			}
			if (data.size() == 0) continue;
			List<String> dropString = config.getLevelEntryKeys(loc + "drops");
			List<ItemDrop> dropList = new ArrayList<ItemDrop>();
			for (String drop : dropString) {
				ItemDrop id = ItemDrop.fromConfig(config, drop + ".");
				if (id != null && id.getType().hasMaterial())
					dropList.add(id);
			}
			ItemDrop[] drops = dropList.toArray(new ItemDrop[] {});
			interactions.add(new Interactions(data, config.getString(loc + "minExp"), config.getString(loc + "maxExp"), config.getString(loc + "minDrops"), config.getString(loc + "maxDrops"), config.getBoolean(loc + "override"), config.getBoolean(loc + "poolChance"), drops));
			
		}
		
		return interactions.toArray(new Interactions[] {});
	}

	public List<MatData> getInteract() {
		return interact;
	}

	public String getMinExp() {
		return minExp;
	}

	public String getMaxExp() {
		return maxExp;
	}
	
	public int getExp(int level) {
		HashMap<String, Object> codes = ChatUtils.getCodes();
		codes.put("%level%", level);
		String minExpString = Chatable.get().getMessage(getMinExp(), codes);
		int minExp = (int) MathUtils.eval(minExpString);
		String maxExpString = Chatable.get().getMessage(getMaxExp(), codes);
		int maxExp = (int) MathUtils.eval(maxExpString);
		return MathUtils.randomInt(minExp, maxExp);
	}

	public boolean isOverride() {
		return override;
	}

	public ItemDrop[] getDrops() {
		return drops;
	}

	public boolean isPoolChance() {
		return poolChance;
	}

	public String getMinDrops() {
		return minDrops;
	}

	public String getMaxDrops() {
		return maxDrops;
	}
	
	public int getNumDrops(int level) {
		HashMap<String, Object> codes = ChatUtils.getCodes();
		codes.put("%level%", level);
		String minDropsString = Chatable.get().getMessage(getMinDrops(), codes);
		int minDrops = (int) MathUtils.eval(minDropsString);
		String maxDropsString = Chatable.get().getMessage(getMaxDrops(), codes);
		int maxDrops = (int) MathUtils.eval(maxDropsString);
		return MathUtils.randomInt(minDrops, maxDrops);
	}
	
	@Override
	public String toString() {
		String s = "Interactions[interact={";
		for (int i = 0; i < interact.size(); i++) {
			s += interact.get(i).getMaterialName();
			if (i < interact.size() - 1) s += ",";
		}
		s += "}, minExp=" + minExp + ", maxExp=" + maxExp + ", minDrops=" + minDrops + ", maxDrops=" + maxDrops + ", override=" + override + ", poolChance=" + poolChance + ", drops={";
		for (int i = 0; i < drops.length; i++) {
			s += drops[i].toString();
			if (i < drops.length - 1) s += ",";
		}
		s += "}]";
		return s;
	}
	
}
