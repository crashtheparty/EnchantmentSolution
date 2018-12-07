package org.ctp.enchantmentsolution.utils.config;

import java.util.ArrayList;
import java.util.List;

public class YamlChild {

	private String path;
	private List<YamlChild> children;
	
	public YamlChild(String path){
		this.setPath(path);
		children = new ArrayList<YamlChild>();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<YamlChild> getChildren() {
		return children;
	}
	
	public boolean addChild(String path) {
		if(path.startsWith(getPath())) {
			if(path.equals(getPath())) {
				return true;
			}
			for(YamlChild child : children) {
				if(child.addChild(path)) {
					return true;
				}
			}
			children.add(new YamlChild(path));
			return true;
		}
		return false;
	}
	
	public String toString() {
		String ret = new String();
		
		ret += "path: " + path;
		
		if(children.size() > 0) {
			ret += " children[";
			for(YamlChild child : children) {
				ret += child.toString();
			}
			ret += "]";
		}
		
		return ret;
	}
}
