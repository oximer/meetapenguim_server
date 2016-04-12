package edu.cmu.java.development.application.services;

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
        return new ArrayList<Contact>();
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
        return new Contact();
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
        return new Contact();
    }
}
