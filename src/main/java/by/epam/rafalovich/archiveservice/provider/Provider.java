package by.epam.rafalovich.archiveservice.provider;


import by.epam.rafalovich.archiveservice.Archive;
import by.epam.rafalovich.archiveservice.Record;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import by.epam.rafalovich.archiveservice.exception.DAOException;
import by.epam.rafalovich.wsdl.archiveservice_wsdl.ArchivePortType;

import org.dozer.Mapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.jws.WebParam;

import java.util.Collection;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 1/30/2017.
 */
@Service
public class Provider implements ArchivePortType {

    @Autowired
    RecordDAO recordDAOImpl;
    @Autowired
    private DozerBeanMapperFactoryBean dozerBean;
    @Autowired
    SenderDAO senderDAOImpl;

    @Override
    public Archive findRecords(@WebParam(partName = "request", name = "request", targetNamespace = "") String request) {

        Archive archive = new Archive();
        List<Record> recordList = archive.getRecord();

        try{

            Collection<CommunicationRecord> records = recordDAOImpl.findRecordsBySender(( new Long(request)));
            Mapper mapper = (Mapper) dozerBean.getObject();
            //mapper.setMappingFiles(Arrays.asList("mapping/dozer_mapping.xml"));

            for (CommunicationRecord x: records) {
                Record record = mapper.map(x, Record.class);
                recordList.add(record);
            }

        }catch (DAOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return archive;
    }


}
