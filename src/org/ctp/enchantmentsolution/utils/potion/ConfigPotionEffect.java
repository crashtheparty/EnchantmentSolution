package org.ctp.enchantmentsolution.utils.potion;

import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.utils.MathUtils;

public class ConfigPotionEffect {

	private PotionEffectType type;
	private String amplifierExpression, durationExpression;

	public ConfigPotionEffect(PotionEffectType type, String amplifierExpression, String durationExpression) {
		this.type = type;
		this.amplifierExpression = amplifierExpression;
		this.durationExpression = durationExpression;
	}

	public PotionEffectType getType() {
		return type;
	}

	public int getAmplifier(int level) {
		String exp = amplifierExpression.replaceAll("%level%", level + "");
		return (int) MathUtils.eval(exp);
	}

	public int getDuration(int level) {
		String exp = durationExpression.replaceAll("%level%", level + "");
		return (int) MathUtils.eval(exp);
	}

}
