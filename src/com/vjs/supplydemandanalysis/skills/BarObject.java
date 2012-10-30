package com.vjs.supplydemandanalysis.skills;

import com.vjs.supplydemandanalysis.activities.AnalysisActivity.BarType;

public class BarObject extends DatabaseObject {
	private int demand;
	private int supply;
	private BarType type;

	private boolean meetsDemand;

	public int getDemand() {
		return demand;
	}

	public void setDemand(int demand) {
		this.demand = demand;
	}

	public int getSupply() {
		return supply;
	}

	public void setSupply(int supply) {
		this.supply = supply;
	}

	public BarType getType() {
		return type;
	}

	public void setType(BarType subject) {
		this.type = subject;
	}

	public void setMeetsDemand(boolean meetsDemand) {
		this.meetsDemand = meetsDemand;
	}

	public boolean isMeetsDemand() {
		return meetsDemand;
	}
}
