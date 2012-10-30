package com.vjs.supplydemandanalysis.employee;

import java.util.ArrayList;
import java.util.List;

import com.vjs.supplydemandanalysis.skills.DatabaseObject;
import com.vjs.supplydemandanalysis.skills.Skill;

public class Employee extends DatabaseObject {

	private List<Skill> skills;

	public Employee() {
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
