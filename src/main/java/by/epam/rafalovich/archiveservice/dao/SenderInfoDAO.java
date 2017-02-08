package by.epam.rafalovich.archiveservice.dao;

import by.epam.rafalovich.archiveservice.entity.SenderInfo;

public interface SenderInfoDAO extends GenericDAO<SenderInfo> {

    SenderInfo findSenderByPhoneNumber(String phoneNumber);

    SenderInfo findSenderByEmail(String email);

    SenderInfo findSenderByFax(String fax);

}
