package com.vjs.supplydemandanalysis.skills;

public class Skill extends DatabaseObject {

	private int skillGroupId;

	public Skill(String name, int taskGroupId) {
		super(name);
		this.skillGroupId = taskGroupId;
	}

	public Skill() {
		super();
	}

	public int getSkillGroupId() {
		return skillGroupId;
	}

	public void setSkillGroupId(int taskGroupId) {
		this.skillGroupId = taskGroupId;
	}

	public boolean equals(Skill other) {
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
