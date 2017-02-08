package by.epam.rafalovich.archiveservice.dao;

import by.epam.rafalovich.archiveservice.entity.Sender;

/**
 * Created by Dzmitry_Rafalovich on 1/21/2017.
 */
public interface SenderDAO extends GenericDAO<Sender> {

    Sender findSenderBySenderName(String name);

    Sender findSenderByPhoneNumber(String number);

    Sender findSenderByFax(String fax);

    Sender findSenderByEmail(String email);
}
