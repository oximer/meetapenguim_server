package edu.cmu.java.development.application.database;

import edu.cmu.java.development.application.resources.Attribute;
import edu.cmu.java.development.application.resources.Contact;
import edu.cmu.java.development.application.resources.ContactInfo;
import edu.cmu.java.development.application.resources.InboxMessage;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Prin on 4/24/2016.
 */
public class Database {

    private Connection connect;
    private Statement statement;
    private ResultSet resultSet;

    public Database() {
        String url = "jdbc:mysql://meetapenguim.cfedu7xab5q8.us-west-2.rds.amazonaws.com:3306/meetapenguin";
        String user = "Prin";
        String pw = System.getProperty("DATABASE_PASSWORD");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pw);
            statement = connect.createStatement();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(pw);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds contact to contact and contact info tables.
     *
     * @param contact
     */
    public void addContact(Contact contact) throws SQLException {
        int contactID = contact.getId();
        Timestamp timestamp = new Timestamp(new Date().getTime());

        //Insert into contact table.
        String command = "insert into contact values(default, '%s','%s','%s', '%s');";
        command = String.format(command, contact.getName(),
                contact.getDescription(), contact.getPhotoUrl(), timestamp.toString());

        statement.executeUpdate(command);

        //Insert contactInfo into contact_info table.
        for (ContactInfo contactInfo : contact.getContactInfoArrayList()) {
            insertContactInfo(contactID, contactInfo);
        }

    }

    public int insertContactInfo(Integer contactId, ContactInfo contactInfo) throws SQLException {
        String attribute = contactInfo.getAttribute().getName();

        String command = "select id from attribute where name='%s'";
        command = String.format(command, attribute);
        resultSet = statement.executeQuery(command);

        resultSet.next();
        int attributeid = resultSet.getInt(1);
        resultSet.close();

        command = "insert into contact_info values(default, '%s', %d, %d)";
        command = String.format(command, contactInfo.getAttributeValue(),
                attributeid, contactId);

        Statement stmt = connect.createStatement();
        stmt.executeUpdate(command, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = stmt.getGeneratedKeys();
        resultSet.next();
        int result = resultSet.getInt(1);
        stmt.close();
        return result;
    }

    public void updateContactInfo(ContactInfo contactInfo) throws SQLException {
        String command = "update contact_info set value='%s' where id=" + contactInfo.getId();
        command = String.format(command, contactInfo.getAttributeValue());

        Statement stmt = connect.createStatement();
        stmt.executeUpdate(command);
        stmt.close();
    }

    public ContactInfo GetContactInfo(Integer contactInfoId) throws SQLException {
        String command = "select * from contact_info where id = %d";
        command = String.format(command, contactInfoId);

        Statement stmt = connect.createStatement();
        ResultSet resultSet = stmt.executeQuery(command);

        ContactInfo contactInfo = null;
        //Loop over all contacts found. Create contact objects.
        if (resultSet.next()) {
            contactInfo = new ContactInfo();
            contactInfo.setId(resultSet.getInt(1));
            contactInfo.setAttributeValue(resultSet.getString(2));
            Attribute attribute = getAttribute(resultSet.getInt(3));
            contactInfo.setAttribute(attribute);
        }

        resultSet.close();
        stmt.close();
        return contactInfo;
    }

    public Attribute getAttribute(Integer attributeId) throws SQLException {
        String command = "select * from attribute where id = %d";
        command = String.format(command, attributeId);

        Statement stmt = connect.createStatement();
        ResultSet resultSet = stmt.executeQuery(command);

        Attribute attribute = null;
        //Loop over all contacts found. Create contact objects.
        if (resultSet.next()) {
            attribute = new Attribute();
            attribute.setId(resultSet.getInt(1));
            attribute.setName(resultSet.getString(2));
        }

        resultSet.close();
        stmt.close();
        return attribute;
    }

    public List<ContactInfo> getContactInfoFromUser(Integer contactId) throws SQLException {
        String command = "select * from contact_info where contactid = %d";
        command = String.format(command, contactId);

        Statement stmt = connect.createStatement();
        ResultSet resultSet = stmt.executeQuery(command);

        List<ContactInfo> contactInfoList = new ArrayList<ContactInfo>();
        //Loop over all contacts found. Create contact objects.
        while (resultSet.next()) {
            ContactInfo contactInfo = GetContactInfo(resultSet.getInt(1));
            contactInfoList.add(contactInfo);
        }

        resultSet.close();
        stmt.close();
        return contactInfoList;
    }

    /**
     * Delete a contact info
     *
     * @param contactInfoId con
     * @return Return true if delete, return false if fail.
     * @throws SQLException
     */
    public boolean deleteContactInfo(int contactInfoId) throws SQLException {
        String command = "delete from contact_info where id = %d";
        command = String.format(command, contactInfoId);

        Statement stmt = connect.createStatement();
        int result = stmt.executeUpdate(command);

        stmt.close();
        return result > 0;
    }

    public Contact updateContact(Contact contact) throws SQLException {
        int contactID = contact.getId();
        Timestamp timestamp = new Timestamp(new Date().getTime());

        //Update contact table.
        String command = "update contact set name='%s',description='%s', photoUrl='%s',lastUpdateTime='%s' where id=%d";
        command = String.format(command, contact.getName(),
                contact.getDescription(), contact.getPhotoUrl(), timestamp.toString(), contactID);
        statement.executeUpdate(command);

        Set<ContactInfo> newContactInfoList = contact.getContactInfoArrayList();
        List<ContactInfo> contactInfoListToRemove = getContactInfoFromUser(contactID);
        contactInfoListToRemove.removeAll(newContactInfoList);

        //Contacts to be removed
        for (ContactInfo info : contactInfoListToRemove) {
            deleteContactInfo(info.getId());
        }

        //Update contact_info table.
        for (ContactInfo contactInfo : newContactInfoList) {
            String attribute = contactInfo.getAttribute().getName();

            command = "select id from attribute where name='%s'";
            command = String.format(command, attribute);
            resultSet = statement.executeQuery(command);

            resultSet.next();
            int attributeid = resultSet.getInt(1);
            resultSet.close();

            //Find if contact info is already in table.
            command = "select id from contact_info where id=%d";
            command = String.format(command, contactInfo.getId());
            resultSet = statement.executeQuery(command);

            //If the contact_info is already in the table, update the value.
            if (resultSet.next()) {
                updateContactInfo(contactInfo);
            }
            //If it is not in the table, insert into the table.
            else {
                contactInfo.setId(insertContactInfo(contactID, contactInfo));
            }
        }
        return contact;
    }

    public int getNextContactTableID() throws SQLException {
        String command = "select auto_increment from information_schema.tables where table_schema = 'meetapenguin' and table_name='contact';";
        resultSet = statement.executeQuery(command);

        resultSet.next();
        int id = resultSet.getInt(1);
        resultSet.close();
        return id;
    }

    public Contact getContact(int contactID, int userID) throws SQLException {

        Contact contact = new Contact();
        Set<ContactInfo> contactInfos = new LinkedHashSet<ContactInfo>();


        //TODO:Add expiration check here. If expired, create a contact object with only the boolean expired=true.
        //TODO: Create a boolean field in contact.

        //Grab contact from contact table.
        String command = "select * from contact where id=%d";
        command = String.format(command, contactID);
        resultSet = statement.executeQuery(command);

        //Nothing found
        if (!resultSet.next()) {
            return null;
        }

        //Build contact object.
        String name = resultSet.getString(2);
        String description = resultSet.getString(3);
        String photoUrl = resultSet.getString(4);

        contact.setName(name);
        contact.setDescription(description);
        contact.setPhotoUrl(photoUrl);
        contact.setId(contactID);

        resultSet.close();


        // Build ContactInfo from table. Grabs only the contact_info that userA knows aboud userB.
        //TODO: Does not take into account if information is expired. Maybe fix?
        command = "select * from (select * from contact_info where contactid=%d) as test where attributeid=any" +
                "(select attributeid from relationship where `from`=%d and `to`=%d);";
        command = String.format(command, contactID, userID, contactID);
        Statement stmt = connect.createStatement();
        ResultSet resultSet = stmt.executeQuery(command);

        // Build ContactInfo from results from table.
        while (resultSet.next()) {
            ContactInfo contactInfo = new ContactInfo();
            Attribute attribute = new Attribute();

            String attributeValue = resultSet.getString(2);
            int attributeID = resultSet.getInt(3);

            Statement attributeStatement = connect.createStatement();
            ResultSet attributeResultSet = attributeStatement.executeQuery(
                    "select name from attribute where id=" + attributeID + ";");
            attributeResultSet.next();
            String attributeName = attributeResultSet.getString(1);
            attributeResultSet.close();

            attribute.setName(attributeName);
            attribute.setId(attributeID);

            contactInfo.setAttribute(attribute);
            contactInfo.setAttributeValue(attributeValue);
            contactInfo.setId(resultSet.getInt(1));
            contactInfo.setEditing(false);

            contactInfos.add(contactInfo);
        }

        contact.setContactInfoArrayList(contactInfos);
        resultSet.close();
        stmt.close();

        return contact;
    }


    public ArrayList<Contact> getContactsLastUpdated(long lastUpdated, int userID) throws SQLException {

        ArrayList<Contact> arrayList = new ArrayList<Contact>();
        String command;

        //If 0 is input, then get all contacts for the contactid.
        //Otherwise get all contacts since the last time stamp
        if (lastUpdated == 0) {
            command = "select id from (select * from contact where (lastUpdateTime < NOW()))";
        } else {
            command = "select id from (select * from contact where (lastUpdateTime < from_unixtime(%d)))";
            command = String.format(command, lastUpdated);
        }

        //Only get contacts that the contactID knows.
        command += "as test where id=any(select `to` from relationship where `from`=" + userID + ");";

        Statement stmt = connect.createStatement();
        ResultSet resultSet = stmt.executeQuery(command);

        //Loop over all contacts found. Create contact objects.
        while (resultSet.next()) {
            Contact c = getContact(resultSet.getInt(1), userID);
            arrayList.add(c);
        }

        resultSet.close();
        stmt.close();

        return arrayList;
    }


    public void createRelationship(int userAid, Contact contact) throws SQLException {
        int fromID = userAid;
        int toID = contact.getId();


        String dateCommand = "from_unixtime(%d)";

        //TODO: handle case when expiration is null
        if (contact.getExpiration() != 0) {
            dateCommand = String.format(dateCommand, contact.getExpiration() / 1000);
        }

        //For each attribute, add to relationships table.
        for (ContactInfo ci : contact.getContactInfoArrayList()) {

            //Check first if it is already in the relationship table. If it is, then only update expiration
            String checkInTableCommand = "select * from relationship where `from`=%d AND `to`=%d AND attributeid=%d";
            checkInTableCommand = String.format(checkInTableCommand, fromID, toID, ci.getAttribute().getId());
            resultSet = statement.executeQuery(checkInTableCommand);

            //Relationship for this attribute is already in the table.
            if (resultSet.next()) {
                int rowID = resultSet.getInt(1);
                String updateExpCommand = "update relationship set expiration=%s where id=" + rowID;
                updateExpCommand = String.format(updateExpCommand, dateCommand);
                resultSet.close();
                statement.executeUpdate(updateExpCommand);
            }
            //Relationship not found. Insert relationship into table.
            else {
                String command = "insert into relationship values(default, %s,%d,%d,%d);";
                command = String.format(command, dateCommand, fromID, toID, ci.getAttribute().getId());
                statement.executeUpdate(command);
            }
        }

    }

    public InboxMessage createMessage(int userID, int contactID) throws SQLException {

        //First check if the message already exists. If it does, then do nothing.
        String command = "select * from message where `from`=%d and `to`=%d";
        command = String.format(command, userID, contactID);
        resultSet = statement.executeQuery(command);

        //If already in table, do nothing.
        if (resultSet.next()) {
            return null;
        }

        Timestamp timestamp = new Timestamp(new Date().getTime());
        command = "insert into message VALUES(default, %d, %d, %d, '%s')";
        command = String.format(command, userID, contactID, 0, timestamp.toString());

        statement.executeUpdate(command);

        //Find id of message.
        command = "select * from message where `from`=%d and `to`=%d";
        command = String.format(command, userID, contactID);
        resultSet = statement.executeQuery(command);

        resultSet.next();
        int cloudID = resultSet.getInt(1);

        Contact contact = getContact(contactID, 0);

        InboxMessage message = new InboxMessage();
        message.setCloudId(cloudID);
        message.setContact(contact);
        message.setMessage("");
        message.setStatus(false);
        message.setTimeStamp(timestamp.getTime());

        return message;
    }
}
