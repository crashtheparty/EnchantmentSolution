package org.ctp.enchantmentsolution.api;

import java.util.List;

import org.ctp.enchantmentsolution.enums.ItemData;
import org.ctp.enchantmentsolution.enums.ItemType;

public class ApiItemType {

	private ItemType itemType;

	/**
	 * Constructor for ApiItemType
	 * 
	 * @param type
	 *            - the type of items
	 */
	public ApiItemType(String type) {
		itemType = ItemType.getItemType(type);
	}

	/**
	 * Constructor for ApiItemType
	 * 
	 * @param type
	 *            - the type of items
	 */
	public ApiItemType(ItemType type) {
		itemType = type;
	}

	/**
	 * Returns the type of items
	 * 
	 * @return String - the type of items
	 */
	public final String getType() {
		return itemType.getType();
	}

	/**
	 * Returns the list of enchantment materials that comes with the type
	 * 
	 * @return List<ItemData> - the list of items in ItemData form
	 */
	public List<ItemData> getEnchantMaterials() {
		return itemType.getEnchantMaterials();
	}

	/**
	 * Returns the list of anvil materials that comes with the type
	 * 
	 * @return List<ItemData> - the list of items in ItemData form
	 */
	public List<ItemData> getAnvilMaterials() {
		return itemType.getAnvilMaterials();
	}

	/**
	 * Returns the display name to be used in /enchantinfo
	 * 
	 * @return String - the display name
	 */
	public String getDisplayName() {
		return itemType.getDisplayName();
	}
}
