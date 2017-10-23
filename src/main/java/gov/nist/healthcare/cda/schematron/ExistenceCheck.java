/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.schematron;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mccaffrey
 */
public class ExistenceCheck {

    private String xpath = null;
    private String message = null;

    public static Collection<ExistenceCheck> getAllExistenceChecks() {

        Collection<ExistenceCheck> checks = new ArrayList<>();

        try {
            DatabaseConnection db = new DatabaseConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * ");
            sql.append("FROM " + DatabaseConnection.EXISTENCE_CHECK_NAME);                     

            ResultSet result = db.executeQuery(sql.toString());
            while(result.next()) {
                ExistenceCheck check = new ExistenceCheck();
          
                check.setXpath(result.getString(DatabaseConnection.EXISTENCE_CHECK_XPATH));
                check.setMessage(result.getString(DatabaseConnection.EXISTENCE_CHECK_MESSAGE));
                checks.add(check);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ExistenceCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checks;
        
    }

    /**
     * @return the xpath
     */
    public String getXpath() {
        return xpath;
    }

    /**
     * @param aXpath the xpath to set
     */
    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param aMessage the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
