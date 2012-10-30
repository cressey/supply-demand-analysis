package com.vjs.supplydemandanalysis.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vjs.supplydemandanalysis.skills.Discipline;
import com.vjs.supplydemandanalysis.skills.Skill;
import com.vjs.supplydemandanalysis.skills.SkillGroup;
import com.vjs.supplydemandanalysis.skills.Subject;

public class DatabaseStuff {
	private static final String DATABASE_NAME = "Skill Analysis";
	/* Employee table */
	protected static final String EMPLOYEE_TABLE = "employeeTable";
	public static final String EMPLOYEE_ID = "employeeTableId";
	public static final String EMPLOYEE_NAME = "employeeId";
	/* Employee skill table */
	protected static final String EMPLOYEE_SKILL_TABLE = "employeeSkillTable";
	public static final String EMPLOYEE_SKILL_ID = "employeeSkillId";
	// public static final String EMPLOYEE_ID = "employeeTableId";
	// public static final String SKILL_ID = "SkillId";
	/* Employee material table */
	protected static final String EMPLOYEE_MATERIAL_TABLE = "employeeMaterialTable";
	public static final String EMPLOYEE_MATERIAL_ID = "employeeMaterialId";
	/* Employee context table */
	protected static final String EMPLOYEE_CONTEXT_TABLE = "employeeContextTable";
	public static final String EMPLOYEE_CONTEXT_ID = "employeeContextId";
	/* SKILL table */
	protected static final String SKILL_TABLE = "SkillTable";
	public static final String SKILL_ID = "SkillId";
	public static final String SKILL_NAME = "SkillName";
	/* MATERIAL table */
	protected static final String MATERIAL_TABLE = "MaterialTable";
	public static final String MATERIAL_ID = "MaterialId";
	public static final String MATERIAL_NAME = "MaterialName";
	/* SkillContext table */
	protected static final String CONTEXT_TABLE = "ContextTable";
	public static final String CONTEXT_ID = "ContextId";
	public static final String CONTEXT_NAME = "ContextName";
	/* SKILL Group table */
	protected static final String SKILL_GROUP_TABLE = "SkillGroupTable";
	public static final String SKILL_GROUP_ID = "SkillGroupId";
	public static final String SKILL_GROUP_NAME = "SkillGroupName";
	/* Material Group table */
	protected static final String MATERIAL_GROUP_TABLE = "MaterialGroupTable";
	public static final String MATERIAL_GROUP_ID = "MaterialGroupId";
	public static final String MATERIAL_GROUP_NAME = "MaterialGroupName";
	public static final String MATERIAL_DISCIPLINE_GROUP = "disciplineGroup";
	/* SkillContext Group table */
	protected static final String CONTEXT_GROUP_TABLE = "ContextGroupTable";
	public static final String CONTEXT_GROUP_ID = "ContextGroupId";
	public static final String CONTEXT_GROUP_NAME = "ContextGroupName";
	public static final String CONTEXT_DISCIPLINE_GROUP = "disciplineGroup";
	/* Subject table */
	protected static final String SUBJECT_TABLE = "subjectGroupTable";
	public static final String SUBJECT_ID = "subjectId";
	public static final String SUBJECT_NAME = "subjectName";
	public static final String SUBJECT_DISCIPLINE_GROUP = "disciplineGroup";
	/* Discipline table */
	protected static final String DISCIPLINE_TABLE = "disciplineTable";
	public static final String DISCIPLINE_ID = "disicplineId";
	public static final String DISCIPLINE_NAME = "disciplineName";
	/* SkillsSheet table */
	protected static final String SKILLS_SHEET_TABLE = "skillsSheetTable";
	public static final String SKILL_SHEET_ID = "skillSheetId";
	public static final String SKILL_SHEET_NAME = "skillSheetName";
	/* SkillsSheet skills table */
	protected static final String SKILLS_SHEET_SKILLS_TABLE = "skillsSheetSkillsTable";
	public static final String SKILL_SHEET_SKILL_ID = "skillSheetSkillId";
	/* SkillsSheet material table */
	protected static final String SKILLS_SHEET_MATERIAL_TABLE = "skillsSheetMaterialTable";
	public static final String SKILL_SHEET_MATERIAL_ID = "skillSheetMaterialId";
	/* SkillsSheet contexts table */
	protected static final String SKILLS_SHEET_CONTEXT_TABLE = "skillsSheetContextsTable";
	public static final String SKILL_SHEET_CONTEXT_ID = "skillSheetContextId";
	/* Settings table */
	protected static final String SETTINGS_TABLE = "settingsTable";
	public static final String SETTING_NAME = "settingName";
	public static final String SETTING_VALUE = "settingValue";
	public static final String SETTING_DB_VERSION = "DB_VERSION";
	public static final String SETTING_DB_VERSION_VALUE = "0";
	private static final int DATABASE_VERSION = 1;
	private DbHelper myHelper;
	private final Context myContext;
	protected SQLiteDatabase myDatabase;
	static List<Discipline> disciplines = new ArrayList<Discipline>();
	static List<Subject> subjects = new ArrayList<Subject>();
	static List<SkillGroup> skillGroups = new ArrayList<SkillGroup>();
	static List<Skill> skills = new ArrayList<Skill>();

	private static class DbHelper extends SQLiteOpenHelper {
		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String createEmployeeTable = "CREATE TABLE " + EMPLOYEE_TABLE
					+ " (" + EMPLOYEE_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + EMPLOYEE_NAME
					+ " TEXT UNIQUE)";
			String createDisciplineTable = "CREATE TABLE " + DISCIPLINE_TABLE
					+ " (" + DISCIPLINE_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + DISCIPLINE_NAME
					+ " TEXT NOT NULL)";
			String createSubjectTable = "CREATE TABLE " + SUBJECT_TABLE + " ("
					+ SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ SUBJECT_NAME + " TEXT NOT NULL, "
					+ SUBJECT_DISCIPLINE_GROUP + " INTEGER NOT NULL)";
			String createSkillGroupTable = "CREATE TABLE " + SKILL_GROUP_TABLE
					+ " (" + SKILL_GROUP_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + SKILL_GROUP_NAME
					+ " TEXT NOT NULL, " + SUBJECT_ID + " INTEGER NOT NULL)";
			String createSkillTable = "CREATE TABLE " + SKILL_TABLE + " ("
					+ SKILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ SKILL_NAME + " TEXT UNIQUE, " + SKILL_GROUP_ID
					+ " INTEGER NOT NULL)";
			String createMaterialGroupTable = "CREATE TABLE "
					+ MATERIAL_GROUP_TABLE + " (" + MATERIAL_GROUP_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ MATERIAL_GROUP_NAME + " TEXT NOT NULL, "
					+ SUBJECT_DISCIPLINE_GROUP + " INTEGER NOT NULL)";
			String createMaterialTable = "CREATE TABLE " + MATERIAL_TABLE
					+ " (" + MATERIAL_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + MATERIAL_NAME
					+ " TEXT UNIQUE, " + MATERIAL_GROUP_ID
					+ " INTEGER NOT NULL)";
			String createContextGroupTable = "CREATE TABLE "
					+ CONTEXT_GROUP_TABLE + " (" + CONTEXT_GROUP_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ CONTEXT_GROUP_NAME + " TEXT NOT NULL, "
					+ SUBJECT_DISCIPLINE_GROUP + " INTEGER NOT NULL)";
			String createContextTable = "CREATE TABLE " + CONTEXT_TABLE + " ("
					+ CONTEXT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ CONTEXT_NAME + " TEXT UNIQUE, " + CONTEXT_GROUP_ID
					+ " INTEGER NOT NULL)";
			String createEmployeeSkillTable = "CREATE TABLE "
					+ EMPLOYEE_SKILL_TABLE + " (" + EMPLOYEE_SKILL_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + EMPLOYEE_ID
					+ " INTEGER NOT NULL, " + SKILL_ID + " INTEGER NOT NULL)";
			String createEmployeeMaterialTable = "CREATE TABLE "
					+ EMPLOYEE_MATERIAL_TABLE + " (" + EMPLOYEE_MATERIAL_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + EMPLOYEE_ID
					+ " INTEGER NOT NULL, " + MATERIAL_ID
					+ " INTEGER NOT NULL)";
			String createEmployeeContextTable = "CREATE TABLE "
					+ EMPLOYEE_CONTEXT_TABLE + " (" + EMPLOYEE_CONTEXT_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + EMPLOYEE_ID
					+ " INTEGER NOT NULL, " + CONTEXT_ID + " INTEGER NOT NULL)";
			String createSkillsSheetTable = "CREATE TABLE "
					+ SKILLS_SHEET_TABLE + " (" + SKILL_SHEET_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + SKILL_SHEET_NAME
					+ " TEXT UNIQUE)";
			String createSkillsSheetSkillsTable = "CREATE TABLE "
					+ SKILLS_SHEET_SKILLS_TABLE + " ( " + SKILL_SHEET_ID
					+ " INTEGER NOT NULL, " + SKILL_ID + " INTEGER NOT NULL)";
			String createSkillsSheetMaterialsTable = "CREATE TABLE "
					+ SKILLS_SHEET_MATERIAL_TABLE + " ( " + SKILL_SHEET_ID
					+ " INTEGER NOT NULL, " + MATERIAL_ID
					+ " INTEGER NOT NULL)";
			String createSkillsSheetContextsTable = "CREATE TABLE "
					+ SKILLS_SHEET_CONTEXT_TABLE + " ( " + SKILL_SHEET_ID
					+ " INTEGER NOT NULL, " + CONTEXT_ID + " INTEGER NOT NULL)";
			db.execSQL(createEmployeeTable);
			db.execSQL(createDisciplineTable);
			db.execSQL(createSubjectTable);
			db.execSQL(createSkillGroupTable);
			db.execSQL(createSkillTable);
			db.execSQL(createMaterialGroupTable);
			db.execSQL(createMaterialTable);
			db.execSQL(createContextGroupTable);
			db.execSQL(createContextTable);
			db.execSQL(createEmployeeSkillTable);
			db.execSQL(createEmployeeMaterialTable);
			db.execSQL(createEmployeeContextTable);
			db.execSQL(createSkillsSheetTable);
			db.execSQL(createSkillsSheetSkillsTable);
			db.execSQL(createSkillsSheetMaterialsTable);
			db.execSQL(createSkillsSheetContextsTable);
			// populateDatabase(db);
			// setup and populate the settings table
			setupSettingsTable(db);
		}

		private void setupSettingsTable(SQLiteDatabase db) {
			String createSettingsTable = "CREATE TABLE " + SETTINGS_TABLE
					+ " ( " + SETTING_NAME + " TEXT UNIQUE, " + SETTING_VALUE
					+ " TEXT NOT NULL)";
			db.execSQL(createSettingsTable);
			// add default values
			String defaultValues = "INSERT INTO " + SETTINGS_TABLE + " (`"
					+ SETTING_NAME + "`, `" + SETTING_VALUE + "`) VALUES ( '"
					+ SETTING_DB_VERSION + "', '" + SETTING_DB_VERSION_VALUE
					+ "' )";
			db.execSQL(defaultValues);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_TABLE);
			onCreate(db);
		}
	}

	public static void populateDatabase(SQLiteDatabase db) {
		disciplines.add(new Discipline("Composites"));
		disciplines.add(new Discipline("Boatbuilding"));
		subjects.add(new Subject("Produce Mouldings", 1));
		subjects.add(new Subject("Curing", 1));
		subjects.add(new Subject("Check Mouldings", 1));
		skillGroups.add(new SkillGroup("Prepare for moulding", 1));
		skillGroups.add(new SkillGroup("Produce moulding", 1));
		skillGroups.add(new SkillGroup("Cure mouldings and joints", 2));
		skillGroups.add(new SkillGroup("Cure operation and cycle", 2));
		skillGroups.add(new SkillGroup("Identification of defects", 3));
		skills.add(new Skill("Checking tooling is correct and complete", 1));
		skills.add(new Skill("Cleaning tooling", 1));
		skills.add(new Skill("Using pattern", 2));
		skills.add(new Skill("Using female tooling", 2));
		skills.add(new Skill("Butt joints", 3));
		skills.add(new Skill("Overlap joints", 3));
		skills.add(new Skill("Staggered joints", 3));
		skills.add(new Skill("Oriented plies", 3));
		skills.add(new Skill("Inverted plies", 3));
		skills.add(new Skill("Balancing plies", 3));
		skills.add(new Skill("Brush Gel coat", 4));
		skills.add(new Skill("Brush resin", 4));
		skills.add(new Skill("Deposition of dry contexts", 4));
		skills.add(new Skill("Templates", 5));
		populateDisciplineTable(disciplines, db);
		populateSubjectTable(subjects, db);
		populateSkillGroupTable(skillGroups, db);
		populateSkillTable(skills, db);
	}

	public static void populateDisciplineTable(List<Discipline> disciplines,
			SQLiteDatabase db) {
		for (Discipline discipline : disciplines) {
			String sql = "INSERT INTO " + DISCIPLINE_TABLE + " (`"
					+ DISCIPLINE_NAME + "`) VALUES ('" + discipline.getName()
					+ "')";
			db.execSQL(sql);
		}
	}

	public static void populateSubjectTable(List<Subject> subjects,
			SQLiteDatabase db) {
		for (Subject subject : subjects) {
			String sql = "INSERT INTO " + SUBJECT_TABLE + " (`" + SUBJECT_NAME
					+ "`, `" + SUBJECT_DISCIPLINE_GROUP + "`) VALUES ('"
					+ subject.getName() + "', " + subject.getDisciplineId()
					+ ")";
			db.execSQL(sql);
		}
	}

	public static void populateSkillGroupTable(List<SkillGroup> skillGroups,
			SQLiteDatabase db) {
		for (SkillGroup skillGroup : skillGroups) {
			String sql = "INSERT INTO " + SKILL_GROUP_TABLE + " (`"
					+ SKILL_GROUP_NAME + "`, `" + SUBJECT_ID + "`) VALUES ('"
					+ skillGroup.getName() + "', " + skillGroup.getSubjectId()
					+ ")";
			db.execSQL(sql);
		}
	}

	public static void populateSkillTable(List<Skill> skills, SQLiteDatabase db) {
		for (Skill skill : skills) {
			String sql = "INSERT INTO " + SKILL_TABLE + " (`" + SKILL_NAME
					+ "`, `" + SKILL_GROUP_ID + "`) VALUES ('"
					+ skill.getName() + "', " + skill.getSkillGroupId() + ")";
			db.execSQL(sql);
		}
	}

	// public static void populateTableFromCSVFile(String file, String
	// tableName,
	// SQLiteDatabase db) {
	//
	// try {
	// prop.load(Thread.currentThread().getContextClassLoader()
	// .getResourceAsStream(file));
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	/**
	 * Constructor
	 * 
	 * @param c
	 */
	public DatabaseStuff(Context c) {
		myContext = c;
	}

	public DatabaseStuff open() throws SQLException {
		myHelper = new DbHelper(myContext);
		myDatabase = myHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		myHelper.close();
	}
}
