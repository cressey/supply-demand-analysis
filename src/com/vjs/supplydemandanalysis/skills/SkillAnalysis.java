package com.vjs.supplydemandanalysis.skills;

public class SkillAnalysis {

	private int skillId;
	private String skillName;
	private String skillGroup;
	private int skillGroupId;
	private String subject;
	private int subjectId;

	private int supply;
	private int demand;

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillGroup() {
		return skillGroup;
	}

	public void setSkillGroup(String skillGroup) {
		this.skillGroup = skillGroup;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getSupply() {
		return supply;
	}

	public void setSupply(int supply) {
		this.supply = supply;
	}

	public int getDemand() {
		return demand;
	}

	public void setDemand(int demand) {
		this.demand = demand;
	}

	public void setSkillGroupId(int skillGroupId) {
		this.skillGroupId = skillGroupId;
	}

	public int getSkillGroupId() {
		return skillGroupId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getSubjectId() {
		return subjectId;
	}

}
