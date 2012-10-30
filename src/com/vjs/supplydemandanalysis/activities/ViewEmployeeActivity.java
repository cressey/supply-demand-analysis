package com.vjs.supplydemandanalysis.activities;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vjs.supplydemandanalysis.R;
import com.vjs.supplydemandanalysis.Utils;
import com.vjs.supplydemandanalysis.database.EmployeeDAO;
import com.vjs.supplydemandanalysis.employee.Employee;
import com.vjs.supplydemandanalysis.skills.ContextDetails;
import com.vjs.supplydemandanalysis.skills.MaterialDetails;
import com.vjs.supplydemandanalysis.skills.SkillDetails;

public class ViewEmployeeActivity extends SuperActivity implements
		OnClickListener {
	EmployeeDAO employeeDao = new EmployeeDAO(this);
	Employee employee;
	List<SkillDetails> skills;
	List<MaterialDetails> materials;
	List<ContextDetails> contexts;
	LinearLayout skillDisplay;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		setContentView(R.layout.viewemployee);
		setUpCoreButtonControls(this);
		int id = getIntent().getIntExtra("com.vjs.taskskillanalysis.id", 0);
		employee = employeeDao.getEmployee(id);
		skills = employeeDao.getEmployeeSkillsWithDetails(employee.getId());
		materials = employeeDao.getEmployeeMaterialsWithDetails(id);
		contexts = employeeDao.getEmployeeContextsWithDetails(id);
		TextView employeeName = (TextView) findViewById(R.id.viewEmployeeText);
		employeeName.setText(employee.getName());
		skillDisplay = (LinearLayout) findViewById(R.id.viewEmployeeLinearLayout);
		TextView skillName = new TextView(this);
		skillName.setText("Skills");
		skillName.setTextSize(22);
		skillName.setPadding(0, 22, 0, 0);
		TextView materialName = new TextView(this);
		materialName.setText("Materials");
		materialName.setTextSize(22);
		materialName.setPadding(0, 22, 0, 0);
		TextView contextName = new TextView(this);
		contextName.setText("Contexts");
		contextName.setTextSize(22);
		contextName.setPadding(0, 22, 0, 0);
		String subject = "";
		String group = "";
		String skill = "";
		String materialGroup = "";
		String material = "";
		String contextGroup = "";
		String context = "";
		for (SkillDetails skillDetails : skills) {
			TextView subjectText = new TextView(this);
			subjectText.setTextSize(20);
			subjectText.setTextColor(Utils.SUBJECTCOLOUR);
			subjectText.setPadding(0, 10, 0, 0);
			TextView groupText = new TextView(this);
			groupText.setTextSize(18);
			groupText.setTextColor(Utils.SKILLSGROUPCOLOUR);
			groupText.setPadding(10, 10, 0, 0);
			TextView skillText = new TextView(this);
			skillText.setTextSize(16);
			skillText.setTextColor(Utils.SKILLSCOLOUR);
			skillText.setPadding(20, 0, 0, 0);
			skillDisplay.addView(skillName);
			if (!subject.equals(skillDetails.getSubjectName())) {
				// output new Subject
				subject = skillDetails.getSubjectName();
				subjectText.setText(subject);
				skillDisplay.addView(subjectText);
				// and new group
				group = skillDetails.getSkillGroupName();
				groupText.setText(group);
				skillDisplay.addView(groupText);
				// and new skill
				skill = skillDetails.getName();
				skillText.setText(skill);
				skillDisplay.addView(skillText);
			} else if (!group.equals(skillDetails.getSkillGroupName())) {
				// new group
				group = skillDetails.getSkillGroupName();
				groupText.setText(group);
				skillDisplay.addView(groupText);
				// new skill
				skill = skillDetails.getName();
				skillText.setText(skill);
				skillDisplay.addView(skillText);
			} else {
				// new skill
				skill = skillDetails.getName();
				skillText.setText(skill);
				skillDisplay.addView(skillText);
			}
		}
		skillDisplay.addView(materialName);
		for (MaterialDetails materialDetails : materials) {
			TextView groupText = new TextView(this);
			groupText.setTextSize(18);
			groupText.setTextColor(Utils.SKILLSGROUPCOLOUR);
			groupText.setPadding(10, 10, 0, 0);
			TextView materialText = new TextView(this);
			materialText.setTextSize(16);
			materialText.setTextColor(Utils.SKILLSCOLOUR);
			materialText.setPadding(20, 0, 0, 0);
			if (!materialGroup.equals(materialDetails.getMaterialGroupName())) {
				// new group
				materialGroup = materialDetails.getMaterialGroupName();
				groupText.setText(materialGroup);
				skillDisplay.addView(groupText);
				// new material
				material = materialDetails.getName();
				materialText.setText(material);
				skillDisplay.addView(materialText);
			} else {
				// new material
				material = materialDetails.getName();
				materialText.setText(material);
				skillDisplay.addView(materialText);
			}
		}
		skillDisplay.addView(contextName);
		for (ContextDetails contextDetails : contexts) {
			TextView groupText = new TextView(this);
			groupText.setTextSize(18);
			groupText.setTextColor(Utils.SKILLSGROUPCOLOUR);
			groupText.setPadding(10, 10, 0, 0);
			TextView contextText = new TextView(this);
			contextText.setTextSize(16);
			contextText.setTextColor(Utils.SKILLSCOLOUR);
			contextText.setPadding(20, 0, 0, 0);
			if (!contextGroup.equals(contextDetails.getContextGroupName())) {
				// new group
				contextGroup = contextDetails.getContextGroupName();
				groupText.setText(contextGroup);
				skillDisplay.addView(groupText);
				// new context
				context = contextDetails.getName();
				contextText.setText(context);
				skillDisplay.addView(contextText);
			} else {
				// new context
				context = contextDetails.getName();
				contextText.setText(context);
				skillDisplay.addView(contextText);
			}
		}
	}

	public void onClick(View arg0) {
		Intent i;
		switch (arg0.getId()) {
		case R.id.backButton:
			ViewEmployeeActivity.this.finish();
			break;
		case R.id.helpButton:
			String message = "Add a decent help message here please!";
			new AlertDialog.Builder(ViewEmployeeActivity.this)
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
			i = new Intent(this, HomeActivity.class);
			startActivity(i);
			ViewEmployeeActivity.this.finish();
			break;
		}
	}
}
