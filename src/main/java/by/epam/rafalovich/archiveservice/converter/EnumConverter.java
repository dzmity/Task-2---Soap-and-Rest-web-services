package by.epam.rafalovich.archiveservice.converter;

import org.dozer.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Dzmitry_Rafalovich on 2/1/2017.
 */
public class EnumConverter implements CustomConverter {

    private static final Logger LOG = LoggerFactory.getLogger(EnumConverter.class);

    @Override
    public Object convert(Object destination, Object source, Class destinationClass, Class sourceClass) {

        if (null == source) {
            LOG.info("Source is null. Return null.");
            return null;
        }

        LOG.info("Mapping Enum class and EnumType class.");
        if (Enum.class.isAssignableFrom(sourceClass) && Enum.class.isAssignableFrom(destinationClass)) {

                return Enum.valueOf((Class<Enum>) destinationClass, String.valueOf(source));
        }

        LOG.error("DestinationClass or sourceClass classes are not assignable for Enum.");
        return null;
    }
}
