
package com.vjs.supplydemandanalysis.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.vjs.supplydemandanalysis.R;

public class SplashScreen extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.splash );
        // Load image with animation
        ImageView image = (ImageView) findViewById( R.id.splashPicture );
        Animation hyperspaceJump = AnimationUtils.loadAnimation( this, R.xml.zoom );
        image.startAnimation( hyperspaceJump );
        // Close this splash screen and start the main activity in a
        // new thread 2 seconds later to allow the animation to complete
        Runnable timer = new Runnable() {
            public void run() {
                Intent i = new Intent();
                i.setClass( getBaseContext(), HomeActivity.class );
                startActivity( i );
                SplashScreen.this.finish();
            }
        };
        new Handler().postDelayed( timer, 2000 );
    }
    {
    }
}
