package by.epam.rafalovich.archiveservice.converter;

import by.epam.rafalovich.archiveservice.CommunicationChannelType;
import by.epam.rafalovich.archiveservice.entity.CommunicationChannel;
import org.dozer.DozerBeanMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by admin on 12.02.2017.
 */
public class CommunicationChannelMappingTest {

    private static final String PATH = "mapping/dozer_mapping.xml";

    private static DozerBeanMapper mapper;

    @BeforeClass
    public static void before() throws Exception {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList(PATH));
    }

    @Test
    public void testSMSCommunicationChannelTypeMapping() {

        CommunicationChannelType sms = CommunicationChannelType.SMS;

        CommunicationChannel result = mapper.map(sms, CommunicationChannel.class);

        assertNotNull(result);
        assertEquals(result, CommunicationChannel.SMS);
    }

    @Test
    public void testSMSCommunicationChannelMapping() {

        CommunicationChannel sms = CommunicationChannel.SMS;

        CommunicationChannelType result = mapper.map(sms, CommunicationChannelType.class);

        assertNotNull(result);
        assertEquals(result, CommunicationChannelType.SMS);
    }

    @Test
    public void testFAXCommunicationChannelTypeMapping() {

        CommunicationChannelType fax = CommunicationChannelType.FAX;

        CommunicationChannel result = mapper.map(fax, CommunicationChannel.class);

        assertNotNull(result);
        assertEquals(result, CommunicationChannel.FAX);
    }

    @Test
    public void testFAXCommunicationChannelMapping() {

        CommunicationChannel fax = CommunicationChannel.FAX;

        CommunicationChannelType result = mapper.map(fax, CommunicationChannelType.class);

        assertNotNull(result);
        assertEquals(result, CommunicationChannelType.FAX);
    }

    @Test
    public void testEMAILCommunicationChannelTypeMapping() {

        CommunicationChannelType email = CommunicationChannelType.EMAIL;

        CommunicationChannel result = mapper.map(email, CommunicationChannel.class);

        assertNotNull(result);
        assertEquals(result, CommunicationChannel.EMAIL);
    }

    @Test
    public void testEMAILCommunicationChannelMapping() {

        CommunicationChannel email = CommunicationChannel.EMAIL;

        CommunicationChannelType result = mapper.map(email, CommunicationChannelType.class);

        assertNotNull(result);
        assertEquals(result, CommunicationChannelType.EMAIL);
    }
}
