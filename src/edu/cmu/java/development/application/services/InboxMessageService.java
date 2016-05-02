package edu.cmu.java.development.application.services;

import edu.cmu.java.development.application.database.Database;
import edu.cmu.java.development.application.resources.InboxMessage;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by urbano on 4/11/16.
 */
@Path("/")
@Api(value = "/messages", description = "Handle the renew messages from Meet A Penguim Application")
public class InboxMessageService {

    @GET
    @Path("/messages")
    @Produces("application/json")
    @ApiOperation(
            value = "List all messages for a specific user",
            response = InboxMessage.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieving the list of messages"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized to access this user contact list. Please check the authorization header")})
    public List<InboxMessage> getMessages(@ApiParam(required = false, value = "timestamp of the last time you call this API") @QueryParam("timestamp") long timestamp
            , @HeaderParam("userID") int userID) throws SQLException {
            Database database = new Database();
        ArrayList<InboxMessage> arrayList = database.getMessagesLastUpdated(timestamp, userID);
        for (InboxMessage inboxMessage : arrayList) {
            inboxMessage.setMessage("This contact is requesting a renew.");
        }
        return arrayList;
    }

    @POST
    @Path("/messages")
    @Produces("application/json")
    @ApiOperation(
            value = "Create a message for a specific user",
            response = InboxMessage.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful creating the message"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 403, message = "Forbidden create a message with a user that you don't have relationship"),
            @ApiResponse(code = 401, message = "Unauthorized to access this user contact list. Please check the authorization header")})
    public List<InboxMessage> createMessage(@ApiParam(required = true, value = "the contact destination") @QueryParam("contactId") int contactId,
                                            @HeaderParam("userID") int userID) throws SQLException {

        Database database = new Database();
        InboxMessage message = database.createMessage(userID, contactId);

        ArrayList<InboxMessage> arrayList = new ArrayList<InboxMessage>();
        arrayList.add(message);

        return arrayList;
    }

    @GET
    @Path("/messages/{id}/")
    @ApiOperation(
            value = "Get a specific message",
            response = InboxMessage.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful getting the message"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized user. Please check the authorization header"),
            @ApiResponse(code = 403, message = "Forbideen access to this information"),
            @ApiResponse(code = 400, message = "Invalid arguments")})
    public InboxMessage getMessage(@PathParam("id") int id) throws SQLException {
        Database database = new Database();
        InboxMessage message = database.getMessage(id);

        return message;
    }

    @POST
    @Path("/messages/{id}/status/")
    @Produces("application/json")
    @ApiOperation(
            value = "Renew a Contact Info Message",
            response = InboxMessage.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful approving a message"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized user. Please check the authorization header"),
            @ApiResponse(code = 400, message = "Invalid arguments")})
    public InboxMessage approveMessage(@ApiParam(required = true, value = "the user ID") @HeaderParam("userId") Integer userId,
                                           @PathParam("id") int messageId, @QueryParam("accepted") boolean accepted,
                                           @QueryParam("newExpiration") long expiration) throws SQLException {

        Database database = new Database();


        //Find correct contact_info, set them to new expiry date.
        database.updateRelationshipFromMessage(userId, messageId, expiration);

        //Find message, delete from table.
        database.deleteMessage(messageId);

        return new InboxMessage();
    }
}
