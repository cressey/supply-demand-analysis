
package com.vjs.supplydemandanalysis.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.vjs.supplydemandanalysis.R;
import com.vjs.supplydemandanalysis.Utils;

public class AdminActivity extends SuperActivity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.admin );
        Button taskBtn = (Button) findViewById( R.id.adminTaskButton );
        taskBtn.setTextSize( Utils.BUTTONTEXTSIZE );
        taskBtn.setOnClickListener( this );
        Button staffBtn = (Button) findViewById( R.id.adminStaffButton );
        staffBtn.setTextSize( Utils.BUTTONTEXTSIZE );
        staffBtn.setOnClickListener( this );
        setUpCoreButtonControls( this );
    }
    public void onClick( View arg0 ) {
        Intent i;
        switch ( arg0.getId() ) {
            case R.id.adminTaskButton:
                i = new Intent( this, AdminSkillsSheetActivity.class );
                startActivity( i );
                break;
            case R.id.adminStaffButton:
                i = new Intent( this, AdminEmployeeActivity.class );
                startActivity( i );
                break;
            case R.id.backButton:
                AdminActivity.this.finish();
                break;
            case R.id.helpButton:
                String message = "Add a decent help message here please!";
                new AlertDialog.Builder( AdminActivity.this ).setTitle( "Help" ).setMessage( message )
                                .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                    public void onClick( DialogInterface dialog, int which ) {
                                    }
                                } ).show();
                break;
            case R.id.homeButton:
                i = new Intent( this, HomeActivity.class );
                startActivity( i );
                AdminActivity.this.finish();
                break;
        }
    }
}
