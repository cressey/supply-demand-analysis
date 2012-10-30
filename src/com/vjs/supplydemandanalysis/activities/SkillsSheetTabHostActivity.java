package com.vjs.supplydemandanalysis.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

import com.vjs.supplydemandanalysis.R;
import com.vjs.supplydemandanalysis.Utils;
import com.vjs.supplydemandanalysis.database.ContextDAO;
import com.vjs.supplydemandanalysis.database.MaterialDAO;
import com.vjs.supplydemandanalysis.database.SkillDAO;
import com.vjs.supplydemandanalysis.database.SkillsSheetDAO;
import com.vjs.supplydemandanalysis.skills.ContextGroup;
import com.vjs.supplydemandanalysis.skills.DatabaseObject;
import com.vjs.supplydemandanalysis.skills.Material;
import com.vjs.supplydemandanalysis.skills.MaterialGroup;
import com.vjs.supplydemandanalysis.skills.Skill;
import com.vjs.supplydemandanalysis.skills.SkillContext;
import com.vjs.supplydemandanalysis.skills.SkillGroup;
import com.vjs.supplydemandanalysis.skills.SkillsSheet;
import com.vjs.supplydemandanalysis.skills.Subject;

public class SkillsSheetTabHostActivity extends TabActivity implements
		OnClickListener, OnTabChangeListener, OnItemSelectedListener {
	// The name of the new skills sheet
	EditText nameText;
	// The new/edited skills sheet object
	SkillsSheet skillsSheet;
	// Database interaction instances
	SkillsSheetDAO skillsSheetDao;
	SkillDAO skillsDao;
	MaterialDAO materialsDao;
	ContextDAO contextsDao;
	// The area on the tabs to be populated by the relevant checkboxes
	LinearLayout checkboxSpace;
	// Store the lists of 'things' on the sheet
	List<Subject> subjects = new ArrayList<Subject>();
	List<SkillGroup> skillGroups = new ArrayList<SkillGroup>();
	List<MaterialGroup> materialGroups = new ArrayList<MaterialGroup>();
	List<ContextGroup> contextGroups = new ArrayList<ContextGroup>();
	List<Skill> selectedSkills = new ArrayList<Skill>();
	List<Material> selectedMaterials = new ArrayList<Material>();
	List<SkillContext> selectedContexts = new ArrayList<SkillContext>();
	List<Skill> skills = new ArrayList<Skill>();
	List<Material> materials = new ArrayList<Material>();
	List<SkillContext> contexts = new ArrayList<SkillContext>();
	// The spinners for the tabs
	Spinner subjectSpinner, skillGroupSpinner, materialGroupSpinner,
			contextGroupSpinner;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		skillsSheetDao = new SkillsSheetDAO(this);
		skillsDao = new SkillDAO(this);
		materialsDao = new MaterialDAO(this);
		contextsDao = new ContextDAO(this);
		setContentView(R.layout.editskillssheet);
		// Set up button controls
		ImageButton saveBtn = (ImageButton) findViewById(R.id.saveButton);
		setUpCoreButtonControls(saveBtn, this);
		nameText = (EditText) findViewById(R.id.skillSheetNameText);
		// Get the selected skill sheet ready to set up checkboxes
		int id = getIntent().getIntExtra("com.vjs.taskskillanalysis.id", 0);
		if (id != 0) {
			skillsSheet = skillsSheetDao.getSkillsSheet(id);
			nameText.setText(skillsSheet.getName());
		} else {
			skillsSheet = new SkillsSheet();
		}
		// Set up the tab hosts and tab widgets
		TabHost tabHost = getTabHost();
		tabHost.setup();
		tabHost.setOnTabChangedListener(this);
		TabSpec skillsTab = tabHost.newTabSpec("Tab 1");
		skillsTab.setContent(R.id.skillsTab);
		skillsTab.setIndicator("Skills");
		TabSpec materialsTab = tabHost.newTabSpec("Tab 2");
		materialsTab.setIndicator("Materials");
		materialsTab.setContent(R.id.materialsTab);
		TabSpec contextsTab = tabHost.newTabSpec("Tab 3");
		contextsTab.setIndicator("Contexts");
		contextsTab.setContent(R.id.contextsTab);
		tabHost.addTab(skillsTab);
		tabHost.addTab(materialsTab);
		tabHost.addTab(contextsTab);
	}

	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.saveButton:
			String name = nameText.getText().toString();
			if (name.equals("")) {
				Dialog d = new Dialog(this);
				d.setTitle("Error");
				TextView tv = new TextView(this);
				tv.setText("Please enter all details");
				d.setContentView(tv);
				d.show();
			} else {
				skillsSheet.setName(nameText.getText().toString());
				SkillsSheetTabHostActivity.this.finish();
			}
			break;
		case R.id.backButton:
			new AlertDialog.Builder(SkillsSheetTabHostActivity.this)
					.setTitle("Go Back")
					.setMessage(
							"Are you sure you don't want to save this entry?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									SkillsSheetTabHostActivity.this.finish();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing
								}
							}).show();
			break;
		case R.id.helpButton:
			String message = "Add a decent help message here please!";
			new AlertDialog.Builder(SkillsSheetTabHostActivity.this)
					.setTitle("Go Back")
					.setMessage(message)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
			break;
		case R.id.homeButton:
			new AlertDialog.Builder(SkillsSheetTabHostActivity.this)
					.setTitle("Go Home")
					.setMessage(
							"Are you sure you don't want to save this entry?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Intent i = new Intent(
											SkillsSheetTabHostActivity.this,
											HomeActivity.class);
									startActivity(i);
									SkillsSheetTabHostActivity.this.finish();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing
								}
							}).show();
			break;
		}
	}

	public void setUpCoreButtonControls(ImageButton button,
			OnClickListener clickListener) {
		ImageButton btn = button;
		btn.setOnClickListener(clickListener);
		btn.setBackgroundColor(Utils.BUTTON_BACKGROUND);
		ImageButton backBtn = (ImageButton) findViewById(R.id.backButton);
		backBtn.setOnClickListener(clickListener);
		backBtn.setBackgroundColor(Utils.BUTTON_BACKGROUND);
		ImageButton helpBtn = (ImageButton) findViewById(R.id.helpButton);
		helpBtn.setOnClickListener(clickListener);
		helpBtn.setBackgroundColor(Utils.BUTTON_BACKGROUND);
		ImageButton homeBtn = (ImageButton) findViewById(R.id.homeButton);
		homeBtn.setOnClickListener(clickListener);
		homeBtn.setBackgroundColor(Utils.BUTTON_BACKGROUND);
	}

	public void onTabChanged(String arg0) {
		int i = getTabHost().getCurrentTab();
		if (i == 0) {
			setUpSkillsTab();
		} else if (i == 1) {
			setUpMaterialsTab();
		} else if (i == 2) {
			setUpContextsTab();
		}
	}

	/**
	 * This method initialises all the skills views and creates the spinner
	 * entries
	 */
	private void setUpSkillsTab() {
		checkboxSpace = (TableLayout) findViewById(R.id.addSkillsCheckboxLayout);
		TextView subj = (TextView) findViewById(R.id.addSkillSubj);
		TextView skillGroup = (TextView) findViewById(R.id.addSkillSkillGroup);
		TextView skill = (TextView) findViewById(R.id.addSkillSkills);
		subj.setTextColor(Utils.SUBJECTCOLOUR);
		skillGroup.setTextColor(Utils.SKILLSGROUPCOLOUR);
		skill.setTextColor(Utils.SKILLSCOLOUR);
		ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				createSubjectSpinnerArray());
		subjectSpinner = (Spinner) findViewById(R.id.skillSheetSubjectSpinner);
		subjectSpinner.setAdapter(subjectAdapter);
		subjectSpinner.setOnItemSelectedListener(this);
		updateSkillGroupSpinner();
		skillGroupSpinner.setOnItemSelectedListener(this);
	}

	public void setUpMaterialsTab() {
		checkboxSpace = (LinearLayout) findViewById(R.id.addMaterialsCheckboxLayout);
		TextView materialGroup = (TextView) findViewById(R.id.materialGroupLabel);
		TextView material = (TextView) findViewById(R.id.materialLabel);
		materialGroup.setTextColor(Utils.MATERIALSGROUPCOLOUR);
		material.setTextColor(Utils.MATERIALSCOLOUR);
		materialGroupSpinner = (Spinner) findViewById(R.id.materialGroupSpinner);
		materialGroupSpinner.setOnItemSelectedListener(this);
		updateMaterialGroupSpinner();
		selectedMaterials = skillsSheetDao.getSkillsSheetMaterials(skillsSheet
				.getId());
	}

	public void setUpContextsTab() {
		checkboxSpace = (LinearLayout) findViewById(R.id.addContextsCheckboxLayout);
		TextView contextGroup = (TextView) findViewById(R.id.contextGroupLabel);
		TextView context = (TextView) findViewById(R.id.contextsLabel);
		contextGroup.setTextColor(Utils.CONTEXTSGROUPCOLOUR);
		context.setTextColor(Utils.CONTEXTSCOLOUR);
		contextGroupSpinner = (Spinner) findViewById(R.id.contextGroupSpinner);
		contextGroupSpinner.setOnItemSelectedListener(this);
		updateContextGroupSpinner();
		selectedContexts = skillsSheetDao.getSkillsSheetContexts(skillsSheet
				.getId());
	}

	private void updateContextCheckboxes(int contextGroupId) {
		checkboxSpace.removeAllViews();
		contexts.removeAll(contexts);
		contexts = contextsDao.getContextList(contextGroupId);
		TableRow newRow = null;
		for (int i = 0; i < contexts.size(); i++) {
			final SkillContext context = contexts.get(i);
			final CheckBox box = new CheckBox(this);
			box.setId(context.getId());
			box.setText(context.getName());
			box.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1));
			if (inListOfContexts(context, selectedContexts)) {
				box.setChecked(true);
			} else {
				box.setChecked(false);
			}
			box.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					updateSelectedItem(context, box.isChecked(), box.getId());
				}
			});
			// new row needed and not the last thing to be added
			if (i % 2 == 0 && i != contexts.size() - 1) {
				newRow = addRow(box);
				newRow.setLayoutParams(new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				// add to previous row
			} else if (i % 2 != 0) {
				newRow.addView(box);
				checkboxSpace.addView(newRow);
				// new row needed and the end of the list
			} else {
				checkboxSpace.addView(addRow(box));
			}
		}
	}

	/**
	 * Check whether context is in list already
	 * 
	 * @param context
	 * @param listOfContexts
	 * @return
	 */
	private boolean inListOfContexts(SkillContext context,
			List<SkillContext> listOfContexts) {
		for (SkillContext ls : listOfContexts) {
			if (context.getId() == ls.getId()) {
				return true;
			}
		}
		return false;
	}

	private void updateSelectedItem(DatabaseObject item, boolean selected,
			int skillsSheetId) {
		skillsSheetDao.open();
		if (item instanceof Skill) {
			if (selected) {
				skillsSheetDao.addSkillsSheetSkill(skillsSheet.getId(),
						((Skill) item).getId());
			} else {
				skillsSheetDao.removeSkillsSheetSkill(skillsSheet.getId(),
						((Skill) item).getId());
			}
		} else if (item instanceof Material) {
			if (selected) {
				skillsSheetDao.addSkillsSheetMaterial(skillsSheet.getId(),
						((Material) item).getId());
			} else {
				skillsSheetDao.removeSkillsSheetMaterial(skillsSheet.getId(),
						((Material) item).getId());
			}
		} else if (item instanceof SkillContext) {
			if (selected) {
				skillsSheetDao.addSkillsSheetContext(skillsSheet.getId(),
						((SkillContext) item).getId());
			} else {
				skillsSheetDao.removeSkillsSheetContext(skillsSheet.getId(),
						((SkillContext) item).getId());
			}
		}
		skillsSheetDao.close();
	}

	/**
	 * The names of all the context groups to be put in the spinner
	 * 
	 * @return
	 */
	public String[] createContextGroupSpinnerArray() {
		contextGroups = contextsDao.getContextGroupList(null);
		String[] result = new String[contextGroups.size()];
		for (int i = 0; i < contextGroups.size(); i++) {
			result[i] = contextGroups.get(i).getName();
		}
		return result;
	}

	private void updateMaterialCheckboxes(int materialGroupId) {
		checkboxSpace.removeAllViews();
		materials.removeAll(materials);
		materials = materialsDao.getMaterialList(materialGroupId);
		TableRow newRow = null;
		for (int i = 0; i < materials.size(); i++) {
			final Material material = materials.get(i);
			final CheckBox box = new CheckBox(this);
			box.setId(material.getId());
			box.setText(material.getName());
			box.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1));
			if (inListOfMaterials(material, selectedMaterials)) {
				box.setChecked(true);
			} else {
				box.setChecked(false);
			}
			box.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					updateSelectedItem(material, box.isChecked(), box.getId());
				}
			});
			// new row needed and not the last thing to be added
			if (i % 2 == 0 && i != materials.size() - 1) {
				newRow = addRow(box);
				newRow.setLayoutParams(new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				// add to previous row
			} else if (i % 2 != 0) {
				newRow.addView(box);
				checkboxSpace.addView(newRow);
				// new row needed and the end of the list
			} else {
				checkboxSpace.addView(addRow(box));
			}
		}
	}

	/**
	 * Check whether material is in list already
	 * 
	 * @param material
	 * @param listOfMaterials
	 * @return
	 */
	private boolean inListOfMaterials(Material material,
			List<Material> listOfMaterials) {
		for (Material ls : listOfMaterials) {
			if (material.getId() == ls.getId()) {
				return true;
			}
		}
		return false;
	}

	private void updateMaterialGroupSpinner() {
		ArrayAdapter<String> materialGroupAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_dropdown_item,
				createMaterialGroupSpinnerArray());
		materialGroupSpinner.setAdapter(materialGroupAdapter);
	}

	private void updateContextGroupSpinner() {
		ArrayAdapter<String> contextGroupAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_dropdown_item,
				createContextGroupSpinnerArray());
		contextGroupSpinner.setAdapter(contextGroupAdapter);
	}

	/**
	 * The names of all the material groups to be put in the spinner
	 * 
	 * @return
	 */
	public String[] createMaterialGroupSpinnerArray() {
		MaterialDAO materialDao = new MaterialDAO(this);
		materialGroups = materialDao.getMaterialGroupList(null);
		String[] result = new String[materialGroups.size()];
		for (int i = 0; i < materialGroups.size(); i++) {
			result[i] = materialGroups.get(i).getName();
		}
		return result;
	}

	/**
	 * @param skillGroupId
	 */
	private void updateSkillCheckboxes(int skillGroupId) {
		checkboxSpace.removeAllViews();
		skills.removeAll(skills);
		skills = skillsDao.getSkillList(skillGroupId);
		TableRow newRow = null;
		for (int i = 0; i < skills.size(); i++) {
			final Skill skill = skills.get(i);
			final CheckBox box = new CheckBox(this);
			box.setId(skill.getId());
			box.setText(skill.getName());
			box.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1));
			if (inListOfSkills(skill, selectedSkills)) {
				box.setChecked(true);
			} else {
				box.setChecked(false);
			}
			box.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					updateSelectedItem(skill, box.isChecked(), box.getId());
				}
			});
			// new row needed and not the last thing to be added
			if (i % 2 == 0 && i != skills.size() - 1) {
				newRow = addRow(box);
				newRow.setLayoutParams(new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				// add to previous row
			} else if (i % 2 != 0) {
				newRow.addView(box);
				checkboxSpace.addView(newRow);
				// new row needed and the end of the list
			} else {
				checkboxSpace.addView(addRow(box));
			}
		}
	}

	private TableRow addRow(CheckBox box) {
		TableRow row = new TableRow(this);
		row.addView(box);
		return row;
	}

	private boolean inListOfSkills(Skill skill, List<Skill> listOfSkills) {
		for (Skill ls : listOfSkills) {
			if (skill.getId() == ls.getId()) {
				return true;
			}
		}
		return false;
	}

	private void updateSkillGroupSpinner() {
		int selectedSubjectId = subjects.get(
				subjectSpinner.getSelectedItemPosition()).getId();
		ArrayAdapter<String> skillAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				createSkillGroupSpinnerArray(selectedSubjectId));
		skillGroupSpinner = (Spinner) findViewById(R.id.skillSheetSkillGroupSpinner);
		skillGroupSpinner.setAdapter(skillAdapter);
	}

	public String[] createSubjectSpinnerArray() {
		SkillDAO taskDao = new SkillDAO(this);
		subjects = taskDao.getSubjectList(null);
		String[] result = new String[subjects.size()];
		for (int i = 0; i < subjects.size(); i++) {
			result[i] = subjects.get(i).getName();
		}
		return result;
	}

	public String[] createSkillGroupSpinnerArray(int subjectId) {
		SkillDAO taskDao = new SkillDAO(this);
		skillGroups = taskDao.getSkillGroupList(subjectId);
		String[] result = new String[skillGroups.size()];
		for (int i = 0; i < skillGroups.size(); i++) {
			result[i] = skillGroups.get(i).getName();
		}
		return result;
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg0.getId() == R.id.skillSheetSubjectSpinner) {
			updateSkillGroupSpinner();
		} else if (arg0.getId() == R.id.skillSheetSkillGroupSpinner) {
			int selectedSkillGroupId = skillGroups.get(
					skillGroupSpinner.getSelectedItemPosition()).getId();
			updateSkillCheckboxes(selectedSkillGroupId);
		} else if (arg0.getId() == R.id.materialGroupSpinner) {
			int selectedMaterialGroupId = materialGroups.get(
					materialGroupSpinner.getSelectedItemPosition()).getId();
			updateMaterialCheckboxes(selectedMaterialGroupId);
		} else if (arg0.getId() == R.id.contextGroupSpinner) {
			int selectedContextGroupId = contextGroups.get(
					contextGroupSpinner.getSelectedItemPosition()).getId();
			updateContextCheckboxes(selectedContextGroupId);
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}
}
