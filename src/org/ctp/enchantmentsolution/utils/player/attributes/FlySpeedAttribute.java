package org.ctp.enchantmentsolution.utils.player.attributes;

import java.util.*;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.ctp.enchantmentsolution.utils.player.ESPlayerAttribute;
import org.ctp.enchantmentsolution.utils.player.ESPlayerAttributeInstance;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class FlySpeedAttribute implements ESPlayerAttributeInstance {

	private Collection<AttributeModifier> modifiers = new ArrayList<AttributeModifier>();
	private Map<UUID, AttributeModifier> modifierUUIDs = new HashMap<UUID, AttributeModifier>();
	private final Map<AttributeModifier.Operation, Set<AttributeModifier>> operationModifiers = Maps.newEnumMap(AttributeModifier.Operation.class);
	private double baseValue = getDefaultValue();

	@Override
	public void addModifier(AttributeModifier modifier) {
		if (modifier != null) {
			addModifierUUID(modifier);
			modifiers.add(modifier);
		}
	}

	private void addModifierUUID(AttributeModifier attributemodifier) {
		AttributeModifier attributemodifier1 = modifierUUIDs.putIfAbsent(attributemodifier.getUniqueId(), attributemodifier);

		if (attributemodifier1 != null) throw new IllegalArgumentException("Modifier is already applied on this attribute!");
		else
			addModifierOperation(attributemodifier.getOperation()).add(attributemodifier);
	}

	private Set<AttributeModifier> addModifierOperation(AttributeModifier.Operation attributemodifier_operation) {
		return operationModifiers.computeIfAbsent(attributemodifier_operation, (attributemodifier_operation1) -> {
			return Sets.newHashSet();
		});
	}

	@Override
	public Attribute getAttribute() {
		return null;
	}

	public ESPlayerAttribute getESAttribute() {
		return ESPlayerAttribute.FLY_SPEED;
	}

	@Override
	public double getBaseValue() {
		return baseValue;
	}

	@Override
	public double getDefaultValue() {
		return 0.1;
	}

	@Override
	public Collection<AttributeModifier> getModifiers() {
		return modifiers;
	}

	public Collection<AttributeModifier> getModifiersFromOperation(Operation operation) {
		Collection<AttributeModifier> m = operationModifiers.get(operation);
		return m != null ? m : new ArrayList<AttributeModifier>();
	}

	@Override
	public double getValue() {
		double value = getFinalValue();
		return value > 1 ? 1 : value;
	}

	private double getFinalValue() {
		double d0 = getBaseValue();

		AttributeModifier attributemodifier;

		for(Iterator<AttributeModifier> iterator = getModifiersFromOperation(Operation.ADD_NUMBER).iterator(); iterator.hasNext(); d0 += attributemodifier.getAmount())
			attributemodifier = iterator.next();

		double d1 = d0;

		AttributeModifier attributemodifier1;
		Iterator<AttributeModifier> iterator1;

		for(iterator1 = getModifiersFromOperation(Operation.ADD_SCALAR).iterator(); iterator1.hasNext(); d1 += d0 * attributemodifier1.getAmount())
			attributemodifier1 = iterator1.next();

		for(iterator1 = getModifiersFromOperation(Operation.MULTIPLY_SCALAR_1).iterator(); iterator1.hasNext(); d1 *= 1.0D + attributemodifier1.getAmount())
			attributemodifier1 = iterator1.next();

		return d1;
	}

	@Override
	public void removeModifier(AttributeModifier modifier) {
		getModifiersFromOperation(modifier.getOperation()).remove(modifier);
		modifierUUIDs.remove(modifier.getUniqueId());
		modifiers.remove(modifier);
	}

	@Override
	public void setBaseValue(double baseValue) {
		this.baseValue = baseValue;
	}

	@Override
	public void removeModifier(UUID uuid) {
		AttributeModifier m = modifierUUIDs.get(uuid);
		if (m != null) removeModifier(m);
	}

}
