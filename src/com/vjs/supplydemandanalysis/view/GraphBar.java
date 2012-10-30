
package com.vjs.supplydemandanalysis.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

public class GraphBar extends View {
    private ShapeDrawable bar;
    public GraphBar( Context context, int height, int width, boolean meetsDemand ) {
        super( context );
        bar = new ShapeDrawable( new RectShape() );
        if ( meetsDemand ) {
            bar.getPaint().setColor( Color.GREEN );
        } else {
            bar.getPaint().setColor( Color.RED );
        }
        bar.setIntrinsicWidth( width );
        bar.setBounds( 0, 0 - height, width, height );
    }
    protected void onDraw( Canvas canvas ) {
        bar.draw( canvas );
    }
}
