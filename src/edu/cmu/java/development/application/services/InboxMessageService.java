package edu.cmu.java.development.application.services;

import edu.cmu.java.development.application.database.Database;
import edu.cmu.java.development.application.resources.Contact;
import edu.cmu.java.development.application.resources.InboxMessage;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
    public List<InboxMessage> getMessages(@ApiParam(required = false, value = "timestamp of the last time you call this API") @QueryParam("timestamp") long timestamp) throws SQLException {
        Contact contact = new Contact();
        contact.setId(2);
        contact.setName("User 1");
        contact.setDescription("Description 1");
        contact.setPhotoUrl("http://s3.amazonaws.com/37assets/svn/765-default-avatar.png");
        InboxMessage message = new InboxMessage();
        message.setCloudId(114);
        message.setContact(contact);
        message.setMessage("Renew it and be friendly");
        message.setTimeStamp(new Date().getTime());
        List<InboxMessage> messageList = new ArrayList<InboxMessage>();
        messageList.add(message);
        return messageList;
        //TODO: Implement. Currently returns mock data.
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
    public InboxMessage getMessage(@PathParam("id") String id) {
        return new InboxMessage();
        //TODO: Implement. Returns empty.
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
    public InboxMessage approveMessage(@ApiParam(required = true, value = "the user ID") @HeaderParam("userId") Integer user,
                                       @PathParam("id") String id, @QueryParam("accepted") boolean accepted) {
        return new InboxMessage();
        //TODO: Implement.
    }
}
