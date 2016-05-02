package edu.cmu.java.development.application.database;

import edu.cmu.java.development.application.resources.InboxMessage;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Prin on 5/2/2016.
 */
public interface MessageDatabase {
    InboxMessage getMessage(int messageID) throws SQLException;

    void deleteMessage(int messageID) throws SQLException;

    ArrayList<InboxMessage> getMessagesLastUpdated(long lastUpdated, int userID) throws SQLException;
}
