package by.epam.rafalovich.archiveservice.provider.rest;

import by.epam.rafalovich.archiveservice.Archive;
import by.epam.rafalovich.archiveservice.CriteriaList;
import by.epam.rafalovich.archiveservice.Record;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import org.dozer.Mapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Service("archiveService")
public class ArchiveService implements IArchiveService {

    @Autowired
    RecordDAO recordDAOImpl;

    @Autowired
    private DozerBeanMapperFactoryBean dozerBean;

    @Override
    public Archive findRecords(long senderId) {

        Archive archive = new Archive();
        List<Record> recordList = archive.getRecord();

        try {

            Collection<CommunicationRecord> records = recordDAOImpl.findRecordsBySender(((senderId)));
            Mapper mapper = (Mapper) dozerBean.getObject();

            for (CommunicationRecord x : records) {
                Record record = mapper.map(x, Record.class);
                recordList.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return archive;
    }
}
