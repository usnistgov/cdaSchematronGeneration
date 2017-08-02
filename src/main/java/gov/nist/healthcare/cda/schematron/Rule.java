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
public class Rule {
 
    private String context = null;
    private Collection<Assert> asserts = null;

    public Element toElement(Document doc) {
        
        Element rule = doc.createElementNS("http://www.ascc.net/xml/schematron", "rule");
        rule.setAttribute("context", this.getContext());
        if(asserts != null) {
            
            Iterator<Assert> it = asserts.iterator();
            while(it.hasNext()) {
                Assert ass = it.next();
                rule.appendChild(ass.toElement(doc));                
            }            
        }

        return rule;
    }
    
    /**
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * @return the asserts
     */
    public Collection<Assert> getAsserts() {
        return asserts;
    }

    /**
     * @param asserts the asserts to set
     */
    public void setAsserts(Collection<Assert> asserts) {
        this.asserts = asserts;
    }
    
}
