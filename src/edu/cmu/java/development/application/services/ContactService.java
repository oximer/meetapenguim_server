package edu.cmu.java.development.application.services;

import edu.cmu.java.development.application.database.Database;
import edu.cmu.java.development.application.resources.Contact;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Contact> getContacts(@ApiParam(required = false, value = "timestamp of the last time you call this API") @HeaderParam("timestamp") long timestamp,
                                     @ApiParam(required = true, value = "contactID of user") @HeaderParam("UserId") int userID) throws SQLException {
        Database database = new Database();
        ArrayList<Contact> arrayList = database.getContactsLastUpdated(timestamp, userID);

        return arrayList;
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
        Database database = new Database();

        //Add to database.
        if (contact != null && contact.getId() == null) {
            contact.setId(database.getNextContactTableID());
            database.addContact(contact);
        }
        //If it is already in database, update instead.
        else if (contact != null) {
                contact = database.updateContact(contact);
        }

        return contact;
    }

    @GET
    @Path("/contacts/{contactId}/")
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
    public Contact getContact(@PathParam("contactId") String contactIdToFind, @QueryParam("userID") int userID) throws SQLException {
        Database database = new Database();

        Contact contact = database.getContact(Integer.parseInt(contactIdToFind), userID);

        return contact;
    }
}
