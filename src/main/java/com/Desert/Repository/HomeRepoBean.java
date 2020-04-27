package com.Desert.Repository;

import com.Desert.Entity.IDea.IDea;
import com.Desert.Entity.Project.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HomeRepoBean implements HomeRepo {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Project> getProjects() {
        Session session = sessionFactory.getCurrentSession();
        Query<Project> query = session.createNativeQuery("SELECT * FROM Project", Project.class);
        return query.getResultList();
    }

    @Override
    public void insertProject(Project project) {
        Session session = sessionFactory.getCurrentSession();
        session.save(project);
    }

    @Override
    public List<IDea> getIDeas() {
        Session session = sessionFactory.getCurrentSession();
        Query<IDea> query = session.createNativeQuery("SELECT * FROM IDea", IDea.class);
        return query.getResultList();
    }

    @Override
    public void insertIDea(IDea iDea) {
        Session session = sessionFactory.getCurrentSession();
        session.save(iDea);
    }
}
