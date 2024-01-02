package org.ctp.enchantmentsolution.enchantments.config.types;

public class DamageModifier implements EnchantmentModifier {

	// private boolean playerModifier, flatScale, healthPercentLessThan,
	// healthPercentGreaterThan;
	// private String damageFlatExpression, damagePercentExpression;
	// private List<String> mobTypes;
	// private double health;

	public DamageModifier() {

	}

	@Override
	public EnchantmentModifierType getType() {
		return EnchantmentModifierType.DAMAGE;
	}

}
