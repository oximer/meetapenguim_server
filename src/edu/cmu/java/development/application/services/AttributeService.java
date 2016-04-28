package edu.cmu.java.development.application.services;

import edu.cmu.java.development.application.resources.Attribute;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by urbano on 4/6/16.
 */
@Path("/")
@Api(value = "/attributes", description = "It describe all possible types for a contact info. Example: facebook, twitter, email, address, telephone, ...")
public class AttributeService {

    @GET
    @Path("/attributes")
    @Produces("application/json")
    @ApiOperation(
            value = "List all possible attributes",
            response = Attribute.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieving the attribute list"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public List<Attribute> board() throws SQLException {
        return new ArrayList<Attribute>();
        //TODO: Think if we actually need this API.
    }
}
