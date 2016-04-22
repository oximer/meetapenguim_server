package edu.cmu.java.development.application.services;

import edu.cmu.java.development.application.resources.Contact;
import edu.cmu.java.development.application.resources.InboxMessage;
import io.swagger.annotations.*;

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
        contact.setName("User 1");
        contact.setDescription("Description 1");
        contact.setExpiration(new Date());
        contact.setPhotoUrl("http://s3.amazonaws.com/37assets/svn/765-default-avatar.png");
        InboxMessage message = new InboxMessage();
        message.setContact(contact);
        message.setMessage("Renew it and be friendly");
        message.setTimeStamp(new Date().getTime());
        List<InboxMessage> messageList = new ArrayList<InboxMessage>();
        messageList.add(message);
        return messageList;
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
            @ApiResponse(code = 403, message = "Forbideen create a message with a user that you don't have relationship"),
            @ApiResponse(code = 401, message = "Unauthorized to access this user contact list. Please check the authorization header")})
    public List<InboxMessage> createMessage(@ApiParam(required = true, value = "the contact destination") @QueryParam("contactId") String contactId) throws SQLException {
        return new ArrayList<InboxMessage>();
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
    }

    @POST
    @Path("/messages/{id}/status")
    @ApiOperation(
            value = "Renew a Contact Info Message",
            response = InboxMessage.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful approving a message"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized user. Please check the authorization header"),
            @ApiResponse(code = 400, message = "Invalid arguments")})
    public InboxMessage approveMessage(@PathParam("id") String id, @ApiParam(required = true, value = "status of message: Approved or Denied") @QueryParam("status") boolean status) {
        return new InboxMessage();
    }
}
