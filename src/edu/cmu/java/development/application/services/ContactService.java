package edu.cmu.java.development.application.services;

import edu.cmu.java.development.application.resources.Attribute;
import edu.cmu.java.development.application.resources.Contact;
import edu.cmu.java.development.application.resources.ContactInfo;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by urbano on 4/6/16.
 */
@Path("/")
@Api(value = "/contacts", description = "Handle the contacts from Meet A Penguim Application")
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
    public List<Contact> getContacts(@ApiParam(required = false, value = "timestamp of the last time you call this API") @QueryParam("timestamp") long timestamp) throws SQLException {
        Contact contact = new Contact();
        contact.setId(2);
        contact.setName("User 1");
        contact.setDescription("Description 1");
        contact.setPhotoUrl("http://s3.amazonaws.com/37assets/svn/765-default-avatar.png");
        ArrayList<ContactInfo> contactInfoList = new ArrayList<ContactInfo>();
        ContactInfo contactInfo = new ContactInfo(new Attribute(1, "Nickname", "drawable/ic_account_box_black_24dp"), "teste", "teste_value");
        contactInfoList.add(contactInfo);
        contact.setContactInfoArrayList(contactInfoList);

        ArrayList<Contact> contactArrayList = new ArrayList<Contact>();
        contactArrayList.add(contact);
        return contactArrayList;
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
    public Contact createContact(@ApiParam(required = true, value = "The contact to be created") Contact contact) throws SQLException {
        Random randomGenerator = new Random();
        contact.setId(randomGenerator.nextInt(Integer.MAX_VALUE));
        return contact;
    }

    @GET
    @Path("/contacts/{id}/")
    @ApiOperation(
            value = "Get a contact information for a specific user",
            response = Contact.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieving the user"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized user. Please check the authorization header"),
            @ApiResponse(code = 403, message = "Forbideen access to this information"),
            @ApiResponse(code = 400, message = "Invalid arguments")})
    public Contact getContact(@PathParam("id") String id) {
        Contact contact = new Contact();
        contact.setName("Prin");
        contact.setDescription("Student");
        contact.setExpiration(new Date());
        return contact;
    }
}
