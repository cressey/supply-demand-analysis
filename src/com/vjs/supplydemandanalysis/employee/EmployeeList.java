package com.vjs.supplydemandanalysis.employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList {
	
	List<Employee> employees;
	
	public List<Employee> getEmployees() {
		if (employees == null){
			employees = new ArrayList<Employee>();
		}
		return employees;
	}
}
