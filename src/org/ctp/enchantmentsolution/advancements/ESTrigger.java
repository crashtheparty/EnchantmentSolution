package org.ctp.enchantmentsolution.advancements;

import org.ctp.enchantmentsolution.api.trigger.Trigger;

public class ESTrigger {

	private String criteria;
	private Trigger trigger;
	private int maxAmount = 0, versionMinimum = 0, versionMaximum = 0;
	
	public ESTrigger(String criteria, Trigger trigger) {
		this.criteria = criteria;
		this.trigger = trigger;
	}
	
	public ESTrigger(String criteria, Trigger trigger, int maxAmount) {
		this.criteria = criteria;
		this.trigger = trigger;
		this.maxAmount = maxAmount;
	}
	
	public ESTrigger(String criteria, Trigger trigger, int maxAmount, int versionMinimum, int versionMaximum) {
		this.criteria = criteria;
		this.trigger = trigger;
		this.maxAmount = maxAmount;
		this.versionMinimum = versionMinimum;
		this.versionMaximum = versionMaximum;
	}
	
	public String getCriteria() {
		return criteria;
	}
	
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	
	public Trigger getTrigger() {
		return trigger;
	}
	
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
	
	public int getMaxAmount() {
		return maxAmount;
	}
	
	public void setMaxAmount(int maxAmount) {
		this.maxAmount = maxAmount;
	}

	public int getVersionMinimum() {
		return versionMinimum;
	}

	public int getVersionMaximum() {
		return versionMaximum;
	}
}
