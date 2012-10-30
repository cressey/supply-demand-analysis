
package com.vjs.supplydemandanalysis.skills;

public class ContextGroup extends DatabaseObject {
    private int disciplineId;
    public ContextGroup( String name, int disciplineId ) {
        super( name );
        this.disciplineId = disciplineId;
    }
    public ContextGroup() {
        super();
    }
    public int getDisciplineId() {
        return disciplineId;
    }
    public void setDisciplineId( int disciplineId ) {
        this.disciplineId = disciplineId;
    }
}
