package org.ctp.enchantmentsolution.enchantments.config;

import java.util.ArrayList;
import java.util.List;

import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.MathUtils;
import org.ctp.enchantmentsolution.utils.config.CustomEnchantmentsConfiguration;

public class EnchantmentDetails {

	private ConfigEnchantment enchantment;
	private DetailsType detailsType;
	private boolean playersOnly, flatScale, infinite;
	private List<String> mobs = new ArrayList<String>();

	public EnchantmentDetails(ConfigEnchantment enchantment, DetailsType detailsType, boolean playersOnly, boolean flatScale, List<String> mobs) {
		this.setEnchantment(enchantment);
		this.setDetailsType(detailsType);
		this.setPlayersOnly(playersOnly);
		this.setFlatScale(flatScale);
		this.setMobs(mobs);
	}

	public boolean isValid() {
		switch (detailsType) {
			case DAMAGE:
				try {
					String s = getEval().replaceAll("(%.*?%)", "1");
					MathUtils.eval(s);
					return true;
				} catch (RuntimeException ex) {
					ex.printStackTrace();
				}
				break;
			default:
				break;

		}
		return false;
	}

	public ConfigEnchantment getEnchantment() {
		return enchantment;
	}

	public void setEnchantment(ConfigEnchantment enchantment) {
		this.enchantment = enchantment;
	}

	public DetailsType getDetailsType() {
		return detailsType;
	}

	public void setDetailsType(DetailsType detailsType) {
		this.detailsType = detailsType;
	}

	public boolean isPlayersOnly() {
		return playersOnly;
	}

	public void setPlayersOnly(boolean playersOnly) {
		this.playersOnly = playersOnly;
	}

	public boolean isFlatScale() {
		return flatScale;
	}

	public void setFlatScale(boolean flatScale) {
		this.flatScale = flatScale;
	}

	public boolean isInfinite() {
		return infinite;
	}

	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}

	public List<String> getMobs() {
		return mobs;
	}

	public void setMobs(List<String> mobs) {
		this.mobs = mobs;
	}

	public enum DetailsType {
		DAMAGE();

		public static DetailsType getType(String type) {
			switch (type.toLowerCase()) {
				case "damage":
					return DAMAGE;
			}
			return null;
		}
	}

	public boolean update() {
		CustomEnchantmentsConfiguration config = Configurations.getCustomEnchantments();
		String name = enchantment.getName();
		String type = config.getString(name + ".data.type");
		if (type == null) throw new NullPointerException("The value for '" + name + ".data.type' must contain a data type: damage.");
		type = type.toLowerCase();
		DetailsType dataType = DetailsType.getType(type);
		if (dataType == null) throw new NullPointerException("The value for '" + name + ".data.type' must be a valid data type: damage.");
		this.setDetailsType(detailsType);
		this.setPlayersOnly(config.getBoolean(name + ".data." + type + ".players-only"));
		this.setFlatScale(config.getBoolean(name + ".data." + type + ".flat-scale"));
		this.setMobs(config.getStringList(name + ".data." + type + ".mobs"));
		return true;
	}

	public String getEval() {
		CustomEnchantmentsConfiguration config = Configurations.getCustomEnchantments();
		if (flatScale) return config.getString(enchantment.getName() + ".data." + detailsType.name().toLowerCase() + ".damage-flat");
		else
			return config.getString(enchantment.getName() + ".data." + detailsType.name().toLowerCase() + ".damage-percent");
	}

}
