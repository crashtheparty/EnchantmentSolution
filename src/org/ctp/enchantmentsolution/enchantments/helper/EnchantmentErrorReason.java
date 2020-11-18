package org.ctp.enchantmentsolution.enchantments.helper;

public enum EnchantmentErrorReason {

	STICKY_HOLD("Bad enchantment in Sticky Hold: %enchant%. Failed to attach to item.");
	
	private final String reason;
	
	EnchantmentErrorReason(String reason){
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}
}
