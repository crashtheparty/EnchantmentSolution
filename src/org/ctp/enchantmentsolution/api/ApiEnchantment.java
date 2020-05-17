package org.ctp.enchantmentsolution.api;

import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;

public abstract class ApiEnchantment extends CustomEnchantment {

	private final ApiEnchantmentWrapper relative;
	private int pointsLevelOne, pointsIncrease, freeLevel;
	private double experience;
	private boolean free;

	/**
	 * Constructor for ApiEnchantment
	 * 
	 * @param englishUSDisplayName
	 *            - the name that shows on items for the enchantment - default
	 *            language of Language.US
	 * @param fiftyConstant
	 *            - the enchantability constant at level 50
	 * @param thirtyConstant
	 *            - the enchantability constant at level 30
	 * @param fiftyModifier
	 *            - the enchantability modifier at level 50
	 * @param thirtyModifier
	 *            - the enchantability modifier at level 30
	 * @param fiftyStartLevel
	 *            - the start level at level 50
	 * @param thirtyStartLevel
	 *            - the start level at level 30
	 * @param fiftyMaxLevel
	 *            - the max level at level 50
	 * @param thirtyMaxLevel-
	 *            the max level at level 30
	 * @param weight
	 *            - the weight of getting the enchantment
	 * @param englishUSDescription
	 *            - the description of the enchantment - default language of
	 *            Language.US
	 * 
	 *            Set curses using the setCurse() method, and set if max level is 1
	 *            using the setMaxLevelOne() method
	 */
	public ApiEnchantment(ApiEnchantmentWrapper relative, String englishUSDisplayName, int fiftyConstant,
	int thirtyConstant, int fiftyModifier, int thirtyModifier, int fiftyStartLevel, int thirtyStartLevel,
	int fiftyMaxLevel, int thirtyMaxLevel, Weight weight, String englishUSDescription) {
		super(englishUSDisplayName, fiftyConstant, thirtyConstant, fiftyModifier, thirtyModifier, fiftyStartLevel, thirtyStartLevel, fiftyMaxLevel, thirtyMaxLevel, weight, englishUSDescription);
		this.relative = relative;
		setPointsLevelOne(-1);
		setPointsIncrease(0);
		setExperience(0);
	}

	/**
	 * Get the relative enchantment for this custom enchantment
	 * 
	 * @return - the relative enchantment
	 */
	@Override
	public ApiEnchantmentWrapper getRelativeEnchantment() {
		return relative;
	}

	public int getPointsLevelOne() {
		return pointsLevelOne;
	}

	protected void setPointsLevelOne(int pointsLevelOne) {
		this.pointsLevelOne = pointsLevelOne;
	}

	public int getPointsIncrease() {
		return pointsIncrease;
	}

	protected void setPointsIncrease(int pointsIncrease) {
		this.pointsIncrease = pointsIncrease;
	}

	public double getExperience() {
		return experience;
	}

	protected void setExperience(double experience) {
		this.experience = experience;
	}

	public boolean isFreeEnchantment() {
		return free;
	}

	protected void setFreeEnchantment(boolean free) {
		this.free = free;
	}

	public int getFreeLevel() {
		return freeLevel;
	}

	protected void setFreeLevel(int freeLevel) {
		this.freeLevel = freeLevel;
	}

}
