package edu.cmu.java.development.application.services;

import edu.cmu.java.development.application.database.Database;
import edu.cmu.java.development.application.resources.Contact;
import io.swagger.annotations.*;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.sql.SQLException;

/**
 * Created by urbano on 4/6/16.
 */
@Path("/")
@Api(value = "/relationship", description = "Handle the contacts from Meet A Penguim Application")
public class ShareService {

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
    public Contact createRelationship(
            @ApiParam(required = true, value = "The Contact object of User B, with only the information that User A knows about User B") Contact contact,
            @ApiParam(required = true, value = "User A's contactID") @HeaderParam("id") int contactID) throws SQLException {

        System.out.println("=================================");
        System.out.println("contactID: " + contactID);
        System.out.println("contact: " + contact);
        System.out.println("=================================");

        Database database = new Database();
        database.createRelationship(contactID, contact);

        return new Contact();
    }
}
