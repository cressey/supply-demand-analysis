package com.vjs.supplydemandanalysis.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.vjs.supplydemandanalysis.activities.AnalysisActivity.BarType;
import com.vjs.supplydemandanalysis.skills.BarObject;
import com.vjs.supplydemandanalysis.skills.SkillAnalysis;

public class DatabaseDAO extends DatabaseStuff {
	public DatabaseDAO(Context c) {
		super(c);
	}

	public List<BarObject> getSubjectCountsForSkillsSheet(
			final List<Integer> skillsSheetIds) {
		String select = "SELECT count(*), " + SUBJECT_NAME + ", " + SUBJECT_ID
				+ " FROM " + SKILLS_SHEET_SKILLS_TABLE + " LEFT JOIN "
				+ SKILL_TABLE + " USING (" + SKILL_ID + ") LEFT JOIN "
				+ SKILL_GROUP_TABLE + " USING (" + SKILL_GROUP_ID
				+ ") LEFT JOIN " + SUBJECT_TABLE + " USING (" + SUBJECT_ID
				+ ")";
		String where = " WHERE "
				+ getWhereClauseForList(SKILL_SHEET_ID, skillsSheetIds);
		String groupBy = " GROUP BY " + SUBJECT_ID;
		String orderBy = " ORDER BY " + SUBJECT_NAME + " ASC";
		// create selection args - MUST be in the same order as where clause
		// convert to string array
		String[] argsArray = new String[skillsSheetIds.size()];
		argsArray = getStringArrayFromIntegers(skillsSheetIds).toArray(
				argsArray);
		List<BarObject> bars = new ArrayList<BarObject>();
		open();
		Cursor c = myDatabase.rawQuery(select + where + groupBy + orderBy,
				argsArray);
		int iId = c.getColumnIndex(SUBJECT_ID);
		int iName = c.getColumnIndex(SUBJECT_NAME);
		int iCount = c.getColumnIndex("count(*)");
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				BarObject s = new BarObject();
				s.setId(c.getInt(iId));
				s.setName(c.getString(iName));
				s.setDemand(c.getInt(iCount));
				s.setType(BarType.SUBJECT);
				bars.add(s);
			}
		}
		close();
		return bars;
	}

	public List<BarObject> getEmployeeCountForSubjectFromSkillsSheet(
			final List<Integer> skillsSheetIds,
			final List<Integer> employeeIds, List<BarObject> bars) {
		String select = "SELECT count(*), " + SUBJECT_NAME + ", " + SUBJECT_ID
				+ " FROM " + EMPLOYEE_SKILL_TABLE + " LEFT JOIN "
				+ SKILLS_SHEET_SKILLS_TABLE + " USING (" + SKILL_ID
				+ ") LEFT JOIN " + SKILL_TABLE + " USING (" + SKILL_ID
				+ ") LEFT JOIN " + SKILL_GROUP_TABLE + " USING ("
				+ SKILL_GROUP_ID + ") LEFT JOIN " + SUBJECT_TABLE + " USING ("
				+ SUBJECT_ID + ")";
		String where = " WHERE "
				+ getWhereClauseForList(SKILL_SHEET_ID, skillsSheetIds)
				+ " AND " + getWhereClauseForList(EMPLOYEE_ID, employeeIds);
		String groupBy = " GROUP BY " + SUBJECT_ID + ", "
				+ SKILLS_SHEET_SKILLS_TABLE + "." + SKILL_ID + ", "
				+ EMPLOYEE_ID;
		String orderBy = " ORDER BY " + SUBJECT_NAME + " ASC";
		// String select = "SELECT count( DISTINCT " + SKILL_ID + ", "
		// + EMPLOYEE_ID + " ), " + SUBJECT_NAME + ", " + SUBJECT_ID
		// + " FROM " + EMPLOYEE_SKILL_TABLE + " LEFT JOIN "
		// + SKILLS_SHEET_SKILLS_TABLE + " USING (" + SKILL_ID
		// + ") LEFT JOIN " + SKILL_TABLE + " USING (" + SKILL_ID
		// + ") LEFT JOIN " + SKILL_GROUP_TABLE + " USING ("
		// + SKILL_GROUP_ID + ") LEFT JOIN " + SUBJECT_TABLE + " USING ("
		// + SUBJECT_ID + ")";
		// String where = " WHERE "
		// + getWhereClauseForList(SKILL_SHEET_ID, skillsSheetIds)
		// + " AND " + getWhereClauseForList(EMPLOYEE_ID, employeeIds);
		// String groupBy = " GROUP BY " + SUBJECT_ID;
		// String orderBy = " ORDER BY " + SUBJECT_NAME + " ASC";
		// create selection args - MUST be in the same order as where clause
		List<String> args = new ArrayList<String>();
		args.addAll(getStringArrayFromIntegers(skillsSheetIds));
		args.addAll(getStringArrayFromIntegers(employeeIds));
		// convert to string array
		String[] argsArray = new String[args.size()];
		argsArray = args.toArray(argsArray);
		open();
		Cursor c = myDatabase.rawQuery(select + where + groupBy + orderBy,
				argsArray);
		int iId = c.getColumnIndex(SUBJECT_ID);
		int iCount = c.getColumnIndex("count(*)");
		int i = 0;
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				while (i < bars.size() && c.getInt(iId) != bars.get(i).getId()) {
					i++;
				}
				if (i < bars.size()) {
					// merge data together
					bars.get(i).setSupply(c.getInt(iCount));
				}
			}
		}
		close();
		return bars;
	}

	public List<BarObject> getSkillsGroupCountsForSkillsSheet(
			final List<Integer> skillsSheetIds, final int subjectId) {
		String select = "SELECT count(*), " + SKILL_GROUP_NAME + ", "
				+ SKILL_GROUP_ID + " FROM " + SKILLS_SHEET_SKILLS_TABLE
				+ " LEFT JOIN " + SKILL_TABLE + " USING (" + SKILL_ID
				+ ") LEFT JOIN " + SKILL_GROUP_TABLE + " USING ("
				+ SKILL_GROUP_ID + ") LEFT JOIN " + SUBJECT_TABLE + " USING ("
				+ SUBJECT_ID + ")";
		String where = " WHERE "
				+ getWhereClauseForList(SKILL_SHEET_ID, skillsSheetIds)
				+ " AND " + SUBJECT_ID + " = ? ";
		String groupBy = " GROUP BY " + SKILL_GROUP_ID;
		String orderBy = " ORDER BY " + SKILL_GROUP_NAME + " ASC";
		// create selection args - MUST be in the same order as where clause
		List<String> args = new ArrayList<String>();
		args.addAll(getStringArrayFromIntegers(skillsSheetIds));
		args.add(String.valueOf(subjectId));
		// convert to string array
		String[] argsArray = new String[args.size()];
		argsArray = args.toArray(argsArray);
		List<BarObject> bars = new ArrayList<BarObject>();
		open();
		Cursor c = myDatabase.rawQuery(select + where + groupBy + orderBy,
				argsArray);
		int iId = c.getColumnIndex(SKILL_GROUP_ID);
		int iName = c.getColumnIndex(SKILL_GROUP_NAME);
		int iCount = c.getColumnIndex("count(*)");
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				BarObject s = new BarObject();
				s.setId(c.getInt(iId));
				s.setName(c.getString(iName));
				s.setDemand(c.getInt(iCount));
				s.setType(BarType.SKILLGROUP);
				bars.add(s);
			}
		}
		close();
		return bars;
	}

	public List<BarObject> getEmployeeCountForSkillsGroupsFromSkillsSheet(
			List<Integer> skillsSheetIds, List<Integer> employeeIds,
			final int subjectId, List<BarObject> bars) {
		// String select = "SELECT count( DISTINCT (" +
		// SKILLS_SHEET_SKILLS_TABLE
		// + "." + SKILL_ID + ", " + EMPLOYEE_ID + " )) as count, "
		// + SKILL_GROUP_NAME + ", " + SKILL_GROUP_ID + " FROM "
		// + EMPLOYEE_SKILL_TABLE + " LEFT JOIN "
		// + SKILLS_SHEET_SKILLS_TABLE + " USING (" + SKILL_ID
		// + ") LEFT JOIN " + SKILL_TABLE + " USING (" + SKILL_ID
		// + ") LEFT JOIN " + SKILL_GROUP_TABLE + " USING ("
		// + SKILL_GROUP_ID + ")";
		String select = "SELECT count(*), " + SKILL_GROUP_NAME + ", "
				+ SKILL_GROUP_ID + " FROM " + EMPLOYEE_SKILL_TABLE
				+ " LEFT JOIN " + SKILLS_SHEET_SKILLS_TABLE + " USING ("
				+ SKILL_ID + ") LEFT JOIN " + SKILL_TABLE + " USING ("
				+ SKILL_ID + ") LEFT JOIN " + SKILL_GROUP_TABLE + " USING ("
				+ SKILL_GROUP_ID + ")";
		String where = " WHERE "
				+ getWhereClauseForList(SKILL_SHEET_ID, skillsSheetIds)
				+ " AND " + getWhereClauseForList(EMPLOYEE_ID, employeeIds)
				+ " AND " + SUBJECT_ID + " = ? ";
		String groupBy = " GROUP BY " + SKILL_GROUP_ID + ", "
				+ SKILLS_SHEET_SKILLS_TABLE + "." + SKILL_ID + ", "
				+ EMPLOYEE_ID;
		String orderBy = " ORDER BY " + SKILL_GROUP_NAME + " ASC";
		// create selection args - MUST be in the same order as where clause
		List<String> args = new ArrayList<String>();
		args.addAll(getStringArrayFromIntegers(skillsSheetIds));
		args.addAll(getStringArrayFromIntegers(employeeIds));
		args.add(String.valueOf(subjectId));
		// convert to string array
		String[] argsArray = new String[args.size()];
		argsArray = args.toArray(argsArray);
		open();
		Cursor c = myDatabase.rawQuery(select + where + groupBy + orderBy,
				argsArray);
		int iId = c.getColumnIndex(SKILL_GROUP_ID);
		int iCount = c.getColumnIndex("count(*)");
		int i = 0;
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				while (i < bars.size() && c.getInt(iId) != bars.get(i).getId()) {
					i++;
				}
				if (i < bars.size()) {
					// merge data together
					bars.get(i).setSupply(c.getInt(iCount));
				}
			}
		}
		close();
		return bars;
	}

	private List<BarObject> getSkillsCountsForSkillsSheet(
			List<Integer> skillsSheetIds, int skillGroupId) {
		// create sections of sql
		String select = "SELECT count(*), " + SKILL_NAME + ", " + SKILL_ID
				+ " FROM " + SKILLS_SHEET_SKILLS_TABLE + " LEFT JOIN "
				+ SKILL_TABLE + " USING (" + SKILL_ID + ") ";
		String where = " WHERE "
				+ getWhereClauseForList(SKILL_SHEET_ID, skillsSheetIds)
				+ " AND " + SKILL_GROUP_ID + " = ? ";
		String groupBy = " GROUP BY " + SKILL_ID;
		String orderBy = " ORDER BY " + SKILL_NAME + " ASC";
		// create selection args - MUST be in the same order as where clause
		List<String> args = new ArrayList<String>();
		args.addAll(getStringArrayFromIntegers(skillsSheetIds));
		args.add(String.valueOf(skillGroupId));
		// convert to string array
		String[] argsArray = new String[args.size()];
		argsArray = args.toArray(argsArray);
		// get the data
		List<BarObject> bars = new ArrayList<BarObject>();
		open();
		Cursor c = myDatabase.rawQuery(select + where + groupBy + orderBy,
				argsArray);
		int iId = c.getColumnIndex(SKILL_ID);
		int iName = c.getColumnIndex(SKILL_NAME);
		int iCount = c.getColumnIndex("count(*)");
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				BarObject s = new BarObject();
				s.setId(c.getInt(iId));
				s.setName(c.getString(iName));
				s.setDemand(c.getInt(iCount));
				s.setType(BarType.SKILL);
				bars.add(s);
			}
		}
		close();
		return bars;
	}

	private List<BarObject> getEmployeeCountForSkillsFromSkillsSheet(
			List<Integer> skillsSheetIds, List<Integer> employeeIds,
			int skillGroupId, List<BarObject> bars) {
		// String select = "SELECT count(*), " + SKILL_NAME + ", " + SKILL_TABLE
		// + "." + SKILL_ID + " FROM " + EMPLOYEE_SKILL_TABLE
		// + " LEFT JOIN " + SKILLS_SHEET_SKILLS_TABLE + " USING ("
		// + SKILL_ID + ") LEFT JOIN " + SKILL_TABLE + " USING ("
		// + SKILL_ID + ")";
		// String where = " WHERE "
		// + getWhereClauseForList(SKILL_SHEET_ID, skillsSheetIds)
		// + " AND " + getWhereClauseForList(EMPLOYEE_ID, employeeIds)
		// + " AND " + SKILL_GROUP_ID + " = ? ";
		// String groupBy = " GROUP BY " + SKILL_TABLE + "." + SKILL_ID;
		// String orderBy = " ORDER BY " + SKILL_NAME + " ASC";
		// String select = "SELECT count(*), " + SKILL_TABLE + "." + SKILL_ID
		// + " FROM " + EMPLOYEE_SKILL_TABLE + " LEFT JOIN "
		// + SKILLS_SHEET_SKILLS_TABLE + " USING (" + SKILL_ID
		// + ") LEFT JOIN " + SKILL_TABLE + " USING (" + SKILL_ID + ")";
		// String where = " WHERE "
		// + getWhereClauseForList(SKILL_SHEET_ID, skillsSheetIds)
		// + " AND " + getWhereClauseForList(EMPLOYEE_ID, employeeIds)
		// + " AND " + SKILL_GROUP_ID + " = ? ";
		// // String groupBy = " GROUP BY " + SKILL_TABLE + "." + SKILL_ID;
		// String groupBy = " GROUP BY " + SKILLS_SHEET_SKILLS_TABLE + "."
		// + SKILL_ID + ", " + EMPLOYEE_ID;
		// String orderBy = " ORDER BY " + SKILL_NAME + " ASC";
		String select = "SELECT count(DISTINCT (" + SKILLS_SHEET_SKILLS_TABLE
				+ "." + SKILL_ID + " || ',' || " + EMPLOYEE_SKILL_TABLE + "."
				+ EMPLOYEE_ID + ") ) AS count, " + SKILL_TABLE + "." + SKILL_ID
				+ ", " + SKILL_TABLE + "." + SKILL_NAME + " FROM "
				+ EMPLOYEE_SKILL_TABLE + " LEFT JOIN "
				+ SKILLS_SHEET_SKILLS_TABLE + " USING (" + SKILL_ID
				+ ") LEFT JOIN " + SKILL_TABLE + " USING (" + SKILL_ID + ")";
		String where = " WHERE "
				+ getWhereClauseForList(SKILL_SHEET_ID, skillsSheetIds)
				+ " AND " + getWhereClauseForList(EMPLOYEE_ID, employeeIds)
				+ " AND " + SKILL_GROUP_ID + " = ? ";
		// String groupBy = " GROUP BY " + SKILL_TABLE + "." + SKILL_ID;
		String groupBy = " GROUP BY " + EMPLOYEE_SKILL_TABLE + "." + SKILL_ID;
		String orderBy = " ORDER BY " + SKILL_NAME + " ASC";
		// create selection args - MUST be in the same order as where clause
		List<String> args = new ArrayList<String>();
		args.addAll(getStringArrayFromIntegers(skillsSheetIds));
		args.addAll(getStringArrayFromIntegers(employeeIds));
		args.add(String.valueOf(skillGroupId));
		// convert to string array
		String[] argsArray = new String[args.size()];
		argsArray = args.toArray(argsArray);
		open();
		Cursor c = myDatabase.rawQuery(select + where + groupBy + orderBy,
				argsArray);
		int iId = c.getColumnIndex(SKILL_ID);
		int iCount = c.getColumnIndex("count");
		int i = 0;
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				while (i < bars.size() && c.getInt(iId) != bars.get(i).getId()) {
					i++;
				}
				if (i < bars.size()) {
					// merge data together
					bars.get(i).setSupply(c.getInt(iCount));
				}
			}
		}
		close();
		return bars;
	}

	public List<BarObject> getSubjectSupplyAndDemandForSkillsSheet(
			List<Integer> skillsSheetIds, List<Integer> employeeIds) {
		List<BarObject> bars = getSubjectCountsForSkillsSheet(skillsSheetIds);
		bars = getEmployeeCountForSubjectFromSkillsSheet(skillsSheetIds,
				employeeIds, bars);
		return bars;
	}

	public List<BarObject> getSubjectDemandForSkillsSheet(
			List<Integer> skillsSheetIds, List<Integer> employeeIds) {
		List<BarObject> bars = getSubjectCountsForSkillsSheet(skillsSheetIds);
		// bars = getEmployeeCountForSubjectFromSkillsSheet( skillsSheetIds,
		// employeeIds, bars );
		return bars;
	}

	public List<BarObject> getSkillGroupSupplyAndDemandForSkillsSheet(
			List<Integer> skillsSheetIds, List<Integer> employeeIds,
			int subjectId) {
		List<BarObject> bars = getSkillsGroupCountsForSkillsSheet(
				skillsSheetIds, subjectId);
		bars = getEmployeeCountForSkillsGroupsFromSkillsSheet(skillsSheetIds,
				employeeIds, subjectId, bars);
		return bars;
	}

	public List<BarObject> getSkillGroupDemandForSkillsSheet(
			List<Integer> skillsSheetIds, List<Integer> employeeIds,
			int subjectId) {
		List<BarObject> bars = getSkillsGroupCountsForSkillsSheet(
				skillsSheetIds, subjectId);
		bars = getEmployeeCountForSkillsGroupsFromSkillsSheet(skillsSheetIds,
				employeeIds, subjectId, bars);
		return bars;
	}

	public List<BarObject> getSkillSupplyAndDemandForSkillsSheet(
			List<Integer> skillsSheetIds, List<Integer> employeeIds,
			int skillGroupId) {
		List<BarObject> bars = getSkillsCountsForSkillsSheet(skillsSheetIds,
				skillGroupId);
		bars = getEmployeeCountForSkillsFromSkillsSheet(skillsSheetIds,
				employeeIds, skillGroupId, bars);
		return bars;
	}

	/**
	 * Converts an Integer arrayList into a String array
	 * 
	 * @param skillsSheetIds
	 * @return
	 */
	private List<String> getStringArrayFromIntegers(List<Integer> skillsSheetIds) {
		List<String> stringValues = new ArrayList<String>(skillsSheetIds.size());
		for (Integer id : skillsSheetIds) {
			stringValues.add(String.valueOf(id));
		}
		return stringValues;
	}

	/**
	 * Gets the where clause section for a column name matching the ids.
	 * 
	 * Will return either ' name = ? ' if there is 1 in the list, or ' name IN
	 * (?,?,...,?) ' if there are multiple, or ' 1=1 ' if there are none.
	 * 
	 * @param columnName
	 *            Name of the column we are looking to match on
	 * @param skillsSheetIds
	 *            List of IDs that are part of the where clause
	 * @return
	 */
	private String getWhereClauseForList(String columnName,
			List<Integer> skillsSheetIds) {
		if (skillsSheetIds.size() == 0) {
			return " 1=1 ";
		} else if (skillsSheetIds.size() == 1) {
			return " " + columnName + " = ? ";
		} else {
			String inClause = " " + columnName + " IN ( ";
			for (Integer id : skillsSheetIds) {
				inClause += "?,";
			}
			// remove trailing comma
			inClause = inClause.substring(0, inClause.length() - 1);
			inClause += " ) ";
			return inClause;
		}
	}

	/**
	 * Returns an indication of whether each subjects skills can be met
	 * 
	 * @param skillSheetIds
	 * @param employeeIds
	 * @return
	 */
	public boolean getSubjectOverview(int subjectId,
			List<Integer> skillsSheetIds, List<Integer> employeeIds) {
		boolean meetDemand = true;
		// get skill groups associated with this subject
		List<Integer> skillGroups = getSkillGroupsForSubject(subjectId);
		for (int skillGroupId : skillGroups) {
			List<BarObject> bars = getSkillSupplyAndDemandForSkillsSheet(
					skillsSheetIds, employeeIds, skillGroupId);
			for (BarObject bar : bars) {
				// if we dont meet a demand then return early
				if (bar.getSupply() < bar.getDemand()) {
					return false;
				}
			}
		}
		return meetDemand;
	}

	public boolean getDisciplineOverview(List<Integer> skillSheetIds,
			List<Integer> employeeIds) {
		boolean meetsDemand = true;
		List<Integer> ids = new ArrayList<Integer>();
		open();
		Cursor c = myDatabase.rawQuery("SELECT " + SUBJECT_ID + " FROM "
				+ SUBJECT_TABLE, new String[] {});
		int iId = c.getColumnIndex(SUBJECT_ID);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				ids.add(c.getInt(iId));
			}
		}
		close();
		for (Integer id : ids) {
			if (!getSubjectOverview(id, skillSheetIds, employeeIds)) {
				return false;
			}
		}
		return meetsDemand;
	}

	public boolean getSkillGroupOverview(int skillGroupId,
			ArrayList<Integer> skillSheetIds, ArrayList<Integer> employeeIds) {
		boolean meetDemand = true;
		// get skills associated with this skill group
		List<Integer> skills = getSkillsForSkillGroup(skillGroupId);
		for (int skillId : skills) {
			List<BarObject> bars = getSkillSupplyAndDemandForSkillsSheet(
					skillSheetIds, employeeIds, skillGroupId);
			for (BarObject bar : bars) {
				// if we dont meet a demand then return early
				if (bar.getSupply() < bar.getDemand()) {
					return false;
				}
			}
		}
		return meetDemand;
	}

	private List<Integer> getSkillsForSkillGroup(int skillGroupId) {
		List<Integer> ids = new ArrayList<Integer>();
		open();
		Cursor c = myDatabase.rawQuery("SELECT " + SKILL_ID + " FROM "
				+ SKILL_TABLE + " WHERE " + SKILL_GROUP_ID + " = ? ",
				new String[] { Long.toString(skillGroupId) });
		int iId = c.getColumnIndex(SKILL_ID);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				ids.add(c.getInt(iId));
			}
		}
		close();
		return ids;
	}

	private List<Integer> getSkillGroupsForSubject(long subjectId) {
		List<Integer> ids = new ArrayList<Integer>();
		open();
		Cursor c = myDatabase.rawQuery("SELECT " + SKILL_GROUP_ID + " FROM "
				+ SKILL_GROUP_TABLE + " WHERE " + SUBJECT_ID + " = ? ",
				new String[] { Long.toString(subjectId) });
		int iId = c.getColumnIndex(SKILL_GROUP_ID);
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				ids.add(c.getInt(iId));
			}
		}
		close();
		return ids;
	}

	public List<SkillAnalysis> getSkillsAnalysis(
			ArrayList<Integer> skillSheetIds, ArrayList<Integer> employeeIds) {
		List<SkillAnalysis> skillAnalysis = getSkillsAnalysisDemand(skillSheetIds);
		skillAnalysis = getSkillsAnalysisSupply(skillSheetIds, employeeIds,
				skillAnalysis);
		return skillAnalysis;
	}

	private List<SkillAnalysis> getSkillsAnalysisSupply(
			ArrayList<Integer> skillSheetIds, ArrayList<Integer> employeeIds,
			List<SkillAnalysis> skillAnalysis) {
		String select = "SELECT count(DISTINCT (" + SKILLS_SHEET_SKILLS_TABLE
				+ "." + SKILL_ID + " || ',' || " + EMPLOYEE_SKILL_TABLE + "."
				+ EMPLOYEE_ID + ") ) AS count, " + SKILL_TABLE + "." + SKILL_ID
				+ " FROM " + EMPLOYEE_SKILL_TABLE + " LEFT JOIN "
				+ SKILLS_SHEET_SKILLS_TABLE + " USING (" + SKILL_ID
				+ ") LEFT JOIN " + SKILL_TABLE + " USING (" + SKILL_ID + ")";
		String where = " WHERE "
				+ getWhereClauseForList(SKILL_SHEET_ID, skillSheetIds)
				+ " AND " + getWhereClauseForList(EMPLOYEE_ID, employeeIds);
		// String groupBy = " GROUP BY " + SKILL_TABLE + "." + SKILL_ID;
		String groupBy = " GROUP BY " + EMPLOYEE_SKILL_TABLE + "." + SKILL_ID;
		String orderBy = " ORDER BY " + SKILL_NAME + " ASC";
		// create selection args - MUST be in the same order as where clause
		List<String> args = new ArrayList<String>();
		args.addAll(getStringArrayFromIntegers(skillSheetIds));
		args.addAll(getStringArrayFromIntegers(employeeIds));
		// convert to string array
		String[] argsArray = new String[args.size()];
		argsArray = args.toArray(argsArray);
		open();
		Cursor c = myDatabase.rawQuery(select + where + groupBy + orderBy,
				argsArray);
		int iId = c.getColumnIndex(SKILL_ID);
		int iCount = c.getColumnIndex("count");
		int i = 0;
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				int skillId = c.getInt(iId);
				for (SkillAnalysis sa : skillAnalysis) {
					if (sa.getSkillId() == skillId) {
						sa.setSupply(c.getInt(iCount));
						break;
					}
				}
			}
		}
		close();
		return skillAnalysis;
	}

	private List<SkillAnalysis> getSkillsAnalysisDemand(
			List<Integer> skillsSheetIds) {
		// create sections of sql
		// String select = "SELECT count(*), " + SKILL_NAME + ", " + SKILL_ID
		// + " FROM " + SKILLS_SHEET_SKILLS_TABLE + " LEFT JOIN "
		// + SKILL_TABLE + " USING (" + SKILL_ID + ") ";

		String select = "SELECT count(*), " + SKILL_NAME + ", " + SKILL_ID
				+ "," + SUBJECT_NAME + ", " + SUBJECT_ID + ", "
				+ SKILL_GROUP_NAME + ", " + SKILL_GROUP_ID + " FROM "
				+ SKILLS_SHEET_SKILLS_TABLE + " LEFT JOIN " + SKILL_TABLE
				+ " USING (" + SKILL_ID + ") LEFT JOIN " + SKILL_GROUP_TABLE
				+ " USING (" + SKILL_GROUP_ID + ") LEFT JOIN " + SUBJECT_TABLE
				+ " USING (" + SUBJECT_ID + ")";

		String where = " WHERE "
				+ getWhereClauseForList(SKILL_SHEET_ID, skillsSheetIds);
		String groupBy = " GROUP BY " + SKILL_ID;
		String orderBy = " ORDER BY " + SKILL_NAME + " ASC";
		// create selection args - MUST be in the same order as where clause
		List<String> args = new ArrayList<String>();
		args.addAll(getStringArrayFromIntegers(skillsSheetIds));
		// convert to string array
		String[] argsArray = new String[args.size()];
		argsArray = args.toArray(argsArray);
		// get the data
		List<SkillAnalysis> skillAnalysis = new ArrayList<SkillAnalysis>();
		open();
		Cursor c = myDatabase.rawQuery(select + where + groupBy + orderBy,
				argsArray);
		int iId = c.getColumnIndex(SKILL_ID);
		int iName = c.getColumnIndex(SKILL_NAME);
		int iSubject = c.getColumnIndex(SUBJECT_NAME);
		int iSubjectId = c.getColumnIndex(SUBJECT_ID);
		int iGroup = c.getColumnIndex(SKILL_GROUP_NAME);
		int iGroupId = c.getColumnIndex(SKILL_GROUP_ID);
		int iCount = c.getColumnIndex("count(*)");
		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				SkillAnalysis sa = new SkillAnalysis();
				sa.setSkillId(c.getInt(iId));
				sa.setSkillName(c.getString(iName));
				sa.setDemand(c.getInt(iCount));
				sa.setSkillGroup(c.getString(iGroup));
				sa.setSkillGroupId(c.getInt(iGroupId));
				sa.setSubject(c.getString(iSubject));
				sa.setSubjectId(c.getInt(iSubjectId));
				skillAnalysis.add(sa);
			}
		}
		close();
		return skillAnalysis;
	}
}
