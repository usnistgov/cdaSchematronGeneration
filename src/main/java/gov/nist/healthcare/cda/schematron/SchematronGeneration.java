/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.schematron;

import gov.nist.healthcare.cda.model.Address;
import hl7OrgV3.AD;
import hl7OrgV3.AdxpStreetAddressLine;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author mccaffrey
 */
public class SchematronGeneration {

    public static Element createAddress(Document doc, Address addr) {

        Element assertElement = doc.createElementNS("http://www.ascc.net/xml/schematron", "assert");
        StringBuilder test = new StringBuilder();
        StringBuilder message = new StringBuilder();
        Collection<String> streetAddresses = addr.getStreetAddress();

        if (streetAddresses.size() > 0) {
            Iterator<String> it = streetAddresses.iterator();
            while(it.hasNext()) {
                String addressLine = it.next();
                if (test.length() != 0) {
                    test.append(" and ");
                }
                test.append("cda:addr/cda:streetAddressLine=\'" + addressLine + "\'");
                message.append("Street Address Line must be " + addressLine + " \n");
            }
        }
        if (addr.getCity() != null) {
            if (test.length() != 0) {
                test.append(" and ");
            }
            test.append("cda:addr/cda:city=\'" + addr.getCity() + "\'");
            message.append("City must be " + addr.getCity() + " \n");
            
        }
        if (addr.getState() != null) {
            if (test.length() != 0) {
                test.append(" and ");
            }
            test.append("cda:addr/cda:state=\'" + addr.getState() + "\'");
            message.append("State must be " + addr.getState() + " \n");
        }
        if (addr.getCounty() != null) {
            if (test.length() != 0) {
                test.append(" and ");
            }
            test.append("cda:addr/cda:county=\'" + addr.getCounty() + "\'");
            message.append("County must be " + addr.getCounty() + " \n");
        }
        if (addr.getPostalCode() != null) {
            if (test.length() != 0) {
                test.append(" and ");
            }
            test.append("cda:addr/cda:postalCode=\'" + addr.getPostalCode() + "\'");
            message.append("Postal Code must be " + addr.getPostalCode() + " \n");
        }
        
        if (addr.getCountry() != null ) {
            if (test.length() != 0) {
                test.append(" and ");
            }
            test.append("cda:addr/cda:country=\'" + addr.getCountry() + "\'");
            message.append("Country must be " + addr.getCountry() + " \n");
        }
        
        

        /*             <sch:assert test='cda:addr/cda:streetAddressLine="143 Taylor Street" and
                cda:addr/cda:state="MD" and
                cda:addr/cda:county="Ann Arundel" and
                cda:addr/cda:postalCode="21401" and
                cda:addr/cda:country="US"'>
                Street Address must be 143 Taylor Street
                City must be Annapolis
                State must be MD
                County must be Ann Arundel
                Postal Code must be 21401
                Country must be US
            </sch:assert>
         */
        assertElement.setAttribute("test", test.toString());
        
        assertElement.setTextContent(message.toString());
        return assertElement;
    }

    public static void main(String[] args) throws ParserConfigurationException {

        Document doc = XmlUtils.createDocument();

        Element schema = doc.createElementNS("http://www.ascc.net/xml/schematron", "schema");

        doc.appendChild(schema);
        Element nsXsi = doc.createElementNS("http://www.ascc.net/xml/schematron", "ns");
        nsXsi.setAttribute("prefix", "xsi");
        nsXsi.setAttribute("uri", "http://www.w3.org/2001/XMLSchema-instance");
        schema.appendChild(nsXsi);

        Element nsSdtc = doc.createElementNS("http://www.ascc.net/xml/schematron", "ns");
        nsSdtc.setAttribute("prefix", "sdtc");
        nsSdtc.setAttribute("uri", "urn:hl7-org:sdtc");
        schema.appendChild(nsSdtc);

        Element nsCda = doc.createElementNS("http://www.ascc.net/xml/schematron", "ns");
        nsCda.setAttribute("prefix", "cda");
        nsCda.setAttribute("uri", "urn:hl7-org:v3");
        schema.appendChild(nsCda);

        Element phaseErrors = doc.createElementNS("http://www.ascc.net/xml/schematron", "phase");
        phaseErrors.setAttribute("id", "errors");
        schema.appendChild(phaseErrors);

        Element activePatternDemoErrors = doc.createElementNS("http://www.ascc.net/xml/schematron", "active");
        activePatternDemoErrors.setAttribute("pattern", "p-PATIENT-DEMOGRAPHICS-ERRORS");
        phaseErrors.appendChild(activePatternDemoErrors);

        /*
        
            <sch:pattern name="PATIENT-DEMOGRAPHICS" id="p-PATIENT-DEMOGRAPHICS-ERRORS">
        <sch:rule context='/cda:ClinicalDocument/cda:recordTarget/cda:patientRole'>
            <sch:assert test='cda:addr/cda:streetAddressLine="143 Taylor Street" and
                cda:addr/cda:state="MD" and
                cda:addr/cda:county="Ann Arundel" and
                cda:addr/cda:postalCode="21401" and
                cda:addr/cda:country="US"'>
                Street Address must be 143 Taylor Street
                City must be Annapolis
                State must be MD
                County must be Ann Arundel
                Postal Code must be 21401
                Country must be US
            </sch:assert>
            
           
            
            
        </sch:rule>
            
        
        
    </sch:pattern>
         */
        //System.out.println(XmlUtils.xmlToString(doc));
        
        Address addr = new Address();
        addr.getStreetAddress().add("143 Taylor Street");
        addr.setCity("Annapolis");
        addr.setState("MD");
        addr.setCounty("Ann Arundel");
        addr.setPostalCode("21401");
        addr.setCountry("US");
        Element assertAddr = createAddress(doc, addr);
        System.out.println("Schematron = " + XmlUtils.xmlToString(assertAddr));
        
        

    }

}
