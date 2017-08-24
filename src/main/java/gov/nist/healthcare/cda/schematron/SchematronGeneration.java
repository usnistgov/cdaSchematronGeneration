/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.schematron;

import gov.nist.healthcare.cda.model.Address;
import gov.nist.healthcare.cda.model.AutopsyDetails;
import gov.nist.healthcare.cda.model.CauseOfDeath;
import gov.nist.healthcare.cda.model.CoronerReferral;
import gov.nist.healthcare.cda.model.DeathAdministration;
import gov.nist.healthcare.cda.model.DeathEvent;
import gov.nist.healthcare.cda.model.Name;
import gov.nist.healthcare.cda.model.PatientDemographics;
import gov.nist.healthcare.cda.model.VitalRecordsDeathReport;
import hl7OrgV3.AD;
import hl7OrgV3.AdxpStreetAddressLine;
import hl7OrgV3.ClinicalDocumentDocument1;
import java.sql.SQLException;
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

    public static void main(String[] args) throws ParserConfigurationException, SQLException {
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
        Schema schema = new Schema();
        VitalRecordsDeathReport vrdr = VitalRecordsDeathReport.getVRDRById("VRDR1");
        addPatientDemographicsRules(schema, vrdr);
        addCauseOfDeathRules(schema, vrdr);
        addDeathAdministrationsRules(schema, vrdr);
        addDeathEventRules(schema,vrdr);

        Document doc = XmlUtils.createDocument();
        System.out.println(XmlUtils.xmlToString(schema.toElement(doc)));

    }

    public static void addDeathAdministrationsRules(Schema schema, VitalRecordsDeathReport vrdr) throws SQLException {
        DeathAdministration da = vrdr.getDeathAdministration();
        AutopsyDetails ad = vrdr.getAutopsyDetails();
        CoronerReferral cr = vrdr.getCoronerReferral();
        Collection<Assert> asserts = new ArrayList<>();

        Assert timePronouncedDead = new Assert();
        timePronouncedDead.setTest("cda:entry/cda:act[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.15' and @extension='2016-12-01']]/cda:effectiveTime[@value = '" + da.getDateTimePronouncedDead() + "']");
        timePronouncedDead.setMessage("Time pronounced dead must be " + da.getDateTimePronouncedDead());
        asserts.add(timePronouncedDead);

        Assert resultsAvailable = new Assert();
        resultsAvailable.setTest("cda:entry/cda:procedure[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.2' and @extension='2016-12-01']]/cda:entryRelationship/cda:observation[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.3' and @extension='2016-12-01']]/cda:value[@value = '" + ad.getResultsAvailable() + "']");
        resultsAvailable.setMessage("Results available must be " + ad.getResultsAvailable());
        asserts.add(resultsAvailable);

        Assert coronerRefNote = new Assert();
        coronerRefNote.setTest("cda:entry/cda:act[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.4' and @extension='2016-12-01']]/cda:entryRelationship/cda:observation[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.5']]/cda:value = '" + cr.getCoronerReferralNote() + "'");
        coronerRefNote.setMessage("Coroner Referral Note must be " + cr.getCoronerReferralNote());
        asserts.add(coronerRefNote);

        Rule deathAdminRule = new Rule();

        deathAdminRule.setContext("//cda:section[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.2.3' and @extension='2016-12-01']]");
        deathAdminRule.setAsserts(asserts);

        Pattern deathAdminPattern = new Pattern();
        deathAdminPattern.setPatternId("DEATH-ADMIN");
        deathAdminPattern.getRules().add(deathAdminRule);

        //Phase errorsPhase = new Phase();
        //errorsPhase.setId("errors");
        //errorsPhase.getPattern().add("CAUSE-OF-DEATH");
        schema.getPatterns().add(deathAdminPattern);
        schema.getErrorPhases().getPattern().add("DEATH-ADMIN");
    }

    public static void addDeathEventRules(Schema schema, VitalRecordsDeathReport vrdr) throws SQLException {
        DeathEvent de = vrdr.getDeathEvent();

        Collection<Assert> asserts = new ArrayList<>();
        Assert mannerAssert = new Assert();
        mannerAssert.setTest("cda:entry/cda:observation[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.11']]/cda:value[@code = '" + de.getMannerOfDeath() + "']");
        mannerAssert.setMessage("Manner of death must be " + de.getMannerOfDeath());
        asserts.add(mannerAssert);
                        
        Rule deathEventRule = new Rule();

        deathEventRule.setContext("//cda:section[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.2.6' and @extension='2016-12-01']]");
        deathEventRule.setAsserts(asserts);

        Pattern deathEventPattern = new Pattern();
        deathEventPattern.setPatternId("DEATH-EVENT");
        deathEventPattern.getRules().add(deathEventRule);

        //Phase errorsPhase = new Phase();
        //errorsPhase.setId("errors");
        //errorsPhase.getPattern().add("CAUSE-OF-DEATH");
        schema.getPatterns().add(deathEventPattern);
        schema.getErrorPhases().getPattern().add("DEATH-EVENT");
        
        
        
        
        
    }

    public static void addCauseOfDeathRules(Schema schema, VitalRecordsDeathReport vrdr) throws SQLException {

        CauseOfDeath cod = vrdr.getCauseOfDeath();

        Collection<Assert> asserts = new ArrayList<>();
        Assert injuryAssert = new Assert();
        injuryAssert.setTest("cda:entry/cda:organizer[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.9' and @extension='2016-12-01']]/cda:component/cda:observation[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.3.40' and @extension='2016-12-01']]/cda:value[@code = '" + cod.getInjuryInvolvedInDeath() + "']");
        injuryAssert.setMessage("Injury involved must be " + cod.getInjuryInvolvedInDeath());

        /*
        <rule context="//cda:section[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.2.5' and @extension='2016-12-01']]">
            

            <assert test="cda:entry/cda:organizer[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.9' and @extension='2016-12-01']]/cda:component/cda:observation[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.3.40' and @extension='2016-12-01']]/cda:value[@code = 'dope']">            
            Must be nope!!!    
            </assert>
        </rule>
         */
        Assert tobaccoAssert = new Assert();
        tobaccoAssert.setTest("cda:entry/cda:observation[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.14' and @extension='2016-12-01']]/cda:value[@code = '" + cod.getTobaccoUse() + "']");
        tobaccoAssert.setMessage("Tobacco Use must be " + cod.getTobaccoUse());

//                    <assert test="cda:entry/cda:observation[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.14' and @extension='2016-12-01']]/cda:value[@code = 'nope']">
        asserts.add(injuryAssert);
        asserts.add(tobaccoAssert);

        Rule causeOfDeathRule = new Rule();

        causeOfDeathRule.setContext("//cda:section[cda:templateId[@root='2.16.840.1.113883.10.20.26.1.2.5' and @extension='2016-12-01']]");
        causeOfDeathRule.setAsserts(asserts);

        Pattern causeOfDeathPattern = new Pattern();
        causeOfDeathPattern.setPatternId("CAUSE-OF-DEATH");
        causeOfDeathPattern.getRules().add(causeOfDeathRule);

        //Phase errorsPhase = new Phase();
        //errorsPhase.setId("errors");
        //errorsPhase.getPattern().add("CAUSE-OF-DEATH");
        schema.getPatterns().add(causeOfDeathPattern);
        schema.getErrorPhases().getPattern().add("CAUSE-OF-DEATH");

    }

    public static void addPatientDemographicsRules(Schema schema, VitalRecordsDeathReport vrdr) throws SQLException {

        PatientDemographics demo = vrdr.getPatientDemographics();

        Collection<Assert> asserts = new ArrayList<>();
        Assert patientDemoSexAssert = new Assert();
        patientDemoSexAssert.setMessage("Patient Sex Must be " + demo.getSex());
        patientDemoSexAssert.setTest("cda:patient/cda:administrativeGenderCode[@code='" + demo.getSex() + "']");
        asserts.add(patientDemoSexAssert);

        Assert patientDemoBirthTimeAssert = new Assert();
        patientDemoBirthTimeAssert.setMessage("Patient Birth Time Must be " + demo.getBirthTime());
        patientDemoBirthTimeAssert.setTest("cda:patient/cda:birthTime[@value='" + demo.getBirthTime() + "']");
        asserts.add(patientDemoBirthTimeAssert);

        Assert patientDemoDeathTimeAssert = new Assert();
        patientDemoDeathTimeAssert.setMessage("Patient Death Time Must be " + demo.getDeathTime());
        patientDemoDeathTimeAssert.setTest("cda:patient/sdtc:deceasedTime[@value='" + demo.getDeathTime() + "']");
        asserts.add(patientDemoDeathTimeAssert);

        Assert patientRaceCode1Assert = new Assert();
        patientRaceCode1Assert.setMessage("Patient Race Code (1) Must be " + demo.getRaceCode1());
        patientRaceCode1Assert.setTest("cda:patient/cda:raceCode[@code='" + demo.getRaceCode1() + "']");
        asserts.add(patientRaceCode1Assert);

        Assert patientRaceCode2Assert = new Assert();
        patientRaceCode2Assert.setMessage("Patient Race Code (2) Must be " + demo.getRaceCode2());
        patientRaceCode2Assert.setTest("cda:patient/sdtc:raceCode[@code='" + demo.getRaceCode2() + "']");
        asserts.add(patientRaceCode2Assert);
        
        Assert patientEthnicCodeAssert = new Assert();
        patientEthnicCodeAssert.setMessage("Patient Ethnic Group Code Must be " + demo.getEthnicGroup());
        patientEthnicCodeAssert.setTest("cda:patient/cda:ethnicGroupCode[@code='" + demo.getEthnicGroup() + "']");
        asserts.add(patientEthnicCodeAssert);
  
        Assert patientDemoSsnAssert = new Assert();
        patientDemoSsnAssert.setMessage("Patient SSN Must be " + demo.getSocialSecurityNumber());
        patientDemoSsnAssert.setTest("cda:id[@codeSystem = '2.16.840.1.113883.4.1' and @extension='" + demo.getSocialSecurityNumber() + "']");
        asserts.add(patientDemoSsnAssert);

        Rule patientDemoRule = new Rule();

        patientDemoRule.setContext("/cda:ClinicalDocument/cda:recordTarget/cda:patientRole");
        patientDemoRule.setAsserts(asserts);

        Pattern patientDemoPattern = new Pattern();
        patientDemoPattern.setPatternId("PATIENT-DEMOGRAPHICS");
        patientDemoPattern.getRules().add(patientDemoRule);

        //Phase errorsPhase = new Phase();
        //errorsPhase.setId("errors");
        //errorsPhase.getPattern().add("PATIENT-DEMOGRAPHICS");
        schema.getPatterns().add(patientDemoPattern);
        schema.getErrorPhases().getPattern().add("PATIENT-DEMOGRAPHICS");
    }

}
