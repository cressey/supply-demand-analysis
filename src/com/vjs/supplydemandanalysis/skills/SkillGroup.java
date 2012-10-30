package com.vjs.supplydemandanalysis.skills;

public class SkillGroup extends DatabaseObject {

	private int subjectId;

	public SkillGroup(String name, int subjectId) {
		super(name);
		this.subjectId = subjectId;
	}

	public SkillGroup() {
		super();
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

}
