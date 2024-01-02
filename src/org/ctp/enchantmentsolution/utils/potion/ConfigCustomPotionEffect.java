package org.ctp.enchantmentsolution.utils.potion;

import org.ctp.enchantmentsolution.utils.MathUtils;

public class ConfigCustomPotionEffect {

	private CustomPotionEffectType type;
	private String amplifierExpression, durationExpression;

	public ConfigCustomPotionEffect(CustomPotionEffectType type, String amplifierExpression, String durationExpression) {
		this.type = type;
		this.amplifierExpression = amplifierExpression;
		this.durationExpression = durationExpression;
	}

	public CustomPotionEffectType getType() {
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
