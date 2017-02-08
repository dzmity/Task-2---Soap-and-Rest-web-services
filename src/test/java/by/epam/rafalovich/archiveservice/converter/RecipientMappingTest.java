package by.epam.rafalovich.archiveservice.converter;

import by.epam.rafalovich.archiveservice.ChannelType;
import by.epam.rafalovich.archiveservice.Recipient;
import by.epam.rafalovich.archiveservice.entity.CommunicationChannel;
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
    public void testRecipientMarshalling() {

        long id = 10L;
        String contact = "+375336012781";
        ChannelType type = ChannelType.SMS;
        CommunicationChannel channel = CommunicationChannel.SMS;

        Recipient dto = new Recipient();
        dto.setId(BigInteger.valueOf(id));
        dto.setRecipientContact(contact);
        dto.setChannelType(type);

        by.epam.rafalovich.archiveservice.entity.Recipient domen = new by.epam.rafalovich.archiveservice.entity.Recipient();
        domen.setRecipientId(id);
        domen.setRecipientContact(contact);
        domen.setChanel(channel);

        by.epam.rafalovich.archiveservice.entity.Recipient result = mapper.map(dto, by.epam.rafalovich.archiveservice.entity.Recipient.class);

        assertEquals(result, domen);
    }

    @Test
    public void testRecipientUnmarshalling() {

        long id = 5L;
        String contact = "qwerty@gmail.com";
        ChannelType type = ChannelType.EMAIL;
        CommunicationChannel channel = CommunicationChannel.EMAIL;

        by.epam.rafalovich.archiveservice.entity.Recipient domen = new by.epam.rafalovich.archiveservice.entity.Recipient();
        domen.setRecipientId(id);
        domen.setRecipientContact(contact);
        domen.setChanel(channel);

        by.epam.rafalovich.archiveservice.Recipient result = mapper.map(domen, by.epam.rafalovich.archiveservice.Recipient.class);

        assertPropertyLenientEquals("id", BigInteger.valueOf(id), result);
        assertPropertyLenientEquals("recipientContact", contact, result);
        assertPropertyLenientEquals("channelType", type, result);
    }
}
