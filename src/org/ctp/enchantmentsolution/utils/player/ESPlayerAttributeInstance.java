package org.ctp.enchantmentsolution.utils.player;

import java.util.UUID;

import org.bukkit.attribute.AttributeInstance;

public interface ESPlayerAttributeInstance extends AttributeInstance {

	void removeModifier(UUID fromString);

}
