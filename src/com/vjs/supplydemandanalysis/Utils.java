
package com.vjs.supplydemandanalysis;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.vjs.supplydemandanalysis.employee.Employee;
import com.vjs.supplydemandanalysis.skills.Skill;
import com.vjs.supplydemandanalysis.skills.SkillsSheet;

public class Utils extends Activity {
    private static final String USERNAME_KEY = "username";
    private static final String LOGGED_IN_KEY = "loggedIn";
    public static final int SUBJECTCOLOUR = 0xFF8b4513;
    public static final int SKILLSGROUPCOLOUR = 0xFF1E90ff;
    public static final int MATERIALSGROUPCOLOUR = 0xFF1E90ff;
    public static final int CONTEXTSGROUPCOLOUR = 0xFF1E90ff;
    public static final int SKILLSCOLOUR = 0xFFFFD700;
    public static final int MATERIALSCOLOUR = 0xFFFFD700;
    public static final int CONTEXTSCOLOUR = 0xFFFFD700;
    public static final int NOTENOUGHEMPLOYEESCOLOUR = 0xffff0000;
    public static final int ENOUGHEMPLOYEESCOLOUR = 0xff00c000;
    public static final int BUTTONTEXTSIZE = 20;
    public static final int BUTTON_BACKGROUND = Color.BLACK;
    public static final boolean inListOfSkills( Skill skill, List<Skill> listOfSkills ) {
        for ( Skill ls : listOfSkills ) {
            if ( skill.getId() == ls.getId() ) {
                return true;
            }
        }
        return false;
    }
    public static final boolean inListOfSkillsSheet( int skillsSheetId, List<SkillsSheet> listOfSkillsSheets ) {
        for ( SkillsSheet ss : listOfSkillsSheets ) {
            if ( skillsSheetId == ss.getId() ) {
                return true;
            }
        }
        return false;
    }
    public static final boolean inListOfEmployees( int employeeId, List<Employee> listOfEmployees ) {
        for ( Employee e : listOfEmployees ) {
            if ( employeeId == e.getId() ) {
                return true;
            }
        }
        return false;
    }
    public static boolean isLoggedIn( Context context ) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences( context );
        return sharedPrefs.getBoolean( LOGGED_IN_KEY, false );
    }
    public static String getUsername( Context context ) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences( context );
        return sharedPrefs.getString( USERNAME_KEY, "" );
    }
    public static void setLoggedIn( Context context, String username ) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences( context );
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean( LOGGED_IN_KEY, true );
        editor.putString( USERNAME_KEY, username );
        editor.commit();
    }
    public static void setLoggedOut( Context context ) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences( context );
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean( LOGGED_IN_KEY, false );
        editor.remove( USERNAME_KEY );
        editor.commit();
    }
    public static String getPreference( Context context, String key ) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences( context );
        return sharedPrefs.getString( key, "" );
    }
}
