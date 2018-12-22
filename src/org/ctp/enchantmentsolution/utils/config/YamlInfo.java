package org.ctp.enchantmentsolution.utils.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class YamlInfo {

	private String path;
	private Object value;
	private String[] comments;
	
	public YamlInfo(String path, Object value) {
		this.path = path;
		this.value = value;
		this.comments = new String[0];
	}
	
	public YamlInfo(String path, Object value, String comments) {
		this.path = path;
		this.value = value;
		this.comments = new String[] {comments};
	}
	
	public YamlInfo(String path, Object value, String[] comments) {
		this.path = path;
		this.value = value;
		this.comments = comments;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String[] getComments() {
		return comments;
	}
	public void setComments(String[] comments) {
		this.comments = comments;
	}
	public String getString() {
		if(value instanceof String) {
			return (String) value;
		}
		return null;
	}
	public int getInt() {
		if(value instanceof Number) {
			return ((Number) value).intValue();
		}
		return 0;
	}
	
	public Integer getInteger() {
		if(value instanceof Integer) {
			return ((Integer) value).intValue();
		}
		return null;
	}

	public boolean getBoolean() {
		if(value instanceof Boolean) {
			return ((Boolean) value).booleanValue();
		}
		return false;
	}
	
	public Boolean getBooleanValue() {
		if(value instanceof Boolean) {
			return ((Boolean) value).booleanValue();
		}
		return null;
	}
	public double getDouble() {
		if(value instanceof Number) {
			return ((Number) value).doubleValue();
		}
		return 0;
	}
	
	public Double getDoubleValue() {
		if(value instanceof Double) {
			return ((Double) value).doubleValue();
		}
		return null;
	}
	public List<String> getStringList() {
		if(value instanceof List<?>) {
			List<?> values = (List<?>) value;
			List<String> strings = new ArrayList<>(values.size());
			for (Object object : values) {
			    strings.add(Objects.toString(object, null));
			}
			return strings;
		}
		return null;
	}
	
}
