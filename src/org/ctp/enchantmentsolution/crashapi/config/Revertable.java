package org.ctp.enchantmentsolution.crashapi.config;

public interface Revertable {

	public void setDefaults();

	public void migrateVersion();

	public void revert();

	public void revert(int backup);

	public void setComments(boolean comments);
}
