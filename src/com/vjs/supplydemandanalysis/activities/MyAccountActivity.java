
package com.vjs.supplydemandanalysis.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vjs.supplydemandanalysis.R;
import com.vjs.supplydemandanalysis.Utils;

public class MyAccountActivity extends SuperActivity implements OnClickListener {
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }
    @Override
    protected void onResume() {
        super.onResume();
        setContentView( R.layout.myaccount );
        ImageButton logoutButton = (ImageButton) findViewById( R.id.logoutButton );
        setUpCoreButtonControls( logoutButton, this );
        String currentUsername = Utils.getUsername( this );
        TextView username = (TextView) findViewById( R.id.currentUser );
        username.setText( " " + currentUsername );
    }
    public void onClick( View arg0 ) {
        switch ( arg0.getId() ) {
            case R.id.logoutButton:
                Utils.setLoggedOut( this );
                Toast.makeText( this, "Logged Out.", Toast.LENGTH_SHORT ).show();
                // Intent i = new Intent( this, HomeActivity.class );
                // startActivity( i );
                MyAccountActivity.this.finish();
                break;
            case R.id.backButton:
                MyAccountActivity.this.finish();
                break;
            case R.id.helpButton:
                String message = "Add a decent help message here please!";
                new AlertDialog.Builder( MyAccountActivity.this ).setTitle( "Go Back" ).setMessage( message )
                                .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                    public void onClick( DialogInterface dialog, int which ) {
                                    }
                                } ).show();
                break;
            case R.id.homeButton:
                Intent i = new Intent( this, HomeActivity.class );
                startActivity( i );
                MyAccountActivity.this.finish();
                break;
        }
    }
}
