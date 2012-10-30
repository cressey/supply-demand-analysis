package com.vjs.supplydemandanalysis.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vjs.supplydemandanalysis.R;
import com.vjs.supplydemandanalysis.database.EmployeeDAO;

public class AddEmployeeActivity extends SuperActivity implements
		OnClickListener {

	EmployeeDAO employeeDao = new EmployeeDAO(this);
	EditText name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.setContentView(R.layout.addemployee);
		name = (EditText) findViewById(R.id.employeeNewNameText);
		ImageButton saveBtn = (ImageButton) findViewById(R.id.saveButton);
		setUpCoreButtonControls(saveBtn, this);
	}

	public void onClick(View arg0) {
		String message = "Decent help message here please";
		Intent i;
		switch (arg0.getId()) {
		case R.id.helpButton:
			new AlertDialog.Builder(AddEmployeeActivity.this)
					.setTitle("Help")
					.setMessage(message)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing
								}
							}).show();
			break;
		case R.id.homeButton:
			new AlertDialog.Builder(AddEmployeeActivity.this)
					.setTitle("Go Home")
					.setMessage(
							"Are you sure you don't want to save this entry?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Intent i = new Intent(
											AddEmployeeActivity.this,
											HomeActivity.class);
									startActivity(i);
									AddEmployeeActivity.this.finish();
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
		case R.id.backButton:
			AddEmployeeActivity.this.finish();
			break;
		case R.id.saveButton:
			String nameChosen = name.getText().toString();
			if (nameChosen.equals("")) {
				Dialog d = new Dialog(this);
				d.setTitle("Error");
				TextView tv = new TextView(this);
				tv.setText("Please enter all details");
				d.setContentView(tv);
				d.show();
			} else {
				try {
					employeeDao.createEmployee(nameChosen);
					AddEmployeeActivity.this.finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		}

	}

}
