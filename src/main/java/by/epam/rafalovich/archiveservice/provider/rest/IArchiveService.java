package by.epam.rafalovich.archiveservice.provider.rest;

import by.epam.rafalovich.archiveservice.Archive;
import by.epam.rafalovich.archiveservice.CriteriaList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Path("/archiveservice")
public interface IArchiveService {

    @GET
    @Path("/findrecords/{senderId}")

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Archive findRecords(@PathParam("senderId")long senderId);
}
