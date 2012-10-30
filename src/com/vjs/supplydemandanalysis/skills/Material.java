package com.vjs.supplydemandanalysis.skills;

public class Material extends DatabaseObject {

	private int materialGroupId;

	public Material(String name, int materialGroupId) {
		super(name);
		this.materialGroupId = materialGroupId;
	}

	public Material() {
		super();
	}

	public int getMaterialGroupId() {
		return materialGroupId;
	}

	public void setMaterialGroupId(int materialGroupId) {
		this.materialGroupId = materialGroupId;
	}

	public boolean equals(Material other) {
		if (this.getId() == other.getId()) {
			return true;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return getId();
	}

}
