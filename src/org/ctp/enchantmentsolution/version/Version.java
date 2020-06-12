package org.ctp.enchantmentsolution.version;

public class Version {

	private String versionName, info;
	private VersionType type;

	public Version(String name, VersionType type) {
		versionName = name;
		setType(type);
	}

	public Version(String name, VersionType type, String info) {
		this(name, type);
		this.setInfo(info);
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public enum VersionType {
		LIVE("live"), EXPERIMENTAL("experimental"), UPCOMING("upcoming"), ALPHA("alpha"), UNKNOWN(null);

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
