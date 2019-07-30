package org.ctp.enchantmentsolution.api.util;

public class AdvancementModificationResult {

	private boolean loaded, changed;
	private String message;
	
	public AdvancementModificationResult(boolean loaded, boolean changed, String message) {
		this.setLoaded(loaded);
		this.setChanged(changed);
		this.setMessage(message);
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
