package by.epam.rafalovich.archiveservice.dao;


import by.epam.rafalovich.archiveservice.entity.SenderInfo;
import by.epam.rafalovich.archiveservice.exception.DAOException;

/**
 *
 *
 */
public interface SenderInfoDAO extends GenericDAO<SenderInfo> {

    SenderInfo findSenderByPhoneNumber(String phoneNumber) throws DAOException;

    SenderInfo findSenderByEmail(String email) throws DAOException;

    SenderInfo findSenderByFax(String fax) throws DAOException;

}
