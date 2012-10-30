package com.vjs.supplydemandanalysis.activities;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.vjs.supplydemandanalysis.R;
import com.vjs.supplydemandanalysis.database.DatabaseStuff;
import com.vjs.supplydemandanalysis.database.EmployeeDAO;
import com.vjs.supplydemandanalysis.employee.Employee;

public class AdminEmployeeActivity extends SuperActivity implements
		OnClickListener {
	TableLayout tl;
	DatabaseStuff db = new DatabaseStuff(this);
	int id;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		setContentView(R.layout.employeeadmin);
		ImageButton addBtn = (ImageButton) findViewById(R.id.addButton);
		setUpCoreButtonControls(addBtn, this);
		tl = (TableLayout) findViewById(R.id.staffAdminTableLayout);
		populateEmployees(tl, new EmployeeDAO(this).getEmployeeList());
	}

	public void populateEmployees(TableLayout tl, List<Employee> employees) {
		for (Employee e : employees) {
			final int name = e.getId();
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			TextView id = new TextView(this);
			String identifier = e.getName();
			id.setTextSize(20);
			id.setText(identifier);
			id.setWidth(150);
			id.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.MATCH_PARENT, 3));
			Button view = new Button(this);
			view.setText("View");
			view.setTag(identifier);
			view.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1));
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getBaseContext(),
							ViewEmployeeActivity.class);
					i.putExtra("com.vjs.taskskillanalysis.id", name);
					startActivity(i);
				}
			});
			Button edit = new Button(this);
			edit.setText("Edit");
			edit.setTag(identifier);
			edit.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1));
			edit.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getBaseContext(),
							EmployeeTabHostActivity.class);
					i.putExtra("com.vjs.taskskillanalysis.id", name);
					startActivity(i);
				}
			});
			Button delete = new Button(this);
			delete.setText("Delete");
			delete.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1));
			delete.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					new AlertDialog.Builder(AdminEmployeeActivity.this)
							.setTitle("Delete entry")
							.setMessage(
									"Are you sure you want to delete this entry?")
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											deleteEntry(name);
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
			tr.addView(id);
			tr.addView(view);
			tr.addView(edit);
			tr.addView(delete);
			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}
	}

	private void deleteEntry(int identifier) {
		EmployeeDAO employeeTable = new EmployeeDAO(this);
		employeeTable.open();
		employeeTable.deleteEmployeeEntry(identifier);
		employeeTable.close();
	}

	public void onClick(View arg0) {
		Intent i;
		switch (arg0.getId()) {
		case R.id.addButton:
			i = new Intent(this, AddEmployeeActivity.class);
			startActivity(i);
			break;
		case R.id.backButton:
			AdminEmployeeActivity.this.finish();
			break;
		case R.id.helpButton:
			String message = "Add a decent help message here please!";
			new AlertDialog.Builder(AdminEmployeeActivity.this)
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
			AdminEmployeeActivity.this.finish();
			break;
		}
	}
}
