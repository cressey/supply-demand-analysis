package com.vjs.supplydemandanalysis.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.vjs.supplydemandanalysis.skills.ContextDetails;
import com.vjs.supplydemandanalysis.skills.DatabaseObject;
import com.vjs.supplydemandanalysis.skills.Material;
import com.vjs.supplydemandanalysis.skills.MaterialDetails;
import com.vjs.supplydemandanalysis.skills.Skill;
import com.vjs.supplydemandanalysis.skills.SkillContext;
import com.vjs.supplydemandanalysis.skills.SkillDetails;
import com.vjs.supplydemandanalysis.skills.SkillsSheet;

/**
 * Handle the database read/write access and methods for the Skill Sheets
 * 
 * @author Rob
 */
public class SkillsSheetDAO extends DatabaseStuff {
	public SkillsSheetDAO(Context c) {
		super(c);
	}

	public int createSkillSheet(String name) {
		open();
		// create skillsSheet
		ContentValues cv = new ContentValues();
		cv.put(SKILL_SHEET_NAME, name);
		int skillsSheetId = (int) myDatabase.insert(SKILLS_SHEET_TABLE, null,
				cv);
		close();
		return skillsSheetId;
	}

	public List<SkillsSheet> getAllSkillsSheets() {
		open();
		String[] columns = new String[] { SKILL_SHEET_ID, SKILL_SHEET_NAME };
		Cursor c = myDatabase.query(SKILLS_SHEET_TABLE, columns, null, null,
				null, null, SKILL_SHEET_NAME + " ASC");
		List<SkillsSheet> skillsSheets = new ArrayList<SkillsSheet>();
		int iId = c.getColumnIndex(SKILL_SHEET_ID);
		int iName = c.getColumnIndex(SKILL_SHEET_NAME);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				SkillsSheet s = new SkillsSheet();
				s.setId(c.getInt(iId));
				s.setName(c.getString(iName));
				skillsSheets.add(s);
			}
		}
		close();
		return skillsSheets;
	}

	public SkillsSheet getSkillsSheet(int id) {
		open();
		SkillsSheet s = new SkillsSheet();
		Cursor c = myDatabase.rawQuery("SELECT * FROM " + SKILLS_SHEET_TABLE
				+ " WHERE " + SKILL_SHEET_ID + " = ?",
				new String[] { Integer.toString(id) });
		int iName = c.getColumnIndex(SKILL_SHEET_NAME);
		if (c != null) {
			c.moveToFirst();
			s.setId(id);
			s.setName(c.getString(iName));
		}
		close();
		return s;
	}

	public void deleteSkillsSheet(int id) {
		open();
		myDatabase.delete(SKILLS_SHEET_TABLE, SKILL_SHEET_ID + " = ?",
				new String[] { Integer.toString(id) });
		myDatabase.delete(SKILLS_SHEET_SKILLS_TABLE, SKILL_SHEET_ID + " = ?",
				new String[] { Integer.toString(id) });
		close();
	}

	public void updateSkillsSheetName(SkillsSheet s) {
		open();
		ContentValues cv = new ContentValues();
		cv.put(SKILL_SHEET_NAME, s.getName());
		myDatabase.update(SKILLS_SHEET_TABLE, cv, SKILL_SHEET_ID + "= ?",
				new String[] { Integer.toString(s.getId()) });
		close();
	}

	/**
	 * Get all the skills belonging to a given skills sheet
	 * 
	 * @param skillsSheetId
	 * @return a list of skill
	 */
	public List<Skill> getSkillsSheetSkills(int skillsSheetId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM "
				+ SKILLS_SHEET_SKILLS_TABLE + " LEFT JOIN " + SKILL_TABLE
				+ " USING (" + SKILL_ID + ") WHERE " + SKILL_SHEET_ID
				+ " = ? ORDER BY " + SKILL_NAME + " ASC",
				new String[] { Integer.toString(skillsSheetId) });
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

	/**
	 * Get all the contexts belonging to a given skills sheet
	 * 
	 * @param skillsSheetId
	 * @return a list of contexts
	 */
	public List<Material> getSkillsSheetMaterials(int skillsSheetId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM "
				+ SKILLS_SHEET_MATERIAL_TABLE + " LEFT JOIN " + MATERIAL_TABLE
				+ " USING (" + MATERIAL_ID + ") WHERE " + SKILL_SHEET_ID
				+ " = ? ORDER BY " + MATERIAL_NAME + " ASC",
				new String[] { Integer.toString(skillsSheetId) });
		List<Material> materials = new ArrayList<Material>();
		int id = c.getColumnIndex(MATERIAL_ID);
		int name = c.getColumnIndex(MATERIAL_NAME);
		int materialGroup = c.getColumnIndex(MATERIAL_GROUP_ID);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				Material m = new Material();
				m.setId(c.getInt(id));
				m.setName(c.getString(name));
				m.setMaterialGroupId(c.getInt(materialGroup));
				materials.add(m);
			}
		}
		close();
		return materials;
	}

	/**
	 * Get all the contexts belonging to a given skills sheet
	 * 
	 * @param skillsSheetId
	 * @return a list of contexts
	 */
	public List<SkillContext> getSkillsSheetContexts(int skillsSheetId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM "
				+ SKILLS_SHEET_CONTEXT_TABLE + " LEFT JOIN " + CONTEXT_TABLE
				+ " USING (" + CONTEXT_ID + ") WHERE " + SKILL_SHEET_ID
				+ " = ? ORDER BY " + CONTEXT_NAME + " ASC",
				new String[] { Integer.toString(skillsSheetId) });
		List<SkillContext> contexts = new ArrayList<SkillContext>();
		int id = c.getColumnIndex(CONTEXT_ID);
		int name = c.getColumnIndex(CONTEXT_NAME);
		int contextGroup = c.getColumnIndex(CONTEXT_GROUP_ID);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				SkillContext sc = new SkillContext();
				sc.setId(c.getInt(id));
				sc.setName(c.getString(name));
				sc.setContextGroupId(c.getInt(contextGroup));
				contexts.add(sc);
			}
		}
		close();
		return contexts;
	}

	/**
	 * Add a skill to a skills sheet
	 * 
	 * @param skillsSheetId
	 * @param skillId
	 */
	public void addSkillsSheetSkill(int skillsSheetId, int skillId) {
		ContentValues cv = new ContentValues();
		cv.put(SKILL_SHEET_ID, skillsSheetId);
		cv.put(SKILL_ID, skillId);
		myDatabase.insert(SKILLS_SHEET_SKILLS_TABLE, null, cv);
	}

	/**
	 * Add a material to a skills sheet
	 * 
	 * @param skillsSheetId
	 * @param materialId
	 */
	public void addSkillsSheetMaterial(int skillsSheetId, int materialId) {
		ContentValues cv = new ContentValues();
		cv.put(SKILL_SHEET_ID, skillsSheetId);
		cv.put(MATERIAL_ID, materialId);
		myDatabase.insert(SKILLS_SHEET_MATERIAL_TABLE, null, cv);
	}

	/**
	 * Add a context to a skills sheet
	 * 
	 * @param skillsSheetId
	 * @param contextId
	 */
	public void addSkillsSheetContext(int skillsSheetId, int contextId) {
		ContentValues cv = new ContentValues();
		cv.put(SKILL_SHEET_ID, skillsSheetId);
		cv.put(CONTEXT_ID, contextId);
		myDatabase.insert(SKILLS_SHEET_CONTEXT_TABLE, null, cv);
	}

	/**
	 * Remove a skill from a skills sheet
	 * 
	 * @param skillsSheetId
	 * @param skillId
	 */
	public void removeSkillsSheetSkill(int skillsSheetId, int skillId) {
		ContentValues cv = new ContentValues();
		cv.put(SKILL_SHEET_ID, skillsSheetId);
		cv.put(SKILL_ID, skillId);
		myDatabase.delete(
				SKILLS_SHEET_SKILLS_TABLE,
				SKILL_SHEET_ID + " =? AND " + SKILL_ID + " =?",
				new String[] { Integer.toString(skillsSheetId),
						Integer.toString(skillId) });
	}

	/**
	 * Remove a material from a skills sheet
	 * 
	 * @param skillsSheetId
	 * @param materialId
	 */
	public void removeSkillsSheetMaterial(int skillsSheetId, int materialId) {
		ContentValues cv = new ContentValues();
		cv.put(SKILL_SHEET_ID, skillsSheetId);
		cv.put(MATERIAL_ID, materialId);
		myDatabase.delete(
				SKILLS_SHEET_MATERIAL_TABLE,
				SKILL_SHEET_ID + " =? AND " + MATERIAL_ID + " =?",
				new String[] { Integer.toString(skillsSheetId),
						Integer.toString(materialId) });
	}

	/**
	 * Remove a context from a skills sheet
	 * 
	 * @param skillsSheetId
	 * @param contextId
	 */
	public void removeSkillsSheetContext(int skillsSheetId, int contextId) {
		ContentValues cv = new ContentValues();
		cv.put(SKILL_SHEET_ID, skillsSheetId);
		cv.put(CONTEXT_ID, contextId);
		myDatabase.delete(
				SKILLS_SHEET_CONTEXT_TABLE,
				SKILL_SHEET_ID + " =? AND " + CONTEXT_ID + " =?",
				new String[] { Integer.toString(skillsSheetId),
						Integer.toString(contextId) });
	}

	// /**
	// * Update the skills sheet with a new list of selected items
	// *
	// * @param skillsSheet
	// * @param selectedItems
	// */
	// public void updateSkillsSheet(SkillsSheet skillsSheet,
	// List<? extends DatabaseObject> selectedItems) {
	// open();
	// updateSkillsSheetName(skillsSheet);
	// int skillSheetId = skillsSheet.getId();
	// List<Skill> currentSheetSkills = getSkillsSheetSkills(skillSheetId);
	// List<Material> currentSheetMaterials =
	// getSkillsSheetMaterials(skillSheetId);
	// List<SkillContext> currentSheetContexts =
	// getSkillsSheetContexts(skillSheetId);
	// for (Object object : selectedItems) {
	// if (object instanceof Skill) {
	// for (Skill skill : skillsSheetSkills) {
	// if (!alreadyInList(skill, skillsSheetSkills)) {
	// removeSkillsSheetSkills(skillSheetId, skill.getId());
	// }
	// }
	// } else if (selectedItems.get(0) instanceof Material) {
	// open();
	// for (Material material : currentSheetMaterials) {
	// if (!alreadyInList(material, selectedItems)) {
	// removeSkillsSheetMaterial(skillSheetId,
	// material.getId());
	// }
	// }
	// } else if (selectedItems.get(0) instanceof SkillContext) {
	// open();
	// for (SkillContext context : currentSheetContexts) {
	// if (!alreadyInList(context, selectedItems)) {
	// removeSkillsSheetContext(skillSheetId, context.getId());
	// }
	// }
	// }
	// }
	// close();
	// }

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

	/**
	 * Get all details for all skills for a chosen skills sheet
	 * 
	 * @param skillsSheetId
	 * @return a list of all skill information
	 */
	public List<SkillDetails> getSkillsSheetSkillsWithDetails(int skillsSheetId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM "
				+ SKILLS_SHEET_SKILLS_TABLE + " LEFT JOIN " + SKILL_TABLE
				+ " USING ('" + SKILL_ID + "') LEFT JOIN " + SKILL_GROUP_TABLE
				+ " USING ('" + SKILL_GROUP_ID + "') LEFT JOIN "
				+ SUBJECT_TABLE + " USING ('" + SUBJECT_ID + "') WHERE "
				+ SKILL_SHEET_ID + " = ? ORDER BY " + SUBJECT_NAME + " ASC, "
				+ SKILL_GROUP_NAME + " ASC, " + SKILL_NAME + " ASC ",
				new String[] { Integer.toString(skillsSheetId) });
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

	/**
	 * Get all details for all contexts for a chosen skills sheet
	 * 
	 * @param skillsSheetId
	 * @return
	 */
	public List<MaterialDetails> getSkillsSheetMaterialsWithDetails(
			int skillsSheetId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM "
				+ SKILLS_SHEET_MATERIAL_TABLE + " LEFT JOIN " + MATERIAL_TABLE
				+ " USING ('" + MATERIAL_ID + "') LEFT JOIN "
				+ MATERIAL_GROUP_TABLE + " USING ('" + MATERIAL_GROUP_ID
				+ "') WHERE " + SKILL_SHEET_ID + " = ? ORDER BY "
				+ MATERIAL_GROUP_NAME + " ASC, " + MATERIAL_NAME + " ASC ",
				new String[] { Integer.toString(skillsSheetId) });
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

	/**
	 * Get all details for all contexts for a chosen skills sheet
	 * 
	 * @param skillsSheetId
	 * @return
	 */
	public List<ContextDetails> getSkillsSheetContextsWithDetails(
			int skillsSheetId) {
		open();
		Cursor c = myDatabase.rawQuery("SELECT * FROM "
				+ SKILLS_SHEET_CONTEXT_TABLE + " LEFT JOIN " + CONTEXT_TABLE
				+ " USING ('" + CONTEXT_ID + "') LEFT JOIN "
				+ CONTEXT_GROUP_TABLE + " USING ('" + CONTEXT_GROUP_ID
				+ "') WHERE " + SKILL_SHEET_ID + " = ? ORDER BY "
				+ CONTEXT_GROUP_NAME + " ASC, " + CONTEXT_NAME + " ASC ",
				new String[] { Integer.toString(skillsSheetId) });
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
}
