package by.epam.rafalovich.archiveservice.provider.rest;

import by.epam.rafalovich.archiveservice.entity.Sender;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Path("/senderservice")
public interface ISenderService {

    @GET
    @Path("/senders")
    @Produces(MediaType.APPLICATION_JSON)
    List<Sender> findAllSenders();

    @GET
    @Path("/sender/{senderId}")
    @Produces(MediaType.APPLICATION_JSON)
    Sender findSender(@PathParam("senderId") long senderId);

    @PUT
    @Path("/senders")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateSender(Sender sender);

    @POST
    @Path("/senders")
    @Consumes(MediaType.APPLICATION_JSON)
    void createSender(Sender sender);

    @DELETE
    @Path("senders/{id}")
    void deleteSender(@PathParam("id") long id);
}
