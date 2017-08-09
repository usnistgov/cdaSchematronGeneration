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
public class Phase {
    
    private String id = null;
    private Collection<String> pattern = null;

    public Element toElement(Document doc) {
        
        Element phase = doc.createElementNS("http://www.ascc.net/xml/schematron", "phase");
        phase.setAttribute("id", this.getId());        
        
        Iterator<String> it = this.getPattern().iterator();
        while (it.hasNext()) {
            Element activePattern = doc.createElementNS("http://www.ascc.net/xml/schematron", "active");
            activePattern.setAttribute("pattern", it.next());
            phase.appendChild(activePattern);
        }
        
        return phase;
        
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the pattern
     */
    public Collection<String> getPattern() {
        if (pattern == null)
            pattern = new ArrayList<>();
        return pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(Collection<String> pattern) {
        this.pattern = pattern;
    }
    
    
    
}
