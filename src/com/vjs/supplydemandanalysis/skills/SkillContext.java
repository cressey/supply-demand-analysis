
package com.vjs.supplydemandanalysis.skills;

public class SkillContext extends DatabaseObject {
    private int contextGroupId;
    public SkillContext( String name, int contextGroupId ) {
        super( name );
        this.contextGroupId = contextGroupId;
    }
    public SkillContext() {
        super();
    }
    public int getContextGroupId() {
        return contextGroupId;
    }
    public void setContextGroupId( int contextGroupId ) {
        this.contextGroupId = contextGroupId;
    }
    public boolean equals( SkillContext other ) {
        if ( this.getId() == other.getId() ) {
            return true;
        } else {
            return false;
        }
    }
    public int hashCode() {
        return getId();
    }
}
