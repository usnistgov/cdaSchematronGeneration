/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.schematron;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author mccaffrey
 */
public class Assert {
    
    private String test = null;
    private String message = null;
  
    public Element toElement(Document doc) {
    
        Element assertElement = doc.createElementNS("http://www.ascc.net/xml/schematron", "assert");
        assertElement.setAttribute("test", this.getTest());
        assertElement.setTextContent(this.getMessage());
        return assertElement;
        
    }
    
    /*
    <assert
                test="cda:addr/cda:streetAddressLine = '143 Taylor Street' and cda:addr/cda:city = 'Annapolis' and cda:addr/cda:state = 'MD' and cda:addr/cda:county = 'Ann Arundel' and cda:addr/cda:postalCode = '21401' and cda:addr/cda:country = 'US'"
                >Street Address Line must be 143 Taylor Street City must be Annapolis State must be
                MD County must be Ann Arundel Postal Code must be 21401 Country must be US </assert>
*/

    /**
     * @return the test
     */
    public String getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(String test) {
        this.test = test;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
}
