/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.schematron;

import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author mccaffrey
 */
public class Schema {
    
    private Collection<Phase> phases = null;
    private Collection<Pattern> patterns = null;

    public Element toElement(Document doc) {
        
        Element schema = SchematronGeneration.createSchema(doc);
        
        if(phases != null) {            
            Iterator<Phase> it = this.getPhases().iterator();
            while(it.hasNext()) {
                schema.appendChild(it.next().toElement(doc));
            }
        }
        if(patterns != null) {
            Iterator<Pattern> it = this.getPatterns().iterator();
            while(it.hasNext()) {
                schema.appendChild(it.next().toElement(doc));
            }
        }
        
        return schema;
        
    }
    
    /**
     * @return the phases
     */
    public Collection<Phase> getPhases() {
        return phases;
    }

    /**
     * @param phases the phases to set
     */
    public void setPhases(Collection<Phase> phases) {
        this.phases = phases;
    }

    /**
     * @return the patterns
     */
    public Collection<Pattern> getPatterns() {
        return patterns;
    }

    /**
     * @param patterns the patterns to set
     */
    public void setPatterns(Collection<Pattern> patterns) {
        this.patterns = patterns;
    }
    
    
    
    
}
