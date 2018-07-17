package db;

import models.Department;
import models.Manager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class DBManager {


    public static Manager findManagerForDept(Department department) {

        Session session;

        session = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = session.createCriteria(Manager.class);
        cr.add(Restrictions.eq("department", department));
        return (Manager) cr.uniqueResult();
    }
}
