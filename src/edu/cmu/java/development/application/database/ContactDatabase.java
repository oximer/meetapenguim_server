package edu.cmu.java.development.application.database;

import edu.cmu.java.development.application.resources.Contact;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Prin on 5/1/2016.
 */
public interface ContactDatabase {
    void addContact(Contact contact) throws SQLException;

    Contact updateContact(Contact contact) throws SQLException;

    int getNextContactTableID() throws SQLException;

    Contact getContact(int contactID, int userID) throws SQLException;

    ArrayList<Contact> getContactsLastUpdated(long lastUpdated, int userID) throws SQLException;
}
