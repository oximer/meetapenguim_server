package edu.cmu.java.development.application.database;

import edu.cmu.java.development.application.resources.Attribute;
import edu.cmu.java.development.application.resources.Contact;
import edu.cmu.java.development.application.resources.ContactInfo;

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
        String url = "jdbc:mysql://localhost/meetapenguin";
        String user = "root";
        String pw = "root";

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
//        Date date = contact.getExpiration();
        Timestamp timestamp = new Timestamp(new Date().getTime());

        String command = "insert into contact values(default, '%s','%s','%s', '%s');";
        command = String.format(command, contact.getName(),
                contact.getDescription(), contact.getPhotoUrl(), timestamp.toString());

        statement.executeUpdate(command);

        for (ContactInfo contactInfo : contact.getContactInfoArrayList()) {
            String attribute = contactInfo.getAttributeName();

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

    public int getNextContactTableID() throws SQLException {
        String command = "select auto_increment from information_schema.tables where table_schema = 'meetapenguin' and table_name='contact';";
        resultSet = statement.executeQuery(command);

        resultSet.next();
        int id = resultSet.getInt(1);
        resultSet.close();
        return id;
    }

    public Contact getContact(int contactID) throws SQLException {

        Contact contact = new Contact();

        String command = "select * from contact where id=%d";
        command = String.format(command, contactID);
        resultSet = statement.executeQuery(command);

        //Nothing found
        if (!resultSet.next()) {
            return null;
        }

        String name = resultSet.getString(2);
        String description = resultSet.getString(3);
        String photoUrl = resultSet.getString(4);

        contact.setName(name);
        contact.setDescription(description);
        contact.setPhotoUrl(photoUrl);
        contact.setId(contactID);

        resultSet.close();

        ArrayList<ContactInfo> contactInfos = new ArrayList<ContactInfo>();

        command = "select * from contact_info where contactid=%d;";
        command = String.format(command, contactID);
        resultSet = statement.executeQuery(command);

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


    //TODO: Unfinished
    public ArrayList<Contact> getContactsLastUpdated(long lastUpdated, int contactID) throws SQLException {

        String command;

        //If 0 is input, then get all contacts for the contactid.
        //Otherwise get all contacts since the last time stamp
        if (lastUpdated == 0) {
            command = "select * from contact where (lastUpdateTime < NOW());";
        } else {
            command = "select * from contact where (lastUpdateTime < from_unixtime(%d, '%Y-%m-%d %T'));";
            command = String.format(command, lastUpdated);
        }

        resultSet = statement.executeQuery(command);

        return null;
    }


    public void createRelationship(int userAid, Contact contact) {

    }
}
