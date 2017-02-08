package by.epam.rafalovich.archiveservice.converter;

import by.epam.rafalovich.archiveservice.Sender;
import by.epam.rafalovich.archiveservice.SenderInfo;
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
public class SenderMappingTest {

    private static final String PATH = "mapping/dozer_mapping.xml";

    private static DozerBeanMapper mapper;

    @BeforeClass
    public static void before() throws Exception {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList(PATH));
    }

    @Test
    public void testSenderMarshalling() {

        long id = 10L;
        String name = "hotel";
        String fax = "123";
        String email = "qwerty@gmail.com";
        String number = "+375336012781";

        Sender dto = new Sender();
        by.epam.rafalovich.archiveservice.entity.Sender domen = new by.epam.rafalovich.archiveservice.entity.Sender();
        domen.setSenderId(id);
        domen.setSenderName(name);

        SenderInfo dtoInfo = new SenderInfo();
        by.epam.rafalovich.archiveservice.entity.SenderInfo domenInfo = new by.epam.rafalovich.archiveservice.entity.SenderInfo();
        dto.setId(BigInteger.valueOf(id));
        dto.setSenderName(name);

        dtoInfo.setId(BigInteger.valueOf(id));
        domenInfo.setId(id);
        dtoInfo.setPhoneNumber(number);
        domenInfo.setSenderPhoneNumber(number);
        dtoInfo.setEmail(email);
        domenInfo.setSenderEmail(email);
        dtoInfo.setFax(fax);
        domenInfo.setSenderFax(fax);

        dto.setSenderInfo(dtoInfo);
        domen.setInfo(domenInfo);

        by.epam.rafalovich.archiveservice.entity.Sender result = mapper.map(dto, by.epam.rafalovich.archiveservice.entity.Sender.class);

        assertEquals(domen, result);
    }

    @Test
    public void testSenderUnmarshalling() {

        long id = 11L;
        String name = "hotel_california";
        String fax = "12345";
        String email = "qwerty@gmail.com";
        String number = "+375336012781";


        by.epam.rafalovich.archiveservice.entity.Sender domen = new by.epam.rafalovich.archiveservice.entity.Sender();
        domen.setSenderId(id);
        domen.setSenderName(name);

        by.epam.rafalovich.archiveservice.entity.SenderInfo domenInfo = new by.epam.rafalovich.archiveservice.entity.SenderInfo();
        domenInfo.setId(id);
        domenInfo.setSenderPhoneNumber(number);
        domenInfo.setSenderEmail(email);
        domenInfo.setSenderFax(fax);
        domen.setInfo(domenInfo);

        by.epam.rafalovich.archiveservice.Sender result = mapper.map(domen, by.epam.rafalovich.archiveservice.Sender.class);

        assertPropertyLenientEquals("id", BigInteger.valueOf(id), result);
        assertPropertyLenientEquals("senderName", name, result);
        assertPropertyLenientEquals("senderInfo.email", email, result);
        assertPropertyLenientEquals("senderInfo.fax", fax, result);
        assertPropertyLenientEquals("senderInfo.phoneNumber", number, result);
        assertPropertyLenientEquals("senderInfo.id", id, result);
    }
}
