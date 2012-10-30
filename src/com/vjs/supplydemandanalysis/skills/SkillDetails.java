package com.vjs.supplydemandanalysis.skills;

public class SkillDetails extends Skill {

	private String subjectName;
	private int subjectId;

	private String skillGroupName;

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getSkillGroupName() {
		return skillGroupName;
	}

	public void setSkillGroupName(String skillGroupName) {
		this.skillGroupName = skillGroupName;
	}

}
