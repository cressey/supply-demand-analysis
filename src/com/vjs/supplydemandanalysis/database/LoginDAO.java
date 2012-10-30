
package com.vjs.supplydemandanalysis.database;

import android.content.Context;
import android.database.Cursor;

public class LoginDAO extends DatabaseStuff {
    public LoginDAO( Context c ) {
        super( c );
    }
    /**
     * Populates the database using raw SQL queries.
     * <p>
     * <b>WARNING: DO NOT RUN THIS METHOD WITH USER INPUT!!!</b>
     */
    public void runDbSetupFromLogin( String rawSql ) {
        open();
        myDatabase.execSQL( rawSql );
        close();
    }
    public String getDbVersion( String username ) {
        open();
        String dbVersion = "0";
        Cursor c = myDatabase.rawQuery( "SELECT * FROM " + SETTINGS_TABLE + " WHERE " + SETTING_NAME + " = 'DB_VERSION' ", new String[]{} );
        int iVersion = c.getColumnIndex( SETTING_VALUE );
        if ( c != null && c.moveToFirst() ) {
            dbVersion = c.getString( iVersion );
        }
        close();
        return dbVersion;
    }
}
