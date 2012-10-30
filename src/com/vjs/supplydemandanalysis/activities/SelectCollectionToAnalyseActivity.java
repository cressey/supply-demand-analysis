
package com.vjs.supplydemandanalysis.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.vjs.supplydemandanalysis.R;
import com.vjs.supplydemandanalysis.Utils;
import com.vjs.supplydemandanalysis.database.EmployeeDAO;
import com.vjs.supplydemandanalysis.database.SkillsSheetDAO;
import com.vjs.supplydemandanalysis.employee.Employee;
import com.vjs.supplydemandanalysis.skills.SkillsSheet;

public class SelectCollectionToAnalyseActivity extends SuperActivity implements OnClickListener {
    SkillsSheetDAO skillsSheetDao = new SkillsSheetDAO( this );
    EmployeeDAO employeeDao = new EmployeeDAO( this );
    List<SkillsSheet> selectedSkills = new ArrayList<SkillsSheet>();
    List<Employee> selectedEmployees = new ArrayList<Employee>();
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }
    @Override
    protected void onResume() {
        super.onResume();
        setContentView( R.layout.skillssheetanalysisselector );
        selectedSkills.clear();
        selectedEmployees.clear();
        setUpCoreButtonControls( this );
        Button goBtn = (Button) findViewById( R.id.selectSkillsGoButton );
        goBtn.setTextSize( Utils.BUTTONTEXTSIZE );
        goBtn.setOnClickListener( this );
        LinearLayout skillSheetLayout = (LinearLayout) findViewById( R.id.taskSkillCheckboxSpace );
        List<SkillsSheet> skillsSheets = skillsSheetDao.getAllSkillsSheets();
        for ( SkillsSheet sheet : skillsSheets ) {
            final int id = sheet.getId();
            final CheckBox box = new CheckBox( this );
            box.setText( sheet.getName() );
            box.setTextSize( 18 );
            box.setOnClickListener( new OnClickListener() {
                public void onClick( View v ) {
                    updateSelectedSkillsSheetList( id, box.isChecked() );
                }
            } );
            skillSheetLayout.addView( box );
        }
        LinearLayout employeeLayout = (LinearLayout) findViewById( R.id.employeeCheckboxSpace );
        List<Employee> employees = employeeDao.getEmployeeList();
        for ( Employee employee : employees ) {
            final int id = employee.getId();
            final CheckBox checkBox = new CheckBox( this );
            checkBox.setText( employee.getName() );
            checkBox.setTextSize( 18 );
            checkBox.setOnClickListener( new OnClickListener() {
                public void onClick( View v ) {
                    updateSelectedEmployeesSheetList( id, checkBox.isChecked() );
                }
            } );
            employeeLayout.addView( checkBox );
        }
    }
    protected void updateSelectedEmployeesSheetList( int id, boolean selected ) {
        if ( selected ) {
            if ( !Utils.inListOfEmployees( id, selectedEmployees ) ) {
                selectedEmployees.add( employeeDao.getEmployee( id ) );
            }
        } else {
            if ( Utils.inListOfEmployees( id, selectedEmployees ) ) {
                selectedEmployees.remove( employeeDao.getEmployee( id ) );
            }
        }
    }
    protected void updateSelectedSkillsSheetList( int id, boolean selected ) {
        if ( selected ) {
            if ( !Utils.inListOfSkillsSheet( id, selectedSkills ) ) {
                selectedSkills.add( skillsSheetDao.getSkillsSheet( id ) );
            }
        } else {
            if ( Utils.inListOfSkillsSheet( id, selectedSkills ) ) {
                selectedSkills.remove( skillsSheetDao.getSkillsSheet( id ) );
            }
        }
    }
    public void onClick( View v ) {
        Intent i;
        switch ( v.getId() ) {
            case R.id.backButton:
                SelectCollectionToAnalyseActivity.this.finish();
                break;
            case R.id.helpButton:
                String message = "Add a decent help message here please!";
                new AlertDialog.Builder( SelectCollectionToAnalyseActivity.this ).setTitle( "Go Back" ).setMessage( message )
                                .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                    public void onClick( DialogInterface dialog, int which ) {
                                    }
                                } ).show();
                break;
            case R.id.homeButton:
                i = new Intent( this, HomeActivity.class );
                startActivity( i );
                SelectCollectionToAnalyseActivity.this.finish();
                break;
            case R.id.selectSkillsGoButton:
                ArrayList<String> keys = new ArrayList<String>();
                ArrayList<Integer> values = new ArrayList<Integer>();
                for ( SkillsSheet skillsSheet : selectedSkills ) {
                    keys.add( "skillsSheet" );
                    values.add( skillsSheet.getId() );
                }
                for ( Employee employee : selectedEmployees ) {
                    keys.add( "employee" );
                    values.add( employee.getId() );
                }
                i = new Intent( this, AnalysisActivity.class );
                i.putExtra( "keys", keys );
                i.putExtra( "values", values );
                startActivity( i );
        }
    }
}
