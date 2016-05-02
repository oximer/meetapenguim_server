package edu.cmu.java.development.application.database;

import edu.cmu.java.development.application.resources.Contact;

import java.sql.SQLException;

/**
 * Created by Prin on 5/2/2016.
 */
public interface RelationshipDatabase {
    void createRelationship(int userAid, Contact contact) throws SQLException;

    void updateRelationshipFromMessage(int userID, int messageID, long expiration) throws SQLException;
}
