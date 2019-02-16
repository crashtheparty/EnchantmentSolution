package org.ctp.enchantmentsolution.version;

public class Version {

	private String versionName;
	private VersionType type;
	
	public Version(String name, VersionType type) {
		versionName = name;
		this.setType(type);
	}
	
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
	public VersionType getType() {
		return type;
	}

	public void setType(VersionType type) {
		this.type = type;
	}

	public enum VersionType{
		LIVE("live"), EXPERIMENTAL("experimental"), UPCOMING("upcoming"), UNKNOWN(null);
		
		private String type;
		
		VersionType(String type) {
			this.setType(type);
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
}
