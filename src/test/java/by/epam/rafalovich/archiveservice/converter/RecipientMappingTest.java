package by.epam.rafalovich.archiveservice.converter;

import by.epam.rafalovich.archiveservice.CommunicationChannelType;
import by.epam.rafalovich.archiveservice.RecipientType;
import by.epam.rafalovich.archiveservice.entity.CommunicationChannel;
import by.epam.rafalovich.archiveservice.entity.Recipient;
import org.dozer.DozerBeanMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyLenientEquals;

/**
 * Created by Dzmitry_Rafalovich on 2/8/2017.
 */
public class RecipientMappingTest {

    private static final String PATH = "mapping/dozer_mapping.xml";

    private static DozerBeanMapper mapper;

    @BeforeClass
    public static void before() throws Exception {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList(PATH));
    }

    @Test
    public void testRecipientTypeMapping() {

        long id = 10L;
        String contact = "+375336012781";
        CommunicationChannelType type = CommunicationChannelType.SMS;
        CommunicationChannel channel = CommunicationChannel.SMS;

        RecipientType dto = new RecipientType();
        dto.setId(BigInteger.valueOf(id));
        dto.setRecipientContact(contact);
        dto.setChannelType(type);

        Recipient domain = new Recipient();
        domain.setRecipientId(id);
        domain.setRecipientContact(contact);
        domain.setChanel(channel);

        Recipient result = mapper.map(dto, Recipient.class);

        assertEquals(result, domain);
    }

    @Test
    public void testRecipientMapping() {

        long id = 5L;
        String contact = "qwerty@gmail.com";
        CommunicationChannelType type = CommunicationChannelType.EMAIL;
        CommunicationChannel channel = CommunicationChannel.EMAIL;

        Recipient domain = new Recipient();
        domain.setRecipientId(id);
        domain.setRecipientContact(contact);
        domain.setChanel(channel);

        RecipientType result = mapper.map(domain, RecipientType.class);

        assertPropertyLenientEquals("id", BigInteger.valueOf(id), result);
        assertPropertyLenientEquals("recipientContact", contact, result);
        assertPropertyLenientEquals("channelType", type, result);
    }
}
