package by.epam.rafalovich.archiveservice.jmx;

import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Created by Dzmitry_Rafalovich on 2/14/2017.
 */

//@ManagedResource(objectName = "mbeans:name=jmxRecord", description = "RecordBean.")
@Component
public class JmxRecord implements JmxRecordMBean {

    private static final Logger LOG = LoggerFactory.getLogger(JmxRecord.class);

    @Autowired
    RecordDAO recordDAOImpl;

    @ManagedOperation(description = "Shows a count of communicationRecords records.")
    public long showRecordCount() {

        LOG.info("Invocation of showRecordCount method in JmxRecord");

        return recordDAOImpl.findAll().size();
    }
}
