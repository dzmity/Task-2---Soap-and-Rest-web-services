package by.epam.rafalovich.archiveservice.dao.impl;


import by.epam.rafalovich.archiveservice.dao.SenderInfoDAO;
import by.epam.rafalovich.archiveservice.entity.SenderInfo;
import by.epam.rafalovich.archiveservice.exception.DAOException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dzmitry_Rafalovich on 1/21/2017.
 */
@Repository
@Transactional
public class SenderInfoDAOImpl extends GenericDAOImpl<SenderInfo> implements SenderInfoDAO {

    @Override
    public SenderInfo findSenderByPhoneNumber(String phoneNumber) throws DAOException {
        Query query = currentSession().getNamedQuery("findByNumberInfo").setString("number",phoneNumber);
        return (SenderInfo) query.uniqueResult();
    }

    @Override
    public SenderInfo findSenderByEmail(String email) throws DAOException {
        Query query = currentSession().getNamedQuery("findByEmailInfo").setString("email",email);
        return (SenderInfo)  query.uniqueResult();
    }

    @Override
    public SenderInfo findSenderByFax(String fax) throws DAOException {
        Query query = currentSession().getNamedQuery("findByFaxInfo").setString("fax",fax);
        return (SenderInfo) query.uniqueResult();
    }
}
