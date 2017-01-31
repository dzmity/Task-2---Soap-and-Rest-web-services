package by.epam.rafalovich.archiveservice.provider;


import by.epam.rafalovich.wsdl.archiveservice_wsdl.ArchivePortType;

/**
 * Created by Dzmitry_Rafalovich on 1/30/2017.
 */

public class Provider implements ArchivePortType {

    @Override
    public String findRecords( String request) {
        return request + 123;
    }
}
