
package com.vjs.supplydemandanalysis.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.vjs.supplydemandanalysis.skills.ContextGroup;
import com.vjs.supplydemandanalysis.skills.Discipline;
import com.vjs.supplydemandanalysis.skills.SkillContext;

public class ContextDAO extends DatabaseStuff {
    public ContextDAO( Context c ) {
        super( c );
        // TODO Auto-generated constructor stub
    }
    public long createContextEntry( String id, String name, String contextGroup ) {
        ContentValues cv = new ContentValues();
        cv.put( CONTEXT_ID, id );
        cv.put( CONTEXT_NAME, name );
        cv.put( CONTEXT_GROUP_ID, contextGroup );
        return myDatabase.insert( CONTEXT_TABLE, null, cv );
    }
    /**
     * Gets a list of contexts belonging to a specific contexts group
     * 
     * @param ContextGroup
     *            the contexts group the contexts belong to
     * @return a list of the contexts
     */
    public List<SkillContext> getContextList( int contextGroupId ) {
        List<SkillContext> contexts = new ArrayList<SkillContext>();
        open();
        Cursor c = myDatabase.rawQuery( "SELECT * FROM " + CONTEXT_TABLE + " WHERE " + CONTEXT_GROUP_ID + " = ? ORDER BY " + CONTEXT_NAME + " ASC",
                        new String[]{Integer.toString( contextGroupId )} );
        int iId = c.getColumnIndex( CONTEXT_ID );
        int iName = c.getColumnIndex( CONTEXT_NAME );
        int iGroup = c.getColumnIndex( CONTEXT_GROUP_ID );
        if ( c != null ) {
            for ( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() ) {
                SkillContext m = new SkillContext();
                m.setId( c.getInt( iId ) );
                m.setName( c.getString( iName ) );
                m.setContextGroupId( c.getInt( iGroup ) );
                contexts.add( m );
            }
        }
        close();
        return contexts;
    }
    /**
     * Gets a list of context groups belonging to a specific discipline
     * 
     * @param Discipline
     *            the discipline the context groups belong to
     * @return a list of the context groups
     */
    public List<ContextGroup> getContextGroupList( String Discipline ) {
        Cursor c = null;
        open();
        if ( Discipline == null ) {
            c = myDatabase.rawQuery( "SELECT * FROM " + CONTEXT_GROUP_TABLE + " ORDER BY " + CONTEXT_GROUP_NAME + " ASC", null );
        } else {
            c = myDatabase.rawQuery( "SELECT * FROM " + CONTEXT_GROUP_TABLE + " WHERE disciplineGroup = ? ORDER BY " + CONTEXT_GROUP_NAME + " ASC",
                            new String[]{Discipline} );
        }
        List<ContextGroup> contextGroups = new ArrayList<ContextGroup>();
        int iId = c.getColumnIndex( CONTEXT_GROUP_ID );
        int iName = c.getColumnIndex( CONTEXT_GROUP_NAME );
        int iGroup = c.getColumnIndex( SUBJECT_DISCIPLINE_GROUP );
        if ( c != null ) {
            for ( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() ) {
                ContextGroup m = new ContextGroup();
                m.setId( c.getInt( iId ) );
                m.setName( c.getString( iName ) );
                m.setDisciplineId( c.getInt( iGroup ) );
                contextGroups.add( m );
            }
        }
        close();
        return contextGroups;
    }
    /**
     * Gets a list of the disciplines
     * 
     * @return a list of the disciplines
     */
    public List<Discipline> getDisciplineList() {
        open();
        Cursor c = myDatabase.rawQuery( "SELECT * FROM " + DISCIPLINE_TABLE + " ORDER BY " + DISCIPLINE_NAME + " ASC", null );
        List<Discipline> disciplines = new ArrayList<Discipline>();
        int iId = c.getColumnIndex( DISCIPLINE_ID );
        int iName = c.getColumnIndex( DISCIPLINE_NAME );
        if ( c != null ) {
            for ( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() ) {
                Discipline s = new Discipline();
                s.setId( c.getInt( iId ) );
                s.setName( c.getString( iName ) );
                disciplines.add( s );
            }
        }
        close();
        return disciplines;
    }
}
