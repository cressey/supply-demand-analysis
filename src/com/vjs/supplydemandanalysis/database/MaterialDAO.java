
package com.vjs.supplydemandanalysis.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.vjs.supplydemandanalysis.skills.Discipline;
import com.vjs.supplydemandanalysis.skills.Material;
import com.vjs.supplydemandanalysis.skills.MaterialGroup;

public class MaterialDAO extends DatabaseStuff {
    public MaterialDAO( Context c ) {
        super( c );
        // TODO Auto-generated constructor stub
    }
    public long createMaterialEntry( String id, String name, String materialGroup ) {
        ContentValues cv = new ContentValues();
        cv.put( MATERIAL_ID, id );
        cv.put( MATERIAL_NAME, name );
        cv.put( MATERIAL_GROUP_ID, materialGroup );
        return myDatabase.insert( MATERIAL_TABLE, null, cv );
    }
    /**
     * Gets a list of contexts belonging to a specific contexts group
     * 
     * @param MaterialGroup
     *            the contexts group the contexts belong to
     * @return a list of the contexts
     */
    public List<Material> getMaterialList( int materialGroupId ) {
        List<Material> materials = new ArrayList<Material>();
        open();
        Cursor c = myDatabase.rawQuery( "SELECT * FROM " + MATERIAL_TABLE + " WHERE " + MATERIAL_GROUP_ID + " = ? ORDER BY " + MATERIAL_NAME + " ASC",
                        new String[]{Integer.toString( materialGroupId )} );
        int iId = c.getColumnIndex( MATERIAL_ID );
        int iName = c.getColumnIndex( MATERIAL_NAME );
        int iGroup = c.getColumnIndex( MATERIAL_GROUP_ID );
        if ( c != null ) {
            for ( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() ) {
                Material m = new Material();
                m.setId( c.getInt( iId ) );
                m.setName( c.getString( iName ) );
                m.setMaterialGroupId( c.getInt( iGroup ) );
                materials.add( m );
            }
        }
        close();
        return materials;
    }
    /**
     * Gets a list of material groups belonging to a specific discipline
     * 
     * @param Discipline
     *            the discipline the material groups belong to
     * @return a list of the material groups
     */
    public List<MaterialGroup> getMaterialGroupList( String Discipline ) {
        Cursor c = null;
        open();
        if ( Discipline == null ) {
            c = myDatabase.rawQuery( "SELECT * FROM " + MATERIAL_GROUP_TABLE + " ORDER BY " + MATERIAL_GROUP_NAME + " ASC", null );
        } else {
            c = myDatabase.rawQuery( "SELECT * FROM " + MATERIAL_GROUP_TABLE + " WHERE disciplineGroup = ? ORDER BY " + MATERIAL_GROUP_NAME + " ASC",
                            new String[]{Discipline} );
        }
        List<MaterialGroup> materialGroups = new ArrayList<MaterialGroup>();
        int iId = c.getColumnIndex( MATERIAL_GROUP_ID );
        int iName = c.getColumnIndex( MATERIAL_GROUP_NAME );
        int iGroup = c.getColumnIndex( SUBJECT_DISCIPLINE_GROUP );
        if ( c != null ) {
            for ( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() ) {
                MaterialGroup m = new MaterialGroup();
                m.setId( c.getInt( iId ) );
                m.setName( c.getString( iName ) );
                m.setDisciplineId( c.getInt( iGroup ) );
                materialGroups.add( m );
            }
        }
        close();
        return materialGroups;
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
