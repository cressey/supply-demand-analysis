package com.vjs.supplydemandanalysis.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.vjs.supplydemandanalysis.employee.Employee;
import com.vjs.supplydemandanalysis.skills.ContextDetails;
import com.vjs.supplydemandanalysis.skills.DatabaseObject;
import com.vjs.supplydemandanalysis.skills.Material;
import com.vjs.supplydemandanalysis.skills.MaterialDetails;
import com.vjs.supplydemandanalysis.skills.Skill;
import com.vjs.supplydemandanalysis.skills.SkillContext;
import com.vjs.supplydemandanalysis.skills.SkillDetails;

public class EmployeeDAO extends DatabaseStuff {
	public EmployeeDAO(Context c) {
		super(c);
	}

	private long createBasicEmployee(Employee e) {
		ContentValues cv = new ContentValues();
		cv.put(EMPLOYEE_NAME, e.getName());
		return myDatabase.insert(EMPLOYEE_TABLE, null, cv);
	}

	public long createEmployee(String e) {
		open();
		ContentValues cv = new ContentValues();
		cv.put(EMPLOYEE_NAME, e);
		int employeeId = (int) myDatabase.insert(EMPLOYEE_TABLE, null, cv);
		close();
		return employeeId;
	}

	public List<Employee> getEmployeeList() {
		open();
		String[] columns = new String[] { EMPLOYEE_NAME, EMPLOYEE_ID };
		Cursor c = myDatabase.query(EMPLOYEE_TABLE, columns, null, null, null,
				null, null);
		List<Employee> employees = new ArrayList<Employee>();
		int iTableID = c.getColumnIndex(EMPLOYEE_ID);
		int iId = c.getColumnIndex(EMPLOYEE_NAME);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				Employee e = new Employee();
				e.setId(c.getInt(iTableID));
				e.setName(c.getString(iId));
				employees.add(e);
			}
		}
		close();
		return employees;
	}

	public Employee getEmployee(int id) {
		open();
		Employee e = new Employee();
		Cursor c = myDatabase.rawQuery("SELECT * FROM " + EMPLOYEE_TABLE
				+ " WHERE " + EMPLOYEE_ID + " = ? ORDER BY " + EMPLOYEE_NAME
				+ " ASC", new String[] { Integer.toString(id) });
		int iTableId = c.getColumnIndex(EMPLOYEE_ID);
		int iId = c.getColumnIndex(EMPLOYEE_NAME);
		if (c != null) {
			c.moveToFirst();
			e.setId(c.getInt(iTableId));
			e.setName(c.getString(iId));
		}
		close();
		return e;
	}

	public void deleteEmployeeEntry(int id) {
		open();
		myDatabase.delete(EMPLOYEE_TABLE, EMPLOYEE_ID + " = ?",
				new String[] { Integer.toString(id) });
		myDatabase.delete(EMPLOYEE_SKILL_TABLE, EMPLOYEE_ID + " = ?",
				new String[] { Integer.toString(id) });
		close();
	}

	private void updateEmployeeName(Employee e) {
		ContentValues cv = new ContentValues();
		cv.put(EMPLOYEE_NAME, e.getName());
		myDatabase.update(EMPLOYEE_TABLE, cv, EMPLOYEE_ID + "= ?",
				new String[] { String.valueOf(e.getId()) });
	}

	public List<Skill> getEmployeeSkills(int employeeId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM " + EMPLOYEE_SKILL_TABLE
				+ " LEFT JOIN " + SKILL_TABLE + " USING (" + SKILL_ID
				+ ") WHERE " + EMPLOYEE_ID + " = ? ORDER BY " + SKILL_NAME
				+ " ASC", new String[] { Integer.toString(employeeId) });
		List<Skill> skills = new ArrayList<Skill>();
		int rId = c.getColumnIndex(SKILL_ID);
		int rName = c.getColumnIndex(SKILL_NAME);
		int rSkillGroup = c.getColumnIndex(SKILL_GROUP_ID);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				Skill s = new Skill();
				s.setId(c.getInt(rId));
				s.setName(c.getString(rName));
				s.setSkillGroupId(c.getInt(rSkillGroup));
				skills.add(s);
			}
		}
		close();
		return skills;
	}

	public List<Material> getEmployeeMaterials(int employeeId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM "
				+ EMPLOYEE_MATERIAL_TABLE + " LEFT JOIN " + MATERIAL_TABLE
				+ " USING (" + MATERIAL_ID + ") WHERE " + EMPLOYEE_ID
				+ " = ? ORDER BY " + MATERIAL_NAME + " ASC",
				new String[] { Integer.toString(employeeId) });
		List<Material> materials = new ArrayList<Material>();
		int rId = c.getColumnIndex(MATERIAL_ID);
		int rName = c.getColumnIndex(MATERIAL_NAME);
		int rMaterialGroup = c.getColumnIndex(MATERIAL_GROUP_ID);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				Material s = new Material();
				s.setId(c.getInt(rId));
				s.setName(c.getString(rName));
				s.setMaterialGroupId(c.getInt(rMaterialGroup));
				materials.add(s);
			}
		}
		close();
		return materials;
	}

	public List<SkillContext> getEmployeeContexts(int employeeId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM "
				+ EMPLOYEE_CONTEXT_TABLE + " LEFT JOIN " + CONTEXT_TABLE
				+ " USING (" + CONTEXT_ID + ") WHERE " + EMPLOYEE_ID
				+ " = ? ORDER BY " + CONTEXT_NAME + " ASC",
				new String[] { Integer.toString(employeeId) });
		List<SkillContext> skillsContexts = new ArrayList<SkillContext>();
		int rId = c.getColumnIndex(CONTEXT_ID);
		int rName = c.getColumnIndex(CONTEXT_NAME);
		int rSkillGroup = c.getColumnIndex(CONTEXT_GROUP_ID);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				SkillContext s = new SkillContext();
				s.setId(c.getInt(rId));
				s.setName(c.getString(rName));
				s.setContextGroupId(c.getInt(rSkillGroup));
				skillsContexts.add(s);
			}
		}
		close();
		return skillsContexts;
	}

	public void addEmployeeSkill(Employee employee, Skill skill) {
		open();
		ContentValues cv = new ContentValues();
		cv.put(EMPLOYEE_ID, employee.getId());
		cv.put(SKILL_ID, skill.getId());
		myDatabase.insert(EMPLOYEE_SKILL_TABLE, null, cv);
		close();
	}

	public void addEmployeeMaterial(Employee employee, Material material) {
		open();
		ContentValues cv = new ContentValues();
		cv.put(EMPLOYEE_ID, employee.getId());
		cv.put(MATERIAL_ID, material.getId());
		myDatabase.insert(EMPLOYEE_MATERIAL_TABLE, null, cv);
		close();
	}

	public void addEmployeeContext(Employee employee, SkillContext context) {
		open();
		ContentValues cv = new ContentValues();
		cv.put(EMPLOYEE_ID, employee.getId());
		cv.put(CONTEXT_ID, context.getId());
		myDatabase.insert(EMPLOYEE_CONTEXT_TABLE, null, cv);
		close();
	}

	public void removeEmployeeSkill(Employee employee, Skill skill) {
		open();
		ContentValues cv = new ContentValues();
		cv.put(EMPLOYEE_ID, employee.getId());
		cv.put(SKILL_ID, skill.getId());
		myDatabase.delete(
				EMPLOYEE_SKILL_TABLE,
				EMPLOYEE_ID + " =? AND " + SKILL_ID + " =?",
				new String[] { Integer.toString(employee.getId()),
						Integer.toString(skill.getId()) });
		close();
	}

	public void removeEmployeeMaterial(Employee employee, Material material) {
		open();
		ContentValues cv = new ContentValues();
		cv.put(EMPLOYEE_ID, employee.getId());
		cv.put(MATERIAL_ID, material.getId());
		myDatabase.delete(
				EMPLOYEE_MATERIAL_TABLE,
				EMPLOYEE_ID + " =? AND " + MATERIAL_ID + " =?",
				new String[] { Integer.toString(employee.getId()),
						Integer.toString(material.getId()) });
		close();
	}

	public void removeEmployeeContext(Employee employee, SkillContext context) {
		open();
		ContentValues cv = new ContentValues();
		cv.put(EMPLOYEE_ID, employee.getId());
		cv.put(CONTEXT_ID, context.getId());
		myDatabase.delete(
				EMPLOYEE_CONTEXT_TABLE,
				EMPLOYEE_ID + " =? AND " + CONTEXT_ID + " =?",
				new String[] { Integer.toString(employee.getId()),
						Integer.toString(context.getId()) });
		close();
	}

	public List<SkillDetails> getEmployeeSkillsWithDetails(int employeeId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM " + EMPLOYEE_SKILL_TABLE
				+ " LEFT JOIN " + SKILL_TABLE + " USING ('" + SKILL_ID
				+ "') LEFT JOIN " + SKILL_GROUP_TABLE + " USING ('"
				+ SKILL_GROUP_ID + "') LEFT JOIN " + SUBJECT_TABLE
				+ " USING ('" + SUBJECT_ID + "') WHERE " + EMPLOYEE_ID
				+ " = ? ORDER BY " + SUBJECT_NAME + " ASC, " + SKILL_GROUP_NAME
				+ " ASC, " + SKILL_NAME + " ASC ",
				new String[] { Integer.toString(employeeId) });
		List<SkillDetails> skills = new ArrayList<SkillDetails>();
		int rId = c.getColumnIndex(SKILL_ID);
		int rName = c.getColumnIndex(SKILL_NAME);
		int rSkillGroup = c.getColumnIndex(SKILL_GROUP_NAME);
		int rSkillGroupId = c.getColumnIndex(SKILL_GROUP_ID);
		int rSubject = c.getColumnIndex(SUBJECT_NAME);
		int rSubjectId = c.getColumnIndex(SUBJECT_ID);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				SkillDetails s = new SkillDetails();
				s.setId(c.getInt(rId));
				s.setName(c.getString(rName));
				s.setSkillGroupName(c.getString(rSkillGroup));
				s.setSkillGroupId(c.getInt(rSkillGroupId));
				s.setSubjectName(c.getString(rSubject));
				s.setSubjectId(c.getInt(rSubjectId));
				skills.add(s);
			}
		}
		close();
		return skills;
	}

	public List<MaterialDetails> getEmployeeMaterialsWithDetails(int employeeId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM "
				+ EMPLOYEE_MATERIAL_TABLE + " LEFT JOIN " + MATERIAL_TABLE
				+ " USING ('" + MATERIAL_ID + "') LEFT JOIN "
				+ MATERIAL_GROUP_TABLE + " USING ('" + MATERIAL_GROUP_ID
				+ "')  WHERE " + EMPLOYEE_ID + " = ? ORDER BY "
				+ MATERIAL_GROUP_NAME + " ASC, " + MATERIAL_NAME + " ASC ",
				new String[] { Integer.toString(employeeId) });
		List<MaterialDetails> materials = new ArrayList<MaterialDetails>();
		int rId = c.getColumnIndex(MATERIAL_ID);
		int rName = c.getColumnIndex(MATERIAL_NAME);
		int materialGroup = c.getColumnIndex(MATERIAL_GROUP_NAME);
		int materialGroupId = c.getColumnIndex(MATERIAL_GROUP_ID);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				MaterialDetails s = new MaterialDetails();
				s.setId(c.getInt(rId));
				s.setName(c.getString(rName));
				s.setMaterialGroupName(c.getString(materialGroup));
				s.setMaterialGroupId(c.getInt(materialGroupId));
				materials.add(s);
			}
		}
		close();
		return materials;
	}

	public List<ContextDetails> getEmployeeContextsWithDetails(int employeeId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM "
				+ EMPLOYEE_CONTEXT_TABLE + " LEFT JOIN " + CONTEXT_TABLE
				+ " USING ('" + CONTEXT_ID + "') LEFT JOIN "
				+ CONTEXT_GROUP_TABLE + " USING ('" + CONTEXT_GROUP_ID
				+ "') WHERE " + EMPLOYEE_ID + " = ? ORDER BY "
				+ CONTEXT_GROUP_NAME + " ASC, " + CONTEXT_NAME + " ASC ",
				new String[] { Integer.toString(employeeId) });
		List<ContextDetails> contexts = new ArrayList<ContextDetails>();
		int rId = c.getColumnIndex(CONTEXT_ID);
		int rName = c.getColumnIndex(CONTEXT_NAME);
		int contextGroup = c.getColumnIndex(CONTEXT_GROUP_NAME);
		int contextGroupId = c.getColumnIndex(CONTEXT_GROUP_ID);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				ContextDetails s = new ContextDetails();
				s.setId(c.getInt(rId));
				s.setName(c.getString(rName));
				s.setContextGroupName(c.getString(contextGroup));
				s.setContextGroupId(c.getInt(contextGroupId));
				contexts.add(s);
			}
		}
		close();
		return contexts;
	}

	// public void updateEmployee(Employee employee, List<Skill> selectedSkills)
	// {
	// open();
	// updateEmployeeName(employee);
	// List<Skill> employeeSkills = getEmployeeSkills(employee.getId());
	// for (Skill skill : employeeSkills) {
	// if (!Utils.inListOfSkills(skill, selectedSkills)) {
	// removeEmployeeSkill(employee, skill);
	// }
	// }
	// for (Skill skill : selectedSkills) {
	// if (!Utils.inListOfSkills(skill, employeeSkills)) {
	// addEmployeeSkill(employee, skill);
	// }
	// }
	// close();
	// }

	/**
	 * Update the employee with a new list of selected items
	 * 
	 * @param employee
	 * @param selectedItems
	 */
	public void updateEmployee(Employee employee,
			List<? extends DatabaseObject> selectedItems) {
		open();
		updateEmployeeName(employee);
		int employeeId = employee.getId();
		if (selectedItems.get(0) instanceof Skill) {
			List<Skill> employeeSkills = getEmployeeSkills(employeeId);
			for (Skill skill : employeeSkills) {
				if (!alreadyInList(skill, selectedItems)) {
					removeEmployeeSkill(employee, skill);
				}
			}
			for (Object skill : selectedItems) {
				if (!alreadyInList(skill, employeeSkills)) {
					addEmployeeSkill(employee, (Skill) skill);
				}
			}
		} else if (selectedItems.get(0) instanceof Material) {
			List<Material> employeeMaterials = getEmployeeMaterials(employeeId);
			for (Material material : employeeMaterials) {
				if (!alreadyInList(material, selectedItems)) {
					removeEmployeeMaterial(employee, material);
				}
			}
			for (Object material : selectedItems) {
				if (!alreadyInList(material, employeeMaterials)) {
					addEmployeeMaterial(employee, (Material) material);
				}
			}
		} else if (selectedItems.get(0) instanceof SkillContext) {
			List<SkillContext> employeeContexts = getEmployeeContexts(employeeId);
			for (SkillContext context : employeeContexts) {
				if (!alreadyInList(context, selectedItems)) {
					removeEmployeeContext(employee, context);
				}
			}
			for (Object context : selectedItems) {
				if (!alreadyInList(context, employeeContexts)) {
					addEmployeeContext(employee, (SkillContext) context);
				}
			}
		}
		close();
	}

	/**
	 * Check if an item is already stored
	 * 
	 * @param thing
	 * @param selectedItems
	 * @return whether it is stored or not
	 */
	private boolean alreadyInList(Object thing,
			List<? extends DatabaseObject> selectedItems) {
		if (selectedItems.get(0) instanceof Skill) {
			for (Object ls : selectedItems) {
				if (((Skill) thing).getId() == ((Skill) ls).getId()) {
					return true;
				}
			}
		} else if (selectedItems.get(0) instanceof Material) {
			for (Object ls : selectedItems) {
				if (((Material) thing).getId() == ((Material) ls).getId()) {
					return true;
				}
			}
		} else if (selectedItems.get(0) instanceof SkillContext) {
			for (Object ls : selectedItems) {
				if (((SkillContext) thing).getId() == ((SkillContext) ls)
						.getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public int createEmployee(Employee employee, List<Skill> selectedSkills) {
		open();
		employee.setId((int) createBasicEmployee(employee));
		for (Skill skill : selectedSkills) {
			addEmployeeSkill(employee, skill);
		}
		close();
		return employee.getId();
	}

}
