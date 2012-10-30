package com.vjs.supplydemandanalysis.skills;

public class Subject extends DatabaseObject {

	private int disciplineId;

	public Subject(String name, int disciplineId) {
		super(name);
		this.disciplineId = disciplineId;
	}

	public Subject() {
		super();
	}

	public int getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(int disciplineId) {
		this.disciplineId = disciplineId;
	}

}
