package com.Desert.Repository;

import com.Desert.Entity.IDea.IDea;
import com.Desert.Entity.IDea.IDeaNote;
import com.Desert.Entity.IDea.IDeaResourceGroup;
import com.Desert.Entity.IDea.IDeaTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IDeaRepoBean implements IDeaRepo {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<IDeaTask> getTaskList(IDea iDea) {
        Session session = sessionFactory.getCurrentSession();
        Query<IDeaTask> query = session.createNativeQuery("SELECT * FROM IDeaTask WHERE ideaID = :ideaID", IDeaTask.class);
        return query.setParameter("ideaID", iDea.getID()).list();
    }

    @Override
    public int insertTask(IDeaTask task) {
        Session session = sessionFactory.getCurrentSession();
        return (int) session.save(task);
    }

    @Override
    public void updateTask(IDeaTask task) {
        Session session = sessionFactory.getCurrentSession();
        session.update(task);
    }

    @Override
    public void deleteTask(IDeaTask task) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(task);
    }

    @Override
    public List<IDeaNote> getNoteList(IDea iDea) {
        Session session = sessionFactory.getCurrentSession();
        Query<IDeaNote> query = session.createNativeQuery("SELECT * FROM IDeaNote WHERE ideaID = :ideaID", IDeaNote.class);
        return query.setParameter("ideaID", iDea.getID()).list();
    }

    @Override
    public int insertResources(IDeaResourceGroup group) {
        Session session = sessionFactory.getCurrentSession();
        return (int) session.save(group);
    }
}
