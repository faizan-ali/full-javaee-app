package co.workamerica.api.rest.clients.profile;

import co.workamerica.entities.clients.Clients;
import co.workamerica.functionality.persistence.ClientPersistence;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Faizan on 7/21/2016.
 */
@Path("/employers")
public class Profile {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Clients getClient(@PathParam("id") String clientID) {
        return ClientPersistence.getByID(clientID);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Clients updateClient () {
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Clients createClient (@DefaultValue("0") @FormParam("clientID") String clientID,
                                 @DefaultValue("") @FormParam("firstName") String firstName,
                                 @DefaultValue("") @FormParam("lastName") String lastName,
                                 @DefaultValue("") @FormParam("email") String email,
                                 @DefaultValue("") @FormParam("password") String password,
                                 @DefaultValue("") @FormParam("company") String company,
                                 @DefaultValue("") @FormParam("viewLimit") String viewLimit,
                                 @DefaultValue("0") @FormParam("schoolAccountID") String schoolAccountID,
                                 @DefaultValue("") @FormParam("dateCreated") String dateCreated,
                                 @DefaultValue("") @FormParam("assignedNumber") String assignedNumber,
                                 @DefaultValue("") @FormParam("approved") String approved,
                                 @DefaultValue("0") @FormParam("companyID") String companyID) {
        return null;
    }

}
