
package com.vjs.supplydemandanalysis.skills;

public class MaterialGroup extends DatabaseObject {
    private int disciplineId;
    public MaterialGroup( String name, int disciplineId ) {
        super( name );
        this.disciplineId = disciplineId;
    }
    public MaterialGroup() {
        super();
    }
    public int getDisciplineId() {
        return disciplineId;
    }
    public void setDisciplineId( int disciplineId ) {
        this.disciplineId = disciplineId;
    }
}
