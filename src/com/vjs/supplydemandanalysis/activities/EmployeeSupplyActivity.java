
package com.vjs.supplydemandanalysis.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.vjs.supplydemandanalysis.R;

public class EmployeeSupplyActivity extends SuperActivity implements OnClickListener {
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }
    @Override
    public void onResume() {
        super.onResume();
        setContentView( R.layout.employeegraphviewer );
        setUpCoreButtonControls( this );
    }
    public void onClick( View arg0 ) {
        switch ( arg0.getId() ) {
            case R.id.backButton:
                EmployeeSupplyActivity.this.finish();
                break;
            case R.id.helpButton:
                String message = "Add a decent help message here please!";
                new AlertDialog.Builder( EmployeeSupplyActivity.this ).setTitle( "Go Back" ).setMessage( message )
                                .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                    public void onClick( DialogInterface dialog, int which ) {
                                    }
                                } ).show();
                break;
            case R.id.homeButton:
                Intent i = new Intent( this, HomeActivity.class );
                startActivity( i );
                EmployeeSupplyActivity.this.finish();
                break;
        }
    }
}
