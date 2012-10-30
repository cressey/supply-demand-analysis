
package com.vjs.supplydemandanalysis.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.vjs.supplydemandanalysis.skills.Discipline;
import com.vjs.supplydemandanalysis.skills.Skill;
import com.vjs.supplydemandanalysis.skills.SkillGroup;
import com.vjs.supplydemandanalysis.skills.Subject;

public class SkillDAO extends DatabaseStuff {
    public SkillDAO( Context c ) {
        super( c );
        // TODO Auto-generated constructor stub
    }
    public long createSKILLEntry( String id, String name, String SKILLGroup ) {
        ContentValues cv = new ContentValues();
        cv.put( SKILL_ID, id );
        cv.put( SKILL_NAME, name );
        cv.put( SKILL_GROUP_ID, SKILLGroup );
        return myDatabase.insert( SKILL_TABLE, null, cv );
    }
    /**
     * Gets a list of skills belonging to a specific skills group
     * 
     * @param SkillGroup
     *            the skill group the skills belong to
     * @return a list of the skills
     */
    public List<Skill> getSkillList( int skillGroupId ) {
        List<Skill> skills = new ArrayList<Skill>();
        open();
        Cursor c = myDatabase.rawQuery( "SELECT * FROM " + SKILL_TABLE + " WHERE " + SKILL_GROUP_ID + " = ? ORDER BY " + SKILL_NAME + " ASC",
                        new String[]{Integer.toString( skillGroupId )} );
        int iId = c.getColumnIndex( SKILL_ID );
        int iName = c.getColumnIndex( SKILL_NAME );
        int iGroup = c.getColumnIndex( SKILL_GROUP_ID );
        if ( c != null ) {
            for ( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() ) {
                Skill s = new Skill();
                s.setId( c.getInt( iId ) );
                s.setName( c.getString( iName ) );
                s.setSkillGroupId( c.getInt( iGroup ) );
                skills.add( s );
            }
        }
        close();
        return skills;
    }
    /**
     * Gets a list of skill groups belonging to a specific subject
     * 
     * @param SubjectGroup
     *            the subject the skill groups belong to
     * @return a list of the skill groups
     */
    public List<SkillGroup> getSkillGroupList( int SubjectGroup ) {
        open();
        Cursor c = myDatabase.rawQuery( "SELECT * FROM " + SKILL_GROUP_TABLE + " WHERE " + SUBJECT_ID + " = ? ORDER BY " + SKILL_GROUP_NAME + " ASC",
                        new String[]{String.valueOf( SubjectGroup )} );
        List<SkillGroup> skillGroups = new ArrayList<SkillGroup>();
        int iId = c.getColumnIndex( SKILL_GROUP_ID );
        int iName = c.getColumnIndex( SKILL_GROUP_NAME );
        int iGroup = c.getColumnIndex( SUBJECT_ID );
        if ( c != null ) {
            for ( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() ) {
                SkillGroup s = new SkillGroup();
                s.setId( c.getInt( iId ) );
                s.setName( c.getString( iName ) );
                s.setSubjectId( c.getInt( iGroup ) );
                skillGroups.add( s );
            }
        }
        close();
        return skillGroups;
    }
    /**
     * Gets a list of subjects belonging to a specific discipline
     * 
     * @param Discipline
     *            the discipline the subjects belong to
     * @return a list of the subjects
     */
    public List<Subject> getSubjectList( String Discipline ) {
        Cursor c = null;
        open();
        if ( Discipline == null ) {
            c = myDatabase.rawQuery( "SELECT * FROM " + SUBJECT_TABLE + " ORDER BY " + SUBJECT_NAME + " ASC", null );
        } else {
            c = myDatabase.rawQuery( "SELECT * FROM " + SUBJECT_TABLE + " WHERE disciplineGroup = ? ORDER BY " + SUBJECT_NAME + " ASC",
                            new String[]{Discipline} );
        }
        List<Subject> subjects = new ArrayList<Subject>();
        int iId = c.getColumnIndex( SUBJECT_ID );
        int iName = c.getColumnIndex( SUBJECT_NAME );
        int iGroup = c.getColumnIndex( SUBJECT_DISCIPLINE_GROUP );
        if ( c != null ) {
            for ( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() ) {
                Subject s = new Subject();
                s.setId( c.getInt( iId ) );
                s.setName( c.getString( iName ) );
                s.setDisciplineId( c.getInt( iGroup ) );
                subjects.add( s );
            }
        }
        close();
        return subjects;
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
