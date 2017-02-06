package by.epam.rafalovich.archiveservice.provider.rest;

import by.epam.rafalovich.archiveservice.Sender;
import by.epam.rafalovich.archiveservice.SenderArchive;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Path("/senderservice")
public interface ISenderService {

    @GET
    @Path("/findallsenders")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    SenderArchive findAllSenders();

    @GET
    @Path("/findsender/{senderId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Sender findSender(@PathParam("senderId") int senderId);

    @PUT
    @Path("/updatesender")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    void updateSender(Sender sender);

    @POST
    @Path("/createsender")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    void createSender(Sender sender);

    @DELETE
    @Path("/deletesender")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    void deleteSender(Sender sender);
}
