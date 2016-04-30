package edu.cmu.java.development.application.database;

import edu.cmu.java.development.application.resources.Attribute;
import edu.cmu.java.development.application.resources.Contact;
import edu.cmu.java.development.application.resources.ContactInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

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
            String attribute = contactInfo.getAttribute().getName();

            command = "select id from attribute where name='%s'";
            command = String.format(command, attribute);
            resultSet = statement.executeQuery(command);

            //TODO: maybe add exceptionhandling incase attribute not in table
            resultSet.next();
            int attributeid = resultSet.getInt(1);
            resultSet.close();

            command = "insert into contact_info values(default, '%s', %d, %d)";
            command = String.format(command, contactInfo.getAttributeValue(),
                    attributeid, contactID);

            statement.executeUpdate(command);

        }

    }

    public void updateContact(Contact contact) throws SQLException {
        int contactID = contact.getId();
        Timestamp timestamp = new Timestamp(new Date().getTime());

        //Update contact table.
        String command = "update contact set name='%s',description='%s', photoUrl='%s',lastUpdateTime='%s' where id=%d";
        command = String.format(command, contact.getName(),
                contact.getDescription(), contact.getPhotoUrl(), timestamp.toString(), contactID);
        statement.executeUpdate(command);

        //Update contact_info table.
        for (ContactInfo contactInfo : contact.getContactInfoArrayList()) {
            String attribute = contactInfo.getAttribute().getName();

            command = "select id from attribute where name='%s'";
            command = String.format(command, attribute);
            resultSet = statement.executeQuery(command);

            //TODO: maybe add exceptionhandling incase attribute not in table
            resultSet.next();
            int attributeid = resultSet.getInt(1);
            resultSet.close();

            //Find if contact info is already in table.
            command = "select id from contact_info where attributeid=%d and contactid=%d";
            command = String.format(command, attributeid, contactID);
            resultSet = statement.executeQuery(command);

            //If the contact_info is already in the table, update the value.
            if (resultSet.next()) {
                int rowID = resultSet.getInt(1);
                resultSet.close();
                command = "update contact_info set value='%s' where id=" + rowID;
                command = String.format(command, contactInfo.getAttributeValue());
                statement.executeUpdate(command);
            }
            //If it is not in the table, insert into the table.
            else {
                command = "insert into contact_info values(default, '%s', %d, %d)";
                command = String.format(command, contactInfo.getAttributeValue(),
                        attributeid, contactID);

                statement.executeUpdate(command);
            }


        }

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
        resultSet = statement.executeQuery(command);

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
            contactInfo.setId(contactID);
            contactInfo.setEditing(false);

            contactInfos.add(contactInfo);
        }

        contact.setContactInfoArrayList(contactInfos);

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
            command = "select id from (select * from contact where (lastUpdateTime < from_unixtime(%d, '%Y-%m-%d %T')))";
            command = String.format(command, lastUpdated);
        }

        //Only get contacts that the contactID knows.
        command += "as test where id=any(select `to` from relationship where `from`=" + userID + ");";

        resultSet = statement.executeQuery(command);

        //Loop over all contacts found. Create contact objects.
        while (resultSet.next()) {
            Contact c = getContact(resultSet.getInt(1), userID);
            arrayList.add(c);
        }

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
}
