package edu.cmu.java.development.application.services;

import edu.cmu.java.development.application.database.Database;
import edu.cmu.java.development.application.resources.Attribute;
import edu.cmu.java.development.application.resources.Contact;
import edu.cmu.java.development.application.resources.ContactInfo;
import edu.cmu.java.development.application.resources.ContactInfo;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by urbano on 4/6/16.
 */
@Path("/")
@Api(value = "/contacts", description = "Handle the contacts from Meet A Penguin Application")
public class ContactService {

    @GET
    @Path("/contacts")
    @Produces("application/json")
    @ApiOperation(
            value = "Retrieve all contacts information for a specific user modified after a timestamp",
            response = Contact.class,
            responseContainer = "List",
            notes = "Use 0 as timestamp to access all"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieving the contact list"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized to access this user contact list. Please check the authorization header")})
    public List<Contact> getContacts(@ApiParam(required = false, value = "timestamp of the last time you call this API") @QueryParam("timestamp") long timestamp,
                                     @ApiParam(required = true, value = "contactID of user") @QueryParam("id") int contactID) throws SQLException {
//        Contact contact = new Contact();
//        contact.setId(2);
//        contact.setName("User 1");
//        contact.setDescription("Description 1");
//        contact.setPhotoUrl("http://s3.amazonaws.com/37assets/svn/765-default-avatar.png");
//        ArrayList<ContactInfo> contactInfoList = new ArrayList<ContactInfo>();
//        ContactInfo contactInfo = new ContactInfo(new Attribute(1, "Nickname", "drawable/ic_account_box_black_24dp"), "teste", "teste_value");
//        contactInfoList.add(contactInfo);
//        contact.setContactInfoArrayList(contactInfoList);
//
//        ArrayList<Contact> contactArrayList = new ArrayList<Contact>();
//        contactArrayList.add(contact);
//
//        return contactArrayList;


        //TODO: Implement.
        return null;
    }

    @POST
    @Path("/contacts")
    @Produces("application/json")
    @ApiOperation(
            value = "Create an contact",
            response = Contact.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful creating the contact"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized to access this user contact list. Please check the authorization header")})
    public Contact createContact(@ApiParam(required = true, value = "The contact to be created") @QueryParam("contact") Contact contact) throws SQLException {
        Database database = new Database();

        if (contact != null && contact.getId() == null) {
            contact.setId(database.getNextContactTableID());

        }

        if (contact != null) {
            database.addContact(contact);
        }

//        Contact c = new Contact();
//
////        c.setExpiration(new Date());
//        c.setName("John");
//        c.setDescription("test3");
//        c.setPhotoUrl("test4");
//
//        ArrayList<ContactInfo> al = new ArrayList<ContactInfo>();
//        ContactInfo c1 = new ContactInfo();
//        Attribute a1 = new Attribute();
//        a1.setName("Facebook");
//        a1.setId(4);
//        c1.setAttributeValue("myfbpage");
//        c1.setAttribute(a1);
////        c1.setId(10);
//
//        ContactInfo c2 = new ContactInfo();
//        Attribute a2 = new Attribute();
//        a2.setName("Location");
//        a2.setId(10);
//        c2.setAttributeValue("pittsburgh");
//        c2.setAttribute(a2);
////        c2.setId(10);
//
//        al.add(c1);
//        al.add(c2);
//
//        c.setContactInfoArrayList(al);
//        return c;


        return contact;
        //TODO: Think about return value. Currently does add to contact table correctly.
    }

    @GET
    @Path("/contacts/{id}/")
    @Produces("application/json")
    @ApiOperation(
            value = "Get a contact information for a specific user",
            response = Contact.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieving the user"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized user. Please check the authorization header"),
            @ApiResponse(code = 403, message = "Forbidden access to this information"),
            @ApiResponse(code = 400, message = "Invalid arguments")})
    public Contact getContact(@PathParam("id") String id) throws SQLException {
        Database database = new Database();

        Contact contact = database.getContact(Integer.parseInt(id));

        return contact;
        //TODO: Think if we need to pass any other parameters. Currently, returns all information of a given contact.
        //TODO: Maybe need to provide the caller's contactid so we can only provide the contact info that the caller is allowed to see.
    }
}
