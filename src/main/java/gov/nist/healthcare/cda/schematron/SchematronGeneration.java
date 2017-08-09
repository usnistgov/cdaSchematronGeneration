/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.schematron;

import gov.nist.healthcare.cda.model.Address;
import gov.nist.healthcare.cda.model.Name;
import gov.nist.healthcare.cda.model.PatientDemographics;
import hl7OrgV3.AD;
import hl7OrgV3.AdxpStreetAddressLine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author mccaffrey
 */
public class SchematronGeneration {

    public static Element createName(Document doc, Name name) {
        Element assertElement = doc.createElementNS("http://www.ascc.net/xml/schematron", "assert");
        StringBuilder test = new StringBuilder();
        StringBuilder message = new StringBuilder();

        if (name.getFirstName() != null) {
            if (test.length() != 0) {
                test.append(" and ");
            }
            test.append("cda:name/cda:givenName=\'" + name.getFirstName() + "\'");
            message.append("First name must be " + name.getFirstName() + " \n");
        }

        if (name.getMiddleName() != null) {
            if (test.length() != 0) {
                test.append(" and ");
            }
            test.append("cda:name/cda:givenName=\'" + name.getMiddleName() + "\'");
            message.append("Middle name must be " + name.getMiddleName() + " \n");
        }

        if (name.getLastName() != null) {
            if (test.length() != 0) {
                test.append(" and ");
            }
            test.append("cda:name/cda:familyName=\'" + name.getLastName() + "\'");
            message.append("Family name must be " + name.getLastName() + " \n");
        }

        if (name.getPrefix() != null) {
            if (test.length() != 0) {
                test.append(" and ");
            }
            test.append("cda:name/cda:prefix=\'" + name.getPrefix() + "\'");
            message.append("Prefix must be " + name.getPrefix() + " \n");
        }

        if (name.getSuffix() != null) {
            if (test.length() != 0) {
                test.append(" and ");
            }
            test.append("cda:name/cda:suffix=\'" + name.getSuffix() + "\'");
            message.append("Suffix must be " + name.getSuffix() + " \n");
        }

        assertElement.setAttribute("test", test.toString());
        assertElement.setTextContent(message.toString());
        return assertElement;

    }

    public static Element createAddress(Document doc, Address addr) {

        Element assertElement = doc.createElementNS("http://www.ascc.net/xml/schematron", "assert");
        StringBuilder test = new StringBuilder();
        StringBuilder message = new StringBuilder();
        Collection<String> streetAddresses = new ArrayList<String>();
        if (addr.getStreetAddress1() != null) {
            streetAddresses.add(addr.getStreetAddress1());
        }
        if (addr.getStreetAddress2() != null) {
            streetAddresses.add(addr.getStreetAddress2());
        }

        if (streetAddresses.size() > 0) {
            Iterator<String> it = streetAddresses.iterator();
            while (it.hasNext()) {
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

        if (addr.getCountry() != null) {
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

    public static Element createSchema(Document doc) throws DOMException {
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
        return schema;
    }

    private static void addPhases(Document doc, Element schema, Collection<String> errorPatterns, Collection<String> warningPatterns, Collection<String> notePatterns) throws DOMException {
        if (errorPatterns != null && !errorPatterns.isEmpty()) {
            Element phaseErrors = doc.createElementNS("http://www.ascc.net/xml/schematron", "phase");
            phaseErrors.setAttribute("id", "errors");
            schema.appendChild(phaseErrors);

            Iterator<String> it = errorPatterns.iterator();
            while (it.hasNext()) {
                Element activePatternDemoErrors = doc.createElementNS("http://www.ascc.net/xml/schematron", "active");
                activePatternDemoErrors.setAttribute("pattern", it.next());
                phaseErrors.appendChild(activePatternDemoErrors);
            }

        }
        if (warningPatterns != null && !warningPatterns.isEmpty()) {
            Element phaseWarnings = doc.createElementNS("http://www.ascc.net/xml/schematron", "phase");
            phaseWarnings.setAttribute("id", "warnings");
            schema.appendChild(phaseWarnings);

            Iterator<String> it = warningPatterns.iterator();
            while (it.hasNext()) {
                Element activePatternDemoWarnings = doc.createElementNS("http://www.ascc.net/xml/schematron", "active");
                activePatternDemoWarnings.setAttribute("pattern", it.next());
                phaseWarnings.appendChild(activePatternDemoWarnings);
            }

        }
        if (notePatterns != null && !notePatterns.isEmpty()) {
            Element phaseNotes = doc.createElementNS("http://www.ascc.net/xml/schematron", "phase");
            phaseNotes.setAttribute("id", "notes");
            schema.appendChild(phaseNotes);

            Iterator<String> it = notePatterns.iterator();
            while (it.hasNext()) {
                Element activePatternDemoNotes = doc.createElementNS("http://www.ascc.net/xml/schematron", "active");
                activePatternDemoNotes.setAttribute("pattern", it.next());
                phaseNotes.appendChild(activePatternDemoNotes);
            }

        }

    }

    public static void main(String[] args) throws ParserConfigurationException {
/*
        Document doc = XmlUtils.createDocument();

        Element schema = createSchema(doc);

        Collection<String> patternErrorsNames = new ArrayList<>();
        patternErrorsNames.add("PATIENT-DEMOGRAPHICS-ERRORS");
        
        addPhases(doc, schema, patternErrorsNames, null, null);

        Element patternErrors = doc.createElementNS("http://www.ascc.net/xml/schematron", "pattern");
        patternErrors.setAttribute("name", "PATIENT-DEMOGRAPHICS");
        patternErrors.setAttribute("id", "p-PATIENT-DEMOGRAPHICS-ERRORS");
        schema.appendChild(patternErrors);

        //  <sch:pattern name="PATIENT-DEMOGRAPHICS" id="p-PATIENT-DEMOGRAPHICS-ERRORS">
        Element ruleAddress = doc.createElementNS("http://www.ascc.net/xml/schematron", "rule");
        ruleAddress.setAttribute("context", "/cda:ClinicalDocument/cda:recordTarget/cda:patientRole");
        patternErrors.appendChild(ruleAddress);

        Address addr = new Address();
        addr.setStreetAddress1("143 Taylor Street");
        addr.setCity("Annapolis");
        addr.setState("MD");
        addr.setCounty("Ann Arundel");
        addr.setPostalCode("21401");
        addr.setCountry("US");
        Element assertAddr = createAddress(doc, addr);

        ruleAddress.appendChild(assertAddr);

        System.out.println(XmlUtils.xmlToString(doc));
*/

    

    
    
    PatientDemographics demo = new PatientDemographics();
    demo.setSex("F");
    
    Assert patientDemoSexAssert = new Assert();
    
    Schema schema = new Schema();
    
    
    patientDemoSexAssert.setMessage("Patient Sex Must be " + demo.getSex());    
    patientDemoSexAssert.setTest("cda:patient/cda:administrativeGender[@code='" + demo.getSex() + "']");

    Rule patientDemoRule = new Rule();
    
    patientDemoRule.setContext("/cda:ClinicalDocument/cda:recordTarget/cda:patientRole");
    patientDemoRule.getAsserts().add(patientDemoSexAssert);
    
    Pattern patientDemoPattern = new Pattern();
    patientDemoPattern.setPatternId("PATIENT-DEMOGRAPHICS");
    patientDemoPattern.getRules().add(patientDemoRule);
    
    Phase errorsPhase = new Phase();
    errorsPhase.setId("errors");
    errorsPhase.getPattern().add("PATIENT-DEMOGRAPHICS");
    
    schema.getPatterns().add(patientDemoPattern);
    schema.getPhases().add(errorsPhase);
    
    Document doc = XmlUtils.createDocument();
    System.out.println(XmlUtils.xmlToString(schema.toElement(doc)));
    
    }

}
