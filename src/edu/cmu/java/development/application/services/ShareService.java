package edu.cmu.java.development.application.services;

import edu.cmu.java.development.application.resources.Contact;
import edu.cmu.java.development.application.resources.ShareAuthorization;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by urbano on 4/6/16.
 */
@Path("/")
@Api(value = "/relationship", description = "Handle the contacts from Meet A Penguim Application")
public class ShareService {

    @GET
    @Path("/relationships")
    @Produces("application/json")
    @ApiOperation(
            value = "Get all the relationship of a specific user",
            response = Contact.class,
            responseContainer = "List",
            notes = "This API works with pagination it return 20 relationship per request. Make a sequence of call until " +
                    "retrieve all information. It returns the sorted list of relationship."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieving the relationship list"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized to access this user contact list. Please check the authorization header")})
    public List<Contact> getRelationships(@ApiParam(required = false, value = "The id of last user that you receive") @QueryParam("lastContactId") String lastContactId) throws SQLException {
        return new ArrayList<Contact>();
    }

    @POST
    @Path("/relationships")
    @Produces("application/json")
    @ApiOperation(
            value = "Create an relationship request of between User A and User B",
            response = Contact.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful creating the relationship"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized to access this user contact list. Please check the authorization header"),
            @ApiResponse(code = 403, message = "Missing user B authorization to add him as a User A contact")})
    public Contact createRelationship(@ApiParam(required = true, value = "The authorization for User B to be added as your contact") @QueryParam("shareAuthorization") ShareAuthorization authorization, @ApiParam(required = true, value = "The contact to be created") @QueryParam("contact") Contact contact) throws SQLException {
        return new Contact();
    }
}