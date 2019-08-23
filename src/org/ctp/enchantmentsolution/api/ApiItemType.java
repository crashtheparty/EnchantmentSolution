package org.ctp.enchantmentsolution.api;

import java.util.List;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.utils.items.ItemType;

public class ApiItemType {
	
	private ItemType itemType;

	/**
	 * Constructor for ApiItemType
	 * @param type - the type of items
	 */
	public ApiItemType(String type) {
		itemType = (ItemType.valueOf(type));
	}
	
	/**
	 * Constructor for ApiItemType
	 * @param type - the type of items
	 */
	public ApiItemType(ItemType type) {
		itemType = type;
	}
	
	/**
	 * Returns the type of items
	 * @return String - the type of items
	 */
	public final String getType() {
		return itemType.getType();
	}

	/**
	 * Returns the list of items that comes with the type
	 * @return List<Material> - the list of items
	 */
	public List<Material> getItemTypes() {
		return itemType.getItemTypes();
	}
	
	/**
	 * Returns the display name to be used in /enchantinfo
	 * @return String - the display name
	 */
	public String getDisplayName() {
		return itemType.getDisplayName();
	}
}
