package by.epam.rafalovich.archiveservice.provider.rest;

import by.epam.rafalovich.archiveservice.SenderArchive;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
