/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.schematron;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author mccaffrey
 */
public class Schema {
    
    private Phase errorPhases = null;
    private Phase warningPhases = null;    
    private Collection<Pattern> patterns = null;

    public Element toElement(Document doc) {
        
        Element schema = SchematronGeneration.createSchema(doc);
        
        if(getErrorPhases() != null && getErrorPhases().getPattern() != null && !getErrorPhases().getPattern().isEmpty()) {            

                schema.appendChild(getErrorPhases().toElement(doc));

        }        
        
        if(getWarningPhases() != null && getWarningPhases().getPattern() != null && !getWarningPhases().getPattern().isEmpty()) {            
                schema.appendChild(getWarningPhases().toElement(doc));
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
     * @return the patterns
     */
    public Collection<Pattern> getPatterns() {
        if (patterns == null)
            patterns = new ArrayList<>();
        return patterns;
    }

    /**
     * @param patterns the patterns to set
     */
    public void setPatterns(Collection<Pattern> patterns) {
        this.patterns = patterns;
    }

    /**
     * @return the errorPhases
     */
    public Phase getErrorPhases() {
        if(errorPhases == null) {
            errorPhases =  new Phase();
            errorPhases.setId("errors");
        }
        return errorPhases;
    }

    /**
     * @param errorPhases the errorPhases to set
     */
    public void setErrorPhases(Phase errorPhases) {
        this.errorPhases = errorPhases;
    }

    /**
     * @return the warningPhases
     */
    public Phase getWarningPhases() {
        if(warningPhases == null) {
            warningPhases =  new Phase();
            warningPhases.setId("warnings");
        }
        return warningPhases;
    }

    /**
     * @param warningPhases the warningPhases to set
     */
    public void setWarningPhases(Phase warningPhases) {
        this.warningPhases = warningPhases;
    }


    
    
    
    
}
