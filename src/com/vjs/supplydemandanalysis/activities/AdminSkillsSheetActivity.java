package com.vjs.supplydemandanalysis.activities;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.vjs.supplydemandanalysis.R;
import com.vjs.supplydemandanalysis.database.SkillsSheetDAO;
import com.vjs.supplydemandanalysis.skills.SkillsSheet;

public class AdminSkillsSheetActivity extends SuperActivity implements
		OnClickListener, OnItemSelectedListener {
	TableLayout tl;
	SkillsSheetDAO skillsSheetDao = new SkillsSheetDAO(this);
	int id;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		setContentView(R.layout.skillsheetadmin);
		ImageButton addBtn = (ImageButton) findViewById(R.id.addButton);
		setUpCoreButtonControls(addBtn, this);
		tl = (TableLayout) findViewById(R.id.taskSheetAdminTableLayout);
		populateTaskSheets(tl, skillsSheetDao.getAllSkillsSheets());
	}

	public void populateTaskSheets(TableLayout tl,
			List<SkillsSheet> skillsSheets) {
		for (SkillsSheet s : skillsSheets) {
			final int id = s.getId();
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new TableLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1));
			TextView nameText = new TextView(this);
			String name = s.getName();
			nameText.setText(name);
			nameText.setTextSize(20);
			nameText.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 3));
			Button view = new Button(this);
			view.setText("View");
			view.setTag(name);
			view.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1));
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getBaseContext(),
							ViewSkillsSheetActivity.class);
					i.putExtra("com.vjs.taskskillanalysis.id", id);
					startActivity(i);
				}
			});
			Button edit = new Button(this);
			edit.setText("Edit");
			edit.setTag(name);
			edit.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1));
			edit.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getBaseContext(),
							SkillsSheetTabHostActivity.class);
					i.putExtra("com.vjs.taskskillanalysis.id", id);
					startActivity(i);
				}
			});
			Button delete = new Button(this);
			delete.setText("Delete");
			delete.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1));
			delete.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					new AlertDialog.Builder(AdminSkillsSheetActivity.this)
							.setTitle("Delete entry")
							.setMessage(
									"Are you sure you want to delete this entry?")
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											deleteEntry(id);
											onResume();
										}
									})
							.setNegativeButton("No",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// do nothing
										}
									}).show();
					onResume();
				}
			});
			tr.addView(nameText);
			tr.addView(view);
			tr.addView(edit);
			tr.addView(delete);
			tl.addView(tr);
		}
	}

	private void deleteEntry(int identifier) {
		skillsSheetDao.deleteSkillsSheet(identifier);
	}

	public void onClick(View arg0) {
		Intent i;
		switch (arg0.getId()) {
		case R.id.addButton:
			i = new Intent(this, AddSkillsSheetActivity.class);
			startActivity(i);
			break;
		case R.id.backButton:
			AdminSkillsSheetActivity.this.finish();
			break;
		case R.id.helpButton:
			String message = "Add a decent help message here please!";
			new AlertDialog.Builder(AdminSkillsSheetActivity.this)
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
			AdminSkillsSheetActivity.this.finish();
			break;
		}
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}
}
