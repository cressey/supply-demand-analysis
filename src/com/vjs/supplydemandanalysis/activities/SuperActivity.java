
package com.vjs.supplydemandanalysis.activities;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.vjs.supplydemandanalysis.R;
import com.vjs.supplydemandanalysis.Utils;

public class SuperActivity extends Activity {
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    public void setUpCoreButtonControls( OnClickListener clickListener ) {
        ImageButton backBtn = (ImageButton) findViewById( R.id.backButton );
        backBtn.setOnClickListener( clickListener );
        backBtn.setBackgroundColor( Utils.BUTTON_BACKGROUND );
        ImageButton helpBtn = (ImageButton) findViewById( R.id.helpButton );
        helpBtn.setOnClickListener( clickListener );
        helpBtn.setBackgroundColor( Utils.BUTTON_BACKGROUND );
        ImageButton homeBtn = (ImageButton) findViewById( R.id.homeButton );
        homeBtn.setOnClickListener( clickListener );
        homeBtn.setBackgroundColor( Utils.BUTTON_BACKGROUND );
    }
    public void setUpCoreButtonControls( ImageButton button, OnClickListener clickListener ) {
        ImageButton btn = button;
        btn.setOnClickListener( clickListener );
        btn.setBackgroundColor( Utils.BUTTON_BACKGROUND );
        ImageButton backBtn = (ImageButton) findViewById( R.id.backButton );
        backBtn.setOnClickListener( clickListener );
        backBtn.setBackgroundColor( Utils.BUTTON_BACKGROUND );
        ImageButton helpBtn = (ImageButton) findViewById( R.id.helpButton );
        helpBtn.setOnClickListener( clickListener );
        helpBtn.setBackgroundColor( Utils.BUTTON_BACKGROUND );
        ImageButton homeBtn = (ImageButton) findViewById( R.id.homeButton );
        homeBtn.setOnClickListener( clickListener );
        homeBtn.setBackgroundColor( Utils.BUTTON_BACKGROUND );
    }
}
