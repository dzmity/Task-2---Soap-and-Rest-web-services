package by.epam.rafalovich.archiveservice.provider.rest;

import by.epam.rafalovich.archiveservice.Archive;
import by.epam.rafalovich.archiveservice.CriteriaList;
import by.epam.rafalovich.archiveservice.OperationType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Path("/archiveservice")
public interface IArchiveService {

    @GET
    @Path("/findrecordsBySender/{senderId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Archive findRecordsBySender(@PathParam("senderId")long senderId);

    @POST
    @Path("/findrecordsJSON")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Archive findRecordsJSON(CriteriaList criteriaList);

    @GET
    @Path("/findrecords")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Archive findRecords(
            @QueryParam("senderId") BigInteger senderId,
            @QueryParam("recipientContact") String recipientContact,
            @QueryParam("startDateTime") XMLGregorianCalendar startDateTime,
            @QueryParam("endDateTime") XMLGregorianCalendar endDateTime,
            @QueryParam("operationType") OperationType operationType);

}
