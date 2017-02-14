package by.epam.rafalovich.archiveservice.provider.rest;

import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import by.epam.rafalovich.archiveservice.entity.Operation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Path("/recordservice")
public interface IArchiveService {

    @GET
    @Path("/records")
    @Produces(MediaType.APPLICATION_JSON)
    List<CommunicationRecord> findRecords(
            @QueryParam("senderId") Long senderId,
            @QueryParam("recipientContact") String recipientContact,
            @QueryParam("startDateTime") String startDateTime,
            @QueryParam("endDateTime") String endDateTime,
            @QueryParam("operationType") Operation operationType);

}
