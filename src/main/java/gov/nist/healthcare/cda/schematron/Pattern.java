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
public class Pattern {

    private String patternId = null;
    private Collection<Rule> rules = null;

    public Element toElement(Document doc) {

        Element pattern = doc.createElementNS("http://www.ascc.net/xml/schematron", "pattern");
        pattern.setAttribute("id", this.getPatternId());
        pattern.setAttribute("name", this.getPatternId());

        if (rules != null) {
            Iterator<Rule> it = rules.iterator();
            while (it.hasNext()) {
                Rule rule = it.next();
                pattern.appendChild(rule.toElement(doc));
            }
        }
        return pattern;
    }

//    <pattern id="p-PATIENT-DEMOGRAPHICS-ERRORS" name="PATIENT-DEMOGRAPHICS">
    /**
     * @return the patternId
     */
    public String getPatternId() {
        return patternId;
    }

    /**
     * @param patternId the patternId to set
     */
    public void setPatternId(String patternId) {
        this.patternId = patternId;
    }

    /**
     * @return the rules
     */
    public Collection<Rule> getRules() {
        if(rules == null)
            rules = new ArrayList<>();
        return rules;
    }

    /**
     * @param rules the rules to set
     */
    public void setRules(Collection<Rule> rules) {
        this.rules = rules;
    }

}
