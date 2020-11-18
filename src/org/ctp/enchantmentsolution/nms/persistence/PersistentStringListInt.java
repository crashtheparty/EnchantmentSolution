package org.ctp.enchantmentsolution.nms.persistence;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.ctp.crashapi.utils.StringUtils;

public class PersistentStringListInt implements PersistentDataType<String, List<Integer>> {

	@Override
	public List<Integer> fromPrimitive(String arg0, PersistentDataAdapterContext arg1) {
		List<Integer> list = new ArrayList<Integer>();
		String[] split = arg0.split(",");
		for(String s: split)
			try {
				list.add(Integer.parseInt(s));
			} catch (Exception ex) {}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<List<Integer>> getComplexType() {
		return (Class<List<Integer>>) new ArrayList<Integer>().getClass();
	}

	@Override
	public Class<String> getPrimitiveType() {
		return String.class;
	}

	@Override
	public String toPrimitive(List<Integer> arg0, PersistentDataAdapterContext arg1) {
		return StringUtils.join(arg0, ",");
	}

}
