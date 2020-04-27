package com.Desert.Repository;

import com.Desert.Entity.Project.Project;
import com.Desert.Entity.Project.ProjectNote;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepoBean implements ProjectRepo {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<ProjectNote> getNoteList(Project project) {
        Session session = sessionFactory.getCurrentSession();
        Query<ProjectNote> query = session.createNativeQuery("SELECT * FROM ProjectNote WHERE projectID = :projectID", ProjectNote.class);
        return query.setParameter("projectID", project.getID()).list();
    }
}
