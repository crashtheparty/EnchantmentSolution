package org.ctp.enchantmentsolution.api;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.enchantments.wrappers.CustomEnchantmentWrapper;

public class ApiEnchantmentWrapper extends CustomEnchantmentWrapper{

	private JavaPlugin plugin;
	private String name;
	/**
	 * Constructor for the ApiEnchantmentWrapper
	 * @param plugin - the plugin of the enchantment
	 * @param namespace - the standard name of the enchantment
	 * @param name - the standard name of the enchantment
	 */
	public ApiEnchantmentWrapper(JavaPlugin plugin, String namespace, String name) {
		super(plugin, namespace);
		
		this.plugin = plugin;
		if(name == null || name == "") {
			throw new NullPointerException("An enchantment's name may not be set to null or an empty string!");
		}
		this.name = name;
	}
	
	/**
	 * Gets the plugin of this enchantment
	 * @return JavaPlugin - the plugin
	 */
	public JavaPlugin getPlugin() {
		return plugin;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStartLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCursed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTreasure() {
		// TODO Auto-generated method stub
		return false;
	}

}
