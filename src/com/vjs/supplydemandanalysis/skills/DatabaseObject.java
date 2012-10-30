
package com.vjs.supplydemandanalysis.skills;

public class DatabaseObject extends Object {
    private int id;
    private String name;
    public DatabaseObject( int id, String name ) {
        this.id = id;
        this.name = name;
    }
    public DatabaseObject( String name ) {
        this.name = name;
    }
    public DatabaseObject() {
    }
    public int getId() {
        return id;
    }
    public void setId( int id ) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    }
}
