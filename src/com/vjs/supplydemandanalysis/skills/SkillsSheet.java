package com.vjs.supplydemandanalysis.skills;

import java.util.ArrayList;
import java.util.List;

public class SkillsSheet extends DatabaseObject {

	private List<Skill> skills;

	public SkillsSheet() {
		super();
	}

	public List<Skill> getSkills() {
		if (skills == null) {
			skills = new ArrayList<Skill>();
		}
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

}
